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
package org.foxbpm.rest.common.api;

import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.ProcessEngineManagement;

/**
 * foxbpm引擎工具类，用于处理rest服务中的所有引擎相关操作
 * @author ych
 *
 */
public class FoxBpmUtil {
	
	/**
	 * 获取流程引擎实例
	 * @return
	 */
	public static ProcessEngine getProcessEngine(){
		return ProcessEngineManagement.getDefaultProcessEngine();
	}

}
