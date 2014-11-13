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
package org.foxbpm.engine.impl.cmd;

import org.foxbpm.engine.db.PersistentObject;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.util.ExceptionUtil;
import org.foxbpm.engine.sqlsession.StatementMap;

/**
 * 手工更新实体方法
 * 被更新的实体必须实现persistentObject方法
 * @author ych
 *
 */
public class UpdatePersistentObjectCmd implements Command<Void> {

	
	private PersistentObject persistentObject;
	private String updateStatement;
	
	/**
	 * 没有传updateStatement参数时，必须在statementMap中配置对应的update语句编号
	 * @param persistentObject
	 */
	public UpdatePersistentObjectCmd(PersistentObject persistentObject) {
		this.persistentObject = persistentObject;
	}
	
	/**
	 * 根据对应的updateStatementId更新对应的实体
	 * @param updateStatement
	 * @param persistentObject
	 */
	public UpdatePersistentObjectCmd(String updateStatement , PersistentObject persistentObject) {
		this.persistentObject = persistentObject;
		this.updateStatement = updateStatement;
	}
	
	 
	public Void execute(CommandContext commandContext) {
		if(updateStatement == null){
			updateStatement = StatementMap.getUpdateStatement(persistentObject);
			if(updateStatement == null){
				throw ExceptionUtil.getException("10602003",persistentObject.getClass().getName());
			}
		}
		commandContext.getSqlSession().update(updateStatement,persistentObject);
		return null;
	}

}
