package org.foxbpm.kernel.runtime;

import java.io.Serializable;
import java.util.Map;


public interface KernelToken extends Serializable {

	void signal();
	
	
	void end();
	
	
	Object getProperty(String name);

	Map<String, Object> getProperties();
	

}
