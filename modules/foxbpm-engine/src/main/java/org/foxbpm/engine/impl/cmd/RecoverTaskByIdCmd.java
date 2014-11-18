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

import java.util.List;

import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.persistence.TaskManager;
import org.foxbpm.engine.impl.task.TaskCommandSystemType;
import org.foxbpm.engine.impl.util.ExceptionUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
/**
 * 追回任务
 * 此命令要求：
 * 1.当前登陆人必须在目标节点（targetNodeId）处理过任务，处理多次则默认取最近的一条
 * 2.主令牌经过的节点才能追回，也就是说主线上的节点
 * 3.分支时，只有同一条分支上的节点之间才能追回操作
 * 不满足上述条件时，则会抛出相应异常信息
 * 如果从分支内追回到主线时，其他分支将被强制结束。
 * @param taskId 要追回的任务编号
 * @param targetNodeId 要追回到的目标节点编号
 * 
 */
public class RecoverTaskByIdCmd implements Command<List<Void>>{

	private String taskId;
	private String targetNodeId;
	
	public RecoverTaskByIdCmd(String taskId,String targetNodeId) {
		this.taskId = taskId;
		this.targetNodeId = targetNodeId;
	}
	
	public List<Void> execute(CommandContext commandContext) {
		String userId = Authentication.getAuthenticatedUserId();
		if(StringUtil.isEmpty(userId)){
			//未设置当前登陆人
			throw ExceptionUtil.getException("10601001");
		}
		if(StringUtil.isEmpty(taskId)){
			//追回的任务编号为空
			throw ExceptionUtil.getException("10601201");
		}
		
		if(StringUtil.isEmpty(targetNodeId)){
			//追回的目标节点编号为空
			throw ExceptionUtil.getException("10601011");
		}
		
		TaskManager taskManager = commandContext.getTaskManager();
		TaskEntity taskEntity = taskManager.findTaskById(taskId);
		if(taskEntity == null){
			//追回的任务{0}不存在
			throw ExceptionUtil.getException("10602201",taskId);
		}
		TaskEntity targetTaskEntity = taskManager.findLastEndTaskByProcessInstanceIdAndNodeId(taskEntity.getProcessInstanceId(), targetNodeId);
		if(targetTaskEntity == null){
			//流程未经过{0}节点，无法追回
			throw ExceptionUtil.getException("10603002",targetNodeId);
		}
		
		if(!targetTaskEntity.getAssignee().equals(Authentication.getAuthenticatedUserId())){
			//任务最后处理人是{}，当前登陆人为{}，无权追回。
			throw ExceptionUtil.getException("10603003",Authentication.getAuthenticatedUserId(),targetTaskEntity.getAssignee());
		}
		
		TokenEntity targetTokenEntity = targetTaskEntity.getToken();
		if(!targetTokenEntity.isRoot() && !targetTaskEntity.getTokenId().equals(taskEntity.getTokenId())){
			//源节点{}，目标节点{}不在统一分支，并且目标节点非主令牌处理
			throw ExceptionUtil.getException("10603004",taskEntity.getNodeId(),targetNodeId);
		}
		
		ProcessDefinitionEntity processDefinitionEntity = taskEntity.getProcessDefinition();
		KernelFlowNodeImpl findFlowNode = processDefinitionEntity.findFlowNode(targetNodeId);
		
		taskEntity.setCommandId(TaskCommandSystemType.RECOVER);
		taskEntity.setCommandType(TaskCommandSystemType.RECOVER);
		taskEntity.setCommandMessage("已追回");
		taskEntity.setAssignee(userId);
		taskEntity.complete(findFlowNode,userId);
		
		return null;
	}
	
}
