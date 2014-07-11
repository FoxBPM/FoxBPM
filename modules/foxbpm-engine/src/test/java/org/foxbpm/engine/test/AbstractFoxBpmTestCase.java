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
 * @author ych
 */
package org.foxbpm.engine.test;


import java.lang.reflect.Method;

import org.foxbpm.engine.IdentityService;
import org.foxbpm.engine.ModelService;
import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.RuntimeService;
import org.foxbpm.engine.TaskService;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.repository.DeploymentBuilder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * foxbpm基础测试类
 * 集成此类的子类，可使用@Deployment 和@Clear注解
 * 解释@Deployment会在方法执行前将deployment中的resource资源发布到数据库
 * 而clear注解可以在方法执行前，清空run_和def_开头的数据库表
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
@Transactional  
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public abstract class AbstractFoxBpmTestCase extends AbstractTransactionalJUnit4SpringContextTests  {

	@Autowired
	public ProcessEngine processEngine;
	@Autowired
	protected ModelService modelService;
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected IdentityService identityService;
	@Autowired
	protected JdbcTemplate jdbcTemplate; 
	
	@Rule  
    public TestName name = new TestName(); 
	
	@Before
	public void annotationDeploymentSetUp() throws Exception {
		
		Method method = null;
		try {
			method = this.getClass().getDeclaredMethod(name.getMethodName(), (Class<?>[]) null);
		} catch (Exception e) {
			throw new FoxBPMException("获取方法失败!", e);
		}
		if(method.isAnnotationPresent(Clear.class)){
//			cleanData();
		}
		Deployment deploymentAnnotation = method.getAnnotation(Deployment.class);
		if (deploymentAnnotation != null) {
			String[] resources = deploymentAnnotation.resources();
			if (resources.length == 0) {
				return;
			}
			DeploymentBuilder deploymentBuilder = null;//processEngine.getModelService().createDeployment().name("测试名称");
			//由于当前不支持一次发布多个流程定义
			for (String resource : resources) {
				deploymentBuilder = processEngine.getModelService().createDeployment().name("测试名称");
				deploymentBuilder.addClasspathResource(resource);
				deploymentBuilder.deploy();
			}
		}
	}
	
	protected void cleanData(){
		jdbcTemplate.execute("delete from foxbpm_def_bytearray");
		jdbcTemplate.execute("delete from foxbpm_def_deployment");
		jdbcTemplate.execute("delete from foxbpm_def_processdefinition");
		jdbcTemplate.execute("delete from foxbpm_run_processinstance");
		jdbcTemplate.execute("delete from foxbpm_run_task");
		jdbcTemplate.execute("delete from foxbpm_run_taskidentitylink");
		jdbcTemplate.execute("delete from foxbpm_run_token");
		jdbcTemplate.execute("delete from foxbpm_run_variable");
	}
}
