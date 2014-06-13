
package org.foxbpm.engine.impl.cache;

import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.cache.Cache;
import org.foxbpm.engine.identity.User;
import org.foxbpm.engine.repository.ProcessDefinition;

public class CacheUtil {
	
	public static void clearCache(){
		clearUserCache();
		clearProcessDefinitionCache();
	}
	
	public static Cache<User> getUserCache(){
		return ProcessEngineManagement.getDefaultProcessEngine().getProcessEngineConfiguration().getUserCache();
	}
	
	public static Cache<ProcessDefinition> getProcessDefinitionCache(){
		return ProcessEngineManagement.getDefaultProcessEngine().getProcessEngineConfiguration().getProcessDefinitionCache();
	}
	
	public static void clearUserCache(){
		getUserCache().clear();
	}
	
	public static void clearProcessDefinitionCache(){
		getProcessDefinitionCache().clear();
	}
	
}
