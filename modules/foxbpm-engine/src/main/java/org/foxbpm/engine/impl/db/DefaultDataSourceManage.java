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
package org.foxbpm.engine.impl.db;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.foxbpm.engine.db.DataSourceManage;
import org.foxbpm.engine.exception.FoxBPMDbException;

/**
 * 默认数据源管理器
 * 用来向引擎提供数据源
 * @author ych
 *
 */
public class DefaultDataSourceManage implements DataSourceManage {

	private static Map<String,DataSource> dataSourceMap = new HashMap<String,DataSource>();
	
	public void init(){
		BasicDataSource bs = new BasicDataSource();
		bs.setDriverClassName("com.mysql.jdbc.Driver");
		bs.setUrl("jdbc:mysql://172.16.40.89/foxbpm?characterEncoding=UTF-8");
		bs.setUsername("root");
		bs.setPassword("fixflow");
		bs.setMaxActive(20);
		bs.setMaxIdle(8);
		dataSourceMap.put(DAFAULT_DATABASE_ID, bs);
	}
	public DataSource getDataSource() {
		return getDataSource(DAFAULT_DATABASE_ID);
	}
	
	public DataSource getDataSource(String key){
		if(dataSourceMap.containsKey(key)){
			return dataSourceMap.get(key);
		}
		throw new FoxBPMDbException("未找到名称为："+key+" 的数据库连接池");
	}

}
