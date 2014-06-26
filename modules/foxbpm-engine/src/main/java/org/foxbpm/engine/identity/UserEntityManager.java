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
package org.foxbpm.engine.identity;

import java.util.List;

import org.foxbpm.engine.impl.entity.UserEntity;

public interface UserEntityManager {

	public UserEntity findUserById(String userId);

	public List<UserEntity> findUsers(String idLike, String nameLike);

	/**
	 * 查询用户
	 * 
	 * @param idLike
	 *            id
	 * @param nameLike
	 *            名称
	 * @param firstResult
	 *            记录起始位置
	 * @param maxResults
	 *            最大记录数
	 * @return 返回用户列表
	 */
	List<UserEntity> findUsers(String idLike, String nameLike, int firstResult, int maxResults);

	/**
	 * 查询用户数
	 * 
	 * @param idLike
	 *            id
	 * @param nameLike
	 *            名称
	 * @return 用户数
	 */
	Object findUserCount(String idLike, String nameLike);
}
