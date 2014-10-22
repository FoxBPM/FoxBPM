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

import java.util.Iterator;

import org.dom4j.Element;
import org.foxbpm.bpmn.converter.util.BpmnXMLUtil;
import org.foxbpm.model.Bounds;
import org.foxbpm.model.BpmnModel;
import org.foxbpm.model.WayPoint;
import org.springframework.util.StringUtils;
/**
 * 图位置信息解析处理类
 * 
 * @author yangguangftlp
 * @date 2014年10月15日
 */
@SuppressWarnings("rawtypes")
public class BpmnDiagramParser extends BpmnParser {
	
	@Override
	public void parse(Element element, BpmnModel model) throws Exception {
		
		Element elem = null;
		String name = null;
		Bounds bounds = null;
		WayPoint wayPoint = null;
		String bpmnElement = null;
		for (Iterator iterator = element.elements().iterator(); iterator.hasNext();) {
			elem = (Element) iterator.next();
			name = elem.getName();
			if (ELEMENT_DI_PLANE.equalsIgnoreCase(name) || ELEMENT_DI_SHAPE.equalsIgnoreCase(name)
			        || ELEMENT_DI_EDGE.equalsIgnoreCase(name)) {
				parse((Element) elem, model);
				continue;
			}
			bpmnElement = element.attributeValue(ATTRIBUTE_DI_BPMNELEMENT);
			if (!StringUtils.isEmpty(bpmnElement)) {
				// 处理dc:Bounds和di:waypoint
				if (ELEMENT_DI_BOUNDS.equalsIgnoreCase(name)) {
					bounds = new Bounds();
					bounds.setMarkerVisible(BpmnXMLUtil.parseBoolean(element.attributeValue(ATTRIBUTE_DI_IS_MARKERVISIBLE)));
					bounds.setExpanded(BpmnXMLUtil.parseBoolean(element.attributeValue(ATTRIBUTE_DI_IS_EXPANDED)));
					bounds.setHorizontal(BpmnXMLUtil.parseBoolean(element.attributeValue(ATTRIBUTE_DI_IS_HORIZONTAL)));
					bounds.setBpmnElement(element.attributeValue(ATTRIBUTE_DI_BPMNELEMENT));
					bounds.setX(Float.valueOf(elem.attributeValue(ATTRIBUTE_DI_X)));
					bounds.setY(Float.valueOf(elem.attributeValue(ATTRIBUTE_DI_Y)));
					bounds.setHeight(Float.valueOf(elem.attributeValue(ATTRIBUTE_DI_HEIGHT)));
					bounds.setWidth(Float.valueOf(elem.attributeValue(ATTRIBUTE_DI_WIDTH)));
					model.addBounds(element.getParent().attributeValue(ATTRIBUTE_DI_BPMNELEMENT), bpmnElement, bounds);
				} else if (ELEMENT_DI_WAYPOINT.equalsIgnoreCase(name)) {
					wayPoint = new WayPoint();
					wayPoint.setX(Double.valueOf(elem.attributeValue(ATTRIBUTE_DI_X)));
					wayPoint.setY(Double.valueOf(elem.attributeValue(ATTRIBUTE_DI_Y)));
					model.addWaypoint(element.getParent().attributeValue(ATTRIBUTE_DI_BPMNELEMENT), bpmnElement, wayPoint);
				}
			}
		}
	}
}
