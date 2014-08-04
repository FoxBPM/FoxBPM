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

import org.foxbpm.engine.exception.FoxBPMBizException;
import org.foxbpm.engine.impl.util.StringUtil;

/**
 * @author kenshin
 *
 */
public class RollBackResetCommand  extends AbstractCustomExpandTaskCommand {
	
	
	public final static String PARAM_ROLLBACK_NODEID="rollBackNodeId";

	/** 退回的节点号 */
	protected String rollBackNodeId;
	
	public String getRollBackNodeId() {
		return rollBackNodeId;
	}

	public void setRollBackNodeId(String rollBackNodeId) {
		this.rollBackNodeId = rollBackNodeId;
	}

	public RollBackResetCommand(ExpandTaskCommand expandTaskCommand) {
		super(expandTaskCommand);
		this.rollBackNodeId=StringUtil.getString(expandTaskCommand.getParam(PARAM_ROLLBACK_NODEID));
		if(StringUtil.isEmpty(this.rollBackNodeId)){
			throw new FoxBPMBizException("退回的节点号不能为空");
		}
	}
	

}
