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
package org.foxbpm.social.impl.entity;

import java.io.Serializable;

import org.foxbpm.engine.impl.entity.UserEntity;

/**
 * 社交用户信息实体
 * 
 * @author kenshin
 *
 */
public class SocialUserEntity extends UserEntity implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	/** 消息数目 */
	private int messageCount=0;
	/** 粉丝数目 */
	private int fansCount=0;
	/** 关注对象数目 */
	private int followCount=0;

	public int getMessageCount() {
		return messageCount;
	}

	public void setMessageCount(int messageCount) {
		this.messageCount = messageCount;
	}

	public int getFansCount() {
		return fansCount;
	}

	public void setFansCount(int fansCount) {
		this.fansCount = fansCount;
	}

	public int getFollowCount() {
		return followCount;
	}

	public void setFollowCount(int followCount) {
		this.followCount = followCount;
	}

}
