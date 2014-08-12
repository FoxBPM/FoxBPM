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
 * @author ych
 */
package org.foxbpm.engine.identity;

import java.util.List;

import org.foxbpm.engine.impl.entity.GroupEntity;
import org.foxbpm.engine.impl.identity.GroupRelationEntity;

/**
 * 用户组定义
 * @author ych
 *
 */
public interface GroupDefinition {

	/**
	 * 获取用户所在的组集合
	 * <p>使用场景：获取用户共享任务时使用,如taskQuery.taskCandidateUser("admin")</p>
	 * @param userId
	 * @return
	 */
	List<GroupEntity> selectGroupByUserId(String userId);
	
	/**
	 * 获取所有的组信息（如:所有角色信息）
	 * <p>使用场景：设计器表达式编辑器上，选择组织机构时使用<p>
	 * @return
	 */
	List<GroupEntity> selectAllGroup();
	
	/**
	 * 获取所有的组和人员的关系映射（如所有的角色对照）
	 * <p>暂时没用到</p>
	 * @return
	 */
	List<GroupRelationEntity> selectAllRelation();
	
	/**
	 * 获取组类型
	 * <p>匹配对应实现</p>
	 * @return
	 */
	String getType();
	
	/**
	 * 获取组名称（如：“部门”，“角色”）
	 * <p>使用场景：设计器获取“用户，部门，角色 等”类型场景，如选发起人时表达式类型选项</p>
	 * @return
	 */
	String getName();
	
	/**
	 * 获取组下的所有人员ID
	 * <p>使用场景：自动发送邮件  连接上使用，给相关处理人发送邮件</p>
	 * @param groupId
	 * @return
	 */
	List<String> selectUserIdsByGroupId(String groupId);
	
	/**
	 * 获取指定组编号的所有子组（包含自身）
	 * <p>使用场景：选择部门作为处理人时，可选择子部门</p>
	 * @param groupId
	 * @return
	 */
	List<GroupEntity> selectChildrenByGroupId(String groupId);
	
	/**
	 * 根据组编号获取组对象
	 * <p>使用场景：获取部门信息时</p>
	 * @param groupId
	 * @return
	 */
	GroupEntity selectGroupByGroupId(String groupId);
	
}
