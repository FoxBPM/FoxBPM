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
 * @author yangguangftlp
 */
package org.foxbpm.rest.service.api.task;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.exception.FoxBPMBizException;
import org.foxbpm.engine.impl.entity.RunningTrackEntity;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.runningtrack.RunningTrack;
import org.foxbpm.rest.common.api.AbstractRestResource;
import org.foxbpm.rest.common.api.DataResult;
import org.foxbpm.rest.common.api.FoxBpmUtil;
import org.restlet.resource.Get;

/**
 * 常量类
 * 
 * @author yangguangftlp
 * @date 2014年8月12日
 */
public class TaskRunTrackResource extends AbstractRestResource {
	@Get
	public DataResult getTaskRunTrackInfor() {
		String processInstanceId = getQueryParameter("processInstanceId", getQuery());
		if (StringUtil.isEmpty(processInstanceId)) {
			throw new FoxBPMBizException("processInstanceId is null!");
		}
		
		ProcessEngine processEngine = FoxBpmUtil.getProcessEngine();
		Map<String, Object> resultData = new HashMap<String, Object>();
		List<Map<String, Object>> runningTracksMap = new ArrayList<Map<String, Object>>();
		Map<String, Object> runningTrackMap = null;
		Date date = null;
		DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT);
		// 获取运行轨迹信息
		List<RunningTrack> runningTracks = processEngine.getRuntimeService().createRunningTrackQuery().processInstanceID(processInstanceId).list();
		for (RunningTrack runningTrack : runningTracks) {
			runningTrackMap = ((RunningTrackEntity) runningTrack).getPersistentState();
			runningTracksMap.add(runningTrackMap);
			date = (Date) runningTrackMap.get("executionTime");
			if (null != date) {
				runningTrackMap.put("executionTime", formatter.format(date));
			}
		}
		resultData.put("runningTrackInfor", runningTracksMap);
		DataResult result = new DataResult();
		result.setData(resultData);
		return result;
	}
}
