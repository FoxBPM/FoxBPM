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
 * @author ych
 */
package org.foxbpm.engine.identity;

import java.util.List;

import org.foxbpm.engine.impl.entity.UserEntity;

public interface UserDefinition {

	/**
	 * 根据用户编号获取用户信息
	 * @param userId
	 * @return
	 */
	public UserEntity findUserById(String userId);

	/**
	 * 根据查询条件获取用户列表
	 * 选择转发人，查询等选人界面时使用，不用系统提供界面时可不实现
	 */
	public List<UserEntity> findUsers(String idLike, String nameLike);

	/**
	 * 根据查询条件获取用户列表（分页）
	 * 选择转发人，查询等选人界面时使用，不用系统提供界面时可不实现
	 */
	List<UserEntity> findUsers(String idLike, String nameLike, int firstResult, int maxResults);

	/**
	 * 根据查询条件获取用户数量
	 * 选择转发人，查询等选人界面时使用，不用系统提供界面时可不实现
	 */
	Long findUserCount(String idLike, String nameLike);
}
