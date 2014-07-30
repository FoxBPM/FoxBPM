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
import org.foxbpm.engine.identity.Group;
import org.foxbpm.engine.identity.User;
import org.foxbpm.engine.impl.agent.AgentDetailsEntity;
import org.foxbpm.engine.impl.agent.AgentEntity;
import org.foxbpm.engine.impl.agent.AgentTo;
import org.foxbpm.engine.impl.cache.CacheUtil;
import org.foxbpm.engine.impl.db.Page;
import org.foxbpm.engine.impl.identity.GroupRelationEntity;
import org.foxbpm.engine.impl.util.GuidUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Clear;
import org.junit.Test;

public class IdentityServiceTest extends AbstractFoxBpmTestCase {
	
	/**
	 * 测试模糊查询用户接口
	 */
	@Test
	public void testGetUser(){
		String sqlInsertUser = "insert into au_userInfo(userId,USERNAME,EMAIL) VALUES ('test_admin2','管理员2','222@qq.com')";
		jdbcTemplate.execute(sqlInsertUser);
		
		User user = identityService.getUser("test_admin2");
		assertEquals("test_admin2",user.getUserId());
		assertEquals("管理员2",user.getUserName());
		assertEquals("222@qq.com",user.getEmail());
	}
	
	/**
	 * 测试无分页的获取用户集合
	 */
	@Test
	public void testGetUsersNoPage(){
		String sqlInsertUser = "insert into au_userInfo(userId,USERNAME,EMAIL) VALUES ('test_admin1','测试管理员1','222@qq.com')";
		String sqlInsertUser2 = "insert into au_userInfo(userId,USERNAME,EMAIL) VALUES ('test_admin2','测试管理员2','222@qq.com')";
		String sqlInsertUser3 = "insert into au_userInfo(userId,USERNAME,EMAIL) VALUES ('test_admin3','测试管理员3','222@qq.com')";
		jdbcTemplate.execute(sqlInsertUser);
		jdbcTemplate.execute(sqlInsertUser2);
		jdbcTemplate.execute(sqlInsertUser3);
		
		
		List<User> users = identityService.getUsers("%est_admin2%", "%测试%理员2%");
		assertEquals(1,users.size());
		
		users = identityService.getUsers("%est_admin2%", null);
		assertEquals(1,users.size());
		
		users = identityService.getUsers("%est_admin%", "%测试%理员%");
		assertEquals(3,users.size());
	}
	
	/**
	 * 测试有分页的获取用户集合
	 */
	@Test
	public void testGetUsers(){
		String sqlInsertUser = "insert into au_userInfo(userId,USERNAME,EMAIL) VALUES ('test_admin1','测试管理员1','222@qq.com')";
		String sqlInsertUser2 = "insert into au_userInfo(userId,USERNAME,EMAIL) VALUES ('test_admin2','测试管理员2','222@qq.com')";
		String sqlInsertUser3 = "insert into au_userInfo(userId,USERNAME,EMAIL) VALUES ('test_admin3','测试管理员3','222@qq.com')";
		jdbcTemplate.execute(sqlInsertUser);
		jdbcTemplate.execute(sqlInsertUser2);
		jdbcTemplate.execute(sqlInsertUser3);
		
		Page page = new Page(0, 1);
		List<User> users = identityService.getUsers("%est_admin%", "%测试%理员%",page);
		assertEquals(1,users.size());
	}
	
	/**
	 * 测试获取指定查询的用户数量
	 */
	@Test
	public void testGetUsersCount(){
		String sqlInsertUser = "insert into au_userInfo(userId,USERNAME,EMAIL) VALUES ('test_admin1','测试管理员1','222@qq.com')";
		String sqlInsertUser2 = "insert into au_userInfo(userId,USERNAME,EMAIL) VALUES ('test_admin2','测试管理员2','222@qq.com')";
		String sqlInsertUser3 = "insert into au_userInfo(userId,USERNAME,EMAIL) VALUES ('test_admin3','测试管理员3','222@qq.com')";
		jdbcTemplate.execute(sqlInsertUser);
		jdbcTemplate.execute(sqlInsertUser2);
		jdbcTemplate.execute(sqlInsertUser3);
		
		long count = identityService.getUsersCount("%est_admin%", "%测试%理员%");
		assertEquals(3,count);
	}

