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
package org.foxbpm.engine.test.api.query;

import java.util.List;

import static org.junit.Assert.*;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.runtime.Token;
import org.foxbpm.engine.runtime.TokenQuery;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

/**
 * 令牌查询测试类
 * 
 * @author ych
 * 
 */
public class TokenQueryTest extends AbstractFoxBpmTestCase {

	/**
	 * 测试令牌查询的各个接口，保证结果正确性
	 */
	@Test
	@Deployment(resources = { "org/foxbpm/test/api/Test_RuntimeService_1.bpmn" })
	public void testTokenQuery() {

		// 启动流程实例
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Test_RuntimeService_1");
		String processInstanceId = processInstance.getId();

		TokenQuery tokenQuery = runtimeService.createTokenQuery();
		tokenQuery.processInstanceId(processInstanceId);
		List<Token> tokens = tokenQuery.list();
		assertEquals(1, tokens.size());

		Token token = tokens.get(0);
		assertEquals(processInstanceId, token.getProcessInstanceId());
		assertEquals("UserTask_1", token.getNodeId());
		assertNotNull(token.getId());
		assertNotNull(token.getNodeEnterTime());
		assertNull(token.getParentId());

		tokenQuery = runtimeService.createTokenQuery();
		tokenQuery.processInstanceId(processInstanceId);
		tokenQuery.tokenIsEnd();
		tokens = tokenQuery.list();
		assertEquals(0, tokens.size());

		tokenQuery.tokenNotEnd();
		tokens = tokenQuery.list();
		assertEquals(1, tokens.size());

	}
}
