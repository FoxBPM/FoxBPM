package org.foxbpm.rest.service.application;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class FoxbpmRestApplication extends Application {

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		RestServicesInit.attachResources(router);
		return router;
	}
}
