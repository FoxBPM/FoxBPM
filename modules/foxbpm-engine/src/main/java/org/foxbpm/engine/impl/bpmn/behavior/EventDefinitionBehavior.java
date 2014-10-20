package org.foxbpm.engine.impl.bpmn.behavior;

import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

@SuppressWarnings("serial")
public abstract class EventDefinitionBehavior extends BaseElementBehavior {

	/**
	 * 事件定义的执行事件
	 * 
	 * @param executionContext
	 *            流程内容上下文
	 */
	public abstract void execute(FlowNodeExecutionContext executionContext, String eventType, Object[] params);
}
