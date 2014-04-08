package org.foxbpm.kernel.advanced.entity;

import java.util.Date;
import java.util.Map;

import org.foxbpm.kernel.advanced.datavariable.DataVariableDefinition;
import org.foxbpm.kernel.advanced.db.PersistentObject;
import org.foxbpm.kernel.advanced.mgmt.DataVariableMgmtInstance;
import org.foxbpm.kernel.advanced.util.ObjectOrBytesUtil;
import org.foxbpm.kernel.runtime.impl.KernelExecutionContextImpl;

public class VariableInstanceEntity implements PersistentObject{
	


	// 需要持久化的字段 //////////////////////////////////////////////////////////
	
	protected String id;
	
	protected String processInstanceId;

	protected String variableKey;

	protected byte[] variableValue;

	protected String variableClassName;

	protected String taskInstanceId;

	protected String tokenId;

	protected String nodeId;

	protected String variableType;

	protected String bizData;
	
	protected boolean isPersistence;
	
	protected Date archiveTime;
	
	// get set //////////////////////////////////////////////////////////
	
	/**
	 * 获取变量编号
	 */
	public String getId() {

		return this.id;
	}

	
	
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getVariableKey() {
		return variableKey;
	}

	public void setVariableKey(String variableKey) {
		this.variableKey = variableKey;
	}

	public byte[] getVariableValue() {
		return variableValue;
	}
	
	public Object getVariableObject() {
		
		
		return ObjectOrBytesUtil.bytesToObject(variableValue);
	}

	public void setVariableValue(byte[] variableValue) {
		this.variableValue = variableValue;
	}

	public String getVariableClassName() {
		return variableClassName;
	}

	public void setVariableClassName(String variableClassName) {
		this.variableClassName = variableClassName;
	}

	public String getTaskInstanceId() {
		return taskInstanceId;
	}

	public void setTaskInstanceId(String taskInstanceId) {
		this.taskInstanceId = taskInstanceId;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getVariableType() {
		return variableType;
	}

	public void setVariableType(String variableType) {
		this.variableType = variableType;
	}

	public String getBizData() {
		return bizData;
	}

	public void setBizData(String bizData) {
		this.bizData = bizData;
	}
	
	
	public Date getArchiveTime() {
		return archiveTime;
	}


	public void setArchiveTime(Date archiveTime) {
		this.archiveTime = archiveTime;
	}

	
	
	// 对象化元素 //////////////////////////////////////////////////////////

	protected DataVariableDefinition dataVariableDefinition;

	protected DataVariableMgmtInstance dataVariableMgmtInstance;
	
	public VariableInstanceEntity () {
	
	}


	public VariableInstanceEntity (DataVariableDefinition dataVariableDefinition,DataVariableMgmtInstance dataVariableMgmtInstance) {
	
		
		
		
		this.dataVariableDefinition = dataVariableDefinition;
		this.isPersistence=dataVariableDefinition.isPersistence();
		this.variableKey=dataVariableDefinition.getId();
		this.dataVariableMgmtInstance = dataVariableMgmtInstance;
		this.processInstanceId=dataVariableMgmtInstance.getProcessInstance().getId();
		this.variableType=dataVariableDefinition.getBizType();

	}
	
	/**
	 * 是否为持久化变量
	 * @return
	 */
	public boolean isPersistence() {
		return this.isPersistence;
	}
	public void setPersistence(boolean isPersistence) {
		this.isPersistence = isPersistence;
	}

	
	
	/** 需要子类重写 */
	public Object getExpressionValue() {

		return null;
	}

	/** 需要子类重写 */
	public void setExpressionValue(Object expressionValue) {

	}


	

	

	/** 需要子类重写 */
	public void executeExpression(KernelExecutionContextImpl executionContext) {

		

	}



	public void setId(String id) {
		// TODO Auto-generated method stub
		
	}



	public Map<String, Object> getPersistentState() {
		// TODO Auto-generated method stub
		return null;
	}


	
	
	

}
