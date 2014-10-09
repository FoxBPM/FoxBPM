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
package org.foxbpm.engine.impl.datavariable;

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.datavariable.VariableInstance;
import org.foxbpm.engine.datavariable.VariableQuery;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.interceptor.CommandExecutor;
import org.foxbpm.engine.impl.query.AbstractQuery;

public class VariableQueryImpl extends AbstractQuery<VariableQuery, VariableInstance> implements VariableQuery {

	private static final long serialVersionUID = 1L;
	private String id;
	private String processInstanceId;
	private String processDefinitionId;
	
	private String processDefinitionKey;
	private String tokenId;
	private String taskId;
	private List<String> keys;
	private String nodeId;
	
	public VariableQueryImpl() {
	}

	public VariableQueryImpl(CommandContext commandContext) {
		super(commandContext);
	}

	public VariableQueryImpl(CommandExecutor commandExecutor) {
		super(commandExecutor);
	}
	
	 
	public VariableQuery id(String id) {
		this.id = id;
		return this;
	}
	
	 
	public VariableQuery processInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
		return this;
	}
	
	 
	public VariableQuery processDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
		return this;
	}
	
	 
	public VariableQuery processDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
		return this;
	}
	
	 
	public VariableQuery taskId(String taskId) {
		this.taskId = taskId;
		return this;
	}
	
	 
	public VariableQuery tokenId(String tokenId) {
		this.tokenId = tokenId;
		return this;
	}
	
	 
	public VariableQuery nodeId(String nodeId) {
		this.nodeId = nodeId;
		return this;
	}
	
	 
	public VariableQuery addVariableKey(String key) {
		if(keys == null){
			keys = new ArrayList<String>();
		}
		if(!keys.contains(key)){
			keys.add(key);
		}
		return this;
	}

	public String getId() {
		return id;
	}


	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public String getTokenId() {
		return tokenId;
	}

	public String getTaskId() {
		return taskId;
	}
	
	public String getNodeId() {
		return nodeId;
	}

	public List<String> getKeys() {
		return keys;
	}
	

	 
	public long executeCount(CommandContext commandContext) {
		return commandContext.getVariableManager().findVariablesCountByQueryCriteria(this);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	 
	public List<VariableInstance> executeList(CommandContext commandContext) {
		return (List)commandContext.getVariableManager().findVariablesByQueryCriteria(this);
	}

}
