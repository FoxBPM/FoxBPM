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
package org.foxbpm.test;

import java.io.IOException;
import java.io.InputStream;

import org.restlet.data.ChallengeScheme;
import org.restlet.representation.InputRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

public class TestDeploy {
	public static void main(String[] args) {
		ClientResource client = new ClientResource("http://127.0.0.1:8082/model/deployments");
		client.setChallengeResponse(ChallengeScheme.HTTP_BASIC,"111", "111");
		
		InputStream input = TestDeploy.class.getClassLoader().getResourceAsStream("FirstFoxbpm.zip");
		Representation deployInput = new InputRepresentation(input);
		Representation result = client.post(deployInput);
		try {
			result.write(System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
