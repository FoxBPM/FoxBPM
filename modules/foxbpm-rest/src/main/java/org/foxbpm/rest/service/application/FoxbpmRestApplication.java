package org.foxbpm.rest.service.application;

import org.restlet.Application;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.ChallengeScheme;
import org.restlet.routing.Router;
import org.restlet.security.ChallengeAuthenticator;
import org.restlet.security.SecretVerifier;
import org.restlet.security.Verifier;

public class FoxbpmRestApplication extends Application {

	protected ChallengeAuthenticator authenticator;

	@Override
	public Restlet createInboundRoot() {

		initializeAuthentication();

		Router router = new Router(getContext());
		RestServicesInit.attachResources(router);
		authenticator.setNext(router);
		return authenticator;
	}

	public void initializeAuthentication() {
		Verifier verifier = new SecretVerifier() {
			@Override
			public boolean verify(String username, char[] password) throws IllegalArgumentException {
				System.out.println("***************************shabi******************************************");
				return true;
			}
		};
		authenticator = new ChallengeAuthenticator(null, true, ChallengeScheme.HTTP_BASIC, "Activiti Realm") {

			@Override
			protected boolean authenticate(Request request, Response response) {

				// Check if authentication is required if a custom
				// RestAuthenticator is set
				if (request.getChallengeResponse() == null) {
					System.out.println("*********************************");
					return false;
				} else {
					System.out.println("####################################*********************************");
					boolean authenticated = super.authenticate(request, response);
					return authenticated;
				}
			}
		};
		authenticator.setVerifier(verifier);
	}

	public String authenticate(Request request, Response response) {
		if (!request.getClientInfo().isAuthenticated()) {
			authenticator.challenge(response, false);
			return null;
		}
		return request.getClientInfo().getUser().getIdentifier();
	}
	
	
}
