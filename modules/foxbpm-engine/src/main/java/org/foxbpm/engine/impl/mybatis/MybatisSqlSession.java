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
 * @author ych
 */
package org.foxbpm.engine.impl.mybatis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.foxbpm.engine.db.PersistentObject;
import org.foxbpm.engine.exception.FoxBPMDbException;
import org.foxbpm.engine.impl.db.ListQueryParameterObject;
import org.foxbpm.engine.impl.interceptor.Session;
import org.foxbpm.engine.sqlsession.ISqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MybatisSqlSession implements ISqlSession,Session {

	SqlSession sqlSession ;
	public static Logger log = LoggerFactory.getLogger(MybatisSqlSession.class);
	 protected Map<Class<?>, Map<String, CachedObject>> cachedObjects = new HashMap<Class<?>, Map<String,CachedObject>>();
	protected List<PersistentObject> insertedObjects = new ArrayList<PersistentObject>();
	public MybatisSqlSession(SqlSession sqlSession){
		this.sqlSession = sqlSession;
	}
	
	public void insert(PersistentObject persistentObject) {
		insertedObjects.add(persistentObject);
		cachePut(persistentObject, false);
	}

//	public void update(PersistentObject persistentObject) {
//	    cachePut(persistentObject,true);
//	}

	@SuppressWarnings("unchecked")
	public <T extends PersistentObject> T selectById(Class<T> entityClass,String id) {
		T persistentObject = cacheGet(entityClass, id);
	    if (persistentObject!=null) {
	    	return persistentObject;
	    }
	    String selectStatement = MyBatisSqlSessionFactory.getSelectStatement(entityClass);
	    persistentObject = (T) sqlSession.selectOne(selectStatement, id);
	    if (persistentObject==null) {
	    	return null;
	    }
	    cachePut(persistentObject, true);
	    return persistentObject;
	}
	
//	public void delete(String deleteStatement, Object parameter) {
//		sqlSession.delete(deleteStatement, parameter);
//	}
//
//	public void delete(String deleteStatement, PersistentObject persistentObject) {
//		sqlSession.delete(deleteStatement, persistentObject);
//
//	}

	public List<?> selectList(String statement) {
		return sqlSession.selectList(statement);
	}
	
	@SuppressWarnings("rawtypes")
	public List selectList(String statement, Object parameter, int firstResult, int maxResults) {   
	    return selectList(statement, new ListQueryParameterObject(parameter, firstResult, maxResults));
	}
	
	@SuppressWarnings({ "rawtypes"})
	public List selectList(String statement,ListQueryParameterObject parameter) {
		return selectListWithRawParameter(statement,parameter);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<?> selectListWithRawParameter(String statement, Object parameter) {
		List loadedObject = sqlSession.selectList(statement, parameter);
		return filterLoadedObjects(loadedObject);
	}
	
	@SuppressWarnings("rawtypes")
	protected List filterLoadedObjects(List<Object> loadedObjects) {
		if (loadedObjects.isEmpty()) {
			return loadedObjects;
		}
		if (!(loadedObjects.get(0) instanceof PersistentObject)) {
			return loadedObjects;
		}

		List<PersistentObject> filteredObjects = new ArrayList<PersistentObject>(
				loadedObjects.size());
		for (Object loadedObject : loadedObjects) {
			PersistentObject cachedPersistentObject = cacheFilter((PersistentObject) loadedObject);
			filteredObjects.add(cachedPersistentObject);
		}
		return filteredObjects;
	}

	public Object selectOne(String statement, Object parameter) {
		Object result = sqlSession.selectOne(statement, parameter);
		if (result instanceof PersistentObject) {
			PersistentObject loadedObject = (PersistentObject) result;
			result = cacheFilter(loadedObject);
		}
		return result;
	}
	
	public void flushUpdates(List<PersistentObject> updateObjects){
		for (PersistentObject updateObject : updateObjects) {
			String updateStatement = MyBatisSqlSessionFactory.getUpdateStatement(updateObject);
			if (updateStatement == null) {
				throw new FoxBPMDbException("no insert statement for "+ updateObject.getClass()
						+ " in the ibatis mapping files");
			}
			log.debug("updating: {}", updateObject);
			sqlSession.update(updateStatement, updateObject);
		}
		updateObjects.clear();
	}
	
	public void flushInserts(){
		for (PersistentObject insertedObject : insertedObjects) {
			String insertStatement = MyBatisSqlSessionFactory.getInsertStatement(insertedObject);
			if (insertStatement == null) {
				throw new FoxBPMDbException("no insert statement for "+ insertedObject.getClass()
						+ " in the ibatis mapping files");
			}
			log.debug("inserting: {}", insertedObject);
			sqlSession.insert(insertStatement, insertedObject);
		}
		insertedObjects.clear();
	}
	
	public void removeUnnecessaryOperations(){
		for(PersistentObject insertedObject : insertedObjects) {
			cacheRemove(insertedObject.getClass(), insertedObject.getId());
		}
	}
	
	public void flush() {
		removeUnnecessaryOperations();
//		flushDeserializedObjects();
		List<PersistentObject> updatedObjects = getUpdatedObjects();

		if (log.isDebugEnabled()) {
			log.debug("flush summary: {} insert, {} update",insertedObjects.size(), updatedObjects.size());
			for (PersistentObject insertedObject : insertedObjects) {
				log.debug("  insert {}", insertedObject);
			}
			for (PersistentObject updatedObject : updatedObjects) {
				log.debug("  update {}", updatedObject);
			}
			log.debug("now executing flush...");
		}

		flushInserts();
		flushUpdates(updatedObjects);
	}
	
	public void closeSession() {
		if(sqlSession != null){
			sqlSession.close();
		}
	}
	
	public List<PersistentObject> getUpdatedObjects(){
		List<PersistentObject> updatedObjects = new ArrayList<PersistentObject>();
		for (Class<?> clazz : cachedObjects.keySet()) {
			Map<String, CachedObject> classCache = cachedObjects.get(clazz);
			for (CachedObject cachedObject : classCache.values()) {
				PersistentObject persistentObject = cachedObject.getPersistentObject();
				Object originalState = cachedObject.getPersistentObjectState();
				if (!persistentObject.getPersistentState().equals(originalState)) {
					updatedObjects.add(persistentObject);
				} else {
					log.trace("loaded object '{}' was not updated",persistentObject);
				}
			}
		}
		return updatedObjects;
	}
	
	
	protected CachedObject cachePut(PersistentObject persistentObject,boolean storeState) {
		Map<String, CachedObject> classCache = cachedObjects.get(persistentObject.getClass());
		if (classCache == null) {
			classCache = new HashMap<String, CachedObject>();
			cachedObjects.put(persistentObject.getClass(), classCache);
		}
		CachedObject cachedObject = new CachedObject(persistentObject,storeState);
		classCache.put(persistentObject.getId(), cachedObject);
		return cachedObject;
	}

	/**
	 * returns the object in the cache. if this object was loaded before, then
	 * the original object is returned. if this is the first time this object is
	 * loaded, then the loadedObject is added to the cache.
	 */
	protected PersistentObject cacheFilter(PersistentObject persistentObject) {
		PersistentObject cachedPersistentObject = cacheGet(persistentObject.getClass(), persistentObject.getId());
		if (cachedPersistentObject != null) {
			return cachedPersistentObject;
		}
		cachePut(persistentObject, true);
		return persistentObject;
	}

	@SuppressWarnings("unchecked")
	protected <T> T cacheGet(Class<T> entityClass, String id) {
		CachedObject cachedObject = null;
		Map<String, CachedObject> classCache = cachedObjects.get(entityClass);
		if (classCache != null) {
			cachedObject = classCache.get(id);
		}
		if (cachedObject != null) {
			return (T) cachedObject.getPersistentObject();
		}
		return null;
	}

	protected void cacheRemove(Class<?> persistentObjectClass,String persistentObjectId) {
		Map<String, CachedObject> classCache = cachedObjects.get(persistentObjectClass);
		if (classCache == null) {
			return;
		}
		classCache.remove(persistentObjectId);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findInCache(Class<T> entityClass) {
		Map<String, CachedObject> classCache = cachedObjects.get(entityClass);
		if (classCache != null) {
			List<T> entities = new ArrayList<T>(classCache.size());
			for (CachedObject cachedObject : classCache.values()) {
				entities.add((T) cachedObject.getPersistentObject());
			}
			return entities;
		}
		return Collections.emptyList();
	}

	public <T> T findInCache(Class<T> entityClass, String id) {
		return cacheGet(entityClass, id);
	}

	public static class CachedObject {
		protected PersistentObject persistentObject;
		protected Object persistentObjectState;

		public CachedObject(PersistentObject persistentObject,boolean storeState) {
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

	public void close() {
		if(this.sqlSession != null){
			this.sqlSession.close();
		}
	}
	
	public void commit(){
		if(this.sqlSession != null){
			this.sqlSession.commit();
		}
	}
	
	public void rollback(){
		if(this.sqlSession != null){
			this.sqlSession.rollback();
		}
	}
}
