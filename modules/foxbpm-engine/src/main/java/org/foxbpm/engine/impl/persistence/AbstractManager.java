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

import org.foxbpm.engine.db.PersistentObject;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.interceptor.Session;
import org.foxbpm.engine.sqlsession.ISqlSession;
/**
 * 持久化管理器抽象类
 * @author kenshin
 */
public abstract class AbstractManager implements Session{
	
	public ISqlSession getSqlSession(){
		return getSession(ISqlSession.class);
	}

	protected <T> T getSession(Class<T> sessionClass) {
		return Context.getCommandContext().getSession(sessionClass);
	}
	
	public void insert(PersistentObject persistentObject) {
		getSqlSession().insert(persistentObject);
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
	
	protected ProcessInstanceManager getProcessInstanceManager() {
		return getSession(ProcessInstanceManager.class);
	}
	
	public <T extends PersistentObject> T selectById(Class<T> entityClass,String id){
		return (T) getSqlSession().selectById(entityClass,id);
	}
	
	public void flush() {
		
	}
	public void close() {
		
	}
}
