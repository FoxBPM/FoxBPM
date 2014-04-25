package org.foxbpm.engine.impl.cmd;

import java.util.Map;

import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.engine.impl.interceptor.CommandContext;

public class SignalCmd extends NeedsTokenCmd<Object> {

	private static final long serialVersionUID = 1L;

	protected String signalName;
	protected Object signalData;
	protected final Map<String, Object> processVariables;

	public SignalCmd(String tokenId, String signalName, Object signalData, Map<String, Object> processVariables) {
		super(tokenId);
		this.signalName = signalName;
		this.signalData = signalData;
		this.processVariables = processVariables;
	}

	@Override
	protected Object execute(CommandContext commandContext, TokenEntity token) {
		if (processVariables != null) {
			token.setVariables(processVariables);
		}

		token.signal();
		return null;
	}

	@Override
	protected String getSuspendedExceptionMessage() {
		return "Cannot signal an execution that is suspended";
	}

}
