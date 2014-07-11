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
package org.foxbpm.engine.impl.mgmt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.impl.datavariable.DataVariableDefinition;
import org.foxbpm.kernel.process.impl.KernelProcessDefinitionImpl;

public class DataVariableMgmtDefinition implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected List<DataVariableDefinition> dataVariableDefinitions = new ArrayList<DataVariableDefinition>();
	protected KernelProcessDefinitionImpl processDefinition;
	public DataVariableMgmtDefinition(KernelProcessDefinitionImpl processDefinition) {
		this.processDefinition = processDefinition;
	}

	public KernelProcessDefinitionImpl getProcessDefinition() {
		return processDefinition;
	}

	public void addDataVariableDefinition(DataVariableDefinition dataVariableDefinition) {
		this.dataVariableDefinitions.add(dataVariableDefinition);
	}

	/**
	 * 获取流程定义的全局变量
	 * 
	 * @return
	 */
	public List<DataVariableDefinition> getDataVariableDefinitions() {
		return dataVariableDefinitions;
	}

	/**
	 * 获取流程定义的全局变量
	 * 
	 * @return
	 */
	public List<DataVariableDefinition> getDataVariableBehaviorsByProcess() {
		List<DataVariableDefinition> dataVariableBehaviorsTemp = new ArrayList<DataVariableDefinition>();
		for (DataVariableDefinition dataVariableBehavior : this.dataVariableDefinitions) {
			if (dataVariableBehavior.isPubilc()) {
				dataVariableBehaviorsTemp.add(dataVariableBehavior);
			}
		}
		return dataVariableBehaviorsTemp;
	}
	
	/**
	 * 获取流程定义的全局变量
	 * 
	 * @return
	 */
	public DataVariableDefinition getProcessDataVariableDefinition(String key) {
		
		for (DataVariableDefinition dataVariableBehavior : this.dataVariableDefinitions) {
			if (dataVariableBehavior.getId().equals(key)) {
				return dataVariableBehavior;
			}
		}
		return null;
	}

	/**
	 * 获取指定节点的数据变量
	 * 
	 * @param nodeId
	 *            节点编号
	 * @return
	 */
	public List<DataVariableDefinition> getDataVariableBehaviorsByNodeId(String nodeId) {
		List<DataVariableDefinition> dataVariableDefinitionsTemp = new ArrayList<DataVariableDefinition>();
		for (DataVariableDefinition dataVariableDefinition : this.dataVariableDefinitions) {
			if (dataVariableDefinition.getNodeId().equals(nodeId)) {
				dataVariableDefinitionsTemp.add(dataVariableDefinition);
			}
		}
		return dataVariableDefinitionsTemp;
	}

}
