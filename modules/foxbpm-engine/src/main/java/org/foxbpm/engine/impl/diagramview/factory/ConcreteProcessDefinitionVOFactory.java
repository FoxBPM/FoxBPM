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
 * @author MAENLIANG
 */
package org.foxbpm.engine.impl.diagramview.factory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.bpmn.behavior.ActivityBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.AssociationBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.BoundaryEventBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.BusinessRuleTaskBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.CallActivityBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.CatchEventBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.EndEventBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.EventBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.EventDefinition;
import org.foxbpm.engine.impl.bpmn.behavior.ExclusiveGatewayBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.GatewayBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.GroupBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.InclusiveGatewayBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.IntermediateCatchEventBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.ManualTaskBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.ParallelGatewayBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.ReceiveTaskBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.ScriptTaskBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.SendTaskBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.ServiceTaskBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.StartEventBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.SubProcessBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.TaskBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.TerminateEventBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.TextAnnotationBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.ThrowEventBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.TimerEventBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.UserTaskBehavior;
import org.foxbpm.engine.impl.bpmn.parser.StyleOption;
import org.foxbpm.engine.impl.diagramview.svg.SVGTemplateNameConstant;
import org.foxbpm.engine.impl.diagramview.svg.SVGTypeNameConstant;
import org.foxbpm.engine.impl.diagramview.vo.VONode;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.kernel.behavior.KernelArtifactBehavior;
import org.foxbpm.kernel.behavior.KernelFlowNodeBehavior;
import org.foxbpm.kernel.process.KernelArtifact;
import org.foxbpm.kernel.process.KernelBaseElement;
import org.foxbpm.kernel.process.KernelLane;
import org.foxbpm.kernel.process.KernelLaneSet;
import org.foxbpm.kernel.process.KernelSequenceFlow;
import org.foxbpm.kernel.process.impl.KernelArtifactImpl;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelSequenceFlowImpl;

/**
 * PROCESS流程图形工厂类
 * 
 * @author MAENLIANG
 * @date 2014-06-11
 * 
 */
public class ConcreteProcessDefinitionVOFactory extends AbstractProcessDefinitionVOFactory {
	private static final String EMPTY_STRING = "";
	private static final int ARRAY_INDEX_FIRST = 0;
	private static final int ARRAY_INDEX_SECOND = 1;

	/**
	 * 创建节点工厂
	 */
	private AbstractFlowElementVOFactory flowNodeVOFactory;

	/**
	 * 根据所有流程节点，和流程连接创建流程SVG文档字符串
	 */
	public String createProcessDefinitionVOString(ProcessDefinitionEntity deployedProcessDefinition) {
		List<VONode> voNodeList = new ArrayList<VONode>();
		// 构建泳道
		this.createLaneSetVO(deployedProcessDefinition.getLaneSets(), voNodeList);
		// 创建所有的流程节点
		this.createFlowNodeVO(deployedProcessDefinition.getFlowNodes(), voNodeList);
		// 构建SEQUENCE
		this.filterSubProcessSequence(deployedProcessDefinition);
		this.createSequenceVO(deployedProcessDefinition.getSequenceFlows(), voNodeList);
		// 构建小部件
		this.createArtifactVO(deployedProcessDefinition.getArtifacts(), voNodeList);
		// 转化成SVG字符串
		return flowNodeVOFactory.convertNodeListToString(deployedProcessDefinition.getProperties(),
				voNodeList);
	}

