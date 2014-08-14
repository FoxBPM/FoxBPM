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
public class JumpTaskCommand  extends AbstractCustomExpandTaskCommand{
	
	public final static String INPUTPARAM_JUMP_NODE_ID = "jumpNodeId";

	/** 跳转的节点号 */
	protected String jumpNodeId;

	public String getJumpNodeId() {
		return jumpNodeId;
	}

	public void setJumpNodeId(String jumpNodeId) {
		this.jumpNodeId = jumpNodeId;
	}

	public JumpTaskCommand(ExpandTaskCommand expandTaskCommand) {
		super(expandTaskCommand);
		this.jumpNodeId=StringUtil.getString(expandTaskCommand.getParamMap().get(INPUTPARAM_JUMP_NODE_ID));
		if (StringUtil.isEmpty(this.jumpNodeId)) {
			throw new FoxBPMBizException("跳转的节点号不能为空");
		}
	}

}
