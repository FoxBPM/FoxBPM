package org.foxbpm.kernel.process;

import java.io.Serializable;


public interface KernelBaseElement extends Serializable {

	String getId();

	KernelProcessDefinition getProcessDefinition();

	Object getProperty(String name);

}
