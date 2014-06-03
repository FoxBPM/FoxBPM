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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.exception.FoxBPMClassLoadingException;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.identity.UserEntityManager;
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
import org.foxbpm.engine.transaction.TransactionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kenshin
 */
public class CommandContext {

	Logger log = LoggerFactory.getLogger(CommandContext.class);
	protected Command<?> command;
	protected Map<Class< ? >, SessionFactory> sessionFactories;
	protected Map<Class< ? >, Session> sessions = new HashMap<Class< ? >, Session>();
	protected TransactionContext transactionContext ;
	protected List<Throwable> exception = null;
	protected ProcessEngineConfigurationImpl processEngineConfigurationImpl;

	public CommandContext(Command<?> command, ProcessEngineConfigurationImpl processEngineConfigurationImpl) {
		this.command = command;
		this.processEngineConfigurationImpl = processEngineConfigurationImpl;
		sessionFactories = processEngineConfigurationImpl.getSessionFactories();
		this.transactionContext = processEngineConfigurationImpl.getTransactionContextFactory().openTransactionContext(this);
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
				throw new FoxBPMClassLoadingException("no session factory configured for " + sessionClass.getName());
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
	
	public VariableManager getVariableManager() {
		return getSession(VariableManager.class);
	}

	public TokenManager getTokenManager() {
		return getSession(TokenManager.class);
	}
	
	public HistoryManager getHistoryManager(){
		return getSession(HistoryManager.class);
	}
	
	public UserEntityManager getUserEntityManager(){
		return getSession(UserEntityManager.class);
	}

	public Command<?> getCommand() {
		return command;
	}
	
	public ISqlSession getSqlSession(){
		return getSession(ISqlSession.class);
	}
	
	public void flushSession(){
		for(Session session : sessions.values()) {
		    session.flush();
		}
	}
	
	public void close(){
		try{
			try{
				if(exception == null){
					flushSession();
				}
			}catch(Exception ex){
				exception(ex);
			}finally{
				
				try {
					if (exception == null) {
						transactionContext.commit();
					}
				} catch (Throwable exception) {
					exception(exception);
				}
				
				if(exception != null){
					transactionContext.rollback();
				}
			}
		}catch(Exception ex){
			exception(ex);
		}finally{
			closeSessions();
		}
		
		if(exception != null){
			for(Throwable e : exception){
				log.error("执行command出错：" + command,e);
			}
			throw new FoxBPMException("执行command出错：" + command.getClass(),exception.get(0));
		}
		
	}
	
	public void closeSessions(){
		for (Session session : sessions.values()) {
			try {
				session.close();
			} catch (Throwable exception) {
				exception(exception);
			}
		}
	}
	
	public void exception(Throwable e){
		if(exception == null){
			exception = new ArrayList<Throwable>();
		}
		exception.add(e);
	}
}
