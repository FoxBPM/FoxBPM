package connector.flowconnector.TestConnector2;


import org.foxbpm.engine.execution.ConnectorExecutionContext;
import org.foxbpm.engine.impl.connector.FlowConnectorHandler;

public class TestConnector2 implements FlowConnectorHandler {

	@Override
	public void execute(ConnectorExecutionContext executionContext) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("**************************测试连接器****************************");
	}

}