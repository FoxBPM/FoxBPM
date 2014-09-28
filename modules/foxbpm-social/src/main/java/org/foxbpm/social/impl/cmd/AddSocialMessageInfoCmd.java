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

import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.social.impl.entity.SocialMessageInfo;

/**
 * 添加一条消息
 * @author MAENLIANG
 *
 */
public class AddSocialMessageInfoCmd implements Command<SocialMessageInfo> {

	private SocialMessageInfo socialMessageInfo;

	public AddSocialMessageInfoCmd(SocialMessageInfo socialMessageInfo) {
		this.socialMessageInfo = socialMessageInfo;
	}

	@Override
	public SocialMessageInfo execute(CommandContext commandContext) {
		commandContext.getSqlSession().insert("addSocialMessageInfo", socialMessageInfo);
		return null;
	}

}
