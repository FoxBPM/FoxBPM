package org.foxbpm.rest.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.foxbpm.engine.ProcessEngineManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FoxBpmRestServletContextListener implements ServletContextListener{

	Logger log = LoggerFactory.getLogger(FoxBpmRestServletContextListener.class);
	public void contextInitialized(ServletContextEvent sce) {
		try{
			ProcessEngineManagement.getDefaultProcessEngine();
			log.info("引擎启动成功");
		}catch(Exception ex){
			log.error("引擎启动失败:"+ex.getMessage(),ex);
		}
	}
	
	public void contextDestroyed(ServletContextEvent sce) {
		ProcessEngineManagement.getDefaultProcessEngine().closeEngine();
	}
}
