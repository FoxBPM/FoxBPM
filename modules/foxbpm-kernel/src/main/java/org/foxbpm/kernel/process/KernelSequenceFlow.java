/**
 * 
 */
package org.foxbpm.kernel.process;

import org.foxbpm.kernel.runtime.KernelExecutionContext;

/**
 * @author kenshin
 *
 */
public interface KernelSequenceFlow extends KernelFlowElement {
	
	
	KernelFlowNode getSourceRef();

	KernelFlowNode getTargetRef();
	
	
	/**
	 * 线条条件处理适配器执行方法
	 * @param executionContext 执行内容
	 */
	boolean isContinue(KernelExecutionContext executionContext);
	
	
	
	/**
	 * 线条执行事件
	 * @param executionContext 执行内容
	 */
	public void take(KernelExecutionContext executionContext);

}
