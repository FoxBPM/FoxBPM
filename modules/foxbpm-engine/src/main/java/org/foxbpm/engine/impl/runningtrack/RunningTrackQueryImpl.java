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
package org.foxbpm.engine.impl.runningtrack;

import java.util.List;

import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.interceptor.CommandExecutor;
import org.foxbpm.engine.impl.query.AbstractQuery;

/**
 * @author kenshin
 */
public class RunningTrackQueryImpl extends AbstractQuery<RunningTrackQuery, RunningTrack>
		implements
			RunningTrackQuery {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String processInstanceID;

	public RunningTrackQueryImpl() {
	}

	public RunningTrackQueryImpl(CommandContext commandContext) {
		super(commandContext);
	}

	public RunningTrackQueryImpl(CommandExecutor commandExecutor) {
		super(commandExecutor);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public List<RunningTrack> executeList(CommandContext commandContext) {
		checkQueryOk();
		return (List) commandContext.getRunningTrackManager().findRunningTrackByProcessInstanceId(
				null);
	}
	@Override
	public RunningTrackQuery processInstanceID(String processInstanceID) {
		this.processInstanceID = processInstanceID;
		return this;
	}

	@Override
	public long executeCount(CommandContext commandContext) {
		return 0;
	}

}
