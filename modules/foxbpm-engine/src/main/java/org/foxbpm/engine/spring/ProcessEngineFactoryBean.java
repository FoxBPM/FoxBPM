package org.foxbpm.engine.spring;

import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.impl.ProcessEngineConfigurationImpl;
import org.foxbpm.engine.impl.ProcessEngineImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ProcessEngineFactoryBean implements FactoryBean<ProcessEngine>, DisposableBean, ApplicationContextAware {

	protected ProcessEngineConfigurationImpl processEngineConfiguration;
	protected ApplicationContext applicationContext;
	protected ProcessEngineImpl processEngine;

	@Override
	public ProcessEngine getObject() throws Exception {
		processEngine = (ProcessEngineImpl) processEngineConfiguration.buildProcessEngine();
		return processEngine;
	}

	@Override
	public void destroy() throws Exception {
		if (processEngine != null) {
			processEngine.closeEngine();
		}
	}

	@Override
	public Class<?> getObjectType() {
		return ProcessEngine.class;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public void setProcessEngineConfiguration(ProcessEngineConfigurationImpl processEngineConfiguration) {
		this.processEngineConfiguration = processEngineConfiguration;
	}

}
