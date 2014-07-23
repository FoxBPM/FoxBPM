package org.foxbpm.engine.impl.cmd;

import java.util.Map;

import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;

public class BoundaryTimeSignalCmd implements Command<Void> {

	protected String tokenId;
	protected String nodeId;
	protected Map<String, Object> transientVariables;
	protected Map<String, Object> persistenceVariables;

	public BoundaryTimeSignalCmd(String tokenId, String nodeId, Map<String, Object> transientVariables,
			Map<String, Object> persistenceVariables) {
		
		this.tokenId=tokenId;
		this.nodeId=nodeId;
		this.transientVariables=transientVariables;
		this.persistenceVariables=persistenceVariables;

	}

	@Override
	public Void execute(CommandContext commandContext) {
		// TODO Auto-generated method stub
		return null;
	}

}
