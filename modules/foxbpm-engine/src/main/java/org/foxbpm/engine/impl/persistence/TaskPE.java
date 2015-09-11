package org.foxbpm.engine.impl.persistence;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 任务推送实体
 * 
 * @author zengxianping
 *
 */
public class TaskPE {
	private String processInstanceId;// 流程实例编号
	private String processDefinitionKey;// 流程定义键
	private String processDefinitionId;// 流程定义编号
	private String bizKey;// 业务关联件
	private String taskId;// 任务编号
	private String taskSubject;// 任务主题
	private String nodeId;// 节点编号
	private String nodeName;// 节点名称
	private String formUriView;// 表单查看uri
	private String formUri;// 表单处理uri
	private String initiator;// 任务提交人
	private String commandType;// 命令类型
	private String assignee;// 任务处理人
	private String taskComment;// 任务处理意见
	private Date createTime;// 任务创建时间
	private Date endTime;// 任务完成时间

	public TaskPE() {
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getBizKey() {
		return bizKey;
	}

	public void setBizKey(String bizKey) {
		this.bizKey = bizKey;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskSubject() {
		return taskSubject;
	}

	public void setTaskSubject(String taskSubject) {
		this.taskSubject = taskSubject;
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

	public String getFormUriView() {
		return formUriView;
	}

	public void setFormUriView(String formUriView) {
		this.formUriView = formUriView;
	}

	public String getFormUri() {
		return formUri;
	}

	public void setFormUri(String formUri) {
		this.formUri = formUri;
	}

	public String getInitiator() {
		return initiator;
	}

	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}

	public String getCommandType() {
		return commandType;
	}

	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getTaskComment() {
		return taskComment;
	}

	public void setTaskComment(String taskComment) {
		this.taskComment = taskComment;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTF-8")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTF-8")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "TaskPE [processInstanceId=" + processInstanceId
				+ ", processDefinitionKey=" + processDefinitionKey
				+ ", processDefinitionId=" + processDefinitionId + ", bizKey="
				+ bizKey + ", taskId=" + taskId + ", taskSubject="
				+ taskSubject + ", nodeId=" + nodeId + ", nodeName=" + nodeName
				+ ", formUriView=" + formUriView + ", formUri=" + formUri
				+ ", initiator=" + initiator + ", commandType=" + commandType
				+ ", assignee=" + assignee + ", taskComment=" + taskComment
				+ ", createTime=" + createTime + ", endTime=" + endTime + "]";
	}

}
