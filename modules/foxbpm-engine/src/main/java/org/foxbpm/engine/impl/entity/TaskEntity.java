package org.foxbpm.engine.impl.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.foxbpm.engine.db.HasRevision;
import org.foxbpm.engine.db.PersistentObject;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.execution.ConnectorExecutionContext;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.expression.ExpressionMgmt;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.task.TaskCommandSystemType;
import org.foxbpm.engine.impl.task.TaskDefinition;
import org.foxbpm.engine.impl.util.ClockUtil;
import org.foxbpm.engine.impl.util.GuidUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.task.DelegateTask;
import org.foxbpm.engine.task.DelegationState;
import org.foxbpm.engine.task.IdentityLink;
import org.foxbpm.engine.task.IdentityLinkType;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskCommand;
import org.foxbpm.engine.task.TaskType;
import org.foxbpm.kernel.process.KernelFlowNode;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
import org.foxbpm.kernel.runtime.impl.KernelVariableScopeImpl;
import org.foxbpm.model.config.foxbpmconfig.TaskCommandDefinition;

public class TaskEntity extends KernelVariableScopeImpl implements Task, DelegateTask, PersistentObject, HasRevision {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String id;

	protected String name;
	
	protected String subject;

	protected String description;
	
	protected String completeDescription;

	protected String processInstanceId;

	protected String processDefinitionId;

	protected String processDefinitionKey;
	
	protected ProcessDefinitionEntity processDefinition;

	protected String processDefinitionName;
	
	protected int version;

	protected String tokenId;

	protected String nodeId;

	protected String nodeName;

	protected String parentId;

	protected String assignee;

	protected Date claimTime;

	protected Date createTime;

	protected Date startTime;

	protected Date endTime;

	protected Date dueDate;
	
	/** 流程实例启动时间 */
	protected Date processStartTime;
	
	/** 流程任务的发起人 */
	protected String processInitiator;

	protected boolean isBlocking = false;

	protected int priority = Task.PRIORITY_NORMAL;

	protected String category;

	protected String owner;

	protected DelegationState delegationState;

	protected String bizKey;

	protected String taskComment;

	protected String formUri;

	protected String formUriView;

	protected String taskGroup;

	protected String taskType = TaskType.FOXBPMTASK;

	protected boolean isCancelled = false;

	protected boolean isSuspended = false;

	protected boolean isOpen = true;

	protected boolean isDraft = false;

	protected int expectedExecutionTime = 0;

	protected String agent;

	protected String admin;

	protected String callActivityInstanceId;

	protected String pendingTaskId;

	protected Date archiveTime;

	protected String commandId;

	protected String commandType;

	protected String commandMessage;

	protected boolean isIdentityLinksInitialized = false;

	protected TokenEntity token;

	protected ProcessInstanceEntity processInstance;

	protected KernelFlowNode node;

	protected TaskDefinition taskDefinition;

	protected List<IdentityLinkEntity> taskIdentityLinks = new ArrayList<IdentityLinkEntity>();

	protected TaskEntity parentTask;

	protected boolean isAutoClaim = false;
	
	protected Map<String, Object>  paramMap=new HashMap<String, Object>();
	
	public TaskEntity() {
		
	}

	public TaskEntity(String taskId) {
		this.id = taskId;
	}

	/** 创建并持久化一个任务 */
	public static TaskEntity createAndInsert(FlowNodeExecutionContext executionContext) {
		TaskEntity task = create();
		task.insert(((TokenEntity) executionContext).getProcessInstance());
		return task;
	}

	public void insert(ProcessInstanceEntity processInstance) {
		CommandContext commandContext = Context.getCommandContext();
		commandContext.getTaskManager().insert(this);
		if (processInstance != null) {
			processInstance.addTask(this);
		}

	}

	public void update() {
		// task
		setOwner(this.getOwner());
		setAssignee(this.getAssignee());
		setDelegationState(this.getDelegationState());
		setName(this.getName());
		setDescription(this.getDescription());
		setPriority(this.getPriority());
		setCreateTime(this.getCreateTime());
		setDueDate(this.getDueDate());
	}

