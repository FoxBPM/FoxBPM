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

import org.foxbpm.engine.identity.User;
import org.foxbpm.engine.impl.agent.AgentDetailsEntity;
import org.foxbpm.engine.impl.agent.AgentEntity;

/**
 * foxbpm中与用户相关的操作均放在此service里
 * @author ych
 *
 */
public interface IdentityService {
	
	/**
	 * 根据用户编号查询用户
	 * @param userId 用户编号 主键
	 * @return
	 */
	User getUser(String userId);
	
	/**
	 * 根据编号或名称模糊匹配
	 * @param idLike 示例： %200802%
	 * @param nameLike 示例： %张%
	 * 
	 * @return 参数可为null,参数之间为or关系，如果都为null代表查询所有
	 */
	List<User> getUsers(String idLike,String nameLike);
	
	/**
	 * 增加代理
	 * 会级联插入对象中的代理明细信息
	 * @param agentInfo 代理实体
	 */
	void addAgent(AgentEntity agentInfo);
	
	/**
	 * 增加代理明细
	 * @param agentDetails 代理明细
	 */
	void addAgentDetails(AgentDetailsEntity agentDetails);
	
	/**
	 * 更新代理主表信息，{不会}级联更新明细
	 * @param agentInfo
	 */
	void updateAgentEntity(AgentEntity agentInfo);
	
	/**
	 * 更新代理明细
	 * @param agentDetails
	 */
	void updateAgentDetailsEntity(AgentDetailsEntity agentDetails);
	
	/**
	 * 删除代理信息，
	 * 会级联删除代理明细
	 * @param agentId
	 */
	void deleteAgent(String agentId);
	
	/**
	 * 删除代理明细
	 * @param agentDetailsId
	 */
	void deleteAgentDetails(String agentDetailsId);
}
