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
 * @author kenshin
 * @author ych
 */
package org.foxbpm.engine.test.util;

import org.foxbpm.engine.cache.Cache;
import org.foxbpm.engine.impl.cache.CacheUtil;
import org.foxbpm.engine.impl.cache.DefaultCache;
import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Assert;
import org.junit.Test;

public class CacheUtilTest extends AbstractFoxBpmTestCase {

	@Test
	@Deployment(resources = {"org/foxbpm/test/api/Test_RuntimeService_1.bpmn"})
	public void testClear(){
		//注解发布后，processDefinitionCache中应该有且仅有一个缓存定义
		Cache<ProcessDefinition> processDefinitionCache = CacheUtil.getProcessDefinitionCache();
		Assert.assertEquals(1, ((DefaultCache<ProcessDefinition>)processDefinitionCache).size());
		
		//查询一次用户后，userCache中应该存在一个user缓存对象
		identityService.getUser("admin");
		Cache<Object> userCache = CacheUtil.getIdentityCache();
		Assert.assertEquals(3, ((DefaultCache<Object>)userCache).size());
		CacheUtil.clearCache();
		//清空缓存后，缓存数量应该为0
		Assert.assertEquals(0, ((DefaultCache<ProcessDefinition>)processDefinitionCache).size());
		Assert.assertEquals(0, ((DefaultCache<Object>)userCache).size());
	}
	
}
