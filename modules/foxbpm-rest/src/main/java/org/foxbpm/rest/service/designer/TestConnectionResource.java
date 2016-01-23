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
package org.foxbpm.rest.service.designer;

import org.foxbpm.rest.common.api.AbstractRestResource;
import org.restlet.resource.Get;

/**
 * 设计器测试网络通讯是否正常
 * @author ych
 *
 */
public class TestConnectionResource extends AbstractRestResource {

	@Get
	public String getMessage(){

		// 只允许内部网络访问
		onlyAllowIntranetAccess();
		
		return "SUCCESS";
	}
}
