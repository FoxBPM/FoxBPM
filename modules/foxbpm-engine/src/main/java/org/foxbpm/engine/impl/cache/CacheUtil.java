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
package org.foxbpm.engine.impl.cache;

import java.util.HashMap;
import java.util.Map;

import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.cache.Cache;
import org.foxbpm.engine.repository.ProcessDefinition;

public class CacheUtil {
	
	@SuppressWarnings({ "unused", "rawtypes" })
	private static Map<String,Cache> cacheMap = new HashMap<String, Cache>();
	public static void clearCache(){
		clearIdentityCache();
		clearProcessDefinitionCache();
		clearUserProcessDefinitionCache();
	}
	
	/**
	 * 用户,组织机构等
	 * @return
	 */
	public static Cache<Object> getIdentityCache(){
		return ProcessEngineManagement.getDefaultProcessEngine().getProcessEngineConfiguration().getIdentityCache();
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
	
	public static void clearIdentityCache(){
		getIdentityCache().clear();
	}
	
	public static void clearProcessDefinitionCache(){
		getProcessDefinitionCache().clear();
	}
	
	public static void clearUserProcessDefinitionCache(){
		getUserProcessDefinitionCache().clear();
	}
	
	
}
