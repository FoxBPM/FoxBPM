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
package org.foxbpm.bpmn.converter.parser;

import org.foxbpm.bpmn.constants.BpmnXMLConstants;
import org.foxbpm.model.BpmnModel;
import org.w3c.dom.Element;

/**
 * 
 * 常量类
 * 
 * @author yangguangftlp
 * @date 2014年10月15日
 */
public class ProcessParser implements BpmnXMLConstants {
	
	public Process parse(Element element, BpmnModel model) throws Exception {
		Process process = null;
/*		if (StringUtils.isNotEmpty(xtr.getAttributeValue(null, ATTRIBUTE_ID))) {
			String processId = xtr.getAttributeValue(null, ATTRIBUTE_ID);
			process = new Process();
			process.setId(processId);
			BpmnXMLUtil.addXMLLocation(process, xtr);
			process.setName(xtr.getAttributeValue(null, ATTRIBUTE_NAME));
			if (StringUtils.isNotEmpty(xtr.getAttributeValue(null, ATTRIBUTE_PROCESS_EXECUTABLE))) {
				process.setExecutable(Boolean.parseBoolean(xtr.getAttributeValue(null, ATTRIBUTE_PROCESS_EXECUTABLE)));
			}
			String candidateUsersString = xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_PROCESS_CANDIDATE_USERS);
			if (StringUtils.isNotEmpty(candidateUsersString)) {
				List<String> candidateUsers = BpmnXMLUtil.parseDelimitedList(candidateUsersString);
				process.setCandidateStarterUsers(candidateUsers);
			}
			String candidateGroupsString = xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_PROCESS_CANDIDATE_GROUPS);
			if (StringUtils.isNotEmpty(candidateGroupsString)) {
				List<String> candidateGroups = BpmnXMLUtil.parseDelimitedList(candidateGroupsString);
				process.setCandidateStarterGroups(candidateGroups);
			}
			
			BpmnXMLUtil.addCustomAttributes(xtr, process, ProcessExport.defaultProcessAttributes);
			
			model.getProcesses().add(process);
			
		}*/
		return process;
	}
}
