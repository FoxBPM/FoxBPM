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
import org.foxbpm.engine.sqlsession.ISqlSessionFactory;

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
		ISqlSessionFactory sqlSessionFactory = commandContext.getProcessEngineConfigurationImpl().getSqlSessionFactory();
		return sqlSessionFactory.createSqlSession();
	}
	
	public void insert(String insertStatement, PersistentObject persistentObject) {
		getSqlSession().insert(insertStatement, persistentObject);
	}

	public void delete(String deleteStatement, PersistentObject persistentObject) {
		delete(deleteStatement, persistentObject.getId());
	}
	
	public void delete(String deleteStatement, String parameter) {
		getSqlSession().delete(deleteStatement, parameter);
	}
	
	public void update(String updateStatement, PersistentObject persistentObject){
		getSqlSession().update(updateStatement, persistentObject);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getPersistentDbMap(String statement, PersistentObject persistentObject){

		return (Map<String, Object>)getSqlSession().selectOne(statement, persistentObject);
	}
}