	/**
	 * 
	 * createFlowNodeVO(构建节点VO)
	 * 
	 * @param flowNodes
	 * @param voNodeList
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	private void createFlowNodeVO(List<KernelFlowNodeImpl> flowNodes, List<VONode> voNodeList) {
		// 遍历所有的流程节点
		Iterator<KernelFlowNodeImpl> flowNodeIterator = flowNodes.iterator();
		String taskType = EMPTY_STRING;
		String svgTemplateFileName = EMPTY_STRING;
		KernelFlowNodeImpl kernelFlowNodeImpl = null;
		VONode voNode = null;
		String[] typeTemplateArray = null;
		while (flowNodeIterator.hasNext()) {
			kernelFlowNodeImpl = flowNodeIterator.next();
			typeTemplateArray = this.getTypeAndTemplateNameByFlowNode(kernelFlowNodeImpl);
			taskType = typeTemplateArray[ARRAY_INDEX_FIRST];
			svgTemplateFileName = typeTemplateArray[ARRAY_INDEX_SECOND];
			if (StringUtils.isBlank(taskType) || StringUtils.isBlank(svgTemplateFileName)) {
				continue;
			}

			// 构造流程节点
			voNode = this.getNodeSVGFromFactory(kernelFlowNodeImpl, taskType, svgTemplateFileName);
			voNodeList.add(voNode);

			// 递归创建子流程节点
			List<KernelFlowNodeImpl> subFlowNodes = kernelFlowNodeImpl.getFlowNodes();

			if (subFlowNodes != null && subFlowNodes.size() > 0
					&& (Boolean) kernelFlowNodeImpl.getProperty(StyleOption.IsExpanded)) {
				createFlowNodeVO(subFlowNodes, voNodeList);
			}
		}
	}

	/**
	 * 泳道VO
	 * 
	 * @param deployedProcessDefinition
	 * @param voNodeList
	 */
	private void createLaneSetVO(List<KernelLaneSet> laneSets, List<VONode> voNodeList) {
		Iterator<KernelLaneSet> laneSetIter = laneSets.iterator();
		KernelLaneSet laneSet = null;
		while (laneSetIter.hasNext()) {
			laneSet = laneSetIter.next();
			this.createLaneVO(laneSet, voNodeList);
		}
	}

	/**
	 * 
	 * createLaneVO(泳道VO)
	 * 
	 * @param laneSet
	 * @param voNodeList
	 * @since 1.0.0
	 */
	private void createLaneVO(KernelLaneSet laneSet, List<VONode> voNodeList) {
		List<KernelLane> lanes = laneSet.getLanes();
		Iterator<KernelLane> laneIter = lanes.iterator();
		KernelLane lane = null;
		VONode voNode = null;
		KernelLaneSet childLaneSet = null;
		while (laneIter.hasNext()) {
			lane = laneIter.next();
			voNode = this.getNodeSVGFromFactory(lane, SVGTypeNameConstant.SVG_TYPE_LANE,
					SVGTemplateNameConstant.TEMPLATE_LANESET);
			voNodeList.add(voNode);
			childLaneSet = lane.getChildLaneSet();
			if (childLaneSet != null) {
				this.createLaneVO(childLaneSet, voNodeList);
			}

		}
	}

	/**
	 * 
	 * createArtifactVO(构造小部件，包括组，注释，连接线)
	 * 
	 * @param deployedProcessDefinition
	 * @param voNodeList
	 * @exception
	 * @since 1.0.0
	 */
	private void createArtifactVO(List<KernelArtifact> artifacts, List<VONode> voNodeList) {
		if (artifacts != null && artifacts.size() > 0) {
			String taskType = EMPTY_STRING;
			String svgTemplateFileName = EMPTY_STRING;
			Iterator<KernelArtifact> iterator = artifacts.iterator();
			VONode voNode = null;
			KernelArtifactImpl kernelArtifactImpl = null;
			KernelArtifactBehavior artifactBehavior = null;
			while (iterator.hasNext()) {
				kernelArtifactImpl = (KernelArtifactImpl) iterator.next();
				artifactBehavior = kernelArtifactImpl.getArtifactBehavior();
				// 小部件分类处理，分别是组、注释、连接线
				if (artifactBehavior instanceof GroupBehavior) {
					taskType = SVGTypeNameConstant.SVG_TYPE_GROUP;
					svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_GROUP;
				} else if (artifactBehavior instanceof TextAnnotationBehavior) {
					taskType = SVGTypeNameConstant.SVG_TYPE_TEXTANNOTATION;
					svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_TEXTANNOTATION;
				} else if (artifactBehavior instanceof AssociationBehavior) {
					taskType = SVGTypeNameConstant.SVG_TYPE_CONNECTOR_ASSOCIATION_UNDIRECTED;
					svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_CONNECTOR_ASSOCIATION;
				}

				voNode = this.getNodeSVGFromFactory(kernelArtifactImpl, taskType,
						svgTemplateFileName);
				voNodeList.add(voNode);
			}
		}

	}
	/**
	 * 创建连线VO
	 * 
	 * @param deployedProcessDefinition
	 * @param voNodeList
	 */
	private void createSequenceVO(Map<String, KernelSequenceFlowImpl> sequenceFlows,
			List<VONode> voNodeList) {
		String taskType = EMPTY_STRING;
		String svgTemplateFileName = EMPTY_STRING;
		// 遍历所有的流程连线
		Iterator<Entry<String, KernelSequenceFlowImpl>> sequenceFlowterator = sequenceFlows
				.entrySet().iterator();
		VONode voNode = null;
		Entry<String, KernelSequenceFlowImpl> nextConnector = null;
		KernelSequenceFlowImpl sequenceFlowImpl = null;
		while (sequenceFlowterator.hasNext()) {
			nextConnector = sequenceFlowterator.next();
			sequenceFlowImpl = nextConnector.getValue();
			taskType = SVGTypeNameConstant.SVG_TYPE_CONNECTOR;
			svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_CONNECTOR_SEQUENCEFLOW;
			voNode = this.getNodeSVGFromFactory(sequenceFlowImpl, taskType, svgTemplateFileName);
			voNodeList.add(voNode);
		}
	}

