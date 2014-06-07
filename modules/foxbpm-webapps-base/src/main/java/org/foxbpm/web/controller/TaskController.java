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
import javax.annotation.Resource;

import org.foxbpm.web.service.interfaces.ITaskService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author MEL
 * @date 2014-06-04
 */
@Controller
public class TaskController {
	@Resource (name="taskService")
	private ITaskService taskService;
	
	@RequestMapping("completeTask")
	public ModelAndView completeTask() {
		return new ModelAndView("completeTask");
	}
	
	@RequestMapping("claimTask")
	public String claimTask() {
		return "claimTask";
	}
}