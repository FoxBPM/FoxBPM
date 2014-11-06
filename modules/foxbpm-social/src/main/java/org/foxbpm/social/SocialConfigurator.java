package org.foxbpm.social;

import org.foxbpm.engine.config.ProcessEngineConfigurator;
import org.foxbpm.engine.impl.ProcessEngineConfigurationImpl;
import org.foxbpm.social.impl.SocialServiceImpl;
import org.foxbpm.social.impl.mybatis.SocialMapperConfigImpl;

public class SocialConfigurator implements ProcessEngineConfigurator {

	public void beforeInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
		//暴露workCalendarService接口
		processEngineConfiguration.getProcessServices().add(new SocialServiceImpl());
		//注册calendar插件用到的mybatis相关的mapper文件
		processEngineConfiguration.getCustomMapperConfig().add(new SocialMapperConfigImpl());
	}

	public void configure(ProcessEngineConfigurationImpl processEngineConfiguration) {
		// TODO Auto-generated method stub

	}

	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

}
