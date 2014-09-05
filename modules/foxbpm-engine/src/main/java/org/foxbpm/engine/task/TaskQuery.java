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

import org.foxbpm.engine.query.Query;

/**
 * 任务查询器
 * 
 * @author kenshin
 */
public interface TaskQuery extends Query<TaskQuery, Task> {

	/**
	 * 任务是否结束
	 * 
	 * @return
	 */
	TaskQuery taskIsEnd();

	/**
	 * 未结束的任务
	 * 
	 * @return
	 */
	TaskQuery taskNotEnd();

	/**
	 * 是否代理
	 * 
	 * @param isAgent
	 * @return
	 */
	TaskQuery isAgent(boolean isAgent);

	/**
	 * 代理id
	 * 
	 * @param agentId
	 * @return
	 */
	TaskQuery agentId(String agentId);

	/**
	 * 是否暂停
	 * 
	 * @param isSuspended
	 * @return
	 */
	TaskQuery isSuspended(boolean isSuspended);

	/**
	 * 根据令牌ID查询
	 * 
	 * @param tokenId
	 * @return
	 */
	TaskQuery tokenId(String tokenId);

	/**
	 * 根据任务发起人查询
	 * 
	 * @return
	 */
	TaskQuery initiator(String initiator);

	/**
	 * 根据taskId查询
	 * 
	 * @param taskId
	 * @return
	 */
	TaskQuery taskId(String taskId);

	/**
	 * 根据任务名称查询
	 * 
	 * @param name
	 * @return
	 */
	TaskQuery taskName(String name);

	/**
	 * 任务名称like匹配
	 * 
	 * @param nameLike
	 * @return
	 */
	TaskQuery taskNameLike(String nameLike);

	/**
	 * 根据业务主键查询
	 * 
	 * @param businessKey
	 * @return
	 */
	TaskQuery businessKey(String businessKey);

	/**
	 * 根据业务主键查询
	 * 
	 * @param businessKey
	 * @return
	 */
	TaskQuery businessKeyLike(String businessKey);

	/**
	 * 任务类型
	 * 
	 * @param taskInstanceType
	 * @return
	 */
	TaskQuery addTaskType(String taskType);

	/**
	 * 任务描述
	 * 
	 * @param description
	 * @return
	 */
	TaskQuery taskDescription(String description);

	/**
	 * 任务描述like匹配
	 * 
	 * @param descriptionLike
	 * @return
	 */
	TaskQuery taskDescriptionLike(String descriptionLike);

	/**
	 * 任务主题
	 * 
	 * @param description
	 * @return
	 */
	TaskQuery taskSubject(String subject);

	/**
	 * 任务主题like匹配
	 * 
	 * @param descriptionLike
	 * @return
	 */
	TaskQuery taskSubjectLike(String subjectLike);

	/**
	 * 指定用户的独占任务
	 * 
	 * @param assignee
	 * @return
	 */
	TaskQuery taskAssignee(String assignee);

	/**
	 * 指定用户的任务
	 * 
	 * @param owner
	 * @return
	 */
	TaskQuery taskOwner(String owner);

	/**
	 * 未被领取的任务
	 * 
	 * @return
	 */
	TaskQuery taskUnnassigned();

	/**
	 * 指定用户的的共享任务
	 * 
	 * @param candidateUser
	 * @return
	 */
	TaskQuery taskCandidateUser(String candidateUser);

	/**
	 * 根据流程实例编号查询
	 * 
	 * @param processInstanceId
	 * @return
	 */
	TaskQuery processInstanceId(String processInstanceId);

	/**
	 * 创建时间等于createTime
	 * 
	 * @param createTime
	 * @return
	 */
	TaskQuery taskCreatedOn(Date createTime);

	/**
	 * 创建时间小于before
	 * 
	 * @param before
	 * @return
	 */
	TaskQuery taskCreatedBefore(Date before);

	/**
	 * 创建时间大于after
	 * 
	 * @param after
	 * @return
	 */
	TaskQuery taskCreatedAfter(Date after);
	
	/**
	 * 期望完成时间等于dueDate
	 * 
	 * @param dueDate
	 * @return
	 */
	TaskQuery taskDueDateOn(Date endTime);

	/**
	 * 期望完成时间小于before
	 * 
	 * @param before
	 * @return
	 */
	TaskQuery taskDueDateBefore(Date before);

	/**
	 * 期望完成时间大于after
	 * @param after
	 * @return
	 */
	TaskQuery taskDueDateAfter(Date after);
	
	
	/**
	 * 期望完成时间等于dueDate
	 * 
	 * @param dueDate
	 * @return
	 */
	TaskQuery taskEndTimeOn(Date dueDate);

	/**
	 * 期望完成时间小于before
	 * 
	 * @param before
	 * @return
	 */
	TaskQuery taskEndTimeBefore(Date before);

	/**
	 * 期望完成时间大于after
	 * @param after
	 * @return
	 */
	TaskQuery taskEndTimeAfter(Date after);
	

	/**
	 * 根据流程定义key查询
	 * 
	 * @param processDefinitionKey
	 * @return
	 */
	TaskQuery processDefinitionKey(String processDefinitionKey);

	/**
	 * 根据流程定义编号查询
	 * 
	 * @param processDefinitionId
	 * @return
	 */
	TaskQuery processDefinitionId(String processDefinitionId);

	/**
	 * 根据流程名称查询
	 * 
	 * @param processDefinitionName
	 * @return
	 */
	TaskQuery processDefinitionName(String processDefinitionName);

	/**
	 * 根据流程名称like查询
	 * 
	 * @param processDefinitionLike
	 * @return
	 */
	TaskQuery processDefinitionNameLike(String processDefinitionLike);

	/**
	 * 根据节点查询
	 * 
	 * @param nodeId
	 * @return
	 */
	TaskQuery nodeId(String nodeId);

	// ordering ////////////////////////////////////////////////////////////

	/**
	 * 根据任务ID排序
	 * 
	 * @return
	 */
	TaskQuery orderByTaskId();

	/**
	 * 根据任务名称排序
	 * 
	 * @return
	 */
	TaskQuery orderByTaskName();

	/**
	 * 根据任务描述排序
	 * 
	 * @return
	 */
	TaskQuery orderByTaskDescription();

	/**
	 * 根据处理人排序
	 * 
	 * @return
	 */
	TaskQuery orderByTaskAssignee();

	/**
	 * 根据创建时间排序
	 * 
	 * @return
	 */
	TaskQuery orderByTaskCreateTime();

	/**
	 * 根据流程实例ID排序
	 * 
	 * @return
	 */
	TaskQuery orderByProcessInstanceId();

	/**
	 * 根据结束时间排序
	 * 
	 * @return
	 */
	TaskQuery orderByEndTime();

}
