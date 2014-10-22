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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Element;
import org.foxbpm.bpmn.constants.BpmnXMLConstants;
import org.foxbpm.bpmn.converter.util.UniqueIDUtil;
import org.foxbpm.model.Bounds;
import org.foxbpm.model.BpmnModel;
import org.foxbpm.model.SequenceFlow;
import org.foxbpm.model.WayPoint;

public class BPMNDIExport extends BpmnExport {
	
	public static void writeBPMNDI(BpmnModel model, Element parentElement) throws Exception {
		
		Element bpmndiagram = null;
		Element bpmnplane = null;
		// 生成bpmnshape及子元素
		if (null != model.getBoundsLocationMap()) {
			Entry<String, Map<String, Bounds>> entry = null;
			Entry<String, Bounds> entryBounds = null;
			SequenceFlow SequenceFlow = null;
			Element bpmnShape = null;
			Element elBounds = null;
			Entry<String, List<WayPoint>> entryList = null;
			WayPoint entryWapoint = null;
			Element bpmnEdge = null;
			Element elWapoint = null;
			Map<String, SequenceFlow> sequenceFlowMap = null;
			// 初始化所有线条
			sequenceFlowMap = model.findAllSequenceFlows();
			for (Iterator<Entry<String, Map<String, Bounds>>> iterator = model.getBoundsLocationMap().entrySet().iterator(); iterator.hasNext();) {
				entry = iterator.next();
				bpmndiagram = parentElement.addElement(BpmnXMLConstants.BPMNDI_PREFIX + ':'
				        + BpmnXMLConstants.ELEMENT_DI_DIAGRAM);
				bpmndiagram.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, UniqueIDUtil.getInstance().generateElementID("BPMNDiagram"));
				bpmndiagram.addAttribute(BpmnXMLConstants.ATTRIBUTE_NAME, "Default Process Diagram");
				
				bpmnplane = bpmndiagram.addElement(BpmnXMLConstants.BPMNDI_PREFIX + ':'
				        + BpmnXMLConstants.ELEMENT_DI_PLANE);
				bpmnplane.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, UniqueIDUtil.getInstance().generateElementID("BPMNPlane"));
				bpmnplane.addAttribute(BpmnXMLConstants.ATTRIBUTE_DI_BPMNELEMENT, entry.getKey());
				
				// 生成bpmnshape
				for (Iterator<Entry<String, Bounds>> iteratorBounds = entry.getValue().entrySet().iterator(); iteratorBounds.hasNext();) {
					entryBounds = iteratorBounds.next();
					bpmnShape = bpmnplane.addElement(BpmnXMLConstants.BPMNDI_PREFIX + ':'
					        + BpmnXMLConstants.ELEMENT_DI_SHAPE);
					bpmnShape.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, BpmnXMLConstants.ELEMENT_DI_SHAPE + '_'
					        + entryBounds.getKey());
					bpmnShape.addAttribute(BpmnXMLConstants.ATTRIBUTE_DI_BPMNELEMENT, entryBounds.getKey());
					
					elBounds = bpmnShape.addElement(BpmnXMLConstants.DC_PREFIX + ':'
					        + BpmnXMLConstants.ELEMENT_DI_BOUNDS);
					elBounds.addAttribute(BpmnXMLConstants.ATTRIBUTE_DI_X, String.valueOf(entryBounds.getValue().getX()));
					elBounds.addAttribute(BpmnXMLConstants.ATTRIBUTE_DI_Y, String.valueOf(entryBounds.getValue().getY()));
					elBounds.addAttribute(BpmnXMLConstants.ATTRIBUTE_DI_WIDTH, String.valueOf(entryBounds.getValue().getWidth()));
					elBounds.addAttribute(BpmnXMLConstants.ATTRIBUTE_DI_HEIGHT, String.valueOf(entryBounds.getValue().getHeight()));
					if (entryBounds.getValue().isMarkerVisible()) {
						bpmnShape.addAttribute(BpmnXMLConstants.ATTRIBUTE_DI_IS_MARKERVISIBLE, String.valueOf(true));
					}
					if (entryBounds.getValue().isExpanded()) {
						bpmnShape.addAttribute(BpmnXMLConstants.ATTRIBUTE_DI_IS_EXPANDED, String.valueOf(true));
					}
					if (entryBounds.getValue().isHorizontal()) {
						bpmnShape.addAttribute(BpmnXMLConstants.ATTRIBUTE_DI_IS_HORIZONTAL, String.valueOf(true));
					}
				}
				
				// 处理
				if (null != model.getWaypointLocationMap().get(entry.getKey())) {
					for (Iterator<Entry<String, List<WayPoint>>> iteratorEntry = model.getWaypointLocationMap().get(entry.getKey()).entrySet().iterator(); iteratorEntry.hasNext();) {
						entryList = iteratorEntry.next();
						bpmnEdge = bpmnplane.addElement(BpmnXMLConstants.BPMNDI_PREFIX + ':'
						        + BpmnXMLConstants.ELEMENT_DI_EDGE);
						bpmnEdge.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, "BPMNPlane_" + entryList.getKey());
						bpmnEdge.addAttribute(BpmnXMLConstants.ATTRIBUTE_DI_BPMNELEMENT, entryList.getKey());
						SequenceFlow = sequenceFlowMap.get(entryList.getKey());
						if (null == SequenceFlow) {
							// 异常
						}
						bpmnEdge.addAttribute(BpmnXMLConstants.ATTRIBUTE_SOURCEELEMENT, BpmnXMLConstants.ELEMENT_DI_SHAPE
						        + '_' + SequenceFlow.getSourceRefId());
						bpmnEdge.addAttribute(BpmnXMLConstants.ATTRIBUTE_TARGETELEMENT, BpmnXMLConstants.ELEMENT_DI_SHAPE
						        + '_' + SequenceFlow.getTargetRefId());
						
						for (Iterator<WayPoint> iteratorEntryWayPoint = entryList.getValue().iterator(); iteratorEntryWayPoint.hasNext();) {
							entryWapoint = iteratorEntryWayPoint.next();
							
							elWapoint = bpmnEdge.addElement(BpmnXMLConstants.DI_PREFIX + ':'
							        + BpmnXMLConstants.ELEMENT_DI_WAYPOINT);
							elWapoint.addAttribute(BpmnXMLConstants.ATTRIBUTE_DI_X, String.valueOf(entryWapoint.getX()));
							elWapoint.addAttribute(BpmnXMLConstants.ATTRIBUTE_DI_Y, String.valueOf(entryWapoint.getY()));
							elWapoint.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.DC_PREFIX
							        + ':' + BpmnXMLConstants.TYPE_POINT);
							elWapoint.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.DC_PREFIX
							        + ':' + BpmnXMLConstants.TYPE_POINT);
						}
					}
				}
			}
		}
	}
}
