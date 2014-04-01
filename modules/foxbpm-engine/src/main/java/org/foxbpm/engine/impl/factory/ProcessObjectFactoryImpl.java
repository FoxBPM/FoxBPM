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
package org.foxbpm.engine.impl.factory;

import java.sql.Connection;
import java.util.List;



import com.founder.fix.bpmn2extensions.coreconfig.ExpandClass;
import com.founder.fix.bpmn2extensions.coreconfig.ExpandClassConfig;
import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.action.AssignmentHandler;
import org.foxbpm.engine.action.CommandHandler;
import org.foxbpm.engine.context.ContextInstance;
import org.foxbpm.engine.exception.FixFlowException;
import org.foxbpm.engine.factory.ProcessObjectFactory;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.ProcessEngineConfigurationImpl;
import org.foxbpm.engine.impl.bpmn.behavior.UserTaskBehavior;
import org.foxbpm.engine.impl.context.ContextInstanceImpl;
import org.foxbpm.engine.impl.interceptor.CommandExecutor;


import org.foxbpm.engine.impl.persistence.definition.DeploymentPersistence;
import org.foxbpm.engine.impl.persistence.definition.ProcessDefinitionPersistence;
import org.foxbpm.engine.impl.persistence.definition.ResourcePersistence;
import org.foxbpm.engine.impl.persistence.instance.HistoryPersistence;
import org.foxbpm.engine.impl.persistence.instance.IdentityLinkPersistence;
import org.foxbpm.engine.impl.persistence.instance.JobPersistence;
import org.foxbpm.engine.impl.persistence.instance.ProcessInstancePersistence;
import org.foxbpm.engine.impl.persistence.instance.TaskInstancePersistence;
import org.foxbpm.engine.impl.persistence.instance.TokenPersistence;
import org.foxbpm.engine.impl.persistence.instance.VariablePersistence;
import org.foxbpm.engine.impl.runtime.ExecutionContextImpl;
import org.foxbpm.engine.impl.runtime.TokenEntity;
import org.foxbpm.engine.impl.task.TaskDefinitionImpl;
import org.foxbpm.engine.impl.util.ReflectUtil;
import org.foxbpm.engine.model.DeploymentQuery;
import org.foxbpm.engine.model.ProcessDefinitionQuery;

import org.foxbpm.engine.runtime.ExecutionContext;
import org.foxbpm.engine.runtime.IdentityLinkQuery;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.runtime.ProcessInstanceQuery;
import org.foxbpm.engine.runtime.TokenQuery;
import org.foxbpm.engine.task.TaskDefinition;
import org.foxbpm.engine.task.TaskMgmtInstance;
import org.foxbpm.engine.task.TaskQuery;


public class ProcessObjectFactoryImpl implements ProcessObjectFactory {
	
	ProcessEngineConfigurationImpl processEngineConfiguration=ProcessEngineManagement.getDefaultProcessEngine().getProcessEngineConfiguration();
	public ProcessObjectFactoryImpl(){
		
	}

	public static ProcessObjectFactory init() {
		return new ProcessObjectFactoryImpl();
	}

	public ExecutionContext createExecutionContext(TokenEntity token) {
		ExecutionContextImpl executionContextImpl = new ExecutionContextImpl(token);
		return executionContextImpl;
	}

	

	public TaskDefinition createTaskDefinition(UserTaskBehavior userTask) {
		TaskDefinitionImpl taskDefinitionImpl = new TaskDefinitionImpl(userTask);
		return taskDefinitionImpl;
	}


	public AssignmentHandler createAssignmentHandler(String className) {
		AssignmentHandler assignmentHandler = (AssignmentHandler) ReflectUtil.instantiate(className);
		return assignmentHandler;
	}

	public CommandHandler createCommandHandler(String className) {
		CommandHandler commandHandler = (CommandHandler) ReflectUtil.instantiate(className);
		return commandHandler;
	}


	public TaskMgmtInstance createTaskMgmtInstance() {
		
		ExpandClassConfig expandClassConfig = processEngineConfiguration.getExpandClassConfig();
		List<ExpandClass>  expandClasses=expandClassConfig.getExpandClass();
		for (ExpandClass expandClass : expandClasses) {
			if(expandClass.getClassId().equals("TaskMgmtInstance")){
				
				TaskMgmtInstance taskMgmtInstance =(TaskMgmtInstance) ReflectUtil.instantiate(expandClass.getClassImpl());
				return taskMgmtInstance;
			}
		}
		throw new FixFlowException("流程引擎扩展配置里的TaskMgmtInstance实现类指定错误");
		
	}
	
