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
package org.foxbpm.connector.common.constant;

/**
 * 数据库表实体字段名常量类
 * 
 * @author yangguangftlp
 * @date 2014年6月30日
 */
public interface EntityFieldName {

	/*********************** mail *********************************/
	/**
	 * 邮件表名称
	 */
	String T_MAIL = "foxbpm_mail";
	/**
	 * 邮件id
	 */
	String MAIL_ID = "MAIL_ID";
	/**
	 * 邮件名称
	 */
	String MAIL_NAME = "MAIL_NAME";
	/**
	 * 邮件发给谁(多个人请使用,分隔)
	 */
	String MAIL_TO = "MAIL_TO";
	/**
	 * 邮件主题
	 */
	String MAIL_SUBJECT = "MAIL_SUBJECT";
	/**
	 * 邮件体
	 */
	String MAIL_BODY = "MAIL_BODY";
	/**
	 * 附件类型
	 */
	String MAIL_BIZ_TYPE = "BIZ_TYPE";
	/**
	 * 附件内容
	 */
	String MAIL_BIZ_VALUE = "BIZ_VALUE";
	/**
	 * 邮件状态
	 */
	String MAIL_STATUS = "MAIL_STATUS";
	/**
	 * 创建时间
	 */
	String MAIL_CREATE_TIME = "CREATE_TIME";
	/**
	 * 发送时间
	 */
	String MAIL_SEND_TIME = "SEND_TIME";
	/**
	 * 邮件抄送给谁(多个人请使用,分隔)
	 */
	String MAIL_CC = "MAIL_CC";
	/**
	 * 创建者
	 */
	String MAIL_CREATE_USER = "CREATE_USER";
	/**
	 * 失败原因
	 */
	String MAIL_FAILURE_REASON = "FAILURE_REASON";
}
