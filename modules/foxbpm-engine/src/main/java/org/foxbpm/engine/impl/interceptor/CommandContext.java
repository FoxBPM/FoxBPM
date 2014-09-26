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

import java.util.HashMap;
import java.util.Map;

import org.foxbpm.engine.exception.FoxBPMClassLoadingException;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.identity.UserDefinition;
import org.foxbpm.engine.impl.ProcessEngineConfigurationImpl;
import org.foxbpm.engine.impl.persistence.AgentManager;
import org.foxbpm.engine.impl.persistence.DeploymentEntityManager;
import org.foxbpm.engine.impl.persistence.HistoryManager;
import org.foxbpm.engine.impl.persistence.IdentityLinkManager;
import org.foxbpm.engine.impl.persistence.ProcessDefinitionManager;
import org.foxbpm.engine.impl.persistence.ProcessInstanceManager;
import org.foxbpm.engine.impl.persistence.ProcessOperatingManager;
import org.foxbpm.engine.impl.persistence.ResourceManager;
import org.foxbpm.engine.impl.persistence.RunningTrackManager;
import org.foxbpm.engine.impl.persistence.TaskManager;
import org.foxbpm.engine.impl.persistence.TokenManager;
import org.foxbpm.engine.impl.persistence.VariableManager;
import org.foxbpm.engine.sqlsession.ISqlSession;
import org.foxbpm.engine.transaction.TransactionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kenshin
 */
public class CommandContext {

	Logger log = LoggerFactory.getLogger(CommandContext.class);
	protected Command<?> command;
	protected Map<Class<?>, SessionFactory> sessionFactories;
	protected Map<Class<?>, Session> sessions = new HashMap<Class<?>, Session>();
	protected TransactionContext transactionContext;
	protected Throwable exception = null;
	protected ProcessEngineConfigurationImpl processEngineConfigurationImpl;
	protected boolean isCommit = true;

	public CommandContext(Command<?> command,
			ProcessEngineConfigurationImpl processEngineConfigurationImpl) {
		this.command = command;
		this.processEngineConfigurationImpl = processEngineConfigurationImpl;
		sessionFactories = processEngineConfigurationImpl.getSessionFactories();
	}

	public ProcessEngineConfigurationImpl getProcessEngineConfigurationImpl() {
		return processEngineConfigurationImpl;
	}

	@SuppressWarnings({"unchecked"})
	public <T> T getSession(Class<T> sessionClass) {
		Session session = sessions.get(sessionClass);
		if (session == null) {
			SessionFactory sessionFactory = sessionFactories.get(sessionClass);
			if (sessionFactory == null) {
				throw new FoxBPMClassLoadingException("no session factory configured for "
						+ sessionClass.getName());
			}
			session = sessionFactory.openSession();
			sessions.put(sessionClass, session);
		}
		return (T) session;
	}

	public DeploymentEntityManager getDeploymentEntityManager() {
		return getSession(DeploymentEntityManager.class);
	}

	public ResourceManager getResourceManager() {
		return getSession(ResourceManager.class);
	}

	public ProcessDefinitionManager getProcessDefinitionManager() {
		return getSession(ProcessDefinitionManager.class);
	}

	public ProcessInstanceManager getProcessInstanceManager() {
		return getSession(ProcessInstanceManager.class);
	}

	public TaskManager getTaskManager() {
		return getSession(TaskManager.class);
	}

	public IdentityLinkManager getIdentityLinkManager() {
		return getSession(IdentityLinkManager.class);
	}

	public AgentManager getAgentManager() {
		return getSession(AgentManager.class);
	}

	public VariableManager getVariableManager() {
		return getSession(VariableManager.class);
	}

	public TokenManager getTokenManager() {
		return getSession(TokenManager.class);
	}

	public RunningTrackManager getRunningTrackManager() {
		return getSession(RunningTrackManager.class);
	}
	
	public ProcessOperatingManager getProcessOperatingManager() {
		return getSession(ProcessOperatingManager.class);
	}

	public HistoryManager getHistoryManager() {
		return getSession(HistoryManager.class);
	}

	public UserDefinition getUserEntityManager() {
		return getProcessEngineConfigurationImpl().getUserDefinition();
	}

	public Command<?> getCommand() {
		return command;
	}

	public ISqlSession getSqlSession() {
		return getSession(ISqlSession.class);
	}

	public void flushSession() {
		for (Session session : sessions.values()) {
			session.flush();
		}
	}

	public Throwable getException() {
		return exception;
	}

	public void setCommit(boolean isCommit) {
		this.isCommit = isCommit;
	}

	public void close() {
		try {
			if (exception == null) {
				flushSession();
			}
		} catch (Exception ex) {
			exception(ex);
		} finally {
			closeSessions();
		}

		if (exception != null) {
			if (exception instanceof Error) {
				throw (Error) exception;
			} else if (exception instanceof RuntimeException) {
				throw (RuntimeException) exception;
			} else {
				throw new FoxBPMException("exception while executing command " + command, exception);
			}
		}

	}

	public void closeSessions() {
		for (Session session : sessions.values()) {
			try {
				session.close();
			} catch (Throwable exception) {
				exception(exception);
			}
		}
	}

	public void exception(Throwable e) {
		if (this.exception == null) {
			this.exception = e;
		} else {
			log.error(
					"masked exception in command context. for root cause, see below as it will be rethrown later.",
					exception);
		}
	}
}