	/** 创建任务 */
	public static TaskEntity create() {
		TaskEntity task = new TaskEntity();
		task.setId(GuidUtil.CreateGuid());
		task.isIdentityLinksInitialized = true;
		task.createTime = ClockUtil.getCurrentTime();
		return task;
	}

	public ProcessInstanceEntity getProcessInstance() {
		return processInstance;
	}

	public void setProcessInstance(ProcessInstanceEntity processInstance) {
		setProcessInstanceId(processInstance.getId());
		setProcessDefinition((ProcessDefinitionEntity)processInstance.getProcessDefinition());
		this.processInstance = processInstance;
	}

	public List<IdentityLinkEntity> getIdentityLinks() {
		if (!isIdentityLinksInitialized) {
			taskIdentityLinks = Context.getCommandContext().getIdentityLinkManager().findIdentityLinksByTaskId(id);
			isIdentityLinksInitialized = true;
		}

		return taskIdentityLinks;
	}

	public TokenEntity getToken() {

		if ((token == null) && (tokenId != null)) {
			this.token = Context.getCommandContext().getTokenManager().findTokenById(tokenId);
		}
		return token;

	}

	public void setToken(TokenEntity token) {
		setTokenId(token.getId());
		setProcessInstance(token.getProcessInstance());
		this.token = token;
	}
	
	protected void ensureProcessDefinitionInitialized() {

		if (processDefinition == null && processDefinitionId != null) {
			ProcessDefinitionEntity processDefinition = Context.getProcessEngineConfiguration().
					getDeploymentManager().findDeployedProcessDefinitionById(processDefinitionId);
			setProcessDefinition(processDefinition);
		}
		
	}
	
	public ProcessDefinitionEntity getProcessDefinition() {
		ensureProcessDefinitionInitialized();
		return processDefinition;
	}
	
	public void setProcessDefinition(ProcessDefinitionEntity processDefinition) {
		if(processDefinition!=null){
			processDefinitionId=processDefinition.getId();
			processDefinitionKey=processDefinition.getKey();
			processDefinitionName=processDefinition.getName();
		}
		this.processDefinition = processDefinition;
	}


	protected void ensureNodeInitialized() {
		
		ensureProcessDefinitionInitialized();
		
		if (node == null && nodeId != null&& processDefinition!=null) {
			KernelFlowNode flowNode=processDefinition.findFlowNode(nodeId);
			if(flowNode!=null){
				setNode(flowNode);
			}
		}
	}
	
	public KernelFlowNode getNode() {
		return node;
	}

	public void setNode(KernelFlowNode node) {
		setNodeId(node.getId());
		this.node = node;
	}

	public TaskDefinition getTaskDefinition() {
		ensureProcessDefinitionInitialized();
		if(processDefinition!=null&&taskDefinition==null&&nodeId!=null){
			TaskDefinition taskDefinition=processDefinition.getTaskDefinitions().get(nodeId);
			setTaskDefinition(taskDefinition);
		}
		return taskDefinition;
	}

	public void setTaskDefinition(TaskDefinition taskDefinition) {
		this.taskDefinition = taskDefinition;
	}

	public List<IdentityLinkEntity> getTaskIdentityLinks() {
		return taskIdentityLinks;
	}

	public void setTaskIdentityLinks(List<IdentityLinkEntity> taskIdentityLinks) {
		this.taskIdentityLinks = taskIdentityLinks;
	}

	public TaskEntity getParentTask() {
		return parentTask;
	}

	public void setParentTask(TaskEntity parentTask) {
		this.parentTask = parentTask;
	}

	public DelegationState getDelegationState() {
		return delegationState;
	}

	public void setDelegationState(DelegationState delegationState) {
		this.delegationState = delegationState;
	}

	public String getDelegationStateString() {
		return (delegationState != null ? delegationState.toString() : null);
	}

	public void setDelegationStateString(String delegationStateString) {
		this.delegationState = (delegationStateString != null ? DelegationState.valueOf(DelegationState.class, delegationStateString)
				: null);
	}

	public void setRevision(int revision) {
		throw new FoxBPMException("未实现");
	}

