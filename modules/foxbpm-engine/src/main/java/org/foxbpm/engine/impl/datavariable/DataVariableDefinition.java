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
package org.foxbpm.engine.impl.datavariable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.foxbpm.engine.expression.Expression;
import org.foxbpm.engine.impl.expression.ExpressionImpl;


public class DataVariableDefinition implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	protected String id;

	/**
	 * 数据类型
	 */
	protected String dataType;

	/**
	 * 是否列表，暂时没用
	 */
	protected boolean isList;

	/**
	 * 是否持久化
	 */
	protected boolean isPersistence;

	/**
	 * 表达式
	 */
	protected Expression expression;

	/**
	 * 中文描述
	 */
	protected String documentation;
	
	/**
	 * 节点编号
	 */
	protected String nodeId;
	
	/**
	 * 业务类型
	 */
	protected String bizType;

	/**
	 * 是否公有（暂时没用）
	 */
	protected boolean isPubilc;

	public DataVariableDefinition() {


	}
	
	public Map<String,Object> getPersistentState(){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("id", id);
		result.put("dataType", dataType);
		result.put("isList", isList);
		result.put("isPersistence", isPersistence);
		result.put("expressionText", expression.getExpressionText());
		result.put("documentation", documentation);
		result.put("nodeId", nodeId);
		result.put("bizType", bizType);
		result.put("isPubilc", isPubilc);
		return result;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public boolean isList() {
		return isList;
	}

	public void setList(boolean isList) {
		this.isList = isList;
	}

	public boolean isPersistence() {
		return isPersistence;
	}

	public void setPersistence(boolean isPersistence) {
		this.isPersistence = isPersistence;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = new ExpressionImpl(expression);
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}
	
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public boolean isPubilc() {
		return isPubilc;
	}

	public void setPubilc(boolean isPubilc) {
		this.isPubilc = isPubilc;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	
}
