package org.foxbpm.rest;

import org.foxbpm.rest.common.RestAuthenticator;
import org.foxbpm.rest.service.application.FoxbpmRestApplication;
import org.restlet.Request;

public class CustomRestApplication extends FoxbpmRestApplication {

	public CustomRestApplication() {
		super();
		this.restAuthenticator = new RestAuthenticator() {
			
			public boolean requestRequiresAuthentication(Request request) {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean isRequestAuthorized(Request request) {
				// TODO Auto-generated method stub
				return false;
			}
		};
	}
}
