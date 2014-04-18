package org.foxbpm.engine.impl.database;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.foxbpm.engine.database.DataSourceManage;

public class DefaultDataSourceManage implements DataSourceManage {

	public DataSource getDataSource() {
		BasicDataSource bs = new BasicDataSource();
		bs.setDriverClassName("com.mysql.jdbc.Driver");
		bs.setUrl("jdbc:mysql://172.16.40.89/idbase?characterEncoding=UTF-8");
		bs.setUsername("root");
		bs.setPassword("fixflow");
		bs.setMaxActive(20);
		bs.setMaxIdle(8);
		return bs;
	}

}
