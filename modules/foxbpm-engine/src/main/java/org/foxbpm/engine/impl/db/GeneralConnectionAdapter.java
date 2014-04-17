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
package org.foxbpm.engine.impl.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.foxbpm.engine.db.FoxConnectionAdapter;
import org.foxbpm.engine.exception.FixFlowDbException;

public class GeneralConnectionAdapter implements FoxConnectionAdapter {

	protected String dbId;

	protected Connection connection;

	public GeneralConnectionAdapter(String dbId) {
		this.dbId = dbId;
	}

	public GeneralConnectionAdapter(String dbId, Connection connection) {
		this.dbId = dbId;
		this.connection = connection;
	}

	public GeneralConnectionAdapter(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		if (this.connection == null) {
			Connection connection = null;
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://172.16.40.89:3306/idbase?characterEncoding=UTF-8";
			String user = "root";
			String password = "fixflow";

			try {
				Class.forName(driver);
				connection = DriverManager.getConnection(url, user, password);
				connection.setAutoCommit(false);
			} catch (Exception e) {
				throw new FixFlowDbException("数据库链接创建失败!", e);
			}// com.mysql.jdbc.Driver

			this.connection = connection;
			if (this.connection == null) {
				throw new FixFlowDbException("ID为" + this.dbId + "的数据库连接创建失败!");
			}

			return this.connection;
		} else {
			return this.connection;

		}
	}

	public void colseConnection() {
		try {
			if (!this.connection.isClosed()) {
				this.connection.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new FixFlowDbException(e.getMessage(), e);
		}
	}

	public void commitConnection() {
		try {
			this.connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new FixFlowDbException(e.getMessage(), e);
		}
	}

	public void rollBackConnection() {
		try {
			this.connection.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new FixFlowDbException(e.getMessage(), e);
		}
	}

	public String getDataBaseId() {
		return this.dbId;
	}

}
