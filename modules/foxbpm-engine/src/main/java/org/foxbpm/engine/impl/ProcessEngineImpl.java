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
package org.foxbpm.engine.impl;

import org.foxbpm.engine.FormService;
import org.foxbpm.engine.HistoryService;
import org.foxbpm.engine.IdentityService;
import org.foxbpm.engine.ManagementService;
import org.foxbpm.engine.ModelService;
import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.RuntimeService;
import org.foxbpm.engine.ScheduleService;
import org.foxbpm.engine.TaskService;
import org.foxbpm.engine.impl.identity.Authentication;
public class ProcessEngineImpl implements ProcessEngine {

	protected String name;
	protected ModelService modelService;
	protected RuntimeService runtimeService;
	protected HistoryService historyService;
	protected IdentityService identityService;
	protected TaskService taskService;
	protected FormService formService;
	protected ScheduleService scheduleService;
	protected ManagementService managementService;

	protected ProcessEngineConfigurationImpl processEngineConfiguration;

	public ProcessEngineImpl(ProcessEngineConfigurationImpl processEngineConfiguration) {

		this.processEngineConfiguration = processEngineConfiguration;
		this.name = processEngineConfiguration.getProcessEngineName();
		this.modelService = processEngineConfiguration.getModelService();
		this.runtimeService = processEngineConfiguration.getRuntimeService();
		// this.historyService = processEngineConfiguration.getHistoryService();
		this.identityService = processEngineConfiguration.getIdentityService();
		this.taskService = processEngineConfiguration.getTaskService();
		// this.formService = processEngineConfiguration.getFormService();
		// this.scheduleService =
		// processEngineConfiguration.getScheduleService();
		// this.managementService =
		// processEngineConfiguration.getManagementService();
		ProcessEngineManagement.registerProcessEngine(this);
	}

	public String getName() {
		return name;
	}

	public FormService getFormService() {
		return formService;
	}

	public HistoryService getHistoryService() {
		return historyService;
	}

	public IdentityService getIdentityService() {
		return identityService;
	}

	public ModelService getModelService() {
		return modelService;
	}

	public RuntimeService getRuntimeService() {
		return runtimeService;
	}

	public ScheduleService getScheduleService() {
		return scheduleService;
	}

	public ManagementService getManagementService() {
		return managementService;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public ProcessEngineConfigurationImpl getProcessEngineConfiguration() {
		return processEngineConfiguration;
	}

	public void setExternalContent(ExternalContent externalContent) {
		String authenticatedUserId = externalContent.getAuthenticatedUserId();
		Authentication.setAuthenticatedUserId(authenticatedUserId);
		// String languageType = externalContent.getLanguageType();

		// if (externalContent.getConnectionManagement() != null &&
		// !externalContent.getConnectionManagement().equals("")) {
		// Context.setConnectionManagementDefault(externalContent.getConnectionManagement());
		// }

		// 国际化语言
		// if (languageType == null || languageType.equals("")) {
		//
		// } else {
		// processEngineConfiguration.getFixFlowResources().setNowLanguage(languageType);
		// }

	}

	public void contextClose() {
		Context.removeCommandContext();
		Context.removeProcessEngineConfiguration();
		// Context.removeAbstractScriptLanguageMgmt();
		// Context.removeLanguageType();
	}

	public void closeEngine() {
		try {
			// contextClose();
		} catch (Exception e) {
			// e.printStackTrace();
		}
		ProcessEngineManagement.unregister(this);
	}
}
