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
package org.foxbpm.rest.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.foxbpm.rest.service.api.config.FlowResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * servlet listener 用于部署到tomcat等web容器时初始化引擎使用
 * @author ych
 *
 */
public class FoxBpmRestServletContextListener implements ServletContextListener{

	Logger log = LoggerFactory.getLogger(FoxBpmRestServletContextListener.class);
	public void contextInitialized(ServletContextEvent sce) {
		try{
			FlowResourceService flowResouceService = new FlowResourceService();
			flowResouceService.generateFlowResouceZipFile();
		}catch(Exception ex){
			log.error("引擎启动失败:"+ex.getMessage(),ex);
		}
	}
	
	public void contextDestroyed(ServletContextEvent sce) {
//		ProcessEngineManagement.getDefaultProcessEngine().closeEngine();
	}
}
