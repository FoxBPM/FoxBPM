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

/**
 * 数据变量定义
 * 
 * @author ych
 * 
 */
public class DataVariable extends BaseElement {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 数据类型
	 */
	protected String dataType;
	
	/**
	 * 字段名称
	 */
	protected String fieldName;
	
	/**
	 * 是否持久化
	 */
	protected boolean isPersistence;
	
	/**
	 * 表达式
	 */
	protected String expression;
	
	/**
	 * 中文描述
	 */
	protected String documentation;
	
	/**
	 * 业务类型
	 */
	protected String bizType;
	
	/**
	 * 是否公有（暂时没用）
	 */
	protected boolean isPubilc;
	
	public String getDataType() {
		return dataType;
	}
	
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public boolean isPersistence() {
		return isPersistence;
	}
	
	public void setPersistence(boolean isPersistence) {
		this.isPersistence = isPersistence;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getExpression() {
		return expression;
	}
	
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	public String getDocumentation() {
		return documentation;
	}
	
	public void setDocumentation(String documentation) {
		this.documentation = documentation;
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
