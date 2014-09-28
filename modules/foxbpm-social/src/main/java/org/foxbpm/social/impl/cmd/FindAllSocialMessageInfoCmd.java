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
 * @author MAENLIANG
 */
package org.foxbpm.social.impl.cmd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.impl.db.ListQueryParameterObject;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.social.impl.entity.SocialMessageInfo;

/**
 * 第一次打开表单获取所有的聊天信息
 * @author MAENLIANG
 *
 */
public class FindAllSocialMessageInfoCmd implements Command<List<SocialMessageInfo>> {

	private String taskId;

	public FindAllSocialMessageInfoCmd(String taskId) {
		this.taskId = taskId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SocialMessageInfo> execute(CommandContext commandContext) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("taskId", taskId); 
		ListQueryParameterObject queryParams = new ListQueryParameterObject();
		queryParams.setParameter(queryMap);
		return (List<SocialMessageInfo>) commandContext.getSqlSession().selectList("findAllSocialMessageInfo", queryParams);
	}

}
