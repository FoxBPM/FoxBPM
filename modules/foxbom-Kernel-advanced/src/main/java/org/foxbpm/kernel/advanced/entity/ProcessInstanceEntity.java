/**
 * Copyright 1996-2014 FoxBPM Co.,Ltd.
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
package org.foxbpm.kernel.advanced.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




import org.foxbpm.kernel.advanced.runtime.ProcessInstanceType;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelProcessDefinitionImpl;
import org.foxbpm.kernel.runtime.InterpretableProcessInstance;
import org.foxbpm.kernel.runtime.impl.KernelProcessInstanceImpl;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;

public class ProcessInstanceEntity extends KernelProcessInstanceImpl{

	// Field　字段
	// //////////////////////////////////////////////////////

	public static final String RULE_GET_PROCESS_INSTANCE_PERSISTENT_STATE = "getProcessInstancePersistentState";

	public static final String RULE_GET_PROCESS_INSTANCE_PERSISTENT_DBMAP = "getProcessInstancePersistentDbMap";

	public static final String RULE_PROCESS_INSTANCE_CLONE = "processInstanceClone";

	/**
	 * 
	 */
	private static final long serialVersionUID = -7192069864977069735L;


	// 持久化字段
	protected String id = null;

	protected String subject;

	protected String processDefinitionId;

	protected String processDefinitionKey;

	protected String definitionId;

	protected String rootTokenId;

	protected String parentProcessInstanceTokenId;

	protected String parentProcessInstanceId;

	protected String startAuthor;

	protected String initiator;

	protected String bizKey;

	protected Date startTime;

	protected Date endTime;

	protected Date updateTime;

	protected Date archiveTime;

	protected ProcessInstanceType instanceType;

	protected String processLocation;

	protected boolean isSuspended = false;

	

	@Override
	public KernelTokenImpl createRootToken() {
		this.rootToken = new TokenEntity();
		this.rootToken.setProcessInstance(this);
		this.rootTokenId = this.rootToken.getId();
		return this.rootToken;
	}

	@Override
	public void initialize() {

		this.taskMgmtInstance = new Taskm;

		this.taskMgmtInstance.setProcessInstance(this);

		this.dataVariableMgmtInstance = new DataVariableMgmtInstance(this);

		this.contextInstance = new ContextInstanceImpl(this);

	}

	// 构造函数 ///////////////////////////////

	public ProcessInstanceEntity() {
		super();
	}

	// Constructor 构造函数
	// /////////////////////////////////////////////////////
	public ProcessInstanceEntity(KernelFlowNodeImpl startFlowNode) {

		super(startFlowNode);

		// 设置流程实例的编号,通过静态方法获得Guid
		this.id = GuidUtil.CreateGuid();

		// 设置流程实例的根令牌

	}


	// 对象字段 /////////////////////

	// 流程定义
	protected KernelProcessDefinitionImpl processDefinition;

	// 任务管理器
	protected TaskMgmtInstance taskMgmtInstance;

	// 变量管理器
	protected DataVariableMgmtInstance dataVariableMgmtInstance;

	// 实例内容管理器
	protected ContextInstance contextInstance;



	// 对象字段 get set //////////////////////



	/**
	 * for setting the process definition, this setter must be used as
	 * subclasses can override
	 */
	public void ensureProcessDefinitionInitialized() {
		if ((processDefinition == null) && (processDefinitionId != null)) {
			ProcessDefinitionEntity deployedProcessDefinition = Context.getCommandContext().getProcessDefinitionManager()
					.findLatestProcessDefinitionById(this.processDefinitionId);
			setProcessDefinition(deployedProcessDefinition);
		}
	}

	/**
	 * 设置流程实例所使用的流程定义
	 * 
	 * @param processDefinition
	 */
	public void setProcessDefinition(KernelProcessDefinitionImpl processDefinition) {
		this.processDefinitionId = processDefinition.getId();
		this.processDefinitionKey = processDefinition.getKey();
		this.processDefinition = processDefinition;
	}

	/**
	 * 获取任务管理器
	 * 
	 * @return
	 */
	public TaskMgmtInstance getTaskMgmtInstance() {
		return this.taskMgmtInstance;
	}

	/**
	 * 获取变量管理器
	 * 
	 * @return
	 */
	public DataVariableMgmtInstance getDataVariableMgmtInstance() {
		return this.dataVariableMgmtInstance;
	}

	/**
	 * 获取实例内容管理器
	 * 
	 * @return
	 */
	public ContextInstance getContextInstance() {
		return this.contextInstance;
	}

	

	// 需要重写这个方法从持久层拿
	protected void ensureParentProcessInstanceInitialized() {
		if (this.parentProcessInstance == null) {
			if (StringUtil.isNotEmpty(this.parentProcessInstanceId)) {
				ProcessInstanceManager processInstanceManager = Context.getCommandContext().getProcessInstanceManager();
				InterpretableProcessInstance interpretableProcessInstance = processInstanceManager.findProcessInstanceById(this.parentProcessInstanceId);
				setParentProcessInstance(interpretableProcessInstance);
			}
		}
	}
	
	public KernelTokenImpl getRootToken() {
		ensureRootTokenInitialized();
		return rootToken;
	}
	
	/** 子类需要重写这个方法从持久层拿令牌  */
	public void ensureRootTokenInitialized(){
		if (this.rootToken == null) {
			if (StringUtil.isNotEmpty(this.rootTokenId)) {
				KernelTokenImpl rootToken = Context.getCommandContext().getTokenManager().findTokenById(this.rootTokenId);
				setRootToken(rootToken);
			}
		}

	}
	
	@Override
	public void ensureParentProcessInstanceTokenInitialized(){
		if (this.parentProcessInstanceToken == null) {

			if (StringUtil.isNotEmpty(this.parentProcessInstanceTokenId)) {

				TokenEntity parentProcessInstanceTokenObj = Context.getCommandContext().getTokenManager()
						.findTokenById(this.parentProcessInstanceTokenId);
				setParentProcessInstanceToken(parentProcessInstanceTokenObj);
			}
		}
	}
	

	


	/**
	 * 获取流程实例的令牌编号集合
	 */
	public List<String> getTokenIdList() {
		List<String> tokenIdList = new ArrayList<String>();
		List<KernelTokenImpl> tokens = getTokens();
		if (tokens != null) {

			for (KernelTokenImpl token : tokens) {
				tokenIdList.add(token.getId());
			}

		}
		return tokenIdList;
	}


	// 遗留方法 ////////////////////

	public void start() {

		// 设置流程实例的开始时间
		this.startTime = new Date();
		super.start();

	}


	


	public void signal() throws Exception {
		if (hasEnded()) {
			throw new FixFlowException("流程已经结束!");
		}
		getRootToken().signal();
	}
	
	public void signal(String tokenId) throws Exception {
		if (hasEnded()) {
			throw new FixFlowException("流程已经结束!");
		}
		getNamedTokens().get(tokenId).signal();
	}

	public void end() {
		getRootToken().end();
		if (endTime == null) {
			// 设置流程结束时间
			endTime = new Date();
			ExecutionContext executionContext = ProcessObjectFactory.FACTORYINSTANCE.createExecutionContext(getRootToken());
			// 插入流程结束任务
			if (this.getProcessDefinition().getStartElement() != null
					&& this.getProcessDefinition().getStartElement() instanceof StartEvent) {
				// 插入流程结束记录
				StartEventBehavior startEventBehavior = (StartEventBehavior) this.getProcessDefinition().getStartElement();
				if (startEventBehavior.isPersistence()) {
					createEndEventTask(executionContext);
				}
			}
			// createEndEventTask(executionContext);
			if (this.getParentProcessInstanceTokenId() != null && this.getParentProcessInstanceToken() != null) {
				FlowNode flowNode = this.getParentProcessInstanceToken().getFlowNode();
				if (flowNode instanceof CallActivity) {
					CallActivityBehavior callActivityBehavior = (CallActivityBehavior) flowNode;
					startParentProcessInstance(this.getParentProcessInstanceToken(), callActivityBehavior);
				}
			}
			// 更新实例状态为正常完成
			this.instanceType = ProcessInstanceType.COMPLETE;
			// 强制结束没有结束的子流程
			endSubProcess();
		}
	}

	/**
	 * 终止所有子流程
	 */
	public void terminationSubProcess() {
		// 判断子流程是否都结束，没结束都强制结束子流程。
		List<ProcessInstanceEntity> processInstanceEntities = this.getSubProcessInstanceList();
		if (processInstanceEntities.size() > 0) {
			for (ProcessInstanceEntity subProcessInstanceEntity : processInstanceEntities) {
				subProcessInstanceEntity.termination();
				try {
					Context.getCommandContext().getProcessInstanceManager().saveProcessInstance(subProcessInstanceEntity);
				} catch (Exception e) {
					e.printStackTrace();
					throw new FixFlowException("子流程强制终止的时候出现错误", e);
				}
			}
		}
	}

	/**
	 * 终止所有子流程
	 */
	public void terminationSubProcess(String tokenId) {
		// 判断子流程是否都结束，没结束都强制结束子流程。
		List<ProcessInstanceEntity> processInstanceEntities = this.getSubProcessInstanceList(tokenId);
		if (processInstanceEntities.size() > 0) {
			for (ProcessInstanceEntity subProcessInstanceEntity : processInstanceEntities) {
				subProcessInstanceEntity.termination();
				try {
					Context.getCommandContext().getProcessInstanceManager().saveProcessInstance(subProcessInstanceEntity);
				} catch (Exception e) {
					e.printStackTrace();
					throw new FixFlowException("子流程强制终止的时候出现错误", e);
				}
			}
		}

	}

	/**
	 * 结束所有子流程
	 */
	public void endSubProcess() {

		List<ProcessInstanceEntity> processInstanceEntities = this.getSubProcessInstanceList();
		if (processInstanceEntities.size() > 0) {
			for (ProcessInstanceEntity subProcessInstanceEntity : processInstanceEntities) {
				subProcessInstanceEntity.termination();
				try {
					Context.getCommandContext().getProcessInstanceManager().saveProcessInstance(subProcessInstanceEntity);
				} catch (Exception e) {
					e.printStackTrace();
					throw new FixFlowException("子流程强制结束的时候出现错误", e);
				}
			}
		}
	}

	/**
	 * 终止流程实例
	 */
	public void termination() {
		getRootToken().end(false);
		if (endTime == null) {
			// 设置流程结束时间
			endTime = new Date();
			// 更新实例状态为终止
			this.setInstanceType(ProcessInstanceType.TERMINATION);
			ExecutionContext executionContext = ProcessObjectFactory.FACTORYINSTANCE.createExecutionContext(getRootToken());
			// 触发流程实例终止事件
			getProcessDefinition().fireEvent(BaseElementEvent.EVENTTYPE_PROCESS_ABORT, executionContext);
			// 强制终止没有结束的子流程
			terminationSubProcess();
		}
	}

	private void createEndEventTask(ExecutionContext executionContext) {
		// 构造创建任务所需的数据
		String newTaskId = GuidUtil.CreateGuid();
		String newTaskProcessInstanceId = executionContext.getProcessInstance().getId();
		String newTaskProcessDefinitionId = executionContext.getProcessDefinition().getProcessDefinitionId();
		String newTaskTokenId = executionContext.getToken().getId();
		String newTaskNodeId = executionContext.getToken().getNodeId();
		String newTaskNodeName = executionContext.getToken().getFlowNode().getName();
		String newTaskDescription = newTaskNodeName;
		Date newTaskCreateTime = ClockUtil.getCurrentTime();
		int newTaskPriority = TaskInstance.PRIORITY_NORMAL;
		String newTaskProcessDefinitionKey = executionContext.getProcessDefinition().getProcessDefinitionKey();
		TaskInstanceType newTaskTaskInstanceType = TaskInstanceType.FIXENDEVENT;
		String newTaskProcessDefinitionName = executionContext.getProcessDefinition().getName();
		boolean isDraft = false;
		// 创建任务
		TaskInstanceEntity taskInstance = new TaskInstanceEntity();
		taskInstance.setId(newTaskId);
		taskInstance.setNodeName(newTaskNodeName);
		taskInstance.setProcessInstanceId(newTaskProcessInstanceId);
		taskInstance.setProcessDefinitionId(newTaskProcessDefinitionId);
		taskInstance.setTokenId(newTaskTokenId);
		taskInstance.setNodeId(newTaskNodeId);
		taskInstance.setName(newTaskNodeName);
		taskInstance.setDescription(newTaskDescription);
		taskInstance.setCreateTime(newTaskCreateTime);
		taskInstance.setPriority(newTaskPriority);
		taskInstance.setProcessDefinitionKey(newTaskProcessDefinitionKey);
		taskInstance.setTaskInstanceType(newTaskTaskInstanceType);
		taskInstance.setProcessDefinitionName(newTaskProcessDefinitionName);
		taskInstance.setDraft(isDraft);

		// taskInstance.setAssigneeId(Authentication.getAuthenticatedUserId());
		// taskInstance.setEndTime(newTaskEndTime);
		taskInstance.setCommandId(TaskCommandType.ENDEVENT);
		taskInstance.setCommandType(TaskCommandType.ENDEVENT);

		TaskCommandDef taskCommandDef = Context.getProcessEngineConfiguration().getTaskCommandDefMap().get(TaskCommandType.ENDEVENT);
		if (taskCommandDef != null) {
			taskInstance.setCommandMessage(taskCommandDef.getName());
		}
		taskInstance.setEndTime(ClockUtil.getCurrentTime());
		// taskInstance.setCallActivityInstanceId(subProcessInstanceId);
		executionContext.getProcessInstance().getTaskMgmtInstance().addTaskInstanceEntity(taskInstance);
		// Context.getCommandContext().getTaskManager().saveTaskInstanceEntity((TaskInstanceEntity)
		// taskInstance);
	}

	private void startParentProcessInstance(TokenEntity parentToken, CallActivityBehavior callActivityBehavior) {

		// 如果父流程的调用节点为异步调用则不再回启父流程直接结束
		if (callActivityBehavior.isAsync()) {
			return;
		}
		// 结束创建的那条子流程状态记录。
		callActivityBehavior.endSubTask(this.getId());
		ExecutionContext executionContext = ProcessObjectFactory.FACTORYINSTANCE.createExecutionContext(this.getRootToken());
		Map<String, Object> dataVarMap = new HashMap<String, Object>();
		SubProcessToDataSourceMapping subProcessToDataSourceMapping = callActivityBehavior.getSubProcessToDataSourceMapping();
		if (subProcessToDataSourceMapping != null) {
			for (DataVariableMapping dataVariableMapping : subProcessToDataSourceMapping.getDataVariableMapping()) {
				String subProcesId = "${" + dataVariableMapping.getSubProcesId() + "}";
				dataVarMap.put(dataVariableMapping.getDataSourceId(), ExpressionMgmt.execute(subProcesId, executionContext));
			}
		}
		ProcessEngine processEngine = ProcessEngineManagement.getDefaultProcessEngine();
		processEngine.getRuntimeService().tokenSignal(this.getParentProcessInstanceTokenId(), null, dataVarMap);
	}

	public Map<String, Object> getDataVariable() {
		QueryVariablesCommand queryVariablesCommand = new QueryVariablesCommand();
		queryVariablesCommand.setProcessInstanceId(this.getId());
		return Context.getCommandContext().getVariableManager().queryVariable(queryVariablesCommand);
	}

	public List<ProcessInstanceEntity> getSubProcessInstanceList() {
		ProcessInstanceManager processInstanceManager = Context.getCommandContext().getProcessInstanceManager();
		List<ProcessInstanceEntity> processInstanceEntities = processInstanceManager.findSubProcessInstanceById(this.getId());
		return processInstanceEntities;
	}

	public List<ProcessInstanceEntity> getSubProcessInstanceList(String tokenId) {
		ProcessInstanceManager processInstanceManager = Context.getCommandContext().getProcessInstanceManager();
		List<ProcessInstanceEntity> processInstanceEntities = processInstanceManager.findSubProcessInstanceByIdAndToken(this.getId(),
				tokenId);
		return processInstanceEntities;
	}

	/**
	 * 判断流程实例是否结束
	 * 
	 * @return
	 */
	public boolean hasEnded() {
		return (endTime != null);
	}

	public void resume() {
		isSuspended = false;
		this.instanceType = ProcessInstanceType.RUNNING;
		getRootToken().resume();
	}

	public void suspend() {
		isSuspended = true;
		this.instanceType = ProcessInstanceType.SUSPEND;
		getRootToken().suspend();
	}

	public int getVersion() {
		return getProcessDefinition().getVersion();
	}

	public boolean isSuspended() {
		return isSuspended;
	}

	// 过时方法
	public void setBizKeyWithoutCascade(String bizKey) {
		this.bizKey = bizKey;
	}

	public void setInitiatorWithoutCascade(String initiator) {
		this.initiator = initiator;
	}

	public void setIdWithoutCascade(String id) {
		this.id = id;
	}

	public void setSuspendedWithoutCascade(boolean isSuspended) {
		this.isSuspended = isSuspended;
	}

	public void setDefinitionIdWithoutCascade(String definitionId) {
		this.definitionId = definitionId;
	}

	public void setProcessDefinitionIdWithoutCascade(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public void setProcessDefinitionKeyWithoutCascade(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public void setStartTimeWithoutCascade(Date startTime) {
		this.startTime = startTime;
	}

	public void setEndTimeWithoutCascade(Date endTime) {
		this.endTime = endTime;
	}

	public void setRootTokenIdWithoutCascade(String rootTokenId) {
		this.rootTokenId = rootTokenId;
	}

	@Override
	public void setRootToken(KernelTokenImpl rootToken) {
		if (rootToken != null) {		
			this.rootTokenId = rootToken.getId();
		}
		super.setRootToken(rootToken);
	}
	
	// set和get方法
		public void setId(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public void setProcessDefinitionId(String processDefinitionId) {
			this.processDefinitionId = processDefinitionId;
		}

		public String getProcessDefinitionId() {
			return this.processDefinitionId;
		}

		public void setProcessDefinitionKey(String processDefinitionKey) {
			this.processDefinitionKey = processDefinitionKey;
		}

		public String getProcessDefinitionKey() {
			return this.processDefinitionKey;
		}

		public void setDefinitionId(String definitionId) {
			this.definitionId = definitionId;
		}

		public String getDefinitionId() {
			return this.definitionId;
		}

		public void setRootTokenId(String rootTokenId) {
			this.rootTokenId = rootTokenId;
		}

		public String getRootTokenId() {
			return this.rootTokenId;
		}

		public void setParentProcessInstanceId(String parentProcessInstanceId) {
			this.parentProcessInstanceId = parentProcessInstanceId;
		}

		public String getParentProcessInstanceId() {
			return parentProcessInstanceId;
		}
		
		@Override
		public void setParentProcessInstanceToken(KernelTokenImpl parentProcessInstanceToken) {
			if(parentProcessInstanceToken!=null){
				this.parentProcessInstanceTokenId=parentProcessInstanceToken.getId();
			}
			this.parentProcessInstanceToken = parentProcessInstanceToken;
		}

		public void setParentProcessInstanceTokenId(String parentProcessInstanceTokenId) {
			this.parentProcessInstanceTokenId = parentProcessInstanceTokenId;
		}

		public String getParentProcessInstanceTokenId() {
			return parentProcessInstanceTokenId;
		}

		public String getStartAuthor() {
			return startAuthor;
		}

		public void setStartAuthor(String startAuthor) {
			this.startAuthor = startAuthor;
		}

		public String getInitiator() {
			return initiator;
		}

		public void setInitiator(String initiator) {
			this.initiator = initiator;
		}

		public String getBizKey() {
			return bizKey;
		}

		public void setBizKey(String bizKey) {
			this.bizKey = bizKey;
		}

		public void setStartTime(Date startTime) {
			this.startTime = startTime;
		}

		public Date getStartTime() {
			return startTime;
		}

		public void setEndTime(Date endTime) {
			this.endTime = endTime;
		}

		public Date getEndTime() {
			return endTime;
		}

		public Date getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}

		public Date getArchiveTime() {
			return archiveTime;
		}

		public void setArchiveTime(Date archiveTime) {
			this.archiveTime = archiveTime;
		}

		public ProcessInstanceType getInstanceType() {
			return instanceType;
		}

		public void setInstanceType(ProcessInstanceType instanceType) {
			this.instanceType = instanceType;
		}

		public void setInstanceTypeString(String instanceType) {

			if (StringUtil.isNotEmpty(instanceType)) {
				this.instanceType = ProcessInstanceType.valueOf(instanceType);
			}

		}

		public String getProcessLocation() {
			return processLocation;
		}

		public void setProcessLocation(String processLocation) {
			this.processLocation = processLocation;
		}

		public void setSuspended(boolean isSuspended) {
			this.isSuspended = isSuspended;
		}

		public void setSuspendedString(String isSuspended) {
			if (StringUtil.isNotEmpty(isSuspended)) {
				this.isSuspended = StringUtil.getBoolean(isSuspended);
			}

		}

		public boolean getSuspended() {
			return this.isSuspended;
		}

	@Override
	public String getCloneRuleId() {
		// TODO Auto-generated method stub
		return RULE_PROCESS_INSTANCE_CLONE;
	}

	@Override
	public String getPersistentDbMapRuleId() {
		// TODO Auto-generated method stub
		return RULE_GET_PROCESS_INSTANCE_PERSISTENT_DBMAP;
	}

	@Override
	public String getPersistentStateRuleId() {
		// TODO Auto-generated method stub
		return RULE_GET_PROCESS_INSTANCE_PERSISTENT_STATE;
	}
}
