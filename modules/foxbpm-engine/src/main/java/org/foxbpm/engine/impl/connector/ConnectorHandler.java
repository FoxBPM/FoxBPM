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
package org.foxbpm.engine.impl.connector;

import org.foxbpm.engine.execution.ConnectorExecutionContext;
import org.foxbpm.kernel.event.KernelListener;
import org.foxbpm.kernel.runtime.ListenerExecutionContext;

public abstract class ConnectorHandler implements KernelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void notify(ListenerExecutionContext executionContext) throws Exception {
		execute((ConnectorExecutionContext)executionContext);
	}
	
	/**
	 * 连接器执行方法
	 * @param executionContext 上下文环境变量
	 * @throws Exception
	 */
	public abstract void execute(ConnectorExecutionContext executionContext) throws Exception;

}
