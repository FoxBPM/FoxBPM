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
 * @author yangguangftlp
 */
package org.foxbpm.test;

import java.io.BufferedReader;

import org.restlet.data.ChallengeScheme;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

/**
 * @author yangguangftlp
 * @date 2014年7月26日
 */
public class TestBizDataObjectResouce {
	public static void main(String[] args) {
		ClientResource client = new ClientResource("http://localhost:8889/foxbpm-webapps-base/service/bizDataObjects/dataBaseMode/foxbpmDataSource");
		client.setChallengeResponse(ChallengeScheme.HTTP_BASIC, "111", "111");
		Representation result = client.get();
		try {
			BufferedReader br = new BufferedReader(result.getReader());
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
