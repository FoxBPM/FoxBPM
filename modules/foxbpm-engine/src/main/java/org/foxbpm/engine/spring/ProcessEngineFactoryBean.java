package org.foxbpm.engine.spring;

import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.impl.ProcessEngineConfigurationImpl;
import org.foxbpm.engine.impl.ProcessEngineImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ProcessEngineFactoryBean implements FactoryBean<ProcessEngine>,
		DisposableBean, ApplicationContextAware {

	// ADD ATTRIBUTE processEngineConfigurationDecrator BY MAENLIANG
	// AT 2014-06-08
	// 采用动态组合的形式构造processEngineConfiguration，从而创建processEngine
	// 避免，processEngineConfiguration存在多个扩展功能，且功能之间有组合时候
	// 创建大量的类似ProcessEngineConfigurationSpring这样的子类的情况
	protected ProcessEngineConfigurationDecrator processEngineConfigurationDecrator;
	protected ProcessEngineConfigurationImpl processEngineConfiguration;
	protected ApplicationContext applicationContext;
	protected ProcessEngineImpl processEngine;

	@Override
	public ProcessEngine getObject() throws Exception {
		new ProcessEngineConfigurationSpringDecrator(new ProcessEngineConfigurationSpringDecrator(new ProcessEngineConfigurationSpring())).buildProcessEngine();
		processEngine = (ProcessEngineImpl) processEngineConfiguration
				.buildProcessEngine();
		// 通过装饰对象创建processEgine
		// processEngine = (ProcessEngineImpl)
		// processEngineConfigurationDecrator
		// .buildProcessEngine();
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
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public void setProcessEngineConfiguration(
			ProcessEngineConfigurationImpl processEngineConfiguration) {
		this.processEngineConfiguration = processEngineConfiguration;
	}

	public ProcessEngineConfigurationDecrator getProcessEngineConfigurationDecrator() {
		return processEngineConfigurationDecrator;
	}

	public void setProcessEngineConfigurationDecrator(
			ProcessEngineConfigurationDecrator processEngineConfigurationDecrator) {
		this.processEngineConfigurationDecrator = processEngineConfigurationDecrator;
	}

}
