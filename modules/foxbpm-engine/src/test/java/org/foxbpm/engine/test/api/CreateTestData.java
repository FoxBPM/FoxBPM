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
package org.foxbpm.engine.test.api;

import java.util.List;

import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskQuery;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

/**
 * 构造测试数据
 * @author ych
 *
 */
public class CreateTestData extends AbstractFoxBpmTestCase{

	
	@Test
	@Deployment(resources = { "org/foxbpm/test/api/Test_RuntimeService_1.bpmn"})
	public void testStartProcessInstanceByKey(){
		long begin = System.currentTimeMillis();
		Authentication.setAuthenticatedUserId("admin");
		/**
		 * 测试不带参数的启动流程
		 */
//		Date date = null;
//		for(int i = 0;i<500;i++){
//			//创建一个通用命令
//			ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
//			//设置流程名
//			expandTaskCommand.setProcessDefinitionKey("Test_RuntimeService_1");
//			//设置流程的业务关联键
//			expandTaskCommand.setBusinessKey("BK_testStartProcessInstanceByKey");
//			//命令类型，可以从流程引擎配置中查询   启动并提交为startandsubmit
//			expandTaskCommand.setCommandType("startandsubmit");
//			//设置提交人
//			expandTaskCommand.setInitiator("admin");
//			//设置命令的id,需和节点上配置的按钮编号对应，会执行按钮中的脚本。
//			expandTaskCommand.setTaskCommandId("HandleCommand_2");
//			
//			
//			Map<String, Object> mapVariables = new HashMap<String, Object>();
//			//设置变量，流程线条上用到，amount<300时走独占任务，否则都共享任务
//			int amount = 280+i;
//			mapVariables.put("amount", amount);
//			expandTaskCommand.setTransientVariables(mapVariables);
//			//执行这个启动并提交的命令，返回启动的流程实例
//			ProcessInstance processInstance = (ProcessInstance)taskService.expandTaskComplete(expandTaskCommand, null);
//			String processInstanceId = processInstance.getId();
//			//取得第六个流程启动时的时间，用来验证大于或小于开始时间的查询
//			if(i == 6){
//				date = processInstance.getStartTime();
//			}
//			//验证是否成功启动
////			assertNotNull(processInstanceId);
//			//暂停1000毫秒，用来验证order by 
////			Thread.sleep(1000);
//		}
		
		for(int i=0;i<1;i++){
			TaskQuery taskQuery = taskService.createTaskQuery();
			List<Task> tasks = taskQuery.taskAssignee("admin").taskCandidateUser("admin").taskNotEnd().orderByTaskCreateTime().desc().listPagination(1, 15);
			System.out.println(tasks.size());
		}
		
		System.out.println("*****时间："+(System.currentTimeMillis() - begin));
	}
}
