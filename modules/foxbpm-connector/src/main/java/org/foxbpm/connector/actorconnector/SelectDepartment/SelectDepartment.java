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
package org.foxbpm.connector.actorconnector.SelectDepartment;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.foxbpm.engine.Constant;
import org.foxbpm.engine.impl.connector.ActorConnectorHandler;
import org.foxbpm.engine.impl.entity.GroupEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.util.AssigneeUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.task.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 选择部门(只包含子部门)
 * 
 * @author yangguangftlp
 * @date 2014年7月9日
 */
public class SelectDepartment extends ActorConnectorHandler {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2721883158970190680L;
	private static Logger LOG = LoggerFactory.getLogger(SelectDepartment.class);
	private java.lang.Object departmentId;
	
	public void assign(DelegateTask task) throws Exception {
		
		if (null == departmentId) {
			LOG.warn("处理人选择器(选择部门(只包含子部门))部门编号表达式为空 ! 节点编号：" + task.getNodeId());
			return;
		}
		List<String> departmentIds = AssigneeUtil.executionExpressionObj(departmentId);
		// 处理部门重复
		Set<String> depIdSet = new HashSet<String>();
		Iterator<String> iterator = departmentIds.iterator();
		while (iterator.hasNext()) {
			depIdSet.add(StringUtil.trim(iterator.next()));
		}
		// 存放查询组
		List<GroupEntity> groupList = null;
		// 存在不包含组本身的子组
		Set<String> groupIdList = null;
		for (String depId : depIdSet) {
			groupList = Authentication.findGroupChildMembersIncludeByGroupId(depId, Constant.DEPT_TYPE);
			if (null != groupList) {
				for (GroupEntity group : groupList) {
					if (!depIdSet.contains(group.getGroupId())) {
						if (null == groupIdList) {
							groupIdList = new HashSet<String>();
						}
						groupIdList.add(group.getGroupId());
					}
				}
			}
		}
		if (null != groupIdList) {
			for (String groupId : groupIdList) {
				task.addCandidateGroup(groupId, Constant.DEPT_TYPE);
			}
		}
	}
	
	public void setDepartmentId(java.lang.Object departmentId) {
		this.departmentId = departmentId;
	}
}