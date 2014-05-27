///**
// * Copyright 1996-2014 FoxBPM ORG.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// * 
// *      http://www.apache.org/licenses/LICENSE-2.0
// * 
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// * 
// * @author kenshin
// * @author ych
// */
//package org.foxbpm.engine.impl.cmd;
//
//import java.util.Map;
//
//import org.foxbpm.engine.impl.interceptor.Command;
//import org.foxbpm.engine.impl.interceptor.CommandContext;
//
//public class GetFlowGraphicsElementPositionCmd implements Command<Map<String, Map<String, Object>>> {
//
//	/**
//	 * 流程定义编号
//	 */
//	protected String processDefinitionId;
//	protected String processDefinitionKey;
//	
////	protected DefinitionsBehavior definitions;
//	public GetFlowGraphicsElementPositionCmd(String processDefinitionId,String processDefinitionKey) {
//		this.processDefinitionId = processDefinitionId;
//		this.processDefinitionKey= processDefinitionKey;
//	}
//	
//	@Override
//	public Map<String, Map<String, Object>> execute(CommandContext commandContext) {
////		DeploymentManager deploymentCache = Context.getProcessEngineConfiguration().getDeploymentManager();
////		ProcessDefinition processDefinition = null;
////		if(processDefinitionId!=null)
////			processDefinition = deploymentCache.findDeployedProcessDefinitionById(processDefinitionId);
////		else
////			processDefinition = deploymentCache.findDeployedLatestProcessDefinitionByKey(processDefinitionKey);
////			
////		DefinitionsBehavior definitions = processDefinition.getDefinitions();
////		Map<String, Map<String, Object>> positionInfo=new HashMap<String, Map<String,Object>>();
////		this.definitions=definitions;
////		List<BPMNDiagram> BPMNDiagramList = definitions.getDiagrams();
////		Map<String,List<String>> tmpMap = new HashMap<String,List<String>>();
////		for (BPMNDiagram bpmnDiagram : BPMNDiagramList) {
////			for (DiagramElement diagramElement : bpmnDiagram.getPlane().getPlaneElement()) {
////				if (diagramElement instanceof BPMNShape) {
////					BPMNShape bpmnShape = (BPMNShape) diagramElement;
////					Map<String, Object>  positionMap=new HashMap<String, Object>();
////					BaseElement bpmnElement=getBaseElement(bpmnShape.getBpmnElement());
////					if(bpmnElement==null){
////						continue;
////					}
////					//判断如果是折叠起来的子流程，则将其所有的子元素的坐标替换成父元素的
////					if(bpmnElement instanceof SubProcess){
////						if(bpmnShape.isIsExpanded() == false){
////							List<String> excludeNodeIds = null;
////							excludeNodeIds = getExcluesElement((SubProcess)bpmnElement);
////							tmpMap.put(bpmnElement.getId(), excludeNodeIds);
////						}
////					}
////					float x=bpmnShape.getBounds().getX();
////					float y=bpmnShape.getBounds().getY();
////					float height=bpmnShape.getBounds().getHeight();
////					float width=bpmnShape.getBounds().getWidth();
////					//height,width,x,y
////					positionMap.put("x",x);
////					positionMap.put("y",y);
////					positionMap.put("height",height);
////					positionMap.put("width",width);
////					positionInfo.put(bpmnElement.getId(), positionMap);
////				}
////				if (diagramElement instanceof BPMNEdge) {
////					//BPMNEdge bpmnEdge = (BPMNEdge) diagramElement;
////				}
////				
////			}
////		}
////		//判断如果是折叠起来的子流程，则将其所有的子元素的坐标替换成父元素的
////		for(String key : tmpMap.keySet()){
////			for(String nodeId:tmpMap.get(key)){
////				positionInfo.put(nodeId, positionInfo.get(key));
////			}
////		}
////		return positionInfo;
//		
//		return null;
//	}
//}
