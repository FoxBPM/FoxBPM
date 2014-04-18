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
package org.foxbpm.engine.impl.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.exception.FixFlowException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.db.PersistentObject;
import org.foxbpm.engine.sqlsession.ISqlSession;
import org.foxbpm.engine.sqlsession.ISqlSessionFactory;

public class JdbcSqlSession implements ISqlSession {
	Connection connection ;
	
	public JdbcSqlSession(){
		DataSource dataSource = ProcessEngineManagement.getDefaultProcessEngine().getProcessEngineConfiguration().getDataSource();
		connection = Context.getDbConnection();
		if( connection== null){
			try {
				connection = dataSource.getConnection();
			} catch (SQLException e) {
				throw new FixFlowException("jdbc数据库连接获取失败，请检查连接池配置",e);
			}
		}
	}
	public void insert(String insertStatement, PersistentObject persistentObject) {
		// TODO Auto-generated method stub
		
	}

	public void delete(String deleteStatement, Object parameter) {
		// TODO Auto-generated method stub
		
	}

	public void delete(String deleteStatement, PersistentObject persistentObject) {
		// TODO Auto-generated method stub
		
	}

	public void update(String updateStatement, PersistentObject persistentObject) {
		// TODO Auto-generated method stub
		
	}

	public List<?> selectList(String statement) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<?> selectList(String statement, Object parameter) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object selectOne(String statement, Object parameter) {
		// TODO Auto-generated method stub
		return null;
	}

}
