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
package org.foxbpm.rest.service.application;

import org.foxbpm.rest.common.application.AbstractRestApplication;
import org.restlet.Restlet;
import org.restlet.routing.Router;

/**
 * foxbpm rest服务暴露的application入口 
 * 需要将此application注册到服务端，如component、defaultHost、web容器等。
 * @author ych
 *
 */
public class FoxbpmRestApplication extends AbstractRestApplication {
	
	public FoxbpmRestApplication() {
		super();
	}
	
	 
	public Restlet createInboundRoot() {
//		initializeAuthentication();
		Router router = new Router(getContext());
		RestServicesInit.attachResources(router);
//		authenticator.setNext(router);
		return router;
	}
}
