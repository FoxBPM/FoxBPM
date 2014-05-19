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
package org.foxbpm.connector.actorconnector.SelectUserActorConnector;

import java.util.List;

import org.foxbpm.engine.exception.FoxBPMConnectorException;
import org.foxbpm.engine.impl.connector.ActorConnectorHandler;
import org.foxbpm.engine.impl.util.AssigneeUtil;
import org.foxbpm.engine.task.DelegateTask;

public class SelectUserActorConnector extends ActorConnectorHandler {

	private static final long serialVersionUID = 1L;

	private java.lang.Object userId;

	public void setUserId(java.lang.Object userId) {
		this.userId = userId;
	}
	
	
	/** humanPerformer独占 potentialOwner共享*/
	private String assignType;

	public void setAssignType(String assignType) {
		this.assignType = assignType;
	}
	
	


	@Override
	public void assign(DelegateTask task) throws Exception {
		

		List<String> userList = AssigneeUtil.executionExpressionObj(userId);
		
		if(assignType!=null){
			if(assignType.equals("humanPerformer")){
				if(userList.size()==1){
					task.setAssignee(userList.get(0));
				}
				else{
					throw new FoxBPMConnectorException("独占处理者不存在或者大于1,请重新检查节点配置.");
				}
			}
			else{
				for (String userId : userList) {
					task.addCandidateUser(userId);
				}
			}
		}

		

		
	}

}