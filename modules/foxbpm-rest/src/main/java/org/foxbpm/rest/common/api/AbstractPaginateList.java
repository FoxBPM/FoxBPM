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

import java.util.List;
import java.util.Map;

import org.foxbpm.engine.exception.FoxBPMIllegalArgumentException;
import org.foxbpm.engine.impl.query.AbstractQuery;
import org.foxbpm.engine.query.Query;
import org.foxbpm.engine.query.QueryProperty;
import org.restlet.data.Form;

/**
 * rest请求返回分页结果集,参考自activiti设计
 * @author ych
 *
 */
public abstract class AbstractPaginateList {

	@SuppressWarnings("rawtypes")
	public DataResult paginateList(Form form, Query query, String defaultSort, Map<String, QueryProperty> properties) {

		// Collect parameters
		int start = RequestUtil.getInteger(form, "start", 0);
		int size = RequestUtil.getInteger(form, "size", 10);
		String sort = form.getValues("sort");
		if (sort == null) {
			sort = defaultSort;
		}
		String order = form.getValues("order");
		if (order == null) {
			order = "asc";
		}

		// Sort order
		if (sort != null && properties.size() > 0) {
			QueryProperty qp = properties.get(sort);
			if (qp == null) {
				throw new FoxBPMIllegalArgumentException("Value for param 'sort' is not valid, '" + sort + "' is not a valid property");
			}
			((AbstractQuery) query).orderBy(qp);
			if (order.equals("asc")) {
				query.asc();
			} else if (order.equals("desc")) {
				query.desc();
			} else {
				throw new FoxBPMIllegalArgumentException("Value for param 'order' is not valid : '" + order + "', must be 'asc' or 'desc'");
			}
		}

		List list = processList(query.listPage(start, size));
		DataResult response = new DataResult();
		response.setStart(start);
		response.setSize(list.size());
		response.setSort(sort);
		response.setOrder(order);
		response.setTotal(query.count());
		response.setData(list);
		return response;
	}

	@SuppressWarnings("rawtypes")
	protected abstract List processList(List list);
}
