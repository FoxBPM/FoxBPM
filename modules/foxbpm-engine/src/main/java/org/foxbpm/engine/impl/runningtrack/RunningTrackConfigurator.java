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
package org.foxbpm.engine.impl.runningtrack;

import org.foxbpm.engine.config.ProcessEngineConfigurator;
import org.foxbpm.engine.event.EventListener;
import org.foxbpm.engine.impl.ProcessEngineConfigurationImpl;
import org.foxbpm.engine.impl.event.EventListenerImpl;

public class RunningTrackConfigurator implements ProcessEngineConfigurator {

	public void beforeInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
		//监听节点信息
		EventListener e = new EventListenerImpl("flowNodeTrackListener-node-enter","node-enter","org.foxbpm.engine.impl.listener.runningtrack.FlowNodeTrackListener");
		//监听线条信息
		EventListener e2 = new EventListenerImpl("SequenceTrackEventListener-sequenceflow-take","sequenceflow-take","org.foxbpm.engine.impl.listener.runningtrack.SequenceTrackEventListener");
		
		processEngineConfiguration.addEventListener(e);
		processEngineConfiguration.addEventListener(e2);
	}

	public void configure(ProcessEngineConfigurationImpl processEngineConfiguration) {

	}

	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

}
