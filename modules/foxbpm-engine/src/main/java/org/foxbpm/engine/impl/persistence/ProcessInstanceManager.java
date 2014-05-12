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

import java.util.List;

import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.runtime.ProcessInstanceQueryImpl;
import org.foxbpm.engine.runtime.ProcessInstance;

/**
 * 流程实例管理器
 * @author kenshin
 *
 */
public class ProcessInstanceManager extends AbstractManager {

	public ProcessInstanceEntity findProcessInstanceById(String id) {
		return selectById(ProcessInstanceEntity.class, id);
	}
	
	public long findProcessInstanceCountByQueryCriteria(ProcessInstanceQueryImpl processsInstanceQuery){
		return (Long) getSqlSession().selectOne("selectProcessInstanceCountByQueryCriteria", processsInstanceQuery);
	}
	
	@SuppressWarnings("unchecked")
	public List<ProcessInstance> findProcessInstanceByQueryCriteria(ProcessInstanceQueryImpl processInstaceQuery){
		return getSqlSession().selectList("selectProcessInstanceByQueryCriteria", processInstaceQuery);
	}

}
