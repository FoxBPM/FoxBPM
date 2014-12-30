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
package org.foxbpm.portal.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.TaskService;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.impl.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class WorkFlowService {

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private ProcessEngine processEngine;
	
	public Object executeTaskCommandJson(Map<String,Object> formData) {
		String taskCommandJson = StringUtil.getString(formData.get("flowCommandInfo"));
		
		
		Map<String,Object> transVariable = new HashMap<String, Object>();
		transVariable.put("formData", formData);
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode params = null;
		try {
			params = objectMapper.readTree(taskCommandJson);
		} catch (Exception e) {
			throw new FoxBPMException("任务命令参数格式不正确",e);
		}
		JsonNode taskIdNode = params.get("taskId");
		JsonNode commandIdNode = params.get("commandId");
		JsonNode processDefinitionKeyNode = params.get("processDefinitionKey");
		JsonNode businessKeyNode = params.get("bizKey");
		JsonNode taskCommentNode = params.get("taskComment");
		// 参数校验
		
		// 命令类型
		JsonNode commandTypeNode = params.get("commandType");
		JsonNode commandParamsNode = params.get("commandParams");
		
		if (commandTypeNode == null) {
			throw new FoxBPMException("commandType is null !");
		}
		// 命令Id
		if (commandIdNode == null) {
			throw new FoxBPMException("commandId is null !");
		}
		
		ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setCommandType(commandTypeNode.getTextValue());
		// 设置命令的id,需和节点上配置的按钮编号对应，会执行按钮中的脚本。
		expandTaskCommand.setTaskCommandId(commandIdNode.getTextValue());
		if(taskCommentNode != null){
			expandTaskCommand.setTaskComment(taskCommentNode.getTextValue());
		}
		expandTaskCommand.setTransientVariables(transVariable);
		//设置任务命令参数
		Map<String,Object> taskParams = new HashMap<String, Object>();
		if(commandParamsNode != null){
			Iterator<String> it = commandParamsNode.getFieldNames();
			while(it.hasNext()){
				String tmp = it.next();
				taskParams.put(tmp, commandParamsNode.get(tmp).getTextValue());
			}
		}
		expandTaskCommand.setParamMap(taskParams);
		if (taskIdNode != null && StringUtil.isNotEmpty(taskIdNode.getTextValue())&& !StringUtil.equals(taskIdNode.getTextValue(),"null")) {
			expandTaskCommand.setTaskId(taskIdNode.getTextValue());
		} else {
			String userId = Authentication.getAuthenticatedUserId();
			expandTaskCommand.setInitiator(userId);
			if(businessKeyNode == null){
				throw new RuntimeException("启动流程时关联键不能为null");
			}
			if(processDefinitionKeyNode == null){
				throw new RuntimeException("启动流程时流程Key不能为null");
			}
			expandTaskCommand.setBusinessKey(businessKeyNode.getTextValue());
			expandTaskCommand.setProcessDefinitionKey(processDefinitionKeyNode.getTextValue());
		}
		return taskService.expandTaskComplete(expandTaskCommand, Object.class);
	}
}
