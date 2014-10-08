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

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.UniformResource;
import org.restlet.service.ConverterService;

/**
 * rest服务返回值中的日期格式化
 * @author ych
 *
 */
public class FoxbpmConverService extends ConverterService {

	protected static final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	
	public FoxbpmConverService() {
		super();
	}
	
	@SuppressWarnings("rawtypes")
	 
	public Representation toRepresentation(Object source, Variant target, UniformResource resource) {
		
		Representation representation = super.toRepresentation(source, target, resource);
		if(representation instanceof JacksonRepresentation){
			((JacksonRepresentation)representation).getObjectMapper().setDateFormat(df);
		}
		return representation;
	}
}
