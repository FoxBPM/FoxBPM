/**
 * 
 */


var _bpmServiceUrl= "/foxbpm-webapps-base/service";
var _bpmFilePath = "/foxbpm-webapps-base/foxbpm";
var _userId = "";
$(document).ready(function(){
	 $.ajax({
		type : "get", 
		dataType : "json", 
		async:true, 
		url : "/foxbpm-webapps-common/service/getLoginState", 
		success:function(response){
			userId = response.responseText;
		},
		error:function(response){
			if(response.status == 401){
				window.location.href="/foxbpm-webapps-common/portal/login.html";
			}
		}
	});
});
