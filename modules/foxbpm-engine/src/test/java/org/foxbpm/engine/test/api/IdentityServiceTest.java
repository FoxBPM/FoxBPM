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

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.foxbpm.engine.Constant;
import org.foxbpm.engine.identity.User;
import org.foxbpm.engine.impl.agent.AgentDetailsEntity;
import org.foxbpm.engine.impl.agent.AgentEntity;
import org.foxbpm.engine.impl.agent.AgentTo;
import org.foxbpm.engine.impl.cache.CacheUtil;
import org.foxbpm.engine.impl.util.GuidUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.junit.Test;

public class IdentityServiceTest extends AbstractFoxBpmTestCase {
	
	/**
	 * 测试模糊查询用户接口
	 */
	@Test
	public void testGetUsers(){
		List<User> users = identityService.getUsers("%admi%", null);
		System.out.println(users.size());
		
		users = identityService.getUsers(null, "%管理%");
		System.out.println(users.size());
		
		users = identityService.getUsers(null, null);
		System.out.println(users.size());
		
	}

	/**
	 * 测试增加和更新代理信息
	 */
	@Test
	public void testSaveAgent(){
		
		//初始化数据
		String sqlInsertUser = "insert into au_userInfo(userId,USERNAME) VALUES ('admin2','管理员2')";
		jdbcTemplate.execute(sqlInsertUser);
		String agentId = GuidUtil.CreateGuid();
		
		/*******测试增加方法*************************/
		//构造代理主对象
		AgentEntity agentEntity = new AgentEntity();
		agentEntity.setId(agentId);
		
		Calendar cStart = Calendar.getInstance();
		cStart.add(Calendar.YEAR, -1);
		Calendar cEnd = Calendar.getInstance();
		cEnd.add(Calendar.YEAR, 1);
		
		agentEntity.setStartTime(cStart.getTime());
		agentEntity.setEndTime(cEnd.getTime());
		agentEntity.setAgentFrom("admin");
		
		//构造代理明细对象
		String agentDetailsId = GuidUtil.CreateGuid();
		AgentDetailsEntity agentDetailsEntity = new AgentDetailsEntity();
		agentDetailsEntity.setId(agentDetailsId);
		agentDetailsEntity.setAgentId(agentId);
		agentDetailsEntity.setAgentTo("admin2");
		agentDetailsEntity.setProcessKey(Constant.FOXBPM_ALL_FLOW);
		
		agentEntity.getAgentDetails().add(agentDetailsEntity);
		
		//保存代理信息
		identityService.addAgent(agentEntity);
		
		//查询出对应用户的代理信息
		User user = identityService.getUser("admin2");
		List<AgentTo> agentInfos = user.getAgentInfo();
		
		//验证是否正确
		assertEquals(1, agentInfos.size());
		
		/*******测试updateDetails*************************/
		//清楚缓存，为下一次测试做准备
		CacheUtil.clearIdentityCache();
		
		//重新构造代理明细，id相同
		agentDetailsEntity = new AgentDetailsEntity();
		agentDetailsEntity.setId(agentDetailsId);
		agentDetailsEntity.setAgentId(agentId);
		agentDetailsEntity.setAgentTo("admin2");
		agentDetailsEntity.setProcessKey("_all_");
		
		//更新代理明细
		identityService.updateAgentDetailsEntity(agentDetailsEntity);
		
		//重新查询用户代理信息
	    user = identityService.getUser("admin2");
		agentInfos = user.getAgentInfo();
		assertEquals(1, agentInfos.size());
		
		//验证是否update成功
		AgentTo agentTo = agentInfos.get(0);
		assertEquals("_all_", agentTo.getProcessKey());
		
		/**  测试updateAgent方法      **/
		
		CacheUtil.clearIdentityCache();
		//重新构造代理实体，id相同
		agentEntity = new AgentEntity();
		agentEntity.setId(agentId);
		
		cStart.add(Calendar.YEAR, 1);
		cEnd.add(Calendar.YEAR, 2);
		
		agentEntity.setStartTime(cStart.getTime());
		agentEntity.setEndTime(cEnd.getTime());
		agentEntity.setAgentFrom("admin");
	
		//更新代理
		identityService.updateAgentEntity(agentEntity);
		
		//重新查询用户代理信息
	    user = identityService.getUser("admin2");
		agentInfos = user.getAgentInfo();
		assertEquals(1, agentInfos.size());
		
		//验证是否更新成功！
		agentTo = agentInfos.get(0);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		assertEquals(cStart.get(Calendar.YEAR), StringUtil.getInt(formatter.format(agentTo.getStartTime())));
		
		/**测试删除代理明细***/
		identityService.deleteAgentDetails(agentDetailsId);
		
		CacheUtil.clearIdentityCache();
		user = identityService.getUser("admin2");
		agentInfos = user.getAgentInfo();
		assertEquals(0, agentInfos.size());
		
		/**测试增加代理明细**/
		agentDetailsEntity = new AgentDetailsEntity();
		agentDetailsEntity.setId(agentDetailsId);
		agentDetailsEntity.setAgentId(agentId);
		agentDetailsEntity.setAgentTo("admin2");
		agentDetailsEntity.setProcessKey("_all_");
		identityService.addAgentDetails(agentDetailsEntity);
		
		CacheUtil.clearIdentityCache();
		user = identityService.getUser("admin2");
		agentInfos = user.getAgentInfo();
		assertEquals(1, agentInfos.size());
		
		/*** 测试删除代理主对象** */
		identityService.deleteAgent(agentId);
		
		CacheUtil.clearIdentityCache();
		user = identityService.getUser("admin2");
		agentInfos = user.getAgentInfo();
		assertEquals(0, agentInfos.size());
	}
	
	
}
