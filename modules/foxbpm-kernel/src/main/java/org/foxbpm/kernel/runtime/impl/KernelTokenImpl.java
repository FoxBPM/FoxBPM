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

import org.foxbpm.kernel.event.KernelEvent;
import org.foxbpm.kernel.process.KernelBaseElement;
import org.foxbpm.kernel.process.KernelSequenceFlow;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelProcessDefinitionImpl;
import org.foxbpm.kernel.process.impl.KernelSequenceFlowImpl;
import org.foxbpm.kernel.runtime.impl.KernelProcessInstanceImpl;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
import org.foxbpm.kernel.runtime.InterpretableExecutionContext;
import org.foxbpm.kernel.runtime.KernelToken;
import org.foxbpm.kernel.runtime.ListenerExecutionContext;

public class KernelTokenImpl implements FlowNodeExecutionContext, 
ListenerExecutionContext, 
KernelToken,
InterpretableExecutionContext  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected KernelProcessInstanceImpl processInstance;

	protected boolean isEnded = false;

	protected boolean isActive = true;

	protected KernelFlowNodeImpl currentFlowNode;

	protected KernelTokenImpl parent;

	protected boolean isLocked = false;

	protected boolean isSuspended = false;

	protected boolean isSubProcessRootToken = false;
	
	protected KernelSequenceFlowImpl sequenceFlow;

	/**
	 * 子令牌集合
	 */
	protected List<KernelTokenImpl> children = new ArrayList<KernelTokenImpl>();

	// 事件 ///////////////////////////////////////////////////////////////////
	protected String eventName;
	protected KernelBaseElement eventSource;
	protected int KernelListenerIndex = 0;
	protected KernelEvent nextEvent;
	protected boolean isOperating = false;

	public KernelFlowNodeImpl getFlowNode() {
		return currentFlowNode;
	}

	public void setFlowNode(KernelFlowNodeImpl flowNode) {
		this.currentFlowNode = flowNode;
	}

	public String getId() {
		return null;
	}

	public KernelProcessInstanceImpl getProcessInstance() {
		return processInstance;
	}

	public void setProcessInstance(KernelProcessInstanceImpl processInstance) {
		this.processInstance = processInstance;
	}
	
	public void enter(KernelFlowNodeImpl flowNode) {
		setFlowNode(flowNode);
		fireEvent(KernelEvent.NODE_ENTER);
		flowNode.getKernelFlowNodeBehavior().enter(this);
	}
	public void execute() {
		fireEvent(KernelEvent.NODE_EXECUTE);
		getFlowNode().getKernelFlowNodeBehavior().execute(this);
	}
	

	public void signal() {
		leave();
	}



	public void fireEvent(KernelEvent kernelEvent) {

		this.nextEvent = kernelEvent;
		if (!isOperating) {
			isOperating = true;
			while (nextEvent != null) {
				KernelEvent currentEvent = this.nextEvent;
				this.nextEvent = null;
				currentEvent.execute(this);
			}
			isOperating = false;
		}

	}

	public Integer getKernelListenerIndex() {
		return KernelListenerIndex;
	}

	public void setKernelListenerIndex(int kernelListenerIndex) {
		KernelListenerIndex = kernelListenerIndex;
	}
	
	public void leave() {
		getFlowNode().getKernelFlowNodeBehavior().leave(this);
	}


	public void take(KernelSequenceFlow sequenceFlow) {
		// 发生节点离开事件
		fireEvent(KernelEvent.NODE_LEAVE);
		// 执行线条的进入方法
		getFlowNode().getKernelFlowNodeBehavior().cleanData(this);
		sequenceFlow.take(this);
	}

	public void take(KernelFlowNodeImpl flowNode) {
		// 发生节点离开事件
		fireEvent(KernelEvent.NODE_LEAVE);
		getFlowNode().getKernelFlowNodeBehavior().cleanData(this);
		enter(flowNode);
	}

	public void end() {
		end(true);
	}

	public void end(boolean verifyParentTermination) {

		// 如果令牌已经有结束时间则不执行令牌结束方法
		if (!isEnded) {

			// 结束令牌.使他不能再启动父令牌
			isActive = false;
			// 结束日期的标志，表明此令牌已经结束。
			isEnded = true;
			// 结束所有子令牌
			if (getChildren() != null) {

				List<KernelTokenImpl> childrenTokens = getChildren();

				for (KernelTokenImpl childrenToken : childrenTokens) {
					if (!childrenToken.isEnded()) {
						childrenToken.end(false);
					}
				}
			}
			// 清理当前节点遗留信息
			getFlowNode().getKernelFlowNodeBehavior().cleanData(this);

			if (verifyParentTermination) {
				// 如果这是根令牌,则需要结束流程实例.
				notifyParentOfTokenEnd();
			}
		}
	}

	protected void notifyParentOfTokenEnd() {
		// 判断是否为根令牌
		if (isRoot()) {
			getProcessInstance().end();
		} else {
			if (getParent() != null) {
				// 下面这句话是用来当前存在分支时 一个分支走到结束另一个分支没结束的时候,不能结束他的父令牌
				// 只有当到达结束节点的令牌的同级分支都结束才能结束父令牌
				// 子流程多实例的时候 每个子流程结束的时候去触发验证完成条件
				if (isSignalParentToken()) {
					// 推动父令牌向后执行
					getParent().signal();

				}else{
					if (!getParent().hasActiveChildren()) {
						getParent().end();
					}
				}


			}
		}
	}

	/** 子类需要重写这个方法 */
	protected boolean isSignalParentToken() {

		return false;

	}

	public boolean hasActiveChildren() {
		boolean foundActiveChildToken = false;
		// 发现至少有一个子令牌,仍然活跃（没有结束.
		List<KernelTokenImpl> childrenTokens = getChildren();
		if (childrenTokens.size() > 0) {
			for (KernelTokenImpl childrenToken : childrenTokens) {
				if (childrenToken.isActive()) {
					foundActiveChildToken = true;
					return foundActiveChildToken;
				}
			}

		}
		return foundActiveChildToken;
	}

	/** 阻止令牌 */
	public void inactivate() {
		this.isActive = false;
	}

	public boolean isEnded() {
		return isEnded;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isActive() {
		return isActive;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public KernelBaseElement getEventSource() {
		return eventSource;
	}

	public void setEventSource(KernelBaseElement eventSource) {
		this.eventSource = eventSource;
	}

	public KernelProcessDefinitionImpl getProcessDefinition() {
		return getProcessInstance().getProcessDefinition();
	}

	public boolean isRoot() {
		return (getParent() == null);
	}

	/** 子类需要重写这个方法从持久层拿父令牌 */
	public void ensureParentInitialized() {

	}

	public KernelTokenImpl getParent() {

		ensureParentInitialized();
		return parent;
	}

	public void setParent(KernelTokenImpl parent) {
		this.parent = parent;
	}

	public List<KernelTokenImpl> getChildren() {
		return children;
	}

	public void setChildren(List<KernelTokenImpl> children) {
		this.children = children;
	}

	public void addChild(KernelTokenImpl token) {

		if (token != null) {
			getChildren().add(token);
		}

	}

	public KernelSequenceFlowImpl getSequenceFlow() {
		return sequenceFlow;
	}

	public void setSequenceFlow(KernelSequenceFlowImpl sequenceFlow) {
		this.sequenceFlow=sequenceFlow;
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
