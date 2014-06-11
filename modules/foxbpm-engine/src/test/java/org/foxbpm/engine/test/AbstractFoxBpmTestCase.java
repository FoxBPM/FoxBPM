package org.foxbpm.engine.test;


import java.lang.reflect.Method;

import org.foxbpm.engine.IdentityService;
import org.foxbpm.engine.ModelService;
import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.RuntimeService;
import org.foxbpm.engine.TaskService;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.repository.DeploymentBuilder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
@Transactional  
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public abstract class AbstractFoxBpmTestCase extends AbstractJUnit4SpringContextTests  {

	@Autowired
	public static ProcessEngine processEngine;
	@Autowired
	protected ModelService modelService;
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected IdentityService identityService;
	
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
