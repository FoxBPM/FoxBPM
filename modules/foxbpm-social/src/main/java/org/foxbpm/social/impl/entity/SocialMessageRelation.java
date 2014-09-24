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
import java.util.Date;

/**
 * 消息关联实体
 * @author kenshin
 *
 */
public class SocialMessageRelation implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	/** 引用消息用户编号 */
	private String refUserId;
	/** 引用消息编号 */
	private String refMsgId;
	/** 消息发布者编号 */
	private String refedUserId;
	/** 被引用消息编号 */
	private String refedMsgId;
	/** 操作类型(1,评论；2，转发；3，消息回复) */
	private int type;
	/** 发布时间 */
	private Date time;
	/** 转发或者评论页码 **/
	private int pageIndex;
	
	
	public String getRefUserId() {
		return refUserId;
	}
	public void setRefUserId(String refUserId) {
		this.refUserId = refUserId;
	}
	public String getRefMsgId() {
		return refMsgId;
	}
	public void setRefMsgId(String refMsgId) {
		this.refMsgId = refMsgId;
	}
	public String getRefedUserId() {
		return refedUserId;
	}
	public void setRefedUserId(String refedUserId) {
		this.refedUserId = refedUserId;
	}
	public String getRefedMsgId() {
		return refedMsgId;
	}
	public void setRefedMsgId(String refedMsgId) {
		this.refedMsgId = refedMsgId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

}
