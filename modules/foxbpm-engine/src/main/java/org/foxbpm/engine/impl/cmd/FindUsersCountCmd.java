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
package org.foxbpm.engine.impl.cmd;

import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;

/**
 * 查询用户数
 * 
 * @author yangguangftlp
 * @date 2014年6月25日
 */
public class FindUsersCountCmd implements Command<Long> {
	/**
	 * 示例：%20080101%
	 */
	private String idLike;
	/**
	 * 示例：%张三%
	 */
	private String nameLike;

	public FindUsersCountCmd(String idLike, String nameLike) {
		this.idLike = idLike;
		this.nameLike = nameLike;
	}

	@Override
	public Long execute(CommandContext commandContext) {
		return commandContext.getUserEntityManager().findUserCount(idLike, nameLike);
	}
}
