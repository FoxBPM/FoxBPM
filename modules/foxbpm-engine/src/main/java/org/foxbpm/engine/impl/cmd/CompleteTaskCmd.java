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
package org.foxbpm.engine.impl.cmd;

import java.util.Map;

import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.interceptor.CommandContext;

public class CompleteTaskCmd extends NeedsActiveTaskCmd<Void> {

	private static final long serialVersionUID = 1L;

	protected Map<String, Object> transientVariables;
	protected Map<String, Object> persistenceVariables;

	public CompleteTaskCmd(String taskId, Map<String, Object> transientVariables, Map<String, Object> persistenceVariables) {
		super(taskId);
		this.transientVariables = transientVariables;
		this.persistenceVariables = persistenceVariables;
	}

	@Override
	protected Void execute(CommandContext commandContext, TaskEntity task) {
		
		if (transientVariables != null && !transientVariables.isEmpty()) {
			task.setProcessInstanceTransientVariables(transientVariables);
		}
		if(persistenceVariables != null && !persistenceVariables.isEmpty()){
			task.setProcessInstanceVariables(persistenceVariables);
		}
		task.setAssignee(Authentication.getAuthenticatedUserId());
		task.complete();
		return null;
	}

}
