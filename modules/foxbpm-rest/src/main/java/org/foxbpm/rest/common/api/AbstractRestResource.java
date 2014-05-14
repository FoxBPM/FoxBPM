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
package org.foxbpm.rest.common.api;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.restlet.data.Form;
import org.restlet.resource.ServerResource;

/**
 * foxbpm rest资源基础实现，包含基础方法
 * @author ych
 *
 */
public abstract class AbstractRestResource extends ServerResource {

	protected String getQueryParameter(String name, Form query) {
		return query.getFirstValue(name);
	}

	protected String getAttribute(String name) {
		return decode((String) getRequest().getAttributes().get(name));
	}

	protected String decode(String string) {
		if (string != null) {
			try {
				return URLDecoder.decode(string, "UTF-8");
			} catch (UnsupportedEncodingException uee) {
				throw new IllegalStateException("JVM does not support UTF-8 encoding.", uee);
			}
		}
		return null;
	}
}
