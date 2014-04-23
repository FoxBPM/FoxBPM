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
package org.foxbpm.engine;

import java.util.Map;

import org.foxbpm.engine.runtime.ProcessInstance;

public interface RuntimeService {

	ProcessInstance startProcessInstanceByKey(String processDefinitionKey);

	ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String bizKey);

	ProcessInstance startProcessInstanceByKey(String processDefinitionKey, Map<String, Object> variables);

	ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String bizKey, Map<String, Object> variables);

	ProcessInstance startProcessInstanceById(String processDefinitionId);

	ProcessInstance startProcessInstanceById(String processDefinitionId, String bizKey);

	ProcessInstance startProcessInstanceById(String processDefinitionId, Map<String, Object> variables);

	ProcessInstance startProcessInstanceById(String processDefinitionId, String bizKey, Map<String, Object> variables);

	ProcessInstance startProcessInstanceByMessage(String messageName);

	ProcessInstance startProcessInstanceByMessage(String messageName, String bizKey);

	ProcessInstance startProcessInstanceByMessage(String messageName, Map<String, Object> processVariables);

	ProcessInstance startProcessInstanceByMessage(String messageName, String bizKey, Map<String, Object> processVariables);

}
