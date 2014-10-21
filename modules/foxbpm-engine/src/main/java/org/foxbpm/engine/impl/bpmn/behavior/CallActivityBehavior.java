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

import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.exception.FoxBPMIllegalArgumentException;
import org.foxbpm.engine.exception.FoxBPMObjectNotFoundException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.expression.ExpressionImpl;
import org.foxbpm.engine.impl.expression.ExpressionMgmt;
import org.foxbpm.engine.impl.identity.Authentication;
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
		
		if(StringUtil.isEmpty(flowId)){
			throw new FoxBPMIllegalArgumentException("共有子流程的key不能为空！");
		}
		if(StringUtil.isEmpty(flowVersion)){
			throw new FoxBPMIllegalArgumentException("共有子流程的version不能为空！");
		}
		
		String processKey = StringUtil.getString(new ExpressionImpl(flowId).getValue(executionContext));
		int version = StringUtil.getInt(new ExpressionImpl(flowVersion).getValue(executionContext));
		String bizKey = StringUtil.getString(new ExpressionImpl(callActivity.getBizKey()).getValue(executionContext));
		ProcessDefinitionEntity processDefinition = Context.getProcessEngineConfiguration().getDeploymentManager()
				.findDeployedProcessDefinitionByKeyAndVersion(processKey, version);
		if(processDefinition == null){
			throw new FoxBPMObjectNotFoundException("公有有子流程key:"+processKey+",version:"+version+" 定义不存在，请检查配置！");
		}
		ProcessInstanceEntity createSubProcessInstance = (ProcessInstanceEntity) executionContext
				.createSubProcessInstance(processDefinition);
		createSubProcessInstance.setBizKey(bizKey);

		/**  */
		createSubProcessInstance.setStartAuthor(Authentication.getAuthenticatedUserId());

		/** 映射数据变量 */
		List<VariableMapping> dataSourceToSubProcessMapping = callActivity.getToSubProcessMapping();
		for (VariableMapping dataVariableMapping : dataSourceToSubProcessMapping) {
			String dataSourceId = "${" + dataVariableMapping.getFormId() + "}";
			createSubProcessInstance.setVariable(dataVariableMapping.getToId(),
					ExpressionMgmt.execute(dataSourceId, executionContext));
		}
		try {
			// 启动流程实例
			createSubProcessInstance.start();
		} catch (Exception e) {
			throw new FoxBPMException("子流程 " + this.getName() + " 启动异常!", e);
		}

		return createSubProcessInstance;

	}
}
