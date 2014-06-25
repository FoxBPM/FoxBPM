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
package org.foxbpm.engine.impl.persistence;

import java.util.List;

import org.foxbpm.engine.impl.agent.AgentDetailsEntity;
import org.foxbpm.engine.impl.agent.AgentEntity;
import org.foxbpm.engine.impl.agent.AgentTo;

/**
 * 代理信息的实体管理器 处理包含代理信息和代理明细信息
 * 
 * @author ych
 * 
 */
public class AgentManager extends AbstractManager {

	@SuppressWarnings("unchecked")
	public List<AgentTo> getAgentTos(String userId) {
		return (List<AgentTo>) getSqlSession().selectListWithRawParameter("selectAgentToByUserId", userId);
	}

	public void saveAgentEntity(AgentEntity agentEntity) {
		insert(agentEntity);
		if (agentEntity.getAgentDetails() != null) {
			for (AgentDetailsEntity agentDetails : agentEntity.getAgentDetails()) {
				if (agentDetails.getAgentId() == null) {
					agentDetails.setAgentId(agentEntity.getId());
				}
				insert(agentDetails);
			}
		}
	}

	public void saveAgentDetailsEntity(AgentDetailsEntity agentDetailsEntity) {
		insert(agentDetailsEntity);
	}

	public void deleteAgentById(String agentId) {
		getSqlSession().delete("deleteAgentById", agentId);
		getSqlSession().delete("deleteAgentDetailsByAgentId", agentId);
	}

	public void deleteAgentDetailsById(String agentDetailsId) {
		getSqlSession().delete("deleteAgentDetailsById", agentDetailsId);
	}

	/**
	 * 查询代理实例
	 * 
	 * @param agentId
	 *            代理id
	 * @return 返回代理实体
	 */
	public AgentEntity queryAgentEntity(String agentUser) {
		AgentEntity agentEntity = (AgentEntity) getSqlSession().selectOne("selectAgentByAgentUser", agentUser);
		if (null != agentEntity) {
			@SuppressWarnings("unchecked")
			List<AgentDetailsEntity> agentDetails = (List<AgentDetailsEntity>) getSqlSession().selectListWithRawParameter("selectAgentDetailById", agentEntity.getId());
			if (null != agentDetails && !agentDetails.isEmpty()) {
				agentEntity.getAgentDetails().addAll(agentDetails);
			}
		}
		return agentEntity;
	}
}
