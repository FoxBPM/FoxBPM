package org.foxbpm.web.db;

import java.sql.Connection;

import org.foxbpm.web.common.util.SpringConfigLoadUtils;
import org.foxbpm.web.db.factory.FoxbpmDBConnectionFactory;

public class DBConnectionManager {
	private static final String CONNECTION_FACTORY_NAME = "foxbpmConnectionFactory";
	private Connection connection = null;
	private static DBConnectionManager instance;

	private DBConnectionManager() {
	}

	public synchronized Connection getConnection() {
		if (connection != null) {
			return connection;
		} else {
			this.initConnection();
			return connection;
		}

	}

	public void initConnection() {
		FoxbpmDBConnectionFactory connedctionFactory = (FoxbpmDBConnectionFactory) SpringConfigLoadUtils
				.getBean(CONNECTION_FACTORY_NAME);
		connection = connedctionFactory.createConnection();
	}

	public static DBConnectionManager getInstance() {
		if (instance == null) {
			instance = new DBConnectionManager();
		}
		return instance;
	}

}
