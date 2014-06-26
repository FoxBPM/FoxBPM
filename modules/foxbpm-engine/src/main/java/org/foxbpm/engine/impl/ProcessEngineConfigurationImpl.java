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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.foxbpm.engine.IdentityService;
import org.foxbpm.engine.ModelService;
import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.ProcessEngineConfiguration;
import org.foxbpm.engine.RuntimeService;
import org.foxbpm.engine.TaskService;
import org.foxbpm.engine.cache.Cache;
import org.foxbpm.engine.db.DataSourceManage;
import org.foxbpm.engine.exception.ExceptionCode;
import org.foxbpm.engine.exception.ExceptionI18NCore;
import org.foxbpm.engine.exception.FoxBPMClassLoadingException;
import org.foxbpm.engine.identity.GroupDefinition;
import org.foxbpm.engine.identity.User;
import org.foxbpm.engine.impl.bpmn.deployer.AbstractDeployer;
import org.foxbpm.engine.impl.bpmn.deployer.BpmnDeployer;
import org.foxbpm.engine.impl.cache.DefaultCache;
import org.foxbpm.engine.impl.db.DefaultDataSourceManage;
import org.foxbpm.engine.impl.diagramview.svg.SVGTemplateContainer;
import org.foxbpm.engine.impl.identity.GroupDeptImpl;
import org.foxbpm.engine.impl.identity.GroupRoleImpl;
import org.foxbpm.engine.impl.interceptor.CommandContextFactory;
import org.foxbpm.engine.impl.interceptor.CommandContextInterceptor;
import org.foxbpm.engine.impl.interceptor.CommandExecutor;
import org.foxbpm.engine.impl.interceptor.CommandExecutorImpl;
import org.foxbpm.engine.impl.interceptor.CommandInterceptor;
import org.foxbpm.engine.impl.interceptor.CommandInvoker;
import org.foxbpm.engine.impl.interceptor.LogInterceptor;
import org.foxbpm.engine.impl.interceptor.SessionFactory;
import org.foxbpm.engine.impl.mybatis.MyBatisSqlSessionFactory;
import org.foxbpm.engine.impl.persistence.AgentManager;
import org.foxbpm.engine.impl.persistence.DeploymentEntityManager;
import org.foxbpm.engine.impl.persistence.GenericManagerFactory;
import org.foxbpm.engine.impl.persistence.IdentityLinkManager;
import org.foxbpm.engine.impl.persistence.ProcessDefinitionManager;
import org.foxbpm.engine.impl.persistence.ProcessInstanceManager;
import org.foxbpm.engine.impl.persistence.ResourceManager;
import org.foxbpm.engine.impl.persistence.TaskManager;
import org.foxbpm.engine.impl.persistence.TokenManager;
import org.foxbpm.engine.impl.persistence.UserEntityManagerFactory;
import org.foxbpm.engine.impl.persistence.VariableManager;
import org.foxbpm.engine.impl.persistence.deploy.Deployer;
import org.foxbpm.engine.impl.persistence.deploy.DeploymentManager;
import org.foxbpm.engine.impl.schedule.FoxbpmScheduler;
import org.foxbpm.engine.impl.transaction.DefaultTransactionContextFactory;
import org.foxbpm.engine.impl.util.ReflectUtil;
import org.foxbpm.engine.modelparse.ProcessModelParseHandler;
import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.engine.sqlsession.ISqlSessionFactory;
import org.foxbpm.engine.transaction.TransactionContextFactory;
import org.foxbpm.model.config.foxbpmconfig.FoxBPMConfig;
import org.foxbpm.model.config.foxbpmconfig.FoxBPMConfigPackage;
import org.foxbpm.model.config.foxbpmconfig.ResourcePath;
import org.foxbpm.model.config.foxbpmconfig.ResourcePathConfig;
import org.foxbpm.model.config.foxbpmconfig.TaskCommandConfig;
import org.foxbpm.model.config.foxbpmconfig.TaskCommandDefinition;
import org.foxbpm.model.config.style.ElementStyle;
import org.foxbpm.model.config.style.FoxBPMStyleConfig;
import org.foxbpm.model.config.style.Style;
import org.foxbpm.model.config.style.StylePackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessEngineConfigurationImpl extends ProcessEngineConfiguration {
	private static Logger log = LoggerFactory
			.getLogger(ProcessEngineConfigurationImpl.class);
	protected CommandExecutor commandExecutor;
	protected CommandContextFactory commandContextFactory;
	protected List<CommandInterceptor> commandInterceptors;
	protected FoxBPMConfig foxBpmConfig;
	protected ResourcePathConfig resourcePathConfig;
	// service
	protected ModelService modelService = new ModelServiceImpl();
	protected RuntimeService runtimeService = new RuntimeServiceImpl();
	protected TaskService taskService = new TaskServiceImpl();
	protected IdentityService identityService = new IdentityServiceImpl();
	protected ISqlSessionFactory sqlSessionFactory;
	protected Map<Class<?>, SessionFactory> sessionFactories;
	protected DataSourceManage dataSourceManager;
	// 定义及发布
	protected int processDefinitionCacheLimit = -1; // By default, no limit
	protected Cache<ProcessDefinition> processDefinitionCache;

	protected int knowledgeBaseCacheLimit = -1;
	protected Cache<ProcessDefinition> knowledgeBaseCache;

	protected int userProcessDefinitionCacheLimit = -1;
	protected Cache<Object> userProcessDefinitionCache;

	protected int userCacheLimit = -1;
	protected Cache<User> userCache;

	protected AbstractDeployer bpmnDeployer;
	protected ProcessModelParseHandler processModelParseHandler;
	protected List<Deployer> customPreDeployers;
	protected List<Deployer> customPostDeployers;
	protected List<Deployer> deployers;
	protected DeploymentManager deploymentManager;

	protected TransactionContextFactory transactionContextFactory;
	protected List<GroupDefinition> groupDefinitions;
	protected FoxBPMStyleConfig foxBPMStyleConfig;

	protected Map<String, Style> styleMap = new HashMap<String, Style>();

	protected TaskCommandConfig taskCommandConfig;

	protected Map<String, TaskCommandDefinition> taskCommandDefinitionMap;

	/**
	 * FOXBPM任务调度器
	 */
	protected FoxbpmScheduler foxbpmScheduler;

	public ProcessEngine buildProcessEngine() {
		init();
		return new ProcessEngineImpl(this);
	}

	protected void init() {
		initExceptionResource();
		initEmfFile();
		initCache();
		initResourcePathConfig();
		initDataSourceManage();
		initSqlSessionFactory();
		initCommandContextFactory();
		initCommandExecutors();
		initServices();
		initSessionFactories();
		initDeployers();
		initGroupDefinitions();
		initTransactionContextFactory();
		// initDbConfig();// dbType
		// // 任务命令配置加载
		initTaskCommandConfig();
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
		// 加载主题样式文件
		initStyle();
		// 加载SVG模版资源
		SVGTemplateContainer.getContainerInstance();
	}

	private void initStyle() {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put("xml", new XMIResourceFactoryImpl());
		InputStream inputStream = null;
		String classPath = "config/style.xml";
		inputStream = ReflectUtil.getResourceAsStream("style.xml");
		if (inputStream != null) {
			classPath = "foxbpm.cfg.xml";
			log.info("开始从classes根目录加载style.xml文件");
		} else {
			log.info("开始从classes/config/目录加载style.xml文件");
		}
		URL url = this.getClass().getClassLoader().getResource(classPath);
		if (url == null) {
			log.error("未能从{}目录下找到style.xml文件", classPath);
			throw new FoxBPMClassLoadingException(
					ExceptionCode.CLASSLOAD_EXCEPTION_FILENOTFOUND, "style.xml");
		}
		String filePath = url.toString();
		Resource resource = null;
		try {
			if (!filePath.startsWith("jar")) {
				filePath = java.net.URLDecoder.decode(
						ReflectUtil.getResource(classPath).getFile(), "utf-8");
				resource = resourceSet.createResource(URI
						.createFileURI(filePath));
			} else {
				resource = resourceSet.createResource(URI.createURI(filePath));
			}
			resourceSet.getPackageRegistry().put(
					StylePackage.eINSTANCE.getNsURI(), StylePackage.eINSTANCE);
			resource.load(null);
		} catch (Exception e) {
			log.error("style.xml文件加载失败", e);
			throw new FoxBPMClassLoadingException(
					ExceptionCode.CLASSLOAD_EXCEPTION, "style.xml", e);
		}

		foxBPMStyleConfig = (FoxBPMStyleConfig) resource.getContents().get(0);

		EList<ElementStyle> elementStyleList = foxBPMStyleConfig
				.getElementStyleConfig().getElementStyle();

		for (ElementStyle elementStyle : elementStyleList) {

			for (Style style : elementStyle.getStyle()) {
				String key = elementStyle.getStyleId() + style.getObject();
				styleMap.put(key, style);
			}
		}
	}

	protected void initTaskCommandConfig() {
		this.taskCommandConfig = foxBpmConfig.getTaskCommandConfig();
		taskCommandDefinitionMap = new HashMap<String, TaskCommandDefinition>();
		for (TaskCommandDefinition taskCommandDef : taskCommandConfig
				.getTaskCommandDefinition()) {
			String id = taskCommandDef.getId();
			taskCommandDefinitionMap.put(id, taskCommandDef);
		}
	}

	protected void initTransactionContextFactory() {
		if (transactionContextFactory == null) {
			transactionContextFactory = new DefaultTransactionContextFactory();
		}
	}

	protected void initGroupDefinitions() {
		if (groupDefinitions == null) {
			groupDefinitions = new ArrayList<GroupDefinition>();
			groupDefinitions.add(new GroupDeptImpl());
			groupDefinitions.add(new GroupRoleImpl());
		}
	}

	protected void initCache() {
		// userCache
		if (userCache == null) {
			if (userCacheLimit <= 0) {
				userCache = new DefaultCache<User>();
			} else {
				userCache = new DefaultCache<User>(userCacheLimit);
			}
		}
		// Process Definition cache
		if (processDefinitionCache == null) {
			if (processDefinitionCacheLimit <= 0) {
				processDefinitionCache = new DefaultCache<ProcessDefinition>();
			} else {
				processDefinitionCache = new DefaultCache<ProcessDefinition>(
						processDefinitionCacheLimit);
			}
		}
		// Knowledge base cache (used for Drools business task)
		if (knowledgeBaseCache == null) {
			if (knowledgeBaseCacheLimit <= 0) {
				knowledgeBaseCache = new DefaultCache<ProcessDefinition>();
			} else {
				knowledgeBaseCache = new DefaultCache<ProcessDefinition>(
						knowledgeBaseCacheLimit);
			}
		}
		if (userProcessDefinitionCache == null) {
			if (userProcessDefinitionCacheLimit <= 0) {
				userProcessDefinitionCache = new DefaultCache<Object>();
			} else {
				userProcessDefinitionCache = new DefaultCache<Object>(
						userProcessDefinitionCacheLimit);
			}
		}
	}

	protected void initSessionFactories() {
		if (sessionFactories == null) {
			sessionFactories = new HashMap<Class<?>, SessionFactory>();
			addSessionFactory(sqlSessionFactory);
			addSessionFactory(new GenericManagerFactory(TaskManager.class));
			addSessionFactory(new GenericManagerFactory(
					ProcessInstanceManager.class));
			addSessionFactory(new GenericManagerFactory(TokenManager.class));
			addSessionFactory(new GenericManagerFactory(
					DeploymentEntityManager.class));
			addSessionFactory(new GenericManagerFactory(
					ProcessDefinitionManager.class));
			addSessionFactory(new GenericManagerFactory(ResourceManager.class));
			addSessionFactory(new GenericManagerFactory(
					IdentityLinkManager.class));
			addSessionFactory(new GenericManagerFactory(VariableManager.class));
			addSessionFactory(new GenericManagerFactory(AgentManager.class));
			addSessionFactory(new UserEntityManagerFactory());
		}
	}

	protected void addSessionFactory(SessionFactory sessionFactory) {
		sessionFactories.put(sessionFactory.getSessionType(), sessionFactory);
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
			deploymentManager.setProcessDefinitionCache(processDefinitionCache);
			deploymentManager.setKnowledgeBaseCache(knowledgeBaseCache);
		}
	}

	protected Collection<? extends Deployer> getDefaultDeployers() {
		List<Deployer> defaultDeployers = new ArrayList<Deployer>();

		if (bpmnDeployer == null) {
			// 添加部署的时候自动启动流程实例 功能，修改时间 2014-06-24
			bpmnDeployer = new BpmnDeployer();
		}

		if (processModelParseHandler == null) {
			processModelParseHandler = (ProcessModelParseHandler) ReflectUtil
					.instantiate("org.foxbpm.engine.impl.bpmn.parser.BpmnParseHandlerImpl");
		}
		bpmnDeployer.setProcessModelParseHandler(processModelParseHandler);
		defaultDeployers.add(bpmnDeployer);
		return defaultDeployers;
	}

	public void initDataSourceManage() {
		if (dataSourceManager == null) {
			dataSourceManager = new DefaultDataSourceManage();
		}
		dataSourceManager.init();
	}

	public void initExceptionResource() {
		ExceptionI18NCore.system_init("I18N/exception");
	}

	protected void initEmfFile() {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put("xml", new XMIResourceFactoryImpl());
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
			throw new FoxBPMClassLoadingException(
					ExceptionCode.CLASSLOAD_EXCEPTION_FILENOTFOUND,
					"foxbpm.cfg.xml");
		}
		String filePath = url.toString();
		Resource resource = null;
		try {
			if (!filePath.startsWith("jar")) {
				filePath = java.net.URLDecoder.decode(
						ReflectUtil.getResource(classPath).getFile(), "utf-8");
				resource = resourceSet.createResource(URI
						.createFileURI(filePath));
			} else {
				resource = resourceSet.createResource(URI.createURI(filePath));
			}
			resourceSet.getPackageRegistry().put(
					FoxBPMConfigPackage.eINSTANCE.getNsURI(),
					FoxBPMConfigPackage.eINSTANCE);
			resource.load(null);
		} catch (Exception e) {
			log.error("fixflowconfig.xml文件加载失败", e);
			throw new FoxBPMClassLoadingException(
					ExceptionCode.CLASSLOAD_EXCEPTION, "fixflowconfig.xml", e);
		}

		foxBpmConfig = (FoxBPMConfig) resource.getContents().get(0);

	}

	public void initSqlSessionFactory() {
		DataSource dataSource = dataSourceManager.getDataSource();
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
		initService(identityService);
	}

	protected void initService(Object service) {
		if (service instanceof ServiceImpl) {
			((ServiceImpl) service).setCommandExecutor(commandExecutor);
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
		commandInterceptors.add(new LogInterceptor());

		CommandInterceptor transactionInterceptor = createTransactionInterceptor();
		if (transactionInterceptor != null) {
			commandInterceptors.add(transactionInterceptor);
		}
		CommandContextInterceptor commandContextInterceptor = new CommandContextInterceptor(
				commandContextFactory, this);
		commandInterceptors.add(commandContextInterceptor);
		commandInterceptors.add(new CommandInvoker());
	}

	protected CommandInterceptor createTransactionInterceptor() {
		return null;
	}

	protected void initCommandExecutors() {
		initBaseCommandInterceptors();
		initCustomInterceptors();
		initGeneralCommandExecutor();
	}

	public void initCustomInterceptors() {
		// 这只是个示例，正常应从配置文件读取拦截器

	}

	public void initGeneralCommandExecutor() {
		CommandInterceptor first = initInterceptorChain(commandInterceptors);
		commandExecutor = new CommandExecutorImpl(first);
	}

	protected CommandInterceptor initInterceptorChain(
			List<CommandInterceptor> chain) {
		for (int i = 0; i < chain.size() - 1; i++) {
			chain.get(i).setNext(chain.get(i + 1));
		}
		return chain.get(0);
	}

	private void initResourcePathConfig() {
		resourcePathConfig = foxBpmConfig.getResourcePathConfig();
	}

	// Setter

	public void setDataSourceManager(DataSourceManage dataSourceManager) {
		this.dataSourceManager = dataSourceManager;
	}

	// Getter方法

	public ModelService getModelService() {
		return modelService;
	}

	public ResourcePathConfig getResourcePathConfig() {
		return resourcePathConfig;
	}

	public TransactionContextFactory getTransactionContextFactory() {
		return transactionContextFactory;
	}

	public void setTransactionContextFactory(
			TransactionContextFactory transactionFactory) {
		this.transactionContextFactory = transactionFactory;
	}

	public List<GroupDefinition> getGroupDefinitions() {
		return groupDefinitions;
	}

	public void setGroupDefinitions(List<GroupDefinition> groupDefinitions) {
		this.groupDefinitions = groupDefinitions;
	}

	public RuntimeService getRuntimeService() {
		return runtimeService;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public IdentityService getIdentityService() {
		return identityService;
	}

	public DataSourceManage getDataSourceManager() {
		return dataSourceManager;
	}

	public FoxBPMConfig getFoxBpmConfig() {
		return foxBpmConfig;
	}

	public Map<Class<?>, SessionFactory> getSessionFactories() {
		return sessionFactories;
	}

	public Cache<User> getUserCache() {
		return userCache;
	}

	public int getUserCacheLimit() {
		return userCacheLimit;
	}

	public void setUserCacheLimit(int userCacheLimit) {
		this.userCacheLimit = userCacheLimit;
	}

	public int getUserProcessDefinitionCacheLimit() {
		return userProcessDefinitionCacheLimit;
	}

	public void setUserProcessDefinitionCacheLimit(
			int userProcessDefinitionCacheLimit) {
		this.userProcessDefinitionCacheLimit = userProcessDefinitionCacheLimit;
	}

	public Cache<Object> getUserProcessDefinitionCache() {
		return userProcessDefinitionCache;
	}

	public void setUserProcessDefinitionCache(
			Cache<Object> userProcessDefinitionCache) {
		this.userProcessDefinitionCache = userProcessDefinitionCache;
	}

	public int getProcessDefinitionCacheLimit() {
		return processDefinitionCacheLimit;
	}

	public void setProcessDefinitionCacheLimit(int processDefinitionCacheLimit) {
		this.processDefinitionCacheLimit = processDefinitionCacheLimit;
	}

	public Cache<ProcessDefinition> getProcessDefinitionCache() {
		return processDefinitionCache;
	}

	public void setProcessDefinitionCache(
			Cache<ProcessDefinition> processDefinitionCache) {
		this.processDefinitionCache = processDefinitionCache;
	}

	public CommandExecutor getCommandExecutor() {
		return commandExecutor;
	}

	public String getInternationPath() {
		return getResourcePath("internationalization").getSrc();
	}

	public ResourcePath getResourcePath(String resourceId) {
		List<ResourcePath> resourcePaths = this.resourcePathConfig
				.getResourcePath();
		for (ResourcePath resourcePath : resourcePaths) {
			if (resourcePath.getId().equals(resourceId)) {
				return resourcePath;
			}
		}
		return null;
	}

	public String getNoneTemplateFilePath() {
		return "config/foxbpm.bpmn";
	}

	public DeploymentManager getDeploymentManager() {
		return deploymentManager;
	}

	public void setDeploymentManager(DeploymentManager deploymentManager) {
		this.deploymentManager = deploymentManager;
	}

	public Map<String, TaskCommandDefinition> getTaskCommandDefinitionMap() {
		return taskCommandDefinitionMap;
	}

	public TaskCommandDefinition getTaskCommandDefinition(
			String taskCommandDefinitionId) {
		return taskCommandDefinitionMap.get(taskCommandDefinitionId);
	}

	public FoxBPMStyleConfig getFoxBPMStyleConfig() {

		return foxBPMStyleConfig;
	}

	public ElementStyle getDefaultElementStyle() {
		String currentStyle = foxBPMStyleConfig.getElementStyleConfig()
				.getCurrentStyle();

		return getElementStyle(currentStyle);

	}

	public Style getStyle(String styleId, String styleObjId) {
		return styleMap.get(styleId + styleObjId);
	}

	public Style getStyle(String styleObjId) {
		String currentStyle = foxBPMStyleConfig.getElementStyleConfig()
				.getCurrentStyle();
		return styleMap.get(currentStyle + styleObjId);
	}

	public ElementStyle getElementStyle(String styleId) {

		EList<ElementStyle> elementStyleList = foxBPMStyleConfig
				.getElementStyleConfig().getElementStyle();

		for (ElementStyle elementStyle : elementStyleList) {
			if (elementStyle.getStyleId().equals(styleId)) {
				return elementStyle;
			}
		}
		return null;
	}

	public FoxbpmScheduler getFoxbpmScheduler() {
		return foxbpmScheduler;
	}

	public void setFoxbpmScheduler(FoxbpmScheduler foxbpmScheduler) {
		this.foxbpmScheduler = foxbpmScheduler;
	}

}
