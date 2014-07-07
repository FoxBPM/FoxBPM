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
package org.foxbpm.connector.flowconnector.DatasourceDatabaseQuery;

import org.foxbpm.connector.common.constant.Constants;
import org.foxbpm.engine.exception.FoxBPMConnectorException;
import org.foxbpm.engine.execution.ConnectorExecutionContext;
import org.foxbpm.engine.impl.connector.FlowConnectorHandler;
import org.foxbpm.engine.impl.db.SqlCommand;
import org.foxbpm.engine.impl.util.DBUtils;
import org.foxbpm.engine.impl.util.StringUtil;

/**
 * datasource查询数据库
 * 
 * @author yangguangftlp
 * @date 2014年7月7日
 */
public class DatasourceDatabaseQuery implements FlowConnectorHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8595980483313031237L;

	private java.lang.String sqlText;

	private java.util.List<java.util.Map<String, Object>> outputObj;

	public void execute(ConnectorExecutionContext executionContext) throws Exception {

		if (StringUtil.isEmpty(sqlText)) {
			throw new FoxBPMConnectorException("sqlText is null!");
		}
		// 只处理查询sql
		if (StringUtil.trim(sqlText).toLowerCase().startsWith(Constants.SQL_SELECT)) {
			SqlCommand sqlCommand = new SqlCommand(DBUtils.getConnection());
			outputObj = sqlCommand.queryForList(sqlText);
		} else {
			// 如果不是select 查询语句
			throw new FoxBPMConnectorException("执行无效的查询sql错误,请检查sql语句:" + sqlText);
		}
	}

	public void setSqlText(java.lang.String sqlText) {
		this.sqlText = sqlText;
	}

	public java.util.List<java.util.Map<String, Object>> getOutputObj() {
		return outputObj;
	}

}