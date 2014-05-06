package org.foxbpm.engine.impl.identity;

import org.foxbpm.engine.identity.Group;

public class GroupFactory {
	
	public static Group createGroup(String type){
		if("role".equals(type)){
			return new RoleEntity();
		}
		return new RoleEntity();
	}

}
