package org.foxbpm.connector.flowconnector.JDBCDatabaseQuery;

import java.sql.Connection;
import java.sql.DriverManager;

import org.foxbpm.connector.common.constant.Constants;
import org.foxbpm.engine.exception.FoxBPMBizException;
import org.foxbpm.engine.execution.ConnectorExecutionContext;
import org.foxbpm.engine.impl.connector.FlowConnectorHandler;
import org.foxbpm.engine.impl.db.SqlCommand;
import org.foxbpm.engine.impl.util.StringUtil;

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
			throw new FoxBPMBizException("driverClassName is null!");
		}
		if (StringUtil.isEmpty(url)) {
			throw new FoxBPMBizException("url is null!");
		}
		if (StringUtil.isEmpty(username)) {
			throw new FoxBPMBizException("username is null!");
		}
		if (StringUtil.isEmpty(password)) {
			throw new FoxBPMBizException("password is null!");
		}
		if (StringUtil.isEmpty(sqlText)) {
			throw new FoxBPMBizException("sqlText is null!");
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
			throw new FoxBPMBizException("执行无效的查询sql错误,请检查sql语句:" + sqlText);
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