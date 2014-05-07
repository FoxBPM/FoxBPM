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
 */
package org.foxbpm.engine.impl.identity;

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.identity.Group;
import org.foxbpm.engine.identity.GroupDefinition;
import org.foxbpm.engine.impl.Context;

public abstract class Authentication {

	static ThreadLocal<String> authenticatedUserIdThreadLocal = new ThreadLocal<String>();

	public static void setAuthenticatedUserId(String authenticatedUserId) {
		authenticatedUserIdThreadLocal.set(authenticatedUserId);
	}

	public static String getAuthenticatedUserId() {
		return authenticatedUserIdThreadLocal.get();
	}
	
	public static List<Group> selectGroupByUserId(String userId) {
		List<GroupDefinition> groupDefinitions = Context.getCommandContext().getProcessEngineConfigurationImpl().getGroupDefinitions();
		List<Group> result = new ArrayList<Group>();
		for(GroupDefinition groupDefinition : groupDefinitions){
			List<Group> tmpGroups = groupDefinition.selectGroupByUserId(userId);
			if(tmpGroups != null && tmpGroups.size() >0){
				result.addAll(tmpGroups);
			}
		}
		return result;
	}

}
