$(document).ready(function() {
	assemble();
});

function assemble() {
	var formId = document.forms[0].id;
	var urlParam = UrlParamUtil(location.href);
	var processInstanceId = urlParam.getParamVal('processInstanceId');
	var processDefinitionKey = urlParam.getParamVal('processDefinitionKey');
	var processDefinitionId = urlParam.getParamVal('processDefinitionId');
	var taskId = urlParam.getParamVal('taskId');
	regFlowCommand(formId, processInstanceId, processDefinitionKey,processDefinitionId, taskId);

}
/**
 * 取url中参数
 * 
 * @param paras
 *            需要获取参数paras的数值
 * @returns 返回该值
 */
function UrlParamUtil(url) {
	this.url = url;
	this.paramObj = {};
	this.getParamVal = function(paramName) {
		var returnValue = paramObj[paramName.toLowerCase()];
		if (typeof (returnValue) == "undefined") {
			return "";
		} else {
			return returnValue;
		}
	};
	/**
	 * 定义私有方法 解析url获取参数
	 */
	function parseUrl() {
		if (typeof (url) != undefined) {
			var paraString = url.substring(url.indexOf("?") + 1, url.length)
					.split("&");
			for (var i = 0; j = paraString[i]; i++) {
				paramObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j
						.substring(j.indexOf("=") + 1, j.length);
			}
		}
	}
	parseUrl();
	return this;
}