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
package org.foxbpm.web.service.interfaces;

import java.util.Map;

import org.foxbpm.engine.runtime.ProcessInstance;

/**
 * 业务服务接口
 * @author yangguangftlp
 * @date: 2014年6月10日
 */
public interface IWebappDemoService {

	/**
	 * 开启一个任务
	 * 
	 * @param params
	 *            查询需要处理的任务
	 * @return 获取任务详细信息
	 */
	public Map<String, Object> startTask(Map<String, Object> params);

	/**
	 * 执行任务
	 * 
	 * @param params
	 *            任务信息
	 * @return 返回完成后的任务信息
	 */
	public ProcessInstance executeTask(Map<String, Object> params);
	
	
}
