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
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.bpmn2.Artifact;
import org.eclipse.bpmn2.Association;
import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.BusinessRuleTask;
import org.eclipse.bpmn2.CallActivity;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.Group;
import org.eclipse.bpmn2.InclusiveGateway;
import org.eclipse.bpmn2.Lane;
import org.eclipse.bpmn2.LaneSet;
import org.eclipse.bpmn2.ManualTask;
import org.eclipse.bpmn2.MessageFlow;
import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.ReceiveTask;
import org.eclipse.bpmn2.RootElement;
import org.eclipse.bpmn2.SendTask;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.ServiceTask;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.bpmn2.SubProcess;
import org.eclipse.bpmn2.Task;
import org.eclipse.bpmn2.TextAnnotation;
import org.eclipse.bpmn2.UserTask;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.di.BPMNEdge;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.di.BpmnDiPackage;
import org.eclipse.bpmn2.util.Bpmn2ResourceFactoryImpl;
import org.eclipse.dd.dc.Bounds;
import org.eclipse.dd.dc.DcPackage;
import org.eclipse.dd.dc.Point;
import org.eclipse.dd.di.DiPackage;
import org.eclipse.dd.di.DiagramElement;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.BasicEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.exception.ExceptionCode;
import org.foxbpm.engine.exception.FoxBPMClassLoadingException;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.ProcessDefinitionEntityBuilder;
import org.foxbpm.engine.impl.ProcessEngineConfigurationImpl;
import org.foxbpm.engine.impl.bpmn.behavior.BaseElementBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.ProcessBehavior;
import org.foxbpm.engine.impl.bpmn.parser.model.BpmnParser;
import org.foxbpm.engine.impl.connector.Connector;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.mgmt.DataVariableMgmtDefinition;
import org.foxbpm.engine.impl.util.BpmnModelUtil;
import org.foxbpm.engine.impl.util.ReflectUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.modelparse.ProcessModelParseHandler;
import org.foxbpm.kernel.ProcessDefinitionBuilder;
import org.foxbpm.kernel.behavior.KernelArtifactBehavior;
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
import org.foxbpm.kernel.process.impl.KernelLaneSetImpl;
import org.foxbpm.kernel.process.impl.KernelSequenceFlowImpl;
import org.foxbpm.model.bpmn.foxbpm.FoxBPMPackage;
import org.foxbpm.model.config.foxbpmconfig.EventListener;
import org.foxbpm.model.config.foxbpmconfig.EventListenerConfig;
import org.foxbpm.model.config.style.Style;

public class BpmnParseHandlerImpl implements ProcessModelParseHandler {

	public KernelProcessDefinition createProcessDefinition(String processId, Object processFile) {
		Process process = null;
		if (processFile != null) {
			process = createProcess(processId, (InputStream) processFile);
		}
		if (process == null) {
			throw new FoxBPMException("文件中没有对应的流程定义，请检查bpmn文件内容和流程key是否对应！");
		}
		KernelProcessDefinition processDefinition = loadBehavior(process);
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
	private KernelProcessDefinition loadBehavior(Process process) {
		String processObjId = BpmnModelUtil.getProcessId(process);
		List<FlowElement> flowElements = process.getFlowElements();
		ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionEntityBuilder(processObjId);
		ProcessBehavior processBehavior = BpmnBehaviorEMFConverter.getProcessBehavior(process, processDefinitionBuilder.getProcessDefinition());
		if (processBehavior != null) {
			for (Connector connector : processBehavior.getConnectors()) {
				processDefinitionBuilder.executionListener(connector.getEventType(), connector);
			}
		}

		for (FlowElement flowElement : flowElements) {
			generateBuilder(processDefinitionBuilder,flowElement,false);
		}

		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) processDefinitionBuilder.buildProcessDefinition();

		if (process.getLaneSets() != null && process.getLaneSets().size() > 0) {
			for (LaneSet laneSet : process.getLaneSets()) {

				KernelLaneSetImpl laneSetObj = new KernelLaneSetImpl(laneSet.getId(), processDefinition);
				laneSetObj.setName(laneSet.getName());
				loadLane(laneSetObj, laneSet, processDefinition);

				processDefinition.getLaneSets().add(laneSetObj);
			}
		}

		// 加载其他元素
		for (Artifact artifact : process.getArtifacts()) {
			KernelArtifactBehavior artifactBehavior = BpmnBehaviorEMFConverter.getArtifactBehavior(artifact, processDefinitionBuilder.getProcessDefinition());
			KernelArtifactImpl kernelArtifactImpl = new KernelArtifactImpl(artifact.getId(), processDefinition);
			if (artifact instanceof Association) {
				kernelArtifactImpl = new KernelAssociationImpl(artifact.getId(), processDefinition);
			}

			kernelArtifactImpl.setArtifactBehavior(artifactBehavior);
			processDefinition.getArtifacts().add(kernelArtifactImpl);

		}

		processDefinition.setKey(processBehavior.getId());
		processDefinition.setName(processBehavior.getName());
		processDefinition.setCategory(processBehavior.getCategory());
		processDefinition.setFormUri(processBehavior.getFormUri());
		processDefinition.setFormUriView(processBehavior.getFormUriView());
		processDefinition.setSubject(processBehavior.getSubject());

		DataVariableMgmtDefinition dataVariableMgmtDefinition = new DataVariableMgmtDefinition(processDefinition);
		dataVariableMgmtDefinition.getDataVariableDefinitions().addAll(processBehavior.getDataVariableDefinitions());
		processDefinition.setDataVariableMgmtDefinition(dataVariableMgmtDefinition);

		processDI(processDefinition, process);

		return processDefinition;
	}
	
