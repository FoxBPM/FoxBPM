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
package org.foxbpm.portal.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.foxbpm.engine.impl.util.StringUtil;

public abstract class AbstractController {

	public Map<String,Object> getFormData(HttpServletRequest request){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Enumeration<String> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			Object tmp = enu.nextElement();
			Object obj = request
					.getParameter(StringUtil.getString(tmp));
			resultMap.put(StringUtil.getString(tmp), StringUtil.getString(obj));
		}
		return resultMap;
	}
	
}
