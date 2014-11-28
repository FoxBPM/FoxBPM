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
