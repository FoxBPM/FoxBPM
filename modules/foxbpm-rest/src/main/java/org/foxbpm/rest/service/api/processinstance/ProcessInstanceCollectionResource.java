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
package org.foxbpm.rest.service.api.processinstance;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.foxbpm.engine.RuntimeService;
import org.foxbpm.engine.impl.util.LocationUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.runtime.ProcessInstanceQuery;
import org.foxbpm.rest.common.RestConstants;
import org.foxbpm.rest.common.api.AbstractRestResource;
import org.foxbpm.rest.common.api.DataResult;
import org.foxbpm.rest.common.api.FoxBpmUtil;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.ext.servlet.ServletUtils;
import org.restlet.resource.Get;

/**
 * 流程实例rest接口
 * @author ych
 *
 */
public class ProcessInstanceCollectionResource extends AbstractRestResource {

	
	@SuppressWarnings("unchecked")
	@Get
	public DataResult getProcessInstance(){
		if(!validationUser())
			return null;
		Form queryForm = getQuery();
		Set<String> queryNames = queryForm.getNames();
		
		RuntimeService runtimeService = FoxBpmUtil.getProcessEngine().getRuntimeService();
		ProcessInstanceQuery processIntanceQuery = runtimeService.createProcessInstanceQuery();
		if(queryNames.contains(RestConstants.PARTICIPATE)){
			String participate = StringUtil.getString(getQueryParameter(RestConstants.PARTICIPATE, queryForm));
			processIntanceQuery.taskParticipants(participate);
		}
		if(queryNames.contains(RestConstants.INITIATOR)){
			String initotor = StringUtil.getString(getQueryParameter(RestConstants.INITIATOR, queryForm));
			processIntanceQuery.initiator(initotor);
		}
		
		if(queryNames.contains(RestConstants.PROCESS_KEY)){
			processIntanceQuery.processDefinitionKey(getQueryParameter(RestConstants.PROCESS_KEY, queryForm));
		}
		
		if(queryNames.contains(RestConstants.NAME_LIKE)){
			processIntanceQuery.processDefinitionNameLike(parseLikeValue(getQueryParameter(RestConstants.NAME_LIKE, queryForm)));
		}
		
		if(queryNames.contains(RestConstants.PROCESSINSTANCE_ID)){
			processIntanceQuery.processInstanceId(getQueryParameter(RestConstants.PROCESSINSTANCE_ID, queryForm));
		}
		
		if(queryNames.contains(RestConstants.PROCESS_ID)){
			processIntanceQuery.processDefinitionId(getQueryParameter(RestConstants.PROCESS_ID, queryForm));
		}
		
		if(queryNames.contains(RestConstants.BIZKEY_LIKE)){
			processIntanceQuery.processInstanceBusinessKeyLike(parseLikeValue(getQueryParameter(RestConstants.BIZKEY_LIKE, queryForm)));
		}
		
		if(queryNames.contains("subjectLike")){
			processIntanceQuery.subjectLike(parseLikeValue(getQueryParameter("subjectLike", queryForm)));
		}
		
		if(queryNames.contains("ended")){
			boolean ended = StringUtil.getBoolean(getQueryParameter("ended", queryForm));
			if(ended){
				processIntanceQuery.isEnd();
			}else{
				processIntanceQuery.notEnd();
			}
		}
		
		if(queryNames.contains("status")){
			processIntanceQuery.processInstanceStatus(getQueryParameter("status", queryForm));
		}
		processIntanceQuery.orderByUpdateTime().desc();
		
		DataResult result = paginateList(processIntanceQuery);
		
		List<Map<String,Object>> mapList = (List<Map<String,Object>>)result.getData();
		if(mapList != null && mapList.size()>0){
			for(Map<String,Object> tmp : mapList){
				String initator = StringUtil.getString(tmp.get("initiator"));
				String initatorName = getUserName(initator);
				tmp.put("initatorName", initatorName);
				String processLocation = StringUtil.getString(tmp.get("processLocation"));
				String processLocationString = LocationUtil.parseProcessLocation(processLocation);
				tmp.put("processLocationString", processLocationString);
			}
		}
		return result;
	}
	
	protected boolean validationUser(){
		
		HttpServletRequest request = ServletUtils.getRequest(getRequest());
		String userId = (String)request.getSession().getAttribute("userId");
		
		Form queryForm = getQuery();
		String participate = getQueryParameter(RestConstants.PARTICIPATE, queryForm);
		String initiator = getQueryParameter(RestConstants.INITIATOR, queryForm);
		if(userId == null || (participate == null && !userId.equals(initiator)) || (participate != null && !userId.equals(participate))){
			setStatus(Status.CLIENT_ERROR_UNAUTHORIZED,"对应的请求无权访问！");
			return false;
		}
		return true;
	}
}
