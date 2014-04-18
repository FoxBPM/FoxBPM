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

import org.foxbpm.engine.impl.entity.TaskEntity;

/**
 * 流程定义管理器
 * @author Kenshin
 */
public class ProcessDefinitionManager extends AbstractManager {

	public void test(){
		System.out.println("manager执行了");
		TaskEntity task = new TaskEntity();
		task.setId("200");
		getSqlSession().insert("testInsert", task);
		throw  new RuntimeException("dd");
	}
	
}
