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
package org.foxbpm.engine.factory;



import java.sql.Connection;

import org.foxbpm.engine.action.AssignmentHandler;
import org.foxbpm.engine.action.CommandHandler;
import org.foxbpm.engine.context.ContextInstance;
import org.foxbpm.engine.impl.bpmn.behavior.UserTaskBehavior;
import org.foxbpm.engine.impl.factory.ProcessObjectFactoryImpl;
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
import org.foxbpm.engine.impl.runtime.TokenEntity;
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


public interface ProcessObjectFactory {
	
	
	

	ProcessObjectFactory FACTORYINSTANCE = ProcessObjectFactoryImpl.init();

	
	ExecutionContext createExecutionContext(TokenEntity token);
	
	
	TaskMgmtInstance createTaskMgmtInstance();
	

	
	TaskDefinition createTaskDefinition(UserTaskBehavior userTask);
	
	AssignmentHandler createAssignmentHandler(String className);
	
	CommandHandler createCommandHandler(String className);
	
	ContextInstance createContextInstance(ProcessInstance processInstance);
	/**
	 * 创建任务实例持久化对象
	 * @param connection 可用的数据库连接
	 * @return
	 */
	TaskInstancePersistence createTaskInstancePersistence(Connection connection);
	
	/**
	 * 创建流程实例持久化对象
	 * @param connection 可用的数据库连接
	 * @return
	 */
	ProcessInstancePersistence createProcessInstancePersistence(Connection connection);
	
	/**
	 * 创建任务候选人持久化
	 * @param connection 可用的数据库连接
	 * @return
	 */
	IdentityLinkPersistence createIdentityLinkPersistence(Connection connection);
	
	/**
	 * 创建定时任务持久化
	 * @param connection 可用的数据库连接
	 * @return
	 */
	JobPersistence createJobPersistence(Connection connection);
	
	/**
	 * 创建令牌持久化
	 * @param connection 可用的数据库连接
	 * @return
	 */
	TokenPersistence createTokenPersistence(Connection connection);
	
	/**
	 * 创建流程变量持久化
	 * @param connection 可用的数据库连接
	 * @return
	 */
	VariablePersistence createVariablePersistence(Connection connection);
	
	/**
	 * 创建流程发布持久化
	 * @param connection 可用的数据库连接
	 * @return
	 */
	DeploymentPersistence createDeploymentPersistence(Connection connection);
	
	/**
	 * 创建资源持久化
	 * @param connection 可用的数据库连接
	 * @return
	 */
	ResourcePersistence createResourcePersistence(Connection connection);
	
	/**
	 * 创建流程定义持久化
	 * @param connection 可用的数据库连接
	 * @return
	 */
	ProcessDefinitionPersistence createProcessDefinitionPersistence(Connection connection);
	
	/**
	 * 创建流程归档持久化
	 * @param connection 可用的数据库连接
	 * @return
	 */
	HistoryPersistence createHistoryPersistence(Connection connection);
	
	/**
	 * 创建流程发布查询
	 * @param  commandExecutor
	 * @return
	 */
	DeploymentQuery createDeploymentQuery(CommandExecutor commandExecutor);
	
	/**
	 * 创建流程实例查询
	 * @param  commandExecutor
	 * @return
	 */
	ProcessInstanceQuery createProcessInstanceQuery(CommandExecutor commandExecutor);
	
	/**
	 * 创建任务查询
	 * @param  commandExecutor
	 * @return
	 */
	TaskQuery createTaskQuery(CommandExecutor commandExecutor);
	
	/**
	 * 创建任务候选人查询
	 * @param  commandExecutor
	 * @return
	 */
	IdentityLinkQuery createIdentityLinkQuery(CommandExecutor commandExecutor);
	
	/**
	 * 创建令牌查询
	 * @param  commandExecutor
	 * @return
	 */
	TokenQuery createTokenQuery(CommandExecutor commandExecutor);
	
	/**
	 * 创建流程定义查询
	 * @param  commandExecutor
	 * @return
	 */
	ProcessDefinitionQuery createProcessDefinitionQuery(CommandExecutor commandExecutor);
	
}
