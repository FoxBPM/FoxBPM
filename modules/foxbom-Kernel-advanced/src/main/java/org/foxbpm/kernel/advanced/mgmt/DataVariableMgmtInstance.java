/**
 * Copyright 1996-2014 FoxBPM Co.,Ltd.
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
 * @author kenshin
 */
package org.foxbpm.kernel.advanced.mgmt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.foxbpm.kernel.advanced.datavariable.DataVariableDefinition;
import org.foxbpm.kernel.advanced.entity.VariableInstanceEntity;
import org.foxbpm.kernel.process.impl.KernelProcessInstanceImpl;



public class DataVariableMgmtInstance implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected DataVariableMgmtDefinition dataVariableMgmtDefinition;
	
	protected KernelProcessInstanceImpl processInstance;
	
	public DataVariableMgmtInstance(KernelProcessInstanceImpl processInstance){		
		this.processInstance=processInstance;	
	}


	
	
	protected List<VariableInstanceEntity> variableInstanceEntities=new ArrayList<VariableInstanceEntity>();
	
	public List<VariableInstanceEntity> getDataVariableEntities() {
		return variableInstanceEntities;
	}
	
	
	public VariableInstanceEntity getDataVariableById(String id){
		
		for (VariableInstanceEntity dataVariableInstance : variableInstanceEntities) {
			if(dataVariableInstance.getId().equals(id)){
				return dataVariableInstance;
			}
		}
		return null;
		
	}
	
	public VariableInstanceEntity getDataVariableByExpressionId(String expressionId){
		for (VariableInstanceEntity dataVariableInstance : variableInstanceEntities) {
			if(dataVariableInstance.getId().equals(expressionId)){
				return dataVariableInstance;
			}
		}
		return null;
	}


	public VariableInstanceEntity createDataVariableInstance(DataVariableDefinition dataVariableDefinition){
		VariableInstanceEntity dataVariableInstance=new VariableInstanceEntity(dataVariableDefinition,this);
		variableInstanceEntities.add(dataVariableInstance);
		return dataVariableInstance;
	}
	

	public KernelProcessInstanceImpl getProcessInstance() {
		return processInstance;
	}



	
}
