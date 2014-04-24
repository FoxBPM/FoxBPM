package org.foxbpm.engine.impl.bpmn.deployer;

import org.foxbpm.engine.impl.entity.DeploymentEntity;
import org.foxbpm.engine.impl.persistence.deploy.Deployer;
import org.foxbpm.engine.modelparse.ProcessModelParseHandler;

public class BpmnDeployer implements Deployer {
	
	protected ProcessModelParseHandler processModelParseHandler;

	public ProcessModelParseHandler getProcessModelParseHandler() {
		return processModelParseHandler;
	}

	public void deploy(DeploymentEntity deployment) {
		// TODO Auto-generated method stub
		
	}
	
	 public void setProcessModelParseHandler(ProcessModelParseHandler processModelParseHandler) {
		    this.processModelParseHandler = processModelParseHandler;
	}

}
