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
package org.foxbpm.engine.impl.listener.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.foxbpm.engine.Constant;
import org.foxbpm.engine.impl.entity.GroupEntity;
import org.foxbpm.engine.impl.entity.IdentityLinkEntity;
import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.engine.impl.entity.UserEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.kernel.event.KernelListener;
import org.foxbpm.kernel.process.KernelFlowNode;
import org.foxbpm.kernel.runtime.ListenerExecutionContext;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;

/**
 * <p>根据流程实例，计算出流程的当前位置信息</p>
 * <p>当前位置包括任务所在位置和令牌所在位置</p>
 * <p>任务位置会有处理人信息，如“人工任务”</p>
 * <p>令牌位置只会有节点信息，因为节点上不一定会创建任务，如“捕获节点”</p>
 * <p>此处计算的信息，都是json结构化的数据，用户可以拿到此数据之后进行解析，显示自己需要的格式。</p>
 * @author ych
 *
 */
public class UpdateLocationListener  implements KernelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void notify(ListenerExecutionContext executionContext) throws Exception {
		TokenEntity token = (TokenEntity)executionContext;
		ProcessInstanceEntity processInstance = token.getProcessInstance();
		if(processInstance != null){
			Map<String,Object> location = getProcessLocation(processInstance);
			
			ObjectMapper objectMapper = new ObjectMapper();
			String l = objectMapper.writeValueAsString(location);
			processInstance.setProcessLocation(l);
		}
	}
	
	private Map<String,Object> getProcessLocation(ProcessInstanceEntity processInstance){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("processStatus",processInstance.getInstanceStatus());
		if (processInstance.isEnd()) {
			return resultMap;
		}
		
		List<TaskEntity> tasks = processInstance.getTasks();
		List<KernelTokenImpl> tokens = processInstance.getTokens();
		List<Map<String,Object>> nodes = new ArrayList<Map<String,Object>>();
		resultMap.put("nodes", nodes);
		boolean isRootTokenEnable = true;
		
		for(TaskEntity tmpTask : tasks){
			if(tmpTask.hasEnded()){
				continue;
			}
			
			String nodeId = tmpTask.getNodeId();
			//删除重复的token
			List<KernelTokenImpl> tokenList = new ArrayList<KernelTokenImpl>(tokens);
			for(int i=0; i<tokenList.size();i++){
				KernelTokenImpl tmpToken = tokenList.get(i);
				//如果存在子令牌，则不计算主令牌的位置。
				if(!tmpToken.isRoot()){
					isRootTokenEnable =false;
				}
				if(tmpToken.getFlowNode().getId().equals(nodeId)){
					tokens.remove(i);
					
				}
			}
			
			Map<String,Object> nodeMap = new HashMap<String, Object>();
			nodes.add(nodeMap);
			nodeMap.put("nodeId", nodeId);
			nodeMap.put("nodeName", tmpTask.getNodeName());
			String assignee = tmpTask.getAssignee();
			
			
			List<Map<String,Object>> users = new ArrayList<Map<String,Object>>();
			nodeMap.put("users", users);
			if(StringUtil.isNotEmpty(assignee)){
				UserEntity user = Authentication.selectUserByUserId(assignee);
				
				Map<String,Object> tmpUser = new HashMap<String, Object>();
				tmpUser.put("userId", user.getUserId());
				tmpUser.put("userName", user.getUserName());
				users.add(tmpUser);
				continue;
			}
			
			Map<String,List<GroupEntity>> groups = new HashMap<String,List<GroupEntity>>();
			nodeMap.put("groups", groups);
			List<IdentityLinkEntity> identityLinkList = tmpTask.getIdentityLinks();
			for (IdentityLinkEntity identityLink : identityLinkList) {
				String userId = identityLink.getUserId();
				if (userId == null) {
					String groupTypeId = identityLink.getGroupType();
					String groupId = identityLink.getGroupId();
					GroupEntity group = Authentication.findGroupById(groupId, groupTypeId);
					if (group == null) {
						continue;
					}
						
					if(groups.get(groupTypeId)!=null){
						groups.get(groupTypeId).add(group);
					}
					else{
						List<GroupEntity> groupTos=new ArrayList<GroupEntity>();
						groupTos.add(group);
						groups.put(groupTypeId, groupTos);
					}
					
				} else {
					
					Map<String,Object> tmpUser = new HashMap<String, Object>();
					UserEntity user=null;
					if (userId.equals(Constant.FOXBPM_ALL_USER)) {
						user=new UserEntity(Constant.FOXBPM_ALL_USER, "所有人");
					} else {
						user= Authentication.selectUserByUserId(userId);
					}
					if(user!=null){
						tmpUser.put("userId", user.getUserId());
						tmpUser.put("userName", user.getUserName());
						users.add(tmpUser);
					}
				}
			}
		}
		
		if(tokens.size() > 0){
			for(KernelTokenImpl tmpToken : tokens){
				//只处理活着的子令牌
				if(tmpToken.isEnded()){
					continue;
				}
				if(tmpToken.isRoot()){
					if(!isRootTokenEnable){
						continue;
					}
				}
				KernelFlowNode node= tmpToken.getFlowNode();
				Map<String,Object> tmpNodeMap = new HashMap<String, Object>();
				tmpNodeMap.put("nodeId", node.getId());
				tmpNodeMap.put("nodeName",node.getName());
				nodes.add(tmpNodeMap);
			}
		}
		return resultMap;
	}
}
