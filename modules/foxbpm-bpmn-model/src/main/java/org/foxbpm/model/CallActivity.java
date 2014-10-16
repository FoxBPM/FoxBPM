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

import java.util.List;

/**
 * 调用活动
 * 多用于外部子流程
 * @author ych
 *
 */
public class CallActivity extends Activity {

	private static final long serialVersionUID = 1L;
	
	protected boolean isAsync;
	
	protected String callableElementId;
	
	protected String callableElementVersion;
	
	protected String bizKey;

	protected List<VariableMapping> toSubProcessMapping;

	protected List<VariableMapping> formSubProcessMapping;

	public boolean isAsync() {
		return isAsync;
	}

	public void setAsync(boolean isAsync) {
		this.isAsync = isAsync;
	}

	public String getCallableElementId() {
		return callableElementId;
	}

	public void setCallableElementId(String callableElementId) {
		this.callableElementId = callableElementId;
	}

	public String getCallableElementVersion() {
		return callableElementVersion;
	}

	public void setCallableElementVersion(String callableElementVersion) {
		this.callableElementVersion = callableElementVersion;
	}

	public String getBizKey() {
		return bizKey;
	}

	public void setBizKey(String bizKey) {
		this.bizKey = bizKey;
	}

	public List<VariableMapping> getToSubProcessMapping() {
		return toSubProcessMapping;
	}

	public void setToSubProcessMapping(List<VariableMapping> toSubProcessMapping) {
		this.toSubProcessMapping = toSubProcessMapping;
	}

	public List<VariableMapping> getFormSubProcessMapping() {
		return formSubProcessMapping;
	}

	public void setFormSubProcessMapping(List<VariableMapping> formSubProcessMapping) {
		this.formSubProcessMapping = formSubProcessMapping;
	}
	
	

}
