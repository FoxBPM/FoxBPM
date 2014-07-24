package org.foxbpm.engine.impl.cmd;

import java.util.List;

import org.foxbpm.engine.impl.datavariable.DataObject;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;

public class GetBizSystemDataObjectCmd implements Command<List<DataObject>> {
	
	protected String type;
	
	public GetBizSystemDataObjectCmd(String type){
		this.type=type;
	}

	@Override
	public List<DataObject> execute(CommandContext commandContext) {
		// TODO Auto-generated method stub
		return null;
	}

}
