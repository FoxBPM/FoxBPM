package org.foxbpm.engine.impl.cmd;

import java.util.List;

import org.foxbpm.engine.impl.datavariable.BizDataObject;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;

public class GetBizDataObjectCmd implements Command<List<BizDataObject>> {
	
	protected String type;
	
	public GetBizDataObjectCmd(String type){
		this.type=type;
	}

	@Override
	public List<BizDataObject> execute(CommandContext commandContext) {
		// TODO Auto-generated method stub
		return null;
	}

}
