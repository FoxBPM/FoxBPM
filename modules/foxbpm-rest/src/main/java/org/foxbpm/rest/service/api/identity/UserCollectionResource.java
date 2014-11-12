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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.IdentityService;
import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.impl.db.Page;
import org.foxbpm.engine.impl.entity.UserEntity;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.rest.common.api.AbstractRestResource;
import org.foxbpm.rest.common.api.DataResult;
import org.foxbpm.rest.common.api.FoxBpmUtil;
import org.restlet.data.Form;
import org.restlet.resource.Get;

/**
 * 常量类
 * 
 * @author yangguangftlp
 * @date 2014年8月26日
 */
public class UserCollectionResource extends AbstractRestResource {
	
	@Get
	public DataResult getUsers() {
		// 获取引擎
		ProcessEngine processEngine = FoxBpmUtil.getProcessEngine();
		// 获取身份服务
		IdentityService identityService = processEngine.getIdentityService();
		Form form = getQuery();
		String id = StringUtil.getString(getQueryParameter("id", form));
		String name = StringUtil.getString(getQueryParameter("name", form));
		
		String idLike = null;
		String nameLike = null;
		if (StringUtil.isNotEmpty(id)) {
			idLike = "%" + id + "%";
		}
		if (StringUtil.isNotEmpty(name)) {
			nameLike = "%" + name + "%";
		}
		initPage();
		// 分页信息
		Page page = null;
		if (pageIndex > -1) {
			page = new Page(pageIndex, pageSize);
		}
		List<UserEntity> userEntitys = identityService.getUsers(idLike, nameLike, page);
		List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();
		if (null != userEntitys) {
			Iterator<UserEntity> iterator = userEntitys.iterator();
			while (iterator.hasNext()) {
				userList.add(iterator.next().getPersistentState());
			}
		}
		DataResult result = new DataResult();
		long resultCount = identityService.getUsersCount(idLike, nameLike);
		result.setData(userList);
		result.setPageIndex(pageIndex);
		result.setPageSize(pageSize);
		result.setRecordsTotal(resultCount);
		result.setRecordsFiltered(resultCount);
		return result;
	}
}
