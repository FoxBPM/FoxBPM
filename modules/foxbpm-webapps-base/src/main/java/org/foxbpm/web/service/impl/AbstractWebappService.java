package org.foxbpm.web.service.impl;

import org.foxbpm.engine.ModelService;
import org.foxbpm.engine.RuntimeService;
import org.foxbpm.engine.TaskService;
import org.foxbpm.web.db.factory.FoxbpmDBConnectionFactory;
import org.foxbpm.web.db.interfaces.BizDBInterface;
import org.foxbpm.web.db.interfaces.IDemoDao;

/**
 * webappService抽象服务
 * 
 * @author Administrator
 * 
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
