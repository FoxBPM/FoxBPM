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
package org.foxbpm.engine.impl.runningtrack;

import java.util.Date;

public interface RunningTrack {

	public boolean isModified(); 
	public String getId();

	public void setId(String id);

	public String getProcessInstanceId();
	public void setProcessInstanceId(String processInstanceId);
	public String getProcessDefinitionId();
	public void setProcessDefinitionId(String processDefinitionId);
	public String getProcessDefinitionKey();
	public void setProcessDefinitionKey(String processDefinitionKey);
	public String getTokenId();
	public void setTokenId(String tokenId);
	public Date getExecutionTime();
	public void setExecutionTime(Date executionTime);
	public String getOperator();
	public void setOperator(String operator);
	public String getNodeId();
	public void setNodeId(String nodeId);
	public String getNodeName();
	public void setNodeName(String nodeName);
	public String getEventName();
	public void setEventName(String eventName);
	public Date getArchiveTime();
	public void setArchiveTime(Date archiveTime);
	public long getTrackRecord();
	public void setTrackRecord(long trackRecord);

}
