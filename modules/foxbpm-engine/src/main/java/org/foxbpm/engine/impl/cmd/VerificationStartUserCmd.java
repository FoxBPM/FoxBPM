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
 * @author ych
 */
package org.foxbpm.engine.impl.cmd;

import java.util.List;

import org.foxbpm.engine.Constant;
import org.foxbpm.engine.exception.FoxBPMBizException;
import org.foxbpm.engine.exception.FoxBPMIllegalArgumentException;
import org.foxbpm.engine.impl.entity.GroupEntity;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.identity.PotentialStarter;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.persistence.deploy.DeploymentManager;
import org.foxbpm.engine.impl.util.StringUtil;

/**
 * 验证用户是否由于权限发起对应流程 
 * 1.未配置启动人，则返回true
 * 2。启动人符合条件，则返回true
 * @author ych
 * 
 */
public class VerificationStartUserCmd implements Command<Boolean> {

	private String userId;
	private String processDefinitionKey;
	private String processDefinitionId;

	public VerificationStartUserCmd(String userId, String processDefinitionKey,
			String processDefinitionId) {
		this.userId = userId;
		this.processDefinitionKey = processDefinitionKey;
		this.processDefinitionId = processDefinitionId;
	}

	@Override
	public Boolean execute(CommandContext commandContext) {
		DeploymentManager deployCache = commandContext.getProcessEngineConfigurationImpl()
				.getDeploymentManager();
		ProcessDefinitionEntity processDefinition = null;
		if (StringUtil.isNotEmpty(processDefinitionId)) {
			processDefinition = deployCache.findDeployedProcessDefinitionById(processDefinitionId);
		} else if (StringUtil.isNotEmpty(processDefinitionKey)) {
			processDefinition = deployCache
					.findDeployedLatestProcessDefinitionByKey(processDefinitionKey);
		} else {
			throw new FoxBPMIllegalArgumentException("验证发起权限时，流程编号和流程唯一键不能同时为空");
		}
		
		List<PotentialStarter> processStarters = processDefinition.getPotentialStarters();
		//如果未配置，则默认所有人
		if(processStarters == null || processStarters.isEmpty()||processStarters.size()==0){
			return true;
		}
		
		List<GroupEntity> groups = Authentication.selectGroupByUserId(userId);
		for(PotentialStarter starter : processStarters){
			String tmpValue = null;
			try{
				tmpValue = (String)starter.getExpression().getValue(null);
			}catch(Exception ex){
				throw new FoxBPMBizException("流程启动人表达式配置错误：" + starter.getExpression().getExpressionText(),ex);
			}
			//表达式值为空，则不进行判断
			if(StringUtil.isEmpty(tmpValue)){
				break;
			}
			
			if(tmpValue.equals("foxbpm_all_user")){
				return true;
			}
			
			String tmpType = starter.getResourceType();
			if(Constant.USER_TYPE.equals(tmpType)){
				if(userId.equals(tmpValue)){
					return true;
				}
			}else{
				//循环当前userId所在的所有组,groupType和GroupId均相同时，返回true
				for(GroupEntity tmpGroup: groups){
					if(tmpType.equals(tmpGroup.getGroupType())){
						if(tmpGroup.getGroupId().equals(tmpValue)){
							return true;
						}
					}
				}
			}
		}

		return false;
	}
}
