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
 */
package org.foxbpm.engine.impl.bpmn.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.foxbpm.bpmn.converter.BpmnXMLConverter;
import org.foxbpm.engine.event.EventListener;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.ProcessDefinitionEntityBuilder;
import org.foxbpm.engine.impl.bpmn.behavior.ActivityBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.BaseElementBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.BoundaryEventBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.EventBehavior;
import org.foxbpm.engine.impl.connector.ConnectorListener;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.mgmt.DataVariableMgmtDefinition;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.modelparse.ProcessModelParseHandler;
import org.foxbpm.kernel.ProcessDefinitionBuilder;
import org.foxbpm.kernel.behavior.KernelFlowNodeBehavior;
import org.foxbpm.kernel.behavior.KernelSequenceFlowBehavior;
import org.foxbpm.kernel.event.KernelEventType;
import org.foxbpm.kernel.event.KernelListener;
import org.foxbpm.kernel.process.KernelDIBounds;
import org.foxbpm.kernel.process.KernelFlowElementsContainer;
import org.foxbpm.kernel.process.KernelLaneSet;
import org.foxbpm.kernel.process.KernelProcessDefinition;
import org.foxbpm.kernel.process.impl.KernelArtifactImpl;
import org.foxbpm.kernel.process.impl.KernelAssociationImpl;
import org.foxbpm.kernel.process.impl.KernelBaseElementImpl;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelLaneImpl;
import org.foxbpm.kernel.process.impl.KernelSequenceFlowImpl;
import org.foxbpm.model.Activity;
import org.foxbpm.model.BpmnModel;
import org.foxbpm.model.Connector;
import org.foxbpm.model.FlowElement;
import org.foxbpm.model.FlowNode;
import org.foxbpm.model.Process;
import org.foxbpm.model.SequenceFlow;
import org.foxbpm.model.StartEvent;
import org.foxbpm.model.SubProcess;
import org.foxbpm.model.config.style.Style;
import org.foxbpm.model.constant.StyleOption;

public class BpmnParseHandlerImpl implements ProcessModelParseHandler {
	
//	private static Map<Class<?>, Style> styleContainer = new HashMap<Class<?>, Style>();
	public static BehaviorRelationMemo behaviorRelationMemo = new BehaviorRelationMemo();
	public KernelProcessDefinition createProcessDefinition(String processId, Object processFile) {
		BpmnModel bpmnModel = null;
		bpmnModel = loadBpmnModel(processId, (InputStream)processFile);
		if (bpmnModel == null) {
			throw new FoxBPMException("文件中没有对应的流程定义，请检查bpmn文件内容和流程key是否对应！");
		}
		KernelProcessDefinition processDefinition = loadProcess(bpmnModel);
		// 关联保存下来的关系
		behaviorRelationMemo.attachActivityAndBoundaryEventBehaviorRelation();
		// 加载监听器
		this.registListener((ProcessDefinitionEntity) processDefinition);
		return processDefinition;
	}
	
	/**
	 * 
	 * loadBehavior 根据Process模型加载流程定义对象
	 * 
	 * @param process
	 * @return 流程定义对象
	 */
	private KernelProcessDefinition loadProcess(BpmnModel bpmnModel) {
		Process process = bpmnModel.getProcesses().get(0);
		String processObjId = process.getId();
		List<FlowElement> flowElements = process.getFlowElements();
		ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionEntityBuilder(processObjId);
		for (Connector connector : process.getConnector()) {
			ConnectorListener connectorListener = new ConnectorListener();
			connectorListener.setConnector(connector);
			processDefinitionBuilder.executionListener(connector.getEventType(), connectorListener);
		}
		
		for (FlowElement flowElement : flowElements) {
			generateBuilder(processDefinitionBuilder, flowElement, false,process.getSequenceFlows());
		}
		
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) processDefinitionBuilder.buildProcessDefinition();
		
//		if (process.getLaneSets() != null && process.getLaneSets().size() > 0) {
//			for (LaneSet laneSet : process.getLaneSets()) {
//				
//				KernelLaneSetImpl laneSetObj = new KernelLaneSetImpl(laneSet.getId(), processDefinition);
//				laneSetObj.setName(laneSet.getName());
//				loadLane(laneSetObj, laneSet, processDefinition);
//				
//				processDefinition.getLaneSets().add(laneSetObj);
//			}
//		}
		
//		// 加载其他元素
//		for (Artifact artifact : process.getArtifacts()) {
//			KernelArtifactBehavior artifactBehavior = BpmnBehaviorEMFConverter.getArtifactBehavior(artifact, processDefinitionBuilder.getProcessDefinition());
//			KernelArtifactImpl kernelArtifactImpl = new KernelArtifactImpl(artifact.getId(), processDefinition);
//			if (artifact instanceof Association) {
//				kernelArtifactImpl = new KernelAssociationImpl(artifact.getId(), processDefinition);
//			}
//			
//			kernelArtifactImpl.setArtifactBehavior(artifactBehavior);
//			processDefinition.getArtifacts().add(kernelArtifactImpl);
//			
//		}
		
