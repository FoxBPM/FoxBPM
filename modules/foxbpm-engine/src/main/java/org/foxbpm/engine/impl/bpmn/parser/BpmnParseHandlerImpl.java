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
import java.util.List;

import org.eclipse.bpmn2.Association;
import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.InclusiveGateway;
import org.eclipse.bpmn2.Lane;
import org.eclipse.bpmn2.LaneSet;
import org.eclipse.bpmn2.MessageFlow;
import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.RootElement;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.bpmn2.Task;
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
import org.foxbpm.engine.impl.connector.Connector;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.util.BpmnModelUtil;
import org.foxbpm.engine.impl.util.ReflectUtil;
import org.foxbpm.engine.modelparse.ProcessModelParseHandler;
import org.foxbpm.kernel.ProcessDefinitionBuilder;
import org.foxbpm.kernel.behavior.KernelFlowNodeBehavior;
import org.foxbpm.kernel.process.KernelLaneSet;
import org.foxbpm.kernel.process.KernelProcessDefinition;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelLaneImpl;
import org.foxbpm.kernel.process.impl.KernelLaneSetImpl;
import org.foxbpm.kernel.process.impl.KernelSequenceFlowImpl;
import org.foxbpm.model.bpmn.foxbpm.FoxBPMPackage;
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
		// // 加载数据变量
		// loadVariable(processDefinition);
		// // 设置FlowNode元素的子流程
		// loadSubProcess(processDefinition);
		return processDefinition;
	}

	private KernelProcessDefinition loadBehavior(Process process) {
		String processObjId = BpmnModelUtil.getProcessId(process);
		// String category=BpmnModelUtil.getProcessCategory(process);
		List<FlowElement> flowElements = process.getFlowElements();
		ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionEntityBuilder(
				processObjId);
		ProcessBehavior processBehavior = BpmnBehaviorEMFConverter.getProcessBehavior(process,
				processDefinitionBuilder.getProcessDefinition());
		if (processBehavior != null) {
			for (Connector connector : processBehavior.getConnectors()) {
				processDefinitionBuilder.executionListener(connector.getEventType(), connector);
			}
		}

		for (FlowElement flowElement : flowElements) {
			KernelFlowNodeBehavior flowNodeBehavior = BpmnBehaviorEMFConverter.getFlowNodeBehavior(
					flowElement, processDefinitionBuilder.getProcessDefinition());
			if (flowElement instanceof FlowNode) {
				processDefinitionBuilder.createFlowNode(flowElement.getId(), flowElement.getName())
						.behavior(flowNodeBehavior);
				if (flowNodeBehavior instanceof BaseElementBehavior) {
					for (Connector connector : ((BaseElementBehavior) flowNodeBehavior)
							.getConnectors()) {
						processDefinitionBuilder.executionListener(connector.getEventType(),
								connector);
					}
				}
				if (flowElement instanceof StartEvent) {
					processDefinitionBuilder.initial();
				}
				List<SequenceFlow> sequenceFlows = ((FlowNode) flowElement).getOutgoing();
				for (SequenceFlow sequenceFlow : sequenceFlows) {
					processDefinitionBuilder.sequenceFlow(sequenceFlow.getTargetRef().getId(),
							sequenceFlow.getId(), sequenceFlow.getName());
				}
				processDefinitionBuilder.endFlowNode();
			}
		}

		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) processDefinitionBuilder
				.buildProcessDefinition();

		if (process.getLaneSets() != null && process.getLaneSets().size() > 0) {
			for (LaneSet laneSet : process.getLaneSets()) {

				KernelLaneSetImpl laneSetObj = new KernelLaneSetImpl(laneSet.getId(),
						processDefinition);
				laneSetObj.setName(laneSet.getName());
				loadLane(laneSetObj, laneSet, processDefinition);

				processDefinition.getLaneSets().add(laneSetObj);
			}
		}

		processDefinition.setKey(processBehavior.getId());
		processDefinition.setName(processBehavior.getName());
		processDefinition.setCategory(processBehavior.getCategory());
		processDefinition.setFormUri(processBehavior.getFormUri());
		processDefinition.setFormUriView(processBehavior.getFormUriView());
		processDefinition.setSubject(processBehavior.getSubject());
		processDI(processDefinition, process);
		return processDefinition;
	}

	private void loadLane(KernelLaneSet kernelLaneSet, LaneSet laneSet,
			ProcessDefinitionEntity processDefinition) {
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
		ProcessEngineConfigurationImpl processEngineConfiguration = Context
				.getProcessEngineConfiguration();
		Definitions definitions = (Definitions) process.eResource().getContents().get(0)
				.eContents().get(0);
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

					BaseElement bpmnElement = getBaseElement(bpmnShape.getBpmnElement());
					KernelFlowNodeImpl findFlowNode = processDefinition.findFlowNode(bpmnElement
							.getId());
					if (findFlowNode != null) {
						findFlowNode.setWidth((new Float(width)).intValue());
						findFlowNode.setHeight((new Float(height)).intValue());
						findFlowNode.setX((new Float(x)).intValue());
						findFlowNode.setY((new Float(y)).intValue());
					}
					KernelLaneImpl lane = (KernelLaneImpl) processDefinition
							.getLaneForId(bpmnElement.getId());
					if (lane != null) {

						lane.setWidth((new Float(width)).intValue());
						lane.setHeight((new Float(height)).intValue());
						lane.setX((new Float(x)).intValue());
						lane.setY((new Float(y)).intValue());
						lane.setProperty(StyleOption.IsHorizontal, bpmnShape.isIsHorizontal());

					}

					Style style = null;
					if (bpmnElement instanceof StartEvent) {
						style = processEngineConfiguration.getStyle("StartEvent");
					}
					if (bpmnElement instanceof EndEvent) {
						style = processEngineConfiguration.getStyle("EndEvent");
					}

					if (bpmnElement instanceof ParallelGateway) {
						style = processEngineConfiguration.getStyle("ParallelGateway");
					}

					if (bpmnElement instanceof InclusiveGateway) {
						style = processEngineConfiguration.getStyle("InclusiveGateway");
					}

					if (bpmnElement instanceof ExclusiveGateway) {
						style = processEngineConfiguration.getStyle("ExclusiveGateway");
					}

					if (bpmnElement instanceof Task) {
						style = processEngineConfiguration.getStyle("UserTask");
					}

					if (bpmnElement instanceof Lane) {
						style = processEngineConfiguration.getStyle("Lane");
					}

					/*
					 * if (bpmnElement instanceof
					 * IntermediateCatchEventBehavior) { String
					 * intermediateTimerEventSVG =
					 * intermediateTimerEventToSVG(bpmnShape);
					 * svg.addChildren(intermediateTimerEventSVG); } if
					 * (bpmnElement instanceof CallActivity) {
					 * 
					 * String taskSVG = callActivityToSVG(bpmnShape);
					 * svg.addChildren(taskSVG); }
					 * 
					 * if (bpmnElement instanceof Gateway) { String gatewaySVG =
					 * gatewayToSVG(bpmnShape); svg.addChildren(gatewaySVG); }
					 * 
					 * if(bpmnElement instanceof Lane) { String laneSVG =
					 * laneToSVG(bpmnShape); svg.addChildren(laneSVG); }
					 * 
					 * if(bpmnElement instanceof Participant) { String laneSVG =
					 * participantToSVG(bpmnShape); svg.addChildren(laneSVG); }
					 * 
					 * 
					 * 
					 * if(bpmnElement instanceof SubProcess) { String
					 * subProcessSVG = subProcessToSVG(bpmnShape);
					 * svg.addChildren(subProcessSVG); } if(bpmnElement
					 * instanceof Group) { String subProcessSVG =
					 * groupToSVG(bpmnShape,bpmnElement);
					 * svg.addChildren(subProcessSVG); } if(bpmnElement
					 * instanceof DataObject) { String dataObjectSVG=
					 * dataObjectToSVG(bpmnShape,bpmnElement);
					 * svg.addChildren(dataObjectSVG); } //DataStoreReference
					 * //DataInput //DataOutput //Message if(bpmnElement
					 * instanceof DataStoreReference) { String
					 * dataStoreReferenceSVG=
					 * dataStoreReferenceToSVG(bpmnShape,bpmnElement);
					 * svg.addChildren(dataStoreReferenceSVG); } if(bpmnElement
					 * instanceof DataInput) { String dataInputSVG=
					 * dataInputToSVG(bpmnShape,bpmnElement);
					 * svg.addChildren(dataInputSVG); } if(bpmnElement
					 * instanceof DataOutput) { String dataOutputSVG=
					 * dataOutputToSVG(bpmnShape,bpmnElement);
					 * svg.addChildren(dataOutputSVG); } if(bpmnElement
					 * instanceof Message) { String messageSVG=
					 * messageToSVG(bpmnShape,bpmnElement);
					 * svg.addChildren(messageSVG); }
					 * 
					 * 
					 * if(bpmnElement instanceof TextAnnotation) { String
					 * messageSVG= textAnnotationToSVG(bpmnShape,bpmnElement);
					 * svg.addChildren(messageSVG); }
					 * 
					 * 
					 * if(bpmnElement instanceof BoundaryEvent) { String
					 * messageSVG= boundaryEventToSVG(bpmnShape,bpmnElement);
					 * svg.addChildren(messageSVG); }
					 */

					if (style != null && findFlowNode != null) {
						findFlowNode.setProperty(StyleOption.Background, style.getBackground());
						findFlowNode.setProperty(StyleOption.Font, style.getFont());
						findFlowNode.setProperty(StyleOption.Foreground, style.getForeground());
						findFlowNode.setProperty(StyleOption.MulitSelectedColor,
								style.getMulitSelectedColor());
						findFlowNode.setProperty(StyleOption.StyleObject, style.getObject());
						findFlowNode.setProperty(StyleOption.SelectedColor,
								style.getSelectedColor());
						findFlowNode.setProperty(StyleOption.TextColor, style.getTextColor());
					} else {
						if (style != null && lane != null) {
							lane.setProperty(StyleOption.Background, style.getBackground());
							lane.setProperty(StyleOption.Font, style.getFont());
							lane.setProperty(StyleOption.Foreground, style.getForeground());
							lane.setProperty(StyleOption.MulitSelectedColor,
									style.getMulitSelectedColor());
							lane.setProperty(StyleOption.StyleObject, style.getObject());
							lane.setProperty(StyleOption.SelectedColor, style.getSelectedColor());
							lane.setProperty(StyleOption.TextColor, style.getTextColor());
						}

					}
				}
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
					BaseElement bpmnElement = getBaseElement(bpmnEdge.getBpmnElement());
					if (bpmnElement instanceof SequenceFlow) {
						KernelSequenceFlowImpl findSequenceFlow = processDefinition
								.findSequenceFlow(bpmnElement.getId());
						Style style = processEngineConfiguration.getStyle("SequenceFlow");
						List<Integer> waypoints = new ArrayList<Integer>();
						for (Point point : pointList) {
							waypoints.add((new Float(point.getX())).intValue());
							waypoints.add((new Float(point.getY())).intValue());
						}

						findSequenceFlow.setWaypoints(waypoints);
						if (style != null) {
							findSequenceFlow.setProperty(StyleOption.Background,
									style.getBackground());
							findSequenceFlow.setProperty(StyleOption.Font, style.getFont());
							findSequenceFlow.setProperty(StyleOption.Foreground,
									style.getForeground());
							findSequenceFlow.setProperty(StyleOption.MulitSelectedColor,
									style.getMulitSelectedColor());
							findSequenceFlow
									.setProperty(StyleOption.StyleObject, style.getObject());
							findSequenceFlow.setProperty(StyleOption.SelectedColor,
									style.getSelectedColor());
							findSequenceFlow.setProperty(StyleOption.TextColor,
									style.getTextColor());
						}
						// String sequenceFlowSVG = sequenceFlowToSVG(bpmnEdge);
						// svg.addEdge(sequenceFlowSVG);
					}
					if (bpmnElement instanceof Association) {
						// String associationSVG = associationToSVG(bpmnEdge);
						// svg.addEdge(associationSVG);
					}
					if (bpmnElement instanceof MessageFlow) {
						// String messageFlowSVG = messageFlowToSVG(bpmnEdge);
						// svg.addChildren(messageFlowSVG);
					}
				}
			}
		}

		processDefinition.setProperty("canvas_maxX", maxX + 30);
		processDefinition.setProperty("canvas_maxY", maxY + 70);
		processDefinition.setProperty("canvas_minX", minX);
		processDefinition.setProperty("canvas_minY", minY);
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
		String fixflowFilePath = ProcessEngineManagement.getDefaultProcessEngine()
				.getProcessEngineConfiguration().getNoneTemplateFilePath();
		URL url = ReflectUtil.getResource(fixflowFilePath);
		if (url == null) {
			throw new FoxBPMClassLoadingException(ExceptionCode.CLASSLOAD_EXCEPTION_FILENOTFOUND,
					fixflowFilePath);
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
		Definitions definitions = (Definitions) ddddResource.getContents().get(0).eContents()
				.get(0);
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
		(EPackage.Registry.INSTANCE).put("http://www.omg.org/spec/BPMN/20100524/MODEL",
				Bpmn2Package.eINSTANCE);
		(EPackage.Registry.INSTANCE).put("http://www.foxbpm.org/foxbpm", FoxBPMPackage.eINSTANCE);
		(EPackage.Registry.INSTANCE).put("http://www.omg.org/spec/DD/20100524/DI",
				DiPackage.eINSTANCE);
		(EPackage.Registry.INSTANCE).put("http://www.omg.org/spec/DD/20100524/DC",
				DcPackage.eINSTANCE);
		(EPackage.Registry.INSTANCE).put("http://www.omg.org/spec/BPMN/20100524/DI",
				BpmnDiPackage.eINSTANCE);
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
