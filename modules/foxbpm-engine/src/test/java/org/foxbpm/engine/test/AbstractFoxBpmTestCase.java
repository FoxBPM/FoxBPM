package org.foxbpm.engine.test;


import java.lang.reflect.Method;
import junit.framework.TestCase;

import org.foxbpm.engine.ModelService;
import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.RuntimeService;
import org.foxbpm.engine.TaskService;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.ProcessEngineConfigurationImpl;
import org.foxbpm.engine.impl.ProcessEngineImpl;
import org.foxbpm.engine.repository.DeploymentBuilder;
import org.foxbpm.spring.ProcessEngineConfigrationImplSpring;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public abstract class AbstractFoxBpmTestCase extends TestCase {

	public static ProcessEngine processEngine;
	protected String deploymentId;
	protected ProcessEngineConfigurationImpl processEngineConfiguration;
	protected ModelService modelService;
	protected RuntimeService runtimeService;
	protected TaskService taskService;
	

	protected void initializeServices() {
		ProcessEngineConfigrationImplSpring processEngineConfigrationImplSpring = new ProcessEngineConfigrationImplSpring();
		processEngine = processEngineConfigrationImplSpring.setProcessEngineName(ProcessEngineManagement.NAME_DEFAULT).buildProcessEngine();
		ProcessEngineManagement.setInit();
		processEngineConfiguration = ((ProcessEngineImpl) processEngine).getProcessEngineConfiguration();
		modelService = processEngine.getModelService();
		runtimeService = processEngine.getRuntimeService();
		taskService = processEngine.getTaskService();
	}
	
	public void runBare() throws Throwable {

		if(modelService == null){
			initializeServices();
		}
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(processEngine.getProcessEngineConfiguration().getDataSourceManage().getDataSource());
		TransactionTemplate t = new TransactionTemplate(dataSourceTransactionManager);
		t.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				try {
					
					execute();
					processEngine.getProcessEngineConfiguration().getDataSourceManage().getDataSource().getConnection().rollback();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
	
	public void execute(){
		try {
			annotationDeploymentSetUp(processEngine, getClass(), getName());
			super.runBare();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
	
	public void annotationDeploymentSetUp(ProcessEngine processEngine, Class<?> testClass, String methodName) throws Exception {
		Method method = null;
		try {
			method = testClass.getDeclaredMethod(methodName, (Class<?>[]) null);
		} catch (Exception e) {
			throw new FoxBPMException("获取方法失败!", e);
		}
		Deployment deploymentAnnotation = method.getAnnotation(Deployment.class);
		if (deploymentAnnotation != null) {
			String[] resources = deploymentAnnotation.resources();
			if (resources.length == 0) {
				return;
			}
			DeploymentBuilder deploymentBuilder = processEngine.getModelService().createDeployment().name("测试名称");
			for (String resource : resources) {
				deploymentBuilder.addClasspathResource(resource);
			}
			deploymentBuilder.deploy();
		}
	}
}
