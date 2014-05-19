package org.foxbpm.engine.test.api;

import java.util.List;

import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.runtime.ProcessInstanceQuery;
import org.foxbpm.engine.runtime.ProcessInstanceStatus;
import org.foxbpm.engine.runtime.Token;
import org.foxbpm.engine.runtime.TokenQuery;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;

public class RuntimeServiceTest extends AbstractFoxBpmTestCase {
	
	public void testTokenQuery(){
		TokenQuery tokenQuery = runtimeService.createTokenQuery();
		tokenQuery.tokenId("2222");
		List<Token> tokens = tokenQuery.list();
		System.out.println("********************"+tokens.size());
		
		tokenQuery = runtimeService.createTokenQuery();
		tokenQuery.processInstanceId("18aebfe6-c30a-40a8-ba6d-ce6c19114ba8");
		tokens = tokenQuery.list();
		System.out.println("********************"+tokens.size());
		
		tokenQuery = runtimeService.createTokenQuery();
		tokenQuery.tokenIsEnd();
		tokens = tokenQuery.list();
		System.out.println("********************"+tokens.size());
		
		tokenQuery = runtimeService.createTokenQuery();
		tokenQuery.tokenNotEnd();
		tokens = tokenQuery.list();
		System.out.println("********************"+tokens.size());
	
	}
	
	
	public void testProcessInstanceQuery(){
		ProcessInstanceQuery processQuery = runtimeService.createProcessInstanceQuery();
		processQuery.processInstanceId("222");
		List<ProcessInstance> process = processQuery.list();
		System.out.println("********************"+process.size());
		
		processQuery = runtimeService.createProcessInstanceQuery();
		processQuery.processDefinitionId("222");
		process = processQuery.list();
		System.out.println("********************"+process.size());
		
		processQuery = runtimeService.createProcessInstanceQuery();
		processQuery.processDefinitionKey("222");
		process = processQuery.list();
		System.out.println("********************"+process.size());
		
		processQuery = runtimeService.createProcessInstanceQuery();
		processQuery.processDefinitionName("222");
		process = processQuery.list();
		System.out.println("********************"+process.size());
		
		processQuery = runtimeService.createProcessInstanceQuery();
		processQuery.processDefinitionNameLike("222");
		process = processQuery.list();
		System.out.println("********************"+process.size());
		
		processQuery = runtimeService.createProcessInstanceQuery();
		processQuery.processDefinitionNameLike("%222%");
		process = processQuery.list();
		System.out.println("********************"+process.size());
		
		processQuery = runtimeService.createProcessInstanceQuery();
		processQuery.initiator("222");
		process = processQuery.list();
		System.out.println("********************"+process.size());
		
		processQuery = runtimeService.createProcessInstanceQuery();
		processQuery.initiatorLike("%222%");
		process = processQuery.list();
		System.out.println("********************"+process.size());
		
		processQuery = runtimeService.createProcessInstanceQuery();
		processQuery.subject("222");
		process = processQuery.list();
		System.out.println("********************"+process.size());
		
		processQuery = runtimeService.createProcessInstanceQuery();
		processQuery.subjectLike("%222%");
		process = processQuery.list();
		System.out.println("********************"+process.size());
		
		processQuery = runtimeService.createProcessInstanceQuery();
		processQuery.processInstanceBusinessKey("222");
		process = processQuery.list();
		System.out.println("********************"+process.size());
		
		processQuery = runtimeService.createProcessInstanceQuery();
		processQuery.processInstanceBusinessKeyLike("%222%");
		process = processQuery.list();
		System.out.println("********************"+process.size());
		
		processQuery = runtimeService.createProcessInstanceQuery();
		processQuery.taskParticipants("222");
		process = processQuery.list();
		System.out.println("********************"+process.size());
		
		processQuery = runtimeService.createProcessInstanceQuery();
		processQuery.processInstanceStatus(ProcessInstanceStatus.RUNNING);
		process = processQuery.list();
		System.out.println("********************"+process.size());
		
		processQuery = runtimeService.createProcessInstanceQuery();
		processQuery.isEnd();
		process = processQuery.list();
		System.out.println("********************"+process.size());
		
		processQuery = runtimeService.createProcessInstanceQuery();
		processQuery.notEnd();
		process = processQuery.list();
		System.out.println("********************"+process.size());
		
		processQuery = runtimeService.createProcessInstanceQuery();
		processQuery.processDefinitionId("222");
		process = processQuery.list();
		System.out.println("********************"+process.size());
	}
}
