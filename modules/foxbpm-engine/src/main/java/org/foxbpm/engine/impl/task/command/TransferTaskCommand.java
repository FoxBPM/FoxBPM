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
 * @author kenshin
 */
public class TransferTaskCommand  extends AbstractCustomExpandTaskCommand {
	
	
	public final static String PARAM_TRANSFER_USER_ID="transferUserId";

	/**
	 * 转发的用户编号
	 */
	protected String transferUserId;
	
	
	
	public TransferTaskCommand(ExpandTaskCommand expandTaskCommand) {
		super(expandTaskCommand);
		this.transferUserId=StringUtil.getString(expandTaskCommand.getParam(PARAM_TRANSFER_USER_ID));
		if(StringUtil.isEmpty(this.transferUserId)){
			throw ExceptionUtil.getException("10502012");
		}
	}
	
	public String getTransferUserId() {
		return transferUserId;
	}
	
	public void setTransferUserId(String transferUserId) {
		this.transferUserId = transferUserId;
	}
}
