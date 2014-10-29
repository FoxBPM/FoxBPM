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
package org.foxbpm.engine.impl.task;

import org.foxbpm.engine.expression.Expression;
import org.foxbpm.engine.impl.expression.ExpressionImpl;
import org.foxbpm.engine.task.CommandParamType;

/**
 * @author kenshin
 *
 */
public class CommandParam {
	
	protected String key;

	protected String name;
	
	protected String description;
	
	protected CommandParamType bizType;
	
	protected String dataType;
	
	protected Expression expression;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CommandParamType getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = CommandParamType.valueOf(bizType);
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = new ExpressionImpl(expression);
	}

}
