package org.foxbpm.connector.flowconnector.DatasourceDatabaseQuery;

import org.foxbpm.connector.common.constant.Constants;
import org.foxbpm.engine.exception.FoxBPMBizException;
import org.foxbpm.engine.execution.ConnectorExecutionContext;
import org.foxbpm.engine.impl.connector.FlowConnectorHandler;
import org.foxbpm.engine.impl.db.SqlCommand;
import org.foxbpm.engine.impl.util.DBUtils;
import org.foxbpm.engine.impl.util.StringUtil;

public class DatasourceDatabaseQuery implements FlowConnectorHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8595980483313031237L;

	private java.lang.String sqlText;

	private java.util.List<java.util.Map<String, Object>> outputObj;

	public void execute(ConnectorExecutionContext executionContext) throws Exception {

		if (StringUtil.isEmpty(sqlText)) {
			throw new FoxBPMBizException("sqlText is null!");
		}
		// 只处理查询sql
		if (StringUtil.trim(sqlText).toLowerCase().startsWith(Constants.SQL_SELECT)) {
			SqlCommand sqlCommand = new SqlCommand(DBUtils.getConnection());
			outputObj = sqlCommand.queryForList(sqlText);
		} else {
			// 如果不是select 查询语句
			throw new FoxBPMBizException("执行无效的查询sql错误,请检查sql语句:" + sqlText);
		}
	}

	public void setSqlText(java.lang.String sqlText) {
		this.sqlText = sqlText;
	}

	public java.util.List<java.util.Map<String, Object>> getOutputObj() {
		return outputObj;
	}

}