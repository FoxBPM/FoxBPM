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

import org.foxbpm.connector.common.constant.EntityFieldName;
import org.foxbpm.engine.ProcessEngineManagement;
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
	private static final String SQL_END_DELIMITER = ";";
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
		
		ProcessEngineConfigurationSpring processEngineConfig = (ProcessEngineConfigurationSpring)Context.getProcessEngineConfiguration();
		PlatformTransactionManager transactionManager = processEngineConfig.getTransactionManager();
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
		transactionTemplate.setPropagationBehavior(TransactionTemplate.PROPAGATION_REQUIRES_NEW);
	    transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				SysMailConfig sysMailConfig = ProcessEngineManagement.getDefaultProcessEngine().getProcessEngineConfiguration().getSysMailConfig();
				MailInfo mailInfoObj = null;
				for (MailInfo mailInfo : sysMailConfig.getMailInfo()) {
					if (mailInfo.getMailName().equals(sysMailConfig.getSelected())) {
						mailInfoObj = mailInfo;
					}
				}
				if (mailInfoObj == null) {
					throw new FoxBPMException("系统邮件配置错误请检查流程邮件配置！");
				}
				List<Object> pObjects = new ArrayList<Object>();
				pObjects.add(MailStatus.NOSEND.toString());

				SqlCommand sqlCommand = new SqlCommand(DBUtils.getConnection());
				String sqlText = getPaginationSql("SELECT * FROM " + EntityFieldName.T_MAIL + " WHERE MAIL_STATUS=?", 1, 10, "*", null);
				List<Map<String, Object>> dataList = sqlCommand.queryForList(sqlText, pObjects);
				for (Map<String, Object> mapData : dataList) {
					try {
						MailEntity mailEntity = new MailEntity();
						mailEntity.persistentInit(mapData);

						MailUtil mailUtil = new MailUtil();
						mailUtil.setSmtpHost(mailInfoObj.getSmtpHost(), StringUtil.getInt(mailInfoObj.getSmtpPort()));
						mailUtil.setSmtpAuthentication(mailInfoObj.getUserName(), mailInfoObj.getPassword());
						// 支持发送多人邮件 #4185
						String to = mailEntity.getMailTo();
						if (StringUtil.isEmpty(StringUtil.trim(to))) {
							throw new FoxBPMBizException("mailTo is null");
						}
						String[] str = to.split(",");
						List<String> userMailToList = new ArrayList<String>();
						for (String userMail : str) {
							if (StringUtil.isNotEmpty(StringUtil.trim(userMail))) {
								userMailToList.add(userMail);
							}
						}
						if (userMailToList.size() == 0) {
							throw new FoxBPMBizException("Mail toaddress is null");
						}
						String[] userMailToFinStrings = (String[]) userMailToList.toArray(new String[userMailToList.size()]);

						mailUtil.setTo(userMailToFinStrings);

						String cc = mailEntity.getMailCc();
						if (cc != null && !cc.equals("")) {
							String[] strCC = cc.split(",");
							List<String> userMailCCList = new ArrayList<String>();
							for (String userMail : strCC) {
								if (StringUtil.isNotEmpty(StringUtil.trim(userMail))) {
									userMailCCList.add(userMail);
								}
							}
							if (userMailCCList.size() == 0) {
								throw new FoxBPMBizException("Mail ccaddress is null");
							}
							String[] userMailCCFinStrings = (String[]) userMailCCList.toArray(new String[userMailCCList.size()]);
							mailUtil.setCC(userMailCCFinStrings);
						}
						String title = mailEntity.getMailSubject();
						String mailContent = mailEntity.getMailBody();
						mailUtil.setFrom(mailInfoObj.getMailAddress());
						mailUtil.setSubject(title);
						mailUtil.setBody(mailContent);
						mailUtil.setContentType(MailUtil.MODE_HTML);
						// 同步发送
						mailUtil.send();
						// 更新邮件状态
						Map<String, Object> objectParam = new HashMap<String, Object>();
						objectParam.put(EntityFieldName.MAIL_STATUS, MailStatus.COMPLETE.toString());
						// 构建Where查询参数
						Object[] objectParamWhere = { StringUtil.getString(mapData.get(EntityFieldName.MAIL_ID)) };
						sqlCommand.update(EntityFieldName.T_MAIL, objectParam, " MAIL_ID=?", objectParamWhere);
					} catch (Exception e) {
						try {
							Map<String, Object> objectParam = new HashMap<String, Object>();
							objectParam.put(EntityFieldName.MAIL_STATUS, MailStatus.FAILURE.toString());
							objectParam.put(EntityFieldName.MAIL_FAILURE_REASON, e.getMessage());
							// 构建Where查询参数
							Object[] objectParamWhere = { StringUtil.getString(mapData.get(EntityFieldName.MAIL_ID)) };
							sqlCommand.update(EntityFieldName.T_MAIL, objectParam, " MAIL_ID=?", objectParamWhere);
						} catch (Exception e2) {
							e.printStackTrace();
							e2.printStackTrace();
						}
					} finally {
						try {
							Map<String, Object> objectParam = new HashMap<String, Object>();
							objectParam.put(EntityFieldName.MAIL_SEND_TIME, new Date());
							// 构建Where查询参数
							Object[] objectParamWhere = { StringUtil.getString(mapData.get(EntityFieldName.MAIL_ID)) };
							sqlCommand.update(EntityFieldName.T_MAIL, objectParam, " MAIL_ID=?", objectParamWhere);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		
		
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
		if (sql.endsWith(SQL_END_DELIMITER)) {
			sql = sql.substring(0, sql.length() - 1 - SQL_END_DELIMITER.length());
		}
		return sql;
	}
}
