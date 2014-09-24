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
 */
package org.foxbpm.social;

import java.util.List;

import org.foxbpm.social.impl.entity.SocialMessageInfo;
import org.foxbpm.social.impl.entity.SocialUser;

/**
 * 
 * @author MAENLIANG
 * 
 */
public interface SocialService {
	/**
	 * 添加用户
	 * 
	 * @param socialUser
	 */
	public void addSocialUserInfo(SocialUser socialUser);

	/**
	 * 添加社交消息，包括聊天消息、评论消息、回复消息
	 * 
	 * @param socialMessageInfo
	 */
	public void addSocialMessageInfo(SocialMessageInfo socialMessageInfo);

	/**
	 * 根据任务ID获取任务所关联的社交消息
	 * 
	 * @param taskId
	 * @return
	 */
	public List<SocialMessageInfo> findSocialMessageInfos(String taskId);
}
