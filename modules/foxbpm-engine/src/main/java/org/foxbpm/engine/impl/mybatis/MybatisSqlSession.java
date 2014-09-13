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
import org.foxbpm.engine.impl.interceptor.Session;
import org.foxbpm.engine.sqlsession.ISqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MybatisSqlSession implements ISqlSession,Session {

	SqlSession sqlSession ;
	public static Logger log = LoggerFactory.getLogger(MybatisSqlSession.class);
	public MybatisSqlSession(SqlSession sqlSession){
		this.sqlSession = sqlSession;
	}
	
	@Override
	public void insert(String insertStatement, Object persistentObject) {
		sqlSession.insert(insertStatement, persistentObject);
	}
	
	public int update(String updateStatement ,Object persistentObject) {
		return sqlSession.update(updateStatement, persistentObject);
	}

	public void delete(String statement, Object parameter) {
		sqlSession.delete(statement, parameter);
	}

	public List<?> selectList(String statement) {
		return sqlSession.selectList(statement);
	}
	
	public List<?> selectList(String statement,Object parameter) {
		return sqlSession.selectList(statement, parameter);
	}
	
	public Object selectOne(String statement, Object parameter) {
		Object result = sqlSession.selectOne(statement, parameter);
		return result;
	}
	
	public void commit(){
//		if(this.sqlSession != null){
//			this.sqlSession.commit();
//		}
	}
	
	public void rollback(){
		if(this.sqlSession != null){
			this.sqlSession.rollback();
		}
	}
	
	@Override
	public void close() {
		if(this.sqlSession != null){
			this.sqlSession.close();
		}
	}
	
	@Override
	public void flush() {
		
	}
}
