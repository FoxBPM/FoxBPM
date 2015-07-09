package org.foxbpm.connector.actorconnector.SelectScopeGroupActor;

import org.foxbpm.engine.impl.connector.ActorConnectorHandler;
import org.foxbpm.engine.task.DelegateTask;
import org.foxbpm.engine.task.IdentityLinkType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelectScopeGroupActor extends ActorConnectorHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOG = LoggerFactory
			.getLogger(SelectScopeGroupActor.class);

	private java.lang.String scopeId;

	private java.lang.String scopeType;

	private java.lang.String groupId;

	public void assign(DelegateTask task) throws Exception {
		if (null == scopeId) {
			LOG.warn("用户组选择器-范围编号表达式为空 ! 节点编号：" + task.getNodeId());
			return;
		}
		if (null == scopeType) {
			LOG.warn("用户组选择器-部门类型表达式为空 ! 节点编号：" + task.getNodeId());
			return;
		}

		task.addGroupIdentityLink(groupId, "good", scopeId, scopeType, IdentityLinkType.CANDIDATE);
	}

	public void setScopeId(java.lang.String scopeId) {
		this.scopeId = scopeId;
	}

	public void setScopeType(java.lang.String scopeType) {
		this.scopeType = scopeType;
	}

	public void setGroupId(java.lang.String groupId) {
		this.groupId = groupId;
	}

}