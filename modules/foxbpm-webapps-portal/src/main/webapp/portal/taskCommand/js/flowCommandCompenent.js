/**
 * 
 */

Foxbpm.FlowCommandCompenent = function (flowConfig){
	 this._config = flowConfig;
};


Foxbpm.FlowCommandCompenent.prototype={
	init:function(){
		var self = this;
		var toolbarsDiv = $("#toolbar");
		var tmpTaskId ;
		if(!_taskId || null == _taskId || '' == _taskId){
			tmpTaskId = 'null';
		}else{
			tmpTaskId = _taskId;
		}
		 $.ajax({
	         type: "get",//使用get方法访问后台
	         dataType: "json",//返回json格式的数据
	         url: _bpmServiceUrl+"/runtime/tasks/"+tmpTaskId+"/taskCommands",//要访问的后台地址
	         data:{key:_processDefinitionKey},
	         success: function(msg){//msg为返回的数据，在这里做数据绑定
	             var data = msg.data;
	             //画任务命令
	             $.each(data, function(i, n){
	                 var toolBarDiv = $("<button type='button' class='btn btn-primary'></button>");
	                 toolBarDiv.attr("commandType",n.type);
	                 toolBarDiv.attr("commandId",n.id);
	                 toolBarDiv.attr("id","btn_"+i);
	                 toolBarDiv.append(n.name);
	                 toolbarsDiv.append(toolBarDiv);
	             });
	             
	             //给任务命令赋值事件
	             $("button[commandType]").click(function(){
	            	 var data = {};
	            	 data.processDefinitionKey = _processDefinitionKey;
	            	 data.processInstanceId = _processInstanceId;
	            	 data.taskId = _taskId;
	            	 var $this = this;
	            	 var commandType = $($this).attr("commandType");
	            	 data.fn=function(params, result){
	            		 var commandObj = {};
	                	 commandObj.processDefinitionKey=_processDefinitionKey;
	                	 commandObj.processInstanceId = _processInstanceId;
	                	 commandObj.taskId=_taskId;
	                	 commandObj.commandId=$($this).attr("commandId");
	                	 commandObj.commandType = commandType;
	                	 if(Foxbpm.commandHandler.result[commandType] !== undefined){
	                		 commandObj.commandParams = Foxbpm.commandHandler.result[commandType](result);
	                	 }
	                	 else {
	                		 commandObj.commandParams = result;
	                	 }
	                	 commandObj.bizKey = self._config.getBizKey();
	                	 commandObj.taskComment = self._config.getTaskComment();
	                	 self._config.flowCommit(commandObj);
	            	 };
	            	 if(Foxbpm.commandHandler[commandType] !== undefined){
	            		 //流程提交所需信息
	            		 Foxbpm.commandHandler[commandType](data);
	            	 }
	            	 else {
	              		 var commandObj = {};
	                	 commandObj.processDefinitionKey=_processDefinitionKey;
	                	 commandObj.processInstanceId = _processInstanceId;
	                	 commandObj.taskId=_taskId;
	                	 commandObj.commandId=$($this).attr("commandId");
	                	 commandObj.commandType = commandType;
	                	 commandObj.bizKey = self._config.getBizKey();
	                	 commandObj.taskComment = self._config.getTaskComment();
	                	 self._config.flowCommit(commandObj);
	            	 }
	        	 });
	         }
		 });
	}
};
