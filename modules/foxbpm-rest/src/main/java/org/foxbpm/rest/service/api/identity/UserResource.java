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
package org.foxbpm.rest.service.api.identity;

import java.util.Map;

import org.foxbpm.engine.IdentityService;
import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.impl.cache.CacheUtil;
import org.foxbpm.engine.impl.entity.UserEntity;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.rest.common.api.AbstractRestResource;
import org.foxbpm.rest.common.api.FoxBpmUtil;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

/**
 * 常量类
 * 
 * @author yangguangftlp
 * @date 2014年8月26日
 */
public class UserResource extends AbstractRestResource {
	
	@Get
	public Object getUserResource() {
		// 获取参数
		String userId = getAttribute("userId");
		if (StringUtil.isNotEmpty(userId)) {
			// 获取引擎
			ProcessEngine processEngine = FoxBpmUtil.getProcessEngine();
			// 获取身份服务
			IdentityService identityService = processEngine.getIdentityService();
			UserEntity userEntity = identityService.getUser(userId);
			if (null != userEntity) {
				return userEntity.getPersistentState();
			}
		}
		return null;
	}
	@Put
	public String updateUserResource(Representation entity) {
		// 获取参数
		String userId = getAttribute("userId");
		Map<String, String> paramsMap = getRequestParams(entity);
		String userName = paramsMap.get("name");
		String email = paramsMap.get("email");
		String tel = paramsMap.get("tel");
		if (StringUtil.isNotEmpty(userId)) {
			UserEntity userEntity = new UserEntity(userId);
			if (StringUtil.isNotEmpty(userName)) {
				userEntity.setUserName(userName);
			}
			if (StringUtil.isNotEmpty(email)) {
				userEntity.setEmail(email);
				
			}
			if (StringUtil.isNotEmpty(tel)) {
				userEntity.setTel(tel);
			}
			// 获取引擎
			ProcessEngine processEngine = FoxBpmUtil.getProcessEngine();
			// 获取身份服务
			IdentityService identityService = processEngine.getIdentityService();
			// 构造用户信息
			identityService.updateUser(userEntity);
			CacheUtil.getIdentityCache().remove("user_" + userEntity.getUserId());
		}
		return "{}";
	}
}
