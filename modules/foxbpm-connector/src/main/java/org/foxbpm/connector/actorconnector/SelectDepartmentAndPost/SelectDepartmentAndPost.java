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
package org.foxbpm.connector.actorconnector.SelectDepartmentAndPost;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.foxbpm.engine.Constant;
import org.foxbpm.engine.impl.connector.ActorConnectorHandler;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.util.AssigneeUtil;
import org.foxbpm.engine.task.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 某部门某岗位
 * 
 * @author thinkgem
 * @date 2015-3-23 copy form SelectDepartmentAndRole
 */
public class SelectDepartmentAndPost extends ActorConnectorHandler {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6171386850998648156L;
	private static Logger LOG = LoggerFactory.getLogger(SelectDepartmentAndPost.class);
	private java.lang.Object departmentId;
	
	private java.lang.Object postId;
	
	public void assign(DelegateTask task) throws Exception {
		
		if (null == departmentId) {
			LOG.warn("处理人选择器(选择某部门和某岗位)部门编号表达式为空 ! 节点编号：" + task.getNodeId());
			return;
		}
		if (null == postId) {
			LOG.warn("处理人选择器(选择某部门和某岗位)岗位编号表达式为空 ! 节点编号：" + task.getNodeId());
			return;
		} 
		
		List<String> deptList = new ArrayList<String>();
		List<String> postList = new ArrayList<String>();
		
		List<String> departmentIds = AssigneeUtil.executionExpressionObj(departmentId);
		List<String> postIds = AssigneeUtil.executionExpressionObj(postId);
		List<String> temp = null;
		// 获取部门下所有用户
		for (String id : departmentIds) {
			temp = Authentication.selectUserIdsByGroupIdAndType(id, Constant.DEPT_TYPE);;
			if (null != temp) {
				deptList.addAll(temp);
			}
		}
		// 获取岗位下所有用户
		for (String id : postIds) {
			temp = Authentication.selectUserIdsByGroupIdAndType(id, Constant.POST_TYPE);
			if (null != temp) {
				postList.addAll(temp);
			}
		}
		
		// 取部门和岗位同时存在的用户
		int min = Math.min(postList.size(), deptList.size());
		// 自动过滤重复
		Set<String> userIds = new HashSet<String>();
		for (int i = 0; i < min; i++) {
			// 取在deptList中相同值
			if (deptList.contains(postList.get(i))) {
				userIds.add(postList.get(i));
			}// 取在postList中相同值
			if (postList.contains(deptList.get(i))) {
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
	
	public void setPostId(java.lang.Object postId) {
		this.postId = postId;
	}
	
}