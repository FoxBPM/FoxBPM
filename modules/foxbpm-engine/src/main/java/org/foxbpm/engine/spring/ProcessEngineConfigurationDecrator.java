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

import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.ProcessEngineConfiguration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * ProcessEngineConfigurationImpl 扩展功能的父类
 * 采用动态组合的形式构造processEngineConfiguration，从而创建processEngine
 * 避免，processEngineConfiguration存在多个扩展功能，且功能之间有组合的时候
 * 创建大量的类似ProcessEngineConfigurationSpring这样的子类的情况
 * 
 * @author MAENLIANG
 * @date 2014-06-08
 */
public class ProcessEngineConfigurationDecrator extends
		ProcessEngineConfiguration {
	protected ProcessEngineConfiguration processEngineConfiguration;

	public ProcessEngineConfigurationDecrator(
			ProcessEngineConfiguration processEngineConfiguration) {
		this.processEngineConfiguration = processEngineConfiguration;
	}

	@Override
	public ProcessEngine buildProcessEngine() {
		return processEngineConfiguration.buildProcessEngine();
	}
}
