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
 * @author MEL
 */
package org.foxbpm.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.web.common.constant.FoxbpmActionNameDefinition;
import org.foxbpm.web.common.constant.FoxbpmServiceNameDefinition;
import org.foxbpm.web.common.constant.FoxbpmViewNameDefinition;
import org.foxbpm.web.common.exception.FoxbpmWebException;
import org.foxbpm.web.service.interfaces.IWebappProcessService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * MVC 控制器
 * 
 * @author MEL
 * @date 2014-06-04
 */
@Controller
public class WebappProcessController {
	@Resource(name = FoxbpmServiceNameDefinition.PROCESS_SERVICENAME)
	private IWebappProcessService webappProcessService;

	/**
	 * 对应到前端请求的action
	 * 
	 * @param parameter
	 *            形参名称必须和请求参数名称一致
	 * @return ModelAndView
	 */
	@RequestMapping(FoxbpmActionNameDefinition.QUERY_PROCESSDEFINITION_ACTION)
	public ModelAndView queryProcessDefinition(String parameter) {
		try {

			List<ProcessDefinition> processDefinitionList = webappProcessService
					.queryProcessDefinition();
		} catch (FoxbpmWebException foxbpmException) {
			return new ModelAndView("error");
		}
		ModelAndView modelAndView = new ModelAndView(
				FoxbpmViewNameDefinition.START_PROCESS_VIEWNAME);
		return modelAndView;
	}

}