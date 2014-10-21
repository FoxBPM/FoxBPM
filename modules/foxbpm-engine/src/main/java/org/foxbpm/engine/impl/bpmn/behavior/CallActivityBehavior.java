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

import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.expression.Expression;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.expression.ExpressionImpl;
import org.foxbpm.engine.impl.expression.ExpressionMgmt;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

public class CallActivityBehavior extends ActivityBehavior {

	private static final long serialVersionUID = 1L;

	protected boolean isAsync = false;

	protected Expression callableElementId;

	protected Expression callableElementVersion;

	protected Expression callableElementBizKey;

	protected DataSourceToSubProcessMapping dataSourceToSubProcessMapping;

	protected SubProcessToDataSourceMapping subProcessToDataSourceMapping;

	 
	public void execute(FlowNodeExecutionContext executionContext) {

		// 创建子流程

		createSubProcess(executionContext);
		// 如果为异步子流程则创建子流程完毕后直接
		// 执行离开事件
		if (isAsync()) {

			super.execute(executionContext);

		}

	}

	private ProcessInstanceEntity createSubProcess(FlowNodeExecutionContext executionContext) {
		String flowId = StringUtil.getString(getCallableElementId() != null ? getCallableElementId().getValue(executionContext) : null);

		String flowVersion = StringUtil.getString(getCallableElementVersion() != null ? getCallableElementVersion().getValue(executionContext): null);
		int version = StringUtil.getInt(flowVersion);

		String bizKey = StringUtil.getString(getCallableElementBizKey() != null ? getCallableElementBizKey().getValue(executionContext)
				: null);

		ProcessDefinitionEntity processDefinition = Context.getProcessEngineConfiguration().getDeploymentManager()
				.findDeployedProcessDefinitionByKeyAndVersion(flowId, version);

		ProcessInstanceEntity createSubProcessInstance = (ProcessInstanceEntity) executionContext
				.createSubProcessInstance(processDefinition);

		createSubProcessInstance.setBizKey(bizKey);

		/**  */
		createSubProcessInstance.setStartAuthor(Authentication.getAuthenticatedUserId());

		/** 映射数据变量 */
		DataSourceToSubProcessMapping dataSourceToSubProcessMapping = getDataSourceToSubProcessMapping();
		if (dataSourceToSubProcessMapping != null) {
			for (DataVariableMapping dataVariableMapping : dataSourceToSubProcessMapping.getDataVariableMappings()) {
				String dataSourceId = "${" + dataVariableMapping.getDataSourceId() + "}";
				createSubProcessInstance.setVariable(dataVariableMapping.getSubProcesId(),
						ExpressionMgmt.execute(dataSourceId, executionContext));
			}

		}

		try {
			// 启动流程实例
			createSubProcessInstance.start();
		} catch (Exception e) {
			throw new FoxBPMException("子流程 " + this.getName() + " 启动异常!", e);
		}

		return createSubProcessInstance;

	}

	public boolean isAsync() {
		return isAsync;
	}

	public void setAsync(boolean isAsync) {
		this.isAsync = isAsync;
	}

	public Expression getCallableElementId() {
		return callableElementId;
	}

	public void setCallableElementId(String callableElementId) {
		this.callableElementId = new ExpressionImpl(callableElementId);
	}

	public Expression getCallableElementVersion() {
		return callableElementVersion;
	}

	public void setCallableElementVersion(String callableElementVersion) {
		this.callableElementVersion = new ExpressionImpl(callableElementVersion);
	}

	public Expression getCallableElementBizKey() {
		return callableElementBizKey;
	}

	public void setCallableElementBizKey(String callableElementBizKey) {
		this.callableElementBizKey = new ExpressionImpl(callableElementBizKey);
	}

	public DataSourceToSubProcessMapping getDataSourceToSubProcessMapping() {
		return dataSourceToSubProcessMapping;
	}

	public void setDataSourceToSubProcessMapping(DataSourceToSubProcessMapping dataSourceToSubProcessMapping) {
		this.dataSourceToSubProcessMapping = dataSourceToSubProcessMapping;
	}

	public SubProcessToDataSourceMapping getSubProcessToDataSourceMapping() {
		return subProcessToDataSourceMapping;
	}

	public void setSubProcessToDataSourceMapping(SubProcessToDataSourceMapping subProcessToDataSourceMapping) {
		this.subProcessToDataSourceMapping = subProcessToDataSourceMapping;
	}

}
