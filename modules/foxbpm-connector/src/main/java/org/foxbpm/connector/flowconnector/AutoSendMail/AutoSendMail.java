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

import org.foxbpm.connector.mail.MailEngine;
import org.foxbpm.connector.mail.MailEntity;
import org.foxbpm.engine.IdentityService;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.execution.ConnectorExecutionContext;
import org.foxbpm.engine.identity.User;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.connector.FlowConnectorHandler;
import org.foxbpm.engine.impl.entity.IdentityLinkEntity;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.model.config.foxbpmconfig.MailInfo;
import org.foxbpm.model.config.foxbpmconfig.SysMailConfig;

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
		// 获取邮件配置
		SysMailConfig sysMailConfig = Context.getProcessEngineConfiguration().getSysMailConfig();
		MailInfo mailInfoObj = null;
		for (MailInfo mailInfo : sysMailConfig.getMailInfo()) {
			if (mailInfo.getMailName().equals(sysMailConfig.getSelected())) {
				mailInfoObj = mailInfo;
			}
		}
		// 判断邮件配置信息是否为空
		if (null == mailInfoObj) {
			throw new FoxBPMException("系统邮件配置错误请检查流程邮件配置！");
		}
		TaskEntity taskEntity = executionContext.getAssignTask();
		StringBuffer mailTitle = new StringBuffer();
		// 如果主题为空
		if (StringUtil.isEmpty(StringUtil.trim(title))) {
			mailTitle.append("[" + taskEntity.getProcessDefinitionName() + "] ").append(taskEntity.getDescription()).append(" is pending for your approval or handle");
		}

		String taskUrl = "http://localhost:8889/foxbpm-webapps-base";
		if (StringUtil.isEmpty(content)) {
			content = "<br>Hello,<br>你好,<br><br> " + mailTitle + "+<br><br>" + "Please click url to deal with job: <br>请访问此链接地址进入任务:<br> <a href=" + taskUrl + ">" + taskUrl + "</a><br><br>"
					+ "Best Regards!<br>诚挚问候!<br>Note: Please do not reply to this email , This mailbox does not allow incoming messages." + "<br>注意: 本邮件为工作流系统发送，请勿回复。 ";
		}
		// 获取用户
		IdentityService identityService = Context.getProcessEngineConfiguration().getIdentityService();
		// 判断是否独占任务
		User user = null;
		MailEntity mailEntity = null;
		taskEntity.setAssignee("admin");
		if (StringUtil.isNotEmpty(taskEntity.getAssignee())) {
			user = identityService.getUser(taskEntity.getAssignee());
			if (null != user) {
				if (StringUtil.isNotEmpty(user.getEmail())) {
					mailEntity = new MailEntity();
					mailEntity.setMailName(title);
					mailEntity.setMailSubject(title);
					mailEntity.setMailTo(user.getEmail());
					mailEntity.setMailBody(content);
					mailEntity.setCreateTime(new Date());
					mailEntity.setBizType("taskremind");
					mailEntity.setBizValue(taskEntity.getId());
					mailEntity.setCreateUser(Authentication.getAuthenticatedUserId());
					MailEngine.getInstance().saveMail(mailEntity);
				}
			}
		} else {
			StringBuffer to = new StringBuffer();
			for (IdentityLinkEntity identityLink : taskEntity.getTaskIdentityLinks()) {
				if (StringUtil.isNotEmpty(identityLink.getUserId())) {
					user = Authentication.selectUserByUserId(identityLink.getUserId());
					if (null != user) {
						if (StringUtil.isNotEmpty(user.getEmail())) {
							to.append(user.getEmail()).append(",");
						}
					}
				} else {/*
					
					String groupIdString = identityLink.getGroupId();
					String groupTypeString = identityLink.getGroupType();
					GroupTo groupTo = Authentication.findGroupByGroupIdAndType(groupIdString, groupTypeString);
					if (groupTo != null) {

						for (GroupDefinition groupDefinition : groupDefinitions) {
							if (groupDefinition.getId().equals(groupTypeString)) {
								List<UserTo> userTos = groupDefinition.findUserByGroupId(groupIdString);
								for (UserTo userTo : userTos) {
									if (userTo != null) {
										
									}
								}
							}
						}

					}*/
				}
			}
			if (to.length() > 0) {
				to.deleteCharAt(to.length() - 1);
				mailEntity = new MailEntity();
				mailEntity.setMailName(title);
				mailEntity.setMailSubject(title);
				mailEntity.setMailTo(StringUtil.getString(to));
				mailEntity.setMailBody(content);
				mailEntity.setCreateTime(new Date());
				mailEntity.setBizType("taskremind");
				mailEntity.setBizValue(taskEntity.getId());
				mailEntity.setCreateUser(Authentication.getAuthenticatedUserId());
				MailEngine.getInstance().saveMail(mailEntity);
			}
		}
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public void setContent(java.lang.String content) {
		this.content = content;
	}

}