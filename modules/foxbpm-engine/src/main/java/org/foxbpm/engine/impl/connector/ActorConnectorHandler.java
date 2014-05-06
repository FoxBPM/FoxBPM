package org.foxbpm.engine.impl.connector;

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.impl.entity.GroupEntity;
import org.foxbpm.engine.impl.entity.UserEntity;

public abstract class ActorConnectorHandler extends ConnectorHandler {

	protected List<UserEntity> users=new ArrayList<UserEntity>();
	
	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}

	public void setGroups(List<GroupEntity> groups) {
		this.groups = groups;
	}

	protected List<GroupEntity> groups=new ArrayList<GroupEntity>();
	
	public void addUser(UserEntity user){
		users.add(user);
	}
	
	public void addGroup(GroupEntity group){
		groups.add(group);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 获取用户类型处理者
	 * @return
	 */
	public List<UserEntity> getUsers(){
		return users;
	}
	
	/**
	 * 获取组类型处理者
	 * @return
	 */
	public List<GroupEntity> getGroups(){
		return groups;
	}
	

}
