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
package org.foxbpm.engine.scriptlanguage;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.foxbpm.engine.datavariable.VariableQuery;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.datavariable.DataVariableDefinition;
import org.foxbpm.engine.impl.datavariable.VariableQueryImpl;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.entity.VariableInstanceEntity;
import org.foxbpm.engine.impl.expression.ExpressionMgmt;
import org.foxbpm.engine.impl.mgmt.DataVariableMgmtInstance;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

public abstract class AbstractScriptLanguageMgmt {

	private static String __REGEX_SIGNS = "\\$\\{[^}{]+\\}";

	/**
	 * 执行表达式
	 * 
	 * @param scriptText
	 *            表达式文本
	 * @return
	 */
	public abstract Object execute(String scriptText);

	/**
	 * 执行表达式,对于没有流程上下文环境的时候,执行表达式的时候需要传入流程定义。
	 * 
	 * @param scriptText
	 *            表达式文本
	 * @param processDefinition
	 *            流程定义
	 * @return
	 */
	public abstract Object execute(String scriptText, ProcessDefinitionEntity processDefinition);

	/**
	 * 向表达式环境中放入变量
	 * 
	 * @param variableName
	 *            变量名称
	 * @param variableObj
	 *            变量值
	 */
	public abstract void setVariable(String variableName, Object variableObj);

	/**
	 * 向表达式环境中放入变量,这个变量将是流程变量${var}
	 * 
	 * @param variableName
	 *            变量名称
	 * @param variableObj
	 *            变量值
	 * @param executionContext
	 *            流程上下文
	 */
	public abstract void setVariable(String variableName, Object variableObj, FlowNodeExecutionContext executionContext);

	/**
	 * 获取变量值
	 * 
	 * @param variableName
	 *            变量名称
	 * @return
	 */
	public abstract Object getVariable(String variableName);

	/**
	 * 执行表达式
	 * 
	 * @param scriptText
	 *            表达式字符串
	 * @param executionContext
	 *            流程上下文
	 * @return
	 */
	public abstract Object execute(String scriptText, FlowNodeExecutionContext executionContext);

	/**
	 * 脚本管理器初始化方法
	 */
	public abstract AbstractScriptLanguageMgmt init();

	public abstract void close();

	public static List<String> getDataVariableList(String scriptText) {
		String inexp = scriptText;
		// ${test} afdfs ${test1}erewr ${test3}
		String regex = "\\$\\{[^}{]+\\}";
		Pattern regexExpType = Pattern.compile(regex);
		Matcher mType = regexExpType.matcher(inexp);
		String expType = inexp;
		List<String> list = new ArrayList<String>();
		while (mType.find()) {
			expType = mType.group();
			expType = expType.substring(2, expType.length() - 1);
			list.add(expType);
		}
		return list;
	}

	public static String getExpressionAll(String inexp) {
		String str = null;
		String regex = __REGEX_SIGNS;
		Pattern regexExpType = Pattern.compile(regex);
		Matcher mType = regexExpType.matcher(inexp);
		String expType = inexp;
		StringBuffer sb = new StringBuffer();
		while (mType.find()) {
			expType = mType.group();
			String dist = expType.substring(2, expType.length() - 1); // StringUtil.getString(getExpressionValue(dataView,expType));
			mType.appendReplacement(sb, dist);
		}
		mType.appendTail(sb);
		str = sb.toString();
		return str;
	}

	protected void dataVariableCalculate(String scriptText, FlowNodeExecutionContext executionContext) {

		ProcessInstanceEntity processInstance = (ProcessInstanceEntity) executionContext.getProcessInstance();
		List<String> dataVariableList = getDataVariableList(scriptText);
		if(processInstance == null){
			if(!dataVariableList.isEmpty()){
				throw new FoxBPMException("没有流程实例上下文，不能解释带变量的表达式："+scriptText);
			}
			return;
		}
		DataVariableMgmtInstance dataVariableMgmtInstance = processInstance.getDataVariableMgmtInstance();
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) processInstance.getProcessDefinition();

		for (String expressionId : dataVariableList) {
			if (dataVariableMgmtInstance.getDataVariableByExpressionId(expressionId) == null) {

				DataVariableDefinition dataVariableDefinition = processDefinition.getDataVariableMgmtDefinition()
						.getProcessDataVariableDefinition(expressionId);

				if (dataVariableDefinition == null) {
					continue;
				}

				if (dataVariableDefinition.isPersistence()) {
					VariableQuery variableQuery = new VariableQueryImpl(Context.getProcessEngineConfiguration().getCommandExecutor());
					variableQuery.addVariableKey(expressionId);
					variableQuery.processInstanceId(processInstance.getId());
					@SuppressWarnings({ "unchecked", "rawtypes" })
					List<VariableInstanceEntity> variableInstances = (List) variableQuery.list();
					if (variableInstances != null && variableInstances.size() == 1) {
						ExpressionMgmt.setVariable(expressionId, variableInstances.get(0).getValueObject());

						// 更新
						Context.getCommandContext().getVariableManager().update(variableInstances.get(0));
						dataVariableMgmtInstance.getDataVariableEntities().add(variableInstances.get(0));
					} else {
						if (variableInstances != null && variableInstances.size() > 1) {

							throw new FoxBPMException("一个流程实例中含有两个相同的key,key(" + expressionId + ") instanceId(" + processInstance.getId()
									+ ")");

						} else {
							VariableInstanceEntity variableInstanceEntity = dataVariableMgmtInstance
									.createDataVariableInstance(dataVariableDefinition);
							Object defaultValue = variableInstanceEntity.getDefaultExpressionValue(executionContext);
							ExpressionMgmt.setVariable(expressionId, defaultValue);

							// 插入
							Context.getCommandContext().getVariableManager().insert(variableInstanceEntity);

						}

					}

				} else {
					Object defaultValue = dataVariableMgmtInstance.createDataVariableInstance(dataVariableDefinition)
							.getDefaultExpressionValue(executionContext);
					ExpressionMgmt.setVariable(expressionId, defaultValue);
				}

			}

		}

	}
}
