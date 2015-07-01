package org.foxbpm.engine.test.api;

import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

public class TenantTest extends AbstractFoxBpmTestCase {

	/**
	 * 测试多租户功能
	 * 启动带有多租户的流程定义
	 * 通过待办任务和流程追踪的接口查询（增加多租户标识），不传标识则能查所有，传正确标识能查到对应信息，错误标识则返回空
	 * 验证结果是否正确
	 */
	@Test
	@Deployment(resources = { "org/foxbpm/test/query/test_agentQuery_1.bpmn" })
	public void testTenant(){
		
	}
	
}
