/**
 * 
 */

var _appName = "/foxbpm-webapps-common/";
var _serviceUrl = "/foxbpm-webapps-common/service/";
var _serviceTaskUrl = "/foxbpm-webapps-common/service/tasks";
var _serviceProcessInstanceUrl = "/foxbpm-webapps-common/service/runtime/process-instances";
var _formUrl = "ajaxpage/editExpense.jsp";
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

function requestUrlParam(paras) {
	var url = location.href;
	var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
	var paraObj = {};
	for (var i = 0; j = paraString[i]; i++) {
		paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j
				.indexOf("=") + 1, j.length);
	}
	var returnValue = paraObj[paras.toLowerCase()];
	if (typeof (returnValue) == "undefined") {
		return "";
	} else {
		return returnValue;
	}
}