package org.foxbpm.engine.impl.identity;

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.Constant;
import org.foxbpm.engine.identity.GroupDefinition;
import org.foxbpm.engine.identity.IGroupDefinitionFactory;

public class GroupDefinitionFactoryImpl implements IGroupDefinitionFactory {

	@Override
	public List<GroupDefinition> getGroupDefinition() {
		List<GroupDefinition> groupDefinitions = new ArrayList<GroupDefinition>();
		groupDefinitions.add(new GroupDeptImpl(Constant.DEPT_TYPE,"部门"));
		groupDefinitions.add(new GroupRoleImpl(Constant.ROLE_TYPE,"角色"));
		return groupDefinitions;
	}

}
