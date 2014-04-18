package org.foxbpm.kernel.runtime.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.foxbpm.kernel.runtime.KernelVariableScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class KernelVariableScopeImpl implements KernelVariableScope,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger LOG = LoggerFactory
			.getLogger(KernelVariableScopeImpl.class);

	protected String id = null;

	protected Map<String, Object> variables = null;

	protected abstract KernelVariableScopeImpl getParentVariableScope();

	/**
	 * must be called before memberfield parent is used. can be used by
	 * subclasses to provide parent member field initialization.
	 */
	protected abstract void ensureParentInitialized();

	// variables
	// ////////////////////////////////////////////////////////////////

	public Object getVariable(String variableName) {
		ensureVariablesInitialized();

		// If value is found in this scope, return it
		if (variables.containsKey(variableName)) {
			return variables.get(variableName);
		}

		// If value not found in this scope, check the parent scope
		ensureParentInitialized();
		if (getParentVariableScope() != null) {
			return getParentVariableScope().getVariable(variableName);
		}

		// Variable is nowhere to be found
		return null;
	}

	public Map<String, Object> getVariables() {
		Map<String, Object> collectedVariables = new HashMap<String, Object>();
		collectVariables(collectedVariables);
		return collectedVariables;
	}

	protected void collectVariables(Map<String, Object> collectedVariables) {
		ensureParentInitialized();
		if (getParentVariableScope() != null) {
			getParentVariableScope().collectVariables(collectedVariables);
		}
		ensureVariablesInitialized();
		for (String variableName : variables.keySet()) {
			collectedVariables.put(variableName, variables.get(variableName));
		}
	}

	public void setVariables(Map<String, ? extends Object> variables) {
		ensureVariablesInitialized();
		if (variables != null) {
			for (String variableName : variables.keySet()) {
				setVariable(variableName, variables.get(variableName));
			}
		}
	}

	public void setVariable(String variableName, Object value) {
		ensureVariablesInitialized();
		if (variables.containsKey(variableName)) {
			setVariableLocally(variableName, value);
		} else {
			ensureParentInitialized();
			if (getParentVariableScope() != null) {
				getParentVariableScope().setVariable(variableName, value);
			} else {
				setVariableLocally(variableName, value);
			}
		}
	}

	public void setVariableLocally(String variableName, Object value) {
		LOG.debug("setting variable '{}' to value '{}' on {}", variableName,
				value, this);
		variables.put(variableName, value);
	}

	public boolean hasVariable(String variableName) {
		ensureVariablesInitialized();
		if (variables.containsKey(variableName)) {
			return true;
		}
		ensureParentInitialized();
		if (getParentVariableScope() != null) {
			return getParentVariableScope().hasVariable(variableName);
		}
		return false;
	}

	protected void ensureVariablesInitialized() {
		if (variables == null) {
			variables = new HashMap<String, Object>();
		}
	}

	public void createVariableLocal(String variableName, Object value) {
	}

	public void createVariablesLocal(Map<String, ? extends Object> variables) {
	}

	public Object getVariableLocal(Object variableName) {
		return null;
	}

	public Set<String> getVariableNames() {
		return null;
	}

	public Set<String> getVariableNamesLocal() {
		return null;
	}

	public Map<String, Object> getVariablesLocal() {
		return null;
	}

	public boolean hasVariableLocal(String variableName) {
		return false;
	}

	public boolean hasVariables() {
		return false;
	}

	public boolean hasVariablesLocal() {
		return false;
	}

	public void removeVariable(String variableName) {
	}

	public void removeVariableLocal(String variableName) {
	}

	public void removeVariables(Collection<String> variableNames) {
	}

	public void removeVariablesLocal(Collection<String> variableNames) {
	}

	public void removeVariables() {
	}

	public void removeVariablesLocal() {
	}

	public void deleteVariablesLocal() {
	}

	public Object setVariableLocal(String variableName, Object value) {
		return null;
	}

	public void setVariablesLocal(Map<String, ? extends Object> variables) {
	}

}
