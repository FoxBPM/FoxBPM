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

import org.foxbpm.engine.IdentityService;
import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.impl.cache.CacheUtil;
import org.foxbpm.engine.impl.entity.UserEntity;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.rest.common.api.AbstractRestResource;
import org.foxbpm.rest.common.api.FoxBpmUtil;
import org.restlet.data.Form;
import org.restlet.resource.Get;

/**
 * 常量类
 * 
 * @author yangguangftlp
 * @date 2014年8月26日
 */
public class UserResource extends AbstractRestResource {
	
	@Get
	public String update() {
		StringBuffer responseText = new StringBuffer("{");
		// 获取参数
		String userId = getAttribute("userId");
		Form query = getQuery();
		String userName = getQueryParameter("name", query);
		String email = getQueryParameter("email", query);
		String tel = getQueryParameter("tel", query);
		
		if (StringUtil.isNotEmpty(userName) || StringUtil.isNotEmpty(email) || StringUtil.isNotEmpty(tel)) {
			try {
				// 获取引擎
				ProcessEngine processEngine = FoxBpmUtil.getProcessEngine();
				// 获取身份服务
				IdentityService identityService = processEngine.getIdentityService();
				// 构造用户信息
				UserEntity userEntity = new UserEntity(userId, userName);
				userEntity.setEmail(email);
				userEntity.setTel(tel);
				identityService.updateUser(userEntity);
				CacheUtil.getIdentityCache().remove("user_" + userEntity.getUserId());
				responseText.append("success:更新成功");
			} catch (Exception e) {
				responseText.append("error:" + e.getMessage());
			}
		}
		responseText.append("}");
		return responseText.toString();
	}
}
