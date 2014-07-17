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

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.identity.User;
import org.foxbpm.engine.runningtrack.RunningTrack;
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
	List<Map<String, Object>> queryStartProcess(Map<String, Object> params);

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
	List<Map<String, Object>> queryProcessInst(Pagination<String> pageInfor,
			Map<String, Object> params);

	/**
	 * 
	 * queryRunningTrack(根据流程实例ID查询该流程的运行轨迹)
	 * 
	 * @param processInstanceID
	 * @return List<RunningTrack>
	 * @exception
	 * @since 1.0.0
	 */
	public List<RunningTrack> queryRunningTrack(String processInstanceID);

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
	Map<String, Object> startTask(Map<String, Object> params);

	/**
	 * 完成任务
	 * 
	 * @param params
	 *            任务执行命令
	 * @return 返回任务执行结果
	 */
	ProcessInstance completeTask(Map<String, Object> params);

	/**
	 * 获取流程图
	 * 
	 * @param params
	 *            请求参数
	 * @return 返回结果信息
	 */
	String getFlowSvgGraph(Map<String, Object> params);

	/**
	 * 获取流程图
	 * 
	 * @param params
	 *            请求参数
	 * @return 返回结果信息
	 */
	InputStream getFlowImagGraph(Map<String, Object> params);

	/**
	 * 获取代理信息
	 * 
	 * @param params
	 *            查询参数
	 * @return 返回代理信息
	 */
	Map<String, Object> queryUserDelegationInfo(Map<String, Object> params);

	/**
	 * 查询用户信息
	 * 
	 * @param pageInfor
	 *            分页信息
	 * @param params
	 *            查询参数
	 * @return 返回查询结果
	 */
	List<User> queryUsers(Pagination<String> pageInfor, Map<String, Object> params);

	/**
	 * 查询用户数
	 * 
	 * @param params
	 *            查询参数
	 * @return 返回查询记录数
	 */
	Long queryUsersCount(Map<String, Object> params);
}
