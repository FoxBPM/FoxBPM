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
package org.foxbpm.connector.actorconnector.InitiatorActorConnector;

import org.foxbpm.engine.impl.connector.ActorConnectorHandler;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.task.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitiatorActorConnector extends ActorConnectorHandler {
	
	private static final long serialVersionUID = 1L;
	private static Logger LOG = LoggerFactory.getLogger(InitiatorActorConnector.class);
	
	public void assign(DelegateTask task) throws Exception {
		String initiator = task.getExecutionContext().getInitiator();
		if (StringUtil.isEmpty(initiator)) {
			LOG.warn("处理人选择器(发起人)流程的提交人未找到,请重新检查节点的人员配置! 节点编号：" + task.getNodeId());
			return;
		}
		task.setAssignee(initiator);
	}
	
}