package org.foxbpm.engine.spring;

import javax.sql.DataSource;

import org.foxbpm.engine.db.DataSourceManage;

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
		return null;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	

}
