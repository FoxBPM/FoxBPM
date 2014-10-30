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
package org.foxbpm.connector.actorconnector.SelectDepartmentAndRole;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.foxbpm.engine.Constant;
import org.foxbpm.engine.exception.FoxBPMConnectorException;
import org.foxbpm.engine.impl.connector.ActorConnectorHandler;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.util.AssigneeUtil;
import org.foxbpm.engine.task.DelegateTask;

/**
 * 某部门某角色
 * 
 * @author yangguangftlp
 * @date 2014年7月8日
 */
public class SelectDepartmentAndRole extends ActorConnectorHandler {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6171386850998648156L;
	
	private java.lang.Object departmentId;
	
	private java.lang.Object roleId;
	
	public void assign(DelegateTask task) throws Exception {
		
		if (null == departmentId) {
			throw new FoxBPMConnectorException("departmentId is null!");
		}
		if (null == roleId) {
			throw new FoxBPMConnectorException("roleId is null!");
		}
		
		List<String> deptList = new ArrayList<String>();
		List<String> roleList = new ArrayList<String>();
		
		List<String> departmentIds = AssigneeUtil.executionExpressionObj(departmentId);
		List<String> roleIds = AssigneeUtil.executionExpressionObj(roleId);
		List<String> temp = null;
		// 获取部门下所有用户
		for (String id : departmentIds) {
			temp = Authentication.selectUserIdsByGroupIdAndType(id, Constant.DEPT_TYPE);;
			if (null != temp) {
				deptList.addAll(temp);
			}
		}
		// 获取角色下所有用户
		for (String id : roleIds) {
			temp = Authentication.selectUserIdsByGroupIdAndType(id, Constant.ROLE_TYPE);
			if (null != temp) {
				roleList.addAll(temp);
			}
		}
		
		// 取部门和角色同时存在的用户
		int min = Math.min(roleList.size(), deptList.size());
		// 自动过滤重复
		Set<String> userIds = new HashSet<String>();
		for (int i = 0; i < min; i++) {
			// 取在deptList中相同值
			if (deptList.contains(roleList.get(i))) {
				userIds.add(roleList.get(i));
			}// 取在roleList中相同值
			if (roleList.contains(deptList.get(i))) {
				userIds.add(deptList.get(i));
			}
		}
		// 当只有一个用户时直接交给该用户处理
		if (userIds.size() == 1) {
			task.setAssignee(userIds.toArray(new String[0])[0]);
		} else {
			task.addCandidateUsers(userIds);
		}
	}
	
	public void setDepartmentId(java.lang.Object departmentId) {
		this.departmentId = departmentId;
	}
	
	public void setRoleId(java.lang.Object roleId) {
		this.roleId = roleId;
	}
	
}