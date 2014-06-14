
package org.foxbpm.engine.impl.cache;

import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.cache.Cache;
import org.foxbpm.engine.identity.User;
import org.foxbpm.engine.repository.ProcessDefinition;

public class CacheUtil {
	
	public static void clearCache(){
		clearUserCache();
		clearProcessDefinitionCache();
		clearUserProcessDefinitionCache();
	}
	
	/**
	 * 用户缓存
	 * @return
	 */
	public static Cache<User> getUserCache(){
		return ProcessEngineManagement.getDefaultProcessEngine().getProcessEngineConfiguration().getUserCache();
	}
	
	/**
	 * 流程定义缓存
	 * @return
	 */
	public static Cache<ProcessDefinition> getProcessDefinitionCache(){
		return ProcessEngineManagement.getDefaultProcessEngine().getProcessEngineConfiguration().getProcessDefinitionCache();
	}
	
	/**
	 * 用户可发起流程缓存
	 * @return
	 */
	public static Cache<Object> getUserProcessDefinitionCache(){
		return ProcessEngineManagement.getDefaultProcessEngine().getProcessEngineConfiguration().getUserProcessDefinitionCache();
	}
	
	public static void clearUserCache(){
		getUserCache().clear();
	}
	
	public static void clearProcessDefinitionCache(){
		getProcessDefinitionCache().clear();
	}
	
	public static void clearUserProcessDefinitionCache(){
		getUserProcessDefinitionCache().clear();
	}
	
	
}
