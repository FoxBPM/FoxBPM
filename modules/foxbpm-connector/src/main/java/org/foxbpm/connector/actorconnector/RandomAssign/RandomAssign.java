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
 * @author yangguangftlp
 */
package org.foxbpm.connector.actorconnector.RandomAssign;

import java.util.List;
import java.util.Random;

import org.foxbpm.engine.impl.connector.ActorConnectorHandler;
import org.foxbpm.engine.impl.util.AssigneeUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.task.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 随机分配任务
 * 
 * @author yangguangftlp
 * @date 2014年7月7日
 */
public class RandomAssign extends ActorConnectorHandler {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 325047500772052099L;
	private java.lang.Object userId;
	private static Logger LOG = LoggerFactory.getLogger(RandomAssign.class);
	public void assign(DelegateTask task) throws Exception {
		if (null == userId) {
			LOG.warn("处理人选择器(资源中随机分配)用户编号表达式为空 ! 节点编号：" + task.getNodeId());
			return;
		}
		// 获取待分配的用户
		List<String> userIds = AssigneeUtil.executionExpressionObj(userId);
		if (userIds.size() == 1) {
			task.setAssignee(StringUtil.trim(userIds.get(0)));
		} else {
			// 随机产生
			Random random = new Random();
			int index = random.nextInt(userIds.size());
			task.setAssignee(StringUtil.trim(userIds.get(index)));
		}
		
	}
	
	public void setUserId(java.lang.Object userId) {
		this.userId = userId;
	}
	
}