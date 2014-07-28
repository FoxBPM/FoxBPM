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
 * @author kenshin
 * @author ych
 */
package org.foxbpm.engine.spring;

import javax.sql.DataSource;

import org.foxbpm.engine.db.DataSourceManage;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

/**
 * spring数据源管理器，通过spring配置注入数据源
 * 
 * @author ych
 * 
 */
public class SpringDataSourceManager implements DataSourceManage {
	
	private DataSource dataSource;
	@Override
	public void init() {
		
	}
	
	@Override
	public DataSource getDataSource() {
		return dataSource;
	}
	
	@Override
	public DataSource getDataSource(String key) {
		// TODO Auto-generated method stub
		return dataSource;
	}
	
	public void setDataSource(DataSource dataSource) {
		if (dataSource instanceof TransactionAwareDataSourceProxy) {
			this.dataSource = dataSource;
		} else {
			DataSource proxiedDataSource = new TransactionAwareDataSourceProxy(dataSource);
			this.dataSource = proxiedDataSource;
		}
	}
	
}
