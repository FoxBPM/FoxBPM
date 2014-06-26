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

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.exception.FoxBPMBizException;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.bpmn.behavior.EventDefinition;
import org.foxbpm.engine.impl.bpmn.behavior.StartEventBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.TimerEventDefinition;
import org.foxbpm.engine.impl.entity.DeploymentEntity;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.entity.ResourceEntity;
import org.foxbpm.engine.impl.persistence.ProcessDefinitionManager;
import org.foxbpm.engine.impl.schedule.FoxbpmJobDetail;
import org.foxbpm.engine.impl.schedule.FoxbpmJobExecutionContext;
import org.foxbpm.engine.impl.schedule.FoxbpmScheduleJob;
import org.foxbpm.engine.impl.schedule.FoxbpmScheduler;
import org.foxbpm.engine.impl.schedule.quartz.ProcessIntanceAutoStartJob;
import org.foxbpm.engine.impl.util.GuidUtil;
import org.foxbpm.kernel.behavior.KernelFlowNodeBehavior;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BpmnDeployer extends AbstractDeployer {
	Logger log = LoggerFactory.getLogger(BpmnDeployer.class);

	public String deploy(DeploymentEntity deployment) {
		ResourceEntity resourceBpmn = null;
		ResourceEntity resourcePng = null;
		Map<String, ResourceEntity> resources = deployment.getResources();
		for (String resourceName : resources.keySet()) {
			if (resourceName.toLowerCase().endsWith(BPMN_RESOURCE_SUFFIX)) {
				resourceBpmn = resources.get(resourceName);
			}
			if (resourceName.toLowerCase().endsWith(DIAGRAM_SUFFIXES)) {
				resourcePng = resources.get(resourceName);
			}
		}
		if (resourceBpmn == null) {
			throw new FoxBPMBizException("发布文件中不存在.bpmn文件");
		}
		InputStream input = new ByteArrayInputStream(resourceBpmn.getBytes());
		ProcessDefinitionManager processDefinitionManager = Context
				.getCommandContext().getProcessDefinitionManager();
		ProcessDefinitionEntity processEntity = (ProcessDefinitionEntity) processModelParseHandler
				.createProcessDefinition("dddd", input);
		if (deployment.isNew()) {// 需要更新数据库（新发布或更新）
			processEntity.setResourceName(resourceBpmn.getName());
			processEntity.setResourceId(resourceBpmn.getId());
			if (resourcePng != null) {
				processEntity.setDiagramResourceName(resourcePng.getName());
			}

			if (isBlank(deployment.getUpdateDeploymentId())) {
				// 发布
				int processDefinitionVersion = 1;
				// 如果外部传了version，则优先处理
				if (resourceBpmn.getVersion() != -1) {
					processDefinitionVersion = resourceBpmn.getVersion();
				} else { // 没有version,则取数据库中的最大version+1,数据库中也不存在时，则version=1
					ProcessDefinitionEntity latestProcessDefinition = processDefinitionManager
							.findLatestProcessDefinitionByKey(processEntity
									.getKey());
					if (latestProcessDefinition != null)
						processDefinitionVersion = latestProcessDefinition
								.getVersion() + 1;
				}
				// 新的发布号
				processEntity.setDeploymentId(deployment.getId());
				// 新的版本号
				processEntity.setVersion(processDefinitionVersion);
				String processDefinitionId = processEntity.getKey() + ":"
						+ processEntity.getVersion() + ":"
						+ GuidUtil.CreateGuid(); // GUID
				// 新的定义ID
				processEntity.setId(processDefinitionId);
				processDefinitionManager.insert(processEntity);
			} else {
				// 更新
				String deploymentId = deployment.getId();
				ProcessDefinitionEntity processEntityNew = processDefinitionManager
						.findProcessDefinitionByDeploymentAndKey(deploymentId,
								processEntity.getKey());
				processEntityNew.setCategory(processEntity.getCategory());
				processEntityNew.setName(processEntity.getName());
				processEntityNew.setResourceName(processEntity
						.getResourceName());
			}
			// 添加自动调度，启动流程实例
			this.addAutoStartProcessInstanceJob(processEntity);

		} else {// 不需要处理数据库,从数据库中查询出来的实体转换为虚拟机定义，用来进行流程运转
			String deploymentId = deployment.getId();
			ProcessDefinitionEntity processEntityNew = processDefinitionManager
					.findProcessDefinitionByDeploymentAndKey(deploymentId,
							processEntity.getKey());
			processEntity.setDeploymentId(deploymentId);
			processEntity.setId(processEntityNew.getId());
			processEntity.setVersion(processEntityNew.getVersion());
			processEntity.setResourceId(processEntityNew.getResourceId());
			processEntity.setResourceName(processEntityNew.getResourceName());
			processEntity.setDiagramResourceName(processEntityNew
					.getDiagramResourceName());
		}

		Context.getProcessEngineConfiguration().getDeploymentManager()
				.getProcessDefinitionCache()
				.add(processEntity.getId(), processEntity);
		return processEntity.getId();
	}

	/**
	 * 部署时候自动调度流程实例启动任务
	 * 
	 * @param processDefinition
	 */
	private void addAutoStartProcessInstanceJob(
			ProcessDefinitionEntity processDefinition) {
		String processDefinitionID = processDefinition.getId();
		List<KernelFlowNodeImpl> flowNodes = processDefinition.getFlowNodes();
		Iterator<KernelFlowNodeImpl> iterator = flowNodes.iterator();
		while (iterator.hasNext()) {
			KernelFlowNodeImpl kernelFlowNodeImpl = iterator.next();
			KernelFlowNodeBehavior kernelFlowNodeBehavior = kernelFlowNodeImpl
					.getKernelFlowNodeBehavior();
			// 获取START EVENT节点
			if (kernelFlowNodeBehavior instanceof StartEventBehavior) {
				List<EventDefinition> eventDefinitions = ((StartEventBehavior) kernelFlowNodeBehavior)
						.getEventDefinitions();
				Iterator<EventDefinition> eventDefIter = eventDefinitions
						.iterator();
				while (eventDefIter.hasNext()) {
					EventDefinition eventDefinition = eventDefIter.next();
					// 如果开始节点存在自动启动属性，那么就调度或者刷新 工作任务
					if (eventDefinition instanceof TimerEventDefinition) {
						Object startDate = ((TimerEventDefinition) eventDefinition)
								.getTimeDate().getValue(null);
						String cronExpression = ((TimerEventDefinition) eventDefinition)
								.getTimeCycle().getExpressionText();
						String eventID = eventDefinition.getId();
						String groupName = processDefinitionID
								+ eventID
								+ FoxbpmJobExecutionContext.NAME_SUFFIX_JOBGROUP;
						String triggerName = processDefinitionID
								+ eventID
								+ FoxbpmJobExecutionContext.NAME_SUFFIX_JOBTRIGGER;
						String detailName = processDefinitionID
								+ eventID
								+ FoxbpmJobExecutionContext.NAME_SUFFIX_JOBDETAIL;
						// 创建TRIGGER==>>>JOB==>>>>JOBDETAIL
						Trigger trigger = this.createTrigger(startDate,
								cronExpression, triggerName, groupName);
						FoxbpmScheduleJob foxbpmJob = new ProcessIntanceAutoStartJob(
								detailName, groupName, trigger);
						FoxbpmJobDetail<FoxbpmScheduleJob> jobDetail = new FoxbpmJobDetail<FoxbpmScheduleJob>(
								foxbpmJob);

						// 设置调度变量
						JobDataMap jobDataMap = jobDetail.getJobDataMap();
						jobDataMap
								.put(FoxbpmJobExecutionContext.PROCESS_DEFINITION_ID,
										processDefinitionID);
						jobDataMap
								.put(FoxbpmJobExecutionContext.PROCESS_DEFINITION_KEY,
										processDefinition.getKey());
						jobDataMap
								.put(FoxbpmJobExecutionContext.PROCESS_DEFINITION_NAME,
										processDefinition.getName());
						jobDataMap.put(FoxbpmJobExecutionContext.FLOW_NODE,
								kernelFlowNodeImpl);
						// 调度
						this.scheduleFoxbpmJob(
								new JobKey(detailName, groupName), jobDetail);

					}
				}
			}
		}
	}

	/**
	 * 创建两种类型TRIGGER
	 * 
	 * @param startDate
	 * @param cronExpression
	 * @param triggerName
	 * @param groupName
	 * @return trigger
	 */
	private Trigger createTrigger(Object startDate, String cronExpression,
			String triggerName, String groupName) {
		Trigger trigger = null;
		TriggerBuilder<Trigger> withIdentity = newTrigger().withIdentity(
				triggerName, groupName);
		if (startDate == null && isBlank(cronExpression)) {
			throw new FoxBPMException("自动启动流程实例，启动时间表达式为空！");
		} else if (startDate == null && isNotBlank(cronExpression)) {
			// CRON表达式启动
			trigger = withIdentity.withSchedule(cronSchedule(cronExpression))
					.build();
		} else if (startDate != null && isBlank(cronExpression)) {
			// Date 启动
			if (startDate instanceof Date) {
				Date date = (Date) startDate;
				trigger = withIdentity.startAt(date).build();
			} else {
				throw new FoxBPMException("自动启动流程实例，启动时间表达式有错误！");
			}
		}
		return trigger;

	}

	/**
	 * 调度，先清空后调度
	 * 
	 * @param jobKey
	 * @param jobDetail
	 */
	private void scheduleFoxbpmJob(JobKey jobKey, FoxbpmJobDetail<?> jobDetail) {
		try {
			FoxbpmScheduler foxbpmScheduler = ProcessEngineManagement
					.getDefaultProcessEngine().getProcessEngineConfiguration()
					.getFoxbpmScheduler();
			if (foxbpmScheduler == null) {
				throw new FoxBPMException("FOXBPM 自动启动类型的部署时候，调度器没有初始化！");
			}

			// 先清空，后调度
			foxbpmScheduler.deleteJob(jobKey);
			foxbpmScheduler.scheduleFoxbpmJob(jobDetail);
		} catch (SchedulerException e) {
			throw new FoxBPMException("调度  《流程自动启动JOB》时候出现问题！");
		}
	}

}
