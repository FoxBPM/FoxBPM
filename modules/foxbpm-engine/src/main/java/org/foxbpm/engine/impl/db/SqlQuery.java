/**
 * Copyright 1996-2014 FoxBPM Co.,Ltd.
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
 */
package org.foxbpm.engine.impl.db;

import java.util.List;

public class SqlQuery {
	
	protected QueryList queryList;
	

	

	
	protected QueryMap queryMap;
	
	protected QueryForValue queryForValue;
	
	public SqlQuery() {
		
	}
	
	public SqlQuery queryList(String sqlText) {
		queryList=new QueryList(sqlText);
		return this;
	}
	
	public SqlQuery queryList(String sqlText,List<Object> data) {
		queryList=new QueryList(sqlText,data);
		return this;
	}
	
	public SqlQuery queryMap(String sqlText) {
		queryMap=new QueryMap(sqlText);
		return this;
	}
	
	public SqlQuery queryMap(String sqlText,List<Object> data) {
		queryMap=new QueryMap(sqlText,data);
		return this;
	}
	
	public SqlQuery queryForValue(String sqlText) {
		queryForValue=new QueryForValue(sqlText);
		return this;
	}
	
	public SqlQuery queryForValue(String sqlText,List<Object> data) {
		queryForValue=new QueryForValue(sqlText,data);
		return this;
	}
	
	public QueryList getQueryList() {
		return queryList;
	}

	public QueryMap getQueryMap() {
		return queryMap;
	}

	public QueryForValue getQueryForValue() {
		return queryForValue;
	}

	

}
