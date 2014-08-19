package org.foxbpm.engine.impl.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.foxbpm.engine.datavariable.VariableInstance;
import org.foxbpm.engine.db.HasRevision;
import org.foxbpm.engine.db.PersistentObject;
import org.foxbpm.engine.impl.datavariable.DataVariableDefinition;
import org.foxbpm.engine.impl.expression.ExpressionMgmt;
import org.foxbpm.engine.impl.mgmt.DataVariableMgmtInstance;
import org.foxbpm.engine.impl.util.GuidUtil;
import org.foxbpm.engine.scriptlanguage.AbstractScriptLanguageMgmt;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
import org.foxbpm.kernel.runtime.impl.KernelVariableInstanceImpl;

public class VariableInstanceEntity extends KernelVariableInstanceImpl implements VariableInstance, PersistentObject,
    HasRevision, Serializable {
	
	public final static String QUERY_DATA_KEY = "queryBizVariable";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// 需要持久化的字段 //////////////////////////////////////////////////////////
	
	protected String id;
	
	protected String processInstanceId;
	
	protected String processDefinitionId;
	
	protected String processDefinitionKey;
	
	protected String key;
	
	protected byte[] value;
	
	protected String className;
	
	protected String tokenId;
	
	protected String nodeId;
	
	protected String taskId;
	
	protected String type;
	
	protected String bizData;
	
	protected boolean isPersistence;
	
	protected Date archiveTime;
	
	protected DataVariableDefinition dataVariableDefinition;
	
	protected DataVariableMgmtInstance dataVariableMgmtInstance;
	
	protected AbstractScriptLanguageMgmt scriptLanguageMgmt;
	
	/** 控制并发修改标示 */
	protected int revision;
	
	public VariableInstanceEntity() {
		
	}
	
	public VariableInstanceEntity(DataVariableDefinition dataVariableDefinition, DataVariableMgmtInstance dataVariableMgmtInstance) {
		
		this.id = GuidUtil.CreateGuid();
		this.dataVariableDefinition = dataVariableDefinition;
		this.isPersistence = dataVariableDefinition.isPersistence();
		this.key = dataVariableDefinition.getId();
		this.dataVariableMgmtInstance = dataVariableMgmtInstance;
		this.processDefinitionId = dataVariableMgmtInstance.getProcessInstance().getProcessDefinition().getId();
		this.processDefinitionKey = dataVariableMgmtInstance.getProcessInstance().getProcessDefinition().getKey();
		this.processInstanceId = dataVariableMgmtInstance.getProcessInstance().getId();
		this.type = dataVariableDefinition.getBizType();
		
	}
	
	// get set //////////////////////////////////////////////////////////
	
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public byte[] getValue() {
		return value;
	}
	
	public Object getValueObject() {
		if (value == null) {
			return null;
		}
		return bytesToObject(value);
	}
	
	public void setValue(byte[] value) {
		this.value = value;
	}
	
	public void setValue(Object value) {
		this.value = ObjectToBytes(value);
	}
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getTaskId() {
		return taskId;
	}
	
	public void setTaskId(String taskId) {
		this.taskId = taskId;
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
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
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
	
	/**
	 * 是否为持久化变量
	 * 
	 * @return
	 */
	public boolean isPersistence() {
		return this.isPersistence;
	}
	
	public void setPersistence(boolean isPersistence) {
		this.isPersistence = isPersistence;
	}
	
	public Object getExpressionValue() {
		Object object = ExpressionMgmt.getVariable(getKey());
		value = ObjectToBytes(object);
		return object;
	}
	
	public byte[] getExpressionValueByte() {
		Object object = ExpressionMgmt.getVariable(getKey());
		value = ObjectToBytes(object);
		return ObjectToBytes(object);
	}
	
	public void setExpressionValue(Object expressionValue) {
		setValue(expressionValue);
		// ExpressionMgmt.setVariable(getKey(), expressionValue);
	}
	
	public void setExpressionValueByte(byte[] value) {
		setValue(value);
		// ExpressionMgmt.setVariable(getKey(), bytesToObject(value));
	}
	
	public void setRevision(int revision) {
		this.revision = revision;
	}
	
	public int getRevision() {
		return revision;
	}
	
	public int getRevisionNext() {
		return revision + 1;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Map<String, Object> getPersistentState() {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}
	
	public boolean isModified() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	
	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}
	
	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}
	
	public Object getDefaultExpressionValue(FlowNodeExecutionContext executionContext) {
		// 不需要持久化的数据变量的处理
		
		Object object = null;
		if (dataVariableDefinition != null && dataVariableDefinition.getExpression() != null) {
			object = dataVariableDefinition.getExpression().getValue(executionContext);
		}
		return object;
	}
	
	/**
	 * byte[] to long
	 * 
	 * @param b
	 * @return
	 */
	public static Object bytesToObject(byte[] b) {
		
		if (b.length > 0) {
			ObjectInput in = null;
			try {
				ByteArrayInputStream byteIn = new ByteArrayInputStream(b);
				in = new ObjectInputStream(byteIn);
				Object obj = in.readObject();
				
				if (obj != null) {
					return obj;
				}
				
			} catch (IOException e) {
				
			} catch (ClassNotFoundException e) {
				
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						
					}
				}
			}
			
			return null;
		} else {
			
			return null;
		}
	}
	
	public static byte[] ObjectToBytes(Object obj) {
		
		if (obj == null) {
			return null;
		}
		
		ObjectOutput out = null;
		try {
			ByteArrayOutputStream byteout = new ByteArrayOutputStream();
			out = new ObjectOutputStream(byteout);
			out.writeObject(obj);
			byte[] buf = byteout.toByteArray();
			
			return buf;
		} catch (IOException e) {
			return null;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					
				}
			}
		}
	}
}
