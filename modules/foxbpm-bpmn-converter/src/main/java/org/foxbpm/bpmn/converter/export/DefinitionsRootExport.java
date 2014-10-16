/* Licensed under the Apache License, Version 2.0 (the "License");
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
 */
package org.foxbpm.bpmn.converter.export;

import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.foxbpm.bpmn.constants.BpmnXMLConstants;
import org.foxbpm.model.BpmnModel;

public class DefinitionsRootExport implements BpmnXMLConstants {
	
	public static void writeRootElement(BpmnModel model, Element parentElement, String encoding) throws Exception {
		/*Element element = DocumentFactory.getInstance().createElement("bpmn2:definitions", "http://www.foxbpm.org");
		element.addNamespace(XSI_PREFIX, "http://www.w3.org/2001/XMLSchema-instance");
		element.addNamespace("bpmn2", "http://www.omg.org/spec/BPMN/20100524/MODEL");
		element.addNamespace(BPMNDC_PREFIX, BPMNDC_NAMESPACE);
		element.addNamespace(BPMNDI_PREFIX, BPMNDI_NAMESPACE);
		element.addNamespace(FOXBPM_PREFIX, FOXBPM_NAMESPACE);
		element.addNamespace(XSD_PREFIX, SCHEMA_NAMESPACE);
		element.addAttribute(XMLNS, "http://www.foxbpm.org");
		element.addAttribute("id", "Definitions_1");
		element.addAttribute(TARGET_NAMESPACE_ATTRIBUTE, "http://www.foxbpm.org");*/
		
		
		//parentElement.add(element);
	}
}