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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ServiceLoader;

import javax.sql.DataSource;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.foxbpm.engine.Constant;
import org.foxbpm.engine.IdentityService;
import org.foxbpm.engine.ModelService;
import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.ProcessEngineConfiguration;
import org.foxbpm.engine.ProcessService;
import org.foxbpm.engine.RuntimeService;
import org.foxbpm.engine.TaskService;
import org.foxbpm.engine.cache.Cache;
import org.foxbpm.engine.config.ProcessEngineConfigurator;
import org.foxbpm.engine.db.DataSourceManage;
import org.foxbpm.engine.exception.ExceptionCode;
import org.foxbpm.engine.exception.ExceptionI18NCore;
import org.foxbpm.engine.exception.FoxBPMClassLoadingException;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.identity.GroupDefinition;
import org.foxbpm.engine.identity.UserDefinition;
import org.foxbpm.engine.impl.bpmn.deployer.AbstractDeployer;
import org.foxbpm.engine.impl.bpmn.deployer.BpmnDeployer;
import org.foxbpm.engine.impl.cache.DefaultCache;
import org.foxbpm.engine.impl.db.DefaultDataSourceManage;
import org.foxbpm.engine.impl.diagramview.svg.SVGTemplateContainer;
import org.foxbpm.engine.impl.identity.GroupDeptImpl;
import org.foxbpm.engine.impl.identity.GroupRoleImpl;
import org.foxbpm.engine.impl.identity.UserDefinitionImpl;
import org.foxbpm.engine.impl.interceptor.CommandConfig;
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
import org.foxbpm.engine.impl.persistence.RunningTrackManager;
import org.foxbpm.engine.impl.persistence.SchedulerManager;
import org.foxbpm.engine.impl.persistence.TaskManager;
import org.foxbpm.engine.impl.persistence.TokenManager;
import org.foxbpm.engine.impl.persistence.VariableManager;
import org.foxbpm.engine.impl.persistence.deploy.Deployer;
import org.foxbpm.engine.impl.persistence.deploy.DeploymentManager;
import org.foxbpm.engine.impl.schedule.FoxbpmScheduler;
import org.foxbpm.engine.impl.task.CommandParamImpl;
import org.foxbpm.engine.impl.task.TaskCommandDefinitionImpl;
import org.foxbpm.engine.impl.task.filter.AbstractCommandFilter;
import org.foxbpm.engine.impl.transaction.DefaultTransactionContextFactory;
import org.foxbpm.engine.impl.util.ReflectUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.modelparse.ProcessModelParseHandler;
import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.engine.sqlsession.ISqlSessionFactory;
import org.foxbpm.engine.task.CommandParam;
import org.foxbpm.engine.task.CommandParamType;
import org.foxbpm.engine.task.TaskCommandDefinition;
import org.foxbpm.engine.transaction.TransactionContextFactory;
import org.foxbpm.model.config.foxbpmconfig.BizDataObjectConfig;
import org.foxbpm.model.config.foxbpmconfig.FoxBPMConfig;
import org.foxbpm.model.config.foxbpmconfig.FoxBPMConfigPackage;
import org.foxbpm.model.config.foxbpmconfig.MailInfo;
import org.foxbpm.model.config.foxbpmconfig.ResourcePath;
import org.foxbpm.model.config.foxbpmconfig.ResourcePathConfig;
import org.foxbpm.model.config.foxbpmconfig.SysMailConfig;
import org.foxbpm.model.config.foxbpmconfig.TaskCommandConfig;
import org.foxbpm.model.config.style.ElementStyle;
import org.foxbpm.model.config.style.FoxBPMStyleConfig;
import org.foxbpm.model.config.style.Style;
import org.foxbpm.model.config.style.StylePackage;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessEngineConfigurationImpl extends ProcessEngineConfiguration {
	private static Logger log = LoggerFactory.getLogger(ProcessEngineConfigurationImpl.class);
	private static int QUART_START_DELAYTIME = 10;
	protected CommandExecutor commandExecutor;
	protected CommandContextFactory commandContextFactory;
	protected List<CommandInterceptor> commandInterceptors;
	protected FoxBPMConfig foxBpmConfig;
	protected ResourcePathConfig resourcePathConfig;
	// service
	protected Map<Class<?>,ProcessService> serviceMap = new HashMap<Class<?>, ProcessService>();
	
	
	protected ISqlSessionFactory sqlSessionFactory;
	protected Map<Class<?>, SessionFactory> sessionFactories;
	protected DataSource dataSource;
	protected BizDataObjectConfig bizDataObjectConfig;
	
	protected boolean quartzEnabled = false;
	
	// 定义及发布
	protected int processDefinitionCacheLimit = -1; // By default, no limit
	protected Cache<ProcessDefinition> processDefinitionCache;
	
	protected int userProcessDefinitionCacheLimit = -1;
	protected Cache<Object> userProcessDefinitionCache;
	
	protected int identityCacheLimit = -1;
	protected Cache<Object> identityCache;
	
	protected AbstractDeployer bpmnDeployer;
	protected ProcessModelParseHandler processModelParseHandler;
	protected List<Deployer> customPreDeployers;
	protected List<Deployer> customPostDeployers;
	protected List<Deployer> deployers;
	protected DeploymentManager deploymentManager;
	protected TransactionContextFactory transactionContextFactory;
	protected List<ProcessEngineConfigurator> configurators = new ArrayList<ProcessEngineConfigurator>();
	protected List<GroupDefinition> groupDefinitions = new ArrayList<GroupDefinition>();
	protected UserDefinition userDefinition;
	protected FoxBPMStyleConfig foxBPMStyleConfig;
	protected Map<String, Style> styleMap = new HashMap<String, Style>();
	protected TaskCommandConfig taskCommandConfig;
	
	protected Map<String, TaskCommandDefinition> taskCommandDefinitionMap;
	
	protected Map<String, AbstractCommandFilter> abstractCommandFilterMap;
	
	protected SysMailConfig sysMailConfig;
	protected String prefix = "foxbpm";
	/**
	 * FOXBPM任务调度器
	 */
	protected FoxbpmScheduler foxbpmScheduler;
	
	public ProcessEngine buildProcessEngine() {
		init();
		return new ProcessEngineImpl(this);
	}
	
	protected void init() {
		initConfigurators();
		configuratorsBeforeInit();
		initExceptionResource();
		initEmfFile();
		initCache();
		initResourcePathConfig();
		initDataSource();
		initSqlSessionFactory();
		initCommandContextFactory();
		initCommandExecutors();
		initServices();
		initSessionFactories();
		initDeployers();
		initGroupDefinitions();
		initUserDefinition();
		initTransactionContextFactory();
		// initDbConfig();// dbType
		// // 任务命令配置加载
		initTaskCommandConfig();
		//
		// initImportDataVariableConfig();
		//
		initQuartz();
		// initUserDefinition();
		initSysMailConfig();
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
		initSVG();
		// 加载任务命令过滤器
		initAbstractCommandFilter();
		
		initBizDataObjectConfig();
		
		configuratorsAfterInit();
	}
	
	protected void initConfigurators() {
		if (configurators.size() > 0) {
			Collections.sort(configurators, new Comparator<ProcessEngineConfigurator>() {
				@Override
				public int compare(ProcessEngineConfigurator configurator1,
				    ProcessEngineConfigurator configurator2) {
					int priority1 = configurator1.getPriority();
					int priority2 = configurator2.getPriority();
					
					if (priority1 < priority2) {
						return -1;
					} else if (priority1 > priority2) {
						return 1;
					}
					return 0;
				}
			});
			log.info("Found {} Process Engine Configurators in total:", configurators.size());
			for (ProcessEngineConfigurator configurator : configurators) {
				log.info("{} (priority:{})", configurator.getClass(), configurator.getPriority());
			}
		}
	}
	
	protected void configuratorsBeforeInit() {
		for (ProcessEngineConfigurator configurator : configurators) {
			log.info("Executing configure() of {} (priority:{})", configurator.getClass(), configurator.getPriority());
			configurator.beforeInit(this);
		}
	}
	
	protected void configuratorsAfterInit() {
		for (ProcessEngineConfigurator configurator : configurators) {
			log.info("Executing configure() of {} (priority:{})", configurator.getClass(), configurator.getPriority());
			configurator.configure(this);
		}
	}
	
	private void initBizDataObjectConfig() {
		this.bizDataObjectConfig = this.foxBpmConfig.getBizDataObjectConfig();
	}
	
	protected void initSysMailConfig() {
		this.sysMailConfig = foxBpmConfig.getSysMailConfig();
	}
	
	protected void initQuartz() {
		
		if (!this.quartzEnabled) {
			return;
		}
		if (this.foxbpmScheduler == null) {
			InputStream inputStream = null;
			try {
				inputStream = ReflectUtil.getResourceAsStream("config/quartz.properties");
				Properties props = new Properties();
				props.load(inputStream);
				// TODO 多数据库源事务问题处理
				SchedulerFactory factory = new StdSchedulerFactory(props);
				Scheduler scheduler = factory.getScheduler();
				foxbpmScheduler = new FoxbpmScheduler();
				foxbpmScheduler.setScheduler(scheduler);
				// TODO 获取系统配置中的调度系统是否启动配置
				scheduler.startDelayed(QUART_START_DELAYTIME);
				
			} catch (IOException e) {
				throw new FoxBPMException("流程引擎初始化加载 QUARTZ调度器配置文件时候出问题", e);
			} catch (SchedulerException e) {
				throw new FoxBPMException("流程引擎初始化QUARTZ调度器时候出问题", e);
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						throw new FoxBPMException("流程引擎初始化QUARTZ调度器,关闭配置文件时候出问题", e);
					}
				}
			}
			
		}
	}
	
	protected void initSVG() {
		SVGTemplateContainer.getContainerInstance();
	}
	
	protected void initAbstractCommandFilter() {
		abstractCommandFilterMap = new HashMap<String, AbstractCommandFilter>();
		String filter = null;
		for (Map.Entry<String,TaskCommandDefinition> tmp : taskCommandDefinitionMap.entrySet()) {
			TaskCommandDefinition taskDefintion = tmp.getValue();
			filter = taskDefintion.getFilterClass();
			abstractCommandFilterMap.put(taskDefintion.getId(), (AbstractCommandFilter) ReflectUtil.instantiate(filter));
		}
	}
	
	private void initStyle() {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xml", new XMIResourceFactoryImpl());
		InputStream inputStream = null;
		String classPath = "config/style.xml";
		inputStream = ReflectUtil.getResourceAsStream("style.xml");
		if (inputStream != null) {
			classPath = "foxbpm.cfg.xml";
		}
		URL url = this.getClass().getClassLoader().getResource(classPath);
		if (url == null) {
			log.error("未能从{}目录下找到style.xml文件", classPath);
			throw new FoxBPMClassLoadingException(ExceptionCode.CLASSLOAD_EXCEPTION_FILENOTFOUND, "style.xml");
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
			resourceSet.getPackageRegistry().put(StylePackage.eINSTANCE.getNsURI(), StylePackage.eINSTANCE);
			resource.load(null);
		} catch (Exception e) {
			log.error("style.xml文件加载失败", e);
			throw new FoxBPMClassLoadingException(ExceptionCode.CLASSLOAD_EXCEPTION, "style.xml", e);
		}
		
		foxBPMStyleConfig = (FoxBPMStyleConfig) resource.getContents().get(0);
		
		EList<ElementStyle> elementStyleList = foxBPMStyleConfig.getElementStyleConfig().getElementStyle();
		for (ElementStyle elementStyle : elementStyleList) {
			for (Style style : elementStyle.getStyle()) {
				styleMap.put(elementStyle.getStyleId() + style.getObject(), style);
			}
		}
	}
	
	protected void initTaskCommandConfig() {
		this.taskCommandConfig = foxBpmConfig.getTaskCommandConfig();
		taskCommandDefinitionMap = new HashMap<String, TaskCommandDefinition>();
		for (TaskCommandDefinition taskCommandDef : getTaskCommandDefinition()) {
			taskCommandDefinitionMap.put(taskCommandDef.getId(), taskCommandDef);
		}
	}
	
	protected List<TaskCommandDefinition> getTaskCommandDefinition(){
		List<TaskCommandDefinition> taskCommands = new ArrayList<TaskCommandDefinition>();
		List<org.foxbpm.model.config.foxbpmconfig.TaskCommandDefinition> commandDefintions = taskCommandConfig.getTaskCommandDefinition();
		//加载foxbpm.cfg.xml中配置的任务命令
		for(org.foxbpm.model.config.foxbpmconfig.TaskCommandDefinition tmpCommand :commandDefintions){
			if("system".equals(tmpCommand.getType())){
				break;
			}
			if(!StringUtil.getBoolean(tmpCommand.getIsEnabled())){
				break;
			}
			TaskCommandDefinitionImpl commandImpl = new TaskCommandDefinitionImpl();
			commandImpl.setId(tmpCommand.getId());
			commandImpl.setName(tmpCommand.getName());
			commandImpl.setCommandClass(tmpCommand.getCommand());
			commandImpl.setCmdClass(tmpCommand.getCmd());
			commandImpl.setFilterClass(tmpCommand.getFilter());
			commandImpl.setDescription(tmpCommand.getDescription());
			List<CommandParam> commandParams = new ArrayList<CommandParam>();
			
			for(org.foxbpm.model.config.foxbpmconfig.CommandParam param : tmpCommand.getCommandParam()){
				CommandParamImpl commandParam = new CommandParamImpl();
				commandParam.setKey(param.getKey());
				commandParam.setDataType(param.getDataType());
				commandParam.setName(param.getName());
				commandParam.setExpression(param.getValue());
				commandParam.setBizType(CommandParamType.valueOf(param.getBizType()));
				commandParam.setDescription(param.getDescription());
				commandParams.add(commandParam);
			}
			commandImpl.setCommandParam(commandParams);
			taskCommands.add(commandImpl);
		}
		
		ServiceLoader<TaskCommandDefinition> s = ServiceLoader.load(TaskCommandDefinition.class); 
		Iterator<TaskCommandDefinition> searchs = s.iterator();
		while(searchs.hasNext()){
			TaskCommandDefinition command = searchs.next();
			taskCommands.add(command);
			log.debug("发现注册任务命令：id:{},name:{},class:{}", command.getId(),command.getName(),command.getClass());
		}
		return taskCommands;
	}
	
	protected void initTransactionContextFactory() {
		if (transactionContextFactory == null) {
			transactionContextFactory = new DefaultTransactionContextFactory();
		}
	}
	
	protected void initUserDefinition() {
		if (userDefinition == null) {
			userDefinition = new UserDefinitionImpl();
		}
	}
	
	protected void initGroupDefinitions() {
		if (groupDefinitions == null || groupDefinitions.isEmpty()) {
			groupDefinitions.add(new GroupDeptImpl(Constant.DEPT_TYPE, "部门"));
			groupDefinitions.add(new GroupRoleImpl(Constant.ROLE_TYPE, "角色"));
		}
	}
	
	protected void initCache() {
		// userCache
		if (identityCache == null) {
			if (identityCacheLimit <= 0) {
				identityCache = new DefaultCache<Object>();
			} else {
				identityCache = new DefaultCache<Object>(identityCacheLimit);
			}
		}
		// Process Definition cache
		if (processDefinitionCache == null) {
			if (processDefinitionCacheLimit <= 0) {
				processDefinitionCache = new DefaultCache<ProcessDefinition>();
			} else {
				processDefinitionCache = new DefaultCache<ProcessDefinition>(processDefinitionCacheLimit);
			}
		}
		if (userProcessDefinitionCache == null) {
			if (userProcessDefinitionCacheLimit <= 0) {
				userProcessDefinitionCache = new DefaultCache<Object>();
			} else {
				userProcessDefinitionCache = new DefaultCache<Object>(userProcessDefinitionCacheLimit);
			}
		}
	}
	
	protected void initSessionFactories() {
		if (sessionFactories == null) {
			sessionFactories = new HashMap<Class<?>, SessionFactory>();
			addSessionFactory(sqlSessionFactory);
			addSessionFactory(new GenericManagerFactory(TaskManager.class));
			addSessionFactory(new GenericManagerFactory(SchedulerManager.class));
			addSessionFactory(new GenericManagerFactory(ProcessInstanceManager.class));
			addSessionFactory(new GenericManagerFactory(TokenManager.class));
			addSessionFactory(new GenericManagerFactory(DeploymentEntityManager.class));
			addSessionFactory(new GenericManagerFactory(ProcessDefinitionManager.class));
			addSessionFactory(new GenericManagerFactory(ResourceManager.class));
			addSessionFactory(new GenericManagerFactory(IdentityLinkManager.class));
			addSessionFactory(new GenericManagerFactory(VariableManager.class));
			addSessionFactory(new GenericManagerFactory(AgentManager.class));
			addSessionFactory(new GenericManagerFactory(RunningTrackManager.class));
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
		}
	}
	
	protected Collection<? extends Deployer> getDefaultDeployers() {
		List<Deployer> defaultDeployers = new ArrayList<Deployer>();
		
		if (bpmnDeployer == null) {
			// 添加部署的时候自动启动流程实例 功能，修改时间 2014-06-24
			bpmnDeployer = new BpmnDeployer();
		}
		
		if (processModelParseHandler == null) {
			processModelParseHandler = (ProcessModelParseHandler) ReflectUtil.instantiate("org.foxbpm.engine.impl.bpmn.parser.BpmnParseHandlerImpl");
		}
		bpmnDeployer.setProcessModelParseHandler(processModelParseHandler);
		defaultDeployers.add(bpmnDeployer);
		return defaultDeployers;
	}
	
	public void initDataSource() {
		if (dataSource == null) {
			DataSourceManage dataSourceManager = new DefaultDataSourceManage();
			dataSourceManager.init();
			dataSource = dataSourceManager.getDataSource();
		}
		
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
		if(sqlSessionFactory == null){
			sqlSessionFactory = new MyBatisSqlSessionFactory();
			sqlSessionFactory.init(dataSource,prefix);
		}
	}
	
	public ISqlSessionFactory getSqlSessionFactory() {
		return this.sqlSessionFactory;
	}
	
	protected void initServices() {
		
		
		ModelServiceImpl modelService = new ModelServiceImpl();
		modelService.setCommandExecutor(commandExecutor);
		RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
		runtimeService.setCommandExecutor(commandExecutor);
		TaskServiceImpl taskService = new TaskServiceImpl();
		taskService.setCommandExecutor(commandExecutor);
		IdentityServiceImpl identityService = new IdentityServiceImpl();
		identityService.setCommandExecutor(commandExecutor);
		
		serviceMap.put(ModelService.class, modelService);
		serviceMap.put(RuntimeService.class, runtimeService);
		serviceMap.put(TaskService.class, taskService);
		serviceMap.put(IdentityService.class, identityService);
		
		//serviceLoader方式加载外部注册service
		ServiceLoader<ProcessService> services = ServiceLoader.load(ProcessService.class);
		Iterator<ProcessService> serviceIterator = services.iterator();
		while(serviceIterator.hasNext()){
			ProcessService tmpService = serviceIterator.next();
			tmpService.setCommandExecutor(commandExecutor);
			serviceMap.put(tmpService.getInterfaceClass(), tmpService);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public  <T> T getService(Class<T> interfaceClass){
		ProcessService service = serviceMap.get(interfaceClass);
		
		if(service == null){
			throw new FoxBPMException("未找到service：{}的实现，请检查配置文件和启动日志！",interfaceClass);
		}
		return (T)service;
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
		CommandContextInterceptor commandContextInterceptor = new CommandContextInterceptor(commandContextFactory, this);
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
		CommandConfig config = new CommandConfig();
		commandExecutor = new CommandExecutorImpl(config, first);
	}
	
	protected CommandInterceptor initInterceptorChain(List<CommandInterceptor> chain) {
		int size = chain.size() - 1;
		for (int i = 0; i < size; i++) {
			chain.get(i).setNext(chain.get(i + 1));
		}
		return chain.get(0);
	}
	
	private void initResourcePathConfig() {
		resourcePathConfig = foxBpmConfig.getResourcePathConfig();
	}
	
	// Setter
	
	public ProcessEngineConfiguration setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		return this;
	}
	
	// Getter方法
	
	public ModelService getModelService() {
		return getService(ModelService.class);
	}
	
	public ResourcePathConfig getResourcePathConfig() {
		return resourcePathConfig;
	}
	
	public TransactionContextFactory getTransactionContextFactory() {
		return transactionContextFactory;
	}
	
	public ProcessEngineConfiguration setTransactionContextFactory(TransactionContextFactory transactionFactory) {
		this.transactionContextFactory = transactionFactory;
		return this;
	}
	
	public List<GroupDefinition> getGroupDefinitions() {
		return groupDefinitions;
	}
	
	public ProcessEngineConfiguration setGroupDefinitions(List<GroupDefinition> groupDefinitions) {
		this.groupDefinitions = groupDefinitions;
		return this;
	}
	
	public RuntimeService getRuntimeService() {
		return getService(RuntimeService.class);
	}
	
	public TaskService getTaskService() {
		return getService(TaskService.class);
	}
	
	public IdentityService getIdentityService() {
		return getService(IdentityService.class);
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}
	
	public FoxBPMConfig getFoxBpmConfig() {
		return foxBpmConfig;
	}
	
	public Map<Class<?>, SessionFactory> getSessionFactories() {
		return sessionFactories;
	}
	
	public Cache<Object> getIdentityCache() {
		return identityCache;
	}
	
	public void setIdentityCacheLimit(int identityCacheLimit) {
		this.identityCacheLimit = identityCacheLimit;
	}
	
	public int getUserProcessDefinitionCacheLimit() {
		return userProcessDefinitionCacheLimit;
	}
	
	public void setUserProcessDefinitionCacheLimit(int userProcessDefinitionCacheLimit) {
		this.userProcessDefinitionCacheLimit = userProcessDefinitionCacheLimit;
	}
	
	public Cache<Object> getUserProcessDefinitionCache() {
		return userProcessDefinitionCache;
	}
	
	public void setUserProcessDefinitionCache(Cache<Object> userProcessDefinitionCache) {
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
	
	public void setProcessDefinitionCache(Cache<ProcessDefinition> processDefinitionCache) {
		this.processDefinitionCache = processDefinitionCache;
	}
	
	public CommandExecutor getCommandExecutor() {
		return commandExecutor;
	}
	
	public List<Deployer> getCustomPostDeployers() {
		return customPostDeployers;
	}
	
	public ProcessEngineConfiguration setCustomPostDeployers(List<Deployer> customPostDeployers) {
		this.customPostDeployers = customPostDeployers;
		return this;
	}
	
	public String getInternationPath() {
		return getResourcePath("internationalization").getSrc();
	}
	
	public ResourcePath getResourcePath(String resourceId) {
		List<ResourcePath> resourcePaths = this.resourcePathConfig.getResourcePath();
		for (ResourcePath resourcePath : resourcePaths) {
			if (StringUtil.equals(resourcePath.getId(), resourceId)) {
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
	
	public TaskCommandDefinition getTaskCommandDefinition(String taskCommandDefinitionId) {
		return taskCommandDefinitionMap.get(taskCommandDefinitionId);
	}
	
	public FoxBPMStyleConfig getFoxBPMStyleConfig() {
		
		return foxBPMStyleConfig;
	}
	
	public ElementStyle getDefaultElementStyle() {
		String currentStyle = foxBPMStyleConfig.getElementStyleConfig().getCurrentStyle();
		
		return getElementStyle(currentStyle);
		
	}
	
	public Style getStyle(String styleId, String styleObjId) {
		return styleMap.get(styleId + styleObjId);
	}
	
	public Style getStyle(String styleObjId) {
		String currentStyle = foxBPMStyleConfig.getElementStyleConfig().getCurrentStyle();
		return styleMap.get(currentStyle + styleObjId);
	}
	
	public ElementStyle getElementStyle(String styleId) {
		EList<ElementStyle> elementStyleList = foxBPMStyleConfig.getElementStyleConfig().getElementStyle();
		for (ElementStyle elementStyle : elementStyleList) {
			if (StringUtil.equals(elementStyle.getStyleId(), styleId)) {
				return elementStyle;
			}
		}
		return null;
	}
	
	public FoxbpmScheduler getFoxbpmScheduler() {
		return foxbpmScheduler;
	}
	
	public ProcessEngineConfiguration setFoxbpmScheduler(FoxbpmScheduler foxbpmScheduler) {
		this.foxbpmScheduler = foxbpmScheduler;
		return this;
	}
	
	public Map<String, AbstractCommandFilter> getAbstractCommandFilterMap() {
		return abstractCommandFilterMap;
	}
	
	public SysMailConfig getSysMailConfig() {
		return sysMailConfig;
	}
	
	public MailInfo getSysMail(String mailId) {
		for (MailInfo mailInfo : sysMailConfig.getMailInfo()) {
			if (StringUtil.equals(mailInfo.getMailName(), mailId)) {
				return mailInfo;
			}
		}
		return null;
	}
	
	public MailInfo getDefaultSysMail() {
		String mailId = sysMailConfig.getSelected();
		for (MailInfo mailInfo : sysMailConfig.getMailInfo()) {
			if (StringUtil.equals(mailInfo.getMailName(), mailId)) {
				return mailInfo;
			}
		}
		return null;
	}
	
	public TaskCommandConfig getTaskCommandConfig() {
		return taskCommandConfig;
	}
	
	public BizDataObjectConfig getBizDataObjectConfig() {
		return bizDataObjectConfig;
	}
	
	public List<ProcessEngineConfigurator> getConfigurators() {
		return configurators;
	}
	
	public ProcessEngineConfiguration setConfigurators(List<ProcessEngineConfigurator> configurators) {
		this.configurators = configurators;
		return this;
	}
	
	public UserDefinition getUserDefinition() {
		return userDefinition;
	}
	
	public ProcessEngineConfiguration setUserDefinition(UserDefinition userDefinition) {
		this.userDefinition = userDefinition;
		return this;
	}
	
	public ProcessEngineConfiguration setQuartzEnabled(boolean quartzEnabled) {
		this.quartzEnabled = quartzEnabled;
		return this;
	}
	
	public boolean getQuartzEnabled() {
		return this.quartzEnabled;
	}
	
	public ProcessEngineConfiguration setPrefix(String prefix) {
		this.prefix = prefix;
		return this;
	}
	
	public String getPrefix() {
		return prefix;
	}
}
