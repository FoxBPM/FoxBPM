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
		
		
Foxbpm.commandHandler.rollbackNode = function(){
	return {nodeId:"userTask_1"};
};

Foxbpm.commandHandler.rollbacktask = function(){

	window.rv = null;
	var rv = null;
	var passObj = {
			fn : function(params, d) {
				params['rollBackNodeId'] = d.rollBackNodeId;
			},
			opener : window
	};
	rv = window.showModalDialog("common/test.html", passObj,
			"dialogWidth=600;dialogHeight=400");
	if(rv){
		return rv;
	}else{
		return window.rv;
	}
};
