package org.foxbpm.engine.impl;

import org.foxbpm.engine.RuntimeService;
import org.foxbpm.engine.impl.cmd.GetProcessByIdCmd;
import org.foxbpm.engine.impl.cmd.GetStartProcessByUserIdCmd;
import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;

public class RuntimeServiceImpl extends ServiceImpl implements RuntimeService {

	public void testSave(ProcessInstanceEntity  processInstanceEntity) {
		 commandExecutor.execute(new GetStartProcessByUserIdCmd(processInstanceEntity));
	}
	
	public ProcessInstanceEntity testSelectById(String id) {
		 return commandExecutor.execute(new GetProcessByIdCmd(id));
	}

}
