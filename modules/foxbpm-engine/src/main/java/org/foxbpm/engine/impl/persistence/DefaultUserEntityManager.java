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
package org.foxbpm.engine.impl.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.identity.UserEntityManager;
import org.foxbpm.engine.impl.entity.UserEntity;

@SuppressWarnings("unchecked")
public class DefaultUserEntityManager extends AbstractManager implements UserEntityManager {

	public UserEntity findUserById(String userId) {
		return (UserEntity) getSqlSession().selectOne("selectUserById", userId);
	}

	@Override
	public List<UserEntity> findUsers(String idLike, String nameLike) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (idLike != null) {
			map.put("userId", idLike);
		}
		if (nameLike != null) {
			map.put("userName", nameLike);
		}
		return (List<UserEntity>) getSqlSession().selectListWithRawParameter("selectUsers", map);
	}

	@Override
	public List<UserEntity> findUsers(String idLike, String nameLike, int firstResult, int maxResults) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		if (idLike != null) {
			queryMap.put("userId", idLike);
		}
		if (nameLike != null) {
			queryMap.put("userName", nameLike);
		}
		return (List<UserEntity>) getSqlSession().selectList("selectUsersByPage", queryMap, firstResult, maxResults);
	}

	@Override
	public Object findUserCount(String idLike, String nameLike) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		if (idLike != null) {
			queryMap.put("userId", idLike);
		}
		if (nameLike != null) {
			queryMap.put("userName", nameLike);
		}
		return getSqlSession().selectOne("selectUsersCount", queryMap);
	}
}
