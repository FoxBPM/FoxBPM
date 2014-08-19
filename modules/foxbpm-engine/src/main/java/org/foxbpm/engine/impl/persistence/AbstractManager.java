/**
 * Copyright 1996-2014 FoxBPM ORG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author kenshin
 * @author ych
 */
package org.foxbpm.engine.impl.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.db.HasRevision;
import org.foxbpm.engine.db.PersistentObject;
import org.foxbpm.engine.exception.FoxBPMDbException;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.interceptor.Session;
import org.foxbpm.engine.sqlsession.ISqlSession;
import org.foxbpm.engine.sqlsession.StatementMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 持久化管理器抽象类
 * 
 * @author kenshin
 */
public abstract class AbstractManager implements Session {

	public static Logger log = LoggerFactory.getLogger(AbstractManager.class);
	protected Map<String, CachedObject> cachedObjects = new HashMap<String, CachedObject>();
	protected List<PersistentObject> insertedObjects = new ArrayList<PersistentObject>();
	protected List<DeleteOperation> deleteOperations = new ArrayList<DeleteOperation>();

	private ISqlSession getSqlSession() {
		return getSession(ISqlSession.class);
	}

	protected <T> T getSession(Class<T> sessionClass) {
		return Context.getCommandContext().getSession(sessionClass);
	}

	public void insert(PersistentObject persistentObject) {
		insertedObjects.add(persistentObject);
		cachePut(persistentObject, false);
	}

