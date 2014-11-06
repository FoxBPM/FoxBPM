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
 * 跳过策略
 * @author ych
 *
 */
public class SkipStrategy extends BaseElement {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 跳过表达式
	 */
	protected String skipExpression;
	
	/**
	 * 是否启用
	 */
	protected boolean isEnable = false;
	
	/**
	 * 是否生成跳过记录
	 */
	protected boolean isCreateSkipTaskRecord = true;
	
	/**
	 * 跳过记录的默认处理人
	 */
	protected String skipAssignee;
	
	/**
	 * 跳过记录的默认处理意见
	 */
	protected String skipComment;

	public String getSkipExpression() {
		return skipExpression;
	}

	public void setSkipExpression(String skipExpression) {
		this.skipExpression = skipExpression;
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

	public String getSkipAssignee() {
		return skipAssignee;
	}

	public void setSkipAssignee(String skipAssignee) {
		this.skipAssignee = skipAssignee;
	}

	public String getSkipComment() {
		return skipComment;
	}

	public void setSkipComment(String skipComment) {
		this.skipComment = skipComment;
	}

}
