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
package org.foxbpm.engine.connector;

import java.util.List;

import org.foxbpm.engine.impl.identity.GroupTo;
import org.foxbpm.engine.impl.identity.UserTo;
import org.foxbpm.engine.runtime.ExecutionContext;

public interface ActorConnectorHandler {
	
	
	/**
	 * 获取用户类型处理者
	 * @param executionContext 流程上下文
	 * @return
	 */
	List<UserTo> UserExecute(ExecutionContext executionContext);
	
	/**
	 * 获取组类型处理者
	 * @param executionContext 流程上下文
	 * @return
	 */
	List<GroupTo> GroupExecute(ExecutionContext executionContext);
	
	
	
}
