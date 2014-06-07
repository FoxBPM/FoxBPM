package org.foxbpm.web.common.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.foxbpm.web.common.exception.FoxbpmWebException;

/**
 * 数据库工具类
 * 
 * @author MEL
 * @date 2014-06-06
 */
public final class DBUtils {
	public static void closeConnectionAndStatement(Connection connection,
			PreparedStatement prepareStatement) throws FoxbpmWebException {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
			if (prepareStatement != null) {
				prepareStatement.close();
			}
		} catch (SQLException e) {
			throw new FoxbpmWebException(String.valueOf(e.getErrorCode()),
					e.getMessage());
		}
	}
}
