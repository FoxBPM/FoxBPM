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
 * @author ych
 */
package org.foxbpm.engine.impl.task.command;

import org.foxbpm.engine.exception.FoxBPMBizException;
import org.foxbpm.engine.impl.util.StringUtil;

/**
 * @author kenshin
 *
 */
public class UpdateCompletionRateCommand extends AbstractCustomExpandTaskCommand {
	
	public final static String INPUTPARAM_COMPLETION_RATE="completionRate";
	
	protected double completionRate;

	
	public double getCompletionRate() {
		return completionRate;
	}


	public void setCompletionRate(double completionRate) {
		this.completionRate = completionRate;
	}


	public UpdateCompletionRateCommand(ExpandTaskCommand expandTaskCommand) {
		super(expandTaskCommand);
		this.completionRate=StringUtil.getInt(expandTaskCommand.getParam(INPUTPARAM_COMPLETION_RATE));
		if(this.completionRate<=100&&this.completionRate>=0){
			throw new FoxBPMBizException("完成率必须在0-100之间");
		}
	}

	
}