	/**
	 * 创建任务实例持久化对象
	 */
	public TaskInstancePersistence createTaskInstancePersistence(Connection connection) {
		
		ExpandClassConfig expandClassConfig = processEngineConfiguration.getExpandClassConfig();
		List<ExpandClass>  expandClasses=expandClassConfig.getExpandClass();
		for (ExpandClass expandClass : expandClasses) {
			if(expandClass.getClassId().equals("TaskInstancePersistence")){
				Object[] objTemp = new Object[] {connection};  
				TaskInstancePersistence taskInstancePersistence =(TaskInstancePersistence) ReflectUtil.instantiate(expandClass.getClassImpl(),objTemp);
				return taskInstancePersistence;
			}
		}
		throw new FixFlowException("流程引擎扩展配置里的TaskInstancePersistence实现类指定错误");
		
	}
	
	/**
	 * 创建流程实例持久化对象
	 */
	public ProcessInstancePersistence createProcessInstancePersistence(
			Connection connection) {
		ExpandClassConfig expandClassConfig=processEngineConfiguration.getExpandClassConfig();
		List<ExpandClass>  expandClasses=expandClassConfig.getExpandClass();
		for (ExpandClass expandClass : expandClasses) {
			if(expandClass.getClassId().equals("ProcessInstancePersistence")){
				Object[] objTemp = new Object[] {connection};  
				ProcessInstancePersistence processInstancePersistence =(ProcessInstancePersistence) ReflectUtil.instantiate(expandClass.getClassImpl(),objTemp);
				return processInstancePersistence;
			}
		}
		throw new FixFlowException("流程引擎扩展配置里的ProcessInstancePersistence实现类指定错误");
	}
	
	/**
	 * 创建任务候选人持久化
	 */
	public IdentityLinkPersistence createIdentityLinkPersistence(
			Connection connection) {
		ExpandClassConfig expandClassConfig=processEngineConfiguration.getExpandClassConfig();
		List<ExpandClass>  expandClasses=expandClassConfig.getExpandClass();
		for (ExpandClass expandClass : expandClasses) {
			if(expandClass.getClassId().equals("IdentityLinkPersistence")){
				Object[] objTemp = new Object[] {connection};  
				IdentityLinkPersistence identityLinkPersistence =(IdentityLinkPersistence) ReflectUtil.instantiate(expandClass.getClassImpl(),objTemp);
				return identityLinkPersistence;
			}
		}
		throw new FixFlowException("流程引擎扩展配置里的IdentityLinkPersistence实现类指定错误");
	}

	/**
	 * 创建定时任务持久化
	 */
	public JobPersistence createJobPersistence(
			Connection connection) {
		ExpandClassConfig expandClassConfig=processEngineConfiguration.getExpandClassConfig();
		List<ExpandClass>  expandClasses=expandClassConfig.getExpandClass();
		for (ExpandClass expandClass : expandClasses) {
			if(expandClass.getClassId().equals("JobPersistence")){
				Object[] objTemp = new Object[] {connection};  
				JobPersistence jobPersistence =(JobPersistence) ReflectUtil.instantiate(expandClass.getClassImpl(),objTemp);
				return jobPersistence;
			}
		}
		throw new FixFlowException("流程引擎扩展配置里的JobPersistence实现类指定错误");
	}
	
	/**
	 * 创建令牌持久化
	 */
	public TokenPersistence createTokenPersistence(Connection connection){
		ExpandClassConfig expandClassConfig=processEngineConfiguration.getExpandClassConfig();
		List<ExpandClass>  expandClasses=expandClassConfig.getExpandClass();
		for (ExpandClass expandClass : expandClasses) {
			if(expandClass.getClassId().equals("TokenPersistence")){
				Object[] objTemp = new Object[] {connection};  
				TokenPersistence tokenPersistence =(TokenPersistence) ReflectUtil.instantiate(expandClass.getClassImpl(),objTemp);
				return tokenPersistence;
			}
		}
		throw new FixFlowException("流程引擎扩展配置里的TokenPersistence实现类指定错误");
	}

