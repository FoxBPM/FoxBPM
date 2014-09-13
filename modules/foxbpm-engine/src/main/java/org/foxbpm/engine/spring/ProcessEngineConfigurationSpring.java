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
package org.foxbpm.engine.spring;

import javax.sql.DataSource;

import org.foxbpm.engine.ProcessEngineConfiguration;
import org.foxbpm.engine.impl.ProcessEngineConfigurationImpl;
import org.foxbpm.engine.impl.interceptor.CommandInterceptor;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * spring 引擎初始化配置类
 * 在这里将事务托管给spring处理
 * @author ych
 *
 */
public class ProcessEngineConfigurationSpring extends ProcessEngineConfigurationImpl {

	protected PlatformTransactionManager transactionManager;

	@Override
	protected void initTransactionContextFactory() {
		if (transactionContextFactory == null && transactionManager != null) {
			transactionContextFactory = new SpringTransactionContextFactory(transactionManager);
		}
	}
	
	@Override
	  public ProcessEngineConfiguration setDataSource(DataSource dataSource) {
	    if (dataSource instanceof TransactionAwareDataSourceProxy) {
	      return super.setDataSource(dataSource);
	    } else {
	      DataSource proxiedDataSource = new TransactionAwareDataSourceProxy(dataSource);
	      return super.setDataSource(proxiedDataSource);
	    }
	  }

	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	@Override
	protected CommandInterceptor createTransactionInterceptor() {
		return new SpringTransactionInterceptor(transactionManager);
	}
	
	

}
