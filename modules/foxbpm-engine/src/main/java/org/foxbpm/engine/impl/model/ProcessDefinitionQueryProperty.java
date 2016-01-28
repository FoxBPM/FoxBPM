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
 * @author kenshin
 * @author ych
 */
package org.foxbpm.engine.impl.model;
import java.util.HashMap;
import java.util.Map;

import org.foxbpm.engine.query.QueryProperty;

/**
 * 
 * @author kenshin
 */
public class ProcessDefinitionQueryProperty implements QueryProperty {
  
	private static final Map<String, ProcessDefinitionQueryProperty> properties = new HashMap<String, ProcessDefinitionQueryProperty>();

	public static final ProcessDefinitionQueryProperty PROCESS_DEFINITION_KEY = new ProcessDefinitionQueryProperty("RES.PROCESS_KEY"); // PD to RES 别名修正 ThinkGem 2016-1-23
	public static final ProcessDefinitionQueryProperty PROCESS_DEFINITION_CATEGORY = new ProcessDefinitionQueryProperty("RES.CATEGORY");
	public static final ProcessDefinitionQueryProperty PROCESS_DEFINITION_ID = new ProcessDefinitionQueryProperty("RES.PROCESS_ID");
	public static final ProcessDefinitionQueryProperty PROCESS_DEFINITION_VERSION = new ProcessDefinitionQueryProperty("RES.VERSION");
	public static final ProcessDefinitionQueryProperty PROCESS_DEFINITION_NAME = new ProcessDefinitionQueryProperty("RES.PROCESS_NAME");
	public static final ProcessDefinitionQueryProperty DEPLOYMENT_ID = new ProcessDefinitionQueryProperty("RES.DEPLOYMENT_ID");
	public static final ProcessDefinitionQueryProperty DEPLOYMENT_TIME = new ProcessDefinitionQueryProperty("RES.DEPLOY_TIME");// RES是fixflow_def_deployment表简称
	
	private String name;

	public ProcessDefinitionQueryProperty(String name) {
		this.name = name;
		properties.put(name, this);
	}

	public String getName() {
		return name;
	}

	public static ProcessDefinitionQueryProperty findByName(String propertyName) {
		return properties.get(propertyName);
	}

}