		processDefinition.setKey(process.getId());
		processDefinition.setName(process.getName());
		processDefinition.setCategory(process.getCategory());
		processDefinition.setFormUri(process.getFormUri());
		processDefinition.setFormUriView(process.getFormUriView());
		processDefinition.setSubject(process.getSubject());
		processDefinition.setPotentialStarters(process.getPotentialStarters());
		processDefinition.setProperty("documentation", process.getDocumentation());
		
		DataVariableMgmtDefinition dataVariableMgmtDefinition = new DataVariableMgmtDefinition(processDefinition);
		dataVariableMgmtDefinition.getDataVariableDefinitions().addAll(process.getDataVariables());
		processDefinition.setDataVariableMgmtDefinition(dataVariableMgmtDefinition);
		processDI(processDefinition, process);
		
		return processDefinition;
	}
	
	/**
	 * 根据flowElement构造builder(递归内部子流程)
	 * 
	 * @param processDefinitionBuilder
	 * @param flowElement
	 * @param isSub
	 *            是否子流程
	 */
	private void generateBuilder(ProcessDefinitionBuilder processDefinitionBuilder,
	    FlowElement flowElement, boolean isSub,Map<String,SequenceFlow> sequenceFlows) {
		KernelFlowNodeBehavior flowNodeBehavior = BpmnBehaviorEMFConverter.getFlowNodeBehavior(flowElement, processDefinitionBuilder.getProcessDefinition());
		if (flowElement instanceof FlowNode) {
			processDefinitionBuilder.createFlowNode(flowElement.getId(), flowElement.getName()).behavior(flowNodeBehavior);
			KernelFlowNodeImpl kernelFlowNodeImpl = processDefinitionBuilder.getFlowNode();
			// 特殊处理开始节点，如果是子流程，则放到properties属性中，否则，放到processDefinition的initial属性中
			if (flowElement instanceof StartEvent) {
				if (!isSub) {
					processDefinitionBuilder.initial();
				} else {
					kernelFlowNodeImpl.getParent().setProperty("initial", kernelFlowNodeImpl);
				}
			}
			
			// 特殊处理子流程，需要递归处理节点
			if (flowElement instanceof SubProcess) {
				SubProcess subProcess = (SubProcess) flowElement;
				Iterator<FlowElement> flowElements = subProcess.getFlowElements().iterator();
				while (flowElements.hasNext()) {
					FlowElement tmpFlowElement = flowElements.next();
					generateBuilder(processDefinitionBuilder, tmpFlowElement, true,sequenceFlows);
				}
			}
			// 处理连接器
			if (flowNodeBehavior instanceof BaseElementBehavior) {
				for (Connector connector : flowElement.getConnector()) {
					ConnectorListener connectorListener = new ConnectorListener();
					connectorListener.setConnector(connector);
					processDefinitionBuilder.executionListener(connector.getEventType(), connectorListener);
				}
			}
			
			// 处理线条
			List<String> sequenceFlowIds = ((FlowNode) flowElement).getOutgoingFlows();
			for (String sequenceFlowId : sequenceFlowIds) {
				SequenceFlow tmpElement = sequenceFlows.get(sequenceFlowId);
				KernelSequenceFlowBehavior kernelSequenceFlowBehavior = BpmnBehaviorEMFConverter.getSequenceFlowBehavior(tmpElement, processDefinitionBuilder.getProcessDefinition());
				processDefinitionBuilder.sequenceFlow(tmpElement.getTargetRefId(), tmpElement.getId(), tmpElement.getName(), kernelSequenceFlowBehavior);
			}
			processDefinitionBuilder.endFlowNode();
		}
	}
	
	/**
	 * 加载配置监听器、 独立加载 和嵌入流程定义创建代码中，算法效率是一样的 监听器集合SIZE * 节点集合SIZE
	 * 不建议侵入到流程定义的LOAD代码中
	 * 
	 * @param processEntity
	 */
	private void registListener(ProcessDefinitionEntity processEntity) {
		// 加载监听器
		List<EventListener> eventListenerList = Context.getProcessEngineConfiguration().getEventListenerConfig();
		KernelListener foxbpmEventListener = null;
		try {
			for (EventListener eventListener : eventListenerList) {
				foxbpmEventListener = (KernelListener) Class.forName(eventListener.getListenerClass()).newInstance();
				if (StringUtil.equals(eventListener.getEventType(), KernelEventType.EVENTTYPE_PROCESS_START)
				        || StringUtil.equals(eventListener.getEventType(), KernelEventType.EVENTTYPE_PROCESS_END)) {
					// 注册启动监听
					processEntity.addKernelListener(eventListener.getEventType(), foxbpmEventListener);
				} else {
					if (StringUtil.equals(eventListener.getEventType(), KernelEventType.EVENTTYPE_SEQUENCEFLOW_TAKE)) {
						// 注册线条监听
						Map<String, KernelSequenceFlowImpl> sequenceFlows = processEntity.getSequenceFlows();
						Set<Entry<String, KernelSequenceFlowImpl>> sequenceEntrySet = sequenceFlows.entrySet();
						Iterator<Entry<String, KernelSequenceFlowImpl>> sequenceEntryIter = sequenceEntrySet.iterator();
						Entry<String, KernelSequenceFlowImpl> sequenceFlow = null;
						KernelSequenceFlowImpl kernelSequenceFlowImpl = null;
						while (sequenceEntryIter.hasNext()) {
							sequenceFlow = sequenceEntryIter.next();
							kernelSequenceFlowImpl = sequenceFlow.getValue();
							kernelSequenceFlowImpl.addKernelListener(foxbpmEventListener);
						}
					} else {
						// 注册节点监听
						List<KernelFlowNodeImpl> flowNodes = processEntity.getFlowNodes();
						this.registerFlowNodeListener(flowNodes, eventListener, foxbpmEventListener);
					}
					
				}
				
			}
		} catch (Exception e) {
			throw new FoxBPMException("加载运行轨迹监听器时出现问题", e);
		}
	}
	
	/**
	 * 
	 * 子流程节点加载轨迹监听器
	 * 
	 * @param flowNodes
	 * @param eventListener
	 * @param foxbpmEventListener
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	private void registerFlowNodeListener(List<KernelFlowNodeImpl> flowNodes,
	    EventListener eventListener, KernelListener foxbpmEventListener) {
		for (KernelFlowNodeImpl kernelFlowNodeImpl : flowNodes) {
			kernelFlowNodeImpl.addKernelListener(eventListener.getEventType(), foxbpmEventListener);
			
			List<KernelFlowNodeImpl> subFlowNodes = kernelFlowNodeImpl.getFlowNodes();
			if (subFlowNodes != null && subFlowNodes.size() > 0) {
				registerFlowNodeListener(subFlowNodes, eventListener, foxbpmEventListener);
			}
		}
	}
	
//	private void loadLane(KernelLaneSet kernelLaneSet, LaneSet laneSet,
//	    ProcessDefinitionEntity processDefinition) {
//		kernelLaneSet.setName(laneSet.getName());
//		for (Lane lane : laneSet.getLanes()) {
//			if (lane != null) {
//				
//				KernelLaneImpl KernelLaneImpl = new KernelLaneImpl(lane.getId(), processDefinition);
//				KernelLaneImpl.setName(lane.getName());
//				kernelLaneSet.getLanes().add(KernelLaneImpl);
//				LaneSet childLaneSet = lane.getChildLaneSet();
//				if (childLaneSet != null) {
//					KernelLaneSetImpl KernelLaneSetImpl = new KernelLaneSetImpl(childLaneSet.getId(), processDefinition);
//					KernelLaneSetImpl.setName(childLaneSet.getName());
//					KernelLaneImpl.setChildLaneSet(KernelLaneSetImpl);
//					loadLane(KernelLaneSetImpl, childLaneSet, processDefinition);
//				} else {
//					continue;
//				}
//			}
//			
//		}
		
//	}
	
	private void processDI(ProcessDefinitionEntity processDefinition, Process process) {
//		Definitions definitions = (Definitions) process.eResource().getContents().get(0).eContents().get(0);
//		List<BPMNDiagram> diagrams = definitions.getDiagrams();
//		if (diagrams == null || diagrams.size() == 0) {
//			return;
//		}
//		float maxX = 0;
//		float maxY = 0;
//		float minY = 0;
//		float minX = 0;
//		for (BPMNDiagram bpmnDiagram : diagrams) {
//			for (DiagramElement diagramElement : bpmnDiagram.getPlane().getPlaneElement()) {
//				// 节点信息
//				if (diagramElement instanceof BPMNShape) {
//					BPMNShape bpmnShape = (BPMNShape) diagramElement;
//					Bounds bounds = bpmnShape.getBounds();
//					float x = bounds.getX();
//					float y = bounds.getY();
//					float width = bounds.getWidth();
//					float height = bounds.getHeight();
//					if (x + width > maxX) {
//						maxX = x + width;
//					}
//					if (y + height > maxY) {
//						maxY = y + height;
//					}
//					if (minY == 0) {
//						minY = y;
//					} else {
//						if (y < minY) {
//							minY = y;
//						}
//					}
//					
//					if (minX == 0) {
//						minX = x;
//					} else {
//						if (x < minX) {
//							minX = x;
//						}
//					}
//					this.loadBPMNShape(width, height, x, y, bpmnShape, processDefinition);
//				}
//				// 线条信息
//				if (diagramElement instanceof BPMNEdge) {
//					BPMNEdge bpmnEdge = (BPMNEdge) diagramElement;
//					List<Point> pointList = bpmnEdge.getWaypoint();
//					for (Point point : pointList) {
//						float x = point.getX();
//						float y = point.getY();
//						if (x > maxX) {
//							maxX = x;
//						}
//						if (y > maxY) {
//							maxY = y;
//						}
//					}
//					this.loadBPMNEdge(pointList, bpmnEdge, processDefinition);
//					
//				}
//			}
//		}
//		
//		processDefinition.setProperty("canvas_maxX", maxX + 30);
//		processDefinition.setProperty("canvas_maxY", maxY + 70);
//		processDefinition.setProperty("canvas_minX", minX);
//		processDefinition.setProperty("canvas_minY", minY);
	}
	
	/**
	 * 
	 * loadBPMNShape(加载 bpmnShape信息)
	 * 
	 * @param width
	 * @param height
	 * @param x
	 * @param y
	 * @param bpmnShape
	 * @param processDefinition
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
//	private void loadBPMNShape(float width, float height, float x, float y, BPMNShape bpmnShape,
//	    ProcessDefinitionEntity processDefinition) {
//		ProcessEngineConfigurationImpl processEngineConfiguration = Context.getProcessEngineConfiguration();
//		BaseElement bpmnElement = getBaseElement(bpmnShape.getBpmnElement());
//		if(bpmnElement == null){
//			return;
//		}
//		Style style = this.getStyle(bpmnElement, processEngineConfiguration);
//		KernelDIBounds kernelDIBounds = this.getDIElementFromProcessDefinition(processDefinition, bpmnElement.getId());
//		if (kernelDIBounds != null) {
//			// 图形基本属性
//			kernelDIBounds.setWidth(width);
//			kernelDIBounds.setHeight(height);
//			kernelDIBounds.setX(x);
//			kernelDIBounds.setY(y);
//			// 泳道水平垂直属性
//			if (kernelDIBounds instanceof KernelLaneImpl) {
//				kernelDIBounds.setProperty(StyleOption.IsHorizontal, bpmnShape.isIsHorizontal());
//			}
//			// 内部子流程展开收起属性
//			if (kernelDIBounds instanceof KernelFlowNodeImpl
//			        && ((KernelFlowNodeImpl) kernelDIBounds).getKernelFlowNodeBehavior() instanceof SubProcessBehavior) {
//				kernelDIBounds.setProperty(StyleOption.IsExpanded, bpmnShape.isIsExpanded());
//			}
//			
//			// 图形式样属性
//			this.setStyleProperties((KernelBaseElementImpl) kernelDIBounds, style);
//		}
//	}
//	/**
//	 * 
//	 * loadBPMNEdge(加载bpmnEdge信息)
//	 * 
//	 * @param pointList
//	 * @param bpmnEdge
//	 * @param processDefinition
//	 *            void
//	 * @exception
//	 * @since 1.0.0
//	 */
//	private void loadBPMNEdge(List<Point> pointList, BPMNEdge bpmnEdge,
//	    ProcessDefinitionEntity processDefinition) {
//		ProcessEngineConfigurationImpl processEngineConfiguration = Context.getProcessEngineConfiguration();
//		BaseElement bpmnElement = getBaseElement(bpmnEdge.getBpmnElement());
//		Style style = null;
//		if (bpmnElement instanceof SequenceFlow) {
//			KernelSequenceFlowImpl findSequenceFlow = processDefinition.findSequenceFlow(bpmnElement.getId());
//			style = processEngineConfiguration.getStyle("SequenceFlow");
//			List<Integer> waypoints = new ArrayList<Integer>();
//			for (Point point : pointList) {
//				waypoints.add((new Float(point.getX())).intValue());
//				waypoints.add((new Float(point.getY())).intValue());
//			}
//			
//			findSequenceFlow.setWaypoints(waypoints);
//			if (style != null) {
//				this.setStyleProperties(findSequenceFlow, style);
//			}
//		}
//		if (bpmnElement instanceof Association) {
//			KernelAssociationImpl kernelAssociationImpl = (KernelAssociationImpl) processDefinition.getKernelArtifactById(bpmnElement.getId());
//			style = processEngineConfiguration.getStyle("Association");
//			List<Integer> waypoints = new ArrayList<Integer>();
//			for (Point point : pointList) {
//				waypoints.add((new Float(point.getX())).intValue());
//				waypoints.add((new Float(point.getY())).intValue());
//			}
//			
//			kernelAssociationImpl.setWaypoints(waypoints);
//			if (style != null) {
//				this.setStyleProperties(kernelAssociationImpl, style);
//			}
//		}
//		if (bpmnElement instanceof MessageFlow) {
//			// TODO MESSAGEFLOW
//		}
//	}
	
	/**
	 * 
	 * getDIElementFromProcessDefinition(获取BPMN的DI元素包括所有节点，除了线条之外)
	 * 
	 * @param processDefinition
	 * @param diElementId
	 * @return KernelDIBounds
	 * @exception
	 * @since 1.0.0
	 */
	private KernelDIBounds getDIElementFromProcessDefinition(
	    ProcessDefinitionEntity processDefinition, String diElementId) {
		KernelFlowNodeImpl localFlowNode = processDefinition.getNamedFlowNodes().get(diElementId);
		if (localFlowNode != null) {
			return localFlowNode;
		}
		KernelFlowElementsContainer flowElementsContainer = null;
		KernelFlowNodeImpl nestedFlowNode = null;
		// 返回节点
		for (KernelFlowNodeImpl activity : processDefinition.getFlowNodes()) {
			if (activity instanceof KernelFlowElementsContainer) {
				flowElementsContainer = (KernelFlowElementsContainer) activity;
				nestedFlowNode = (KernelFlowNodeImpl) flowElementsContainer.findFlowNode(diElementId);
				if (nestedFlowNode != null) {
					return nestedFlowNode;
				}
				
			}
			
		}
		
		// 返回泳道
		if (processDefinition.getLaneSets() != null && processDefinition.getLaneSets().size() > 0) {
			KernelLaneImpl lane = null;
			for (KernelLaneSet set : processDefinition.getLaneSets()) {
				lane = (KernelLaneImpl) set.getLaneForId(diElementId);
				if (lane != null) {
					return lane;
				}
			}
		}
		KernelArtifactImpl kernelArtifactImpl = (KernelArtifactImpl) processDefinition.getKernelArtifactById(diElementId);
		// 返回非线条部件
		if (!(kernelArtifactImpl instanceof KernelAssociationImpl)) {
			return kernelArtifactImpl;
		}
		return null;
	}
	
	/**
	 * 
	 * getStyle(获取元素式样)
	 * 
	 * @param bpmnElement
	 * @param processEngineConfiguration
	 * @return Style
	 * @exception
	 * @since 1.0.0
	 */
