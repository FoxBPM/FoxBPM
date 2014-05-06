package org.foxbpm.engine.impl.connector;

import org.foxbpm.engine.execution.ConnectorExecutionContext;
import org.foxbpm.kernel.event.KernelListener;
import org.foxbpm.kernel.runtime.ListenerExecutionContext;

public abstract class ConnectorHandler implements KernelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void notify(ListenerExecutionContext executionContext) throws Exception {
		execute((ConnectorExecutionContext)executionContext);
	}
	
	/**
	 * 连接器执行方法
	 * @param executionContext 上下文环境变量
	 * @throws Exception
	 */
	public abstract void execute(ConnectorExecutionContext executionContext) throws Exception;

}
