package org.foxbpm.rest.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.IdentityService;
import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.identity.GroupDefinition;
import org.foxbpm.engine.impl.entity.GroupEntity;
import org.foxbpm.engine.impl.entity.IdentityLinkEntity;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.entity.UserEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.task.Task;
import org.foxbpm.kernel.runtime.ProcessInstanceStatus;
import org.foxbpm.rest.common.api.FoxBpmUtil;

public class FlowUtil {

	/**
	 * 获得实例的当前处理信息
	 * 
	 * @param taskInstanceQueryTo
	 * @return 例如 "人工任务(共享角色:功能角色)(共享部门:平台产品部,财务部)"
	 */
	public static String getShareTaskNowNodeInfo(Task task) {
		TaskEntity taskEntity = (TaskEntity)task;
		if(task.getEndTime()==null){
			try {
				return processState(taskEntity);
			} catch (Exception e) {
				throw new FoxBPMException("当前步骤转换失败",e);
			}
		}
		else{
			String processInstanceId = task.getProcessInstanceId();
			return getShareTaskNowNodeInfo(processInstanceId);
		}
	}

	/**
	 * @param processInstanceId
	 * @return
	 */
	public static String getShareTaskNowNodeInfo(String processInstanceId) {
		try {
			String taskInfo = "";
			ProcessEngine engine = FoxBpmUtil.getProcessEngine();
			ProcessInstance processInstance = engine.getRuntimeService().createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			if (processInstance.getEndTime() != null) {
				if(processInstance.getInstanceStatus().equals(ProcessInstanceStatus.COMPLETE)){
					return "完成";
				}else{
					return "已终止";
				}
			}
			List<Task> tasks = new ArrayList<Task>();
			tasks =engine.getTaskService().createTaskQuery().processInstanceId(processInstanceId).taskNotEnd().list();
			for (Task task : tasks) {
				TaskEntity taskEntity = (TaskEntity)task;
				if(taskInfo.equals("") && tasks.size()==1){
					taskInfo=taskInfo + processState(taskEntity);
				}
				else{
					taskInfo=taskInfo+"<div>"+processState(taskEntity)+"</div>";
				}
			}
			return taskInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private static String processState(TaskEntity task) throws Exception{
		String taskInfo="";
		String assignee = task.getAssignee();
		ProcessEngine engine = FoxBpmUtil.getProcessEngine();
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity)engine.getModelService().getProcessDefinition(task.getProcessDefinitionId());
		String nodeName = processDefinition.getTaskDefinitions().get(task.getNodeId()).getName();
		taskInfo = taskInfo + nodeName;
		IdentityService identityService = engine.getIdentityService();
		if (assignee == null) {
			List<UserEntity> userTos=new ArrayList<UserEntity>();
			Map<String, List<GroupEntity>> groupTosMap=new HashMap<String, List<GroupEntity>>();
			List<IdentityLinkEntity> identityLinkList = task.getIdentityLinks();
			for (IdentityLinkEntity identityLink : identityLinkList) {
				String userId = identityLink.getUserId();
				if (userId == null) {
					String groupTypeId = identityLink.getGroupType();
					String groupId = identityLink.getGroupId();
					GroupEntity group = Authentication.findGroupById(groupId, groupTypeId);
					if (group == null) {
						continue;
					}
					if(groupTosMap.get(groupTypeId)!=null){
						groupTosMap.get(groupTypeId).add(group);
					}
					else{
						List<GroupEntity> groupTos=new ArrayList<GroupEntity>();
						groupTos.add(group);
						groupTosMap.put(groupTypeId, groupTos);
					}
					
				} else {
					UserEntity user=null;
					if (userId.equals("fixflow_allusers")) {
						user=new UserEntity("fixflow_allusers", "所有人");
					} else {
						user= Authentication.selectUserByUserId(userId);
					}
					if(user!=null){
						userTos.add(user);
					}
				}
			}
			if(userTos.size()>0){
				String groupTypeName="";
				groupTypeName = "用户";
				taskInfo += "(共享 " + groupTypeName + " : ";
				for (int i = 0; i < userTos.size(); i++) {
					UserEntity userTo=userTos.get(i);
					if(i==userTos.size()-1){
						taskInfo += userTo.getUserName();
					}
					else{
						taskInfo += userTo.getUserName()+",";
					}
				}
				taskInfo=taskInfo+")";
			}
			for (String groupToKey : groupTosMap.keySet()) {
				List<GroupEntity> groupTos=groupTosMap.get(groupToKey);
				GroupDefinition groupDefinition = identityService.getGroupDefinition(groupToKey);
				String groupTypeName = "";
				groupTypeName = groupDefinition.getName();
				taskInfo += "(共享 " + groupTypeName + " : ";
				taskInfo += listToStr(groupTos, ",",groupToKey) + ")";
			}
		} else {
			UserEntity user = Authentication.selectUserByUserId(assignee);
			String username = user.getUserName();
			username="<span title='"+username+"("+assignee+")'>"+username+"</span>";
			taskInfo = taskInfo + " (处理者 ： " + username + ") ";
		}
		return taskInfo;
	}
	
	public static String listToStr(List<GroupEntity> groupTos, String joinChar,String groupType){
		if(groupTos==null||groupTos.size()==0|| joinChar == null){
			return "";
		}
		String listStr = "";
		for(GroupEntity groupTo : groupTos){
			List<UserEntity> userTos=Authentication.selectUserByGroupIdAndType(groupTo.getGroupId(), groupType);
			String nameList="";
			int x=0;
			int y=5;
			if(userTos.size()>y){
				userTos=userTos.subList(0, y);
				x=1;
			}
			for (int i = 0; i < userTos.size(); i++) {
				UserEntity userTo=userTos.get(i);
				if(i==userTos.size()-1){
					nameList=nameList+userTo.getUserName()+"("+userTo.getUserId()+")";
				}
				else{
					nameList=nameList+userTo.getUserName()+"("+userTo.getUserId()+"),  ";
				}
			}
			if(x==1){
				nameList=nameList+" .......";
			}
			listStr = listStr+"<span title='"+nameList+"'>"+groupTo.getGroupName()+"</span>"+joinChar;
		}
		listStr = listStr.substring(0, listStr.length()- joinChar.length());
		return listStr;
	}
	
}
