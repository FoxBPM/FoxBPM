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
package org.foxbpm.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 连接器
 * 
 * @author ych
 * 
 */
public class Connector extends BaseElement {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 包名
	 */
	protected String packageName;
	
	/**
	 * 类名
	 */
	protected String className;
	
	/**
	 * 连接器在流程定义中使用时的唯一编号
	 */
	protected String connectorInstanceId;
	
	/**
	 * 连接器在流程定义中使用时的名称
	 */
	protected String connectorInstanceName;
	
	/**
	 * 连接器的触发事件
	 */
	protected String eventType;
	
	/**
	 * 连接器在流程定义中使用时的描述
	 */
	protected String documentation;
	
	/**
	 * 连接器异常处理类全名
	 */
	protected String errorHandling;
	
	/**
	 * 连接器异常处理机制
	 */
	protected String errorCode;
	
	/**
	 * 连接器输入参数集合
	 */
	protected List<InputParam> inputsParam = new ArrayList<InputParam>();
	
	/**
	 * 连接器输出参数集合
	 */
	protected List<OutputParam> outputsParam = new ArrayList<OutputParam>();
	/**
	 * 连接器输出参数定义集合
	 */
	protected List<OutputParamDef> outputsParamDef = new ArrayList<OutputParamDef>();
	
	/**
	 * 连接器定时器
	 */
	protected TimerEventDefinition timerEventDefinition;
	
	/**
	 * 定时器跳过策略
	 */
	protected String timerSkipExpression;
	
	/**
	 * 连接器跳过策略
	 */
	protected String skipExpression;
	/**
	 * 连接器跳过策略
	 */
	protected String skipComment;
	/**
	 * 时间执行
	 */
	protected String isTimeExecute;
	/**
	 * 类型
	 */
	protected String type;
	public String getPackageName() {
		return packageName;
	}
	
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getConnectorInstanceId() {
		return connectorInstanceId;
	}
	
	public void setConnectorInstanceId(String connectorInstanceId) {
		this.connectorInstanceId = connectorInstanceId;
	}
	
	public String getConnectorInstanceName() {
		return connectorInstanceName;
	}
	
	public void setConnectorInstanceName(String connectorInstanceName) {
		this.connectorInstanceName = connectorInstanceName;
	}
	
	public String getEventType() {
		return eventType;
	}
	
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
	public String getDocumentation() {
		return documentation;
	}
	
	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}
	
	public String getErrorHandling() {
		return errorHandling;
	}
	
	public void setErrorHandling(String errorHandling) {
		this.errorHandling = errorHandling;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public List<InputParam> getInputsParam() {
		return inputsParam;
	}
	
	public void setInputsParam(List<InputParam> inputsParam) {
		this.inputsParam = inputsParam;
	}
	
	public List<OutputParam> getOutputsParam() {
		return outputsParam;
	}
	
	public void setOutputsParam(List<OutputParam> outputsParam) {
		this.outputsParam = outputsParam;
	}
	
	public TimerEventDefinition getTimerEventDefinition() {
		return timerEventDefinition;
	}
	
	public void setTimerEventDefinition(TimerEventDefinition timerEventDefinition) {
		this.timerEventDefinition = timerEventDefinition;
	}
	
	public String getSkipExpression() {
		return skipExpression;
	}
	
	public void setSkipExpression(String skipExpression) {
		this.skipExpression = skipExpression;
	}
	
	public String getIsTimeExecute() {
		return isTimeExecute;
	}
	
	public void setIsTimeExecute(String isTimeExecute) {
		this.isTimeExecute = isTimeExecute;
	}
	
	public String getTimerSkipExpression() {
		return timerSkipExpression;
	}
	
	public void setTimerSkipExpression(String timerSkipExpression) {
		this.timerSkipExpression = timerSkipExpression;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getSkipComment() {
		return skipComment;
	}
	
	public void setSkipComment(String skipComment) {
		this.skipComment = skipComment;
	}
	
	public List<OutputParamDef> getOutputsParamDef() {
		return outputsParamDef;
	}
	
	public void setOutputsParamDef(List<OutputParamDef> outputsParamDef) {
		this.outputsParamDef = outputsParamDef;
	}
}
