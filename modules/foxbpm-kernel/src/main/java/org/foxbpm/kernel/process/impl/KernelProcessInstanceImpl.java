package org.foxbpm.kernel.process.impl;

import java.util.Map;

import org.foxbpm.kernel.runtime.InterpretableProcessInstance;
import org.foxbpm.kernel.runtime.KernelToken;
import org.foxbpm.kernel.runtime.impl.KernelExecutionContextImpl;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;

public class KernelProcessInstanceImpl implements InterpretableProcessInstance {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected KernelProcessDefinitionImpl processDefinition;
	
	protected KernelTokenImpl rootToken;
	
	protected KernelFlowNodeImpl startFlowNode;
	

	public KernelProcessInstanceImpl() {

	}

	

	public KernelProcessInstanceImpl(KernelFlowNodeImpl flowNode) {
		
		//设置流程实例启动的节点
		this.startFlowNode=flowNode;
		
		//创建跟令牌
		createRootToken();
		

	}

	public KernelTokenImpl createRootToken() {
		this.rootToken = new KernelTokenImpl();
		this.rootToken.setProcessInstance(this);
		return this.rootToken;
	}
	
	/** 启动流程实例 */
	public void start() {
		//创建基于Token的上下文
		KernelExecutionContextImpl executionContext=this.rootToken.createExecutionContext();
		//将令牌放置到开始节点中
		this.startFlowNode.enter(executionContext);

	}
	
	/**  这个方法需要子类重写 */
	public void initialize() {
		// TODO Auto-generated method stub

	}

	/**  这个方法需要子类重写 */
	public String getId() {
		return null;
	}
	
	public boolean hasVariable(String variableName) {
		return false;
	}

	public void setVariable(String variableName, Object value) {
		// TODO Auto-generated method stub

	}

	public Object getVariable(String variableName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Object> getVariables() {
		// TODO Auto-generated method stub
		return null;
	}

	

	public void deleteCascade(String deleteReason) {
		// TODO Auto-generated method stub

	}

	public void setProcessDefinition(KernelProcessDefinitionImpl processDefinition) {
		this.processDefinition = processDefinition;
	}



	public KernelToken findToken(String tokenId) {
		// TODO Auto-generated method stub
		return null;
	}



	public boolean isEnded() {
		// TODO Auto-generated method stub
		return false;
	}

	
	

}
