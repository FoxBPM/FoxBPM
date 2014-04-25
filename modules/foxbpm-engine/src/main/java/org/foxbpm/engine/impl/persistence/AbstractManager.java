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

import java.util.Map;

import org.foxbpm.engine.db.PersistentObject;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.sqlsession.ISqlSession;
/**
 * 持久化管理器抽象类
 * @author kenshin
 */
public abstract class AbstractManager {

	protected CommandContext commandContext;

	public CommandContext getCommandContext() {
		return commandContext;
	}

	public void setCommandContext(CommandContext commandContext) {
		this.commandContext = commandContext;
	}
	
	public ISqlSession getSqlSession(){
		return commandContext.getSqlSession();
	}
	
	public void insert(PersistentObject persistentObject) {
		getSqlSession().insert(persistentObject);
	}

//	public void delete(String deleteStatement, PersistentObject persistentObject) {
//		delete(deleteStatement, persistentObject.getId());
//	}
//	
//	public void delete(String deleteStatement, String parameter) {
//		getSqlSession().delete(deleteStatement, parameter);
//	}
	
//	public void update(PersistentObject persistentObject){
//		getSqlSession().update(persistentObject);
//	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getPersistentDbMap(String statement, PersistentObject persistentObject){
		return (Map<String, Object>)getSqlSession().selectOne(statement, persistentObject);
	}
	
	public <T extends PersistentObject> T selectById(Class<T> entityClass,String id){
		return (T) getSqlSession().selectById(entityClass,id);
	}
}
