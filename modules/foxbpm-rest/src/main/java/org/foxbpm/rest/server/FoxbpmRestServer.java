package org.foxbpm.rest.server;

import org.foxbpm.rest.service.application.FoxbpmRestApplication;
import org.restlet.Component;
import org.restlet.data.Protocol;

public class FoxbpmRestServer {

	public static void main(String[] args) throws Exception {
		
		 Component component = new Component();
		 component.getServers().add(Protocol.HTTP, 8082);
		 component.getDefaultHost().attach(new FoxbpmRestApplication());
		 component.start();     
		 System.out.println("The restlet server started ...");
	}
}
