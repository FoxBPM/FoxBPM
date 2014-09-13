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

import org.foxbpm.engine.exception.FoxBPMIllegalArgumentException;
import org.foxbpm.engine.identity.GroupDefinition;
import org.foxbpm.engine.impl.entity.GroupEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;

/**
 * 获取指定组类型下所有的组实体
 * @author ych
 *
 */
public class GetAllGroupByTypeCmd implements Command<List<GroupEntity>> {

	
	private String groupType;
	public GetAllGroupByTypeCmd(String groupType) {
		this.groupType = groupType;
	}
	
	@Override
	public List<GroupEntity> execute(CommandContext commandContext) {
		if(this.groupType == null || this.groupType.equals("")){
			throw new FoxBPMIllegalArgumentException("groupType不能为null");
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
			throw new FoxBPMIllegalArgumentException("未知的组类型："+this.groupType);
		}
		List<GroupEntity> groupEntitys = groupDefinition.selectAllGroup();
		return groupEntitys;
	}
	
}
