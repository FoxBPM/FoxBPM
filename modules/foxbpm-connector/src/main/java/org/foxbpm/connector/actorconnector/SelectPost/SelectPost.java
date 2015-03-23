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
 * @author yangguangftlp
 */
package org.foxbpm.connector.actorconnector.SelectPost;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.foxbpm.engine.Constant;
import org.foxbpm.engine.impl.connector.ActorConnectorHandler;
import org.foxbpm.engine.impl.util.AssigneeUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.task.DelegateTask;
import org.foxbpm.engine.task.IdentityLinkType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 岗位选择
 * 
 * @author thinkgem
 * @date 2015-3-23 copy form SelectRole
 */
public class SelectPost extends ActorConnectorHandler {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2220037950979163348L;
	private static Logger LOG = LoggerFactory.getLogger(SelectPost.class);
	private java.lang.Object postId;
	
	public void assign(DelegateTask task) throws Exception {
		if (null == postId) {
			LOG.warn("处理人选择器(选择岗位)岗位编号表达式为空 ! 节点编号：" + task.getNodeId());
			return;
		}
		// 处理岗位重复
		List<String> userList = AssigneeUtil.executionExpressionObj(postId);
		Set<String> postIdSet = new HashSet<String>();
		Iterator<String> iterator = userList.iterator();
		while (iterator.hasNext()) {
			postIdSet.add(StringUtil.trim(iterator.next()));
		}
		// 处理岗位
		for (String id : postIdSet) {
			task.addGroupIdentityLink(id, Constant.POST_TYPE, IdentityLinkType.CANDIDATE);
		}
	}
	
	public void setPostId(java.lang.Object postId) {
		this.postId = postId;
	}
	
}