	public void update(PersistentObject persistentObject) {
		cachePut(persistentObject, false);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List selectList(String statement, Object parameter) {
		List resultList = getSqlSession().selectList(statement, parameter);
		return filterLoadedObjects(resultList);
	}

	public Object selectOne(String statement, Object parameter) {
		Object result = getSqlSession().selectOne(statement, parameter);
		if (result instanceof PersistentObject) {
			PersistentObject loadedObject = (PersistentObject) result;
			result = cacheFilter(loadedObject);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends PersistentObject> T selectById(Class<T> entityClass , String id) {
		T persistentObject = cacheGet(id);
	    if (persistentObject!=null) {
	    	return persistentObject;
	    }
	    String selectStatement = StatementMap.getSelectStatement(entityClass);
	    persistentObject = (T) getSqlSession().selectOne(selectStatement, id);
	    if (persistentObject==null) {
	    	return null;
	    }
	    cachePut(persistentObject, true);
	    return persistentObject;
	}

	protected DeploymentEntityManager getDeploymentManager() {
		return getSession(DeploymentEntityManager.class);
	}

	protected ResourceManager getResourceManager() {
		return getSession(ResourceManager.class);
	}

	protected ProcessDefinitionManager getProcessDefinitionManager() {
		return getSession(ProcessDefinitionManager.class);
	}

	protected TaskManager getTaskManager() {
		return getSession(TaskManager.class);
	}

	protected IdentityLinkManager getIdentityLinkManager() {
		return getSession(IdentityLinkManager.class);
	}

	protected VariableManager getVariableManager() {
		return getSession(VariableManager.class);
	}

	protected TokenManager getTokenManager() {
		return getSession(TokenManager.class);
	}

	/**
	 * 
	 * 获取运行轨迹MANAGER
	 * 
	 * @return RunningTrackManager
	 * @exception
	 * @since 1.0.0
	 */
	protected RunningTrackManager getRunningTrackManager() {
		return getSession(RunningTrackManager.class);
	}

	/**
	 * 
	 * 获取调度器MANAGER
	 * 
	 * @return SchedulerManager
	 * @exception
	 * @since 1.0.0
	 */
	protected SchedulerManager getSchedulerManager() {
		return getSession(SchedulerManager.class);
	}

	protected ProcessInstanceManager getProcessInstanceManager() {
		return getSession(ProcessInstanceManager.class);
	}

	public void flush() {
		if(cachedObjects.isEmpty()){
			return;
		}
		removeUnnecessaryOperations();
		List<PersistentObject> updatedObjects = getUpdatedObjects();

		if (log.isDebugEnabled()) {
			log.debug("{}:flush summary: {} insert, {} update,{} delete.", this.getClass(),insertedObjects.size(), updatedObjects.size(), deleteOperations.size());
			log.debug("now executing flush...");
		}

		flushInserts();
		flushDeletes();
		flushUpdates(updatedObjects);
	}

	public void close() {

	}

	public void flushUpdates(List<PersistentObject> updateObjects) {
		for (PersistentObject updateObject : updateObjects) {
			String updateStatement = StatementMap.getUpdateStatement(updateObject);
			if (updateStatement == null) {
				throw new FoxBPMDbException("no update statement for " + updateObject.getClass() + " in the ibatis mapping files");
			}
			log.debug("updating: {}", updateObject);
			getSqlSession().update(updateStatement, updateObject);
		}
		updateObjects.clear();
	}

	public void flushInserts() {
		for (PersistentObject insertedObject : insertedObjects) {
			String insertStatement = StatementMap.getInsertStatement(insertedObject);
			if (insertStatement == null) {
				throw new FoxBPMDbException("no insert statement for " + insertedObject.getClass() + " in the ibatis mapping files");
			}
			log.debug("inserting: {}", insertedObject);
			getSqlSession().insert(insertStatement, insertedObject);
		}
		insertedObjects.clear();
	}

	protected void flushDeletes() {
		for (DeleteOperation delete : deleteOperations) {
			log.debug("executing: {}", delete);
			delete.execute();
		}
		deleteOperations.clear();
	}

	// delete
	// ///////////////////////////////////////////////////////////////////

	public void delete(String statement, Object parameter) {
		deleteOperations.add(new BulkDeleteOperation(statement, parameter));
	}

	public void delete(PersistentObject persistentObject) {
		for (DeleteOperation deleteOperation : deleteOperations) {
			if (deleteOperation.sameIdentity(persistentObject)) {
				log.debug("skipping redundant delete: {}", persistentObject);
				return; // Skip this delete. It was already added.
			}
		}

		deleteOperations.add(new CheckedDeleteOperation(persistentObject));
	}

	public void removeUnnecessaryOperations() {
		// 如果对象既在insert中，又在delete中，则直接删除，不做处理
		for (Iterator<DeleteOperation> deleteIt = deleteOperations.iterator(); deleteIt.hasNext();) {
			DeleteOperation deleteOperation = deleteIt.next();
			for (Iterator<PersistentObject> insertIt = insertedObjects.iterator(); insertIt.hasNext();) {
				PersistentObject insertedObject = insertIt.next();
				if (deleteOperation.sameIdentity(insertedObject)) {
					insertIt.remove();
					deleteIt.remove();
				}
			}
			deleteOperation.clearCache();
		}

		for (PersistentObject insertedObject : insertedObjects) {
			cacheRemove(insertedObject.getId());
		}
	}

	public static class CachedObject {
		protected PersistentObject persistentObject;
		protected Object persistentObjectState;

		public CachedObject(PersistentObject persistentObject, boolean storeState) {
			this.persistentObject = persistentObject;
			if (storeState) {
				this.persistentObjectState = persistentObject.getPersistentState();
			}
		}

		public PersistentObject getPersistentObject() {
			return persistentObject;
		}

		public Object getPersistentObjectState() {
			return persistentObjectState;
		}
	}

	public List<PersistentObject> getUpdatedObjects() {
		List<PersistentObject> updatedObjects = new ArrayList<PersistentObject>();
		for (CachedObject cachedObject : cachedObjects.values()) {
			PersistentObject persistentObject = cachedObject.getPersistentObject();
			if (!isPersistentObjectDeleted(persistentObject)) {
				Object originalState = cachedObject.getPersistentObjectState();
				if (!persistentObject.getPersistentState().equals(originalState)) {
					updatedObjects.add(persistentObject);
				} else {
					log.trace("loaded object '{}' was not updated", persistentObject);
				}
			}
		}
		return updatedObjects;
	}

	protected boolean isPersistentObjectDeleted(PersistentObject persistentObject) {
		for (DeleteOperation deleteOperation : deleteOperations) {
			if (deleteOperation.sameIdentity(persistentObject)) {
				return true;
			}
		}
		return false;
	}

	protected CachedObject cachePut(PersistentObject persistentObject, boolean storeState) {
		CachedObject cachedObject = new CachedObject(persistentObject, storeState);
		cachedObjects.put(persistentObject.getId(), cachedObject);
		return cachedObject;
	}

	/**
	 * returns the object in the cache. if this object was loaded before, then
	 * the original object is returned. if this is the first time this object is
	 * loaded, then the loadedObject is added to the cache.
	 */
	protected PersistentObject cacheFilter(PersistentObject persistentObject) {
		PersistentObject cachedPersistentObject = cacheGet(persistentObject.getId());
		if (cachedPersistentObject != null) {
			return cachedPersistentObject;
		}
		cachePut(persistentObject, true);
		return persistentObject;
	}

	@SuppressWarnings("unchecked")
	protected <T> T cacheGet(String id) {
		CachedObject cachedObject = null;
		cachedObject = cachedObjects.get(id);
		if (cachedObject != null) {
			return (T) cachedObject.getPersistentObject();
		}
		return null;
	}

	protected void cacheRemove(String persistentObjectId) {
		cachedObjects.remove(persistentObjectId);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findInCache(Class<T> entityClass) {
		List<T> entities = new ArrayList<T>(cachedObjects.size());
		for (CachedObject cachedObject : cachedObjects.values()) {
			entities.add((T) cachedObject.getPersistentObject());
		}
		return entities;
	}

	public interface DeleteOperation {

		boolean sameIdentity(PersistentObject other);

		void clearCache();

		void execute();

	}

	/**
	 * Use this {@link DeleteOperation} to execute a dedicated delete statement.
	 * It is important to note there won't be any optimistic locking checks done
	 * for these kind of delete operations!
	 * 
	 * For example, a usage of this operation would be to delete all variables
	 * for a certain execution, when that certain execution is removed. The
	 * optimistic locking happens on the execution, but the variables can be
	 * removed by a simple 'delete from var_table where execution_id is xxx'. It
	 * could very well be there are no variables, which would also work with
	 * this query, but not with the regular {@link CheckedDeleteOperation}.
	 */
	public class BulkDeleteOperation implements DeleteOperation {
		private String statement;
		private Object parameter;

		public BulkDeleteOperation(String statement, Object parameter) {
			this.statement = statement;
			this.parameter = parameter;
		}

		@Override
		public boolean sameIdentity(PersistentObject other) {
			// this implementation is unable to determine what the identity of
			// the removed object(s) will be.
			return false;
		}

		@Override
		public void clearCache() {
			// this implementation cannot clear the object(s) to be removed from
			// the cache.
		}

		@Override
		public void execute() {
			getSqlSession().delete(statement, parameter);
		}

		@Override
		public String toString() {
			return "bulk delete: " + statement + "(" + parameter + ")";
		}
	}

	/**
	 * A {@link DeleteOperation} that checks for concurrent modifications if the
	 * persistent object implements {@link HasRevision}. That is, it employs
	 * optimisting concurrency control. Used when the persistent object has been
	 * fetched already.
	 */
	public class CheckedDeleteOperation implements DeleteOperation {
		protected final PersistentObject persistentObject;

		public CheckedDeleteOperation(PersistentObject persistentObject) {
			this.persistentObject = persistentObject;
		}

		@Override
		public boolean sameIdentity(PersistentObject other) {
			return persistentObject.getClass().equals(other.getClass()) && persistentObject.getId().equals(other.getId());
		}

		@Override
		public void clearCache() {
			cacheRemove(persistentObject.getId());
		}

		public void execute() {
			String deleteStatement = StatementMap.getDeleteStatement(persistentObject.getClass());
			if (deleteStatement == null) {
				throw new FoxBPMException("no delete statement for " + persistentObject.getClass() + " in the ibatis mapping files");
			}
			getSqlSession().delete(deleteStatement, persistentObject);
		}

		public PersistentObject getPersistentObject() {
			return persistentObject;
		}

		@Override
		public String toString() {
			return "delete " + persistentObject;
		}
	}

	protected List<?> filterLoadedObjects(List<Object> loadedObjects) {
		if (loadedObjects.isEmpty()) {
			return loadedObjects;
		}
		if (!(loadedObjects.get(0) instanceof PersistentObject)) {
			return loadedObjects;
		}

		List<PersistentObject> filteredObjects = new ArrayList<PersistentObject>(loadedObjects.size());
		for (Object loadedObject : loadedObjects) {
			PersistentObject cachedPersistentObject = cacheFilter((PersistentObject) loadedObject);
			filteredObjects.add(cachedPersistentObject);
		}
		return filteredObjects;
	}

}
