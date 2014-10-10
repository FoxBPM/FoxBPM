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
package org.foxbpm.rest.common.application;

import org.foxbpm.rest.common.RestAuthenticator;
import org.foxbpm.rest.common.security.DefaultSecretVerifier;
import org.restlet.Application;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Status;
import org.restlet.security.ChallengeAuthenticator;
import org.restlet.security.Verifier;

/**
 * foxbpm rest服务基础类 继承自此类的application会经过foxbpm的统一管理，包括安全校验，返回值处理等
 * 
 * @author ych
 */
public abstract class AbstractRestApplication extends Application {

	protected ChallengeAuthenticator authenticator;
	protected FoxbpmStatusService foxbpmStatusService;
	protected FoxbpmConverService foxbpmConverService;
	protected RestAuthenticator restAuthenticator;
	
	protected Verifier verifier;

	public AbstractRestApplication() {
		if (foxbpmStatusService == null) {
			foxbpmStatusService = new FoxbpmStatusService();
			setStatusService(foxbpmStatusService);
			foxbpmConverService = new FoxbpmConverService();
			setConverterService(foxbpmConverService);
		}
	}

	public void initializeAuthentication() {
		if(verifier == null){
			verifier = new DefaultSecretVerifier();
		}
		authenticator = new ChallengeAuthenticator(null, false, ChallengeScheme.HTTP_BASIC, "Foxbpm Realm") {
			protected boolean authenticate(Request request, Response response) {
				if (restAuthenticator != null && !restAuthenticator.requestRequiresAuthentication(request)) {
					return true;
				}
				if (request.getChallengeResponse() == null) {
					response.setStatus(Status.CLIENT_ERROR_UNAUTHORIZED);
					return false;
				} else {
					boolean authenticated = super.authenticate(request, response);
					if (authenticated && restAuthenticator != null) {
						authenticated = restAuthenticator.isRequestAuthorized(request);
					}
					return authenticated;
				}
			}
		};
		authenticator.setVerifier(verifier);
	}

	public String authenticate(Request request, Response response) {
		if (!request.getClientInfo().isAuthenticated()) {
			authenticator.challenge(response, false);
			return null;
		}
		return request.getClientInfo().getUser().getIdentifier();
	}

}
