/**
 * 
 */

Foxbpm.commandHandler = {};

//流程状态
Foxbpm.commandHandler.processStatus = function(data){
	var url = _bpmFilePath+"/showTaskDetailInfor.jsp?";
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
		
		
Foxbpm.commandHandler.transfer = function(data){
	var params = {};
	params.url = _bpmServiceUrl + "/identity/users";
	params.fn = data.fn;
	showDialog(_bpmFilePath+"/selectUser.html",params);
};
/**
 * 命令返回值封装
 */
Foxbpm.commandHandler.result = {
	transfer : function(userId) {
		return {
			transferUserId : userId
		};
	},
	pending : function(userId) {
		return {
			pendingUserId : userId
		}
	}
};


Foxbpm.commandHandler.pending = function(data){
	var params = {};
	params.url = _bpmServiceUrl + "/identity/users";
	params.fn = data.fn;
	showDialog(_bpmFilePath+"/selectUser.html",params);
};

Foxbpm.commandHandler.rollBack_reset = function(data){
	var params = {};
	var queryUrl = _bpmServiceUrl + "/runtime/tasks/"+data.taskId+"/rollbackNodes";
	var queryData = {taskId:data.taskId};
	var returnData = {rollBackNodeId:"nodeId"};
	var columnInfo = {nodeId:"节点编号",nodeName:"节点名称"};
	params.queryData = queryData;
	params.returnData = returnData;
	params.columnInfo = columnInfo;
	params.url = queryUrl;
	params.fn = data.fn;
	showDialog(_bpmFilePath+"/selectData.html",params);
};

Foxbpm.commandHandler.rollBack_assignee = function(data){
	var params = {};
	var queryUrl = _bpmServiceUrl + "/runtime/tasks/"+data.taskId+"/rollbackTasks";
	var queryData = {taskId:data.taskId};
	var returnData = {rollBackNodeId:"nodeId",rollBackAssignee:"assignee"};
	var columnInfo = {nodeName:"步骤名称",assgneeUserName:"处理者",endTime:"处理时间",commandMessage:"处理结果",taskComment:"处理意见"};
	params.queryData = queryData;
	params.returnData = returnData;
	params.columnInfo = columnInfo;
	params.url = queryUrl;
	params.fn = data.fn;
	showDialog(_bpmFilePath+"/selectData.html",params);
};



function showDialog(url, params) {
	window.rv = null;
	var rv = null;
	var passObj = {
		opener : window,
		params : params,
	};
	if (params && params.fn) {
		passObj.fn = params.fn;
	}

	$.ligerDialog.open({
		height : 400,
		width : 600,
		url : url,
		showMax : false,
		showToggle : false,
		showMin : false,
		isResize : true,
		slide : false,
		data : passObj
	});
}
