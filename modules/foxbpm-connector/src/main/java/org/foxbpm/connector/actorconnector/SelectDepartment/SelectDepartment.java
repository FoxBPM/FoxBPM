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
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.foxbpm.connector.common.constant.Constants;
import org.foxbpm.engine.Constant;
import org.foxbpm.engine.exception.FoxBPMConnectorException;
import org.foxbpm.engine.impl.connector.ActorConnectorHandler;
import org.foxbpm.engine.impl.entity.GroupEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.task.DelegateTask;

/**
 * 选择部门
 * 
 * @author yangguangftlp
 * @date 2014年7月9日
 */
public class SelectDepartment extends ActorConnectorHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2721883158970190680L;
	private java.lang.String departmentId;

	public void assign(DelegateTask task) throws Exception {

		if (StringUtil.isEmpty(StringUtil.trim(departmentId))) {
			throw new FoxBPMConnectorException("departmentId is null!");
		}
		// 处理部门重复
		StringTokenizer st = new StringTokenizer(StringUtil.trim(departmentId), Constants.COMMA);
		Set<String> depIdSet = new HashSet<String>();
		while (st.hasMoreTokens()) {
			depIdSet.add(StringUtil.trim(st.nextToken()));
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

	public void setDepartmentId(java.lang.String departmentId) {
		this.departmentId = departmentId;
	}
}