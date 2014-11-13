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
package org.foxbpm.engine.impl.task.command;

import org.foxbpm.engine.impl.util.ExceptionUtil;
import org.foxbpm.engine.impl.util.StringUtil;

/**
 * 
 * @author kenshin
 */
public class PendingTaskCommand extends AbstractCustomExpandTaskCommand {
	
	/** 转办给的用户编号 */
	public final static String INPUTPARAM_PENDING_USER_ID = "pendingUserId";
	
	/**
	 * 转办的用户编号
	 */
	protected String pendingUserId;
	
	public String getPendingUserId() {
		return pendingUserId;
	}

	public void setPendingUserId(String pendingUserId) {
		this.pendingUserId = pendingUserId;
	}

	public PendingTaskCommand(ExpandTaskCommand expandTaskCommand) {
		super(expandTaskCommand);
		this.pendingUserId=StringUtil.getString(expandTaskCommand.getParamMap().get(INPUTPARAM_PENDING_USER_ID));
		if (StringUtil.isEmpty(this.pendingUserId)) {
			throw ExceptionUtil.getException("10502008");
		}
	}


}
