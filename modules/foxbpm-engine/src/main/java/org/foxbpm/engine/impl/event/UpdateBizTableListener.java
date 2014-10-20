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
 * @author ych
 */
package org.foxbpm.engine.impl.event;

import org.foxbpm.engine.impl.db.SqlCommand;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.expression.ExpressionImpl;
import org.foxbpm.engine.impl.util.DBUtils;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.kernel.event.KernelListener;
import org.foxbpm.kernel.runtime.ListenerExecutionContext;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;
import org.foxbpm.model.DataVariableDefinition;

/**
 * <p>更新业务字段监听</p>
 * <p>该监听会先判断是否存在表名(_BizName)和关联字段(_BizKeyField)的变量,如果存在则会更新该业务表的_processInstanceId,_processInstanceStatus,_processStep的字段。</p>
 * <p>使用条件：</p>
 * <p>	1.流程定义中存在_BizName和_BizKeyField变量，并赋值正确，该变量只能为固定的string，不能有上下文的计算。</p>
 * <p>  2._BizName的业务表中存在_processInstanceId(流程实例号)、_processInstanceStatus(流程当前状态)、_processStep(当前处理人信息)字段</p>
 * @author ych
 * @version 1.0
 */
public class UpdateBizTableListener implements KernelListener {

	private static final long serialVersionUID = 1L;

	public void notify(ListenerExecutionContext executionContext) throws Exception {
		KernelTokenImpl kernelTokenImpl = (KernelTokenImpl) executionContext;
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity)kernelTokenImpl.getProcessDefinition();
		DataVariableDefinition bizName = processDefinition.getDataVariableMgmtDefinition().getProcessDataVariableDefinition("_BizName");
		DataVariableDefinition bizKeyField = processDefinition.getDataVariableMgmtDefinition().getProcessDataVariableDefinition("_BizKeyField");
		if(bizName != null && bizKeyField != null){
			String bizNameString = StringUtil.getString(new ExpressionImpl(bizName.getExpression()).getValue(null));
			String bizKeyFiledString = StringUtil.getString(new ExpressionImpl(bizKeyField.getExpression()).getValue(null));
			String sql = "update "+bizNameString + " set processInstanceId = ?,processInstanceStatus = ?, processStep = ? "
					+ "where " + bizKeyFiledString + "=?";
			ProcessInstanceEntity processInstanceEntity = (ProcessInstanceEntity)kernelTokenImpl.getProcessInstance();
			Object []params = new Object[]{
					processInstanceEntity.getId(),
					processInstanceEntity.getInstanceStatus(),
					processInstanceEntity.getProcessLocation(),
					processInstanceEntity.getBizKey()
			};
			SqlCommand sqlCommand = new SqlCommand(DBUtils.getConnection());
			sqlCommand.execute(sql, params);
		}
	}
	
	

}
