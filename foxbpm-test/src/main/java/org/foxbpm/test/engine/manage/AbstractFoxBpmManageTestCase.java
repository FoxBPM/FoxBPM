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
 * @author ych
 */
package org.foxbpm.test.engine.manage;


import java.sql.SQLException;

import junit.framework.TestCase;

import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.ProcessEngineConfigurationImpl;
import org.foxbpm.engine.impl.ProcessEngineImpl;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.spring.ProcessEngineConfigrationImplSpring;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * foxbpm manage测试基类，使用spring控制事务，并初始化了commandContext类
 * @author ych
 *
 */
public abstract class AbstractFoxBpmManageTestCase extends TestCase {

	public static ProcessEngine processEngine;
	protected String deploymentId;

	protected ProcessEngineConfigurationImpl processEngineConfiguration;
	
	protected CommandContext commandContext;

	protected void initializeServices() {
		ProcessEngineConfigrationImplSpring processEngineConfigrationImplSpring = new ProcessEngineConfigrationImplSpring();
		processEngine = processEngineConfigrationImplSpring.setProcessEngineName("spring").buildProcessEngine();
		processEngineConfiguration = ((ProcessEngineImpl) processEngine).getProcessEngineConfiguration();
		commandContext = new CommandContext(null, processEngine.getProcessEngineConfiguration());
		Context.setCommandContext(commandContext);
	}
	
	public void runBare() throws Throwable {

		initializeServices();
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(processEngine.getProcessEngineConfiguration().getDataSourceManage().getDataSource());
		TransactionTemplate t = new TransactionTemplate(dataSourceTransactionManager);
		t.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				execute();
				commandContext.flushSession();
				try {
					processEngine.getProcessEngineConfiguration().getDataSourceManage().getDataSource().getConnection().rollback();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
	
	public void execute(){
		try {
			super.runBare();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
}
