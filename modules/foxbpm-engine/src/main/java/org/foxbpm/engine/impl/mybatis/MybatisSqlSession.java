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
package org.foxbpm.engine.impl.mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.foxbpm.engine.impl.db.PersistentObject;
import org.foxbpm.engine.sqlsession.ISqlSession;

public class MybatisSqlSession implements ISqlSession {

	SqlSession sqlSession ;
	public MybatisSqlSession(SqlSession sqlSession){
		this.sqlSession = sqlSession;
	}

	public void insert(String insertStatement, PersistentObject persistentObject) {
		sqlSession.insert(insertStatement, persistentObject);
	}

	public void delete(String deleteStatement, Object parameter) {
		sqlSession.delete(deleteStatement, parameter);
	}

	public void delete(String deleteStatement, PersistentObject persistentObject) {
		sqlSession.delete(deleteStatement, persistentObject);

	}

	public void update(String updateStatement, PersistentObject persistentObject) {
		sqlSession.update(updateStatement, persistentObject);
	}

	public List selectList(String statement) {
		return sqlSession.selectList(statement);
	}

	public List selectList(String statement, Object parameter) {
		return sqlSession.selectList(statement, parameter);
	}

	public Object selectOne(String statement, Object parameter) {
		return null;
	}

}
