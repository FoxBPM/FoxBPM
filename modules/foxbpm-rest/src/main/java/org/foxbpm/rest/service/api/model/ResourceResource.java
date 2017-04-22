package org.foxbpm.rest.service.api.model;

import java.io.InputStream;

import org.foxbpm.engine.ModelService;
import org.foxbpm.rest.common.api.AbstractRestResource;
import org.foxbpm.rest.common.api.FoxBpmUtil;
import org.restlet.resource.Get;

public class ResourceResource extends AbstractRestResource {

	@Get
	public InputStream getResource(){

		// 只允许内部网络访问
		onlyAllowIntranetAccess();
		
		String deploymentId = getAttribute("deploymentId");
		String resourceName = getAttribute("resourceName");
		ModelService modelService = FoxBpmUtil.getProcessEngine().getModelService();
		InputStream input = modelService.getResourceByDeployIdAndName(deploymentId, resourceName);
		return input;
	}
}
