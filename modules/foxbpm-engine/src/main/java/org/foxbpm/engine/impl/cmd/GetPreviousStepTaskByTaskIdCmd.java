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

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.impl.bpmn.behavior.ProcessDefinitionBehavior;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.persistence.ProcessDefinitionManager;
import org.foxbpm.engine.impl.persistence.ProcessInstanceManager;
import org.foxbpm.engine.impl.persistence.TaskManager;
import org.foxbpm.engine.impl.runtime.ProcessInstanceEntity;
import org.foxbpm.engine.impl.runtime.TokenEntity;
import org.foxbpm.engine.task.TaskInstance;


public class GetPreviousStepTaskByTaskIdCmd implements Command<List<TaskInstance>> {

	protected String taskId;
	
	public GetPreviousStepTaskByTaskIdCmd(String taskId){
		this.taskId=taskId;
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<TaskInstance> execute(CommandContext commandContext) {
		TaskManager taskManager = commandContext.getTaskManager();

		TaskInstance taskInstanceQuery = taskManager.findTaskById(taskId);
		
		
		String tokenId = taskInstanceQuery.getTokenId();
		String processDefinitionId = taskInstanceQuery.getProcessDefinitionId();
		ProcessInstanceManager processInstanceManager = commandContext.getProcessInstanceManager();

		String processInstanceId = taskInstanceQuery.getProcessInstanceId();

		ProcessDefinitionManager processDefinitionManager = commandContext.getProcessDefinitionManager();

		ProcessDefinitionBehavior processDefinition = processDefinitionManager.findLatestProcessDefinitionById(processDefinitionId);

		ProcessInstanceEntity processInstanceImpl = processInstanceManager.findProcessInstanceById(processInstanceId, processDefinition);

		TokenEntity token=processInstanceImpl.getTokenMap().get(tokenId);
		
		
		
		List<String> tokenIdList=new ArrayList<String>();
		

		addTokenParent(token, tokenIdList);
		
		List<TaskInstance> taskInstanceQueryToList=(List)taskManager.findTasksByTokenIdList(tokenIdList);
		
		List<TaskInstance> taskInstanceQueryToTemp=new ArrayList<TaskInstance>();
		
		for (TaskInstance taskInstanceQueryTo : taskInstanceQueryToList) {
			if(!taskInstanceQueryTo.getId().equals(this.taskId)){
				if(taskInstanceQueryTo.getTaskGroup()!=null){
					
					taskInstanceQueryToTemp.add(taskInstanceQueryTo);
				}
				else {
					if(taskInstanceQueryToTemp.size()==0){
						taskInstanceQueryToTemp.add(taskInstanceQueryTo);
						return taskInstanceQueryToTemp;
					}
					else{
						return taskInstanceQueryToTemp;
					}
					
				}
			}
		}
		
		return taskInstanceQueryToList;
	}
	
	private void addTokenParent(TokenEntity token,List<String> tokenList)
	{
		
		tokenList.add(token.getId());
		if(token.getParent()!=null)
		{
			addTokenParent(token.getParent(),tokenList);
		}
	}

}
