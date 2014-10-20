import junit.framework.TestCase;

import org.foxbpm.bpmn.parser.BpmnParseHandlerImpl;


public class TestAA extends TestCase{

	
	public static void testdd() {
		
		BpmnParseHandlerImpl bpmnParseHandlerImpl = new BpmnParseHandlerImpl();
		bpmnParseHandlerImpl.createProcessDefinition("", null);
	}
}
