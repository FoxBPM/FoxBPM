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
 * @author yangguangftlp
 */
package org.foxbpm.engine.test.util;

import java.util.Iterator;

import org.foxbpm.engine.config.FoxBPMConfig;
import org.foxbpm.engine.event.EventListener;
import org.foxbpm.engine.impl.ProcessEngineConfigurationImpl;
import org.foxbpm.engine.impl.util.FoxBPMCfgParseUtil;
import org.foxbpm.engine.impl.util.ReflectUtil;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 常量类
 * 
 * @author yangguangftlp
 * @date 2014年10月27日
 */
public class XMLToObjectTest extends AbstractFoxBpmTestCase {
	private static Logger log = LoggerFactory.getLogger(ProcessEngineConfigurationImpl.class);
	@Test
	public void testTransform() {
		EventListener tmp = null;
		System.out.println("---------------------------------------------");
		for (Iterator<EventListener> iterator = processEngine.getProcessEngineConfiguration().getEventListeners().iterator(); iterator.hasNext();) {
			tmp = iterator.next();
			log.debug("监听编号：{},监听事件：{},级别：{},类名：{}", tmp.getId(), tmp.getEventType(), tmp.getPriority(), tmp.getListenerClass());
		}
	};
	@Test
	public void testPase() {
		FoxBPMConfig a = FoxBPMCfgParseUtil.getInstance().parsecfg(ReflectUtil.getResourceAsStream("org/foxbpm/foxbpm.cfg.xml"));
		System.out.println(a);
	};
	
}
