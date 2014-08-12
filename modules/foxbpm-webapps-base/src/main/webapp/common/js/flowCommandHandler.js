/**
 * 
 */

Foxbpm.commandHandler = {};

//流程状态
Foxbpm.commandHandler.processStatus = function(data){
	var url = "queryTaskDetailInfor.action?";
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
	var userId = showDialog("common/selectUser.html");
	if(userId == null || userId === undefined){
		return null;
	}
	return {transferUserId:userId};
};

Foxbpm.commandHandler.rollBack_reset = function(data){
	var resultId = showDialog("common/selectRollbackNode.html?taskId="+data.taskId);
	if(resultId == null || resultId === undefined){
		return null;
	}
	return {rollBackNodeId:resultId};
};



function showDialog(url){
	window.rv = null;
	var rv = null;
	var passObj = {
		opener : window
	};
	rv = window.showModalDialog(url, passObj,
			"dialogWidth=600;dialogHeight=400");
	if(rv){
		return rv;
	}else{
		return window.rv;
	}
}
