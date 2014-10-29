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
package org.foxbpm.connector.actorconnector.SelectTaskByLeast;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.foxbpm.connector.common.constant.Constants;
import org.foxbpm.engine.TaskService;
import org.foxbpm.engine.exception.FoxBPMConnectorException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.connector.ActorConnectorHandler;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.task.DelegateTask;
import org.foxbpm.engine.task.TaskQuery;

/**
 * 选择任务最少者
 * 
 * @author yangguangftlp
 * @date 2014年7月7日
 */
public class SelectTaskByLeast extends ActorConnectorHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7475673707019152696L;
	private java.lang.String userId;

	public void assign(DelegateTask task) throws Exception {

		if (StringUtil.isEmpty(StringUtil.trim(userId))) {
			throw new FoxBPMConnectorException("userId is null!");
		}
		// 获取待分配的用户
		List<String> userIds = Arrays.asList(StringUtil.trim(userId).split(Constants.COMMA));
		if (userIds.size() == 1) {
			// 直接分配
			task.setAssignee(StringUtil.trim(userId));
		} else {
			// 过滤重复用户id
			Set<String> userIdSet = new HashSet<String>();
			for (String id : userIds) {
				userIdSet.add(StringUtil.trim(id));
			}
			// 处理用户id重复输入
//			Map<String, Integer> userTasks = new HashMap<String, Integer>();
			TaskService taskService = Context.getProcessEngineConfiguration().getTaskService();
			TaskQuery taskQuery = null;
			int minTaskCount= 0 ;
			String minTaskUserId = "";
			// 记录每个用户的任务数
			for (String id : userIdSet) {
				taskQuery = taskService.createTaskQuery();
				taskQuery.taskAssignee(id);
				taskQuery.taskCandidateUser(id);
				taskQuery.taskNotEnd();
				// 如果数据量大 long 转换int 可能存在问题
//				userTasks.put(id, StringUtil.getInt(taskQuery.count()));
				if(minTaskCount == 0){
					minTaskCount = (int)taskQuery.count();
				}
				if(taskQuery.count() < minTaskCount){
					minTaskUserId = id;
				}
			}
			// 排序
//			List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(userTasks.entrySet());
//			Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
//				public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
//					return (o1.getValue()).compareTo(o2.getValue());
//				}
//			});
//			task.setAssignee(list.get(0).getKey());
			task.setAssignee(minTaskUserId);
		}
	}

	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}

}