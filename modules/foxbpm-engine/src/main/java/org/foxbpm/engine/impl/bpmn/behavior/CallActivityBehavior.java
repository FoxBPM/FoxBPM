/**
 * Copyright 1996-2014 FoxBPM Co.,Ltd.
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
package org.foxbpm.engine.impl.bpmn.behavior;

import java.util.List;

import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.expression.ExpressionImpl;
import org.foxbpm.engine.impl.expression.ExpressionMgmt;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.util.ExceptionUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
import org.foxbpm.model.CallActivity;
import org.foxbpm.model.VariableMapping;

public class CallActivityBehavior extends ActivityBehavior {

	private static final long serialVersionUID = 1L;

	public void execute(FlowNodeExecutionContext executionContext) {
		CallActivity callActivity = (CallActivity)baseElement;
		// 创建子流程

		createSubProcess(executionContext);
		// 如果为异步子流程则创建子流程完毕后直接
		// 执行离开事件
		if (callActivity.isAsync()) {
			super.execute(executionContext);
		}
	}

	private ProcessInstanceEntity createSubProcess(FlowNodeExecutionContext executionContext) {
		CallActivity callActivity = (CallActivity)baseElement;
		String flowId = callActivity.getCallableElementId();
		String flowVersion = callActivity.getCallableElementVersion();
		String processKey = null;
		try{
			processKey = StringUtil.getString(new ExpressionImpl(flowId).getValue(executionContext));
		}catch(Exception ex){
			throw ExceptionUtil.getException("10404014",ex,this.getFlowNode().getId());
		}
		
		if(StringUtil.isEmpty(processKey)){
			throw ExceptionUtil.getException("10401001",this.getFlowNode().getId());
		}
		
		int version;
		try{
			version = StringUtil.getInt(new ExpressionImpl(flowVersion).getValue(executionContext));
		}catch(NumberFormatException ex){
			throw ExceptionUtil.getException("10404015",ex,this.getFlowNode().getId());
		}catch(Exception ex){
			throw ExceptionUtil.getException("10404016",ex,this.getFlowNode().getId());
		}
		
		String bizKey = null;
		try{
			bizKey = StringUtil.getString(new ExpressionImpl(callActivity.getBizKey()).getValue(executionContext));
		}catch(Exception ex){
			throw ExceptionUtil.getException("10404017",ex,this.getFlowNode().getId());
		}
				
		ProcessDefinitionEntity processDefinition = Context.getProcessEngineConfiguration().getDeploymentManager()
				.findDeployedProcessDefinitionByKeyAndVersion(processKey, version);
		if(processDefinition == null){
			throw ExceptionUtil.getException("10402001",this.getFlowNode().getId(), processKey,String.valueOf(version));
		}
		ProcessInstanceEntity createSubProcessInstance = (ProcessInstanceEntity) executionContext
				.createSubProcessInstance(processDefinition);
		createSubProcessInstance.setBizKey(bizKey);

		/**  */
		createSubProcessInstance.setStartAuthor(Authentication.getAuthenticatedUserId());

		/** 映射数据变量 */
		List<VariableMapping> dataSourceToSubProcessMapping = callActivity.getToSubProcessMapping();
		if(dataSourceToSubProcessMapping != null){
			for (VariableMapping dataVariableMapping : dataSourceToSubProcessMapping) {
				String dataSourceId = "${" + dataVariableMapping.getFormId() + "}";
				
				Object value = null;
				try{
					value = ExpressionMgmt.execute(dataSourceId, executionContext);
				}catch(Exception ex){
					throw ExceptionUtil.getException("10404018",dataSourceId,this.getFlowNode().getId());
				}
				createSubProcessInstance.setVariable(dataVariableMapping.getToId(),value);
			}
		}
		// 启动流程实例
		createSubProcessInstance.start();
		return createSubProcessInstance;

	}
}
