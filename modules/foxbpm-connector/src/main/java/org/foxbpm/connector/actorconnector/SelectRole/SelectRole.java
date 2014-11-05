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
package org.foxbpm.connector.actorconnector.SelectRole;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.foxbpm.engine.Constant;
import org.foxbpm.engine.exception.FoxBPMConnectorException;
import org.foxbpm.engine.impl.connector.ActorConnectorHandler;
import org.foxbpm.engine.impl.util.AssigneeUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.task.DelegateTask;
import org.foxbpm.engine.task.IdentityLinkType;

/**
 * 角色选择
 * 
 * @author yangguangftlp
 * @date 2014年7月7日
 */
public class SelectRole extends ActorConnectorHandler {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5466313199990930905L;
	
	private java.lang.Object roleId;
	
	public void assign(DelegateTask task) throws Exception {
		if (null == roleId) {
			throw new FoxBPMConnectorException("处理人选择器(选择角色)角色编号表达式为空 ! 节点编号：" + task.getNodeId());
		}
		// 处理角色重复
		List<String> userList = AssigneeUtil.executionExpressionObj(roleId);
		Set<String> roleIdSet = new HashSet<String>();
		Iterator<String> iterator = userList.iterator();
		while (iterator.hasNext()) {
			roleIdSet.add(StringUtil.trim(iterator.next()));
		}
		// 处理角色
		for (String id : roleIdSet) {
			task.addGroupIdentityLink(id, Constant.ROLE_TYPE, IdentityLinkType.CANDIDATE);
		}
	}
	
	public void setRoleId(java.lang.Object roleId) {
		this.roleId = roleId;
	}
	
}