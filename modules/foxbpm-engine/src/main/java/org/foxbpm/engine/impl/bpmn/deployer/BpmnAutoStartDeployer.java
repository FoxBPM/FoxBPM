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
 * @author ych
 */
package org.foxbpm.engine.impl.bpmn.deployer;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.bpmn.behavior.EventDefinition;
import org.foxbpm.engine.impl.bpmn.behavior.StartEventBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.TimerEventDefinition;
import org.foxbpm.engine.impl.entity.DeploymentEntity;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.persistence.deploy.Deployer;
import org.foxbpm.engine.impl.schedule.FoxbpmJobDetail;
import org.foxbpm.engine.impl.schedule.FoxbpmJobExecutionContext;
import org.foxbpm.engine.impl.schedule.FoxbpmScheduler;
import org.foxbpm.engine.impl.schedule.quartz.ProcessIntanceAutoStartJob;
import org.foxbpm.engine.impl.util.GuidUtil;
import org.foxbpm.kernel.behavior.KernelFlowNodeBehavior;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

/**
 * 
 * @author MAENLIANG
 * @date 2014-06-25
 */
public class BpmnAutoStartDeployer implements Deployer {

	private Deployer deployer;

	public BpmnAutoStartDeployer(Deployer deployer) {
		this.deployer = deployer;
	}

	@Override
	public String deploy(DeploymentEntity deployment) {
		if (deployer == null) {
			throw new FoxBPMException("BpmnAutoStartDeployer 没有设置 具体deployer!");
		}
		// BPMN部署
		String processDefinitionID = deployer.deploy(deployment);
		// 设置自动调度JOB
		if (deployment.isNew()) {// 需要更新数据库（新发布或更新）；判断是否有定时启动流程的START EVENT节点
			ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) Context
					.getProcessEngineConfiguration().getDeploymentManager()
					.getProcessDefinitionCache().get(processDefinitionID);

			List<KernelFlowNodeImpl> flowNodes = processDefinition
					.getFlowNodes();
			Iterator<KernelFlowNodeImpl> iterator = flowNodes.iterator();
			while (iterator.hasNext()) {
				KernelFlowNodeImpl kernelFlowNodeImpl = iterator.next();
				KernelFlowNodeBehavior kernelFlowNodeBehavior = kernelFlowNodeImpl
						.getKernelFlowNodeBehavior();
				if (kernelFlowNodeBehavior instanceof StartEventBehavior) {
					List<EventDefinition> eventDefinitions = ((StartEventBehavior) kernelFlowNodeBehavior)
							.getEventDefinitions();
					Iterator<EventDefinition> eventDefIter = eventDefinitions
							.iterator();
					while (eventDefIter.hasNext()) {
						EventDefinition eventDefinition = eventDefIter.next();
						if (eventDefinition instanceof TimerEventDefinition) {
							Object dateObj = ((TimerEventDefinition) eventDefinition)
									.getTimeDate().getValue(null);
							String expressionText = ((TimerEventDefinition) eventDefinition)
									.getTimeCycle().getExpressionText();
							Trigger trigger = null;
							if (dateObj == null
									&& StringUtils.isBlank(expressionText)) {
								throw new FoxBPMException("自动启动流程实例，启动时间表达式为空！");
							} else if (dateObj == null
									&& StringUtils.isNotBlank(expressionText)) {
								// CRON表达式启动
								trigger = TriggerBuilder
										.newTrigger()
										.withSchedule(
												CronScheduleBuilder
														.cronSchedule(expressionText))
										.withIdentity(GuidUtil.CreateGuid(),
												processDefinitionID).build();
							} else if (dateObj != null
									&& StringUtils.isBlank(expressionText)) {
								// Date 启动
								if (dateObj instanceof Date) {
									Date date = (Date) dateObj;
									trigger = TriggerBuilder
											.newTrigger()
											.withIdentity(
													GuidUtil.CreateGuid(),
													processDefinitionID)
											.startAt(date).build();
								} else {
									throw new FoxBPMException(
											"自动启动流程实例，启动时间表达式有错误！");
								}
							}
							String jobName = GuidUtil.CreateGuid();
							FoxbpmJobDetail<ProcessIntanceAutoStartJob> jobDetail = new FoxbpmJobDetail<ProcessIntanceAutoStartJob>(
									new ProcessIntanceAutoStartJob(), jobName,
									processDefinitionID, trigger);

							// 根据配置文件创建trigger
							// 创建启动job
							JobDataMap jobDataMap = jobDetail.getJobDataMap();
							jobDataMap
									.put(FoxbpmJobExecutionContext.PROCESS_DEFINITION_ID,
											processDefinitionID);
							jobDataMap.put(
									FoxbpmJobExecutionContext.BUSINESS_KEY, "");
							jobDataMap
									.put(FoxbpmJobExecutionContext.PROCESS_DEFINITION_KEY,
											processDefinition.getKey());
							jobDataMap
									.put(FoxbpmJobExecutionContext.PROCESS_DEFINITION_NAME,
											processDefinition.getName());
							jobDataMap.put(FoxbpmJobExecutionContext.FLOW_NODE,
									kernelFlowNodeImpl);
							try {
								FoxbpmScheduler foxbpmScheduler = ProcessEngineManagement
										.getDefaultProcessEngine()
										.getProcessEngineConfiguration()
										.getFoxbpmScheduler();
								foxbpmScheduler.scheduleFoxbpmJob(jobDetail);
							} catch (SchedulerException e) {
								throw new FoxBPMException("调度流程自动启动JOB出现问题！");
							}
						}
					}
				}
			}
		}
		return processDefinitionID;
	}
}
