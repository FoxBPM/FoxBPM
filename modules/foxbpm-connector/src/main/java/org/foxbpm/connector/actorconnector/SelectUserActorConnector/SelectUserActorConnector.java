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

import org.foxbpm.engine.impl.connector.ActorConnectorHandler;
import org.foxbpm.engine.impl.util.AssigneeUtil;
import org.foxbpm.engine.task.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 选择用户
 * 
 * @author yangguangftlp
 * @date 2014年7月14日
 */
public class SelectUserActorConnector extends ActorConnectorHandler {
	
	private static final long serialVersionUID = 1L;
	private static Logger LOG = LoggerFactory.getLogger(SelectUserActorConnector.class);
	private java.lang.Object userId;
	
	public void setUserId(java.lang.Object userId) {
		this.userId = userId;
	}
	
	public void assign(DelegateTask task) throws Exception {
		if (null == userId) {
			LOG.warn("处理人选择器(选择用户)用户编号表达式为空 ! 节点编号：" + task.getNodeId());
			return;
		}
		List<String> userList = AssigneeUtil.executionExpressionObj(userId);
		if (userList.size() == 1) {
			task.setAssignee(userList.get(0));
		} else {
			task.addCandidateUsers(userList);
		}
	}
}