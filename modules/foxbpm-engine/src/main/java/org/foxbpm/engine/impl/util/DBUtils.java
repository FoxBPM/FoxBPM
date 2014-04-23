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
 * @author ych
 */
package org.foxbpm.engine.impl.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.db.DataSourceManage;
import org.foxbpm.engine.exception.FoxBPMDbException;

/**
 * 数据源工具类
 * @author ych
 *
 */
public class DBUtils {

	public static DataSource getDataSource(){
		return getDataSource(DataSourceManage.DAFAULT_DATABASE_ID);
	}
	public static DataSource getDataSource(String key){
		DataSourceManage dataSourceManage = ProcessEngineManagement.getDefaultProcessEngine().getProcessEngineConfiguration().getDataSourceManage();
		return dataSourceManage.getDataSource(key);
	}
	public static Connection getConnection(){
		return getConnection(DataSourceManage.DAFAULT_DATABASE_ID);
	}
	public static Connection getConnection(String key){
		DataSourceManage dataSourceManage = ProcessEngineManagement.getDefaultProcessEngine().getProcessEngineConfiguration().getDataSourceManage();
		try {
			return dataSourceManage.getDataSource().getConnection();
		} catch (SQLException e) {
			throw new FoxBPMDbException("获取数据库连接："+key+" 出错",e);
		}
	}
}
