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
 */
package org.foxbpm.engine.task;

import java.util.Date;
import java.util.Map;

/**
 * @author kenshin
 *
 */
public interface Task{
	
	int PRIORITY_VERYLOW = 20;
	int PRIORITY_LOW = 40;
	int PRIORITY_NORMAL = 60;
	int PRIORITY_HIGH = 80;
	int PRIORITY_VERYHIGH = 100;
	/**
	 * 获取任务编号
	 */
	String getId();
	
	/**
	 * 获取任务名称
	 * @return
	 */
	String getName();
	
	/**
	 * 获得任务对应的流程实例编号
	 * @return
	 */
	String getProcessInstanceId();

	/**
	 * 获取流程定义唯一编号
	 * @return
	 */
	String getProcessDefinitionId();
	
	/**
	 * 获取流程定义KEY
	 * @return
	 */
	String getProcessDefinitionKey();
	
	/**
	 * 获取流程定义名称
	 * @return
	 */
	String getProcessDefinitionName();
	
	/**
	 * 获取任务所在节点ID
	 * @return
	 */
	String getNodeId();
	
	/**
	 * 获取流程所在节点名称
	 * @return
	 */
	String getNodeName();
	
	/**
	 * 获取任务处理人
	 * 当共享任务未领取时，此字段为空
	 * @return
	 */
	String getAssignee();
	
	/**
	 * 获取任务领取时间
	 * @return
	 */
	Date getClaimTime();
	
	/**
	 * 获取任务结束时间
	 * @return
	 */
	Date getEndTime();
	
	/**
	 * 获取任务创建时间
	 * @return
	 */
	Date getCreateTime();
	
	/**
	 * 获取任务关联键
	 * @return
	 */
	String getBizKey();
	
	/**
	 * 获取任务处理意见
	 * @return
	 */
	String getTaskComment();
	
	/**
	 * 获取操作表单
	 * @return
	 */
	String getFormUri();
	
	/**
	 * 获取浏览表单
	 * @return
	 */
	String getFormUriView();
	
	/**
	 * 获取任务类型
	 * @return
	 */
	String getTaskType();
	
	/**
	 * 获取任务主题
	 * @return
	 */
	String getSubject();
	
	/**
	 * 获取任务描述
	 * @return
	 */
	String getDescription();
	
	/**
	 * 获取完成后任务描述
	 * @return
	 */
	String getCompleteDescription();
	
	/**
	 * 获取任务处理命令编号
	 * 当任务未结束时此属性为空
	 * @return
	 */
	String getCommandId();
	
	/**
	 * 获取实体属性map
	 * @return
	 */
	Map<String, Object> getPersistentState();

	/**
	 * 是否暂停
	 * @return
	 */
	boolean isSuspended(); 

	/**
	 * 是否结束
	 * @return
	 */
	boolean hasEnded();

	/** 是否自动领取 */
	boolean isAutoClaim();
	
	/**
	 * 获取任务代理状态
	 * @return
	 */
	DelegationState getDelegationState();
	
	/**
	 * 任务编号
	 * @param id
	 */
	void setId(String id);
	
	/**
	 * 流程实例号
	 * @param processInstanceId 流程实例号
	 */
	void setProcessInstanceId(String processInstanceId);
	
	/**
	 * 流程定义编号
	 * @param processDefinitionId
	 */
	void setProcessDefinitionId(String processDefinitionId);
	
	/**
	 * 流程定义key
	 * @param processDefinitionKey
	 */
	void setProcessDefinitionKey(String processDefinitionKey);
	
	/**
	 * 任务名称
	 * @param name
	 */
	void setName(String name);
	
	/**
	 * 任务描述
	 * @param description
	 */
	void setDescription(String description);
	
	/**
	 * 完成后任务描述
	 * @param description
	 */
	void setCompleteDescription(String description);
	
	/**
	 * 处理人
	 * @param assignee
	 */
	void setAssignee(String assignee);
	
	/**
	 * 结束时间
	 * @param endTime
	 */
	void setEndTime(Date endTime);
	
	/**
	 * 任务主题
	 * @param subject
	 */
	void setSubject(String subject);
	
	/**
	 * 操作表单
	 * @param formUri
	 */
	void setFormUri(String formUri);
	
	/**
	 * 浏览表单
	 * @param formUri
	 */
	void setFormUriView(String formUri);
	
	/**
	 * 业务关连建
	 * @param bizKey
	 */
	void setBizKey(String bizKey);
	
	/**
	 * 令牌编号
	 * @param tokenId
	 */
	void setTokenId(String tokenId);
	
	/**
	 * 节点id
	 * @param nodeId
	 */
	void setNodeId(String nodeId);
	
	/**
	 * 节点名称
	 * @param nodeName
	 */
	void setNodeName(String nodeName);
	
	/**
	 * 设置任务类型
	 * @param taskType
	 */
	void setTaskType(String taskType);
}
