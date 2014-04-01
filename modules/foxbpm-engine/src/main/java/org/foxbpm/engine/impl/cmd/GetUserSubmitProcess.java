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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.bpmn.behavior.ProcessDefinitionBehavior;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.interceptor.CommandExecutor;
import org.foxbpm.engine.impl.persistence.ProcessDefinitionManager;
import org.foxbpm.engine.impl.util.StringUtil;

public class GetUserSubmitProcess implements Command<List<Map<String, String>>>{

	protected String userId;
	protected int number;
	 
	public GetUserSubmitProcess(String userId, int number){
		this.userId=userId;
		this.number=number;
	}
	
	
	public List<Map<String, String>> execute(CommandContext commandContext) {
		
		
		List<Map<String, Object>> processDefData=commandContext.getProcessDefinitionManager().findUserSubmitProcess(this.userId,this.number);
		//CommandExecutor commandExecutor=Context.getProcessEngineConfiguration().getCommandExecutor();
		
		ProcessDefinitionManager processDefinitionManager =commandContext.getProcessDefinitionManager();
		CommandExecutor commandExecutor=Context.getProcessEngineConfiguration().getCommandExecutor();
		List<Map<String,String>> processData=new ArrayList<Map<String,String>>();
		
		for (Map<String, Object> map : processDefData) {
			String processKey=StringUtil.getString(map.get("PROCESS_KEY"));
			boolean state=commandExecutor.execute(new VerificationStartUserCmd(this.userId,processKey,null));
			if(state){
				Map<String, String> dataMap=new HashMap<String, String>();
				
		

				ProcessDefinitionBehavior processDefinition = processDefinitionManager
						.findLatestProcessDefinitionByKey(processKey);
				
				
				
				String startFormKey=null;
				if(processDefinition!=null){
					startFormKey=processDefinition.getStartFormKey();
				}
				else{
					continue;
				}
				
				
				dataMap.put("startFormKey", startFormKey);

				dataMap.put("processDefinitionId", StringUtil.getString(map.get("PROCESS_ID")));
				dataMap.put("processDefinitionName", StringUtil.getString(map.get("PROCESS_NAME")));
				dataMap.put("processDefinitionKey", processKey);
				dataMap.put("category", StringUtil.getString(map.get("CATEGORY")));
				dataMap.put("version", StringUtil.getString(map.get("VERSION")));
				dataMap.put("resourceName", StringUtil.getString(map.get("RESOURCE_NAME")));
				dataMap.put("resourceId", StringUtil.getString(map.get("RESOURCE_ID")));
				dataMap.put("deploymentId", StringUtil.getString(map.get("DEPLOYMENT_ID")));
				dataMap.put("diagramResourceName", StringUtil.getString(map.get("DIAGRAM_RESOURCE_NAME")));
				processData.add(dataMap);
			}
		}
		
		return processData;
		
	}

}
