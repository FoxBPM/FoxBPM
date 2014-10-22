package org.foxbpm.bpmn.converter;

import java.util.Iterator;
import java.util.List;

import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.foxbpm.bpmn.constants.BpmnXMLConstants;
import org.foxbpm.model.BaseElement;
import org.foxbpm.model.FlowElement;
import org.foxbpm.model.Lane;
import org.foxbpm.model.LaneSet;

public class LaneSetXmlConverter extends FlowElementXMLConverter {

	public FlowElement cretateFlowElement() {
		return new LaneSet();
	}

	public Element cretateXMLElement() {
		return DocumentFactory.getInstance().createElement(BpmnXMLConstants.BPMN2_PREFIX + ':'
		        + BpmnXMLConstants.ELEMENT_LANESET, BpmnXMLConstants.BPMN2_NAMESPACE);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void convertXMLToModel(Element element, BaseElement baseElement) {
		LaneSet laneset = (LaneSet)baseElement;
		Iterator<Element> laneIterator = element.elements().iterator();
		while(laneIterator.hasNext()){
			Element tmp = laneIterator.next();
			Lane lane = parseLane(tmp);
			laneset.getLanes().add(lane);
		}
		super.convertXMLToModel(element, baseElement);
	}
	
	@Override
	public void convertModelToXML(Element element, BaseElement baseElement) {
		
		LaneSet laneSet = (LaneSet)baseElement;
		List<Lane> lanes = laneSet.getLanes();
		if(lanes != null){
			for(Lane tmpLane : lanes){
				Element tmpLaneElement = parseLaneToXml(tmpLane);
				element.add(tmpLaneElement);
			}
		}
		super.convertModelToXML(element, baseElement);
	}

	@Override
	public Class<? extends BaseElement> getBpmnElementType() {
		return LaneSet.class;
	}

	@Override
	public String getXMLElementName() {
		return "laneSet";
	}
	
	protected Element parseLaneToXml(Lane lane){
		Element element = DocumentFactory.getInstance().createElement(BpmnXMLConstants.BPMN2_PREFIX + ':'
		        + BpmnXMLConstants.ELEMENT_CHILDLANESET, BpmnXMLConstants.BPMN2_NAMESPACE);
		element.addAttribute("xsi:type", "bpmn2:tLaneSet");
		LaneSet laneSet= lane.getChildLaneSet();
		if(laneSet != null){
			List<Lane> lanes = laneSet.getLanes();
			if(lanes != null){
				for(Lane tmpLane : lanes){
					Element tmpElement = parseLaneToXml(tmpLane);
					element.add(tmpElement);
				}
			}
		}
		List<String> flowNodeRefs = lane.getFlowElementRefs();
		if(flowNodeRefs != null){
			for(String tmpRef : flowNodeRefs){
				Element elementFlowRef = DocumentFactory.getInstance().createElement(BpmnXMLConstants.BPMN2_PREFIX + ':'
				        + BpmnXMLConstants.ELEMENT_FLOWNODEREF, BpmnXMLConstants.BPMN2_NAMESPACE);
				elementFlowRef.addText(tmpRef);
				element.add(elementFlowRef);
			}
		}
		return element;
	}
	
	@SuppressWarnings("unchecked")
	public Lane parseLane(Element element){
		Lane lane = new Lane();
		lane.setId(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_ID));
		lane.setName(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_NAME));
		
		Element childLaneSet = element.element(BpmnXMLConstants.ELEMENT_CHILDLANESET);
		if(childLaneSet != null){
			LaneSet laneSet = new LaneSet();
			Iterator<Element> elementIterator = childLaneSet.elements(BpmnXMLConstants.ELEMENT_LANE).iterator();
			while(elementIterator.hasNext()){
				Element tmp = elementIterator.next();
				Lane tmpLane = parseLane(tmp);
				laneSet.getLanes().add(tmpLane);
			}
			lane.setChildLaneSet(laneSet);
		}
		Iterator<Element> elementIterator = element.elements(BpmnXMLConstants.ELEMENT_FLOWNODEREF).iterator();
		while(elementIterator.hasNext()){
			//设置元素
			Element tmp = elementIterator.next();
			lane.getFlowElementRefs().add(tmp.getTextTrim());
		}
		
		return lane;
	}

}
