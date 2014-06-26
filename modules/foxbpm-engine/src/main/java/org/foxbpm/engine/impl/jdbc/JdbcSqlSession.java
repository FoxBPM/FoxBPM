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

import org.foxbpm.engine.db.PersistentObject;
import org.foxbpm.engine.impl.db.ListQueryParameterObject;
import org.foxbpm.engine.sqlsession.ISqlSession;

@SuppressWarnings("rawtypes")
public class JdbcSqlSession implements ISqlSession {
	public void closeSession() {
		// TODO Auto-generated method stub

	}

	public void delete(String deleteStatement, Object parameter) {
		// TODO Auto-generated method stub

	}

	@Override
	public List selectList(String statement, Object parameter, int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	public void insert(PersistentObject persistentObject) {
		// TODO Auto-generated method stub

	}

	public void delete(String deleteStatement, PersistentObject persistentObject) {
		// TODO Auto-generated method stub

	}

	public void update(PersistentObject persistentObject) {
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

	public <T extends PersistentObject> T selectById(Class<T> entityClass, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List selectList(String statement, ListQueryParameterObject parameter) {
		// TODO Auto-generated method stub
		return null;
	}

	public List selectListWithRawParameter(String statement, Object parameter) {
		// TODO Auto-generated method stub
		return null;
	}

	public void flush() {
		// TODO Auto-generated method stub

	}

	public void commit() {
		// TODO Auto-generated method stub

	}

	public void rollback() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(PersistentObject persistentObject) {
		// TODO Auto-generated method stub

	}
}
