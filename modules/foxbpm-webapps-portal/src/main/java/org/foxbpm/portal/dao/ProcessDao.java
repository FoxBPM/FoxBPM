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
package org.foxbpm.portal.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.foxbpm.portal.model.ProcessInfoEntity;
import org.springframework.stereotype.Component;

/**
 * 流程信息表Dao层
 * @author ych
 */
@Component("processDao")
public class ProcessDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	/**
	 * 获取流程实例状态
	 * @param processInstanceId
	 * @return
	 */
	public ProcessInfoEntity selectProcessInfoById(String processInstanceId){
		return entityManager.find(ProcessInfoEntity.class, processInstanceId);
	}
}
