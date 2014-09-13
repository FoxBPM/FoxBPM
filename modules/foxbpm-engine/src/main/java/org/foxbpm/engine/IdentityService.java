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
 * @author kenshin
 * @author ych
 */
package org.foxbpm.engine;

import java.util.List;

import org.foxbpm.engine.identity.GroupDefinition;
import org.foxbpm.engine.impl.agent.AgentDetailsEntity;
import org.foxbpm.engine.impl.agent.AgentEntity;
import org.foxbpm.engine.impl.db.Page;
import org.foxbpm.engine.impl.entity.GroupEntity;
import org.foxbpm.engine.impl.entity.UserEntity;
import org.foxbpm.engine.impl.identity.GroupRelationEntity;

/**
 * foxbpm中与用户相关的操作均放在此service里
 * 
 * @author ych
 * 
 */
public interface IdentityService {
	
	/**
	 * 根据用户编号查询用户
	 * 
	 * @param userId
	 *            用户编号 主键
	 * @return
	 */
	UserEntity getUser(String userId);
	
	/**
	 * 根据编号或名称模糊匹配
	 * 
	 * @param idLike
	 *            示例： %200802%
	 * @param nameLike
	 *            示例： %张%
	 * 
	 * @return 参数可为null,参数之间为and关系，如果都为null代表查询所有,
	 *         此结果集中的user对象只包含数据库中的基础属性，没有其他扩展属性，如代理信息等
	 */
	List<UserEntity> getUsers(String idLike, String nameLike);
	
	/**
	 * 查询用户
	 * 
	 * @param idLike
	 *            示例： %200802%
	 * @param nameLike
	 *            示例： %张%
	 * @page 分页对象
	 * @return <p>
	 *         返回查询结果
	 *         </p>
	 *         <p>
	 *         此结果集中的user对象只包含数据库中的基础属性，没有其他扩展属性，如代理信息等
	 *         </p>
	 */
	List<UserEntity> getUsers(String idLike, String nameLike, Page page);
	
	/**
	 * 查询用户数
	 * 
	 * @param idLike
	 *            示例： %200802%
	 * @param nameLike
	 *            示例： %张%
	 * @return 返回查询结果数量
	 */
	Long getUsersCount(String idLike, String nameLike);
	
	/**
	 * 更新用户
	 * 
	 * @param user
	 */
	public void updateUser(UserEntity user);
	
	/**
	 * 添加用户
	 * 
	 * @param user
	 */
	public void addUser(UserEntity user);
	
	/**
	 * 删除用户
	 * 
	 * @param user
	 */
	public void deleteUser(String userId);
	
	/**
	 * 增加代理 会级联插入对象中的代理明细信息
	 * 
	 * @param agentInfo
	 *            代理实体
	 */
	void addAgent(AgentEntity agentInfo);
	
	/**
	 * 增加代理明细
	 * 
	 * @param agentDetails
	 *            代理明细
	 */
	void addAgentDetails(AgentDetailsEntity agentDetails);
	
	/**
	 * 更新代理主表信息，{不会}级联更新明细
	 * 
	 * @param agentInfo
	 */
	void updateAgentEntity(AgentEntity agentInfo);
	
	/**
	 * 更新代理明细
	 * 
	 * @param agentDetails
	 */
	void updateAgentDetailsEntity(AgentDetailsEntity agentDetails);
	
	/**
	 * 删除代理信息， 会级联删除代理明细
	 * 
	 * @param agentId
	 */
	void deleteAgent(String agentId);
	
	/**
	 * 删除代理明细
	 * 
	 * @param agentDetailsId
	 */
	void deleteAgentDetails(String agentDetailsId);
	
	/**
	 * 查询代理
	 * 
	 * @param agentUser
	 *            代理者
	 * @return 返回代理对象
	 */
	AgentEntity queryAgent(String agentUser);
	
	/**
	 * 查询某组类型下所有的组实体
	 * <p>
	 * 如： identityService.getAllGroup("dept");查询所有的部门
	 * </p>
	 * 
	 * @param groupType
	 * @return
	 */
	List<GroupEntity> getAllGroup(String groupType);
	
	/**
	 * 查询某组类型下所有的组与人员的关系映射
	 * <p>
	 * 如： identityService.getAllGroupRelation("dept");查询所有的部门与人员的关系映射
	 * </p>
	 * 
	 * @param groupType
	 * @return
	 */
	List<GroupRelationEntity> getAllGroupRelation(String groupType);
	
	/**
	 * 获取所有的组定义 如["角色"，"部门"]
	 * 
	 * @return
	 */
	List<GroupDefinition> getAllGroupDefinitions();
	
	/**
	 * 获取指定的组定义
	 * 
	 */
	GroupDefinition getGroupDefinition(String groupType);
	
}
