/**
 * 
 */

var _appName = "/foxbpm-webapps-common/";
var _serviceUrl = "/foxbpm-webapps-common/service/";
var _serviceTaskUrl = "/foxbpm-webapps-common/service/tasks";
var _serviceProcessInstanceUrl = "/foxbpm-webapps-common/service/runtime/process-instances";
var _formUrl = "ajaxpage/editform.html";
var _userId = "";
$.ajax({
	type : "get", 
	dataType : "json", 
	async:false, 
	url : _serviceUrl + "getLoginState", 
	success:function(response){
		_userId = response.userId;
	},
	error:function(response){
		if(response.status == 401){
			window.location.href=_appName+"login.html";
		}
	}
});

function showForm(dataId,taskId,processInstanceId){
	var formUrl = _formUrl+"?dataId="+dataId+"&taskId="+taskId+"&processInstanceId="+processInstanceId;
	$("#contentFrame").attr("src",formUrl);
	$('#remoteModal').modal({backdrop:"static"});
}
function showDiagram(processDefinitionKey,processInstanceId){ 
	window.open("taskCommand/showTaskDetailInfor.html?processDefinitionKey="+processDefinitionKey+"&processInstanceId="+processInstanceId);
}