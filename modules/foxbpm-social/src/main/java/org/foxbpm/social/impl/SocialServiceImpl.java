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
package org.foxbpm.social.impl;

import java.util.Date;
import java.util.List;

import org.foxbpm.engine.impl.ServiceImpl;
import org.foxbpm.social.SocialService;
import org.foxbpm.social.impl.cmd.AddSocialMessageInfoCmd;
import org.foxbpm.social.impl.cmd.AddSocialUserCmd;
import org.foxbpm.social.impl.cmd.FindAllSocialMessageInfoCmd;
import org.foxbpm.social.impl.cmd.FindReplySocialMessageInfoCmd;
import org.foxbpm.social.impl.entity.SocialMessageInfo;
import org.foxbpm.social.impl.entity.SocialUser;

/**
 * 
 * @author MAENLIANG
 * 
 */
public class SocialServiceImpl extends ServiceImpl implements SocialService {

	 
	public Class<?> getInterfaceClass() {
		return SocialService.class;
	}

	 
	public void addSocialUserInfo(SocialUser socialUser) {
		commandExecutor.execute(new AddSocialUserCmd(socialUser));
	}

	 
	public void addSocialMessageInfo(SocialMessageInfo socialMessageInfo) {
		commandExecutor.execute(new AddSocialMessageInfoCmd(socialMessageInfo));
	}

	 
	public List<SocialMessageInfo> findAllSocialMessageInfo(String taskId) {
		return commandExecutor.execute(new FindAllSocialMessageInfoCmd(taskId));
	}

	 
	public List<SocialMessageInfo> findAllSocialMessageInfo(String taskId,
			String userId, Date loginTime) {
		List<SocialMessageInfo> listSocialMessageInfo = commandExecutor.execute(new FindReplySocialMessageInfoCmd(
				taskId, userId, loginTime));
		
		return listSocialMessageInfo;
	}

}
