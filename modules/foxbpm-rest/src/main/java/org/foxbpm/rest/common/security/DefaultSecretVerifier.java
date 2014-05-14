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
package org.foxbpm.rest.common.security;

import org.restlet.security.SecretVerifier;

/**
 * 默认的安全校验器，需要其他校验时可创建新的SecretVerifier子类，并注册到abstractRestApplication中
 * @author ych
 *
 */
public class DefaultSecretVerifier extends SecretVerifier{

	@Override
	public boolean verify(String userName, char[] password) throws IllegalArgumentException {
		System.out.println("UserName is :" + userName);
		String pwd = new String(password);
		System.out.println("password is :" + pwd);
		return true;
	}
}
