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
package org.foxbpm.engine.repository;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.foxbpm.engine.impl.entity.DeploymentEntity;

/**
 * 
 * @author kenshin
 *
 */
public interface DeploymentBuilder {
  
	/**
	 * 
	 * @param resourceName
	 * @param inputStream
	 * @return
	 */
	DeploymentBuilder addInputStream(String resourceName,InputStream inputStream);
	
	DeploymentBuilder addInputStream(String resourceName,InputStream inputStream,int version);

	DeploymentBuilder addZipInputStream(ZipInputStream zipInputStream);
	
	DeploymentBuilder addZipInputStream(ZipInputStream zipInputStream,int version);

	DeploymentBuilder name(String name);

	DeploymentBuilder updateDeploymentId(String updateDeploymentId);
	
	DeploymentBuilder addClasspathResource(String resource);

	Deployment deploy();
	
	DeploymentEntity getDeployment();
}
