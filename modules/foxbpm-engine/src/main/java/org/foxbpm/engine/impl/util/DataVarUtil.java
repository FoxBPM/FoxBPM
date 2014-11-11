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
package org.foxbpm.engine.impl.util;

import java.text.MessageFormat;
import java.util.Map;

import org.foxbpm.engine.exception.FoxBPMDbException;
import org.foxbpm.engine.impl.expression.ExpressionMgmt;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 数据变量工具类
 * 
 * @author yangguangftlp
 * @date 2014年7月28日
 */
public class DataVarUtil {
	/** 实例 */
	private static DataVarUtil instance;
	/** 查询sql */
	private final static String QUERY_DATASQL = "select {0} from {1} where {2} = ?";

	public static DataVarUtil getInstance() {
		if (null == instance) {
			synchronized (DataVarUtil.class) {
				if (null == instance) {
					instance = new DataVarUtil();
				}
			}
		}
		return instance;
	}

	/**
	 * 获取数据值
	 * 
	 * @param dataSource
	 *            数据源
	 * @param bizkey
	 *            业务key
	 * @param field
	 *            字段名称
	 * @return 返回字段值
	 */
	public Object getDataValue(String dataSource, String bizkey, String field,
			FlowNodeExecutionContext executionContext) {
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(
					DBUtils.getDataSource(dataSource));

			String bizObjName = StringUtil.getString(ExpressionMgmt.execute(
					"${_BizName}", executionContext));
			String bizField = StringUtil.getString(ExpressionMgmt.execute(
					"${_BizKeyField}", executionContext));

			String sql = MessageFormat.format(QUERY_DATASQL, new Object[] {
					field, bizObjName, bizField });
			return jdbcTemplate.queryForObject(sql, Object.class,
					new Object[] { bizkey });

		} catch (Exception e) {
			throw new FoxBPMDbException("数据变量值获取失败!", e);
		}
	}
	
	/**
	 * 
	 * @param dataSource
	 * @param bizkey
	 * @param bizName
	 * @param bizKeyField
	 * @param executionContext
	 * @return
	 */
	public Map<String, Object> getDataValue(String dataSource, String bizkey, String bizName, String bizKeyField) {
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(DBUtils.getDataSource(dataSource));
			String sql = MessageFormat.format(QUERY_DATASQL, new Object[]{"*", bizName, bizKeyField});
			return jdbcTemplate.queryForMap(sql, new Object[]{bizkey});
		} catch (Exception e) {
			throw new FoxBPMDbException("数据变量值获取失败!", e);
		}
	}
}