	/**
	 * 创建流程变量持久化
	 */
	public VariablePersistence createVariablePersistence(Connection connection){
		ExpandClassConfig expandClassConfig=processEngineConfiguration.getExpandClassConfig();
		List<ExpandClass>  expandClasses=expandClassConfig.getExpandClass();
		for (ExpandClass expandClass : expandClasses) {
			if(expandClass.getClassId().equals("VariablePersistence")){
				Object[] objTemp = new Object[] {connection};  
				VariablePersistence variablePersistence =(VariablePersistence) ReflectUtil.instantiate(expandClass.getClassImpl(),objTemp);
				return variablePersistence;
			}
		}
		throw new FixFlowException("流程引擎扩展配置里的VariablePersistence实现类指定错误");
	}
	
	/**
	 * 创建流程发布持久化
	 */
	public DeploymentPersistence createDeploymentPersistence(Connection connection){
		ExpandClassConfig expandClassConfig=processEngineConfiguration.getExpandClassConfig();
		List<ExpandClass>  expandClasses=expandClassConfig.getExpandClass();
		for (ExpandClass expandClass : expandClasses) {
			if(expandClass.getClassId().equals("DeploymentPersistence")){
				Object[] objTemp = new Object[] {connection};  
				DeploymentPersistence deploymentPersistence =(DeploymentPersistence) ReflectUtil.instantiate(expandClass.getClassImpl(),objTemp);
				return deploymentPersistence;
			}
		}
		throw new FixFlowException("流程引擎扩展配置里的DeploymentPersistence实现类指定错误");
	}
	
	/**
	 * 创建资源发布持久化
	 */
	public ResourcePersistence createResourcePersistence(Connection connection){
		ExpandClassConfig expandClassConfig=Context.getProcessEngineConfiguration().getExpandClassConfig();
		List<ExpandClass>  expandClasses=expandClassConfig.getExpandClass();
		for (ExpandClass expandClass : expandClasses) {
			if(expandClass.getClassId().equals("ResourcePersistence")){
				Object[] objTemp = new Object[] {connection};  
				ResourcePersistence resourcePersistence =(ResourcePersistence) ReflectUtil.instantiate(expandClass.getClassImpl(),objTemp);
				return resourcePersistence;
			}
		}
		throw new FixFlowException("流程引擎扩展配置里的ResourcePersistence实现类指定错误");
	}
	
	/**
	 * 创建流程定义持久化
	 */
	public ProcessDefinitionPersistence createProcessDefinitionPersistence(Connection connection){
		ExpandClassConfig expandClassConfig=processEngineConfiguration.getExpandClassConfig();
		List<ExpandClass>  expandClasses=expandClassConfig.getExpandClass();
		for (ExpandClass expandClass : expandClasses) {
			if(expandClass.getClassId().equals("ProcessDefinitionPersistence")){
				Object[] objTemp = new Object[] {connection};  
				ProcessDefinitionPersistence processDefinitionPersistence =(ProcessDefinitionPersistence) ReflectUtil.instantiate(expandClass.getClassImpl(),objTemp);
				return processDefinitionPersistence;
			}
		}
		throw new FixFlowException("流程引擎扩展配置里的ProcessDefinitionPersistence实现类指定错误");
	}
	
	/**
	 * 创建流程归档持久化
	 */
	public HistoryPersistence createHistoryPersistence(Connection connection){
		ExpandClassConfig expandClassConfig=processEngineConfiguration.getExpandClassConfig();
		List<ExpandClass>  expandClasses=expandClassConfig.getExpandClass();
		for (ExpandClass expandClass : expandClasses) {
			if(expandClass.getClassId().equals("HistoryPersistence")){
				Object[] objTemp = new Object[] {connection};  
				HistoryPersistence historyPersistence =(HistoryPersistence) ReflectUtil.instantiate(expandClass.getClassImpl(),objTemp);
				return historyPersistence;
			}
		}
		throw new FixFlowException("流程引擎扩展配置里的HistoryPersistence实现类指定错误");
	}
	
	/**
	 * 创建流程发布查询
	 */
	public DeploymentQuery createDeploymentQuery(CommandExecutor commandExecutor){
		ExpandClassConfig expandClassConfig=processEngineConfiguration.getExpandClassConfig();
		List<ExpandClass>  expandClasses=expandClassConfig.getExpandClass();
		for (ExpandClass expandClass : expandClasses) {
			if(expandClass.getClassId().equals("ProcessDefinitionPersistence")){
				Object[] objTemp = new Object[] {commandExecutor};  
				DeploymentQuery deploymentQuery =(DeploymentQuery) ReflectUtil.instantiate(expandClass.getClassImpl(),objTemp);
				return deploymentQuery;
			}
		}
		throw new FixFlowException("流程引擎扩展配置里的ProcessDefinitionPersistence实现类指定错误");
	}
	
