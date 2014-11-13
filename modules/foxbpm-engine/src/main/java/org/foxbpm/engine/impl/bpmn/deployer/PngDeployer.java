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
 * @author yangguangftlp
 */
package org.foxbpm.engine.impl.bpmn.deployer;

import org.foxbpm.engine.Constant;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.diagramview.factory.ConcreteProcessDefinitionVOFactory;
import org.foxbpm.engine.impl.entity.DeploymentEntity;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.entity.ResourceEntity;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.persistence.DeploymentEntityManager;
import org.foxbpm.engine.impl.persistence.ProcessDefinitionManager;
import org.foxbpm.engine.impl.persistence.deploy.DeploymentManager;
import org.foxbpm.engine.impl.util.ExceptionUtil;
import org.foxbpm.engine.impl.util.SVGConverterUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * png图片发布处理类
 * 
 * @author yangguangftlp
 * @date 2014年7月17日
 */
public class PngDeployer extends AbstractDeployer {
	/** 日志处理 */
	private final static Logger LOG = LoggerFactory.getLogger(PngDeployer.class);
	
	 
	public String deploy(DeploymentEntity deployment) {
		LOG.debug("start deploy PNG");
		// 获取流程定义Id
		String processDefineId = StringUtil.getString(deployment.getProperty(Constant.PROCESS_DEFINE_ID));
		
		// 处理流程定义Id不存在情况
		if (StringUtil.isEmpty(processDefineId)) {
			return null;
		}
		
		// 处理更新和新增
		if (deployment.isNew()) {
			ResourceEntity resourcePngNew = null;
			for (ResourceEntity resourceEntity : deployment.getResources().values()) {
				if (resourceEntity.getName().toLowerCase().endsWith(DIAGRAM_SUFFIXES)) {
					resourcePngNew = resourceEntity;
					break;
				}
			}
			
			// 获取命令上下文
			CommandContext context = Context.getCommandContext();
			// 获取流程定义管理器
			ProcessDefinitionManager processDefinitionManager = context.getProcessDefinitionManager();
			// 获取发布实例管理器
			DeploymentEntityManager deploymentEntityManager = context.getDeploymentEntityManager();
			// 获取发布管理器
			DeploymentManager deploymentManager = Context.getProcessEngineConfiguration().getDeploymentManager();
			// 获取更新发布Id
			String updateDeploymentId = deployment.getUpdateDeploymentId();
			// 从缓存中获取流程定义实例
			ProcessDefinitionEntity processDefinieCacheEntity = (ProcessDefinitionEntity) deploymentManager.getProcessDefinitionCache().get(processDefineId);
			
			// 处理不存在png资源情况,需要生成一个png资源
			if (null == resourcePngNew) {
				// 获取svg内容
				String svgCode = new ConcreteProcessDefinitionVOFactory().createProcessDefinitionVOString(processDefinieCacheEntity);
				// 将获取svg内容存入缓存中
				processDefinieCacheEntity.getProperties().put(Constant.SVG_PROPERTIES_NAME, svgCode);
				// 根据svg生成png资源实例
				resourcePngNew = new ResourceEntity();
				// 设置资源名称 流程定义key+.png
				resourcePngNew.setName(processDefinieCacheEntity.getKey() + DIAGRAM_SUFFIXES);
				// 设置发布Id
				resourcePngNew.setDeploymentId(deployment.getId());
				// svg转成png
				resourcePngNew.setBytes(SVGConverterUtil.getInstance().convertToPng(svgCode));
			}
			
			// 处理更新发布
			if (StringUtil.isNotEmpty(updateDeploymentId)) {
				// 从sql缓存获取已存在的发布实例
				DeploymentEntity deploymentOld = deploymentEntityManager.findDeploymentById(updateDeploymentId);
				if (null == deploymentOld) {
					throw ExceptionUtil.getException("10102002" , updateDeploymentId);
				}
				ResourceEntity resourcePngOld = null;
				for (ResourceEntity resourceEntity : deploymentOld.getResources().values()) {
					if (resourceEntity.getName().toLowerCase().endsWith(DIAGRAM_SUFFIXES)) {
						resourcePngOld = resourceEntity;
						break;
					}
				}
				// 如果不存更新,那么本次视为新增
				if (null == resourcePngOld) {
					deploymentEntityManager.insert(resourcePngNew);
				} else {
					resourcePngOld.setBytes(resourcePngNew.getBytes());
					// 需要显示调用更新,主要更新png资源
					deploymentEntityManager.update(resourcePngOld);
				}
				
			} else {
				/********************* 数据库操作 *****************************/
				// 将png资源插入数据库
				deploymentEntityManager.insertResource(resourcePngNew);
			}
			// 需要更新资源信息
			processDefinieCacheEntity.setDiagramResourceName(resourcePngNew.getName());
			// 这里需要将图资源名称更新至流程定义表
			ProcessDefinitionEntity processDefinieEntity = processDefinitionManager.findProcessDefinitionById(processDefineId);
			processDefinieEntity.setDiagramResourceName(resourcePngNew.getName());
		}
		LOG.debug("end deploy PNG");
		return processDefineId;
	}
}
