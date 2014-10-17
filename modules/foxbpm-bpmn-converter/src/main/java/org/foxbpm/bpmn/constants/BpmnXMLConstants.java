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
package org.foxbpm.bpmn.constants;

/**
 * BPMN元素常量
 * 
 * @author yangguangftlp
 * @date 2014年10月15日
 */
public interface BpmnXMLConstants {
	
	String BPMN2_PREFIX = "bpmn2";
	String BPMN2_NAMESPACE = "http://www.omg.org/spec/BPMN/20100524/MODEL";
	String XSI_PREFIX = "xsi";
	String XSI_NAMESPACE = "http://www.w3.org/2001/XMLSchema-instance";
	String XSD_PREFIX = "xsd";
	String XSD_NAMESPACE = "http://www.w3.org/2001/XMLSchema";
	String XMLNS_PREFIX = "xmlns";
	String XMLNS_NAMESPACE = "http://www.foxbpm.org";
	
	String FOXBPM_PREFIX = "http://www.omg.org/spec/BPMN/20100524/DI";
	String FOXBPM_NAMESPACE = "http://www.omg.org/spec/BPMN/20100524/DI";
	String DI_PREFIX = "di";
	String DI_NAMESPACE = "http://www.omg.org/spec/DD/20100524/DI";
	String DC_PREFIX = "dc";
	String DC_NAMESPACE = "http://www.omg.org/spec/DD/20100524/DC";
	String BPMNDI_PREFIX = "bpmndi";
	String BPMNDI_NAMESPACE = "http://www.omg.org/spec/BPMN/20100524/DI";
	
	String TYPE_LANGUAGE_ATTRIBUTE = "typeLanguage";
	String TARGET_NAMESPACE_ATTRIBUTE = "targetNamespace";
	String EXPRESSION_LANGUAGE_ATTRIBUTE = "expressionLanguage";
	String PROCESS_NAMESPACE = "http://www.activiti.org/test";
	
	// 属性
	/** category */
	String ATTRIBUTE_CATEGORY = "category";
	/** subject */
	String ATTRIBUTE_SUBJECT = "subject";
	/** key */
	String ATTRIBUTE_KEY = "key";
	/** id */
	String ATTRIBUTE_ID = "id";
	/** name */
	String ATTRIBUTE_NAME = "name";
	/** type */
	String ATTRIBUTE_TYPE = "type";
	/** default */
	String ATTRIBUTE_DEFAULT = "default";
	/** itemRef */
	String ATTRIBUTE_ITEM_REF = "itemRef";
	/** bizType */
	String ATTRIBUTE_BIZTYPE = "bizType";
	/** dataType */
	String ATTRIBUTE_DATATYPE = "dataType";
	/** fieldName */
	String ATTRIBUTE_FIELDNAME = "fieldName";
	/** resourceType */
	String ATTRIBUTE_RESOURCETYPE = "resourceType";
	/** description */
	String ATTRIBUTE_DESCRIPTION = "description";
	
	// 结束
	
	
	//扩展属性
	String ATTRIBUTE_FOXBPM_CLAIMTYPE = "claimType";
	String ATTRIBUTE_FOXBPM_TASKTYPE = "taskType";
	
	// 节点名称
	/** formUri */
	String ELEMENT_FORMURI = "formUri";
	/** formUriView */
	String ELEMENT_FORMURIVIEW = "formUriView";
	/** taskSubject */
	String ELEMENT_TASKSUBJECT = "taskSubject";
	/** dataVariable */
	String ELEMENT_DATAVARIABLE = "dataVariable";
	/** potentialStarter */
	String ELEMENT_POTENTIALSTARTER = "potentialStarter";
	/** expression */
	String ELEMENT_EXPRESSION = "expression";
	/** definitions */
	String ELEMENT_DEFINITIONS = "definitions";
	/** documentation */
	String ELEMENT_DOCUMENTATION = "documentation";
	/** process */
	String ELEMENT_PROCESS = "process";
	/** transaction */
	String ELEMENT_TRANSACTION = "transaction";
	/** extensionElements */
	String ELEMENT_EXTENSIONS = "extensionElements";
	/** subProcess */
	String ELEMENT_SUBPROCESS = "subProcess";
	/** potentialStarter */
	String ELEMENT_POTENTIAL_STARTER = "potentialStarter";
	/** userTask */
	String ELEMENT_TASK_USER = "userTask";
	
	String ELEMENT_CONNECTORINSTANCEELEMENTS = "connectorInstanceElements";
	// 结束
	
	/** isExecutable */
	String ATTRIBUTE_PROCESS_EXECUTABLE = "isExecutable";
	/** candidateStarterUsers */
	String ATTRIBUTE_PROCESS_CANDIDATE_USERS = "candidateStarterUsers";
	/** candidateStarterGroups */
	String ATTRIBUTE_PROCESS_CANDIDATE_GROUPS = "candidateStarterGroups";
	/** triggeredByEvent */
	String ATTRIBUTE_TRIGGERED_BY = "triggeredByEvent";
	
	/**************************** BPMNDiagram节点 *****************************************/
	/** BPMNDiagram */
	String ELEMENT_DI_DIAGRAM = "BPMNDiagram";
	/** BPMNPlane */
	String ELEMENT_DI_PLANE = "BPMNPlane";
	/** BPMNShape */
	String ELEMENT_DI_SHAPE = "BPMNShape";
	/** BPMNEdge */
	String ELEMENT_DI_EDGE = "BPMNEdge";
	/** BPMNLabel */
	String ELEMENT_DI_LABEL = "BPMNLabel";
	/** Bounds */
	String ELEMENT_DI_BOUNDS = "Bounds";
	/** waypoint */
	String ELEMENT_DI_WAYPOINT = "waypoint";
	/** bpmnElement */
	String ATTRIBUTE_DI_BPMNELEMENT = "bpmnElement";
	/** isExpanded */
	String ATTRIBUTE_DI_IS_EXPANDED = "isExpanded";
	/** width */
	String ATTRIBUTE_DI_WIDTH = "width";
	/** height */
	String ATTRIBUTE_DI_HEIGHT = "height";
	/** x */
	String ATTRIBUTE_DI_X = "x";
	/** y */
	String ATTRIBUTE_DI_Y = "y";
	
}
