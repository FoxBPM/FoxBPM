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
import java.util.HashMap;
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
import org.foxbpm.engine.impl.bpmn.behavior.TextAnnotationBehavior;
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
import org.foxbpm.model.Activity;
import org.foxbpm.model.CatchEvent;
import org.foxbpm.model.EndEvent;
import org.foxbpm.model.EventDefinition;
import org.foxbpm.model.LoopCharacteristics;
import org.foxbpm.model.MultiInstanceLoopCharacteristics;
import org.foxbpm.model.TerminateEventDefinition;
import org.foxbpm.model.TimerEventDefinition;

/**
 * PROCESS流程图形工厂类
 * 
 * @author MAENLIANG
 * @date 2014-06-11
 * 
 */
public class ConcreteProcessDefinitionVOFactory extends AbstractProcessDefinitionVOFactory {
	private static final String EMPTY_STRING = "";
	private final static Map<Class<?>, String[]> svgTypeMap = initSvgTypeMap();
	/**
	 * SVG类型索引
	 */
	private static final int SVG_TYPE_INDEX = 0;
	/**
	 * SVG模版名称索引
	 */
	private static final int SVG_TEMPLATENAME_INDEX = 1;
	
	/**
	 * 创建节点工厂
	 */
	private AbstractFlowElementVOFactory flowNodeVOFactory;
	
	/**
	 * 
	 * initSvgTypeMap()
	 * 
	 * @return Map<Class<?>,String[]>
	 * @exception
	 * @since 1.0.0
	 */
	private static Map<Class<?>, String[]> initSvgTypeMap() {
		Map<Class<?>, String[]> svgTypeMap = new HashMap<Class<?>, String[]>();
		svgTypeMap.put(TaskBehavior.class, new String[]{SVGTypeNameConstant.SVG_TYPE_TASK, SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK});
		
		svgTypeMap.put(UserTaskBehavior.class, new String[]{SVGTypeNameConstant.ACTIVITY_USERTASK, SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK});
		svgTypeMap.put(SendTaskBehavior.class, new String[]{SVGTypeNameConstant.ACTIVITY_SENDTASK, SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK});
		svgTypeMap.put(ServiceTaskBehavior.class, new String[]{SVGTypeNameConstant.ACTIVITY_SERVICETASK, SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK});
		svgTypeMap.put(ManualTaskBehavior.class, new String[]{SVGTypeNameConstant.ACTIVITY_MANUALTASK, SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK});
		svgTypeMap.put(BusinessRuleTaskBehavior.class, new String[]{SVGTypeNameConstant.ACTIVITY_BUSINESSRULETASK, SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK});
		svgTypeMap.put(ReceiveTaskBehavior.class, new String[]{SVGTypeNameConstant.ACTIVITY_RECEIVETASK, SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK});
		svgTypeMap.put(ScriptTaskBehavior.class, new String[]{SVGTypeNameConstant.ACTIVITY_SCRIPTTASK, SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK});
		svgTypeMap.put(CallActivityBehavior.class, new String[]{SVGTypeNameConstant.SVG_TYPE_CALLACTIVITY, SVGTemplateNameConstant.TEMPLATE_ACTIVITY_CALLACTIVITY});
		svgTypeMap.put(ExclusiveGatewayBehavior.class, new String[]{SVGTypeNameConstant.SVT_TYPE_GATEWAY, SVGTemplateNameConstant.TEMPLATE_GATEWAY_EXCLUSIVE});
		svgTypeMap.put(InclusiveGatewayBehavior.class, new String[]{SVGTypeNameConstant.SVT_TYPE_GATEWAY, SVGTemplateNameConstant.TEMPLATE_GATEWAY_INCLUSIVE});
		svgTypeMap.put(ParallelGatewayBehavior.class, new String[]{SVGTypeNameConstant.SVT_TYPE_GATEWAY, SVGTemplateNameConstant.TEMPLATE_GATEWAY_PARALLEL});
		svgTypeMap.put(GroupBehavior.class, new String[]{SVGTypeNameConstant.SVG_TYPE_GROUP, SVGTemplateNameConstant.TEMPLATE_GROUP});
		svgTypeMap.put(TextAnnotationBehavior.class, new String[]{SVGTypeNameConstant.SVG_TYPE_TEXTANNOTATION, SVGTemplateNameConstant.TEMPLATE_TEXTANNOTATION});
		svgTypeMap.put(AssociationBehavior.class, new String[]{SVGTypeNameConstant.SVG_TYPE_CONNECTOR_ASSOCIATION_UNDIRECTED, SVGTemplateNameConstant.TEMPLATE_CONNECTOR_ASSOCIATION});
		return svgTypeMap;
	}
	
