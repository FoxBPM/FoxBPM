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
package org.foxbpm.engine.impl.cmd;

import java.util.List;

import org.foxbpm.engine.identity.GroupDefinition;
import org.foxbpm.engine.impl.entity.GroupEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.util.ExceptionUtil;
import org.foxbpm.engine.impl.util.StringUtil;

/**
 * 获取组编号下的所有子组（包含自身）
 * @author ych
 *
 */
public class FindGroupByIdCmd  implements Command<GroupEntity>{

	private String groupType;
	private String groupId;
	
	public FindGroupByIdCmd(String groupType,String groupId) {
		this.groupId = groupId;
		this.groupType = groupType;
	}
	
	 
	public GroupEntity execute(CommandContext commandContext) {
		if(StringUtil.isEmpty(groupType)){
			throw ExceptionUtil.getException("10601004");
		}
		
		if(StringUtil.isEmpty(groupId)){
			throw ExceptionUtil.getException("10601005");
		}
		List<GroupDefinition> groupDefinitions = commandContext.getProcessEngineConfigurationImpl().getGroupDefinitions();
		for(GroupDefinition groupDefinition : groupDefinitions){
			if(groupDefinition.getType().equals(groupType)){
				GroupEntity group = groupDefinition.selectGroupByGroupId(groupId);
				return group;
			}
		}
		throw ExceptionUtil.getException("10602001");
	}
}
