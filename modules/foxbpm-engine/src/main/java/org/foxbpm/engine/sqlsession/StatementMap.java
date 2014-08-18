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
		
		updateStatements.put(ProcessInstanceEntity.class, "updateProcessInstance");
		updateStatements.put(TaskEntity.class, "updateTask");
		updateStatements.put(TokenEntity.class, "updateToken");
		updateStatements.put(DeploymentEntity.class, "updateDeployment");
		updateStatements.put(ProcessDefinitionEntity.class, "updateProcessDefinition");
		updateStatements.put(ResourceEntity.class, "updateResource");
		updateStatements.put(VariableInstanceEntity.class, "updateVariable");
		updateStatements.put(AgentEntity.class, "updateAgentEntity");
		updateStatements.put(AgentDetailsEntity.class, "updateAgentDetailsEntity");
		
		selectStatements.put(ProcessInstanceEntity.class, "selectProcessInstanceById");
		selectStatements.put(TaskEntity.class, "selectTaskById");
		selectStatements.put(TokenEntity.class, "selectTokenById");
		selectStatements.put(DeploymentEntity.class, "selectDeploymentById");
		selectStatements.put(ProcessDefinitionEntity.class, "selectProcessDefinitionById");
		selectStatements.put(ResourceEntity.class, "selectResourceById");
		selectStatements.put(IdentityLinkEntity.class, "selectIdentityLinkById");
		selectStatements.put(VariableInstanceEntity.class, "selectVariableById");
		selectStatements.put(RunningTrackEntity.class, "selectRunningTrackByInstanceId");
		
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
