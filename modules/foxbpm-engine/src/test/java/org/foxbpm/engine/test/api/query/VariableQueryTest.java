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
package org.foxbpm.engine.test.api.query;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.datavariable.VariableInstance;
import org.foxbpm.engine.datavariable.VariableQuery;
import org.foxbpm.engine.impl.entity.VariableInstanceEntity;
import org.foxbpm.engine.impl.expression.ExpressionMgmt;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.interceptor.CommandExecutor;
import org.foxbpm.engine.impl.util.GuidUtil;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.junit.Test;

public class VariableQueryTest extends AbstractFoxBpmTestCase{

	
	@Test
	public void testVariableQuery(){
		
		VariableQuery variableQuery = runtimeService.createVariableQuery();
		variableQuery.id("id");
		variableQuery.processInstanceId("processInstanceId");
		variableQuery.processDefinitionId("processDefinitionId");
		variableQuery.processDefinitionKey("processDefinitionKey");
		variableQuery.tokenId("tokenId");
		variableQuery.taskId("taskId");
		variableQuery.nodeId("nodeId");
		
		variableQuery.addVariableKey("key1");
		variableQuery.addVariableKey("key2");
		List<VariableInstance> variables = variableQuery.listPage(0, 15);
		assertEquals(0, variables.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSaveVariable(){
		
		String guid = GuidUtil.CreateGuid();
		VariableInstanceEntity variableEntity = new VariableInstanceEntity();
		variableEntity.setId(guid);
		variableEntity.setProcessInstanceId("processInstanceId");
		variableEntity.setProcessDefinitionId("processDefinitionId");
		variableEntity.setProcessDefinitionKey("processDefinitionKey");
		variableEntity.setTokenId("tokenId");
		variableEntity.setTaskId("taskId");
		variableEntity.setTokenId("tokenId");
		variableEntity.setNodeId("nodeId");
		variableEntity.setClassName("className");
		variableEntity.setBizData("bizData");
		CommandExecutor commandExecutor = processEngine.getProcessEngineConfiguration().getCommandExecutor();
		commandExecutor.execute(new SaveVariableCmd(variableEntity,false));
		VariableQuery variableQuery = runtimeService.createVariableQuery();
		variableQuery.id(guid);
		VariableInstance variableInstanceEntity = variableQuery.singleResult();
		assertEquals("processInstanceId", variableInstanceEntity.getProcessInstanceId());
		assertEquals("processDefinitionId",variableInstanceEntity.getProcessDefinitionId());
		assertEquals("processDefinitionKey",variableInstanceEntity.getProcessDefinitionKey());
		assertEquals("taskId",variableInstanceEntity.getTaskId());
		assertEquals("tokenId",variableInstanceEntity.getTokenId());
		assertEquals("nodeId",variableInstanceEntity.getNodeId());
		
		Map<String,String> value = (Map<String,String>)variableInstanceEntity.getValueObject();
		assertEquals("Test", value.get("test"));
		
		variableEntity.setProcessInstanceId("processInstanceId1");
		variableEntity.setProcessDefinitionId("processDefinitionId1");
		variableEntity.setProcessDefinitionKey("processDefinitionKey1");
		variableEntity.setTokenId("tokenId1");
		variableEntity.setTaskId("taskId1");
		variableEntity.setTokenId("tokenId1");
		variableEntity.setNodeId("nodeId1");
		variableEntity.setClassName("className1");
		variableEntity.setBizData("bizData1");
		
		commandExecutor.execute(new SaveVariableCmd(variableEntity,true));
		
		variableInstanceEntity = variableQuery.singleResult();
		assertEquals("processInstanceId1", variableInstanceEntity.getProcessInstanceId());
		assertEquals("processDefinitionId1",variableInstanceEntity.getProcessDefinitionId());
		assertEquals("processDefinitionKey1",variableInstanceEntity.getProcessDefinitionKey());
		assertEquals("taskId1",variableInstanceEntity.getTaskId());
		assertEquals("tokenId1",variableInstanceEntity.getTokenId());
		assertEquals("nodeId1",variableInstanceEntity.getNodeId());
		
		value = (Map<String,String>)variableInstanceEntity.getValueObject();
		assertEquals("Test2", value.get("test"));
		
		
	}
	
	
    private class SaveVariableCmd implements Command<Void>{
    	
    	private VariableInstanceEntity variableInstanceEntity;
    	private boolean isUpdate;
    	public SaveVariableCmd(VariableInstanceEntity variableEntity,boolean isUpdate) {
			this.variableInstanceEntity = variableEntity;
			this.isUpdate = isUpdate;
		}
    	
		@Override
		public Void execute(CommandContext commandContext) {
			
			Map<String,String> variableValue = new HashMap<String, String>();
			if(isUpdate){
				variableValue.put("test", "Test2");
				variableInstanceEntity.setExpressionValue(variableValue);
				ExpressionMgmt.setVariable(variableInstanceEntity.getKey(), variableValue);
				commandContext.getVariableManager().update(variableInstanceEntity);
			}else{
				variableValue.put("test", "Test");
				variableInstanceEntity.setExpressionValue(variableValue);
				ExpressionMgmt.setVariable(variableInstanceEntity.getKey(), variableValue);
				commandContext.getVariableManager().insert(variableInstanceEntity);
			}
			return null;
		}
    	
    }
	
}
