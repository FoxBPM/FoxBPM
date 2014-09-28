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
 * @author MAENLIANG
 */
package org.foxbpm.social.impl.cmd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.impl.db.ListQueryParameterObject;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.social.impl.entity.SocialMessageInfo;

/**
 * 根据任务ID，获取及时回复信息，起始消息时间为用户登录时的时间，最后一次加载所有消息时需要刷新此时间
 * @author MAENLIANG
 *
 */
public class FindReplySocialMessageInfoCmd implements Command<List<SocialMessageInfo>> {

	/**
	 * 当前的任务Id
	 */
	private String taskId;
	/**
	 * 当前登录用户
	 */
	private String userId;
	
	/**
	 * 用户登录系统的时间
	 */
	private String loginTime;
	public FindReplySocialMessageInfoCmd(String taskId,String userId,String loginTime) {
		this.taskId = taskId;
		this.userId = userId;
		this.loginTime = loginTime;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SocialMessageInfo> execute(CommandContext commandContext) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("taskId", taskId); 
		queryMap.put("userId", userId); 
		queryMap.put("loginTime", loginTime); 
		ListQueryParameterObject queryParams = new ListQueryParameterObject();
		queryParams.setParameter(queryMap);
		return (List<SocialMessageInfo>) commandContext.getSqlSession().selectList("findReplaySocialMessageInfo", queryParams);
	}

}
