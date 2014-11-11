/**
 * 
 */

var Foxbpm = {};

// 初始化流程参数，从url获取对应参数
var _bpmServiceUrl = $("base").attr("href")+"service";
var _bpmFilePath = $("base").attr("href")+"portal/taskCommand";
var _processDefinitionKey = requestUrlParam("processDefinitionKey");
var _processInstanceId = requestUrlParam("processInstanceId");
var _taskId = requestUrlParam("taskId");

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