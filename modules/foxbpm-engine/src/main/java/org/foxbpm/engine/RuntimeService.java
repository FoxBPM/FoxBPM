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
 * @author ych
 */
package org.foxbpm.engine;

import java.util.Map;

import org.foxbpm.engine.datavariable.VariableQuery;
import org.foxbpm.engine.runningtrack.RunningTrackQuery;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.runtime.ProcessInstanceQuery;
import org.foxbpm.engine.runtime.TokenQuery;

public interface RuntimeService {

	/**
	 * 根据流程定义key启动流程，启动对应key的最新版本
	 * 
	 * @param processDefinitionKey
	 * @return
	 */
	ProcessInstance startProcessInstanceByKey(String processDefinitionKey);

	/**
	 * 根据流程定义key启动流程，启动对应key的最新版本
	 * 
	 * @param processDefinitionKey
	 * @param bizKey
	 *            流程关联键
	 * @return
	 */
	ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String bizKey);

	/**
	 * 根据流程定义Key启动流程，启动对应key的最新版本
	 * 
	 * @param processDefinitionKey
	 * @param transientVariables
	 *            瞬态变量
	 * @param persistenceVariables
	 *            持久化变量
	 * @return
	 */
	ProcessInstance startProcessInstanceByKey(String processDefinitionKey, Map<String, Object> transientVariables, Map<String, Object> persistenceVariables);

	/**
	 * 根据流程定义Key启动流程，启动对应key的最新版本
	 * 
	 * @param processDefinitionKey
	 * @param bizKey
	 *            流程关联键
	 * @param transientVariables
	 *            瞬态变量
	 * @param persistenceVariables
	 *            持久化变量
	 * @return
	 */
	ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String bizKey, Map<String, Object> transientVariables, Map<String, Object> persistenceVariables);

	/**
	 * 根据流程定义id(唯一编号)启动流程
	 * 
	 * @param processDefinitionId
	 * @return
	 */
	ProcessInstance startProcessInstanceById(String processDefinitionId);

	/**
	 * 根据流程定义id(唯一编号)启动流程
	 * 
	 * @param processDefinitionId
	 * @param bizKey
	 *            流程关联键
	 * @return
	 */
	ProcessInstance startProcessInstanceById(String processDefinitionId, String bizKey);

	/**
	 * 根据流程定义id(唯一编号)启动流程
	 * 
	 * @param processDefinitionId
	 * @param transientVariables
	 *            瞬态变量
	 * @param persistenceVariables
	 *            持久化变量
	 * @return
	 */
	ProcessInstance startProcessInstanceById(String processDefinitionId, Map<String, Object> transientVariables, Map<String, Object> persistenceVariables);

	/**
	 * 根据流程定义id(唯一编号)启动流程
	 * 
	 * @param processDefinitionId
	 * @param bizKey
	 *            流程关联键
	 * @param transientVariables
	 *            瞬态变量
	 * @param persistenceVariables
	 *            持久化变量
	 * @return
	 */
	ProcessInstance startProcessInstanceById(String processDefinitionId, String bizKey, Map<String, Object> transientVariables, Map<String, Object> persistenceVariables);

	/**
	 * 根据消息启动流程(尚未实现)
	 * 
	 * @param messageName
	 * @return
	 */
	ProcessInstance startProcessInstanceByMessage(String messageName);

	/**
	 * 根据消息启动流程(尚未实现)
	 * 
	 * @param messageName
	 * @param bizKey
	 *            流程关联键
	 * @return
	 */
	ProcessInstance startProcessInstanceByMessage(String messageName, String bizKey);

	/**
	 * 根据消息启动流程(尚未实现)
	 * 
	 * @param messageName
	 * @param processVariables
	 *            持久化变量
	 * @return
	 */
	ProcessInstance startProcessInstanceByMessage(String messageName, Map<String, Object> processVariables);

	/**
	 * 根据消息启动流程(尚未实现)
	 * 
	 * @param messageName
	 * @param bizKey
	 *            流程关联键
	 * @param processVariables
	 *            持久化变量
	 * @return
	 */
	ProcessInstance startProcessInstanceByMessage(String messageName, String bizKey, Map<String, Object> processVariables);

	/**
	 * 驱动流程实例向下走一步，流转到下一个等待节点(如：人工任务等)
	 * 
	 * @param tokenId
	 *            令牌编号，可以从流程实例上获得，也可以通过tokenQuery获得
	 */
	void signal(String tokenId);

	/**
	 * 驱动流程实例向下走一步，流转到下一个等待节点(如：人工任务等)
	 * 
	 * @param tokenId
	 *            令牌编号，可以从流程实例上获得，也可以通过tokenQuery获得
	 * @param transientVariables
	 *            瞬态变量
	 * @param persistenceVariables
	 *            持久化变量
	 */
	void signal(String tokenId, Map<String, Object> transientVariables, Map<String, Object> persistenceVariables);

	/**
	 * 创建令牌查询对象
	 * 
	 * @return
	 */
	TokenQuery createTokenQuery();

	/**
	 * 创建流程实例查询对象
	 * 
	 * @return
	 */
	ProcessInstanceQuery createProcessInstanceQuery();

	/**
	 * 创建变量查询对象
	 * 
	 * @return
	 */
	VariableQuery createVariableQuery();

	/**
	 * 创建运行轨迹查询对象
	 * 
	 * @return
	 */
	RunningTrackQuery createRunningTrackQuery();

	/**
	 * 根据流程实例号删除流程实例（级联删除所有相关数据）
	 * 
	 * @param processInstanceId
	 */
	void deleteProcessInstance(String processInstanceId);
}
