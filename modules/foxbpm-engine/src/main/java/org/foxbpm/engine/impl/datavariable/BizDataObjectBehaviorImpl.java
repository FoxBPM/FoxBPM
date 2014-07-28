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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	
	@Override
	public List<BizDataObject> getDataObjects(String dataSource) {
		LOG.debug("getDataObjects(String dataSource),dataSource=" + dataSource);
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(DBUtils.getDataSource(dataSource));
			// 获取此 当前数据源连接 对象的当前目录名称。
			String catalog = jdbcTemplate.getDataSource().getConnection().getCatalog();
			StringBuffer sql = new StringBuffer("select * from information_schema.columns");
			sql.append(" where TABLE_SCHEMA = '").append(catalog).append("'");
			
			LOG.info("the sql is " + sql);
			
			SqlRowSet rs = jdbcTemplate.queryForRowSet(sql.toString());
			List<BizDataObject> bizDataObjects = new ArrayList<BizDataObject>();
			BizDataObject bizDataObject = null;
			DataVariableDefinition dataVariableDefine = null;
			String tableName = null;
			while (rs.next()) {
				// 处理首次和区分不同表
				if (!rs.getString("TABLE_NAME").equals(tableName)) {
					bizDataObject = new BizDataObject();
					bizDataObject.setId(rs.getString("TABLE_NAME"));
					bizDataObject.setDataSource(dataSource);
					bizDataObject.setDocumentation(rs.getString("COLUMN_COMMENT"));
					// 添加业务数据对象
					bizDataObjects.add(bizDataObject);
				}
				dataVariableDefine = new DataVariableDefinition();
				dataVariableDefine.setId(rs.getString("COLUMN_NAME"));
				dataVariableDefine.setDataType(rs.getString("DATA_TYPE"));
				dataVariableDefine.setExpression("bizData.getDataValue();");
				dataVariableDefine.setDocumentation(rs.getString("COLUMN_COMMENT"));
				dataVariableDefine.setBizType("biztype");
				// 添加数据变量定义
				bizDataObject.getDataVariableDefinitions().add(dataVariableDefine);
				tableName = rs.getString("TABLE_NAME");
			}
			LOG.debug("end getDataObjects(String dataSource)");
			return bizDataObjects;
		} catch (SQLException e) {
			throw new FoxBPMDbException("获取数据对象失败!", e);
		}
	}
}