	/**
	 * 根据所有流程节点，和流程连接创建流程SVG文档字符串
	 */
	public String createProcessDefinitionVOString(ProcessDefinitionEntity deployedProcessDefinition) {
		List<VONode> voNodeList = new ArrayList<VONode>();
		// 构建泳道
		this.createLaneSetVO(deployedProcessDefinition.getLaneSets(), voNodeList);
		// 创建所有的流程节点
		this.createFlowNodeVO(deployedProcessDefinition.getFlowNodes(), voNodeList);
		// 构建SEQUENCE、不能包括内部子流程中的线条
		this.filterSubProcessSequence(deployedProcessDefinition);
		this.createSequenceVO(deployedProcessDefinition.getSequenceFlows(), voNodeList);
		// 构建小部件
		this.createArtifactVO(deployedProcessDefinition.getArtifacts(), voNodeList);
		// 转化成SVG字符串
		return flowNodeVOFactory.convertNodeListToString(deployedProcessDefinition.getProperties(), voNodeList);
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
		Iterator<KernelFlowNodeImpl> flowNodeIterator = flowNodes.iterator();
		String taskType = EMPTY_STRING;
		String svgTemplateFileName = EMPTY_STRING;
		KernelFlowNodeImpl kernelFlowNodeImpl = null;
		VONode voNode = null;
		String[] typeTemplateArray = null;
		// 子流程节点集合
		List<KernelFlowNodeImpl> subFlowNodes = null;
		while (flowNodeIterator.hasNext()) {
			kernelFlowNodeImpl = flowNodeIterator.next();
			// 根据流程节点类型获取对应的SVG 类型 和 模版名称
			typeTemplateArray = this.getTypeAndTemplateNameByFlowNode(kernelFlowNodeImpl);
			taskType = typeTemplateArray[SVG_TYPE_INDEX];
			svgTemplateFileName = typeTemplateArray[SVG_TEMPLATENAME_INDEX];
			if (StringUtils.isBlank(taskType) || StringUtils.isBlank(svgTemplateFileName)) {
				continue;
			}
			
			// 构造流程节点
			voNode = this.getNodeSVGFromFactory(kernelFlowNodeImpl, taskType, svgTemplateFileName);
			voNodeList.add(voNode);
			
			// 递归创建子流程节点
			subFlowNodes = kernelFlowNodeImpl.getFlowNodes();
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
	 * 递归创建泳道VO
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
			voNode = this.getNodeSVGFromFactory(lane, SVGTypeNameConstant.SVG_TYPE_LANE, SVGTemplateNameConstant.TEMPLATE_LANESET);
			voNodeList.add(voNode);
			// 如果存在子泳道则递归创建
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
			String[] temp = null;
			while (iterator.hasNext()) {
				kernelArtifactImpl = (KernelArtifactImpl) iterator.next();
				artifactBehavior = kernelArtifactImpl.getArtifactBehavior();
				// 小部件分类处理，分别是组、注释、连接线
				temp = svgTypeMap.get(artifactBehavior.getClass());
				taskType = temp[0];
				svgTemplateFileName = temp[1];
				voNode = this.getNodeSVGFromFactory(kernelArtifactImpl, taskType, svgTemplateFileName);
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
		Iterator<Entry<String, KernelSequenceFlowImpl>> sequenceFlowterator = sequenceFlows.entrySet().iterator();
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
		flowNodeVOFactory = AbstractFlowElementVOFactory.createSVGFactory(kernelBaseElement, svgTemplateFileName);
		return flowNodeVOFactory.createFlowElementSVGVO(svgType);
	}
	
	/**
	 * 根据节点类型获取SVG类型和模版名称
	 * 
	 * @param kernelFlowNodeImpl
	 * @return SVG类型和模版名称
	 */
	private String[] getTypeAndTemplateNameByFlowNode(KernelFlowNodeImpl kernelFlowNodeImpl) {
		KernelFlowNodeBehavior kernelFlowNodeBehavior = kernelFlowNodeImpl.getKernelFlowNodeBehavior();
		if (kernelFlowNodeBehavior == null) {
			throw new FoxBPMException(kernelFlowNodeImpl.getId()
			        + " 对应的behavior 是空！导致获取VOFactory 异常！");
		}
		String taskType = EMPTY_STRING;
		String svgTemplateFileName = EMPTY_STRING;
		// 活动任务，先判断父类以减少判断次数，提高效率
		
		if (kernelFlowNodeBehavior instanceof TaskBehavior
		        || kernelFlowNodeBehavior instanceof GatewayBehavior
		        || kernelFlowNodeBehavior instanceof CallActivityBehavior) {
			String[] temp = svgTypeMap.get(kernelFlowNodeBehavior.getClass());
			taskType = temp[0];
			svgTemplateFileName = temp[1];
			// 判断多实例，暂时只添加活动节点
			if (kernelFlowNodeBehavior instanceof ActivityBehavior) {
				ActivityBehavior activityBehavior = (ActivityBehavior) kernelFlowNodeBehavior;
				
				Activity act = (Activity)activityBehavior.getBaseElement();
				
				
				LoopCharacteristics loopCharacteristics = act.getLoopCharacteristics();
				if (loopCharacteristics != null
				        && loopCharacteristics instanceof MultiInstanceLoopCharacteristics) {
					temp = svgTypeMap.get(kernelFlowNodeBehavior.getClass());
					taskType = temp[0] + "/sequential";
					svgTemplateFileName = temp[1];
				}
			}
		} else if (kernelFlowNodeBehavior instanceof SubProcessBehavior) {
			// 子流程
			taskType = SVGTypeNameConstant.SVG_TYPE_SUBPROCESS;
			if ((Boolean) kernelFlowNodeImpl.getProperty(StyleOption.IsExpanded)) {
				svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_ACTIVITY_SUBPROCESS;
			} else {
				svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_ACTIVITY_SUBPROCESS_COLLAPSED;
			}
		} else if (kernelFlowNodeBehavior instanceof CatchEventBehavior) {
			// 捕获事件
			boolean hasTimerDefinition = this.hasTimerDefinition((CatchEventBehavior) kernelFlowNodeBehavior);
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
				taskType = SVGTypeNameConstant.SVG_TYPE_EVENT_BOUNDARY_NONEINTERRUPTING_TIME;
				if (((BoundaryEventBehavior) kernelFlowNodeBehavior).isCancelActivity()) {
					taskType = SVGTypeNameConstant.SVG_TYPE_EVENT_BOUNDARY_INTERRUPTING_TIME;
				}
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
		List<EventDefinition> eventDefinitions = ((EndEvent)endEventBehavior.getBaseElement()).getEventDefinitions();
		if(eventDefinitions != null){
			Iterator<EventDefinition> iterator = eventDefinitions.iterator();
			while (iterator.hasNext()) {
				EventDefinition next = iterator.next();
				if (next instanceof TerminateEventDefinition) {
					return true;
				}
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
		List<EventDefinition> eventDefinitions = ((CatchEvent)catchEventBehavior.getBaseElement()).getEventDefinitions();
		if(eventDefinitions != null){
			Iterator<EventDefinition> iterator = eventDefinitions.iterator();
			while (iterator.hasNext()) {
				EventDefinition next = iterator.next();
				if (next instanceof TimerEventDefinition) {
					return true;
				}
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
					this.filterSubProcessSequenceBySubNodeSequence(deployedProcessDefinition, outgoingSequenceFlows);
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
		Map<String, KernelSequenceFlowImpl> sequenceFlows = deployedProcessDefinition.getSequenceFlows();
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
