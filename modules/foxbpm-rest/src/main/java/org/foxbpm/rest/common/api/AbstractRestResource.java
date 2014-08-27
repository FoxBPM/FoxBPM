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
package org.foxbpm.rest.common.api;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.foxbpm.engine.db.PersistentObject;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.query.Query;
import org.foxbpm.rest.common.RestConstants;
import org.restlet.data.Form;
import org.restlet.resource.ServerResource;

/**
 * foxbpm rest资源基础实现，包含基础方法
 * @author ych
 *
 */
public abstract class AbstractRestResource extends ServerResource {

	protected int pageIndex = 1;
	protected int pageSize = 15;
	protected String getQueryParameter(String name, Form query) {
		return query.getFirstValue(name);
	}

	protected String getAttribute(String name) {
		return decode((String) getRequest().getAttributes().get(name));
	}

	protected String decode(String string) {
		if (string != null) {
			try {
				return URLDecoder.decode(string, "UTF-8");
			} catch (UnsupportedEncodingException uee) {
				throw new IllegalStateException("JVM does not support UTF-8 encoding.", uee);
			}
		}
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DataResult paginateList(Query query) {

		Form form = getQuery();
		Set<String> names = form.getNames();
		if (names.contains(RestConstants.PAGE_START)) {
			if(names.contains(RestConstants.PAGE_LENGTH)){
				pageSize = StringUtil.getInt(getQueryParameter(RestConstants.PAGE_LENGTH, form));
			}
			pageIndex = StringUtil.getInt(getQueryParameter(RestConstants.PAGE_START, form))/pageSize + 1;
		}
		List<PersistentObject> resultObjects = query.listPagination(pageIndex,pageSize);
		List<Map<String, Object>> dataMap = new ArrayList<Map<String,Object>>();
		if(resultObjects != null){
			Iterator<PersistentObject> iterator = resultObjects.iterator();
			while(iterator.hasNext()){
				dataMap.add(iterator.next().getPersistentState());
			}
		}
		
		DataResult result = new DataResult();
		result.setData(dataMap);
		result.setPageIndex(pageIndex);
		result.setPageSize(pageSize);
		result.setTotal(query.count());
		result.setRecordsTotal(query.count());
		result.setRecordsFiltered(query.count());
		return result;
	}
}
