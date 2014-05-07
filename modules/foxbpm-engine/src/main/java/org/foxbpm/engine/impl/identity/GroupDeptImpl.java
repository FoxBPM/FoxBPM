package org.foxbpm.engine.impl.identity;

import java.util.List;

import org.foxbpm.engine.identity.Group;
import org.foxbpm.engine.identity.GroupDefinition;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.sqlsession.ISqlSession;

public class GroupDeptImpl implements GroupDefinition {

	@SuppressWarnings("unchecked")
	public List<Group> selectGroupByUserId(String userId) {
		ISqlSession sqlsession = Context.getCommandContext().getSqlSession();
		List<Group> groups = (List<Group>)sqlsession.selectList("selectDeptByUserId", userId);
		return groups;
	}

}
