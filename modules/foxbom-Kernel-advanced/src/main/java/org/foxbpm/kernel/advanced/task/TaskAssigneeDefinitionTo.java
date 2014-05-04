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
package org.foxbpm.kernel.advanced.task;


/**
 * 任务分配定义
 * @author kenshin
 *
 */
public class TaskAssigneeDefinitionTo {
	

	/**
	 * 直接处理人的表达式
	 */
	protected String  userIdExpression;
	
	

	/**
	 * 组编号的表达式
	 */
	protected String groupIdExpression;
	
	/**
	 * 组的类型编号
	 */
	protected String groupTypeExpression;
	
	/**
	 * 任务类型(共享、独占)
	 */
	protected IdentityLinkType identityLinkType;
	
	
	/**
	 * 资源范围
	 */
	protected String resourceRange;
	
	/**
	 * 是否包含子组
	 */
	protected boolean isContainsSub;


	
	

	

	public String getUserIdExpression() {
		return userIdExpression;
	}

	public void setUserIdExpression(String userIdExpression) {
		this.userIdExpression = userIdExpression;
	}

	public String getGroupIdExpression() {
		return groupIdExpression;
	}

	public void setGroupIdExpression(String groupIdExpression) {
		this.groupIdExpression = groupIdExpression;
	}

	public String getGroupTypeExpression() {
		return groupTypeExpression;
	}

	public void setGroupTypeExpression(String groupTypeExpression) {
		this.groupTypeExpression = groupTypeExpression;
	}

	public IdentityLinkType getIdentityLinkType() {
		return identityLinkType;
	}

	public void setIdentityLinkType(IdentityLinkType identityLinkType) {
		this.identityLinkType = identityLinkType;
	}

	
	public String getResourceRange() {
		return resourceRange;
	}

	public void setResourceRange(String resourceRange) {
		this.resourceRange = resourceRange;
	}
	public boolean isContainsSub() {
		return isContainsSub;
	}

	public void setContainsSub(boolean isContainsSub) {
		this.isContainsSub = isContainsSub;
	}
	
	

}
