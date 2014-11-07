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
package org.foxbpm.engine.sqlsession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.foxbpm.engine.db.PersistentObject;
import org.foxbpm.engine.impl.agent.AgentDetailsEntity;
import org.foxbpm.engine.impl.agent.AgentEntity;
import org.foxbpm.engine.impl.entity.DeploymentEntity;
import org.foxbpm.engine.impl.entity.IdentityLinkEntity;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.entity.ProcessOperatingEntity;
import org.foxbpm.engine.impl.entity.ResourceEntity;
import org.foxbpm.engine.impl.entity.RunningTrackEntity;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.engine.impl.entity.VariableInstanceEntity;

public class StatementMap {

	protected static Map<Class<?>,String>  insertStatements = new ConcurrentHashMap<Class<?>, String>();
	protected static Map<Class<?>,String>  updateStatements = new ConcurrentHashMap<Class<?>, String>();
	protected static Map<Class<?>,String>  deleteStatements = new ConcurrentHashMap<Class<?>, String>();
	protected static Map<Class<?>,String>  selectStatements = new ConcurrentHashMap<Class<?>, String>();
	static{
		
		insertStatements.put(ProcessInstanceEntity.class, "insertProcessInstance");
		insertStatements.put(TaskEntity.class, "insertTask");
		insertStatements.put(TokenEntity.class, "insertToken");
		insertStatements.put(DeploymentEntity.class, "insertDeployment");
		insertStatements.put(ProcessDefinitionEntity.class, "insertProcessDefinition");
		insertStatements.put(ResourceEntity.class, "insertResource");
		insertStatements.put(IdentityLinkEntity.class, "insertIdentityLink");
		insertStatements.put(VariableInstanceEntity.class, "insertVariable");
		insertStatements.put(AgentEntity.class, "insertAgent");
		insertStatements.put(AgentDetailsEntity.class, "insertAgentDetails");
		insertStatements.put(RunningTrackEntity.class, "insertRunningTrack");
		insertStatements.put(ProcessOperatingEntity.class, "insertProcessOperating");
		
		
		
		
		updateStatements.put(ProcessInstanceEntity.class, "updateProcessInstance");
		updateStatements.put(TaskEntity.class, "updateTask");
		updateStatements.put(TokenEntity.class, "updateToken");
		updateStatements.put(DeploymentEntity.class, "updateDeployment");
		updateStatements.put(ProcessDefinitionEntity.class, "updateProcessDefinition");
		updateStatements.put(ResourceEntity.class, "updateResource");
		updateStatements.put(VariableInstanceEntity.class, "updateVariable");
		updateStatements.put(AgentEntity.class, "updateAgentEntity");
		updateStatements.put(AgentDetailsEntity.class, "updateAgentDetailsEntity");
		updateStatements.put(ProcessOperatingEntity.class, "updateProcessOperatingById");
		
		selectStatements.put(ProcessInstanceEntity.class, "selectProcessInstanceById");
		selectStatements.put(TaskEntity.class, "selectTaskById");
		selectStatements.put(TokenEntity.class, "selectTokenById");
		selectStatements.put(DeploymentEntity.class, "selectDeploymentById");
		selectStatements.put(ProcessDefinitionEntity.class, "selectProcessDefinitionById");
		selectStatements.put(ResourceEntity.class, "selectResourceById");
		selectStatements.put(IdentityLinkEntity.class, "selectIdentityLinkById");
		selectStatements.put(VariableInstanceEntity.class, "selectVariableById");
		selectStatements.put(RunningTrackEntity.class, "selectRunningTrackByInstanceId");
		selectStatements.put(ProcessOperatingEntity.class, "selectProcessOperatingById");
		
	}
	public static String getInsertStatement(PersistentObject object) {
	    return insertStatements.get(object.getClass());
	}

	public static String getUpdateStatement(PersistentObject object) {
	    return updateStatements.get(object.getClass());
	}

	public static String getDeleteStatement(Class<?> persistentObjectClass) {
	    return deleteStatements.get(persistentObjectClass);
	}

	public static String getSelectStatement(Class<?> persistentObjectClass) {
		  return selectStatements.get(persistentObjectClass);
	}
}
