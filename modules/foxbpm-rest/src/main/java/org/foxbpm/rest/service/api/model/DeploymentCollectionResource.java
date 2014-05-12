package org.foxbpm.rest.service.api.model;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.foxbpm.engine.ModelService;
import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.exception.FoxBPMException;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class DeploymentCollectionResource extends ServerResource {

	@Post
	public String deploy(Representation entity){
		InputStream input = null;
		try {
			input = entity.getStream();
			ProcessEngine engine = ProcessEngineManagement.getDefaultProcessEngine();
			ModelService modelService = engine.getModelService();
			ZipInputStream zip = new ZipInputStream(input);
			modelService.deployByZip(zip);
			setStatus(Status.SUCCESS_CREATED);
		} catch (Exception e) {
			if (e instanceof FoxBPMException) {
				throw (FoxBPMException) e;
			}
			throw new FoxBPMException(e.getMessage(), e);
		}
		return "success";
	}
}
