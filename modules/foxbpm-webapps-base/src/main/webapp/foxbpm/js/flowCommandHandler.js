/**
 * 
 */

Foxbpm.commandHandler = {};

//流程状态
Foxbpm.commandHandler.processStatus = function(data){
	var url = _bpmFilePath+"/showTaskDetailInfor.html?";
	if(data.processInstanceId){
		url += "processInstanceId=" + data.processInstanceId;
	}else if(data.processDefinitionKey){
		url += "processDefinitionKey=" + data.processDefinitionKey;
	}else  if(data.processDefinitionId){
		url += "processDefinitionId=" + data.processDefinitionId;
	}else{
		return {status:"error",message:"参数不足"};
	}
	window.open(url);
	return {status:"success"};
};
		
		
Foxbpm.commandHandler.transfer = function(){
	var userId = showDialog(_bpmFilePath+"/selectUser.html");
	if(userId == null || userId === undefined){
		return null;
	}
	return {transferUserId:userId};
};


Foxbpm.commandHandler.pending = function(){
	var userId = showDialog(_bpmFilePath+"/selectUser.html");
	if(userId == null || userId === undefined){
		return null;
	}
	return {pendingUserId:userId};
};

Foxbpm.commandHandler.rollBack_reset = function(data){
	var params = {};
	var url = _bpmFilePath+"/selectData.html";
	var queryUrl = _bpmServiceUrl + "/flowNode/rollbackNodes";
	var queryData = {taskId:data.taskId};
	var returnData = {rollBackNodeId:"nodeId"};
	var columnInfo = {nodeId:"节点编号",nodeName:"节点名称"};
	params.queryData = queryData;
	params.returnData = returnData;
	params.columnInfo = columnInfo;
	params.url = queryUrl;
	var result = showDialog(url,params);
	return result;
};

Foxbpm.commandHandler.rollBack_assignee = function(data){
	var params = {};
	var url = _bpmFilePath+"/selectData.html";
	var queryUrl = _bpmServiceUrl + "/task/rollbackTasks";
	var queryData = {taskId:data.taskId};
	var returnData = {rollBackNodeId:"nodeId",rollBackAssignee:"assignee"};
	var columnInfo = {nodeName:"步骤名称",assgneeUserName:"处理者",endTime:"处理时间",commandMessage:"处理结果",taskComment:"处理意见"};
	params.queryData = queryData;
	params.returnData = returnData;
	params.columnInfo = columnInfo;
	params.url = queryUrl;
	var result = showDialog(url,params);
	return result;
};



function showDialog(url,params){
	window.rv = null;
	var rv = null;
	var passObj = {
		opener : window,
		params : params
	};
	rv = window.showModalDialog(url, passObj,
			"dialogWidth=600;dialogHeight=400");
	if(rv){
		return rv;
	}else{
		return window.rv;
	}
}
