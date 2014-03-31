package org.foxbpm.kernel.runtime;

import java.io.Serializable;

import org.foxbpm.kernel.runtime.impl.KernelExecutionContextImpl;

public interface KernelToken extends Serializable {
	
	KernelExecutionContextImpl createExecutionContext();
	
	
	void signal(KernelExecutionContext executionContext);
	
	
	

}
