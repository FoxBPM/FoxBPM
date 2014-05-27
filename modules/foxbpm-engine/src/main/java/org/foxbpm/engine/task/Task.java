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
	
	int PRIORITY_MINIUM = 0;
	int PRIORITY_NORMAL = 50;
	int PRIORITY_MAXIMUM = 100;
	
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
}
