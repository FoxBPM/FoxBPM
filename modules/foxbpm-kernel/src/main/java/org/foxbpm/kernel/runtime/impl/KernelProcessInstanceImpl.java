/**
 * Copyright 1996-2014 FoxBPM ORG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author kenshin
 */
package org.foxbpm.kernel.runtime.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.foxbpm.kernel.event.KernelEvent;
import org.foxbpm.kernel.process.KernelException;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelProcessDefinitionImpl;
import org.foxbpm.kernel.runtime.InterpretableProcessInstance;
import org.foxbpm.kernel.runtime.KernelProcessInstance;

public class KernelProcessInstanceImpl implements InterpretableProcessInstance {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected KernelProcessDefinitionImpl processDefinition;

	protected KernelTokenImpl rootToken;

	protected KernelFlowNodeImpl startFlowNode;

	/** 流程令牌集合 */
	protected List<KernelTokenImpl> tokens = new ArrayList<KernelTokenImpl>();

	// 父流程
	protected KernelProcessInstanceImpl parentProcessInstance;

	// 父流程实例令牌
	protected KernelTokenImpl parentProcessInstanceToken;

	protected boolean isEnded = false;

	/**
	 * 获取父流程实例
	 * 
	 * @return
	 */
	public KernelProcessInstanceImpl getParentProcessInstance() {
		ensureParentProcessInstanceInitialized();
		return parentProcessInstance;
	}

	/**
	 * 设置父流程实例
	 * 
	 * @param parentProcessInstance
	 *            父流程实例
	 */
	public void setParentProcessInstance(InterpretableProcessInstance parentProcessInstance) {
		this.parentProcessInstance = (KernelProcessInstanceImpl) parentProcessInstance;
	}

	public void ensureParentProcessInstanceTokenInitialized() {

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

	public KernelProcessInstanceImpl() {
		this(null);
	}

	public KernelProcessInstanceImpl(KernelFlowNodeImpl flowNode) {

		// 设置流程实例启动的节点
		this.startFlowNode = flowNode;

		KernelTokenImpl token = createRootToken();
		// 创建根令牌
		addToken(token);

	}

	public KernelTokenImpl createRootToken() {
		this.rootToken = createToken();
		this.rootToken.setProcessInstance(this);
		return this.rootToken;
	}

	/** 子类需要重写这个方法 */
	public KernelTokenImpl createToken() {
		return new KernelTokenImpl();
	}
	
	/** 子类需要重写这个方法创建自己的ID */
	public String createTokenId(){
		return UUID.randomUUID().toString();
	}
	

	/** 创建子令牌 */
	public KernelTokenImpl createChildrenToken(KernelTokenImpl parent) {
		KernelTokenImpl childrenToken = createToken();

		// 设置令牌的流程实例对象
		childrenToken.setProcessInstance(this);
		// 设置令牌所在的节点
		childrenToken.setFlowNode(parent.getFlowNode());

		childrenToken.setParent(parent);

		parent.getChildren().add(childrenToken);
		// 将生成的新节点放入流程实例令牌列表中
		addToken(childrenToken);

		return childrenToken;

	}

	/** 启动流程实例 */
	public void start() {

		if (this.rootToken.getFlowNode() == null) {
			// 触发流程实例启动事件
			rootToken.fireEvent(KernelEvent.PROCESS_START);
			// 将令牌放置到开始节点中
			rootToken.enter(startFlowNode);

		} else {
			throw new KernelException("流程实例已经启动!");
		}

	}

	/** 创建一个流程实例,子类需要重写他 */
	protected KernelProcessInstanceImpl newProcessInstance() {
		return new KernelProcessInstanceImpl();
	}

	public KernelProcessInstance createSubProcessInstance(KernelProcessDefinitionImpl processDefinition) {
		return createSubProcessInstance(processDefinition, this.rootToken);
	}

	public KernelProcessInstance createSubProcessInstance(KernelProcessDefinitionImpl processDefinition, KernelTokenImpl token) {
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

	/** 子类需要重写这个方法从持久层拿令牌 */
	public void ensureRootTokenInitialized() {

	}

	public KernelTokenImpl getRootToken() {
		ensureRootTokenInitialized();
		return rootToken;
	}

	public KernelProcessDefinitionImpl getProcessDefinition() {
		ensureProcessDefinitionInitialized();
		return processDefinition;
	}

	/** 子类需要重写这个方法从持久层拿流程定义 */
	public void ensureProcessDefinitionInitialized() {

	}

	public void setProcessDefinition(KernelProcessDefinitionImpl processDefinition) {
		this.processDefinition = processDefinition;
	}

	public List<KernelTokenImpl> getTokens() {
		return tokens;
	}


	public void addToken(KernelTokenImpl token) {

		if (token != null) {
			tokens.add(token);
		}

	}


	public boolean isEnded() {
		return isEnded;
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

	public void end() {
		isEnded = true;
		getRootToken().end();
		getRootToken().fireEvent(KernelEvent.PROCESS_END);
		if (getParentProcessInstance() != null) {
			signalParentProcessInstance();
		}
	}

	/** 子类可以重写这个方法实现自己的启动子流程方法 */
	protected void signalParentProcessInstance() {
		getParentProcessInstanceToken().signal();
	}
}