	/**
	 * 创建流程实例查询
	 */
	public ProcessInstanceQuery createProcessInstanceQuery(CommandExecutor commandExecutor){
		
		

		ExpandClassConfig expandClassConfig=processEngineConfiguration.getExpandClassConfig();
		List<ExpandClass>  expandClasses=expandClassConfig.getExpandClass();
		for (ExpandClass expandClass : expandClasses) {
			if(expandClass.getClassId().equals("ProcessInstanceQuery")){
				Object[] objTemp = new Object[] {commandExecutor};  
				ProcessInstanceQuery processInstanceQuery =(ProcessInstanceQuery) ReflectUtil.instantiate(expandClass.getClassImpl(),objTemp);
				return processInstanceQuery;
			}
		}
		throw new FixFlowException("流程引擎扩展配置里的ProcessInstanceQuery实现类指定错误");
	}
	
	/**
	 * 创建流程实例查询
	 */
	public IdentityLinkQuery createIdentityLinkQuery(CommandExecutor commandExecutor){
		ExpandClassConfig expandClassConfig=processEngineConfiguration.getExpandClassConfig();
		List<ExpandClass>  expandClasses=expandClassConfig.getExpandClass();
		for (ExpandClass expandClass : expandClasses) {
			if(expandClass.getClassId().equals("IdentityLinkQuery")){
				Object[] objTemp = new Object[] {commandExecutor};  
				IdentityLinkQuery identityLinkQuery =(IdentityLinkQuery) ReflectUtil.instantiate(expandClass.getClassImpl(),objTemp);
				return identityLinkQuery;
			}
		}
		throw new FixFlowException("流程引擎扩展配置里的IdentityLinkQuery实现类指定错误");
	}
	
	/**
	 * 创建任务查询
	 */
	public TaskQuery createTaskQuery(CommandExecutor commandExecutor){
		ExpandClassConfig expandClassConfig=processEngineConfiguration.getExpandClassConfig();
		List<ExpandClass>  expandClasses=expandClassConfig.getExpandClass();
		for (ExpandClass expandClass : expandClasses) {
			if(expandClass.getClassId().equals("TaskQuery")){
				Object[] objTemp = new Object[] {commandExecutor};  
				TaskQuery taskQuery =(TaskQuery) ReflectUtil.instantiate(expandClass.getClassImpl(),objTemp);
				return taskQuery;
			}
		}
		throw new FixFlowException("流程引擎扩展配置里的TaskQuery实现类指定错误");
	}
	
	/**
	 * 创建令牌查询
	 */
	public TokenQuery createTokenQuery(CommandExecutor commandExecutor){
		ExpandClassConfig expandClassConfig=processEngineConfiguration.getExpandClassConfig();
		List<ExpandClass>  expandClasses=expandClassConfig.getExpandClass();
		for (ExpandClass expandClass : expandClasses) {
			if(expandClass.getClassId().equals("TokenQuery")){
				Object[] objTemp = new Object[] {commandExecutor};  
				TokenQuery tokenQuery =(TokenQuery) ReflectUtil.instantiate(expandClass.getClassImpl(),objTemp);
				return tokenQuery;
			}
		}
		throw new FixFlowException("流程引擎扩展配置里的TokenQuery实现类指定错误");
	}
	
	/**
	 * 创建流程定义查询
	 */
	public ProcessDefinitionQuery createProcessDefinitionQuery(CommandExecutor commandExecutor){
		ExpandClassConfig expandClassConfig=processEngineConfiguration.getExpandClassConfig();
		List<ExpandClass>  expandClasses=expandClassConfig.getExpandClass();
		for (ExpandClass expandClass : expandClasses) {
			if(expandClass.getClassId().equals("ProcessDefinitionQuery")){
				Object[] objTemp = new Object[] {commandExecutor};  
				ProcessDefinitionQuery processDefinitionQuery =(ProcessDefinitionQuery) ReflectUtil.instantiate(expandClass.getClassImpl(),objTemp);
				return processDefinitionQuery;
			}
		}
		throw new FixFlowException("流程引擎扩展配置里的ProcessDefinitionQuery实现类指定错误");
	}
	public ContextInstance createContextInstance(ProcessInstance processInstance) {
		ContextInstanceImpl contextInstanceImpl = new ContextInstanceImpl(processInstance);
		return contextInstanceImpl;
	}






}
