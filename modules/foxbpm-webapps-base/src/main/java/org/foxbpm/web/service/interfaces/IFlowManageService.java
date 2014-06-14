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

import org.foxbpm.web.common.exception.FoxbpmWebException;
import org.foxbpm.web.common.util.Pagination;

/**
 * 流程管理接口
 * 
 * @author yangguangftlp
 * @date 2014年6月12日
 */
public interface IFlowManageService {

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
	 * 流程定义新增和更新，取决于参数中有没有deploymentId
	 * 
	 * @param params
	 */
	void deployByZip(Map<String, Object> params);

	/**
	 * 删除流程定义 根据deploymentId
	 * 
	 * @param params
	 */
	void deleteDeploy(Map<String, Object> params);
}
