package org.foxbpm.kernel.runtime.impl;

import java.io.Serializable;

import org.foxbpm.kernel.runtime.KernelVariableScope;

public abstract class KernelVariableScopeImpl implements KernelVariableScope, Serializable {

	private static final long serialVersionUID = 1L;
	
	protected String id = null;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
//
//	protected Map<String, KernelVariableInstanceImpl> variableInstances = null;
//	
//	
//
//
//	//
////	
//	protected abstract List<KernelVariableInstanceImpl> loadVariableInstances();
////
////	protected abstract KernelVariableScopeImpl getParentVariableScope();
////
//	protected abstract void initializeVariableInstanceBackPointer(KernelVariableInstanceImpl variableInstance);
////
//	/** 初始化变量 */
//	protected void ensureVariableInstancesInitialized() {
//		if (variableInstances == null) {
//			variableInstances = new HashMap<String, KernelVariableInstanceImpl>();
//			List<KernelVariableInstanceImpl> variableInstancesList = loadVariableInstances();
//			for (KernelVariableInstanceImpl variableInstance : variableInstancesList) {
//				variableInstances.put(variableInstance.getName(), variableInstance);
//			}
//		}
//	}
//	
//	protected KernelVariableInstanceImpl createVariableInstance(String variableName, Object value, KernelVariableScopeImpl sourceVariableScope) {
//
//		KernelVariableInstanceImpl variableInstance = KernelVariableInstanceImpl.createAndInsert(variableName, null, value);
//		initializeVariableInstanceBackPointer(variableInstance);
//		variableInstances.put(variableName, variableInstance);
//
//		return null;
//	}
//	
//	public Map<String, KernelVariableInstanceImpl> getVariableInstances() {
//		ensureVariableInstancesInitialized();
//		return variableInstances;
//	}
//
//	/** 获取全部变量(包含父级) */
//	public Map<String, Object> getVariables() {
//		return collectVariables(new HashMap<String, Object>());
//	}
//
//	/** 收集变量 */
//	protected Map<String, Object> collectVariables(HashMap<String, Object> variables) {
//		ensureVariableInstancesInitialized();
//		KernelVariableScopeImpl parentScope = getParentVariableScope();
//		if (parentScope != null) {
//			variables.putAll(parentScope.collectVariables(variables));
//		}
//		for (KernelVariableInstanceImpl variableInstance : variableInstances.values()) {
//			variables.put(variableInstance.getName(), variableInstance.getValue());
//		}
//		return variables;
//	}
//
//	/** 获取指定变量(包含父级) */
//	public Object getVariable(String variableName) {
//		ensureVariableInstancesInitialized();
//		KernelVariableInstanceImpl variableInstance = variableInstances.get(variableName);
//		if (variableInstance != null) {
//			return variableInstance.getValue();
//		}
//		KernelVariableScope parentScope = getParentVariableScope();
//		if (parentScope != null) {
//			return parentScope.getVariable(variableName);
//		}
//		return null;
//	}
//
//	/** 获取局部变量 */
//	public Object getVariableLocal(String variableName) {
//		ensureVariableInstancesInitialized();
//		KernelVariableInstanceImpl variableInstance = variableInstances.get(variableName);
//		if (variableInstance != null) {
//			return variableInstance.getValue();
//		}
//		return null;
//	}
//
//	/** 是否含有变量(包含父级) */
//	public boolean hasVariables() {
//		ensureVariableInstancesInitialized();
//		if (!variableInstances.isEmpty()) {
//			return true;
//		}
//		KernelVariableScope parentScope = getParentVariableScope();
//		if (parentScope != null) {
//			return parentScope.hasVariables();
//		}
//		return false;
//	}
//
//	/** 是否含有局部变量 */
//	public boolean hasVariablesLocal() {
//		ensureVariableInstancesInitialized();
//		return !variableInstances.isEmpty();
//	}
//
//	/** 是否含有指定变量(包含父级) */
//	public boolean hasVariable(String variableName) {
//		if (hasVariableLocal(variableName)) {
//			return true;
//		}
//		KernelVariableScope parentScope = getParentVariableScope();
//		if (parentScope != null) {
//			return parentScope.hasVariable(variableName);
//		}
//		return false;
//	}
//
//	/** 是否含有指定局部变量 */
//	public boolean hasVariableLocal(String variableName) {
//		ensureVariableInstancesInitialized();
//		return variableInstances.containsKey(variableName);
//	}
//
//	/** 收集变量的name(包含父级) */
//	protected Set<String> collectVariableNames(Set<String> variableNames) {
//		ensureVariableInstancesInitialized();
//		KernelVariableScopeImpl parentScope = getParentVariableScope();
//		if (parentScope != null) {
//			variableNames.addAll(parentScope.collectVariableNames(variableNames));
//		}
//		for (KernelVariableInstanceImpl variableInstance : variableInstances.values()) {
//			variableNames.add(variableInstance.getName());
//		}
//		return variableNames;
//	}
//
//	/** 获取变量Name集合(包含父级) */
//	public Set<String> getVariableNames() {
//		return collectVariableNames(new HashSet<String>());
//	}
//
//	/** 获取所有的局部变量 */
//	public Map<String, Object> getVariablesLocal() {
//		Map<String, Object> variables = new HashMap<String, Object>();
//		ensureVariableInstancesInitialized();
//		for (KernelVariableInstanceImpl variableInstance : variableInstances.values()) {
//			variables.put(variableInstance.getName(), variableInstance.getValue());
//		}
//		return variables;
//	}
//
//	/** 获取所有的局部变量name */
//	public Set<String> getVariableNamesLocal() {
//		ensureVariableInstancesInitialized();
//		return variableInstances.keySet();
//	}
//
//	/** 创建局部变量 */
//	public void createVariablesLocal(Map<String, ? extends Object> variables) {
//		if (variables != null) {
//			for (Map.Entry<String, ? extends Object> entry : variables.entrySet()) {
//				createVariableLocal(entry.getKey(), entry.getValue());
//			}
//		}
//	}
//
//	/** 更新变量(包含父级) */
//	public void setVariables(Map<String, ? extends Object> variables) {
//		if (variables != null) {
//			for (String variableName : variables.keySet()) {
//				setVariable(variableName, variables.get(variableName));
//			}
//		}
//	}
//
//	/** 更新局部变量 */
//	public void setVariablesLocal(Map<String, ? extends Object> variables) {
//		if (variables != null) {
//			for (String variableName : variables.keySet()) {
//				setVariableLocal(variableName, variables.get(variableName));
//			}
//		}
//	}
//
//	/** 移除全部变量(包含父级) */
//	public void removeVariables() {
//		ensureVariableInstancesInitialized();
//		Set<String> variableNames = new HashSet<String>(variableInstances.keySet());
//		for (String variableName : variableNames) {
//			removeVariable(variableName);
//		}
//	}
//
//	/** 移除局部变量 */
//	public void removeVariablesLocal() {
//		List<String> variableNames = new ArrayList<String>(getVariableNamesLocal());
//		for (String variableName : variableNames) {
//			removeVariableLocal(variableName);
//		}
//	}
//
//	/** 删除变量实例在离开容器的时候 */
//	public void deleteVariablesInstanceForLeavingScope() {
//		List<String> variableNames = new ArrayList<String>(getVariableNamesLocal());
//		for (String variableName : variableNames) {
//			ensureVariableInstancesInitialized();
//			variableInstances.remove(variableName);
//		}
//	}
//
//	/** 移除变量(包含父级) */
//	public void removeVariables(Collection<String> variableNames) {
//		if (variableNames != null) {
//			for (String variableName : variableNames) {
//				removeVariable(variableName);
//			}
//		}
//	}
//
//	/** 移除局部变量 */
//	public void removeVariablesLocal(Collection<String> variableNames) {
//		if (variableNames != null) {
//			for (String variableName : variableNames) {
//				removeVariableLocal(variableName);
//			}
//		}
//	}
//
//	/** 更新变量 */
//	public void setVariable(String variableName, Object value) {
//		setVariable(variableName, value, getSourceVariableScope());
//	}
//
//	/** 更新变量 */
//	protected void setVariable(String variableName, Object value, KernelVariableScopeImpl sourceVariableScope) {
//		if (hasVariableLocal(variableName)) {
//			setVariableLocal(variableName, value, sourceVariableScope);
//			return;
//		}
//		KernelVariableScopeImpl parentVariableScope = getParentVariableScope();
//		if (parentVariableScope != null) {
//			if (sourceVariableScope == null) {
//				parentVariableScope.setVariable(variableName, value);
//			} else {
//				parentVariableScope.setVariable(variableName, value, sourceVariableScope);
//			}
//			return;
//		}
//		createVariableLocal(variableName, value);
//	}
//
//	public Object setVariableLocal(String variableName, Object value) {
//		return setVariableLocal(variableName, value, getSourceVariableScope());
//	}
//
//	public Object setVariableLocal(String variableName, Object value, KernelVariableScopeImpl sourceVariableScope) {
//		ensureVariableInstancesInitialized();
//		KernelVariableInstanceImpl variableInstance = variableInstances.get(variableName);
//		if (variableInstance == null) {
//			createVariableLocal(variableName, value);
//		} else {
//			updateVariableInstance(variableInstance, value, sourceVariableScope);
//		}
//
//		return null;
//	}
//
//	public void createVariableLocal(String variableName, Object value) {
//		createVariableLocal(variableName, value, getSourceVariableScope());
//	}
//
//	/**
//	 * only called when a new variable is created on this variable scope. This
//	 * method is also responsible for propagating the creation of this variable
//	 * to the history.
//	 */
//	protected void createVariableLocal(String variableName, Object value, KernelVariableScopeImpl sourceVariableScope) {
//		ensureVariableInstancesInitialized();
//
//		if (variableInstances.containsKey(variableName)) {
//			throw new KernelException("variable '" + variableName
//					+ "' already exists. Use setVariableLocal if you want to overwrite the value");
//		}
//
//		createVariableInstance(variableName, value, sourceVariableScope);
//	}
//
//	public void removeVariable(String variableName) {
//		removeVariable(variableName, getSourceVariableScope());
//	}
//
//	protected void removeVariable(String variableName, KernelVariableScopeImpl sourceVariableScope) {
//		ensureVariableInstancesInitialized();
//		if (variableInstances.containsKey(variableName)) {
//			removeVariableLocal(variableName);
//			return;
//		}
//		KernelVariableScopeImpl parentVariableScope = getParentVariableScope();
//		if (parentVariableScope != null) {
//			if (sourceVariableScope == null) {
//				parentVariableScope.removeVariable(variableName);
//			} else {
//				parentVariableScope.removeVariable(variableName, sourceVariableScope);
//			}
//		}
//	}
//
//	public void removeVariableLocal(String variableName) {
//		removeVariableLocal(variableName, getSourceVariableScope());
//	}
//
//	protected KernelVariableScopeImpl getSourceVariableScope() {
//		return null;
//	}
//
//	protected void removeVariableLocal(String variableName, KernelVariableScopeImpl sourceVariableScope) {
//		ensureVariableInstancesInitialized();
//		KernelVariableInstanceImpl variableInstance = variableInstances.remove(variableName);
//		if (variableInstance != null) {
//			deleteVariableInstanceForExplicitUserCall(variableInstance, sourceVariableScope);
//		}
//	}
//
//	protected void deleteVariableInstanceForExplicitUserCall(KernelVariableInstanceImpl variableInstance,
//			KernelVariableScopeImpl sourceVariableScope) {
//		variableInstance.delete();
//		variableInstance.setValue(null);
//
//	}
//
//	protected void updateVariableInstance(KernelVariableInstanceImpl variableInstance, Object value, KernelVariableScopeImpl sourceVariableScope) {
//
//		
//		/*
//		// type should be changed
//		if ((variableInstance != null) && (!variableInstance.getType().isAbleToStore(value))) {
//			VariableTypes variableTypes = Context.getProcessEngineConfiguration().getVariableTypes();
//			VariableType newType = variableTypes.findVariableType(value);
//			variableInstance.setValue(null);
//			variableInstance.setType(newType);
//			variableInstance.forceUpdate();
//		}
//		variableInstance.setValue(value);*/
//
//	}
//

//
//
//	/**
//	 * Execution variable updates have activity instance ids, but historic task
//	 * variable updates don't.
//	 */
//	protected boolean isActivityIdUsedForDetails() {
//		return true;
//	}
//


}
