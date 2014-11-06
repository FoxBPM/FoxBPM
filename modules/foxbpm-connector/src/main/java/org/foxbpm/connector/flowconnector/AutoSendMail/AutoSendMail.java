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
package org.foxbpm.connector.flowconnector.AutoSendMail;

import java.util.Date;
import java.util.List;

import org.foxbpm.connector.common.constant.Constants;
import org.foxbpm.connector.mail.MailEngine;
import org.foxbpm.connector.mail.MailEntity;
import org.foxbpm.engine.Constant;
import org.foxbpm.engine.IdentityService;
import org.foxbpm.engine.execution.ConnectorExecutionContext;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.ProcessEngineConfigurationImpl;
import org.foxbpm.engine.impl.connector.FlowConnectorHandler;
import org.foxbpm.engine.impl.entity.IdentityLinkEntity;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.entity.UserEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.util.StringUtil;

/**
 * 自动发送邮件
 * 
 * @author yangguangftlp
 * @date 2014年7月1日
 */
public class AutoSendMail implements FlowConnectorHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4576139954014666005L;

	private java.lang.String title;

	private java.lang.String content;

	public void execute(ConnectorExecutionContext executionContext) throws Exception {

		// 从上下文拿引擎
		ProcessEngineConfigurationImpl peconfig = Context.getProcessEngineConfiguration();
		TaskEntity taskEntity = executionContext.getAssignTask();
		if (null != taskEntity) {
			StringBuffer mailTitle = new StringBuffer();
			// 如果主题为空
			if (StringUtil.isEmpty(StringUtil.trim(title))) {
				mailTitle.append('[').append(taskEntity.getProcessDefinitionName()).append(']').append(taskEntity.getDescription()).append(" is pending for your approval or handle");
			}

			String taskUrl = "http://www.baidu.com";
			if (StringUtil.isEmpty(content)) {
				content = "<br>Hello,<br>你好,<br><br> " + mailTitle + "<br><br>" + "Please click url to deal with job: <br>请访问此链接地址进入任务:<br> <a href=" + taskUrl + ">" + taskUrl + "</a><br><br>"
						+ "Best Regards!<br>诚挚问候!<br>Note: Please do not reply to this email , This mailbox does not allow incoming messages." + "<br>注意: 本邮件为工作流系统发送，请勿回复。 ";
			}
			// 获取用户
			IdentityService identityService = peconfig.getIdentityService();
			// 判断是否独占任务
			UserEntity user = null;
			if (StringUtil.isNotEmpty(taskEntity.getAssignee())) {
				user = Authentication.selectUserByUserId(taskEntity.getAssignee());
				// 判断用户是否为空
				if (null != user) {
					// 如果用户存在邮件地址即发生邮件
					if (StringUtil.isNotEmpty(user.getEmail())) {
						// 保存邮件实体
						saveMail(user.getEmail(), title, content, taskEntity.getId());
					}
				}
			} else {
				// 处理共享任务
				StringBuffer to = new StringBuffer();
				String userId = null;
				for (IdentityLinkEntity identityLink : taskEntity.getIdentityLinks()) {
					userId = identityLink.getUserId();
					if (StringUtil.isNotEmpty(userId)) {
						if (!Constant.FOXBPM_ALL_USER.equals(userId)) {
							user = Authentication.selectUserByUserId(userId);
							if (null != user) {
								if (StringUtil.isNotEmpty(user.getEmail())) {
									to.append(user.getEmail()).append(Constants.COMMA);
								}
							}
						} else {
							// 处理所有者
							List<UserEntity> users = identityService.getUsers(null, null);
							if (null != users) {
								for (UserEntity u : users) {
									if (StringUtil.isNotEmpty(u.getEmail())) {
										to.append(u.getEmail()).append(Constants.COMMA);
									}
								}
							}
						}
					} else {
						// 获取组下面所有用户
						List<UserEntity> users = Authentication.selectUserByGroupIdAndType(identityLink.getGroupId(), identityLink.getGroupType());
						if (null != users) {
							for (UserEntity u : users) {
								if (StringUtil.isNotEmpty(u.getEmail())) {
									to.append(u.getEmail()).append(Constants.COMMA);
								}
							}
						}
					}
				}
				if (to.length() > 0) {
					// 删除最后一个','
					to.deleteCharAt(to.length() - 1);
					// 保存邮件实体
					saveMail(to.toString(), title, content, taskEntity.getId());
				}
			}
		}
	}

	/**
	 * 保存邮件
	 * 
	 * @param to
	 *            发给人
	 * @param title
	 *            主题
	 * @param mailContent
	 *            邮件内容
	 * @param taskId
	 *            任务id
	 */
	private void saveMail(String to, String mailtitle, String mailContent, String taskId) {
		// 创建邮件实体
		MailEntity mailEntity = new MailEntity();
		mailEntity.setMailName(mailtitle);
		mailEntity.setMailSubject(mailtitle);
		mailEntity.setMailTo(to);
		mailEntity.setMailBody(mailContent);
		mailEntity.setCreateTime(new Date());
		mailEntity.setCreateUser(Authentication.getAuthenticatedUserId());
		// 调用引擎保存邮件
		MailEngine.getInstance().saveMail(mailEntity);
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public void setContent(java.lang.String content) {
		this.content = content;
	}

}