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
 * @author MAENLIANG
 */
package org.foxbpm.engine.spring;

import org.foxbpm.engine.ProcessEngineConfiguration;
import org.foxbpm.engine.impl.ProcessEngineConfigurationImpl;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 
 * 为ProcessEngineConfigurationImpl动态装配 spring事物功能
 * 
 * @author MAENLIANG
 * @date 2014-06-08
 */
public class ProcessEngineConfigurationSpringDecrator extends
		ProcessEngineConfigurationDecrator {
	protected PlatformTransactionManager springTransactionManager;

	public ProcessEngineConfigurationSpringDecrator(
			ProcessEngineConfiguration processEngineConfiguration) {
		super(processEngineConfiguration);
		if (processEngineConfiguration instanceof ProcessEngineConfigurationImpl) {
			((ProcessEngineConfigurationImpl) processEngineConfiguration)
					.setTransactionContextFactory(new SpringTransactionContextFactory(
							springTransactionManager));
		}
	}

	public PlatformTransactionManager getSpringTransactionManager() {
		return springTransactionManager;
	}

	public void setSpringTransactionManager(
			PlatformTransactionManager springTransactionManager) {
		this.springTransactionManager = springTransactionManager;
	}

}