	/**
	 * 创建流程元素SVG
	 * 
	 * @param kernelFlowNodeImpl
	 * @param svgType
	 * @param svgTemplateFileName
	 * @return
	 */
	private VONode getNodeSVGFromFactory(KernelBaseElement kernelBaseElement, String svgType,
			String svgTemplateFileName) {
		// 调用节点构造方法，创建SVG VALUE OBJECT 对象
		flowNodeVOFactory = AbstractFlowElementVOFactory.createSVGFactory(kernelBaseElement,
				svgTemplateFileName);
		return flowNodeVOFactory.createFlowElementSVGVO(svgType);
	}

	/**
	 * 获取nodeType和模版名称
	 * 
	 * @param kernelFlowNodeImpl
	 * @return
	 */
	private String[] getTypeAndTemplateNameByFlowNode(KernelFlowNodeImpl kernelFlowNodeImpl) {
		KernelFlowNodeBehavior kernelFlowNodeBehavior = kernelFlowNodeImpl
				.getKernelFlowNodeBehavior();
		if (kernelFlowNodeBehavior == null) {
			throw new FoxBPMException(kernelFlowNodeImpl.getId()
					+ " 对应的behavior 是空！导致获取VOFactory 异常！");
		}
		String taskType = EMPTY_STRING;
		String svgTemplateFileName = EMPTY_STRING;
		// 活动任务，先判断父类以减少判断次数，提高效率
		if (kernelFlowNodeBehavior instanceof ActivityBehavior) {
			if (kernelFlowNodeBehavior instanceof TaskBehavior) {
				// 人工任务
				if (kernelFlowNodeBehavior instanceof UserTaskBehavior) {
					taskType = SVGTypeNameConstant.ACTIVITY_USERTASK;
					svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK;
				} else if (kernelFlowNodeBehavior instanceof SendTaskBehavior) {
					taskType = SVGTypeNameConstant.ACTIVITY_SENDTASK;
					svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK;
				} else if (kernelFlowNodeBehavior instanceof ServiceTaskBehavior) {
					taskType = SVGTypeNameConstant.ACTIVITY_SERVICETASK;
					svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK;
				} else if (kernelFlowNodeBehavior instanceof ManualTaskBehavior) {
					taskType = SVGTypeNameConstant.ACTIVITY_MANUALTASK;
					svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK;
				} else if (kernelFlowNodeBehavior instanceof BusinessRuleTaskBehavior) {
					taskType = SVGTypeNameConstant.ACTIVITY_BUSINESSRULETASK;
					svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK;
				} else if (kernelFlowNodeBehavior instanceof ReceiveTaskBehavior) {
					taskType = SVGTypeNameConstant.ACTIVITY_RECEIVETASK;
					svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK;
				} else if (kernelFlowNodeBehavior instanceof ScriptTaskBehavior) {
					taskType = SVGTypeNameConstant.ACTIVITY_SCRIPTTASK;
					svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK;
				} else {
					taskType = SVGTypeNameConstant.SVG_TYPE_TASK;
					svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK;
				}
			} else if (kernelFlowNodeBehavior instanceof CallActivityBehavior) {
				// 外部子流程
				taskType = SVGTypeNameConstant.SVG_TYPE_CALLACTIVITY;
				svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_ACTIVITY_CALLACTIVITY;
			} else if (kernelFlowNodeBehavior instanceof SubProcessBehavior) {
				// 子流程
				taskType = SVGTypeNameConstant.SVG_TYPE_SUBPROCESS;
				if ((Boolean) kernelFlowNodeImpl.getProperty(StyleOption.IsExpanded)) {
					svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_ACTIVITY_SUBPROCESS;
				} else {
					svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_ACTIVITY_SUBPROCESS_COLLAPSED;
				}
			}
			// 网关
		} else if (kernelFlowNodeBehavior instanceof GatewayBehavior) {
			if (kernelFlowNodeBehavior instanceof ExclusiveGatewayBehavior) {
				// 排他网关
				taskType = SVGTypeNameConstant.SVT_TYPE_GATEWAY;
				svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_GATEWAY_EXCLUSIVE;
			} else if (kernelFlowNodeBehavior instanceof InclusiveGatewayBehavior) {
				// 包容网关
				taskType = SVGTypeNameConstant.SVT_TYPE_GATEWAY;
				svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_GATEWAY_INCLUSIVE;
			} else if (kernelFlowNodeBehavior instanceof ParallelGatewayBehavior) {
				// 并行网关
				taskType = SVGTypeNameConstant.SVT_TYPE_GATEWAY;
				svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_GATEWAY_PARALLEL;
			}
		} else if (kernelFlowNodeBehavior instanceof EventBehavior) {
			// 捕获事件
			if (kernelFlowNodeBehavior instanceof CatchEventBehavior) {
				boolean hasTimerDefinition = this
						.hasTimerDefinition((CatchEventBehavior) kernelFlowNodeBehavior);
				if (hasTimerDefinition) {
					svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_CATCHEVENT_TIMER;
				}
				if (kernelFlowNodeBehavior instanceof StartEventBehavior) {
					// 开始事件
					taskType = SVGTypeNameConstant.SVG_TYPE_EVENT;
					svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_STARTEVENT_NONE;
					if (hasTimerDefinition) {
						taskType = SVGTypeNameConstant.SVG_TYPE_EVENT_START_TIMER;
						svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_STARTEVENT_TIMER;
					}
				} else if (kernelFlowNodeBehavior instanceof BoundaryEventBehavior) {
					// 边界事件
					taskType = SVGTypeNameConstant.SVG_TYPE_EVENT_BOUNDARY_INTERRUPTING_TIME;
				} else if (kernelFlowNodeBehavior instanceof IntermediateCatchEventBehavior) {
					// 中间件事件
					taskType = SVGTypeNameConstant.SVG_TYPE_EVENT_BOUNDARY_INTERRUPTING_TIME;
				}
			} else if (kernelFlowNodeBehavior instanceof EndEventBehavior) {
				// 结束事件
				taskType = SVGTypeNameConstant.SVG_TYPE_EVENT;
				svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_ENDEVENT_NONE;
				if (this.hasTerminateDefinition((EndEventBehavior) kernelFlowNodeBehavior)) {
					taskType = SVGTypeNameConstant.SVG_TYPE_EVENT_END_TERMINATE;
					svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_ENDEVENT_TERMINATE;
				}

			} else if (kernelFlowNodeBehavior instanceof ThrowEventBehavior) {
				// 抛出事件
			}
		}

		return new String[]{taskType, svgTemplateFileName};
	}

