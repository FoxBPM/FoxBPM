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
package org.foxbpm.engine.impl.datavariable;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.Constant;
import org.foxbpm.engine.datavariable.BizDataObjectBehavior;
import org.foxbpm.engine.exception.FoxBPMDbException;
import org.foxbpm.engine.impl.util.DBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * 业务数据变量行为类
 * 
 * @author yangguangftlp
 * @date 2014年7月26日
 */
public class BizDataObjectBehaviorImpl implements BizDataObjectBehavior {
	
	/** 日志处理 */
	private final static Logger LOG = LoggerFactory.getLogger(BizDataObjectBehaviorImpl.class);
	/** 表名称 */
	private final static String TABLE_NAME = "TABLE_NAME";
	/** 表描述 */
	private final static String TABLE_COMMENT = "TABLE_COMMENT";
	/** 表字段类型 */
	private final static String DATA_TYPE = "DATA_TYPE";
	/** 列名称 */
	private final static String COLUMN_NAME = "COLUMN_NAME";
	/** 列描述 */
	private final static String COLUMN_COMMENT = "COLUMN_COMMENT";
	/** sql */
	private final static String TABLE_INFOR = "select t.*,'TABLE_COMMENT' from information_schema.columns t where TABLE_SCHEMA = ?";
	/** MYSQL类型 */
	private final static String MySQL_TYPE = "MySQL";
	/** ORACLE类型 */
	private final static String ORACLE_TYPE = "Oracle";
	@Override
	public List<BizDataObject> getDataObjects(String dataSource) {
		LOG.debug("getDataObjects(String dataSource),dataSource=" + dataSource);
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(DBUtils.getDataSource());
			DatabaseMetaData dm = DBUtils.getDataSource().getConnection().getMetaData();
			String databaseType = dm.getDatabaseProductName();
			String databaseVersion = dm.getDatabaseProductVersion();
			LOG.info("the database type is " + databaseType + ",version is " + databaseVersion);
			// 定义sql返回结果集
			SqlRowSet rs = null;
			boolean isOracle = false;
			if (MySQL_TYPE.equalsIgnoreCase(databaseType)) {
				// 获取此 当前数据源连接 对象的当前目录名称。
				String catalog = jdbcTemplate.getDataSource().getConnection().getCatalog();
				LOG.info("the sql is " + TABLE_INFOR);
				rs = jdbcTemplate.queryForRowSet(TABLE_INFOR, new Object[]{catalog});
			} else if (ORACLE_TYPE.equalsIgnoreCase(databaseType)) {
				isOracle = true;
				StringBuffer sql = new StringBuffer("select t_col.DATA_TYPE,t_col.TABLE_NAME,t_col.COLUMN_NAME,t_des.comments as TABLE_COMMENT,c_des.comments as COLUMN_COMMENT from user_tab_columns t_col,user_col_comments c_des,user_tab_comments t_des ").append("where t_col.table_name = c_des.table_name and t_col.table_name = t_des.table_name order by t_col.table_name");
				rs = jdbcTemplate.queryForRowSet(sql.toString());
			}
			
			List<BizDataObject> bizDataObjects = new ArrayList<BizDataObject>();
			BizDataObject bizDataObject = null;
			DataVariableDefinition dataVariableDefine = null;
			String tableName = null;
			StringBuffer sbExpression = new StringBuffer();
			// 获取表信息
			while (rs.next()) {
				// 处理首次和区分不同表
				if (!rs.getString(TABLE_NAME).equals(tableName)) {
					bizDataObject = new BizDataObject();
					bizDataObject.setId(rs.getString(TABLE_NAME));
					if (isOracle) {
						bizDataObject.setName(rs.getString(TABLE_COMMENT));
					}
					bizDataObject.setDataSource(dataSource);
					// 添加业务数据对象
					bizDataObjects.add(bizDataObject);
				}
				dataVariableDefine = new DataVariableDefinition();
				dataVariableDefine.setId(rs.getString(COLUMN_NAME));
				dataVariableDefine.setFieldName(rs.getString(COLUMN_NAME));
				dataVariableDefine.setDataType(rs.getString(DATA_TYPE));
				// 生成表达式
				sbExpression.append("import org.foxbpm.engine.impl.util.DataVarUtil;\n");
				sbExpression.append("DataVarUtil.getInstance().getDataValue(");
				sbExpression.append("\"" + dataSource + "\"").append(',').append("processInfo.getProcessInstance().getBizKey(),");
				sbExpression.append("\"" + dataVariableDefine.getId() + "\"");
				sbExpression.append(",processInfo);");
				dataVariableDefine.setExpression(sbExpression.toString());
				
				dataVariableDefine.setDocumentation(rs.getString(COLUMN_COMMENT));
				dataVariableDefine.setBizType(Constant.DB_BIZTYPE);
				// 添加数据变量定义
				bizDataObject.getDataVariableDefinitions().add(dataVariableDefine);
				tableName = rs.getString(TABLE_NAME);
				// 清空sbExpression缓存
				sbExpression.delete(0, sbExpression.length());
			}
			LOG.debug("end getDataObjects(String dataSource)");
			return bizDataObjects;
		} catch (SQLException e) {
			throw new FoxBPMDbException("获取数据对象失败!", e);
		}
	}
}
