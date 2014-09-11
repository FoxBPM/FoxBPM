/**
 * 
 */

var _appName = "/foxbpm-webapps-common/";
var _serviceUrl = "/foxbpm-webapps-common/service/";
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
