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

import java.util.Iterator;
import java.util.Map.Entry;

import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.foxbpm.bpmn.constants.BpmnXMLConstants;
import org.foxbpm.model.BpmnModel;
import org.foxbpm.model.Graphic;

public class BPMNDIExport extends BpmnExport {
	
	public static void writeBPMNDI(BpmnModel model, Element parentElement) throws Exception {
		
		Element bpmndiagram = DocumentFactory.getInstance().createElement(BpmnXMLConstants.BPMN2_PREFIX + ':'
		        + BpmnXMLConstants.ELEMENT_DI_DIAGRAM);
		bpmndiagram.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, "BPMNDiagram_1");
		bpmndiagram.addAttribute(BpmnXMLConstants.ATTRIBUTE_NAME, "Default Process Diagram");
		Element bpmnplane = DocumentFactory.getInstance().createElement(BpmnXMLConstants.BPMN2_PREFIX + ':'
		        + BpmnXMLConstants.ELEMENT_DI_PLANE);
		bpmnplane.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, "BPMNPlane_1");
		bpmnplane.addAttribute(BpmnXMLConstants.ATTRIBUTE_DI_BPMNELEMENT, model.getProcesses().get(0).getId());
		
		// 生成bpmnshape及子元素
		if (null != model.getBoundsLocationMap()) {
			Entry<String, Graphic> entry = null;
			Element bpmnshape = null;
			Element bounds = null;
			for (Iterator<Entry<String, Graphic>> iterator = model.getBoundsLocationMap().entrySet().iterator(); iterator.hasNext();) {
				entry = iterator.next();
				bpmnshape = DocumentFactory.getInstance().createElement(BpmnXMLConstants.BPMN2_PREFIX + ':'
				        + BpmnXMLConstants.ELEMENT_DI_SHAPE);
				bpmnplane.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, BpmnXMLConstants.ELEMENT_DI_SHAPE + '_'
				        + entry.getKey());
				bpmnplane.addAttribute(BpmnXMLConstants.ATTRIBUTE_NAME, entry.getKey());
				bounds = DocumentFactory.getInstance().createElement(BpmnXMLConstants.DC_PREFIX + ':'
				        + BpmnXMLConstants.ELEMENT_DI_BOUNDS);
				
				bounds.addAttribute(BpmnXMLConstants.ATTRIBUTE_DI_X, String.valueOf(entry.getValue().getX()));
				bounds.addAttribute(BpmnXMLConstants.ATTRIBUTE_DI_Y, String.valueOf(entry.getValue().getY()));
				bounds.addAttribute(BpmnXMLConstants.ATTRIBUTE_DI_WIDTH, String.valueOf(entry.getValue().getY()));
				bounds.addAttribute(BpmnXMLConstants.ATTRIBUTE_DI_HEIGHT, String.valueOf(entry.getValue().getY()));
				
				bpmnshape.add(bounds);
			}
		}
		
		bpmndiagram.add(bpmnplane);
		parentElement.add(bpmndiagram);
	}
}
