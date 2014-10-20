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
 * @author yangguangftlp
 */
package org.foxbpm.bpmn.exceptions;

/**
 * 
 * 转换器异常
 * 
 * @author yangguangftlp
 * @date 2014年10月20日
 */
public class BpmnConverterException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public BpmnConverterException(String message) {
		super(message);
	}
	
	public BpmnConverterException(String message, Throwable t) {
		super(message, t);
	}
	public BpmnConverterException(Throwable t) {
		super(t);
	}
}
