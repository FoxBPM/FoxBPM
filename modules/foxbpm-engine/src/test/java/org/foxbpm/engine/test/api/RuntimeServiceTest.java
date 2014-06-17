package org.foxbpm.engine.test.api;

import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Clear;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

public class RuntimeServiceTest extends AbstractFoxBpmTestCase {

	@Test
	public void testSVG() {
		this.runtimeService
				.createProcessInstanceSVGImageString("9caac2fc-8703-4f89-a64d-5e7f815886c8");
	}

	@Test
	public void testTokenQuery() {
		// TokenQuery tokenQuery = runtimeService.createTokenQuery();
		// tokenQuery.tokenId("2222");
		// List<Token> tokens = tokenQuery.list();
		// System.out.println("********************"+tokens.size());
		//
		// tokenQuery = runtimeService.createTokenQuery();
		// tokenQuery.processInstanceId("18aebfe6-c30a-40a8-ba6d-ce6c19114ba8");
		// tokens = tokenQuery.list();
		// System.out.println("********************"+tokens.size());
		//
		// tokenQuery = runtimeService.createTokenQuery();
		// tokenQuery.tokenIsEnd();
		// tokens = tokenQuery.list();
		// System.out.println("********************"+tokens.size());
		//
		// tokenQuery = runtimeService.createTokenQuery();
		// tokenQuery.tokenNotEnd();
		// tokens = tokenQuery.list();
		// System.out.println("********************"+tokens.size());

	}

	@Test
	@Clear
	@Deployment(resources = { "process_test_1.bpmn" })
	public void testProcessInstanceQuery() {
		// ProcessInstanceQuery processQuery =
		// runtimeService.createProcessInstanceQuery();
		// processQuery.processInstanceId("222");
		// List<ProcessInstance> process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.processDefinitionId("222");
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.processDefinitionKey("222");
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.processDefinitionName("222");
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.processDefinitionNameLike("222");
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.processDefinitionNameLike("%222%");
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.initiator("222");
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.initiatorLike("%222%");
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.subject("222");
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.subjectLike("%222%");
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.processInstanceBusinessKey("222");
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.processInstanceBusinessKeyLike("%222%");
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.taskParticipants("222");
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.processInstanceStatus(ProcessInstanceStatus.RUNNING);
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.isEnd();
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.notEnd();
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.processDefinitionId("222");
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
	}
}
