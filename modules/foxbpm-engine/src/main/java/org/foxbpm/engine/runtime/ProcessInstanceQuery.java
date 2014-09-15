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
 * @author ych
 * @author kenshin
 */
package org.foxbpm.engine.runtime;

import java.util.Date;

import org.foxbpm.engine.query.Query;

/**
 * 流程实例查询
 * @author kenshin
 *
 */
public interface ProcessInstanceQuery extends Query<ProcessInstanceQuery, ProcessInstance>{

	/**
	 * 根据流程实例号查询
	 * @param processInstanceId
	 * @return
	 */
	ProcessInstanceQuery processInstanceId(String processInstanceId);

	/**
	 * 根据业务关联键查询
	 * @param processInstanceBusinessKey
	 * @return
	 */
	ProcessInstanceQuery processInstanceBusinessKey(String processInstanceBusinessKey);
	
	/**
	 * 根据业务关联键查询
	 * @param processInstanceBusinessKey
	 * @return
	 */
	ProcessInstanceQuery processInstanceBusinessKeyLike(String processInstanceBusinessKey);

	/**
	 * 根据流程定义key查询
	 * @param processDefinitionKey
	 * @return
	 */
	ProcessInstanceQuery processDefinitionKey(String processDefinitionKey);

	/**
	 * 根据流程定义编号查询
	 * @param processDefinitionId
	 * @return
	 */
	ProcessInstanceQuery processDefinitionId(String processDefinitionId);
	
	/**
	 * 流程发起人
	 * @param initiator
	 * @return
	 */
	ProcessInstanceQuery initiator(String initiator);
	
	/**
	 * 流程发起人like查询
	 * @param initiatorLike
	 * @return
	 */
	ProcessInstanceQuery initiatorLike(String initiatorLike);
	
	/**
	 * 已经结束的流程实例
	 * @return
	 */
	ProcessInstanceQuery isEnd();
	
	/**
	 * 未结束的流程实例
	 * @return
	 */
	ProcessInstanceQuery notEnd();
	
	/**
	 * 根据任务主题查询
	 * @param subject
	 * @return
	 */
	ProcessInstanceQuery subject(String subject);
	
	/**
	 * 根据任务主题like查询
	 * @param subjectLike
	 * @return
	 */
	ProcessInstanceQuery subjectLike(String subjectLike);
	
	/**
	 * 流程开始时间等于
	 * @param startTime
	 * @return
	 */
	ProcessInstanceQuery startTimeOn(Date startTime);

	/**
	 * 流程开始时间大于
	 * @param startTimeBefore
	 * @return
	 */
	ProcessInstanceQuery startTimeBefore(Date startTimeBefore);

	/**
	 * 流程开始时间小于
	 * @param startTimeAfter
	 * @return
	 */
	ProcessInstanceQuery startTimeAfter(Date startTimeAfter);
	
	/**
	 * 流程结束时间等于
	 * @param startTime
	 * @return
	 */
	ProcessInstanceQuery endTimeOn(Date startTime);

	/**
	 * 流程结束时间大于
	 * @param startTimeBefore
	 * @return
	 */
	ProcessInstanceQuery endTimeBefore(Date startTimeBefore);

	/**
	 * 流程结束时间小于
	 * @param startTimeAfter
	 * @return
	 */
	ProcessInstanceQuery endTimeAfter(Date startTimeAfter);

	/**
	 * 根据参与人查询，流程追踪时
	 * @param taskParticipants
	 * @return
	 */
	ProcessInstanceQuery taskParticipants(String taskParticipants);

	/**
	 * 根据流程状态查询
	 * @return
	 */
	ProcessInstanceQuery processInstanceStatus(String running);
	
	/**
	 * 根据流程定义名称查询
	 * @param definitionName
	 * @return
	 */
	ProcessInstanceQuery processDefinitionName(String definitionName);
	
	/**
	 * 根据流程定义名称like查询
	 * @param definitionNameLike
	 * @return
	 */
	ProcessInstanceQuery processDefinitionNameLike(String definitionNameLike);
	
	/**
	 * 根据流程实例编号排序
	 * @return
	 */
	ProcessInstanceQuery orderByProcessInstanceId();
	
	/**
	 * 根据流程实例开始时间排序
	 * @return
	 */
	ProcessInstanceQuery orderByStartTime();

	/**
	 * 根据流程定义key排序
	 * @return
	 */
	ProcessInstanceQuery orderByProcessDefinitionKey();

	/**
	 * 根据流程定义编号排序
	 * @return
	 */
	ProcessInstanceQuery orderByProcessDefinitionId();
	
	/**
	 * 根据上次更新时间排序
	 * @return
	 */
	ProcessInstanceQuery orderByUpdateTime();
	
}