	/**
	 * 测试增加和更新代理信息
	 */
	@Test
	public void testAgent(){
		
		//初始化数据
		String sqlInsertUser = "insert into au_userInfo(userId,USERNAME) VALUES ('test_admin2','管理员2')";
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
		agentDetailsEntity.setAgentTo("test_admin2");
		agentDetailsEntity.setProcessKey(Constant.FOXBPM_ALL_FLOW);
		
		agentEntity.getAgentDetails().add(agentDetailsEntity);
		
		//保存代理信息
		identityService.addAgent(agentEntity);
		
		//查询出对应用户的代理信息
		User user = identityService.getUser("test_admin2");
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
		agentDetailsEntity.setAgentTo("test_admin2");
		agentDetailsEntity.setProcessKey(Constant.FOXBPM_ALL_FLOW);
		
		//更新代理明细
		identityService.updateAgentDetailsEntity(agentDetailsEntity);
		
		//重新查询用户代理信息
	    user = identityService.getUser("test_admin2");
		agentInfos = user.getAgentInfo();
		assertEquals(1, agentInfos.size());
		
		//验证是否update成功
		AgentTo agentTo = agentInfos.get(0);
		assertEquals(Constant.FOXBPM_ALL_FLOW, agentTo.getProcessKey());
		
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
	    user = identityService.getUser("test_admin2");
		agentInfos = user.getAgentInfo();
		assertEquals(1, agentInfos.size());
		
		//验证是否更新成功！
		agentTo = agentInfos.get(0);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		assertEquals(cStart.get(Calendar.YEAR), StringUtil.getInt(formatter.format(agentTo.getStartTime())));
		
		/**测试删除代理明细***/
		identityService.deleteAgentDetails(agentDetailsId);
		
		CacheUtil.clearIdentityCache();
		user = identityService.getUser("test_admin2");
		agentInfos = user.getAgentInfo();
		assertEquals(0, agentInfos.size());
		
		/**测试增加代理明细**/
		agentDetailsEntity = new AgentDetailsEntity();
		agentDetailsEntity.setId(agentDetailsId);
		agentDetailsEntity.setAgentId(agentId);
		agentDetailsEntity.setAgentTo("test_admin2");
		agentDetailsEntity.setProcessKey(Constant.FOXBPM_ALL_FLOW);
		identityService.addAgentDetails(agentDetailsEntity);
		
		CacheUtil.clearIdentityCache();
		user = identityService.getUser("test_admin2");
		agentInfos = user.getAgentInfo();
		assertEquals(1, agentInfos.size());
		
		/*** 测试删除代理主对象** */
		identityService.deleteAgent(agentId);
		
		CacheUtil.clearIdentityCache();
		user = identityService.getUser("test_admin2");
		agentInfos = user.getAgentInfo();
		assertEquals(0, agentInfos.size());
	}
	
	/**
	 * 测试获取所有组实体
	 */
	@Test
	@Clear(tables={"au_roleinfo","au_orginfo"})
	public void testGetAllGroup(){
		String insertRole = "insert into au_roleinfo(roleid,rolename) values('role1001','角色名称')";
		String insertDept = "insert into au_orginfo(orgid,suporgid,orgname) values('dept1002','1000','部门名称')";
		jdbcTemplate.execute(insertDept);
		jdbcTemplate.execute(insertRole);
		
		List<Group> groups = identityService.getAllGroup("role");
		assertEquals(1,groups.size());
		Group role = groups.get(0);
		assertEquals("role1001", role.getGroupId());
		assertEquals("角色名称", role.getGroupName());
		assertEquals("role", role.getGroupType());
		assertEquals("", role.getSupGroupId());
		
		groups = identityService.getAllGroup("dept");
		assertEquals(1,groups.size());
		Group dept = groups.get(0);
		assertEquals("dept1002", dept.getGroupId());
		assertEquals("部门名称", dept.getGroupName());
		assertEquals("dept", dept.getGroupType());
		assertEquals("1000", dept.getSupGroupId());
	}
	
	/**
	 * 测试获取所有组实体
	 */
	@Test
	@Clear(tables={"au_group_relation"})
	public void testGetAllGroupRelation(){
		String insertRelationRole = "insert into au_group_Relation(guid,userid,groupid,groupType) values('roleRelation','admin','groupRoleid','role') ";
		String insertRelationDept = "insert into au_group_Relation(guid,userid,groupid,groupType) values('deptRelation','admin','groupDeptid','dept') ";
		jdbcTemplate.execute(insertRelationRole);
		jdbcTemplate.execute(insertRelationDept);
		
		List<GroupRelationEntity> roleRelations = identityService.getAllGroupRelation("role");
		assertEquals(1,roleRelations.size());
		GroupRelationEntity roleRelationEntity = roleRelations.get(0);
		assertEquals("groupRoleid", roleRelationEntity.getGroupId());
		assertEquals("admin", roleRelationEntity.getUserId());
		assertEquals("role", roleRelationEntity.getGroupType());
		
		roleRelations = identityService.getAllGroupRelation("dept");
		assertEquals(1,roleRelations.size());
		GroupRelationEntity deptRelationEntity = roleRelations.get(0);
		assertEquals("groupDeptid", deptRelationEntity.getGroupId());
		assertEquals("admin", deptRelationEntity.getUserId());
		assertEquals("dept", deptRelationEntity.getGroupType());
	}
	
	
}
