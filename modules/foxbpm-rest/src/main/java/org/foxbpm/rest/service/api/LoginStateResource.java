package org.foxbpm.rest.service.api;

import org.foxbpm.rest.common.api.AbstractRestResource;
import org.restlet.resource.Get;

public class LoginStateResource extends AbstractRestResource {

	@Get
	public String getLoginState(){
		if(!validationUser()){
			return null;
		}
		return "{\"userId\":\""+userId+"\"}";
	}
}
