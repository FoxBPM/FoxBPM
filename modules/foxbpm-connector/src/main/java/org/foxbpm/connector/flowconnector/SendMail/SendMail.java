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
package org.foxbpm.connector.flowconnector.SendMail;

import java.util.Date;

import org.foxbpm.connector.mail.MailEngine;
import org.foxbpm.connector.mail.MailEntity;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.execution.ConnectorExecutionContext;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.connector.FlowConnectorHandler;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.model.config.foxbpmconfig.MailInfo;
import org.foxbpm.model.config.foxbpmconfig.SysMailConfig;

/**
 * 发送邮件
 * 
 * @author yangguangftlp
 * @date 2014年7月1日
 */
public class SendMail implements FlowConnectorHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7217470393022025648L;

	private java.lang.String to;

	private java.lang.String title;

	private java.lang.String cc;

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
		MailEntity mailEntity = new MailEntity();
		mailEntity.setMailName(title);
		mailEntity.setMailSubject(title);
		mailEntity.setMailTo(to);
		mailEntity.setMailCc(cc);
		mailEntity.setMailBody(content);
		mailEntity.setCreateTime(new Date());
		mailEntity.setCreateUser(Authentication.getAuthenticatedUserId());
		MailEngine.getInstance().saveMail(mailEntity);
	}

	public void setTo(java.lang.String to) {
		this.to = to;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public void setCc(java.lang.String cc) {
		this.cc = cc;
	}

	public void setContent(java.lang.String content) {
		this.content = content;
	}
}