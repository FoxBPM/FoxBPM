package org.foxbpm.engine.impl.schedule.db;

import java.sql.Connection;

import org.foxbpm.engine.impl.util.DBUtils;
import org.quartz.JobPersistenceException;
import org.quartz.impl.jdbcjobstore.JobStoreTX;

public class FoxbpmJobStore extends JobStoreTX {
	@Override
	protected Connection getConnection() throws JobPersistenceException {
		return DBUtils.getConnection();
	}

	@Override
	protected void closeConnection(Connection connection) {
		// super.closeConnection(connection);
	}

	@Override
	protected void commitConnection(Connection connection) throws JobPersistenceException {
		// super.commitConnection(connection);
	}

	@Override
	protected void rollbackConnection(Connection connection) {
		// super.rollbackConnection(connection);
	}
}
