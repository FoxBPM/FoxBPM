package org.foxbpm.engine.impl.cmd;

import java.util.Map;

import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.engine.impl.interceptor.CommandContext;

public class SignalCmd extends NeedsTokenCmd<Object> {

	private static final long serialVersionUID = 1L;

	protected String signalName;
	protected Object signalData;
	protected Map<String, Object> transientVariables;
	protected Map<String, Object> persistenceVariables;

	public SignalCmd(String tokenId, String signalName, Object signalData, Map<String, Object> transientVariables, Map<String, Object> persistenceVariables) {
		super(tokenId);
		this.signalName = signalName;
		this.signalData = signalData;
		this.transientVariables = transientVariables;
		this.persistenceVariables = persistenceVariables;
	}

	@Override
	protected Object execute(CommandContext commandContext, TokenEntity token) {
		if (transientVariables != null) {
			token.setProcessInstanceVariables(transientVariables);
		}

		token.signal();
		return null;
	}

	@Override
	protected String getSuspendedExceptionMessage() {
		return "Cannot signal an execution that is suspended";
	}

}
