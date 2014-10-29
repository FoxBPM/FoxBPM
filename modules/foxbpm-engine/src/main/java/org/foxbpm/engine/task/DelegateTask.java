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

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.foxbpm.engine.execution.ConnectorExecutionContext;
import org.foxbpm.engine.impl.entity.GroupEntity;
import org.foxbpm.engine.impl.entity.UserEntity;
import org.foxbpm.kernel.runtime.KernelVariableScope;

/**
 * @author kenshin
 * 
 */
public interface DelegateTask extends KernelVariableScope {

	/** 获取任务的编号 */
	String getId();

	/** 任务的名称或者标题 */
	String getName();
	
	/** 任务的名称或者标题 */
	String getNodeId();


	/** 设置任务名称 */
	void setName(String name);

	/** 获取任务的描述 */
	String getDescription();

	/** 设置任务的描述 */
	void setDescription(String description);

	/** 获取优先级 [0..19] 最低, [20..39] 低, [40..59] 中, [60..79] 高 [80..100] 最高 */
	int getPriority();

	/** 设置优先级 [0..19] 最低, [20..39] 低, [40..59] 中, [60..79] 高 [80..100] 最高 */
	void setPriority(int priority);

	/** 获取流程实例编号 */
	String getProcessInstanceId();

	/** 获取令牌编号 */
	String getTokenId();

	/** 获取流程定义编号*/
	String getProcessDefinitionId();

	/** 获取任务创建时间 */
	Date getCreateTime();

	/** 获取任务定义编号 */
	String getTaskDefinitionKey();

	/** 获取流程内容执行器 */
	ConnectorExecutionContext getExecutionContext();

	/** 获取事件名称 */
	String getEventName();

	/** 给任务添加一个候选用户 */
	void addCandidateUser(String userId);
	
	/** 给任务添加一个候选用户 */
	void addCandidateUserEntity(UserEntity user);

	/** 给任务添加多个候选用户 */
	void addCandidateUsers(Collection<String> candidateUsers);
	
	/** 给任务添加多个候选用户 */
	void addCandidateUserEntitys(Collection<UserEntity> candidateUsers);

	/** 给任务添加一个候选组 */
	void addCandidateGroup(String groupId,String groupType);
	
	/** 给任务添加一个候选组 */
	void addCandidateGroupEntity(GroupEntity group);

	/** 给任务添加多个候选组 */
	void addCandidateGroupEntitys(Collection<GroupEntity> candidateGroups);

	/** 获取任务的拥有人 */
	String getOwner();

	/** 设置任务的拥有者 */
	void setOwner(String owner);

	/** 获取任务的处理者*/
	String getAssignee();

	/** 设置任务的处理者*/
	void setAssignee(String assignee);

	/** 获取任务的到期时间 */
	Date getDueDate();

	/** 设置任务的到期时间 */
	void setDueDate(Date dueDate);

	/** 给任务添加一个候选用户 */
	void addUserIdentityLink(String userId, String identityLinkType);

	/** 给任务添加一个候选组 */
	void addGroupIdentityLink(String groupId,String groupType, String identityLinkType);

	/** 删除一个候选用户 */
	void deleteCandidateUser(String userId);

	/** 删除一个候选组 */
	void deleteCandidateGroup(String groupId);

	/** 删除候选用户 */
	void deleteUserIdentityLink(String userId, String identityLinkType);

	/** 删除候选组 */
	void deleteGroupIdentityLink(String groupId,String groupType, String identityLinkType);

	/** 获取候选用户 */
	Set<IdentityLink> getCandidates();
	
	/** 是否自动领取 */
	boolean isAutoClaim();


}
