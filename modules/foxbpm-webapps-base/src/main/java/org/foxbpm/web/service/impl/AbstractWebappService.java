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
 * @author yangguangftlp
 */
package org.foxbpm.web.service.impl;

import org.foxbpm.engine.ModelService;
import org.foxbpm.engine.RuntimeService;
import org.foxbpm.engine.TaskService;
import org.foxbpm.web.db.factory.FoxbpmDBConnectionFactory;
import org.foxbpm.web.db.interfaces.BizDBInterface;
import org.foxbpm.web.db.interfaces.IDemoDao;

/**
 * webapp抽象服务
 * @author yangguangftlp
 * @date 2014年6月11日
 */
public abstract class AbstractWebappService {
	protected BizDBInterface bizDB;
	protected FoxbpmDBConnectionFactory dbfactory;
	protected TaskService taskService;
	protected ModelService modelService;
	protected RuntimeService runtimeService;
	protected IDemoDao idemoDao;

	public BizDBInterface getBizDB() {
		return bizDB;
	}

	public void setBizDB(BizDBInterface bizDB) {
		this.bizDB = bizDB;
	}

	public FoxbpmDBConnectionFactory getDbfactory() {
		return dbfactory;
	}

	public void setDbfactory(FoxbpmDBConnectionFactory dbfactory) {
		this.dbfactory = dbfactory;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public ModelService getModelService() {
		return modelService;
	}

	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}

	public RuntimeService getRuntimeService() {
		return runtimeService;
	}

	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	public IDemoDao getIdemoDao() {
		return idemoDao;
	}

	public void setIdemoDao(IDemoDao idemoDao) {
		this.idemoDao = idemoDao;
	}
}
