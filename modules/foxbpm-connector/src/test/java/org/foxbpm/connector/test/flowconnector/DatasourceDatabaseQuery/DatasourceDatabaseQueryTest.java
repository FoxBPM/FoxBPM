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
package org.foxbpm.connector.test.flowconnector.DatasourceDatabaseQuery;

import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

/**
 * 测试数据源查询sql
 * 
 * @author yangguangftlp
 * @date 2014年7月10日
 */
public class DatasourceDatabaseQueryTest extends AbstractFoxBpmTestCase {
	
	/**
	 * 测试进入节点时或离开时查询sql
	 * <p>1.使用场景：节点进入或离开时查询sql</p>
	 * <p>2.预置条件：<p>
	 *          1.在任务节点进入或离开时配置连接器
	 * <p>3.处理过程：首先，驱动流程进入节点或离开节点</p>
	 * <p>4.测试用例：</p>
	 * <p>		1.执行完成后，相应查看结果</p>
	 */
	@Test
	@Deployment(resources = { "org/foxbpm/connector/test/flowconnector/DatasourceDatabaseQuery/DatasourceDatabaseQueryTest_1.bpmn" })
	public void testJDBCDatabaseQuery() {
		Authentication.setAuthenticatedUserId("admin");
		// 启动流程
		runtimeService.startProcessInstanceByKey("DatasourceDatabaseQueryTest_1");
		// 涉及到变量结果
	}
}
