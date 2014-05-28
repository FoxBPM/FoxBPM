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
package org.foxbpm.engine;

import java.util.List;

import org.foxbpm.engine.identity.User;

public interface IdentityService {
	
	/**
	 * 根据用户编号查询用户
	 * @param userId 用户编号 主键
	 * @return
	 */
	User getUser(String userId);
	
	/**
	 * 根据编号或名称模糊匹配
	 * @param idLike 示例： %200802%
	 * @param nameLike 示例： %张%
	 * 
	 * @return 参数可为null,参数之间为or关系，如果都为null代表查询所有
	 */
	List<User> getUsers(String idLike,String nameLike);
}
