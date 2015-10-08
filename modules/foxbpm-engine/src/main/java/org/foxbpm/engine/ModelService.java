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
package org.foxbpm.engine;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.impl.datavariable.BizDataObject;
import org.foxbpm.engine.impl.model.DeploymentBuilderImpl;
import org.foxbpm.engine.repository.Deployment;
import org.foxbpm.engine.repository.DeploymentBuilder;
import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.engine.repository.ProcessDefinitionQuery;

/**
 * 获取所有对流程定义级别操作
 * 
 * @author kenshin
 * 
 */
public interface ModelService {
	
	/**
	 * 获取用户可以发起的流程集合
	 * 
	 * @param userId
	 *            用户编号
	 * @return "processDefinitionId" 流程唯一号;<br>
	 *         "processDefinitionName" 流程名称;<br>
	 *         "processDefinitionKey" 流程定义号;<br>
	 *         "category" 分类;<br>
	 *         "version" 版本号;<br>
	 *         "resourceName", 流程定义资源名称;<br>
	 *         "resourceId" 流程定义资源编号;<br>
	 *         "deploymentId" 资源定义发布号;<br>
	 *         "diagramResourceName" 流程图名称;<br>
	 *         "startFormKey" 启动表单;<br>
	 */
	List<ProcessDefinition> getStartProcessByUserId(String userId);
	
	/**
	 * 发布资源
	 * @param deploymentBuilderImpl
	 * @return
	 */
	Deployment deploy(DeploymentBuilderImpl deploymentBuilderImpl);
	
	void deleteDeployment(String deploymentId);
	
	/**
	 * 创建发布器
	 * @return
	 */
	DeploymentBuilder createDeployment();
	
	/**
	 * 根据发布编号获取发布对象
	 * @param deploymentId 发布编号
	 * @return
	 */
	Deployment getDeploymentById(String deploymentId);
	
	/**
	 * 创建流程定义查询对象
	 * @return
	 */
	ProcessDefinitionQuery createProcessDefinitionQuery();
	
	/**
	 * 获取流程图节点信息
	 * 
	 * @param processDefinitionId
	 *            流程唯一编号
	 * @return key为节点编号,value为 Map<String,Object> Key(height,width,x,y)
	 *         (height="36.0" width="36.0" x="100.0" y="100.0")
	 */
	Map<String, Map<String, Object>> getFlowGraphicsElementPositionById(String processDefinitionId);
	
	/**
	 * 获取流程图节点信息
	 * 
	 * @param processDefinitionKey
	 *            流程定义key
	 * @return key为节点编号,value为 Map<String,Object> Key(height,width,x,y)
	 *         (height="36.0" width="36.0" x="100.0" y="100.0")
	 */
	Map<String, Map<String, Object>> getFlowGraphicsElementPositionByKey(String processDefinitionKey);
	
	/**
	 * 获取流程图图片Stream
	 * 
	 * @param processDefinitionId
	 *            流程唯一编号
	 * @return 图片Stream
	 */
	InputStream GetFlowGraphicsImgStreamByDefId(String processDefinitionId);
	
	/**
	 * 获取流程图图片Stream
	 * 
	 * @param processDefinitionKey
	 *            流程编号
	 * @return 图片Stream
	 */
	InputStream GetFlowGraphicsImgStreamByDefKey(String processDefinitionKey);
	
	/**
	 * 获取流程定义(内置缓存)
	 * 
	 * @param processDefinitionId
	 *            流程唯一编号
	 * @return 获取流程定义 
	 */
	ProcessDefinition getProcessDefinition(String processDefinitionId);
	
	/**
	 * 获取流程定义(内置缓存)
	 * 
	 * @param processKey
	 *            流程key
	 * @param version
	 *            版本号
	 * @return 获取流程定义 
	 */
	ProcessDefinition getProcessDefinition(String processKey, int version);
	
	/**
	 * 根据流程定义ID获取该流程对应的SVG文档字符串
	 * 
	 * @param processDefinitionId
	 *            流程定义ID
	 * @return SVG文档字符串
	 */
	String getProcessDefinitionSVG(String processDefinitionId);
	
	/**
	 * 判断用户是否有权限发起流程 根据流程定义上开始节点后面第一个人工节点的任务分配判断权限
	 * 
	 * @param userId
	 *            用户编号 不能为空
	 * @param processDefinitionId
	 *            流程定义唯一编号
	 * @return
	 */
	boolean verifyStartProcessByUserId(String userId, String processDefinitionId);
	
	/**
	 * 根据发布号和资源名称获取资源流
	 * 
	 * @param deployId
	 * @param resourceName
	 * @return
	 */
	InputStream getResourceByDeployIdAndName(String deployId, String resourceName);
	
	/**
	 * 获取最新的流程定义
	 * 
	 * @param processDefinitionKey
	 *            流程定义
	 * @return 返回流程定义实例
	 */
	ProcessDefinition getLatestProcessDefinition(String processDefinitionKey);
	
	/**
	 * 获取业务系统的抽象关联数据对象
	 * 
	 * @param behaviorId
	 *            行为Id
	 * @param dataSource
	 *            数据源名称(spring配置)
	 */
	List<BizDataObject> getBizDataObject(String behaviorId, String dataSource);
	
	/**
	 * 获取配置的所有的业务对象
	 * @return 
	 * <p>格式：[{id:"dataBase",name:"数据库变量",data:[]},{id:"formData",name:"表单变量",data:[]}]</p>
	 */
	List<Map<String,Object>> getAllBizObjects();
}
