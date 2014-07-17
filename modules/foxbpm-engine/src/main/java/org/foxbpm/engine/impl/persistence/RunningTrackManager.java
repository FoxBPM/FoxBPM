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
package org.foxbpm.engine.impl.persistence;

import java.util.List;

import org.foxbpm.engine.runningtrack.RunningTrack;

/**
 * 
 * 
 * RunningTrackManager 运行轨迹管理
 * 
 * MAENLIANG 2014年7月9日 上午9:56:12
 * 
 * @version 1.0.0
 * 
 */
public class RunningTrackManager extends AbstractManager {
	@SuppressWarnings({"unchecked"})
	public List<RunningTrack> findRunningTrackByProcessInstanceId(String processInstanceId) {
		return (List<RunningTrack>) getSqlSession().selectListWithRawParameter(
				"selectRunningTrackByProcessInstanceId", processInstanceId);
	}
}