	/**
	 * 
	 * hasTerminateDefinition(判断是否有终止定义)
	 * 
	 * @param endEventBehavior
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	private boolean hasTerminateDefinition(EndEventBehavior endEventBehavior) {
		List<EventDefinition> eventDefinitions = endEventBehavior.getEventDefinitions();
		Iterator<EventDefinition> iterator = eventDefinitions.iterator();
		while (iterator.hasNext()) {
			EventDefinition next = iterator.next();
			if (next instanceof TerminateEventBehavior) {
				return true;
			}
		}
		return false;

	}
	/**
	 * 
	 * hasTimerDefinition(判断捕获事件是否有时间定义)
	 * 
	 * @param catchEventBehavior
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	private boolean hasTimerDefinition(CatchEventBehavior catchEventBehavior) {
		List<EventDefinition> eventDefinitions = catchEventBehavior.getEventDefinitions();
		Iterator<EventDefinition> iterator = eventDefinitions.iterator();
		while (iterator.hasNext()) {
			EventDefinition next = iterator.next();
			if (next instanceof TimerEventBehavior) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 
	 * filterSubProcessSequence(过滤流程定义中属于子流程中的线条)
	 * 
	 * @param deployedProcessDefinition
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	private void filterSubProcessSequence(ProcessDefinitionEntity deployedProcessDefinition) {
		List<KernelFlowNodeImpl> flowNodes = deployedProcessDefinition.getFlowNodes();
		Iterator<KernelFlowNodeImpl> iterator = flowNodes.iterator();
		KernelFlowNodeImpl kernelFlowNodeImpl = null;
		List<KernelFlowNodeImpl> subFlowNodes = null;
		KernelFlowNodeImpl subKernelFlowNodeImpl = null;
		List<KernelSequenceFlow> outgoingSequenceFlows = null;
		while (iterator.hasNext()) {
			kernelFlowNodeImpl = iterator.next();
			subFlowNodes = kernelFlowNodeImpl.getFlowNodes();
			if (subFlowNodes != null && subFlowNodes.size() > 0
					&& !(Boolean) kernelFlowNodeImpl.getProperty(StyleOption.IsExpanded)) {
				// 如果子流程是收起来的，那么就过滤当前子流程包含的节点所关联的线条，因为所有的线条都是附属在主流程定义上的
				Iterator<KernelFlowNodeImpl> subIterator = subFlowNodes.iterator();
				while (subIterator.hasNext()) {
					subKernelFlowNodeImpl = subIterator.next();
					outgoingSequenceFlows = subKernelFlowNodeImpl.getOutgoingSequenceFlows();
					this.filterSubProcessSequenceBySubNodeSequence(deployedProcessDefinition,
							outgoingSequenceFlows);
				}
			}
		}
	}

	/**
	 * 
	 * filterSubProcessSequenceBySubNodeSequence(根据子流程节点线条集合过滤)
	 * 
	 * @param deployedProcessDefinition
	 * @param outgoingSequenceFlows
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	private void filterSubProcessSequenceBySubNodeSequence(
			ProcessDefinitionEntity deployedProcessDefinition,
			List<KernelSequenceFlow> outgoingSequenceFlows) {
		Iterator<KernelSequenceFlow> iterator = outgoingSequenceFlows.iterator();
		KernelSequenceFlow kernelSequenceFlow = null;
		while (iterator.hasNext()) {
			kernelSequenceFlow = iterator.next();
			this.filterSubProcessSequenceByID(deployedProcessDefinition, kernelSequenceFlow.getId());
		}
	}

	/**
	 * 
	 * filterSubProcessSequenceByID(根据ID过滤)
	 * 
	 * @param deployedProcessDefinition
	 * @param id
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	private void filterSubProcessSequenceByID(ProcessDefinitionEntity deployedProcessDefinition,
			String id) {
		Map<String, KernelSequenceFlowImpl> sequenceFlows = deployedProcessDefinition
				.getSequenceFlows();
		Set<Entry<String, KernelSequenceFlowImpl>> entrySet = sequenceFlows.entrySet();
		Iterator<Entry<String, KernelSequenceFlowImpl>> iterator = entrySet.iterator();
		Entry<String, KernelSequenceFlowImpl> next = null;
		KernelSequenceFlowImpl value = null;
		while (iterator.hasNext()) {
			next = iterator.next();
			value = next.getValue();
			if (StringUtils.equals(value.getId(), id)) {
				iterator.remove();
			}
		}
	}

}
