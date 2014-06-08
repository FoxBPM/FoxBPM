package org.foxbpm.web.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import lombok.Setter;

import org.foxbpm.web.common.exception.FoxbpmWebException;
import org.foxbpm.web.common.util.DBUtils;
import org.foxbpm.web.db.factory.FoxbpmDBConnectionFactory;
import org.foxbpm.web.db.interfaces.BizDBInterface;
import org.foxbpm.web.model.BizInfo;

public class BizDBImpl implements BizDBInterface {
	private final static String SQL_INERT_BIZINFO = "INSERT INTO BIZ_INFO VALUES(?,?,?)";
	@Setter
	private FoxbpmDBConnectionFactory dbfactory;
	@Override
	public void insertBizInfo(BizInfo bizInfo,Connection connection) throws FoxbpmWebException {
		PreparedStatement prepareStatement = null;
		Connection connections = dbfactory.createConnection();
		try {
			prepareStatement = connection.prepareStatement(SQL_INERT_BIZINFO);
			prepareStatement.setString(1, bizInfo.getId());
			prepareStatement.setString(2, bizInfo.getName());
			prepareStatement.setString(3, bizInfo.getComment());
			prepareStatement.execute();
		} catch (SQLException e) {
			throw new FoxbpmWebException(e.getMessage(),String.valueOf(e.getErrorCode()),e);
		} finally {
			DBUtils.closeConnectionAndStatement(null, prepareStatement);
		}
	}

}
