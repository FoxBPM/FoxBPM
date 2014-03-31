package org.foxbpm.kernel.runtime.impl;

import java.util.Map;

import org.foxbpm.kernel.runtime.KernelExecutionContext;

public class KernelExecutionContextImpl implements KernelExecutionContext {

	protected KernelTokenImpl token;
	
	public void setToken(KernelTokenImpl token) {
		this.token=token;
	}
	
	public KernelTokenImpl getToken() {
		return this.token;
	}

	public void setProperty(String name, Object value) {
		// TODO Auto-generated method stub
		
	}

	public Object getProperty(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Object> getProperties() {
		// TODO Auto-generated method stub
		return null;
	}


	
	
	
	

}
