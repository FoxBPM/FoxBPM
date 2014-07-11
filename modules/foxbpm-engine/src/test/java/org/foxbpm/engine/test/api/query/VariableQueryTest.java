package org.foxbpm.engine.test.api.query;

import java.util.List;

import org.foxbpm.engine.datavariable.VariableInstance;
import org.foxbpm.engine.datavariable.VariableQuery;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.junit.Test;

public class VariableQueryTest extends AbstractFoxBpmTestCase{

	
	@Test
	public void testVariableQuery(){
		VariableQuery variableQuery = runtimeService.createVariableQuery();
		variableQuery.id("dd");
		variableQuery.processInstanceId("processInstanceId");
		variableQuery.processDefinitionId("processDefinitionId");
		variableQuery.processDefinitionKey("processDefinitionKey");
		variableQuery.tokenId("tokenId");
		variableQuery.taskId("taskId");
		variableQuery.nodeId("nodeId");
		
		variableQuery.addVariableKey("key1");
		variableQuery.addVariableKey("key2");
		List<VariableInstance> variables = variableQuery.listPage(0, 15);
		System.out.println(variables.size());
	}
	
}
