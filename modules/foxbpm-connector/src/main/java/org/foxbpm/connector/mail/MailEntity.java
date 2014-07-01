/**
 * Copyright 1996-2013 Founder International Co.,Ltd.
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
package org.foxbpm.connector.mail;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.foxbpm.connector.common.constant.EntityFieldName;
import org.foxbpm.engine.db.PersistentObject;
import org.foxbpm.engine.impl.util.GuidUtil;
import org.foxbpm.engine.impl.util.StringUtil;

/**
 * 邮件实体
 * 
 * @author yangguangftlp
 * @date 2014年6月30日
 */
public class MailEntity implements PersistentObject {

	/**
	 * 邮件id
	 */
	protected String mailId;
	/**
	 * 邮件名称
	 */
	protected String mailName;
	/**
	 * 邮件发给谁(多个人请使用,分隔)
	 */
	protected String mailTo;
	/**
	 * 邮件抄送给谁(多个人请使用,分隔)
	 */
	protected String mailCc;
	/**
	 * 邮件主题
	 */
	protected String mailSubject;
	/**
	 * 邮件体
	 */
	protected String mailBody;
	/**
	 * 不加类型
	 */
	protected String bizType;
	/**
	 * 附件内容
	 */
	protected String bizValue;
	/**
	 * 邮件状态
	 */
	protected MailStatus mailStatus;
	/**
	 * 创建时间
	 */
	protected Date createTime;
	/**
	 * 发送时间
	 */
	protected Date sendTime;
	/**
	 * 创建者
	 */
	protected String createUser;
	/**
	 * 失败原因
	 */
	protected String failureReason;

	public MailEntity() {
		this.mailId = GuidUtil.CreateGuid();
		this.mailStatus = MailStatus.NOSEND;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getMailName() {
		return mailName;
	}

	public void setMailName(String mailName) {
		this.mailName = mailName;
	}

	public String getMailTo() {
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	public String getMailCc() {
		return mailCc;
	}

	public void setMailCc(String mailCc) {
		this.mailCc = mailCc;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailBody() {
		return mailBody;
	}

	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getBizValue() {
		return bizValue;
	}

	public void setBizValue(String bizValue) {
		this.bizValue = bizValue;
	}

	public MailStatus getMailStatus() {
		return mailStatus;
	}

	public void setMailStatus(MailStatus mailStatus) {
		this.mailStatus = mailStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public void persistentInit(Map<String, Object> entityMap) {
		for (String dataKey : entityMap.keySet()) {
			if (dataKey.equals(EntityFieldName.MAIL_ID)) {
				this.mailId = StringUtil.getString(entityMap.get(dataKey));
			} else if (dataKey.equals(EntityFieldName.MAIL_NAME)) {
				this.mailName = StringUtil.getString(entityMap.get(dataKey));
			} else if (dataKey.equals(EntityFieldName.MAIL_TO)) {
				this.mailTo = StringUtil.getString(entityMap.get(dataKey));
			} else if (dataKey.equals(EntityFieldName.MAIL_SUBJECT)) {
				this.mailSubject = StringUtil.getString(entityMap.get(dataKey));
			} else if (dataKey.equals(EntityFieldName.MAIL_BODY) && null != entityMap.get(dataKey)) {
				this.mailBody = new String((byte[]) entityMap.get(dataKey));
			} else if (dataKey.equals(EntityFieldName.MAIL_BIZ_TYPE)) {
				this.bizType = StringUtil.getString(entityMap.get(dataKey));
			} else if (dataKey.equals(EntityFieldName.MAIL_BIZ_VALUE)) {
				this.bizValue = StringUtil.getString(entityMap.get(dataKey));
			} else if (dataKey.equals(EntityFieldName.MAIL_STATUS)) {
				this.mailStatus = MailStatus.valueOf(StringUtil.getString(entityMap.get(dataKey)));
			} else if (dataKey.equals(EntityFieldName.MAIL_CREATE_TIME)) {
				this.createTime = StringUtil.getDate((entityMap.get(dataKey)));
			} else if (dataKey.equals(EntityFieldName.MAIL_SEND_TIME)) {
				this.sendTime = StringUtil.getDate(entityMap.get(dataKey));
			} else if (dataKey.equals(EntityFieldName.MAIL_CC)) {
				this.mailCc = StringUtil.getString(entityMap.get(dataKey));
			} else if (dataKey.equals(EntityFieldName.MAIL_CREATE_USER)) {
				this.createUser = StringUtil.getString(entityMap.get(dataKey));
			} else if (dataKey.equals(EntityFieldName.MAIL_FAILURE_REASON)) {
				this.failureReason = StringUtil.getString(entityMap.get(dataKey));
			}
		}
	}

	public String getId() {
		return this.mailId;
	}

	public Map<String, Object> getPersistentState() {
		Map<String, Object> objectParam = new HashMap<String, Object>();
		objectParam.put(EntityFieldName.MAIL_ID, getId());
		objectParam.put(EntityFieldName.MAIL_NAME, getMailName());
		objectParam.put(EntityFieldName.MAIL_TO, getMailTo());
		objectParam.put(EntityFieldName.MAIL_SUBJECT, getMailSubject());
		objectParam.put(EntityFieldName.MAIL_BODY, getMailBody().getBytes());
		objectParam.put(EntityFieldName.MAIL_BIZ_TYPE, getBizType());
		objectParam.put(EntityFieldName.MAIL_BIZ_VALUE, getBizValue());
		objectParam.put(EntityFieldName.MAIL_STATUS, getMailStatus().toString());
		objectParam.put(EntityFieldName.MAIL_CREATE_TIME, getCreateTime());
		objectParam.put(EntityFieldName.MAIL_SEND_TIME, getSendTime());
		objectParam.put(EntityFieldName.MAIL_CC, getMailCc());
		objectParam.put(EntityFieldName.MAIL_CREATE_USER, getCreateUser());
		objectParam.put(EntityFieldName.MAIL_FAILURE_REASON, getFailureReason());
		return objectParam;
	}

	@Override
	public void setId(String id) {
		this.mailId = id;
	}

	@Override
	public boolean isModified() {
		return false;
	}

}
