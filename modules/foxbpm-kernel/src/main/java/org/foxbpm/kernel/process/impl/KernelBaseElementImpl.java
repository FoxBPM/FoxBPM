package org.foxbpm.kernel.process.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.foxbpm.kernel.process.KernelBaseElement;

public class KernelBaseElementImpl implements KernelBaseElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String id;
	protected KernelProcessDefinitionImpl processDefinition;
	protected Map<String, Object> properties;

	public KernelBaseElementImpl(String id, KernelProcessDefinitionImpl processDefinition) {
		this.id = id;
		this.processDefinition = processDefinition;
	}

	public void setProperty(String name, Object value) {
		if (properties == null) {
			properties = new HashMap<String, Object>();
		}
		properties.put(name, value);
	}

	public Object getProperty(String name) {
		if (properties == null) {
			return null;
		}
		return properties.get(name);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getProperties() {
		if (properties == null) {
			return Collections.EMPTY_MAP;
		}
		return properties;
	}

	// getters and setters
	// //////////////////////////////////////////////////////

	public String getId() {
		return id;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public KernelProcessDefinitionImpl getProcessDefinition() {
		return processDefinition;
	}

}
