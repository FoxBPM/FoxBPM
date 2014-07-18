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

import org.foxbpm.engine.expression.Expression;
import org.foxbpm.engine.impl.expression.ExpressionImpl;

/**
 * @author kenshin
 *
 */
public class SkipStrategy extends BaseElementBehavior {

	private static final long serialVersionUID = 1L;
	
	protected Expression skipExpression;
	
	
	protected boolean isEnable=false;
	
	protected boolean isCreateSkipTaskRecord=true;
	
	

	protected Expression skipAssignee;
	
	protected Expression skipComment;

	public Expression getSkipExpression() {
		return skipExpression;
	}

	public void setSkipExpression(String skipExpression) {
		this.skipExpression = new ExpressionImpl(skipExpression);
	}

	public boolean isEnable() {
		return isEnable;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public boolean isCreateSkipTaskRecord() {
		return isCreateSkipTaskRecord;
	}

	public void setCreateSkipTaskRecord(boolean isCreateSkipTaskRecord) {
		this.isCreateSkipTaskRecord = isCreateSkipTaskRecord;
	}

	public Expression getSkipAssignee() {
		return skipAssignee;
	}

	public void setSkipAssignee(String skipAssignee) {
		this.skipAssignee = new ExpressionImpl(skipAssignee);
	}

	public Expression getSkipComment() {
		return skipComment;
	}

	public void setSkipComment(String skipComment) {
		this.skipComment = new ExpressionImpl(skipComment);
	}





}
