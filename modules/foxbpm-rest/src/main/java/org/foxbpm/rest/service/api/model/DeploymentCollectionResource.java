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
package org.foxbpm.rest.service.api.model;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.foxbpm.engine.ModelService;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.exception.FoxBPMIllegalArgumentException;
import org.foxbpm.engine.repository.Deployment;
import org.foxbpm.engine.repository.DeploymentBuilder;
import org.foxbpm.engine.repository.ProcessDefinitionQuery;
import org.foxbpm.rest.common.api.AbstractRestResource;
import org.foxbpm.rest.common.api.FoxBpmUtil;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;

/**
 * 流程定义集合资源
 * Post：发布
 * @author ych
 *
 */
public class DeploymentCollectionResource extends AbstractRestResource {

	@Post
	public String deploy(Representation entity){
		InputStream input = null;
		String processDefinitionKey = null;
		try {
			input = entity.getStream();
			if(input == null){
				throw new FoxBPMIllegalArgumentException("请求中必须包含文件流inputStream");
			}
			ModelService modelService = FoxBpmUtil.getProcessEngine().getModelService();
			ZipInputStream zip = new ZipInputStream(input);
			DeploymentBuilder deploymentBuilder = modelService.createDeployment();
			deploymentBuilder.addZipInputStream(zip);
			Deployment deployment = deploymentBuilder.deploy();
			setStatus(Status.SUCCESS_CREATED);
			ProcessDefinitionQuery processDefinitionQuery = modelService.createProcessDefinitionQuery();
			processDefinitionKey = processDefinitionQuery.deploymentId(deployment.getId()).singleResult().getId();
		} catch (Exception e) {
			if (e instanceof FoxBPMException) {
				throw (FoxBPMException) e;
			}
			throw new FoxBPMException(e.getMessage(), e);
		}		
		return processDefinitionKey;
	}
}
