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

import org.foxbpm.engine.config.FoxBPMConfig;
import org.foxbpm.engine.impl.util.FoxBPMCfgParseUtil;
import org.foxbpm.engine.impl.util.ReflectUtil;
import org.junit.Test;

/**
 * 常量类
 * 
 * @author yangguangftlp
 * @date 2014年10月27日
 */
public class XMLToObjectTest {
	@Test
	public void testTransform() {
		/*SAXReader reader = new SAXReader();
		Document doc;
		try {
			doc = reader.read(ReflectUtil.getResourceAsStream("org/foxbpm/foxbpm.cfg.xml"));
			FoxBPMConfig a = (FoxBPMConfig) XMLToObject.getInstance().transform(doc, FoxBPMConfig.class, true);
			System.out.println(a);
		} catch (DocumentException e) {
			e.printStackTrace();
		}*/
	};
	@Test
	public void testPase() {
		FoxBPMConfig a = FoxBPMCfgParseUtil.getInstance().parsecfg(ReflectUtil.getResourceAsStream("org/foxbpm/foxbpm.cfg.xml"));
		System.out.println(a);
	};
	
}
