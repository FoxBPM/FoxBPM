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
package org.foxbpm.engine.task;

/**
 * 任务范围，用来做某部门，某角色类型的任务分配使用
 * @author ych
 *
 */
public class Scrope {

	/**
	 * 范围类型
	 */
	private String scropeType;
	
	/**
	 * 范围编号
	 */
	private String scropeId;

	public String getScropeType() {
		return scropeType;
	}

	public void setScropeType(String scropeType) {
		this.scropeType = scropeType;
	}

	public String getScropeId() {
		return scropeId;
	}

	public void setScropeId(String scropeId) {
		this.scropeId = scropeId;
	}
}
