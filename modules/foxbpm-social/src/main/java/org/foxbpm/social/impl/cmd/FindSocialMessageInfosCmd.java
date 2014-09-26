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

import java.util.List;

import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.social.impl.entity.SocialMessageInfo;

/**
 * 根据任务ID，查找对应的消息信息
 * @author MAENLIANG
 *
 */
public class FindSocialMessageInfosCmd implements Command<List<SocialMessageInfo>> {

	private String taskId;

	public FindSocialMessageInfosCmd(String taskId) {
		this.taskId = taskId;
	}

	@Override
	public List<SocialMessageInfo> execute(CommandContext commandContext) {
		// TODO Auto-generated method stub
		return null;
	}

}