	/**
	 * 根据flowElement构造builder(递归内部子流程)
	 * @param processDefinitionBuilder
	 * @param flowElement
	 * @param isSub 是否子流程
	 */
	private void generateBuilder(ProcessDefinitionBuilder processDefinitionBuilder ,FlowElement flowElement, boolean isSub){
		KernelFlowNodeBehavior flowNodeBehavior = BpmnBehaviorEMFConverter.getFlowNodeBehavior(flowElement, processDefinitionBuilder.getProcessDefinition());
		if (flowElement instanceof FlowNode) {
			processDefinitionBuilder.createFlowNode(flowElement.getId(), flowElement.getName()).behavior(flowNodeBehavior);
			KernelFlowNodeImpl kernelFlowNodeImpl = processDefinitionBuilder.getFlowNode();
			//特殊处理开始节点，如果是子流程，则放到properties属性中，否则，放到processDefinition的initial属性中
			if (flowElement instanceof StartEvent) {
				if(!isSub){
					processDefinitionBuilder.initial();
				}else{
					kernelFlowNodeImpl.getParent().setProperty(BpmnParser.INITIAL, kernelFlowNodeImpl);
				}
			}
			
			// 特殊处理子流程，需要递归处理节点
			if(flowElement instanceof SubProcess){
				SubProcess subProcess = (SubProcess)flowElement;
				Iterator<FlowElement> flowElements = subProcess.getFlowElements().iterator();
				while(flowElements.hasNext()){
					FlowElement tmpFlowElement = flowElements.next();
					generateBuilder(processDefinitionBuilder,tmpFlowElement,true);
				}
			}
			
			//处理连接器
			if (flowNodeBehavior instanceof BaseElementBehavior) {
				for (Connector connector : ((BaseElementBehavior) flowNodeBehavior).getConnectors()) {
					processDefinitionBuilder.executionListener(connector.getEventType(), connector);
				}
			}
			
			//处理线条
			List<SequenceFlow> sequenceFlows = ((FlowNode) flowElement).getOutgoing();
			for (SequenceFlow sequenceFlow : sequenceFlows) {
				KernelSequenceFlowBehavior kernelSequenceFlowBehavior = BpmnBehaviorEMFConverter.getSequenceFlowBehavior(sequenceFlow, processDefinitionBuilder.getProcessDefinition());
				processDefinitionBuilder.sequenceFlow(sequenceFlow.getTargetRef().getId(), sequenceFlow.getId(), sequenceFlow.getName(),kernelSequenceFlowBehavior);
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
		EventListenerConfig eventListenerConfig = Context.getProcessEngineConfiguration().getFoxBpmConfig().getEventListenerConfig();
		if (eventListenerConfig != null) {
			// 加载监听器
			List<EventListener> eventListenerList = eventListenerConfig.getEventListener();
			KernelListener foxbpmEventListener = null;
			try {
				for (EventListener eventListener : eventListenerList) {
					foxbpmEventListener = (KernelListener) Class.forName(eventListener.getListenerClass()).newInstance();
					if (StringUtil.equals(eventListener.getEventType(), KernelEventType.EVENTTYPE_PROCESS_START) || StringUtil.equals(eventListener.getEventType(), KernelEventType.EVENTTYPE_PROCESS_END)) {
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
							for (KernelFlowNodeImpl kernelFlowNodeImpl : flowNodes) {
								kernelFlowNodeImpl.addKernelListener(eventListener.getEventType(), foxbpmEventListener);
							}
						}

					}

				}
			} catch (Exception e) {
				throw new FoxBPMException("加载运行轨迹监听器时出现问题", e);
			}
		}

	}

	private void loadLane(KernelLaneSet kernelLaneSet, LaneSet laneSet, ProcessDefinitionEntity processDefinition) {
		kernelLaneSet.setName(laneSet.getName());
		for (Lane lane : laneSet.getLanes()) {
			if (lane != null) {

				KernelLaneImpl KernelLaneImpl = new KernelLaneImpl(lane.getId(), processDefinition);
				KernelLaneImpl.setName(lane.getName());
				kernelLaneSet.getLanes().add(KernelLaneImpl);
				LaneSet childLaneSet = lane.getChildLaneSet();
				if (childLaneSet != null) {
					KernelLaneSetImpl KernelLaneSetImpl = new KernelLaneSetImpl(childLaneSet.getId(), processDefinition);
					KernelLaneSetImpl.setName(childLaneSet.getName());
					KernelLaneImpl.setChildLaneSet(KernelLaneSetImpl);
					loadLane(KernelLaneSetImpl, childLaneSet, processDefinition);
				} else {
					continue;
				}
			}

		}

	}

	private void processDI(ProcessDefinitionEntity processDefinition, Process process) {
		Definitions definitions = (Definitions) process.eResource().getContents().get(0).eContents().get(0);
		List<BPMNDiagram> diagrams = definitions.getDiagrams();
		if (diagrams == null || diagrams.size() == 0) {
			return;
		}
		float maxX = 0;
		float maxY = 0;
		float minY = 0;
		float minX = 0;
		for (BPMNDiagram bpmnDiagram : diagrams) {
			for (DiagramElement diagramElement : bpmnDiagram.getPlane().getPlaneElement()) {
				// 节点信息
				if (diagramElement instanceof BPMNShape) {
					BPMNShape bpmnShape = (BPMNShape) diagramElement;
					Bounds bounds = bpmnShape.getBounds();
					float x = bounds.getX();
					float y = bounds.getY();
					float width = bounds.getWidth();
					float height = bounds.getHeight();
					if (x + width > maxX) {
						maxX = x + width;
					}
					if (y + height > maxY) {
						maxY = y + height;
					}
					if (minY == 0) {
						minY = y;
					} else {
						if (y < minY) {
							minY = y;
						}
					}

					if (minX == 0) {
						minX = x;
					} else {
						if (x < minX) {
							minX = x;
						}
					}
					this.loadBPMNShape(width, height, x, y, bpmnShape, processDefinition);
				}
				// 线条信息
				if (diagramElement instanceof BPMNEdge) {
					BPMNEdge bpmnEdge = (BPMNEdge) diagramElement;
					List<Point> pointList = bpmnEdge.getWaypoint();
					for (Point point : pointList) {
						float x = point.getX();
						float y = point.getY();
						if (x > maxX) {
							maxX = x;
						}
						if (y > maxY) {
							maxY = y;
						}
					}
					this.loadBPMNEdge(pointList, bpmnEdge, processDefinition);

				}
			}
		}

		processDefinition.setProperty("canvas_maxX", maxX + 30);
		processDefinition.setProperty("canvas_maxY", maxY + 70);
		processDefinition.setProperty("canvas_minX", minX);
		processDefinition.setProperty("canvas_minY", minY);
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
	private void loadBPMNShape(float width, float height, float x, float y, BPMNShape bpmnShape, ProcessDefinitionEntity processDefinition) {
		ProcessEngineConfigurationImpl processEngineConfiguration = Context.getProcessEngineConfiguration();
		BaseElement bpmnElement = getBaseElement(bpmnShape.getBpmnElement());
		Style style = this.getStyle(bpmnElement, processEngineConfiguration);
		KernelDIBounds kernelDIBounds = this.getDIElementFromProcessDefinition(processDefinition, bpmnElement.getId());
		if (kernelDIBounds != null) {
			// 图形基本属性
			kernelDIBounds.setWidth(width);
			kernelDIBounds.setHeight(height);
			kernelDIBounds.setX(x);
			kernelDIBounds.setY(y);
			// 泳道水平垂直属性
			if (kernelDIBounds instanceof KernelLaneImpl) {
				kernelDIBounds.setProperty(StyleOption.IsHorizontal, bpmnShape.isIsHorizontal());
			}
			// 图形式样属性
			this.setStyleProperties((KernelBaseElementImpl) kernelDIBounds, style);
		}
	}

	/**
	 * 
	 * loadBPMNEdge(加载bpmnEdge信息)
	 * 
	 * @param pointList
	 * @param bpmnEdge
	 * @param processDefinition
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	private void loadBPMNEdge(List<Point> pointList, BPMNEdge bpmnEdge, ProcessDefinitionEntity processDefinition) {
		ProcessEngineConfigurationImpl processEngineConfiguration = Context.getProcessEngineConfiguration();
		BaseElement bpmnElement = getBaseElement(bpmnEdge.getBpmnElement());
		Style style = null;
		if (bpmnElement instanceof SequenceFlow) {
			KernelSequenceFlowImpl findSequenceFlow = processDefinition.findSequenceFlow(bpmnElement.getId());
			style = processEngineConfiguration.getStyle("SequenceFlow");
			List<Integer> waypoints = new ArrayList<Integer>();
			for (Point point : pointList) {
				waypoints.add((new Float(point.getX())).intValue());
				waypoints.add((new Float(point.getY())).intValue());
			}

			findSequenceFlow.setWaypoints(waypoints);
			if (style != null) {
				this.setStyleProperties(findSequenceFlow, style);
			}
		}
		if (bpmnElement instanceof Association) {
			KernelAssociationImpl kernelAssociationImpl = (KernelAssociationImpl) processDefinition.getKernelArtifactById(bpmnElement.getId());
			style = processEngineConfiguration.getStyle("Association");
			List<Integer> waypoints = new ArrayList<Integer>();
			for (Point point : pointList) {
				waypoints.add((new Float(point.getX())).intValue());
				waypoints.add((new Float(point.getY())).intValue());
			}

			kernelAssociationImpl.setWaypoints(waypoints);
			if (style != null) {
				this.setStyleProperties(kernelAssociationImpl, style);
			}
		}
		if (bpmnElement instanceof MessageFlow) {
			// TODO MESSAGEFLOW
		}
	}

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
	private KernelDIBounds getDIElementFromProcessDefinition(ProcessDefinitionEntity processDefinition, String diElementId) {
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
	private Style getStyle(BaseElement bpmnElement, ProcessEngineConfigurationImpl processEngineConfiguration) {
		Style style = null;
		if (bpmnElement instanceof StartEvent) {
			style = processEngineConfiguration.getStyle("StartEvent");
		} else if (bpmnElement instanceof EndEvent) {
			style = processEngineConfiguration.getStyle("EndEvent");
		} else if (bpmnElement instanceof ParallelGateway) {
			style = processEngineConfiguration.getStyle("ParallelGateway");
		} else if (bpmnElement instanceof InclusiveGateway) {
			style = processEngineConfiguration.getStyle("InclusiveGateway");
		} else if (bpmnElement instanceof ExclusiveGateway) {
			style = processEngineConfiguration.getStyle("ExclusiveGateway");
		} else if (bpmnElement instanceof UserTask) {
			style = processEngineConfiguration.getStyle("UserTask");
		} else if (bpmnElement instanceof Lane) {
			style = processEngineConfiguration.getStyle("Lane");
		} else if (bpmnElement instanceof TextAnnotation) {
			style = processEngineConfiguration.getStyle("TextAnnotation");
		} else if (bpmnElement instanceof Group) {
			style = processEngineConfiguration.getStyle("Group");
		}else if (bpmnElement instanceof SubProcess) {
			style = processEngineConfiguration.getStyle("SubProcess");
		}else if (bpmnElement instanceof CallActivity) {
			style = processEngineConfiguration.getStyle("CallActivity");
		}else if (bpmnElement instanceof ServiceTask) {
			style = processEngineConfiguration.getStyle("ServiceTask");
		}else if (bpmnElement instanceof ManualTask) {
			style = processEngineConfiguration.getStyle("ManualTask");
		}else if (bpmnElement instanceof SendTask) {
			style = processEngineConfiguration.getStyle("SendTask");
		}else if (bpmnElement instanceof ReceiveTask) {
			style = processEngineConfiguration.getStyle("ReceiveTask");
		}else if (bpmnElement instanceof BusinessRuleTask) {
			style = processEngineConfiguration.getStyle("BusinessRuleTask");
		}else if (bpmnElement instanceof Task) {
			style = processEngineConfiguration.getStyle("Task");
		}
		
		if(style == null){
			throw new FoxBPMException("未找到"+bpmnElement.getClass()+"的style样式");
		}
		return style;
	}

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

	private BaseElement getBaseElement(BaseElement baseElement) {
		if (baseElement == null) {
			return null;
		}

		if (baseElement.getId() == null) {
			BasicEObjectImpl basicEObjectImpl = (BasicEObjectImpl) baseElement;
			if (basicEObjectImpl != null && basicEObjectImpl.eProxyURI() != null) {
				String elementId = basicEObjectImpl.eProxyURI().fragment();
				BaseElement bpmnElement = BpmnModelUtil.findElement(elementId, baseElement);
				return bpmnElement;
			} else {
				return null;
			}
		} else {
			return baseElement;
		}
	}

	private Process createProcess(String processId, InputStream is) {

		ResourceSet resourceSet = getResourceSet();
		String fixflowFilePath = ProcessEngineManagement.getDefaultProcessEngine().getProcessEngineConfiguration().getNoneTemplateFilePath();
		URL url = ReflectUtil.getResource(fixflowFilePath);
		if (url == null) {
			throw new FoxBPMClassLoadingException(ExceptionCode.CLASSLOAD_EXCEPTION_FILENOTFOUND, fixflowFilePath);
		}
		String filePath = url.toString();
		Resource ddddResource = null;
		try {
			if (!filePath.startsWith("jar")) {
				filePath = java.net.URLDecoder.decode(url.getFile(), "utf-8");
				ddddResource = resourceSet.createResource(URI.createFileURI(filePath));
			} else {
				ddddResource = resourceSet.createResource(URI.createURI(filePath));
			}
			ddddResource.load(is, null);
		} catch (Exception e) {
			throw new FoxBPMClassLoadingException(ExceptionCode.CLASSLOAD_EXCEPTION, e);
		}
		Definitions definitions = (Definitions) ddddResource.getContents().get(0).eContents().get(0);
		for (RootElement rootElement : definitions.getRootElements()) {
			if (rootElement instanceof Process) {
				Process processObj = (Process) rootElement;
				processObj = (Process) rootElement;
				return processObj;
			}
		}
		return null;

	}

	private ResourceSet getResourceSet() {
		ResourceSet resourceSet = new ResourceSetImpl();
		(EPackage.Registry.INSTANCE).put("http://www.omg.org/spec/BPMN/20100524/MODEL", Bpmn2Package.eINSTANCE);
		(EPackage.Registry.INSTANCE).put("http://www.foxbpm.org/foxbpm", FoxBPMPackage.eINSTANCE);
		(EPackage.Registry.INSTANCE).put("http://www.omg.org/spec/DD/20100524/DI", DiPackage.eINSTANCE);
		(EPackage.Registry.INSTANCE).put("http://www.omg.org/spec/DD/20100524/DC", DcPackage.eINSTANCE);
		(EPackage.Registry.INSTANCE).put("http://www.omg.org/spec/BPMN/20100524/DI", BpmnDiPackage.eINSTANCE);
		FoxBPMPackage.eINSTANCE.eClass();
		FoxBPMPackage xxxPackage = FoxBPMPackage.eINSTANCE;
		EPackage.Registry.INSTANCE.put(xxxPackage.getNsURI(), xxxPackage);
		Bpmn2ResourceFactoryImpl ddd = new Bpmn2ResourceFactoryImpl();
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("foxbpm", ddd);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("bpmn", ddd);
		resourceSet.getPackageRegistry().put(xxxPackage.getNsURI(), xxxPackage);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("bpmn", ddd);
		return resourceSet;
	}

}
