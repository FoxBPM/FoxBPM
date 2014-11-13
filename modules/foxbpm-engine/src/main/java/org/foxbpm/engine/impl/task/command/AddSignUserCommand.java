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

import org.foxbpm.engine.impl.util.ExceptionUtil;
import org.foxbpm.engine.impl.util.StringUtil;

/**
 * @author kenshin
 * 
 */
public class AddSignUserCommand extends AbstractCustomExpandTaskCommand {

	public final static String INPUTPARAM_ADDRESOURCES = "addResources";
	public final static String INPUTPARAM_ISPARALLEL = "isParallel";
	public final static String INPUTPARAM_ISRESOLVED = "isResolved";
	public final static String INPUTPARAM_ISMOREADDSIGN = "isMoreAddSign";

	protected String addResources;
	protected boolean isParallel;
	protected boolean isResolved;
	protected boolean isMoreAddSign;

	public String getAddResources() {
		return addResources;
	}

	public void setAddResources(String addResources) {
		this.addResources = addResources;
	}

	public boolean isParallel() {
		return isParallel;
	}

	public void setParallel(boolean isParallel) {
		this.isParallel = isParallel;
	}

	public boolean isResolved() {
		return isResolved;
	}

	public void setResolved(boolean isResolved) {
		this.isResolved = isResolved;
	}

	public boolean isMoreAddSign() {
		return isMoreAddSign;
	}

	public void setMoreAddSign(boolean isMoreAddSign) {
		this.isMoreAddSign = isMoreAddSign; 
	}

	public AddSignUserCommand(ExpandTaskCommand expandTaskCommand) {
		super(expandTaskCommand);
		this.addResources = StringUtil.getString(expandTaskCommand.getParamMap().get(INPUTPARAM_ADDRESOURCES));
		if (StringUtil.isEmpty(this.addResources)) {
			throw ExceptionUtil.getException("10502006");
		}
		this.isParallel = StringUtil.getBoolean(expandTaskCommand.getParamMap().get(INPUTPARAM_ISPARALLEL));
		this.isResolved = StringUtil.getBoolean(expandTaskCommand.getParamMap().get(INPUTPARAM_ISRESOLVED));
		this.isMoreAddSign = StringUtil.getBoolean(expandTaskCommand.getParamMap().get(INPUTPARAM_ISMOREADDSIGN));
	}

}
