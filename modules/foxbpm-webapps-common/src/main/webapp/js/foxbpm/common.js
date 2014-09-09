/**
 * 
 */

var _bpmServiceUrl= "/foxbpm-webapps-base/service";
var _bpmFilePath = "/foxbpm-webapps-base/foxbpm";
var _userId = "";
$.ajax({
	type : "get", 
	dataType : "json", 
	async:false, 
	url : "/foxbpm-webapps-common/service/getLoginState", 
	success:function(response){
		_userId = response.userId;
	},
	error:function(response){
		if(response.status == 401){
			window.location.href="/foxbpm-webapps-common/portal/login.html";
		}
	}
});
