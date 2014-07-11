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
package org.foxbpm.connector.flowconnector.SendMail;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.foxbpm.engine.impl.db.SqlCommand;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.util.DBUtils;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

/**
 * 发送邮件
 * 
 * @author yangguangftlp
 * @date 2014年7月1日
 */
public class SendMailTest extends AbstractFoxBpmTestCase {
	
	/**
	 * 测试进入节点时或离开时发邮件 
	 * <p>1.使用场景：节点进入或离开时发送邮件</p>
	 * <p>2.预置条件：<p>
	 *          1.在任务节点进入或离开时配置连接器
	 *          2.设置邮件标题和内容
	 *          3.系统配置发邮件信息
	 * <p>3.处理过程：首先，启动流程时触发发送邮件动作</p>
	 * <p>4.测试用例：</p>
	 * <p>		1.执行完成后，相应查看数据库表中邮件状态</p>
	 */
	@Test
	@Deployment(resources = { "org/foxbpm/connector/flowconnector/SendMail/SendMailTest_1.bpmn"})
	public void testSendMail(){
		Authentication.setAuthenticatedUserId("admin");
		// 启动流程
		runtimeService.startProcessInstanceByKey("SendMailTest_1");
		//查询
		String sqlSelectMail = "select * from foxbpm_mail where MAIL_NAME = 'SendMailTest_1'";
		String mailStatus = null;
		SqlCommand sqlCommand = new SqlCommand(DBUtils.getConnection());
		List<Map<String, Object>> rs = sqlCommand.queryForList(sqlSelectMail);
		if (null != rs && !rs.isEmpty()) {
			mailStatus = StringUtil.getString(rs.get(0).get("MAIL_STATUS"));
		}
		assertEquals("NOSEND", mailStatus);
	}
}