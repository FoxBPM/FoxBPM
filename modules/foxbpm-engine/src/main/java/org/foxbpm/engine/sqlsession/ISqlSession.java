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
package org.foxbpm.engine.sqlsession;

import java.util.List;

import org.foxbpm.engine.db.PersistentObject;
import org.foxbpm.engine.impl.db.ListQueryParameterObject;

public interface ISqlSession {
	public void insert(PersistentObject persistentObject);

	public void delete(String deleteStatement, Object parameter);

	public void delete(PersistentObject persistentObject);

	public void update(PersistentObject persistentObject);

	public List<?> selectList(String statement);

	@SuppressWarnings("rawtypes")
	public List selectList(String statement, ListQueryParameterObject parameter);

	@SuppressWarnings("rawtypes")
	public List selectList(String statement, Object parameter, int firstResult, int maxResults);

	@SuppressWarnings("rawtypes")
	public List selectListWithRawParameter(String statement, Object parameter);

	public Object selectOne(String statement, Object parameter);

	public <T extends PersistentObject> T selectById(Class<T> entityClass, String id);

	public void flush();

	public void closeSession();

	public void commit();

	public void rollback();
}