//	private Style getStyle(BaseElement bpmnElement,
//	    ProcessEngineConfigurationImpl processEngineConfiguration) {
//		Style style = null;
//		if (styleContainer.size() == 0) {
//			styleContainer.put(StartEventImpl.class, processEngineConfiguration.getStyle("StartEvent"));
//			styleContainer.put(EndEventImpl.class, processEngineConfiguration.getStyle("EndEvent"));
//			styleContainer.put(ParallelGatewayImpl.class, processEngineConfiguration.getStyle("ParallelGateway"));
//			styleContainer.put(InclusiveGatewayImpl.class, processEngineConfiguration.getStyle("InclusiveGateway"));
//			styleContainer.put(ExclusiveGatewayImpl.class, processEngineConfiguration.getStyle("ExclusiveGateway"));
//			styleContainer.put(UserTaskImpl.class, processEngineConfiguration.getStyle("UserTask"));
//			styleContainer.put(LaneImpl.class, processEngineConfiguration.getStyle("Lane"));
//			styleContainer.put(TextAnnotationImpl.class, processEngineConfiguration.getStyle("TextAnnotation"));
//			styleContainer.put(GroupImpl.class, processEngineConfiguration.getStyle("Group"));
//			styleContainer.put(SubProcessImpl.class, processEngineConfiguration.getStyle("SubProcess"));
//			styleContainer.put(CallActivityImpl.class, processEngineConfiguration.getStyle("CallActivity"));
//			styleContainer.put(ServiceTaskImpl.class, processEngineConfiguration.getStyle("ServiceTask"));
//			styleContainer.put(TaskImpl.class, processEngineConfiguration.getStyle("Task"));
//			styleContainer.put(ManualTaskImpl.class, processEngineConfiguration.getStyle("ManualTask"));
//			styleContainer.put(ScriptTaskImpl.class, processEngineConfiguration.getStyle("ScriptTask"));
//			styleContainer.put(SendTaskImpl.class, processEngineConfiguration.getStyle("SendTask"));
//			styleContainer.put(ReceiveTaskImpl.class, processEngineConfiguration.getStyle("ReceiveTask"));
//			styleContainer.put(BusinessRuleTaskImpl.class, processEngineConfiguration.getStyle("BusinessRuleTask"));
//			styleContainer.put(BoundaryEventImpl.class, processEngineConfiguration.getStyle("BoundaryEvent"));
//			styleContainer.put(IntermediateCatchEventImpl.class, processEngineConfiguration.getStyle("IntermediateCatchEvent"));
//			styleContainer.put(IntermediateThrowEventImpl.class, processEngineConfiguration.getStyle("IntermediateThrowEvent"));
//		}
//		
//		style = styleContainer.get(bpmnElement.getClass());
//		
//		if (style == null) {
//			throw new FoxBPMException("未找到" + bpmnElement.getClass() + "的style样式");
//		}
//		return style;
//	}
	
	/**
	 * 
	 * setStyleProperties(设置元素式样)
	 * 
	 * @param kernelBaseElementImpl
	 * @param style
	 * @exception
	 * @since 1.0.0
	 */
	private void setStyleProperties(KernelBaseElementImpl kernelBaseElementImpl, Style style) {
		kernelBaseElementImpl.setProperty(StyleOption.Background, style.getBackground());
		kernelBaseElementImpl.setProperty(StyleOption.Font, style.getFont());
		kernelBaseElementImpl.setProperty(StyleOption.Foreground, style.getForeground());
		kernelBaseElementImpl.setProperty(StyleOption.MulitSelectedColor, style.getMulitSelectedColor());
		kernelBaseElementImpl.setProperty(StyleOption.StyleObject, style.getObject());
		kernelBaseElementImpl.setProperty(StyleOption.SelectedColor, style.getSelectedColor());
		kernelBaseElementImpl.setProperty(StyleOption.TextColor, style.getTextColor());
	}
	
	private BpmnModel loadBpmnModel(String processId, InputStream is) {
		BpmnXMLConverter converter = new BpmnXMLConverter();
		SAXReader reader = new SAXReader();
		BpmnModel bpmnModel = null;
		try {
			Document doc = reader.read(is);
			bpmnModel = converter.convertToBpmnModel(doc);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return bpmnModel;
	}
	
	/**
	 * 
	 * 
	 * BehaviorRelationMemo
	 * 
	 * MAENLIANG 2014年8月4日 下午1:51:31
	 * 
	 * @version 1.0.0
	 * 
	 */
	public static class BehaviorRelationMemo {
		/** 临时存储MAP */
		private Map<String, ActivityBehavior> attachActivityMap = new HashMap<String, ActivityBehavior>();
		private Map<String, List<BoundaryEventBehavior>> beAttachedActivityMap = new HashMap<String, List<BoundaryEventBehavior>>();
		
		/**
		 * 
		 * attachActivityAndBoundaryEventBehaviorRelation(创建Activity
		 * 和BoundaryEventBehavior之间的关联关系) void
		 * 
		 * @exception
		 * @since 1.0.0
		 */
		public void attachActivityAndBoundaryEventBehaviorRelation() {
			Set<String> keySet = beAttachedActivityMap.keySet();
			for (String activityID : keySet) {
				if (attachActivityMap.containsKey(activityID)) {
					List<BoundaryEventBehavior> list = beAttachedActivityMap.get(activityID);
					for (EventBehavior behavior : list) {
						attachActivityMap.get(activityID).getBoundaryEventBehaviors().add((BoundaryEventBehavior) behavior);
					}
					
				}
			}
			this.attachActivityMap.clear();
			this.beAttachedActivityMap.clear();
		}
		
		/**
		 * 
		 * addActivity(保存解释得到的活动节点)
		 * 
		 * @param activity
		 * @param activityBehavior
		 *            void
		 * @exception
		 * @since 1.0.0
		 */
		public void addActivity(Activity activity, ActivityBehavior activityBehavior) {
			this.attachActivityMap.put(activity.getId(), activityBehavior);
		}
		/**
		 * 
		 * addActivity(保存解释得到的事件行为)
		 * 
		 * @param activity
		 * @param eventBehavior
		 *            void
		 * @exception
		 * @since 1.0.0
		 */
		public void addBeAttachedActivity(String activityId, BoundaryEventBehavior eventBehavior) {
			List<BoundaryEventBehavior> list = beAttachedActivityMap.get(activityId);
			if (list == null) {
				list = new ArrayList<BoundaryEventBehavior>();
				this.beAttachedActivityMap.put(activityId, list);
			}
			list.add(eventBehavior);
			
		}
	}
	
}
