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
package org.foxbpm.connector.flowconnector.JDBCDatabaseQuery;

import java.sql.Connection;
import java.sql.DriverManager;

import org.foxbpm.connector.common.constant.Constants;
import org.foxbpm.engine.exception.FoxBPMConnectorException;
import org.foxbpm.engine.execution.ConnectorExecutionContext;
import org.foxbpm.engine.impl.connector.FlowConnectorHandler;
import org.foxbpm.engine.impl.db.SqlCommand;
import org.foxbpm.engine.impl.util.StringUtil;

/**
 * JDBC数据库查询
 * 
 * @author yangguangftlp
 * @date 2014年7月7日
 */
public class JDBCDatabaseQuery implements FlowConnectorHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3120408358341797232L;

	private java.lang.String driverClassName;

	private java.lang.String url;

	private java.lang.String username;

	private java.lang.String password;

	private java.lang.String sqlText;

	private java.util.List<java.util.Map<String, Object>> outputObj;

	public void execute(ConnectorExecutionContext executionContext) throws Exception {

		if (StringUtil.isEmpty(driverClassName)) {
			throw new FoxBPMConnectorException("driverClassName is null!");
		}
		if (StringUtil.isEmpty(url)) {
			throw new FoxBPMConnectorException("url is null!");
		}
		if (StringUtil.isEmpty(username)) {
			throw new FoxBPMConnectorException("username is null!");
		}
		if (StringUtil.isEmpty(password)) {
			throw new FoxBPMConnectorException("password is null!");
		}
		if (StringUtil.isEmpty(sqlText)) {
			throw new FoxBPMConnectorException("sqlText is null!");
		}
		// 只处理查询sql
		if (StringUtil.trim(sqlText).toLowerCase().startsWith(Constants.SQL_SELECT)) {
			// 加载驱动
			Class.forName(driverClassName);
			Connection connection = null;
			try {
				// 打开一个数据库连接
				connection = DriverManager.getConnection(url, username, password);
				SqlCommand sqlCommand = new SqlCommand(connection);
				outputObj = sqlCommand.queryForList(sqlText);
			} finally {
				if (null != connection) {
					// 释放连接
					connection.close();
				}
			}
		} else {
			// 如果不是select 查询语句
			throw new FoxBPMConnectorException("执行无效的查询sql错误,请检查sql语句:" + sqlText);
		}
	}

	public void setDriverClassName(java.lang.String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public void setUrl(java.lang.String url) {
		this.url = url;
	}

	public void setUsername(java.lang.String username) {
		this.username = username;
	}

	public void setPassword(java.lang.String password) {
		this.password = password;
	}

	public void setSqlText(java.lang.String sqlText) {
		this.sqlText = sqlText;
	}

	public java.util.List<java.util.Map<String, Object>> getOutputObj() {
		return outputObj;
	}

}