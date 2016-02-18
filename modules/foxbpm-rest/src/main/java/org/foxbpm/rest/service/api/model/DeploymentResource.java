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
import org.foxbpm.engine.exception.FoxbpmPluginException;
import org.foxbpm.engine.repository.DeploymentBuilder;
import org.foxbpm.rest.common.api.AbstractRestResource;
import org.foxbpm.rest.common.api.FoxBpmUtil;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Put;

public class DeploymentResource extends AbstractRestResource {
	
	@Put
	public String update(Representation entity){
		
		// 只允许内部网络访问
		onlyAllowIntranetAccess();
		
		String deploymentId = getAttribute("deploymentId");
		InputStream input = null;
		try {
			input = entity.getStream();
			if(input == null){
				throw new FoxbpmPluginException("请求中必须包含文件流", "Rest服务");
			}
			ModelService modelService = FoxBpmUtil.getProcessEngine().getModelService();
			DeploymentBuilder deploymentBuilder = modelService.createDeployment();
			ZipInputStream zip = new ZipInputStream(input);
			deploymentBuilder.updateDeploymentId(deploymentId);
			deploymentBuilder.addZipInputStream(zip);
			deploymentBuilder.deploy();
			setStatus(Status.SUCCESS_OK);
		} catch (Exception e) {
			if (e instanceof FoxBPMException) {
				throw (FoxBPMException) e;
			}
			throw new FoxBPMException(e.getMessage(), e);
		}
		
		return "SUCCESS";
	}
	
	@Delete
	public String deleteDeployment(){
		
		// 只允许内部网络访问
		onlyAllowIntranetAccess();
		
		String deploymentId = getAttribute("deploymentId");
		ModelService modelService = FoxBpmUtil.getProcessEngine().getModelService();
		modelService.deleteDeployment(deploymentId);
		return "SUCCESS";
	}
}
