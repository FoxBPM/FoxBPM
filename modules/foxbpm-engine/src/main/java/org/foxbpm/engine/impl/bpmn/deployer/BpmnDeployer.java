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

import java.io.ByteArrayInputStream;
import java.util.List;

import org.foxbpm.engine.Constant;
import org.foxbpm.engine.exception.FoxBPMBizException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.bpmn.behavior.EventDefinition;
import org.foxbpm.engine.impl.bpmn.behavior.StartEventBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.TimerEventBehavior;
import org.foxbpm.engine.impl.entity.DeploymentEntity;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.entity.ResourceEntity;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.persistence.DeploymentEntityManager;
import org.foxbpm.engine.impl.persistence.ProcessDefinitionManager;
import org.foxbpm.engine.impl.task.TaskDefinition;
import org.foxbpm.engine.impl.util.GuidUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.kernel.behavior.KernelFlowNodeBehavior;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bpmn发布处理类
 * 
 * @author yangguangftlp
 * @date 2014年7月17日
 */
public class BpmnDeployer extends AbstractDeployer {
	/** 日志处理 */
	private final static Logger LOG = LoggerFactory.getLogger(BpmnDeployer.class);
	/** 版本号标记 */
	private final static String VERSION_FLAG = ":";
	
	public String deploy(DeploymentEntity deployment) {
		LOG.debug("start deploy");
		ResourceEntity resourceBpmnNew = null;
		// 获取资源实例
		for (ResourceEntity resourceEntity : deployment.getResources().values()) {
			if (resourceEntity.getName().toLowerCase().endsWith(BPMN_RESOURCE_SUFFIX)) {
				resourceBpmnNew = resourceEntity;
				break;
			}
		}
		// bpmn图不存在
		if (null == resourceBpmnNew) {
			throw new FoxBPMBizException("发布包中必须存在bpmn文件");
		}
		
		// 获取命令上下文
		CommandContext context = Context.getCommandContext();
		// 获取流程定义管理
		ProcessDefinitionManager processDefinitionManager = context.getProcessDefinitionManager();
		// 根据bpmn文件生成流程定义实例
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) processModelParseHandler.createProcessDefinition("dddd", new ByteArrayInputStream(resourceBpmnNew.getBytes()));
		String processDefineKey = processDefinitionEntity.getKey();
		// 获取发布Id
		String deploymentId = deployment.getId();
		
		// 处理新发布或者更新 isNew控制更新和添加
		if (deployment.isNew()) {
			// 获取发布实例管理器
			DeploymentEntityManager deploymentEntityManager = context.getDeploymentEntityManager();
			// 获取更新发布Id
			String updateDeploymentId = deployment.getUpdateDeploymentId();
			// 设置资源信息
			processDefinitionEntity.setResourceName(resourceBpmnNew.getName());
			processDefinitionEntity.setResourceId(resourceBpmnNew.getId());
			processDefinitionEntity.setDeploymentTime(deployment.getDeploymentTime());
			
			// 启动表单
			String formUri = getStartFormUri(processDefinitionEntity);
			// 处理更新发布
			if (StringUtil.isNotEmpty(updateDeploymentId)) {
				// 从sql缓存获取已存在的发布实例
				DeploymentEntity deploymentOld = deploymentEntityManager.findDeploymentById(updateDeploymentId);
				
				if (null == deploymentOld) {
					throw new FoxBPMBizException("无效的更新发布号updateDeploymentId：" + updateDeploymentId);
				}
				
				ResourceEntity resourceBpmnOld = null;
				
				for (ResourceEntity resourceEntity : deploymentOld.getResources().values()) {
					if (resourceEntity.getName().toLowerCase().endsWith(BPMN_RESOURCE_SUFFIX)) {
						resourceBpmnOld = resourceEntity;
						break;
					}
				}
				// 如果不存在更新情况,可能发布的时候
				if (null == resourceBpmnOld) {
					Context.getCommandContext().getResourceManager().insert(resourceBpmnNew);
				} else {
					resourceBpmnOld.setBytes(resourceBpmnNew.getBytes());
					// 需要显示调用更新,主要更新bpmn资源
					deploymentEntityManager.update(resourceBpmnOld);
				}
				// 从sql缓存中获取流程定义实例,该操作会自动更新数据库
				ProcessDefinitionEntity processEntityNew = processDefinitionManager.findProcessDefinitionByDeploymentAndKey(updateDeploymentId, processDefineKey);
				if (null == processEntityNew) {
					LOG.error("无效的更新发布号updateDeploymentId：" + updateDeploymentId);
					throw new FoxBPMBizException("无效的更新发布号updateDeploymentId：" + updateDeploymentId);
				}
				processEntityNew.setCategory(processDefinitionEntity.getCategory());
				processEntityNew.setName(processDefinitionEntity.getName());
				processEntityNew.setResourceName(processDefinitionEntity.getResourceName());
				processEntityNew.setStartFormUri(formUri);
				// 获取流程定义Id
				processDefinitionEntity.setId(processEntityNew.getId());
				processDefinitionEntity.setDeploymentId(deploymentId);
			} else {// 新增
				int version = 1;
				// 如果外部传了version，则优先处理
				if (resourceBpmnNew.getVersion() != -1) {
					version = resourceBpmnNew.getVersion();
				} else { // 没有version,则取数据库中的最大version+1,数据库中也不存在时，则version=1
					ProcessDefinitionEntity latestProcessDefinition = processDefinitionManager.findLatestProcessDefinitionByKey(processDefineKey);
					if (null != latestProcessDefinition) {
						version = latestProcessDefinition.getVersion() + 1;
					}
				}
				// 生成流程定义Id
				String processDefinitionId = new StringBuffer(processDefineKey).append(VERSION_FLAG).append(version).append(VERSION_FLAG).append(GuidUtil.CreateGuid()).toString();
				processDefinitionEntity.setId(processDefinitionId);
				processDefinitionEntity.setStartFormUri(formUri);
				processDefinitionEntity.setDeploymentId(deploymentId);
				processDefinitionEntity.setVersion(version);
				
				/********************* 数据库操作 *****************************/
				// 将bpmn资源实例入库
				deploymentEntityManager.insertResource(resourceBpmnNew);
				// 将流程定义实例添入库
				processDefinitionManager.insert(processDefinitionEntity);
			}
			// 添加自动调度，启动流程实例
			addAutoStartProcessInstanceJob(processDefinitionEntity);
		} else {
			// 不需要处理数据库,这里主要将重新封装流程定义实例包括两部分(bpmn模型,资源信息)
			ProcessDefinitionEntity processEntityNew = processDefinitionManager.findProcessDefinitionByDeploymentAndKey(deploymentId, processDefineKey);
			processDefinitionEntity.setDeploymentId(deploymentId);
			processDefinitionEntity.setId(processEntityNew.getId());
			processDefinitionEntity.setVersion(processEntityNew.getVersion());
			processDefinitionEntity.setResourceId(processEntityNew.getResourceId());
			processDefinitionEntity.setResourceName(processEntityNew.getResourceName());
			processDefinitionEntity.setDiagramResourceName(processEntityNew.getDiagramResourceName());
			processDefinitionEntity.setStartFormUri(processEntityNew.getStartFormUri());
			processDefinitionEntity.setDeploymentTime(processEntityNew.getDeploymentTime());
		}
		
