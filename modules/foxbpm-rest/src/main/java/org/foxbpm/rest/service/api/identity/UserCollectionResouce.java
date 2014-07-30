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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.identity.User;
import org.foxbpm.rest.common.api.AbstractRestResource;
import org.foxbpm.rest.common.api.DataResult;
import org.foxbpm.rest.common.api.FoxBpmUtil;
import org.restlet.resource.Get;

/**
 * 组织机构数据
 * 
 * @author yangguangftlp
 * @date 2014年7月30日
 */
public class UserCollectionResouce extends AbstractRestResource {
	@Get
	public DataResult getAllUsers() {
		List<User> users = FoxBpmUtil.getProcessEngine().getIdentityService().getUsers(null, null);
		// 数据转换
		List<Map<String, Object>> resultDatas = new ArrayList<Map<String, Object>>();
		for (User user : users) {
			resultDatas.add(user.getPersistentState());
		}
		// 数据响应体构造
		DataResult result = new DataResult();
		result.setData(resultDatas);
		result.setStart(0);
		result.setTotal(users.size());
		return result;
	}
}
