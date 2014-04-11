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
package org.foxbpm.test.engine;


import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.foxbpm.model.config.foxbpmconfig.FoxBPMConfig;
import org.foxbpm.model.config.foxbpmconfig.FoxBPMConfigFactory;
import org.foxbpm.model.config.foxbpmconfig.ResourcePath;
import org.foxbpm.model.config.foxbpmconfig.ResourcePathConfig;
import org.foxbpm.model.config.foxbpmconfig.util.FoxBPMConfigResourceImpl;

import junit.framework.TestCase;

/**
 * 用来根据config的模型生成config文件
 * @author ych
 *
 */
public class TestBuildConfigFile extends TestCase{

	public void testBuildConfigXmlFile(){
		FoxBPMConfigFactory.eINSTANCE.eClass();
		FoxBPMConfig bpmConfig = FoxBPMConfigFactory.eINSTANCE.createFoxBPMConfig();
		ResourcePathConfig resourcePathConfig=FoxBPMConfigFactory.eINSTANCE.createResourcePathConfig();
		ResourcePath resourcePath = FoxBPMConfigFactory.eINSTANCE.createResourcePath();
		resourcePath.setId("cao");
		resourcePath.setName("ri");
		resourcePath.setSrc("chedan");
		resourcePathConfig.getResourcePath().add(resourcePath);
		bpmConfig.setResourcePathConfig(resourcePathConfig);

		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("xml", new XMIResourceFactoryImpl());
		FoxBPMConfigResourceImpl resource = new FoxBPMConfigResourceImpl(URI.createFileURI("E:\\foxbpm.cfg.xml"));
		resource.setEncoding("UTF-8");
		resource.getContents().add(bpmConfig);
		try {
			resource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			resource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
