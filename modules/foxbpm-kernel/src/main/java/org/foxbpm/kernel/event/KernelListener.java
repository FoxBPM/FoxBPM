/**
 * 
 */
package org.foxbpm.kernel.event;

import java.io.Serializable;

import org.foxbpm.kernel.runtime.ListenerExecutionContext;

/**
 * @author kenshin
 *
 */
public interface KernelListener extends Serializable {
	
	/** 执行事件通知 */
	void notify(ListenerExecutionContext executionContext) throws Exception;
	
	/** 是否中断引擎执行 */
	//boolean isInterrupt();

}
