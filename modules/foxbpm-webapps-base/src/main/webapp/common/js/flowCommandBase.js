/**
 * 
 */

var Foxbpm = {};

var _processDefinitionKey = null;
var _processInstanceId = null;
var _taskId = null;


$(document).ready(function() {
	
	//初始化流程参数 processDefinitionKey,taskId等
	initFlowParas();
	
	var toolbarsDiv = $("#toolbar");
	toolbarsDiv.append("<input type='hidden' name='flowInfo' id='flowInfo' />");
	 $.ajax({
         type: "get",//使用get方法访问后台
         dataType: "json",//返回json格式的数据
         url: "service/task/taskCommands",//要访问的后台地址
         data:{key:_processDefinitionKey,taskId:_taskId},
         success: function(msg){//msg为返回的数据，在这里做数据绑定
             var data = msg.data;
             
             //画任务命令
             $.each(data, function(i, n){
                 var toolBarDiv = $("<div class='btn-normal'></div>");
                 toolBarDiv.attr("commandType",n.type);
                 toolBarDiv.attr("commandId",n.id);
                 toolBarDiv.attr("id","btn_"+i);
                 var name = "<a>"+n.name+"</a>";
                 toolBarDiv.append(name);
                 toolbarsDiv.append(toolBarDiv);
             });
             
             //给任务命令赋值事件
             $("div[commandType]").click(function(){
            	 
            	 var data = {};
            	 data.processDefinitionKey = _processDefinitionKey;
            	 data.processInstanceId = _processInstanceId;
            	 data.taskId = _taskId;
            	 
            	 var result = null;
            	 var commandType = $(this).attr("commandType");
            	 
            	 var handler = new Foxbpm.commandHandler();
            	 if(commandType in handler){
            		 result = handler[commandType](data);
                	 if(result.status == "error"){
                		 alert(result.message);
                		 return;
                	 }else if(result.status == "success"){
                		 return ;
                	 }
            	 }
            	 
            	 //流程提交所需信息
            	 var commandObj = {};
            	 commandObj.processDefinitionKey=_processDefinitionKey;
            	 commandObj.processInstanceId = _processInstanceId;
            	 commandObj.taskId=_taskId;
            	 commandObj.commandId=$(this).attr("commandId");
            	 commandObj.commandType = commandType;
            	 commandObj.commandParams = result;
            	 
            	 commandObj.bizKey = getBizKey();
            	 commandObj.taskComment = getTaskComment();

            	 $("#flowInfo").val(JSON.stringify(commandObj));
            	 flowCommit();
        	 });
         }
	 });
});


function initFlowParas(){
	//初始化流程参数，从url获取对应参数
	_processDefinitionKey = requestUrlParam("processDefinitionKey");
	_processInstanceId = requestUrlParam("processInstanceId");
	_taskId = requestUrlParam("taskId");
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