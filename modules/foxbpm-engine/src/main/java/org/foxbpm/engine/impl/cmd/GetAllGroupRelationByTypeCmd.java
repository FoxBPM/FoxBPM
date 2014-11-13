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
import org.foxbpm.engine.impl.identity.GroupRelationEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.util.ExceptionUtil;
import org.foxbpm.engine.impl.util.StringUtil;

/**
 * 获取指定组类型下所有的组与人员的关系映射
 * @author ych
 *
 */
public class GetAllGroupRelationByTypeCmd implements Command<List<GroupRelationEntity>> {

	
	private String groupType;
	public GetAllGroupRelationByTypeCmd(String groupType) {
		this.groupType = groupType;
	}
	
	 
	public List<GroupRelationEntity> execute(CommandContext commandContext) {
		if(StringUtil.isEmpty(groupType)){
			throw ExceptionUtil.getException("10601004");
		}
		List<GroupDefinition> groupDefinitins = commandContext.getProcessEngineConfigurationImpl().getGroupDefinitions();
		GroupDefinition groupDefinition = null;
		if(groupDefinitins != null && !groupDefinitins.isEmpty()){
			for(GroupDefinition tmpDefiniton : groupDefinitins){
				if(groupType.equals(tmpDefiniton.getType())){
					groupDefinition = tmpDefiniton;
					break;
				}
			}
		}
		if(groupDefinition == null){
			throw ExceptionUtil.getException("10602001");
		}
		List<GroupRelationEntity> results = groupDefinition.selectAllRelation();
		return results;
	}
	
}
