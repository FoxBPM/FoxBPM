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
package org.foxbpm.web.service.impl;

import org.foxbpm.engine.IdentityService;
import org.foxbpm.engine.ModelService;
import org.foxbpm.engine.RuntimeService;
import org.foxbpm.engine.TaskService;
import org.foxbpm.engine.impl.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 工作流抽象类
 * 
 * @author yangguangftlp
 * @date 2014年6月12日
 */
public abstract class AbstWorkFlowService {
	// 任务服务
	@Autowired
	protected TaskService taskService;
	// 模型服务
	@Autowired
	protected ModelService modelService;
	// 运行时服务
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	protected IdentityService identityService;

	/**
	 * 拼装参数like %xx%
	 * 
	 * @param param
	 *            参数
	 * @return 返回拼装的参数
	 */
	protected String assembleLikeParam(String param) {
		return "%" + param + "%";
	}

	/**
	 * 根据用户Id获取用户名称
	 * 
	 * @param userId
	 *            用户Id
	 * @return 返回用户名
	 */
	protected String getUserName(String userId) {
		if (userId == null || "".equals(userId)) {
			return "空用户名";
		}
		UserEntity tmpUser = identityService.getUser(userId);
		if (tmpUser != null) {
			return tmpUser.getUserName();
		} else {
			return "未知用户:" + userId;
		}

	}
}
