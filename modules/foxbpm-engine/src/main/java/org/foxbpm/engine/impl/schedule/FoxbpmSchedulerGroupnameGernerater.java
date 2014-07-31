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
 * @author MAENLIANG
 */
package org.foxbpm.engine.impl.schedule;

import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

/**
 * 
 * 
 * FoxbpmSchedulerGroupnameGernerater 调度任务的GroupName 生成器
 * 
 * MAENLIANG 2014年7月24日 下午8:51:31
 * 
 * @version 1.0.0
 * 
 */
public class FoxbpmSchedulerGroupnameGernerater {
	private FlowNodeExecutionContext executionContext;
	private final static String APPEND_FLAG = "_";
	/**
	 * 默认GroupName长度
	 */
	private final static int DEFAULT_GROUPNAME_LENGTH = 200;
	/**
	 * 
	 * 创建一个新的实例 FoxbpmSchedulerGroupnameGernerater.
	 * 
	 * @param executionContext
	 *            运行时令牌
	 */
	public FoxbpmSchedulerGroupnameGernerater(FlowNodeExecutionContext executionContext) {
		this.executionContext = executionContext;
	}
	
	/**
	 * 
	 * gernerateInstanceGroupName(针对流程实例创建GroupName)
	 * 
	 * @return String
	 * @exception
	 * @since 1.0.0
	 */
	public String gernerateDefinitionGroupName() {
		StringBuffer buffer = new StringBuffer(DEFAULT_GROUPNAME_LENGTH);
		String tokenId = executionContext.getId();
		String nodeId = executionContext.getFlowNode().getId();
		String processInstanceId = executionContext.getProcessInstanceId();
		String processKey = executionContext.getProcessDefinition().getKey();
		return buffer.append(tokenId).append(APPEND_FLAG).append(nodeId).append(APPEND_FLAG).append(processInstanceId).append(APPEND_FLAG).append(processKey).toString();
	}
	
	/**
	 * 
	 * gernerateInstanceGroupName(针对流程实例创建GroupName)
	 * 
	 * @return String
	 * @exception
	 * @since 1.0.0
	 */
	public String gernerateInstanceGroupName() {
		StringBuffer buffer = new StringBuffer(DEFAULT_GROUPNAME_LENGTH);
		String tokenId = executionContext.getId();
		String nodeId = executionContext.getFlowNode().getId();
		String processInstanceId = executionContext.getProcessInstanceId();
		return buffer.append(tokenId).append(APPEND_FLAG).append(nodeId).append(APPEND_FLAG).append(processInstanceId).toString();
	}
	/**
	 * 
	 * gernerateNodeGroupName(针对节点创建GroupName)
	 * 
	 * @return String
	 * @exception
	 * @since 1.0.0
	 */
	public String gernerateNodeGroupName() {
		StringBuffer buffer = new StringBuffer(DEFAULT_GROUPNAME_LENGTH);
		String tokenId = executionContext.getId();
		String nodeId = executionContext.getFlowNode().getId();
		return buffer.append(tokenId).append(APPEND_FLAG).append(nodeId).toString();
		
	}
	
	/**
	 * 
	 * gernerateNodeGroupName(针对边界事件节点创建GroupName)
	 * 
	 * @return String
	 * @exception
	 * @since 1.0.0
	 */
	public String gernerateBoundaryNodeGroupName() {
		StringBuffer buffer = new StringBuffer(DEFAULT_GROUPNAME_LENGTH);
		String tokenId = executionContext.getParent().getId();
		String nodeId = executionContext.getFlowNode().getId();
		return buffer.append(tokenId).append(APPEND_FLAG).append(nodeId).toString();
		
	}
}