	public int getRevision() {
		return 0;
	}

	public int getRevisionNext() {
		return 0;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public boolean isModified() {
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
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

	public String getProcessDefinitionName() {
		return processDefinitionName;
	}

	public void setProcessDefinitionName(String processDefinitionName) {
		this.processDefinitionName = processDefinitionName;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
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

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public Date getClaimTime() {
		return claimTime;
	}

	public void setClaimTime(Date claimTime) {
		this.claimTime = claimTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public boolean isBlocking() {
		return isBlocking;
	}

	public void setBlocking(boolean isBlocking) {
		this.isBlocking = isBlocking;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getBizKey() {
		return bizKey;
	}

	public void setBizKey(String bizKey) {
		this.bizKey = bizKey;
	}

	public String getTaskComment() {
		return taskComment;
	}

	public void setTaskComment(String taskComment) {
		this.taskComment = taskComment;
	}

	public String getFormUri() {
		return formUri;
	}

	public void setFormUri(String formUri) {
		this.formUri = formUri;
	}

	public String getFormUriView() {
		return formUriView;
	}

	public void setFormUriView(String formUriView) {
		this.formUriView = formUriView;
	}

	public String getTaskGroup() {
		return taskGroup;
	}

	public void setTaskGroup(String taskGroup) {
		this.taskGroup = taskGroup;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	public boolean isSuspended() {
		return isSuspended;
	}

	public void setSuspended(boolean isSuspended) {
		this.isSuspended = isSuspended;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public boolean isDraft() {
		return isDraft;
	}

	public void setDraft(boolean isDraft) {
		this.isDraft = isDraft;
	}

	public int getExpectedExecutionTime() {
		return expectedExecutionTime;
	}

	public void setExpectedExecutionTime(int expectedExecutionTime) {
		this.expectedExecutionTime = expectedExecutionTime;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getCallActivityInstanceId() {
		return callActivityInstanceId;
	}

	public void setCallActivityInstanceId(String callActivityInstanceId) {
		this.callActivityInstanceId = callActivityInstanceId;
	}

	public String getPendingTaskId() {
		return pendingTaskId;
	}

	public void setPendingTaskId(String pendingTaskId) {
		this.pendingTaskId = pendingTaskId;
	}

	public Date getArchiveTime() {
		return archiveTime;
	}

	public void setArchiveTime(Date archiveTime) {
		this.archiveTime = archiveTime;
	}

	public String getCommandId() {
		return commandId;
	}

	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}

	public String getCommandType() {
		return commandType;
	}

	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}

	public String getCommandMessage() {
		return commandMessage;
	}

	public void setCommandMessage(String commandMessage) {
		this.commandMessage = commandMessage;
	}

	public Object getVariableLocal(Object variableName) {
		throw new FoxBPMException("未实现");
	}

	public String getTaskDefinitionKey() {
		throw new FoxBPMException("未实现");
	}

	public ConnectorExecutionContext getExecutionContext() {
		throw new FoxBPMException("未实现");
	}
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getCompleteDescription() {
		return completeDescription;
	}

	public void setCompleteDescription(String completeDescription) {
		this.completeDescription = completeDescription;
	}

	public String getEventName() {
		throw new FoxBPMException("未实现");
	}

	public void addCandidateUser(String userId) {
		 addIdentityLink(userId, null,null, IdentityLinkType.CANDIDATE);
	}

	public void addCandidateUserEntity(UserEntity user) {
		 addIdentityLink(user.getUserId(), null,null, IdentityLinkType.CANDIDATE);
	}

	public void addCandidateUsers(Collection<String> candidateUsers) {
		if(candidateUsers != null){
			for(String userId : candidateUsers){
				addCandidateUser(userId);
			}
		}
	}

	public void addCandidateUserEntitys(Collection<UserEntity> candidateUsers) {
		if(candidateUsers != null){
			for(UserEntity user : candidateUsers){
				addCandidateUserEntity(user);
			}
		}
	}

	public void addCandidateGroup(String groupId, String groupType) {
		addGroupIdentityLink(groupId,groupType,IdentityLinkType.CANDIDATE);
	}

	public void addCandidateGroupEntity(GroupEntity group) {
		addCandidateGroup(group.getGroupId(),group.getGroupType());
	}

	public void addCandidateGroupEntitys(Collection<GroupEntity> candidateGroups) {
		if(candidateGroups != null){
			for(GroupEntity group : candidateGroups){
				addCandidateGroupEntity(group);
			}
		}
	}

	public void addUserIdentityLink(String userId, String identityLinkType) {
		addIdentityLink(userId, null, null, identityLinkType);
	}

	public void addGroupIdentityLink(String groupId, String groupType, String identityLinkType) {
		addIdentityLink(null, groupId, groupType , identityLinkType);
	}

	public void deleteCandidateUser(String userId) {
		throw new FoxBPMException("未实现");
	}

	public void deleteCandidateGroup(String groupId) {
		throw new FoxBPMException("未实现");
	}

	public void deleteUserIdentityLink(String userId, String identityLinkType) {
		throw new FoxBPMException("未实现");
	}

	public void deleteGroupIdentityLink(String groupId, String groupType, String identityLinkType) {
		throw new FoxBPMException("未实现");
	}
	
	public IdentityLinkEntity addIdentityLink(String userId, String groupId,String groupType, String type) {
		IdentityLinkEntity identityLinkEntity = new IdentityLinkEntity();
		identityLinkEntity.setId(GuidUtil.CreateGuid());
		getIdentityLinks().add(identityLinkEntity);
		identityLinkEntity.setTask(this);
		identityLinkEntity.setUserId(userId);
		identityLinkEntity.setGroupId(groupId);
		identityLinkEntity.setType(type);
		identityLinkEntity.setGroupType(groupType);
		//判断开始流程权限时会触发add方法，此时不应该存储数据
		if(this.id != null){
			identityLinkEntity.insert();
		}
		return identityLinkEntity;
	}

	public Set<IdentityLink> getCandidates() {
		throw new FoxBPMException("未实现");
	}
	
	public void setProcessInstanceTransientVariables(Map<String, Object> parameters) {
		if (getProcessInstance() != null) {
			if (parameters == null) {
				return;
			}
			for (String mapKey : parameters.keySet()) {
				ExpressionMgmt.setVariable(mapKey, parameters.get(mapKey));
			}
		}
	}
	
	public void setProcessInstancePersistenceVariablesVariables(Map<String, Object> parameters) {
		throw new FoxBPMException("未实现");
	}

	public boolean isAutoClaim() {
		return getTaskDefinition().isAutoClaim();
	}

	public void complete() {
		// 设置是否为草稿
		this.isDraft = false;
		if (this.endTime != null) {
			throw new FoxBPMException("任务已经结束,不能再进行处理.");
		}
		if (this.isSuspended) {
			throw new FoxBPMException("任务已经暂停不能再处理");
		}
		this.endTime = new Date();
		this.isOpen = false;
		// 触发事件
		if (tokenId != null) {
			TokenEntity token = getToken();
			token.removeTask(this);
			token.signal();
		}
	}
	
	/**
	 * 这个结束并不会去推动令牌向下。例如用在退回的时候。
	 */
	public void complete(TaskCommand taskCommand, String taskComment) {

		if (this.endTime != null) {
			throw new FoxBPMException("任务已经结束,不能再进行处理.");
		}
		if (this.isSuspended) {
			throw new FoxBPMException("任务已经暂停不能再处理");
		}

		this.endTime = new Date();

		this.isDraft = false;

		this.isOpen = false;

		this.taskComment = taskComment;

		if (taskCommand != null && taskCommand.getTaskCommandType() != null && !taskCommand.getTaskCommandType().equals("")) {
			String taskCommandType = taskCommand.getTaskCommandType();
			String taskCommandName = taskCommand.getName();
			// 设置流程自动结束信息 autoEnd
			this.setCommandId(taskCommand.getId());
			this.setCommandType(taskCommandType);
			if (taskCommandName == null) {
				
				TaskCommandDefinition taskCommandDef = Context.getProcessEngineConfiguration().getTaskCommandDefinition(taskCommandType);
				if (taskCommandDef != null) {
					this.setCommandMessage(taskCommandDef.getName());
				}
			} else {
				this.setCommandMessage(taskCommandName);

			}

		} else {

			this.setCommandId(TaskCommandSystemType.AUTOEND);
			this.setCommandType(TaskCommandSystemType.AUTOEND);
			TaskCommandDefinition taskCommandDef = Context.getProcessEngineConfiguration().getTaskCommandDefinition(TaskCommandSystemType.AUTOEND);
			if (taskCommandDef != null) {
				this.setCommandMessage(taskCommandDef.getName());
			}
		}

	}
	
	public Map<String, Object> getPersistentState() {
		Map<String,Object> persistentState = new HashMap<String, Object>();
		persistentState.put("id", getId());		
		persistentState.put("name", getName());		
		persistentState.put("subject", getSubject());
		persistentState.put("description", getDescription());
		persistentState.put("completeDescription",getCompleteDescription());
		persistentState.put("processInstanceId", getProcessInstanceId());
		persistentState.put("processDefinitionId", getProcessDefinitionId());
		persistentState.put("processDefinitionKey", getProcessDefinitionKey());		
		persistentState.put("processDefinitionName", getProcessDefinitionName());	
		persistentState.put("version", getVersion());
		persistentState.put("tokenId", getTokenId());
		persistentState.put("nodeId", getNodeId());
		persistentState.put("nodeName", getNodeName());		
		persistentState.put("parentId", getParentId());		
		persistentState.put("assignee", getAssignee());
		persistentState.put("claimTime", getClaimTime());		
		persistentState.put("createTime", getCreateTime());
		persistentState.put("startTime", getStartTime());		
		persistentState.put("endTime", getEndTime());		
		persistentState.put("dueDate", getDueDate());		
		persistentState.put("processStartTime", getProcessStartTime());		
		persistentState.put("processInitiator", getProcessInitiator());		
		persistentState.put("priority", String.valueOf(getPriority()));		
		persistentState.put("category", String.valueOf(getCategory()));		
		persistentState.put("owner", getOwner());		
		persistentState.put("delegationState", StringUtil.getString(getDelegationState()));		
		persistentState.put("bizKey", getBizKey());		
		persistentState.put("taskComment", getTaskComment());		
		persistentState.put("formUri", getFormUri());
		persistentState.put("formUriView", getFormUriView());		
		persistentState.put("taskGroup", getTaskGroup());		
		persistentState.put("taskType", StringUtil.getString(getTaskType()));		
		persistentState.put("isBlocking", String.valueOf(isBlocking()));
		persistentState.put("isCancelled", String.valueOf(isCancelled()));		
		persistentState.put("isSuspended", String.valueOf(isSuspended()));		
		persistentState.put("isOpen", String.valueOf(isOpen()));
		persistentState.put("isDraft", String.valueOf(isDraft()));
		persistentState.put("expectedExecutionTime", String.valueOf(getExpectedExecutionTime()));
		persistentState.put("agent", getAgent());		
		persistentState.put("admin", getAdmin());		
		persistentState.put("callActivityInstanceId", getCallActivityInstanceId());		
		persistentState.put("pendingTaskId",getPendingTaskId());		
		persistentState.put("archiveTime", getArchiveTime());		
		persistentState.put("commandId", getCommandId());
		persistentState.put("commandType", getCommandType());		
		persistentState.put("commandMessage", getCommandMessage());
		persistentState.put("processStartTime", getProcessStartTime());
		persistentState.put("processInitiator", getProcessInitiator());
		persistentState.put("completeDescription", getCompleteDescription());
		

		return persistentState;
	}

	@Override
	public boolean hasEnded() {
		return endTime!=null;
	}
	
	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

	public Date getProcessStartTime() {
		return processStartTime;
	}

	public void setProcessStartTime(Date processStartTime) {
		this.processStartTime = processStartTime;
	}

	public String getProcessInitiator() {
		return processInitiator;
	}

	public void setProcessInitiator(String processInitiator) {
		this.processInitiator = processInitiator;
	}


}