		// 提供给其他发布器使用
		deployment.addProperty(Constant.PROCESS_DEFINE_ID, processDefinitionEntity.getId());
		// 将封装的流程定义实例添加到缓存
		Context.getProcessEngineConfiguration().getDeploymentManager().getProcessDefinitionCache().add(processDefinitionEntity.getId(), processDefinitionEntity);
		LOG.debug("end deploy");
		return processDefinitionEntity.getId();
	}
	
	/**
	 * 部署时候自动调度流程实例启动任务
	 * 
	 * @param processDefinition
	 */
	private void addAutoStartProcessInstanceJob(ProcessDefinitionEntity processDefinition) {
		
		List<KernelFlowNodeImpl> flowNodes = processDefinition.getFlowNodes();
		KernelFlowNodeBehavior kernelFlowNodeBehavior = null;
		List<EventDefinition> eventDefinitions = null;
		
		for (KernelFlowNodeImpl kernelFlowNodeImpl : flowNodes) {
			kernelFlowNodeBehavior = kernelFlowNodeImpl.getKernelFlowNodeBehavior();
			// 获取START EVENT节点
			if (kernelFlowNodeBehavior instanceof StartEventBehavior) {
				eventDefinitions = ((StartEventBehavior) kernelFlowNodeBehavior).getEventDefinitions();
				for (EventDefinition eventDefinition : eventDefinitions) {
					// 如果开始节点存在自动启动属性，那么就调度或者刷新 工作任务
					if (eventDefinition instanceof TimerEventBehavior) {
						Object[] params = new String[]{processDefinition.getId(), processDefinition.getKey(), processDefinition.getName(), kernelFlowNodeImpl.getId()};
						eventDefinition.execute(null, TimerEventBehavior.EVENT_TYPE_START, params);
					}
				}
			}
		}
	}
	
	/**
	 * 获取发起节点表单
	 * <p>
	 * 顺序逻辑：startEvent后面人工节点的节点操作表单->流程定义默认表单
	 * </p>
	 * 
	 * @param processDefinitionEntity
	 * @return
	 */
	private String getStartFormUri(ProcessDefinitionEntity processDefinitionEntity) {
		String formUri = null;
		TaskDefinition taskDefinition = processDefinitionEntity.getSubTaskDefinition();
		if (taskDefinition != null) {
			formUri = StringUtil.getString(taskDefinition.getFormUri().getValue(null));
		}
		if (StringUtil.isNotEmpty(formUri)) {
			return formUri;
		}
		formUri = StringUtil.getString(processDefinitionEntity.getFormUri().getValue(null));
		return formUri;
	}
	
}
