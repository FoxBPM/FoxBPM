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
 * 连接器入参
 * @author ych
 *
 */
public class InputParam extends BaseElement {

	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	protected String name;

	/**
	 * 数据类型
	 */
	protected String dataType;

	/**
	 * 是否执行表达式
	 */
	protected boolean isExecute = true;

	/**
	 * 值表达式
	 */
	protected String expression;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public boolean isExecute() {
		return isExecute;
	}

	public void setExecute(boolean isExecute) {
		this.isExecute = isExecute;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
}
