package org.foxbpm.engine.spring;

import org.foxbpm.engine.impl.ProcessEngineConfigurationImpl;
import org.springframework.transaction.PlatformTransactionManager;

public class ProcessEngineConfigurationSpring extends ProcessEngineConfigurationImpl {

	protected PlatformTransactionManager transactionManager;

	@Override
	protected void initTransactionContextFactory() {
		if (transactionContextFactory == null && transactionManager != null) {
			transactionContextFactory = new SpringTransactionContextFactory(transactionManager);
		}
	}

	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

}
