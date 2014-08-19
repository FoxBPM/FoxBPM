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
	/**
	 * 
	 * 根据流程实例ID查询，该流程实例所关联的所有流程运行轨迹
	 * 
	 * @param processInstanceId
	 * @return List<RunningTrack>
	 * @exception
	 * @since 1.0.0
	 */
	@SuppressWarnings({"unchecked"})
	public List<RunningTrack> findRunningTrackByProcessInstanceId(String processInstanceId) {
		return (List<RunningTrack>) selectList("selectRunningTrackByProcessInstanceId", processInstanceId);
	}
	/**
	 * 
	 * 根据流程实例ID删除该流程实例所关联的所有运行轨迹
	 * 
	 * @param processInstanceId
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public void deleteRunningTrackByProcessInstanceId(String processInstanceId) {
		delete("deleteRunningTrackByProcessInstanceId", processInstanceId);
	}
}
