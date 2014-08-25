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
 * @author yangguangftlp
 */
package org.foxbpm.rest.service.api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.impl.datavariable.BizDataObject;
import org.foxbpm.rest.common.api.AbstractRestResource;
import org.foxbpm.rest.common.api.DataResult;
import org.foxbpm.rest.common.api.FoxBpmUtil;
import org.restlet.resource.Get;

/**
 * 业务数据对象资源获取
 * 
 * @author yangguangftlp
 * @date 2014年7月26日
 */
public class BizDataObjectResouce extends AbstractRestResource {
	@Get
	public DataResult getBizDataObject() {
		String behaviorId = getAttribute("behaviorId");
		String dataSource = getAttribute("dataSource");
		List<BizDataObject> bizDataObjects = FoxBpmUtil.getProcessEngine().getModelService().getBizDataObject(behaviorId, dataSource);
		// 数据转换
		List<Map<String, Object>> resultDatas = new ArrayList<Map<String, Object>>();
		for (BizDataObject bizDataObject : bizDataObjects) {
			resultDatas.add(bizDataObject.getPersistentState());
		}
		//数据响应体构造
		DataResult result = new DataResult();
		result.setData(resultDatas);
		result.setTotal(bizDataObjects.size());
		return result;
	}
}
