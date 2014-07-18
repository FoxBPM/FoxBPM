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
 * @author kenshin
 * @author ych
 */
package org.foxbpm.engine.impl.cmd;

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.identity.User;
import org.foxbpm.engine.identity.UserEntityManager;
import org.foxbpm.engine.impl.db.Page;
import org.foxbpm.engine.impl.entity.UserEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;

/**
 * 根据userId 或者userName 模糊匹配 参数之间or关系
 * 
 * @author Administrator
 * 
 */
public class FindUsersCmd implements Command<List<User>> {

	/**
	 * 示例：%20080101%
	 */
	private String idLike;
	/**
	 * 示例：%张三%
	 */
	private String nameLike;
	private Page page;

	public FindUsersCmd(String idLike, String nameLike, Page page) {
		this.idLike = idLike;
		this.nameLike = nameLike;
		this.page = page;
	}

	@Override
	public List<User> execute(CommandContext commandContext) {
		UserEntityManager userEntityManager = commandContext.getUserEntityManager();
		List<UserEntity> userEntityList = null;
		if (null != page) {
			userEntityList = userEntityManager.findUsers(idLike, nameLike, page.getFirstResult(), page.getMaxResults());
		} else {
			userEntityList = userEntityManager.findUsers(idLike, nameLike);
		}
		List<User> userList = null;
		if (null != userEntityList && !userEntityList.isEmpty()) {
			userList = new ArrayList<User>();
			for (UserEntity userEntity : userEntityList) {
				userList.add(userEntity);
			}
		}
		return userList;
	}
}
