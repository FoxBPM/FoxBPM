package org.foxbpm.engine.spring;

import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.impl.ProcessEngineConfigurationImpl;
import org.foxbpm.engine.spring.db.SpringDataSourceManage;

public class ProcessEngineConfigrationImplSpring extends ProcessEngineConfigurationImpl {

	@Override
	public ProcessEngine buildProcessEngine() {
		this.dataSourceManage = new SpringDataSourceManage();
		return super.buildProcessEngine();
	}
}
