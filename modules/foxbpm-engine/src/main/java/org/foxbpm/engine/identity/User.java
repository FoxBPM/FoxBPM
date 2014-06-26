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
package org.foxbpm.engine.identity;

import java.util.List;
import java.util.Map;

import org.foxbpm.engine.impl.agent.AgentTo;

public interface User {

	public String getUserId();

	public String getUserName();

	public String getEmail();

	public List<Group> getGroups();

	public Map<String, Object> getPropertyMap();

	public Object getPropertyValue(String propertyName);

	public Map<String, Object> getPersistentState();

	public List<AgentTo> getAgentInfo();
}
