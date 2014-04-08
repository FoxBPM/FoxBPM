package org.foxbpm.kernel.runtime.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.kernel.event.KernelEvent;
import org.foxbpm.kernel.process.KernelException;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelProcessDefinitionImpl;
import org.foxbpm.kernel.runtime.InterpretableProcessInstance;
import org.foxbpm.kernel.runtime.KernelProcessInstance;
import org.foxbpm.kernel.runtime.KernelToken;

public class KernelProcessInstanceImpl implements InterpretableProcessInstance {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected KernelProcessDefinitionImpl processDefinition;

	
	protected KernelTokenImpl rootToken;


	protected KernelFlowNodeImpl startFlowNode;
	
	/** 流程令牌集合*/
	protected List<KernelTokenImpl> tokens=new ArrayList<KernelTokenImpl>();
	
	

	protected Map<String, KernelTokenImpl> namedTokens=new HashMap<String, KernelTokenImpl>();
	

	// 父流程
	protected KernelProcessInstanceImpl parentProcessInstance;

	/**
	 * 获取父流程实例
	 * @return
	 */
	public KernelProcessInstanceImpl getParentProcessInstance() {
		ensureParentProcessInstanceInitialized();
		return parentProcessInstance;
	}

	/**
	 * 设置父流程实例
	 * @param parentProcessInstance 父流程实例
	 */
	public void setParentProcessInstance(InterpretableProcessInstance parentProcessInstance) {
		this.parentProcessInstance = (KernelProcessInstanceImpl)parentProcessInstance;
	}
	
	
	public void ensureParentProcessInstanceTokenInitialized(){
		
	}

	public KernelTokenImpl getParentProcessInstanceToken() {
		ensureParentProcessInstanceTokenInitialized();
		return parentProcessInstanceToken;
	}

	public void setParentProcessInstanceToken(KernelTokenImpl parentProcessInstanceToken) {
		this.parentProcessInstanceToken = parentProcessInstanceToken;
	}

	// 需要重写这个方法从持久层拿
	protected void ensureParentProcessInstanceInitialized() {
	}

	// 父流程实例令牌
	protected KernelTokenImpl parentProcessInstanceToken;



	public KernelProcessInstanceImpl() {
		this(null);
	}

	public KernelProcessInstanceImpl(KernelFlowNodeImpl flowNode) {

		// 设置流程实例启动的节点
		this.startFlowNode = flowNode;

		KernelTokenImpl token=createRootToken();
		// 创建根令牌
		addToken(token);

	}

	public KernelTokenImpl createRootToken() {
		this.rootToken = new KernelTokenImpl();
		this.rootToken.setProcessInstance(this);
		return this.rootToken;
	}

	/** 启动流程实例 */
	public void start() {

		if (this.rootToken.getFlowNode() == null) {
			// 创建基于Token的上下文
			KernelExecutionContextImpl executionContext = rootToken.createExecutionContext();
			//触发流程实例启动事件
			rootToken.fireEvent(KernelEvent.PROCESS_START, executionContext);
			// 将令牌放置到开始节点中
			startFlowNode.enter(executionContext);
			
		} else {
			throw new KernelException("流程实例已经启动!");
		}

	}

	/** 创建一个流程实例,子类需要重写他 */
	protected KernelProcessInstanceImpl newProcessInstance() {
		return new KernelProcessInstanceImpl();
	}

	public KernelProcessInstance createSubProcessInstance(KernelProcessDefinitionImpl processDefinition) {
		return createSubProcessInstance(processDefinition,this.rootToken);
	}
	
	public KernelProcessInstance createSubProcessInstance(KernelProcessDefinitionImpl processDefinition,KernelTokenImpl token) {
		KernelProcessInstanceImpl subProcessInstance = newProcessInstance();
		subProcessInstance.setParentProcessInstance(this);
		subProcessInstance.setProcessDefinition(processDefinition);
		subProcessInstance.setParentProcessInstanceToken(token);
		return subProcessInstance;
	}

	/** 这个方法需要子类重写 */
	public void initialize() {
		// TODO Auto-generated method stub

	}

	/** 这个方法需要子类重写 */
	public String getId() {
		return null;
	}
	
	
	/** 子类需要重写这个方法从持久层拿令牌  */
	public void ensureRootTokenInitialized(){
		
	}
	
	
	public KernelTokenImpl getRootToken() {
		ensureRootTokenInitialized();
		return rootToken;
	}


	
	
	public KernelProcessDefinitionImpl getProcessDefinition() {
		ensureProcessDefinitionInitialized();
		return processDefinition;
	}
	
	/** 子类需要重写这个方法从持久层拿流程定义  */
	public void ensureProcessDefinitionInitialized(){
		
	}


	public void setProcessDefinition(KernelProcessDefinitionImpl processDefinition) {
		this.processDefinition = processDefinition;
	}
	
	
	public List<KernelTokenImpl> getTokens() {
		return tokens;
	}

	public Map<String, KernelTokenImpl>  getNamedTokens() {
		return namedTokens;
	}
	
	public void addToken(KernelTokenImpl token){
		
		if(token!=null){
			if(namedTokens.containsKey(token.getId())){
				throw new KernelException("token '" + token.getId() + "' 已经存在!");
			}
			namedTokens.put(token.getId(), token);
			tokens.add(token);
		}
		
	}

	public KernelToken findToken(String tokenId) {
		return getNamedTokens().get(tokenId);
	}

	public boolean isEnded() {
		// TODO Auto-generated method stub
		return false;
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
}
