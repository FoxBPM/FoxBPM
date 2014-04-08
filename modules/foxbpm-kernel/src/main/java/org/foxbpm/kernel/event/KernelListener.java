/**
 * 
 */
package org.foxbpm.kernel.event;

import org.foxbpm.kernel.runtime.KernelExecutionContext;

/**
 * @author kenshin
 *
 */
public interface KernelListener {
	
	/** 执行事件通知 */
	void notify(KernelExecutionContext executionContext) throws Exception;
	
	/** 是否中断引擎执行 */
	//boolean isInterrupt();

}
