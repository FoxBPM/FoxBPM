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
 * @author ych
 */
package org.foxbpm.engine.impl.cmd;

import java.util.Map;

import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.engine.impl.interceptor.CommandContext;

public class SignalCmd extends NeedsTokenCmd<Object> {

	private static final long serialVersionUID = 1L;

	protected String signalName;
	protected Object signalData;
	protected Map<String, Object> transientVariables;
	protected Map<String, Object> persistenceVariables;

	public SignalCmd(String tokenId, String signalName, Object signalData, Map<String, Object> transientVariables, Map<String, Object> persistenceVariables) {
		super(tokenId);
		this.signalName = signalName;
		this.signalData = signalData;
		this.transientVariables = transientVariables;
		this.persistenceVariables = persistenceVariables;
	}

	 
	protected Object execute(CommandContext commandContext, TokenEntity token) {
		if (transientVariables != null) {
			token.setProcessInstanceVariables(transientVariables);
		}

		token.signal();
		return null;
	}

	 
	protected String getSuspendedExceptionMessage() {
		return "Cannot signal an execution that is suspended";
	}

}
