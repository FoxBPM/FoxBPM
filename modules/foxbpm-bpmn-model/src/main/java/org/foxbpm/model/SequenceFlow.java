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
 * 流程线条元素
 * @author ych
 *
 */
public class SequenceFlow extends FlowElement {

	private static final long serialVersionUID = 1L;
	
	private String sourceRefId;
	
	private String targetRefId;
	
	/**
	 * 线条表达式，一般用来判断路径是否可通过
	 */
	private String flowCondition;

	public String getFlowCondition() {
		return flowCondition;
	}

	public void setFlowCondition(String flowCondition) {
		this.flowCondition = flowCondition;
	}

	public String getSourceRefId() {
		return sourceRefId;
	}

	public void setSourceRefId(String sourceRefId) {
		this.sourceRefId = sourceRefId;
	}

	public String getTargetRefId() {
		return targetRefId;
	}

	public void setTargetRefId(String targetRefId) {
		this.targetRefId = targetRefId;
	}
	
	

}
