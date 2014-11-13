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

package org.foxbpm.rest.common.application;

import org.codehaus.jackson.map.JsonMappingException;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.rest.common.RestError;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.service.StatusService;

/**
 * rest服务统一处理返回值
 * 
 * @author ych
 */
public class FoxbpmStatusService extends StatusService {

	protected static final String NEWLINE_REPLACE_REGEX = "\\r\\n|\\r|\\n";
	 
	public Representation getRepresentation(Status status, Request request, Response response) {
		if (status != null && status.isError()) {
			RestError error = new RestError();
			error.setStatusCode(status.getCode());
			error.setErrorMessage(status.getDescription());
			return new JacksonRepresentation<RestError>(error);
		} else {
			return super.getRepresentation(status, request, response);
		}
	}

	 
	public Status getStatus(Throwable throwable, Request request, Response response) {
		Status status = null;
		if (throwable instanceof JsonMappingException && throwable.getCause() != null) {
			status = getSpecificStatus(throwable.getCause(), request, response);
		}
		if (status == null) {
			Throwable causeThrowable = null;
			if (throwable.getCause() != null && throwable.getCause() instanceof FoxBPMException) {
				causeThrowable = throwable.getCause();
			} else {
				causeThrowable = throwable;
			}
			status = getSpecificStatus(causeThrowable, request, response);
		}
		return status != null ? status : Status.SERVER_ERROR_INTERNAL;
	}

	protected Status getSpecificStatus(Throwable throwable, Request request, Response response) {
		Status status = null;

		if (throwable instanceof ResourceException) {
			ResourceException re = (ResourceException) throwable;
			status = re.getStatus();
		} else if (throwable instanceof FoxBPMException) {
			status = new Status(Status.SERVER_ERROR_INTERNAL.getCode(), getSafeStatusName(throwable.getMessage()), getSafeStatusName(throwable.getMessage()), null);
		} else {
			status = null;
		}

		return status;
	}

	protected String getSafeStatusName(String name) {
		if (name != null) {
			return name.replaceAll(NEWLINE_REPLACE_REGEX, " ");
		}
		return null;
	}
}
