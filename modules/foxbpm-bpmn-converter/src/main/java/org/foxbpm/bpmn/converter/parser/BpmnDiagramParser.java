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

import java.util.ArrayList;
import java.util.Iterator;

import org.dom4j.Element;
import org.foxbpm.model.BpmnModel;
import org.foxbpm.model.Graphic;
/**
 * 常量类
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
		Graphic graphic = null;
		String bpmnElement = null;
		for (Iterator iterator = element.elements().iterator(); iterator.hasNext();) {
			elem = (Element) iterator.next();
			name = elem.getName();
			if (ELEMENT_DI_PLANE.equalsIgnoreCase(name) || ELEMENT_DI_SHAPE.equalsIgnoreCase(name)
			        || ELEMENT_DI_EDGE.equalsIgnoreCase(name)) {
				parse((Element) elem, model);
				break;
			}
			//处理dc:Bounds和di:waypoint
			graphic = new Graphic();
			graphic.setExpanded(("true".equalsIgnoreCase(element.attributeValue(ATTRIBUTE_DI_IS_EXPANDED))));
			graphic.setBpmnElement(element.attributeValue(ATTRIBUTE_DI_BPMNELEMENT));
			graphic.setX(Double.valueOf(elem.attributeValue(ATTRIBUTE_DI_X)));
			graphic.setY(Double.valueOf(elem.attributeValue(ATTRIBUTE_DI_Y)));
			
			if (ELEMENT_DI_BOUNDS.equalsIgnoreCase(name)) {
				graphic.setHeight(Double.valueOf(elem.attributeValue(ATTRIBUTE_DI_HEIGHT)));
				graphic.setWidth(Double.valueOf(elem.attributeValue(ATTRIBUTE_DI_WIDTH)));
				model.addBoundsGraphic(graphic.getBpmnElement(), graphic);
			} else if (ELEMENT_DI_WAYPOINT.equalsIgnoreCase(name)) {
				bpmnElement = element.attributeValue(ATTRIBUTE_DI_BPMNELEMENT);
				if (null == model.getWaypointGraphic(bpmnElement)) {
					model.addWaypointGraphic(bpmnElement, new ArrayList<Graphic>());
				}
				model.getWaypointGraphic(bpmnElement).add(graphic);
			}
		}
	}
}
