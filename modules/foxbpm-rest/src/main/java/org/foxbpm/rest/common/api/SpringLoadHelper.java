package org.foxbpm.rest.common.api;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringLoadHelper implements ApplicationContextAware {

	private static ApplicationContext applicationContext; 
	 
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringLoadHelper.applicationContext = applicationContext;
	}
	
	public static Object getBean(String name) throws BeansException{
		return applicationContext.getBean(name);
	}
	
	public static Object getBean(Class<?> c){
		return applicationContext.getBean(c);
	}
}
