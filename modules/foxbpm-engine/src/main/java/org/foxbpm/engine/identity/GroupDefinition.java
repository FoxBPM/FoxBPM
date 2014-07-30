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
	 * @param userId
	 * @return
	 */
	List<Group> selectGroupByUserId(String userId);
	
	/**
	 * 获取所有的组信息（如:所有角色信息）
	 * @return
	 */
	List<GroupEntity> selectAllGroup();
	
	/**
	 * 获取所有的组和人员的关系映射（如所有的角色对照）
	 * @return
	 */
	List<GroupRelationEntity> selectAllRelation();
	
	/**
	 * 获取组类型
	 * @return
	 */
	String getType();
	
	/**
	 * 获取组下的所有人员ID
	 * @param groupId
	 * @return
	 */
	List<String> selectUserIdsByGroupId(String groupId);
	
	/**
	 * 获取指定组编号的所有子组（包含自身）
	 * @param groupId
	 * @return
	 */
	List<Group> selectChildrenByGroupId(String groupId);
	
	/**
	 * 根据组编号获取组对象
	 * @param groupId
	 * @return
	 */
	Group selectGroupByGroupId(String groupId);
	
}
