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

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.impl.bpmn.behavior.ActivityBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.BusinessRuleTaskBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.CallActivityBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.EndEventBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.EventBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.ExclusiveGatewayBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.GatewayBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.InclusiveGatewayBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.ManualTaskBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.ParallelGatewayBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.ReceiveTaskBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.ScriptTaskBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.SendTaskBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.ServiceTaskBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.StartEventBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.SubProcessBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.TaskBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.UserTaskBehavior;
import org.foxbpm.engine.impl.diagramview.svg.SVGTemplateNameConstant;
import org.foxbpm.engine.impl.diagramview.svg.SVGTypeNameConstant;
import org.foxbpm.engine.impl.diagramview.svg.vo.VONode;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.task.Task;
import org.foxbpm.kernel.behavior.KernelFlowNodeBehavior;
import org.foxbpm.kernel.process.KernelFlowElement;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelSequenceFlowImpl;

/**
 * PROCESS流程图形工厂类
 * 
 * @author MAENLIANG
 * @date 2014-06-11
 * 
 */
public class ConcreteProcessDefinitionVOFactory extends
		AbstractProcessDefinitionVOFactory {
	private static final String EMPTY_STRING = "";
	private static final int EMPTY_LIST = 0;
	private static final int ARRAY_INDEX_FIRST = 0;
	private static final int ARRAY_INDEX_SECOND = 1;
	private static final String TASK_END = "end";
	private static final String TASK_NOTEND = "notEnd";
	private static final String TASK_NOTEXISTS = "notExists";

	/**
	 * 创建节点工厂
	 */
	private AbstractFlowNodeVOFactory flowNodeVOFactory;

	/**
	 * 创建流程图，包括标记信息
	 * 
	 * @param taskList
	 *            所有需要标识的任务信息
	 * @param deployedProcessDefinition
	 *            流程定义信息
	 * @return 包含标记的，流程图字符串
	 */
	public String createProcessInstanceSVGImageString(List<Task> taskList,
			ProcessDefinitionEntity deployedProcessDefinition) {
		List<KernelFlowNodeImpl> flowNodes = deployedProcessDefinition
				.getFlowNodes();
		Map<String, KernelSequenceFlowImpl> sequenceFlows = deployedProcessDefinition
				.getSequenceFlows();

		List<VONode> voNodeList = new ArrayList<VONode>();
		// 遍历所有的流程节点
		Iterator<KernelFlowNodeImpl> flowNodeIterator = flowNodes.iterator();
		String taskType = EMPTY_STRING;
		String svgTemplateFileName = EMPTY_STRING;
		while (flowNodeIterator.hasNext()) {
			KernelFlowNodeImpl kernelFlowNodeImpl = flowNodeIterator.next();
			String[] typeTemplateArray = this
					.getTypeAndTemplateNameByFlowNode(kernelFlowNodeImpl);
			taskType = typeTemplateArray[ARRAY_INDEX_FIRST];
			svgTemplateFileName = typeTemplateArray[ARRAY_INDEX_SECOND];
			VONode voNode = null;
			// 如果任务列表为空，或者当前节点是网关节点，则不添加标识
//			if (taskList == null
//					|| taskList.size() == EMPTY_LIST
//					|| kernelFlowNodeImpl.getKernelFlowNodeBehavior() instanceof GatewayBehavior) {
				voNode = this.getNodeSVGFromFactory(kernelFlowNodeImpl,
						taskType, svgTemplateFileName);
			// } else {
			// String taskState = this.confirmTaskNotEnd(taskList,
			// kernelFlowNodeImpl.getId());
			// voNode = this.getSignedNodeSVGFromFactory(kernelFlowNodeImpl,
			// taskType, svgTemplateFileName, taskState);
			// }
			voNodeList.add(voNode);
		}

		// 遍历所有的流程连线
		Iterator<Entry<String, KernelSequenceFlowImpl>> sequenceFlowterator = sequenceFlows
				.entrySet().iterator();
		while (sequenceFlowterator.hasNext()) {
			Entry<String, KernelSequenceFlowImpl> nextConnector = sequenceFlowterator
					.next();
			KernelSequenceFlowImpl sequenceFlowImpl = nextConnector.getValue();
			taskType = SVGTypeNameConstant.SVG_TYPE_CONNECTOR;
			svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_CONNECTOR_SEQUENCEFLOW;
			VONode voNode = this.getNodeSVGFromFactory(sequenceFlowImpl,
					taskType, svgTemplateFileName);
			voNodeList.add(voNode);
		}

		String svgStr = flowNodeVOFactory.convertNodeListToString(
				deployedProcessDefinition.getProperties(), voNodeList);
		System.out.println(svgStr);
		return svgStr;
	}

	/**
	 * 根据所有流程节点，和流程连接创建流程SVG文档字符串
	 */
	public String createProcessDefinitionVOString(
			ProcessDefinitionEntity deployedProcessDefinition) {
		// 内部处理
		return this.createProcessInstanceSVGImageString(null,
				deployedProcessDefinition);
	}

	/**
	 * 判断任务节点是否已经处理过
	 * 
	 * @param taskList
	 * @param taskFlowNodeID
	 * @return
	 */
	private String confirmTaskNotEnd(List<Task> taskList, String taskFlowNodeID) {
		String result = TASK_NOTEXISTS;
		Iterator<Task> notEndIter = taskList.iterator();
		while (notEndIter.hasNext()) {
			Task notEnd = notEndIter.next();
			if (StringUtils
					.equalsIgnoreCase(notEnd.getNodeId(), taskFlowNodeID)) {
				if (notEnd.hasEnded()) {
					result = TASK_END;
				} else {
					result = TASK_NOTEND;
				}
				return result;
			}
		}
		return result;
	}

	/**
	 * 创建流程元素SVG
	 * 
	 * @param kernelFlowNodeImpl
	 * @param svgType
	 * @param svgTemplateFileName
	 * @return
	 */
	private VONode getNodeSVGFromFactory(KernelFlowElement kernelFlowNodeImpl,
			String svgType, String svgTemplateFileName) {
		// 调用节点构造方法，创建SVG VALUE OBJECT 对象
		flowNodeVOFactory = AbstractFlowNodeVOFactory.createSVGFactory(
				kernelFlowNodeImpl, svgTemplateFileName);
		return flowNodeVOFactory.createFlowElementSVGVO(svgType);
	}

	/**
	 * 创建具有标记的流程元素SVG
	 * 
	 * @param kernelFlowNodeImpl
	 * @param svgType
	 * @param svgTemplateFileName
	 * @return
	 */
	private VONode getSignedNodeSVGFromFactory(
			KernelFlowElement kernelFlowNodeImpl, String svgType,
			String svgTemplateFileName, String taskState) {
		// 创建具体工厂
		AbstractFlowNodeVOFactory conreateFactory = AbstractFlowNodeVOFactory
				.createSVGFactory(kernelFlowNodeImpl, svgTemplateFileName);
		// 创建标记工厂，标记工厂
		flowNodeVOFactory = AbstractFlowNodeVOFactory.createSignedSVGFactory(
				kernelFlowNodeImpl, svgTemplateFileName, taskState,
				conreateFactory);
		// 标记构造独自实现，非侵入,独自扩展，对应SgnProcessStateSVGFactory工厂，
		return flowNodeVOFactory.createFlowElementSVGVO(svgType);
	}

	/**
	 * 获取nodeType和模版名称
	 * 
	 * @param kernelFlowNodeImpl
	 * @return
	 */
	private String[] getTypeAndTemplateNameByFlowNode(
			KernelFlowNodeImpl kernelFlowNodeImpl) {
		KernelFlowNodeBehavior kernelFlowNodeBehavior = kernelFlowNodeImpl
				.getKernelFlowNodeBehavior();
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
				}
			} else if (kernelFlowNodeBehavior instanceof CallActivityBehavior) {
				// 外部子流程
			} else if (kernelFlowNodeBehavior instanceof SubProcessBehavior) {
				// 子流程
			}
			// 网关
		} else if (kernelFlowNodeBehavior instanceof GatewayBehavior) {
			if (kernelFlowNodeBehavior instanceof ExclusiveGatewayBehavior) {
				// 排他网关
				taskType = SVGTypeNameConstant.SVT_TYPE_GATEWAY;
				svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_GATEWAY_EXCLUSIVE;
			} else if (kernelFlowNodeBehavior instanceof InclusiveGatewayBehavior) {
				// 并行网关
				taskType = SVGTypeNameConstant.SVT_TYPE_GATEWAY;
				svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_GATEWAY_INCLUSIVE;
			} else if (kernelFlowNodeBehavior instanceof ParallelGatewayBehavior) {
				// 包容网关
			}
			// 事件
		} else if (kernelFlowNodeBehavior instanceof EventBehavior) {
			if (kernelFlowNodeBehavior instanceof StartEventBehavior) {
				// 开始事件
				taskType = SVGTypeNameConstant.SVG_TYPE_EVENT;
				svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_STARTEVENT_NONE;
			} else if (kernelFlowNodeBehavior instanceof EndEventBehavior) {
				// 结束事件
				taskType = SVGTypeNameConstant.SVG_TYPE_EVENT;
				svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_ENDEVENT_NONE;
			}
		}

		return new String[] { taskType, svgTemplateFileName };
	}

}
