package org.foxbpm.plugin.processinfo;

import org.foxbpm.engine.config.ProcessEngineConfigurator;
import org.foxbpm.engine.event.EventListener;
import org.foxbpm.engine.impl.ProcessEngineConfigurationImpl;
import org.foxbpm.engine.impl.event.EventListenerImpl;

public class ProcessInfoConfigurator implements ProcessEngineConfigurator {

	public void beforeInit(ProcessEngineConfigurationImpl processEngineConfiguration) {	
		//计算流程位置信息
		EventListener udpateInfoListener = new EventListenerImpl("UpdateProcessLocationInfoTableListener-before-process-save","before-process-save","org.foxbpm.engine.impl.listener.process.UpdateLocationListener",1);
		//流程启动时插入流程信息表
		EventListener insertListener = new EventListenerImpl("InsertProcessInfoListener-process-start","process-start","org.foxbpm.plugin.processinfo.InsertProcessInfoTableListener");
		//流程保存时，更新流程信息
		EventListener udpateListener = new EventListenerImpl("UpdateProcessInfoListener-before-process-save","before-process-save","org.foxbpm.plugin.processinfo.UpdateProcessInfoTableListener",60);
		//流程结束时，删除流程信息。
		EventListener deleteListener = new EventListenerImpl("DeleteProcessInfoListener-process-end","process-end","org.foxbpm.plugin.processinfo.DeleteProcessInoTableListener");
		processEngineConfiguration.addEventListener(udpateInfoListener);
		processEngineConfiguration.addEventListener(udpateListener);
		processEngineConfiguration.addEventListener(insertListener);
		processEngineConfiguration.addEventListener(deleteListener);
		
		
	}

	public void configure(ProcessEngineConfigurationImpl processEngineConfiguration) {
		// TODO Auto-generated method stub

	}

	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

}
