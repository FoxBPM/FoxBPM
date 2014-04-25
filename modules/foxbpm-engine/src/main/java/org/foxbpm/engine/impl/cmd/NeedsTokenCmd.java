package org.foxbpm.engine.impl.cmd;

import java.io.Serializable;

import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.exception.FoxBPMIllegalArgumentException;
import org.foxbpm.engine.exception.FoxBPMObjectNotFoundException;
import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;

public abstract class NeedsTokenCmd<T> implements Command<T>, Serializable {

	private static final long serialVersionUID = 1L;

	protected String tokenId;

	public NeedsTokenCmd(String tokenId) {
		this.tokenId = tokenId;
	}

	public T execute(CommandContext commandContext) {
		if (tokenId == null) {
			throw new FoxBPMIllegalArgumentException("executionId is null");
		}

		TokenEntity token = commandContext.getTokenManager().findTokenById(tokenId);

		if (token == null) {
			throw new FoxBPMObjectNotFoundException("execution " + tokenId + " doesn't exist");
		}

		if (token.isSuspended()) {
			throw new FoxBPMException(getSuspendedExceptionMessage());
		}

		return execute(commandContext, token);
	}


	protected abstract T execute(CommandContext commandContext, TokenEntity token);


	protected String getSuspendedExceptionMessage() {
		return "Cannot execution operation because execution '" + tokenId + "' is suspended";
	}

}
