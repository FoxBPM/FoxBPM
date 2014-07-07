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
package org.foxbpm.kernel.event;

import java.io.Serializable;

import org.foxbpm.kernel.runtime.ListenerExecutionContext;

/**
 * @author kenshin
 * 
 */
public interface KernelListener extends Serializable {

	/** 执行事件通知 */
	void notify(ListenerExecutionContext executionContext) throws Exception;

	/** 是否中断引擎执行 */
	// boolean isInterrupt();

}
