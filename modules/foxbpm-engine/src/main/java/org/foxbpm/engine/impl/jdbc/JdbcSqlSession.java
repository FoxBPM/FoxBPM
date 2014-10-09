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

import java.util.List;

import org.foxbpm.engine.sqlsession.ISqlSession;

public class JdbcSqlSession implements ISqlSession {

	 
	public void insert(String insertStatement, Object persistentObject) {
		// TODO Auto-generated method stub
		
	}

	 
	public void delete(String deleteStatement, Object parameter) {
		// TODO Auto-generated method stub
		
	}

	 
	public int update(String updateStatement, Object persistentObject) {
		// TODO Auto-generated method stub
		return 0;
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

	 
	public void commit() {
		// TODO Auto-generated method stub
		
	}

	 
	public void rollback() {
		// TODO Auto-generated method stub
		
	}
	
}
