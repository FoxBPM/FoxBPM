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
 * @author demornain
 */
package org.foxbpm.calendar.mybatis.cmd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.calendar.mybatis.entity.CalendarTypeEntity;
import org.foxbpm.engine.impl.db.ListQueryParameterObject;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;

public class GetCalendarTypeCmd implements Command<List<?>> {
	private int pageIndex;
	private int pageSize;
	private String idLike;
	private String nameLike;
	
	public GetCalendarTypeCmd(int pageIndex, int pageSize, String idLike) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.idLike = idLike;
	}
	
	public GetCalendarTypeCmd(int pageIndex, int pageSize, String idLike, String nameLike) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.idLike = idLike;
		this.nameLike = nameLike;
	}

	@SuppressWarnings("unchecked")
	 
	public List<CalendarTypeEntity> execute(CommandContext commandContext) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("id", idLike);
		queryMap.put("name", nameLike);
		int firstResult = pageIndex * pageSize - pageSize;
		int maxResults = pageSize;
		ListQueryParameterObject queryParams = new ListQueryParameterObject(queryMap, firstResult, maxResults);
		return (List<CalendarTypeEntity>) commandContext.getSqlSession().selectList("selectAllCalendarType", queryParams);
	}

}
