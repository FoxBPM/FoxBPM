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
package org.foxbpm.engine.impl.interceptor;

import java.sql.Connection;

import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.ProcessEngineConfigurationImpl;
import org.foxbpm.engine.impl.cache.CacheObject;
import org.foxbpm.engine.impl.db.DbSqlSession;
import org.foxbpm.engine.impl.db.MappingSqlSession;
import org.foxbpm.engine.impl.persistence.CommentManager;
import org.foxbpm.engine.impl.persistence.DeploymentManager;
import org.foxbpm.engine.impl.persistence.EventSubscriptionManager;
import org.foxbpm.engine.impl.persistence.HistoryManager;
import org.foxbpm.engine.impl.persistence.IdentityLinkManager;
import org.foxbpm.engine.impl.persistence.JobManager;
import org.foxbpm.engine.impl.persistence.ProcessDefinitionManager;
import org.foxbpm.engine.impl.persistence.ProcessInstanceManager;
import org.foxbpm.engine.impl.persistence.ResourceManager;
import org.foxbpm.engine.impl.persistence.TaskManager;
import org.foxbpm.engine.impl.persistence.TokenManager;
import org.foxbpm.engine.impl.persistence.VariableManager;

/**
 * @author kenshin
 */
public class CommandContext {

	protected Command<?> command;

	protected ProcessEngineConfigurationImpl processEngineConfigurationImpl;

	public CommandContext(Command<?> command, ProcessEngineConfigurationImpl processEngineConfigurationImpl) {
		this.command = command;
		this.processEngineConfigurationImpl = processEngineConfigurationImpl;

	}

	public ProcessEngineConfigurationImpl getProcessEngineConfigurationImpl() {
		return processEngineConfigurationImpl;
	}

	public DeploymentManager getDeploymentManager() {

		DeploymentManager deploymentManager = new DeploymentManager();
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

	public CommentManager getCommentManager() {
		CommentManager commentManager = new CommentManager();
		commentManager.setCommandContext(this);

		return commentManager;
	}

	public TokenManager getTokenManager() {
		TokenManager tokenManager = new TokenManager();
		tokenManager.setCommandContext(this);
		return tokenManager;
	}

	public EventSubscriptionManager getEventSubscriptionManager() {
		EventSubscriptionManager eventSubscriptionManager = new EventSubscriptionManager();
		eventSubscriptionManager.setCommandContext(this);
		return eventSubscriptionManager;
	}
	
	public HistoryManager getHistoryManager(){
		HistoryManager historyManager = new HistoryManager();
		historyManager.setCommandContext(this);
		return historyManager;
	}
	public JobManager getJobManager() {
		JobManager jobManager = new JobManager();
		jobManager.setCommandContext(this);
		return jobManager;
	}
	
	
	// getters and setters
	// //////////////////////////////////////////////////////

	public DbSqlSession getDbSqlSession() {
		return new DbSqlSession(Context.getDbConnection(), Context.getCacheObject());
	}
	
	public MappingSqlSession getMappingSqlSession() {
		return new MappingSqlSession(Context.getDbConnection(), Context.getCacheObject());
	}
	
	

	public CacheObject getCacheObject() {
		return Context.getCacheObject();
	}

	public Command<?> getCommand() {
		return command;
	}

	public Connection getConnection() {
		return Context.getDbConnection();
	}

}
