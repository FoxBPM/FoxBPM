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

import java.util.List;
import java.util.Map;

import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.web.common.exception.FoxbpmWebException;
import org.foxbpm.web.common.util.Pagination;

/**
 * 工作流服务接口
 * 
 * @author yangguangftlp
 * @date 2014年6月11日
 */
public interface IWorkFlowService {
	/**
	 * 查询所有流程定义信息
	 * 
	 * @param pageInfor
	 *            分页对象
	 * @param params
	 *            查询条件参数
	 * @return 返回查询结果
	 * @throws FoxbpmWebException
	 */
	List<Map<String, Object>> queryProcessDef(Pagination<String> pageInfor, Map<String, Object> params);

	/**
	 * 查询所有流程实例信息
	 * 
	 * @param pageInfor
	 *            分页对象
	 * @param params
	 *            查询条件参数
	 * @return 返回查询结果
	 * @throws FoxbpmWebException
	 */
	List<Map<String, Object>> queryProcessInst(Pagination<String> pageInfor, Map<String, Object> params);

	/**
	 * 查询任务详细信息
	 * 
	 * @param requestParams
	 *            查询条件
	 * @return 返回查询结果
	 */
	Map<String, Object> queryTaskDetailInfor(Map<String, Object> params);

	/**
	 * 查询代办任务
	 * 
	 * @param pageInfor
	 *            分页对象
	 * @param params
	 *            查询条件
	 * @return 返回查询结果
	 */
	List<Map<String, Object>> queryToDoTask(Pagination<String> pageInfor, Map<String, Object> params);

	/**
	 * 开启任务
	 * 
	 * @param params
	 *            请求参数
	 * @return 返回任务信息
	 */
	public Map<String, Object> startTask(Map<String, Object> params);

	/**
	 * 完成任务
	 * 
	 * @param params
	 *            任务执行命令
	 * @return 返回任务执行结果
	 */
	public ProcessInstance completeTask(Map<String, Object> params);

}
