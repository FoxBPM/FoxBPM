package org.foxbpm.engine.impl.persistence;

import java.util.List;

import org.foxbpm.engine.impl.agent.AgentTo;

public class AgentManager extends AbstractManager{

	@SuppressWarnings("unchecked")
	public List<AgentTo> getAgentTos(String userId){
		return (List<AgentTo>)getSqlSession().selectListWithRawParameter("selectAgentToByUserId", userId);
	}
}
