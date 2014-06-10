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

import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.impl.ProcessEngineConfigurationImpl;
import org.foxbpm.engine.impl.ProcessEngineImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * spring引擎创建工厂
 * @author Administrator
 *
 */
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
		processEngine = (ProcessEngineImpl) processEngineConfiguration.setProcessEngineName(ProcessEngineManagement.NAME_DEFAULT)
				.buildProcessEngine();
		ProcessEngineManagement.setInit();
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
