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
 * @author yangguangftlp
 */
package org.foxbpm.connector.mail;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.connector.common.constant.Constants;
import org.foxbpm.connector.common.constant.EntityFieldName;
import org.foxbpm.engine.exception.FoxBPMBizException;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.db.SqlCommand;
import org.foxbpm.engine.impl.util.DBUtils;
import org.foxbpm.engine.impl.util.MailUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.spring.ProcessEngineConfigurationSpring;
import org.foxbpm.model.config.foxbpmconfig.MailInfo;
import org.foxbpm.model.config.foxbpmconfig.SysMailConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 邮件引擎
 * 
 * @author yangguangftlp
 * @date 2014年6月30日
 */
public class MailEngine {
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MailEngine.class);

	/**
	 * 邮件引擎实例
	 */
	private static MailEngine instance;

	/**
	 * sql 执行
	 */

	private MailEngine() {

	}

	public static MailEngine getInstance() {
		if (null == instance) {
			synchronized (MailEngine.class) {
				if (null == instance) {
					instance = new MailEngine();
				}
			}
		}
		return instance;
	}

	/**
	 * 保存邮件实体
	 * 
	 * @param mailEntity
	 */
	public void saveMail(MailEntity mailEntity) {
		// 获取数据库操作
		SqlCommand sqlCommand = new SqlCommand(DBUtils.getConnection());
		sqlCommand.insert(EntityFieldName.T_MAIL, mailEntity.getPersistentState());
	}

	/**
	 * 同步执行发送邮件 该方法有定时任务执行
	 */
	public synchronized void sendMail() {
		LOGGER.debug("start sendMail()");
		ProcessEngineConfigurationSpring processEngineConfig = (ProcessEngineConfigurationSpring) Context.getProcessEngineConfiguration();
		PlatformTransactionManager transactionManager = processEngineConfig.getTransactionManager();
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
		transactionTemplate.setPropagationBehavior(TransactionTemplate.PROPAGATION_REQUIRES_NEW);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				// 获取邮件配置
				SysMailConfig sysMailConfig = Context.getProcessEngineConfiguration().getSysMailConfig();
				MailInfo mailInfoObj = null;
				for (MailInfo mailInfo : sysMailConfig.getMailInfo()) {
					if (mailInfo.getMailName().equals(sysMailConfig.getSelected())) {
						mailInfoObj = mailInfo;
					}
				}
				if (mailInfoObj == null) {
					LOGGER.error("系统邮件配置错误请检查流程邮件配置！");
					throw new FoxBPMException("系统邮件配置错误请检查流程邮件配置！");
				}

				List<Object> pObjects = new ArrayList<Object>();
				// 添加查询条件
				pObjects.add(MailStatus.NOSEND.toString());
				SqlCommand sqlCommand = new SqlCommand(DBUtils.getConnection());
				String sqlText = getPaginationSql("SELECT * FROM " + EntityFieldName.T_MAIL + " WHERE MAIL_STATUS=?", 1, 10, "*", null);
				List<Map<String, Object>> dataList = sqlCommand.queryForList(sqlText, pObjects);
				// 邮件表字段更新变量
				Map<String, Object> objectParam = new HashMap<String, Object>();
				// 邮件实体
				MailEntity mailEntity = null;
				// 处理未发送的邮件
				for (Map<String, Object> mapData : dataList) {
					mailEntity = new MailEntity();
					try {
						mailEntity.persistentInit(mapData);
						// 邮件处理
						MailUtil mailUtil = new MailUtil();
						mailUtil.setSmtpHost(mailInfoObj.getSmtpHost(), StringUtil.getInt(mailInfoObj.getSmtpPort()));
						mailUtil.setSmtpAuthentication(mailInfoObj.getUserName(), mailInfoObj.getPassword());
						// 支持发送多人邮件 #4185
						String to = mailEntity.getMailTo();
						if (StringUtil.isEmpty(StringUtil.trim(to))) {
							LOGGER.error("mailTo is null");
							throw new FoxBPMBizException("mailTo is null");
						}
						String[] strTo = to.split(",");
						List<String> userMailToList = new ArrayList<String>();
						for (String userMail : strTo) {
							if (StringUtil.isNotEmpty(StringUtil.trim(userMail))) {
								userMailToList.add(userMail);
							}
						}
						if (userMailToList.size() == 0) {
							LOGGER.error("Mail toaddress is null");
							throw new FoxBPMBizException("Mail toaddress is null");
						}
						mailUtil.setTo(userMailToList.toArray(new String[0]));

						String cc = mailEntity.getMailCc();
						if (StringUtil.isNotEmpty(StringUtil.trim(cc))) {
							String[] strCC = cc.split(Constants.COMMA);
							List<String> userMailCCList = new ArrayList<String>();
							for (String userMail : strCC) {
								if (StringUtil.isNotEmpty(StringUtil.trim(userMail))) {
									userMailCCList.add(userMail);
								}
							}
							if (userMailCCList.size() == 0) {
								throw new FoxBPMBizException("Mail ccaddress is null");
							}
							mailUtil.setCC(userMailCCList.toArray(new String[0]));
						}

						mailUtil.setFrom(mailInfoObj.getMailAddress());
						mailUtil.setSubject(mailEntity.getMailSubject());
						mailUtil.setBody(mailEntity.getMailBody());
						mailUtil.setContentType(MailUtil.MODE_HTML);
						// 同步发送
						mailUtil.send();
						// 清空数据
						objectParam.clear();
						// 设置邮件状态为完成
						objectParam.put(EntityFieldName.MAIL_STATUS, MailStatus.COMPLETE.toString());
						sqlCommand.update(EntityFieldName.T_MAIL, objectParam, " MAIL_ID=?", new Object[] { mailEntity.getMailId() });
					} catch (Exception e) {
						LOGGER.error("邮件发送失败，邮件id：" + mailEntity.getMailId(), e);
						try {
							// 清空数据
							objectParam.clear();
							// 设置邮件状态为失败
							objectParam.put(EntityFieldName.MAIL_STATUS, MailStatus.FAILURE.toString());
							// 设置邮件失败原因
							objectParam.put(EntityFieldName.MAIL_FAILURE_REASON, e.getMessage());
							sqlCommand.update(EntityFieldName.T_MAIL, objectParam, " MAIL_ID=?", new Object[] { mailEntity.getMailId() });
						} catch (Exception e1) {
							LOGGER.error("更新邮件状态失败，邮件id：" + mailEntity.getMailId(), e1);
						}
					} finally {
						try {
							// 清除参数
							objectParam.clear();
							// 设置邮件发送时间
							objectParam.put(EntityFieldName.MAIL_SEND_TIME, new Date());
							sqlCommand.update(EntityFieldName.T_MAIL, objectParam, " MAIL_ID=?", new Object[] { mailEntity.getMailId() });
						} catch (Exception e) {
							LOGGER.error("更新邮件状态失败，邮件id：" + mailEntity.getMailId(), e);
						}
					}
				}
			}
		});
		LOGGER.debug("end sendMail()");
	}

	/**
	 * 分页处理
	 * 
	 * @param sql
	 *            sql
	 * @param firstResult
	 *            开始页
	 * @param maxResults
	 *            最大记录数
	 * @param fields
	 *            字段
	 * @param orderBy
	 *            排序
	 * @return 返回分页sql
	 */
	public String getPaginationSql(String sql, int firstResult, int maxResults, String fields, String orderBy) {
		sql = trim(sql);
		if (StringUtil.isEmpty(fields)) {
			fields = "*";
		}
		StringBuffer sb = new StringBuffer(sql.length() + 20);
		sb.append("(");
		sb.append("SELECT " + fields + " FROM (select A.* from(");
		sb.append(sql);
		sb.append(" )A");
		sb.append(" )c LIMIT ");
		sb.append(firstResult - 1);
		sb.append(",");
		sb.append(maxResults - (firstResult - 1));
		sb.append(")");

		return sb.toString();
	}

	private String trim(String sql) {
		sql = sql.trim();
		if (sql.endsWith(Constants.SQL_END_DELIMITER)) {
			sql = sql.substring(0, sql.length() - 1 - Constants.SQL_END_DELIMITER.length());
		}
		return sql;
	}
}
