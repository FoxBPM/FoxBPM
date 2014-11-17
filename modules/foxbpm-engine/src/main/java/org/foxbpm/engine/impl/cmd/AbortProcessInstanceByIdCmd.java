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

import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.util.ExceptionUtil;
import org.foxbpm.engine.impl.util.StringUtil;

/**
 * 删除流程实例
 * @author ych
 *
 */
public class AbortProcessInstanceByIdCmd implements Command<Void> {

	private String processInstanceId;
	public AbortProcessInstanceByIdCmd(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	 
	public Void execute(CommandContext commandContext) {
		if(StringUtil.isEmpty(processInstanceId)){
			throw ExceptionUtil.getException("10601010");
		}
		ProcessInstanceEntity processInstanceEntity = commandContext.getProcessInstanceManager().findProcessInstanceById(processInstanceId);
		if(processInstanceEntity == null){
			throw ExceptionUtil.getException("10602301",processInstanceId);
		}
		processInstanceEntity.abort();
		return null;
	}
}
