package org.foxbpm.engine.impl.cmd;

import java.io.Serializable;
import java.util.Map;

import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.persistence.deploy.DeploymentManager;
import org.foxbpm.engine.runtime.ProcessInstance;

public class StartProcessInstanceCmd<T> implements Command<ProcessInstance>, Serializable {

	private static final long serialVersionUID = 1L;

	protected String processDefinitionKey;
	protected String processDefinitionId;
	protected Map<String, Object> variables;
	protected String bizKey;
	
	
	public StartProcessInstanceCmd(String processDefinitionKey, String processDefinitionId, String bizKey, Map<String, Object> variables){
	    this.processDefinitionKey = processDefinitionKey;
	    this.processDefinitionId = processDefinitionId;
	    this.bizKey = bizKey;
	    this.variables = variables;
	}

	public ProcessInstance execute(CommandContext commandContext) {
		 DeploymentManager deploymentCache = Context
			      .getProcessEngineConfiguration()
			      .getDeploymentManager();
			    
			    // Find the process definition
			    ProcessDefinitionEntity processDefinition = null;
			    if (processDefinitionId!=null) {
			      processDefinition = deploymentCache.findDeployedProcessDefinitionById(processDefinitionId);
			      if (processDefinition == null) {
			        throw new ActivitiObjectNotFoundException("No process definition found for id = '" + processDefinitionId + "'", ProcessDefinition.class);
			      }
			    } else if(processDefinitionKey != null){
			      processDefinition = deploymentCache.findDeployedLatestProcessDefinitionByKey(processDefinitionKey);
			      if (processDefinition == null) {
			        throw new ActivitiObjectNotFoundException("No process definition found for key '" + processDefinitionKey +"'", ProcessDefinition.class);
			      }
			    } else {
			      throw new ActivitiIllegalArgumentException("processDefinitionKey and processDefinitionId are null");
			    }
			    
			    // Do not start process a process instance if the process definition is suspended
			    if (processDefinition.isSuspended()) {
			      throw new ActivitiException("Cannot start process instance. Process definition " 
			              + processDefinition.getName() + " (id = " + processDefinition.getId() + ") is suspended");
			    }

			    // Start the process instance
			    ProcessInstanceEntity processInstance = processDefinition.createProcessInstance(bizKey);
			    if (variables!=null) {
			      processInstance.setVariables(variables);
			    }
			    processInstance.start();
			    
			    return processInstance;
			  }
	}

}
