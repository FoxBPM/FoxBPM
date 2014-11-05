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
package org.foxbpm.connector.actorconnector.LastStepActorConnector;

import org.foxbpm.engine.exception.FoxBPMConnectorException;
import org.foxbpm.engine.impl.connector.ActorConnectorHandler;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.task.DelegateTask;

/**
 * 上一步骤处理者
 * 
 * @author yangguangftlp
 * @date 2014年7月14日
 */
public class LastStepActorConnector extends ActorConnectorHandler {
	
	private static final long serialVersionUID = 1L;
	
	public void assign(DelegateTask task) throws Exception {
		String userId = Authentication.getAuthenticatedUserId();
		if (StringUtil.isEmpty(userId)) {
			throw new FoxBPMConnectorException("处理人选择器(上一步骤处理者)上一步处理者未找到,请重新检查借点的人员配置! 节点编号：" + task.getNodeId());
		}
		task.setAssignee(userId);
	}
}