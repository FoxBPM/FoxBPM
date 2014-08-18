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
 * @author kenshin
 * @author ych
 */
package org.foxbpm.engine.impl.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.impl.datavariable.VariableQueryImpl;
import org.foxbpm.engine.impl.entity.QueryVariablesCommand;
import org.foxbpm.engine.impl.entity.VariableInstanceEntity;
import org.foxbpm.kernel.runtime.impl.KernelVariableInstanceImpl;

/**
 * 变量管理器
 * @author kenshin
 *
 */
public class VariableManager extends AbstractManager {

	public List<KernelVariableInstanceImpl> findVariableInstancesByProcessInstanceId(String id) {
		return null;
	}
	
	
	public VariableInstanceEntity findVariableById(String variableId){
		return selectById(variableId);
	}
	
	@SuppressWarnings("unchecked")
	public List<VariableInstanceEntity> findVariableByProcessInstanceId(String processInstanceId){
		return (List<VariableInstanceEntity>)selectList("selectVariableByProcessInstanceId", processInstanceId);
	}
	
	public VariableInstanceEntity findVariableByProcessInstanceIdAndKey(String processInstanceId,String key){
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("processInstanceId", processInstanceId);
		paraMap.put("key", key);
		return (VariableInstanceEntity)selectOne("selectVariableByProcessInstanceIdAndKey",paraMap);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> queryVariable(QueryVariablesCommand queryVariableCommand){
		List<VariableInstanceEntity> variables =  (List<VariableInstanceEntity>)selectList("selectVariableByQueryCommand", queryVariableCommand);
		Map<String,Object> result = new HashMap<String,Object>();
		if(variables != null){
			for(VariableInstanceEntity variable : variables){
				result.put(variable.getKey(), variable.getValue());
			}
		}
		return result;
	}
	
	public void deleteVariableByProcessInstanceId(String processInstanceId){
		delete("deleteVariableByProcessInstanceId", processInstanceId);
	}
	
	@SuppressWarnings("unchecked")
	public List<VariableInstanceEntity> findVariablesByQueryCriteria(VariableQueryImpl variableQueryImpl) {
		return (List<VariableInstanceEntity>) selectList("findVariablesByQueryCriteria", variableQueryImpl);
	}

	public long findVariablesCountByQueryCriteria(VariableQueryImpl variableQueryImpl) {
		return (Long) selectOne("findVariableCountByQueryCriteria", variableQueryImpl);
	}
}
