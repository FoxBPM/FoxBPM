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
 */
package org.foxbpm.engine.impl.identity;

import java.io.Serializable;

import org.foxbpm.engine.expression.Expression;
import org.foxbpm.engine.impl.expression.ExpressionImpl;

public class PotentialStarter implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String resourceType;
	
	protected Expression expression;
	
	protected String documentation;
	
	
	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
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

}
