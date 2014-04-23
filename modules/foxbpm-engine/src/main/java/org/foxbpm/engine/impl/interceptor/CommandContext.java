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
package org.foxbpm.engine.impl.interceptor;

import org.foxbpm.engine.impl.ProcessEngineConfigurationImpl;
import org.foxbpm.engine.impl.persistence.DeploymentEntityManager;
import org.foxbpm.engine.impl.persistence.HistoryManager;
import org.foxbpm.engine.impl.persistence.IdentityLinkManager;
import org.foxbpm.engine.impl.persistence.ProcessDefinitionManager;
import org.foxbpm.engine.impl.persistence.ProcessInstanceManager;
import org.foxbpm.engine.impl.persistence.ResourceManager;
import org.foxbpm.engine.impl.persistence.TaskManager;
import org.foxbpm.engine.impl.persistence.TokenManager;
import org.foxbpm.engine.impl.persistence.VariableManager;
import org.foxbpm.engine.sqlsession.ISqlSession;
import org.foxbpm.engine.sqlsession.ISqlSessionFactory;

/**
 * @author kenshin
 */
public class CommandContext {

	protected Command<?> command;
	protected ISqlSession sqlSession;
	

	protected ProcessEngineConfigurationImpl processEngineConfigurationImpl;

	public CommandContext(Command<?> command, ProcessEngineConfigurationImpl processEngineConfigurationImpl) {
		this.command = command;
		this.processEngineConfigurationImpl = processEngineConfigurationImpl;

	}

	public ProcessEngineConfigurationImpl getProcessEngineConfigurationImpl() {
		return processEngineConfigurationImpl;
	}
	
	

	public DeploymentEntityManager getDeploymentEntityManager() {
		DeploymentEntityManager deploymentManager = new DeploymentEntityManager();
		deploymentManager.setCommandContext(this);
		return deploymentManager;
	}

	public ResourceManager getResourceManager() {
		ResourceManager resourceManager = new ResourceManager();
		resourceManager.setCommandContext(this);
		return resourceManager;
	}

	public ProcessDefinitionManager getProcessDefinitionManager() {
		ProcessDefinitionManager processDefinitionManager = new ProcessDefinitionManager();
		processDefinitionManager.setCommandContext(this);
		return processDefinitionManager;
	}

	public ProcessInstanceManager getProcessInstanceManager() {
		ProcessInstanceManager processInstanceManager = new ProcessInstanceManager();
		processInstanceManager.setCommandContext(this);
		return processInstanceManager;
	}

	public TaskManager getTaskManager() {
		TaskManager taskManager = new TaskManager();
		taskManager.setCommandContext(this);
		return taskManager;
	}

	public IdentityLinkManager getIdentityLinkManager() {
		IdentityLinkManager identityLinkManager = new IdentityLinkManager();
		identityLinkManager.setCommandContext(this);

		return identityLinkManager;
	}


	
	public VariableManager getVariableManager() {
		VariableManager variableManager = new VariableManager();
		variableManager.setCommandContext(this);

		return variableManager;
	}

	public TokenManager getTokenManager() {
		TokenManager tokenManager = new TokenManager();
		tokenManager.setCommandContext(this);
		return tokenManager;
	}
	
	public HistoryManager getHistoryManager(){
		HistoryManager historyManager = new HistoryManager();
		historyManager.setCommandContext(this);
		return historyManager;
	}


	public Command<?> getCommand() {
		return command;
	}
	
	public ISqlSession getSqlSession(){
		if(sqlSession == null){
			ISqlSessionFactory sqlSessionFactory = getProcessEngineConfigurationImpl().getSqlSessionFactory();
			sqlSession = sqlSessionFactory.createSqlSession();
		}
		return sqlSession;
	}
	
	public void close(){
		if(sqlSession != null){
			sqlSession.closeSession();
		}
	}

}
