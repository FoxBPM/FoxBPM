package org.foxbpm.engine.impl.identity;

import org.foxbpm.engine.identity.IUserDefinitionFactory;
import org.foxbpm.engine.identity.UserDefinition;

public class UserDefinitionFactoryImpl implements IUserDefinitionFactory {

	@Override
	public UserDefinition getUserDefinition() {
		return new UserDefinitionImpl();
	}

}
