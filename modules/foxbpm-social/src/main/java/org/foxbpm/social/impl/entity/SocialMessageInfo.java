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

/**
 * 消息实体
 * @author kenshin
 *
 */
public class SocialMessageInfo implements Serializable  {

	private static final long serialVersionUID = 1L;
	/** 发消息用户编号 */
	private String userId;
	/** 消息编号 */
	private String msgId;
	/** 消息内容 */
	private String content;
	/** 消息类型（0，原创；1，评论；2，转发； 3，聊天） */
	private int type;
	/** 评论过数量（只增不减，删除评论不影响此值，可以作为评论多页显示的页码） */
	private String commentedCount;
	/** 保留的评论数量 */
	private String commentCount;
	/** 转发过数量（只增不减，删除转发不影响此值，可以作为转发多页显示的页码） */
	private String transferredCount;
	/** 保留的转发数量 */
	private String transferCount;
	/** 发布时间 */
	private String time;
	/** 任务ID*/
	private String taskId;
	/** 流程实例ID*/
	private String processInstanceId;
	/** 是否公开FLAG*/
	private int openFlag;
	
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public int getOpenFlag() {
		return openFlag;
	}
	public void setOpenFlag(int openFlag) {
		this.openFlag = openFlag;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getCommentedCount() {
		return commentedCount;
	}
	public void setCommentedCount(String commentedCount) {
		this.commentedCount = commentedCount;
	}
	public String getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}
	public String getTransferredCount() {
		return transferredCount;
	}
	public void setTransferredCount(String transferredCount) {
		this.transferredCount = transferredCount;
	}
	public String getTransferCount() {
		return transferCount;
	}
	public void setTransferCount(String transferCount) {
		this.transferCount = transferCount;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

}
