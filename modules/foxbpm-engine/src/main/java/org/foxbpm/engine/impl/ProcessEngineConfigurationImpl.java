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

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.foxbpm.engine.ModelService;
import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.ProcessEngineConfiguration;
import org.foxbpm.engine.RuntimeService;
import org.foxbpm.engine.TaskService;
import org.foxbpm.engine.db.DataSourceManage;
import org.foxbpm.engine.exception.ExceptionCode;
import org.foxbpm.engine.exception.ExceptionI18NCore;
import org.foxbpm.engine.exception.FoxBPMClassLoadingException;
import org.foxbpm.engine.impl.bpmn.deployer.BpmnDeployer;
import org.foxbpm.engine.impl.db.DefaultDataSourceManage;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.interceptor.CommandContextFactory;
import org.foxbpm.engine.impl.interceptor.CommandContextInterceptor;
import org.foxbpm.engine.impl.interceptor.CommandExecutor;
import org.foxbpm.engine.impl.interceptor.CommandExecutorImpl;
import org.foxbpm.engine.impl.interceptor.CommandInterceptor;
import org.foxbpm.engine.impl.interceptor.LogInterceptor;
import org.foxbpm.engine.impl.mybatis.MyBatisSqlSessionFactory;
import org.foxbpm.engine.impl.persistence.deploy.DefaultDeploymentCache;
import org.foxbpm.engine.impl.persistence.deploy.Deployer;
import org.foxbpm.engine.impl.persistence.deploy.DeploymentCache;
import org.foxbpm.engine.impl.persistence.deploy.DeploymentManager;
import org.foxbpm.engine.impl.util.ReflectUtil;
import org.foxbpm.engine.modelparse.ProcessModelParseHandler;
import org.foxbpm.engine.sqlsession.ISqlSessionFactory;
import org.foxbpm.model.config.foxbpmconfig.FoxBPMConfig;
import org.foxbpm.model.config.foxbpmconfig.FoxBPMConfigPackage;
import org.foxbpm.model.config.foxbpmconfig.ResourcePath;
import org.foxbpm.model.config.foxbpmconfig.ResourcePathConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessEngineConfigurationImpl extends ProcessEngineConfiguration {

	private static Logger log = LoggerFactory.getLogger(ProcessEngineConfigurationImpl.class);
	protected CommandInterceptor commandExecutor;
	protected CommandContextFactory commandContextFactory;
	protected List<CommandInterceptor> commandInterceptors;
	protected FoxBPMConfig foxBpmConfig;
	protected ResourcePathConfig resourcePathConfig;
	//service
	protected ModelService modelService = new ModelServiceImpl();
	protected RuntimeService runtimeService = new RuntimeServiceImpl();
	protected TaskService taskService = new TaskServiceImpl();
	protected ISqlSessionFactory sqlSessionFactory;
	protected DataSourceManage dataSourceManage;
	// 定义及发布
	protected int processDefinitionCacheLimit = -1; // By default, no limit
	protected DeploymentCache<ProcessDefinitionEntity> processDefinitionCache;

	protected int knowledgeBaseCacheLimit = -1;
	protected DeploymentCache<Object> knowledgeBaseCache;

	protected BpmnDeployer bpmnDeployer;
	protected ProcessModelParseHandler processModelParseHandler;
	protected List<Deployer> customPreDeployers;
	protected List<Deployer> customPostDeployers;
	protected List<Deployer> deployers;
	protected DeploymentManager deploymentManager;

	public ProcessEngine buildProcessEngine() {
		init();
		return new ProcessEngineImpl(this);
	}

	protected void init() {
		initExceptionResource();
		initEmfFile();
		initResourcePathConfig();
		// initDataVariableConfig();
		initDataSourceManage();
		initSqlSessionFactory();
		initCommandContextFactory();
		initCommandExecutors();
		initServices();
		initDeployers();
		// initDataBaseTable();
		// initCache();
		// initDeployers();
		// initGroupDefinitions();
		// initDbConfig();// dbType
		// // 任务命令配置加载
		// initTaskCommandConfig();
		//
		// initImportDataVariableConfig();
		//
		// initQuartz();
		// initUserDefinition();
		// initSysMailConfig();
		// initExpandClassConfig();
		// initEventSubscriptionConfig();
		// initMessageSubscription();
		// initScriptLanguageConfig();
		// initInternationalizationConfig();
		// initFixFlowResources();
		// initPigeonholeConfig();
		// initExpandCmdConfig();
		// initAbstractCommandFilter();
		// initBizData();
		// initPriorityConfig();
		// initAssignPolicyConfig();
		// initThreadPool();

	}

	protected void initDeployers() {
		if (this.deployers == null) {
			this.deployers = new ArrayList<Deployer>();
			if (customPreDeployers != null) {
				this.deployers.addAll(customPreDeployers);
			}
			this.deployers.addAll(getDefaultDeployers());
			if (customPostDeployers != null) {
				this.deployers.addAll(customPostDeployers);
			}
		}
		if (deploymentManager == null) {
			deploymentManager = new DeploymentManager();
			deploymentManager.setDeployers(deployers);

			// Process Definition cache
			if (processDefinitionCache == null) {
				if (processDefinitionCacheLimit <= 0) {
					processDefinitionCache = new DefaultDeploymentCache<ProcessDefinitionEntity>();
				} else {
					processDefinitionCache = new DefaultDeploymentCache<ProcessDefinitionEntity>(processDefinitionCacheLimit);
				}
			}

			// Knowledge base cache (used for Drools business task)
			if (knowledgeBaseCache == null) {
				if (knowledgeBaseCacheLimit <= 0) {
					knowledgeBaseCache = new DefaultDeploymentCache<Object>();
				} else {
					knowledgeBaseCache = new DefaultDeploymentCache<Object>(knowledgeBaseCacheLimit);
				}
			}

			deploymentManager.setProcessDefinitionCache(processDefinitionCache);
			deploymentManager.setKnowledgeBaseCache(knowledgeBaseCache);
		}
	}

	protected Collection<? extends Deployer> getDefaultDeployers() {
		List<Deployer> defaultDeployers = new ArrayList<Deployer>();

		if (bpmnDeployer == null) {
			bpmnDeployer = new BpmnDeployer();
		}

		if (processModelParseHandler == null) {
			processModelParseHandler = (ProcessModelParseHandler)ReflectUtil.instantiate("org.foxbpm.engine.impl.bpmn.parser.BpmnParseHandlerImpl");
		}
		bpmnDeployer.setProcessModelParseHandler(processModelParseHandler);
		defaultDeployers.add(bpmnDeployer);
		return defaultDeployers;
	}

	public void initDataSourceManage() {
		if (dataSourceManage == null) {
			dataSourceManage = new DefaultDataSourceManage();
		}
		dataSourceManage.init();
	}

	public void initExceptionResource() {
		ExceptionI18NCore.system_init("I18N/exception");
	}

	protected void initEmfFile() {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xml", new XMIResourceFactoryImpl());
		InputStream inputStream = null;
		String classPath = "config/foxbpm.cfg.xml";
		inputStream = ReflectUtil.getResourceAsStream("foxbpm.cfg.xml");
		if (inputStream != null) {
			classPath = "foxbpm.cfg.xml";
			log.info("开始从classes根目录加载foxbpm.cfg.xml文件");
		} else {
			log.info("开始从classes/config/foxbpm.cfg.xml目录加载foxbpm.cfg.xml文件");
		}
		URL url = this.getClass().getClassLoader().getResource(classPath);
		if (url == null) {
			log.error("未能从{}目录下找到foxbpm.cfg.xml文件", classPath);
			throw new FoxBPMClassLoadingException(ExceptionCode.CLASSLOAD_EXCEPTION_FILENOTFOUND, "foxbpm.cfg.xml");
		}
		String filePath = url.toString();
		Resource resource = null;
		try {
			if (!filePath.startsWith("jar")) {
				filePath = java.net.URLDecoder.decode(ReflectUtil.getResource(classPath).getFile(), "utf-8");
				resource = resourceSet.createResource(URI.createFileURI(filePath));
			} else {
				resource = resourceSet.createResource(URI.createURI(filePath));
			}
			resourceSet.getPackageRegistry().put(FoxBPMConfigPackage.eINSTANCE.getNsURI(), FoxBPMConfigPackage.eINSTANCE);
			resource.load(null);
		} catch (Exception e) {
			log.error("fixflowconfig.xml文件加载失败", e);
			throw new FoxBPMClassLoadingException(ExceptionCode.CLASSLOAD_EXCEPTION, "fixflowconfig.xml", e);
		}

		foxBpmConfig = (FoxBPMConfig) resource.getContents().get(0);

	}

	public void initSqlSessionFactory() {
		DataSource dataSource = dataSourceManage.getDataSource();
		sqlSessionFactory = new MyBatisSqlSessionFactory();
		sqlSessionFactory.init(dataSource);
	}

	public ISqlSessionFactory getSqlSessionFactory() {
		return this.sqlSessionFactory;
	}

	protected void initServices() {
		initService(modelService);
		initService(runtimeService);
		initService(taskService);
	}

	protected void initService(Object service) {
		CommandExecutor executor = initInterceptorChain(commandInterceptors);
		if (service instanceof ServiceImpl) {
			((ServiceImpl) service).setCommandExecutor(executor);
		}
	}

	protected void initCommandContextFactory() {
		if (commandContextFactory == null) {
			commandContextFactory = new CommandContextFactory();
			commandContextFactory.setProcessEngineConfiguration(this);
		}
	}

	public void initBaseCommandInterceptors() {
		commandInterceptors = new ArrayList<CommandInterceptor>();
		CommandContextInterceptor commandContextInterceptor = new CommandContextInterceptor(commandContextFactory, this);
		commandInterceptors.add(commandContextInterceptor);
	}

	protected void initCommandExecutors() {
		initBaseCommandInterceptors();
		initCustomInterceptors();
		initGeneralCommandExecutor();
	}

	public void initCustomInterceptors() {
		// 这只是个示例，正常应从配置文件读取拦截器
		commandInterceptors.add(new LogInterceptor());
	}

	public void initGeneralCommandExecutor() {
		commandInterceptors.add(new CommandExecutorImpl());
	}

	protected CommandInterceptor initInterceptorChain(List<CommandInterceptor> chain) {
		for (int i = 0; i < chain.size() - 1; i++) {
			chain.get(i).setNext(chain.get(i + 1));
		}
		return chain.get(0);
	}

	private void initResourcePathConfig() {
		resourcePathConfig = foxBpmConfig.getResourcePathConfig();
	}

	// Getter方法

	public ModelService getModelService() {
		return modelService;
	}

	public RuntimeService getRuntimeService() {
		return runtimeService;
	}
	
	public TaskService getTaskService(){
		return taskService;
	}

	public DataSourceManage getDataSourceManage() {
		return dataSourceManage;
	}

	public FoxBPMConfig getFoxBpmConfig() {
		return foxBpmConfig;
	}

	public String getInternationPath() {
		return getResourcePath("internationalization").getSrc();
	}

	public ResourcePath getResourcePath(String resourceId) {
		List<ResourcePath> resourcePaths = this.resourcePathConfig.getResourcePath();
		for (ResourcePath resourcePath : resourcePaths) {
			if (resourcePath.getId().equals(resourceId)) {
				return resourcePath;
			}
		}
		return null;
	}

	public String getNoneTemplateFilePath() {
		return "fixflowfile.bpmn";
	}

	
	public DeploymentManager getDeploymentManager() {
		return deploymentManager;
	}

	public void setDeploymentManager(DeploymentManager deploymentManager) {
		this.deploymentManager = deploymentManager;
	}
}
