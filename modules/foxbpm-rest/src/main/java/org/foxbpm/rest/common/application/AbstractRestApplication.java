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

import org.foxbpm.rest.common.security.DefaultSecretVerifier;
import org.restlet.Application;
import org.restlet.data.ChallengeScheme;
import org.restlet.security.ChallengeAuthenticator;
import org.restlet.security.Verifier;

/**
 * foxbpm rest服务基础类
 * 继承自此类的application会经过foxbpm的统一管理，包括安全校验，返回值处理等
 * @author ych
 *
 */
public abstract class AbstractRestApplication extends Application {

	protected ChallengeAuthenticator authenticator;
	public AbstractRestApplication(){
		
	}
	
	public void initializeAuthentication() {
		Verifier verifier = new DefaultSecretVerifier();
		authenticator = new ChallengeAuthenticator(null, false, ChallengeScheme.HTTP_BASIC, "Foxbpm Realm");
		authenticator.setVerifier(verifier);
	}
	
}
