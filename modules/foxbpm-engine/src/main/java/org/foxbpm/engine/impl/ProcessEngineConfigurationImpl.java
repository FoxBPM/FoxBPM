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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.foxbpm.engine.Constant;
import org.foxbpm.engine.IdentityService;
import org.foxbpm.engine.ModelService;
import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.ProcessEngineConfiguration;
import org.foxbpm.engine.ProcessService;
import org.foxbpm.engine.RuntimeService;
import org.foxbpm.engine.TaskService;
import org.foxbpm.engine.cache.Cache;
import org.foxbpm.engine.calendar.WorkCalendar;
import org.foxbpm.engine.config.FoxBPMConfig;
import org.foxbpm.engine.config.ProcessEngineConfigurator;
import org.foxbpm.engine.event.EventListener;
import org.foxbpm.engine.exception.ExceptionI18NCore;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.identity.GroupDefinition;
import org.foxbpm.engine.identity.UserDefinition;
import org.foxbpm.engine.impl.bpmn.deployer.BpmnDeployer;
import org.foxbpm.engine.impl.bpmn.deployer.PngDeployer;
import org.foxbpm.engine.impl.cache.DefaultCache;
import org.foxbpm.engine.impl.diagramview.svg.SVGTemplateContainer;
import org.foxbpm.engine.impl.event.EventListenerImpl;
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
import org.foxbpm.engine.impl.mybatis.FoxbpmMapperConfig;
import org.foxbpm.engine.impl.mybatis.MyBatisSqlSessionFactory;
import org.foxbpm.engine.impl.persistence.AgentManager;
import org.foxbpm.engine.impl.persistence.DeploymentEntityManager;
import org.foxbpm.engine.impl.persistence.GenericManagerFactory;
import org.foxbpm.engine.impl.persistence.IdentityLinkManager;
import org.foxbpm.engine.impl.persistence.ProcessDefinitionManager;
import org.foxbpm.engine.impl.persistence.ProcessInstanceManager;
import org.foxbpm.engine.impl.persistence.ProcessOperatingManager;
import org.foxbpm.engine.impl.persistence.ResourceManager;
import org.foxbpm.engine.impl.persistence.RunningTrackManager;
import org.foxbpm.engine.impl.persistence.SchedulerManager;
import org.foxbpm.engine.impl.persistence.TaskManager;
import org.foxbpm.engine.impl.persistence.TokenManager;
import org.foxbpm.engine.impl.persistence.VariableManager;
import org.foxbpm.engine.impl.persistence.deploy.Deployer;
import org.foxbpm.engine.impl.persistence.deploy.DeploymentManager;
import org.foxbpm.engine.impl.schedule.FoxbpmScheduler;
import org.foxbpm.engine.impl.task.filter.AbstractCommandFilter;
import org.foxbpm.engine.impl.util.ExceptionUtil;
import org.foxbpm.engine.impl.util.FoxBPMCfgParseUtil;
import org.foxbpm.engine.impl.util.ListenerComparator;
import org.foxbpm.engine.impl.util.ReflectUtil;
import org.foxbpm.engine.impl.util.ServiceLoader;
import org.foxbpm.engine.impl.workcalendar.DefaultWorkCalendar;
import org.foxbpm.engine.modelparse.ProcessModelParseHandler;
import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.engine.sqlsession.ISqlSessionFactory;
import org.foxbpm.engine.task.TaskCommandDefinition;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessEngineConfigurationImpl extends ProcessEngineConfiguration {
	private static Logger log = LoggerFactory.getLogger(ProcessEngineConfigurationImpl.class);
	private static int QUART_START_DELAYTIME = 10;
	protected List<ProcessEngineConfigurator> allConfigurators;
	protected CommandExecutor commandExecutor;
	protected CommandContextFactory commandContextFactory;
	protected List<CommandInterceptor> commandInterceptors;
	protected FoxBPMConfig foxBpmConfig = new FoxBPMConfig();
	// service
	protected List<ProcessService> processServices = new ArrayList<ProcessService>();
	protected Map<Class<?>, ProcessService> serviceMap = new HashMap<Class<?>, ProcessService>();
	// session工厂
	protected ISqlSessionFactory sqlSessionFactory;
	protected Map<Class<?>, SessionFactory> sessionFactories;
	// 缓存配置
	protected int processDefinitionCacheLimit = -1; // By default, no limit
	protected Cache<ProcessDefinition> processDefinitionCache;
	protected int userProcessDefinitionCacheLimit = -1;
	protected Cache<Object> userProcessDefinitionCache;
	protected int identityCacheLimit = -1;
	protected Cache<Object> identityCache;
	
	// 定义发布器
	protected List<Deployer> deployers;
	protected DeploymentManager deploymentManager;
	
	protected List<TaskCommandDefinition> taskCommandDefinitions = new ArrayList<TaskCommandDefinition>();
	
	// 用户扩展点
	
	/**
	 * 数据源
	 */
	protected DataSource dataSource;
	
	/**
	 * 工作日历
	 */
	protected WorkCalendar workCalendar;
	
	/**
	 * 定时任务是否开启
	 */
	protected boolean quartzEnabled = false;
	
	/**
	 * 自定义前置发布器
	 */
	protected List<Deployer> customPreDeployers;
	
	/**
	 * 自定义后置发布器
	 */
	protected List<Deployer> customPostDeployers;
	
	/**
	 * 组定义（组织机构，角色等扩展点）
	 */
	protected List<GroupDefinition> groupDefinitions;
	
	/**
	 * 用户信息扩展点
	 */
	protected UserDefinition userDefinition;
	
	/**
	 * 数据库表前缀
	 */
	protected String prefix = "foxbpm";
	
	/**
	 * 任务是否自动领取
	 */
	protected int autoClaim = -1;
	
	/**
	 * 用户配置文件路径
	 */
	protected String configXmlPath;
	
	/**
	 * 用户配置文件流，优先级高于configPath
	 */
	protected InputStream configXmlStream;
	
	/**
	 * 用户配置文件路径
	 */
	protected String connectorMenuPath;
	
	/**
	 * 邮件服务器地址
	 */
	protected String mailServerAddress;
	
	/**
	 * 邮件服务器端口
	 */
	protected String mailServerPort;
	
	/**
	 * 邮件服务器用户名
	 */
	protected String mailUserName;
	
	/**
	 * 邮件服务器密码
	 */
	protected String mailPassword;
	
	/**
	 * 引擎初始化配置
	 */
	protected List<ProcessEngineConfigurator> configurators;
	
	/**
	 * 任务命令定义配置
	 */
	protected Map<String, TaskCommandDefinition> taskCommandDefinitionMap;
	
	/**
	 * 任务命令展现过滤器配置
	 */
	protected Map<String, AbstractCommandFilter> commandFilterMap;
	
	/** 
	 * 所有的事件监听配置(系统级和外部) 
	 * 事件监听配置
	 */ 
	protected List<EventListener> eventListeners = new ArrayList<EventListener>(10); 
	protected Map<String,List<EventListener>> eventMapListeners; 
	
	/**
	 * FOXBPM任务调度器
	 */
	protected FoxbpmScheduler foxbpmScheduler;
	
	/**
	 * 自定义mapper文件
	 */
	protected List<FoxbpmMapperConfig> customMapperConfig = new ArrayList<FoxbpmMapperConfig>();
	
	public ProcessEngine buildProcessEngine() {
		init();
		return new ProcessEngineImpl(this);
	}
	
	protected void init() {
		initExceptionResource();
		// 优先加载扩展文件
		initExpandConfig();
		// 加载foxbpm.cfg.xml配置文件，配置了引擎默认配置。
		initEngineConfig();
		// 加载外部插件
		initConfigurators();
		configuratorsBeforeInit();
		initCache();
		// 加载sessionFactory
		initSqlSessionFactory();
		initSessionFactories();
		// 加载命令相关
		initCommandContextFactory();
		initCommandExecutors();
		initTaskCommand();
		initEventListeners();
		initServices();
		initDeployers();
		// 加载组织机构相关
		initGroupDefinitions();
		initUserDefinition();
		initCalendar();
		initQuartz();
		// 加载SVG模版资源
		initSVG();
		configuratorsAfterInit();
	}
	
	protected void initCalendar() {
		if (workCalendar == null) {
			workCalendar = new DefaultWorkCalendar();
		}
	}
	
	protected void initConfigurators() {
		allConfigurators = new ArrayList<ProcessEngineConfigurator>();
		//外部注入配置，如spring
		if (configurators != null) {
			allConfigurators.addAll(configurators);
		}
		//config文件中配置
		if(foxBpmConfig.getConfigurators() != null){
			allConfigurators.addAll(foxBpmConfig.getConfigurators());
		}
		
		//java spi扫描
		ServiceLoader<ProcessEngineConfigurator> configuratorServiceLoader = ServiceLoader.load(ProcessEngineConfigurator.class);
		for (ProcessEngineConfigurator configurator : configuratorServiceLoader) {
			allConfigurators.add(configurator);
		}
		if (allConfigurators != null && allConfigurators.size() > 0) {
			Collections.sort(allConfigurators, new Comparator<ProcessEngineConfigurator>() {
				
				public int compare(ProcessEngineConfigurator configurator1, ProcessEngineConfigurator configurator2) {
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
			log.info("共发现 {} 个Configurators配置:", allConfigurators.size());
			for (ProcessEngineConfigurator configurator : allConfigurators) {
				log.info("{} (优先级:{})", configurator.getClass(), configurator.getPriority());
			}
		}
	}
	
	protected void configuratorsBeforeInit() {
		if (allConfigurators != null) {
			for (ProcessEngineConfigurator configurator : allConfigurators) {
				log.info("加载 configure： {} (优先级:{})", configurator.getClass(), configurator.getPriority());
				try{
					configurator.beforeInit(this);
				}catch(Exception ex){
					throw ExceptionUtil.getException("00000003",ex);
				}
				
			}
		}
	}
	
	protected void configuratorsAfterInit() {
		if (allConfigurators != null) {
			for (ProcessEngineConfigurator configurator : allConfigurators) {
				log.info("执行 configure() of {} (优先级:{})", configurator.getClass(), configurator.getPriority());
				try{
					configurator.configure(this);
				}catch(Exception ex){
					throw ExceptionUtil.getException("00000004",ex);
				}
			}
		}
	}
	
	protected void initEventListeners() {
		List<EventListenerImpl> eventListenerImpls = foxBpmConfig.getEventListeners();
		if (eventListenerImpls != null && !eventListenerImpls.isEmpty()) {
			Map<String, EventListener> eventListenerMap = new HashMap<String, EventListener>();
			EventListenerImpl tmp = null;
			for (Iterator<EventListenerImpl> iterator = eventListenerImpls.iterator(); iterator.hasNext();) {
				tmp = iterator.next();
				eventListenerMap.put(tmp.getId(), tmp);
			}
			eventListeners.addAll(eventListenerMap.values());
		}
		// 对所有监听进行排序
		Collections.sort(eventListeners, new ListenerComparator());
		if (log.isDebugEnabled() && !eventListeners.isEmpty()) {
			EventListener tmp = null;
			log.debug("监听器优先级排序，结果如下：");
			for (Iterator<EventListener> iterator = eventListeners.iterator(); iterator.hasNext();) {
				tmp = iterator.next();
				log.debug("监听编号：{},监听事件：{},优先级：{},类名：{}", tmp.getId(), tmp.getEventType(), tmp.getPriority(), tmp.getListenerClass());
			}
		}
		if(eventListeners != null) {
			this.eventMapListeners = new HashMap<String, List<EventListener>>();
			List<EventListener> tmpEventListeners = null;
			for(EventListener tmp :eventListeners){
				if(eventMapListeners.get(tmp.getEventType()) == null){
					tmpEventListeners =  new ArrayList<EventListener>();
					tmpEventListeners.add(tmp);
					eventMapListeners.put(tmp.getEventType(), tmpEventListeners);
				}else{
					eventMapListeners.get(tmp.getEventType()).add(tmp);
				} 
			}
		}
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
				throw ExceptionUtil.getException("00007001",e);
			} catch (SchedulerException e) {
				throw ExceptionUtil.getException("00008001", e);
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						throw ExceptionUtil.getException("00007002",e);
					}
				}
			}
			
		}
	}
	
	protected void initSVG() {
		SVGTemplateContainer.getContainerInstance();
	}
	
	protected void initTaskCommand() {
		taskCommandDefinitionMap = new HashMap<String, TaskCommandDefinition>();
		commandFilterMap = new HashMap<String, AbstractCommandFilter>();
		for (TaskCommandDefinition taskCommandDef : getAllTaskCommandDefinitions()) {
			if(!"system".equals(taskCommandDef.getType())){
				TaskCommandDefinition tmp = taskCommandDefinitionMap.get(taskCommandDef.getId());
				if (tmp == null) {
					taskCommandDefinitions.add(taskCommandDef);
					taskCommandDefinitionMap.put(taskCommandDef.getId(), taskCommandDef);
					AbstractCommandFilter filter = null;
					if ("toDoTasks".equals(taskCommandDef.getType())) {
						try{
							filter = (AbstractCommandFilter) ReflectUtil.instantiate(taskCommandDef.getFilterClass());
						}catch(Exception ex){
							throw ExceptionUtil.getException("00005001",ex,taskCommandDef.getId(),taskCommandDef.getFilterClass());
						}
						commandFilterMap.put(taskCommandDef.getId(),filter);
					}
				} else {
					log.debug("发现重复任务命令，忽略此命令：" + taskCommandDef.getId());
				}
			}
			
		}
	}
	
	protected List<TaskCommandDefinition> getAllTaskCommandDefinitions() {
		List<TaskCommandDefinition> taskCommands = new ArrayList<TaskCommandDefinition>();
		if (foxBpmConfig.getTaskCommandDefinitions() != null) {
			taskCommands.addAll(foxBpmConfig.getTaskCommandDefinitions());
		}
		ServiceLoader<TaskCommandDefinition> s = ServiceLoader.load(TaskCommandDefinition.class);
		Iterator<TaskCommandDefinition> searchs = s.iterator();
		while (searchs.hasNext()) {
			TaskCommandDefinition command = searchs.next();
			taskCommands.add(command);
			log.debug("发现注册任务命令：id:{},name:{},class:{}", command.getId(), command.getName(), command.getClass());
		}
		return taskCommands;
	}
	
	protected void initUserDefinition() {
		if (userDefinition == null) {
			userDefinition = new UserDefinitionImpl();
		}
	}
	
	protected void initGroupDefinitions() {
		if (groupDefinitions == null) {
			groupDefinitions = new ArrayList<GroupDefinition>();
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
			addSessionFactory(new GenericManagerFactory(ProcessOperatingManager.class));
		}
	}
	
	protected void addSessionFactory(SessionFactory sessionFactory) {
		sessionFactories.put(sessionFactory.getSessionType(), sessionFactory);
	}
	
	protected void initDeployers() {
		this.deployers = new ArrayList<Deployer>();
		if (customPreDeployers != null) {
			this.deployers.addAll(customPreDeployers);
		}
		this.deployers.addAll(getDefaultDeployers());
		if (customPostDeployers != null) {
			this.deployers.addAll(customPostDeployers);
		}
		deploymentManager = new DeploymentManager();
		deploymentManager.setDeployers(deployers);
		deploymentManager.setProcessDefinitionCache(processDefinitionCache);
	}
	
	protected Collection<? extends Deployer> getDefaultDeployers() {
		List<Deployer> defaultDeployers = new ArrayList<Deployer>();
		// 添加部署的时候自动启动流程实例 功能，修改时间 2014-06-24
		BpmnDeployer bpmnDeployer = new BpmnDeployer();
		PngDeployer pngDeployer = new PngDeployer();
		ProcessModelParseHandler processModelParseHandler = null;
		try{
			processModelParseHandler = (ProcessModelParseHandler) ReflectUtil.instantiate("org.foxbpm.engine.impl.bpmn.parser.BpmnParseHandlerImpl");
		}catch(Exception ex){
			throw ExceptionUtil.getException("00005002" , ex);
		}
				
		bpmnDeployer.setProcessModelParseHandler(processModelParseHandler);
		defaultDeployers.add(bpmnDeployer);
		defaultDeployers.add(pngDeployer);
		return defaultDeployers;
	}
	
	public void initExceptionResource() {
		ExceptionI18NCore.system_init("I18N/exception");
	}
	
	protected void initEngineConfig() {
		FoxBPMCfgParseUtil configUtil = FoxBPMCfgParseUtil.getInstance();
		InputStream configStream = ReflectUtil.getResourceAsStream("config/foxbpm.cfg.xml");
		if (configStream == null) {
			throw ExceptionUtil.getException("00002001");
		}
		try {
			FoxBPMConfig tmpfoxBpmConfig = configUtil.parsecfg(configStream);
			foxBpmConfig.addConfig(tmpfoxBpmConfig);
		} catch (Exception ex) {
			if (ex instanceof FoxBPMException) {
				throw (FoxBPMException) ex;
			} else {
				throw ExceptionUtil.getException("00006005");
			}
		}
	}
	
	protected void initExpandConfig() {
		FoxBPMCfgParseUtil configUtil = FoxBPMCfgParseUtil.getInstance();
		if (configXmlStream == null && configXmlPath == null) {
			return;
		}
		if (configXmlStream == null) {
			configXmlStream = ReflectUtil.getResourceAsStream(configXmlPath);
		}
		if (configXmlStream == null) {
			throw ExceptionUtil.getException("00002002");
		}
		try {
			FoxBPMConfig expandConfig = configUtil.parsecfg(configXmlStream);
			foxBpmConfig.addConfig(expandConfig);
		} catch (Exception ex) {
			if (ex instanceof FoxBPMException) {
				throw (FoxBPMException) ex;
			} else {
				throw ExceptionUtil.getException("00006006");
			}
		}
	}
	
	public void initSqlSessionFactory() {
		if (sqlSessionFactory == null) {
			sqlSessionFactory = new MyBatisSqlSessionFactory();
			try {
				sqlSessionFactory.init(this);
			} catch (SQLException e) {
				throw ExceptionUtil.getException("00009002",e);
			}
		}
	}
	
	protected void initServices() {
		
		processServices.add(new ModelServiceImpl());
		processServices.add(new RuntimeServiceImpl());
		processServices.add(new TaskServiceImpl());
		processServices.add(new IdentityServiceImpl());
		
		Iterator<ProcessService> iterator = processServices.iterator();
		while (iterator.hasNext()) {
			ProcessService tmpService = iterator.next();
			if (!serviceMap.containsKey(tmpService.getClass())) {
				tmpService.setCommandExecutor(commandExecutor);
				serviceMap.put(tmpService.getInterfaceClass(), tmpService);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getService(Class<T> interfaceClass) {
		ProcessService service = serviceMap.get(interfaceClass);
		return (T) service;
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
	
	// Getter方法
	
	public ISqlSessionFactory getSqlSessionFactory() {
		return this.sqlSessionFactory;
	}
	
	public ModelService getModelService() {
		return getService(ModelService.class);
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
	
	public ProcessEngineConfiguration setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		return this;
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
	
	public String getNoneTemplateFilePath() {
		return "config/foxbpm.bpmn";
	}
	
	public DeploymentManager getDeploymentManager() {
		return deploymentManager;
	}
	
	public Map<String, TaskCommandDefinition> getTaskCommandDefinitionMap() {
		return taskCommandDefinitionMap;
	}
	
	public TaskCommandDefinition getTaskCommandDefinition(String taskCommandDefinitionId) {
		return taskCommandDefinitionMap.get(taskCommandDefinitionId);
	} 
	public List<EventListener> getEventListeners() {
		return eventListeners; 
	}
	public  Map<String,List<EventListener>>  getEventMapListeners(){
		return eventMapListeners; 
	} 

	/**
	 * 添加监听器
	 * 
	 * @param listener
	 *            监听器
	 */
	public void addEventListener(EventListener listener) {
		eventListeners.add(listener);
	} 
	public FoxbpmScheduler getFoxbpmScheduler() {
		if (!quartzEnabled) {
			return null;
		}
		return foxbpmScheduler;
	}
	
	public ProcessEngineConfiguration setFoxbpmScheduler(FoxbpmScheduler foxbpmScheduler) {
		this.foxbpmScheduler = foxbpmScheduler;
		return this;
	}
	
	public Map<String, AbstractCommandFilter> getCommandFilterMap() {
		return commandFilterMap;
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
	
	public List<ProcessService> getProcessServices() {
		return this.processServices;
	}
	
	public List<FoxbpmMapperConfig> getCustomMapperConfig() {
		return customMapperConfig;
	}
	
	public ProcessEngineConfiguration setPrefix(String prefix) {
		this.prefix = prefix;
		return this;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public boolean isAutoClaim() {
		return autoClaim == 1;
	}
	
	public void setAutoClaim(int isAutoClaim) {
		this.autoClaim = isAutoClaim;
	}
	
	public void setWorkCalendar(WorkCalendar workCalendar) {
		this.workCalendar = workCalendar;
	}
	
	public WorkCalendar getWorkCalendar() {
		return workCalendar;
	}
	
	public String getMailServerAddress() {
		return mailServerAddress;
	}
	
	public void setMailServerAddress(String mailServerAddress) {
		this.mailServerAddress = mailServerAddress;
	}
	
	public String getMailServerPort() {
		return mailServerPort;
	}
	
	public void setMailServerPort(String mailServerPort) {
		this.mailServerPort = mailServerPort;
	}
	
	public String getMailUserName() {
		return mailUserName;
	}
	
	public void setMailUserName(String mailUserName) {
		this.mailUserName = mailUserName;
	}
	
	public String getMailPassword() {
		return mailPassword;
	}
	
	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}
	
	public List<TaskCommandDefinition> getTaskCommandDefinitions() {
		return taskCommandDefinitions;
	}
	
	public void setConfigXmlPath(String configXmlPath) {
		this.configXmlPath = configXmlPath;
	}
	
	public String getConfigXmlPath() {
		return configXmlPath;
	}
	
	public void setConfigXmlStream(InputStream configXmlStream) {
		this.configXmlStream = configXmlStream;
	}
	
	public InputStream getConfigXmlStream() {
		return configXmlStream;
	}
	
	public void setConnectorMenuPath(String connectorMenuPath) {
		this.connectorMenuPath = connectorMenuPath;
	}
	
	public String getConnectorMenuPath() {
		return connectorMenuPath;
	}
	
	public void setIdentityCache(Cache<Object> identityCache) {
		this.identityCache = identityCache;
	}
	
}
