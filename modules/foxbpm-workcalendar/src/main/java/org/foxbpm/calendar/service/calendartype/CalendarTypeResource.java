//package org.foxbpm.calendar.service.calendartype;
//
//import java.util.Set;
//
//import org.foxbpm.calendar.service.WorkCalendarService;
//import org.foxbpm.engine.TaskService;
//import org.foxbpm.engine.impl.util.StringUtil;
//import org.foxbpm.engine.task.TaskQuery;
//import org.foxbpm.rest.common.RestConstants;
//import org.foxbpm.rest.common.api.DataResult;
//import org.foxbpm.rest.common.api.FoxBpmUtil;
//import org.restlet.data.Form;
//import org.restlet.resource.Get;
//
//public class CalendarTypeResource {
//	@Get
//	public DataResult getTasks(){
//		
//		Form queryForm = getQuery();
//		Set<String> queryNames = queryForm.getNames();
//		
//		if(!validationUser())
//			return null;
//		
//		WorkCalendarService workCalendarService = null//怎么来;
//		
//		boolean ended = false;
//		if(queryNames.contains(RestConstants.IS_END)){
//			ended = StringUtil.getBoolean(getQueryParameter(RestConstants.IS_END, queryForm));
//		}
//	
//		DataResult result = paginateList(workCalendarService.getCalendarType());
//		
//		return result;
//	}
//}
