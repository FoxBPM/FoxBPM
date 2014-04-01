/**
 * Copyright 1996-2014 FoxBPM Co.,Ltd.
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
 */
package org.foxbpm.engine.impl.cmd;


import org.foxbpm.engine.impl.bpmn.behavior.ProcessDefinitionBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.UserTaskBehavior;
import org.foxbpm.engine.impl.expression.ExpressionMgmt;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.persistence.ProcessDefinitionManager;

public class GetTaskFormByNodeId<T> implements Command<String> {
	
	protected String processDefinitionId;
	protected String nodeId;

	public GetTaskFormByNodeId(String processDefinitionId, String nodeId) {
		this.processDefinitionId = processDefinitionId;
		this.nodeId = nodeId;
	}

	public String execute(CommandContext commandContext) {

		ProcessDefinitionManager processDefinitionManager = commandContext.getProcessDefinitionManager();

		ProcessDefinitionBehavior processDefinition = processDefinitionManager
				.findLatestProcessDefinitionById(processDefinitionId);
		UserTaskBehavior userTask = (UserTaskBehavior) processDefinition.getDefinitions().getElement(nodeId);
		Object returnFormUri = null;
		if(userTask.getFormUri()!=null && !userTask.getFormUri().equals(""))
			returnFormUri=ExpressionMgmt.execute(userTask.getFormUri(),processDefinition);
		if(returnFormUri!=null && !returnFormUri.equals("")){
			return returnFormUri.toString();
		}
		else{
			String defaultFormUri=processDefinition.getDefaultFormUri();
			if(defaultFormUri != null && !defaultFormUri.equals("")){
					return defaultFormUri.toString();
			}
			
		}
		
		return null;
	}
}
