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
package org.foxbpm.connector.actorconnector.UserIdActorConnector;

import org.foxbpm.engine.impl.connector.ActorConnectorHandler;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.task.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserIdActorConnector extends ActorConnectorHandler {
	
	private static final long serialVersionUID = 1L;
	private static Logger LOG = LoggerFactory.getLogger(UserIdActorConnector.class);
	/** humanPerformer独占 potentialOwner共享 */
	private String assignType;
	public void setAssignType(String assignType) {
		this.assignType = assignType;
	}
	
	public void assign(DelegateTask task) throws Exception {
		
		String userId = Authentication.getAuthenticatedUserId();
		
		if (StringUtil.isEmpty(userId)) {
			LOG.warn("处理人选择器(系统登陆人)系统登录人未找到,请重新检查借点的人员配置! 节点编号：" + task.getNodeId());
			return;
		}
		
		if (assignType != null) {
			if (assignType.equals("humanPerformer")) {
				task.setAssignee(userId);
			} else {
				task.addCandidateUser(userId);
			}
		} else {
			task.setAssignee(userId);
		}
	}
	
}