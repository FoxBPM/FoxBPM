/**
 * 标记常量属性
 */
var TASK_END_COLOR = "#458f05";
var TASK_END_WIDTH = "2";
var TASK_ING_COLOR = "#ff7200";
var TASK_ING_WIDTH = "2";
var RUNNING_TRACK_COLOR = "#ff7200";
var RUNNING_TRACK_WIDTH = 5;
var RUNNING_MILLESIMAL_SPEED = 1000;

/**
 * 全局变量
 */
var _GlobalFlowVar = {
	taskListEnd : [],
	taskListIng : [],
	nodeInforArr : [],
	isIE : window.ActiveXObject && $.browser.msie,
	_self:this,
	init : function(config) {
		this.taskListEnd = config.taskListEnd || [];
		this.taskListIng = config.taskListIng || [];
		this.nodeInforArr = config.nodeInforArr || [];
	}
};
var backUpColorDictionary = {};
var backUpWidthDictionary = {};
//
var TaskDeatailInfor;
/**
 * 任务详细信息
 */
Foxbpm.TaskDeatailInfor = function(flowConfig) {
	this._config = flowConfig || {
		taksDetailDiv : 'taksDetailDiv',
		runningTrackDIV : 'runningTrackDIV',
		flowGraphicDiv : 'flowGraphicDiv'
	};
	this._flowGraphic = new FlowGraphic({
		'isIE' : _GlobalFlowVar.isIE,
		'parentId' : 'flowImg',
		'action' : _bpmServiceUrl+'/flowGraphic/flowImg',
		'processInstanceId' : _processInstanceId,
		'processDefinitionKey' : _processDefinitionKey
	});
	this._runTrack = new RunTrack({
		action : _bpmServiceUrl+'/task/runTrack',
		processInstanceId : _processInstanceId
	});
	TaskDeatailInfor = this;
};

Foxbpm.TaskDeatailInfor.prototype = {
	init : function() {
		var _self = this;
		if(_processInstanceId && "" != _processInstanceId){
		// 设置定义名称
			$.ajax({
					type : "get",// 使用get方法访问后台
					dataType : "json",// 返回json格式的数据
					url : _bpmServiceUrl+"/task/taskInfor",// 要访问的后台地址
					data : {
						processInstanceId : _processInstanceId
					},
					success : function(msg) {// msg为返回的数据，在这里做数据绑定
						if (!msg && !msg.data) {
							alert("返回数据为null");
							return;
						}
						var data = msg.data;
						var taksDetailDiv = $("#"+TaskDeatailInfor._config.taksDetailDiv);
						//添加流程主题信息
						taksDetailDiv.before($("<h1 id='processDefinitionName'>"+ data.processName + "</h1>"));
						// 任务信息
						var table;
						var table_thead;
						var table_tr;
						// 任务结束信息
						if (data.endTasks) {
							taksDetailDiv.append("<div class='proc_bg'><h3><span id='clz'>已完成</span></h3>");
							var taskEndDoneTb = $("<div id='taskEndDoneTb'>");
							table = $("<table width='100' class='table-list'>");
							table_thead = $("<thead>");
							table_thead
									.append("<th style='width: 160px'>步骤名称</th>");
							table_thead
									.append("<th style='width: 160px'>到达时间</th>");
							table_thead
									.append("<th style='width: 160px'>完成时间</th>");
							table_thead
									.append("<th style='width: 160px'>处理者</th>");
							table_thead
									.append("<th style='width: 80px'>处理结果</th>");
							table_thead
									.append("<th style='width: 80px'>处理意见</th>");
							table_thead.append("</thead>");
							table.append(table_thead);
							// 已完成任务
							$.each(data.endTasks,
									function(j, row) {
										table_tr = $("<tr "
												+ (j % 2 == 0 ? "class='gray'"
														: "") + ">");
										table_tr.append("<td>" + row.nodeName
												+ "</td>");
										table_tr.append("<td>"
												+ (!row.createTime ? ''
														: row.createTime)
												+ "</td>");
										table_tr.append("<td>"
												+ (!row.endTime ? ''
														: row.endTime)
												+ "</td>");
										table_tr.append("<td>"
												+ (!row.userName ? ''
														: row.userName)
												+ "</td>");
										table_tr.append("<td class='left'>"
												+ (!row.commandMessage ? ''
														: row.commandMessage)
												+ "</td>");
										table_tr.append("<td class='left'>"
												+ (!row.taskComment ? ''
														: row.taskComment)
												+ "</td>");
										table_tr.append("</tr>");
										table.append(table_tr);
									});
							//添加完成信息
							taskEndDoneTb.append(table);
							taksDetailDiv.append(taskEndDoneTb);
						}
						// 未结束任务
						if (data.openTasks) {
							taksDetailDiv.append("<div class='proc_bg'><h3><span id='clz'>未完成</span></h3>");
							var taskNotEndDoneTb = $("<div id='taskNotEndDoneTb'>");
							table = $("<table width='100' class='table-list'>");
							table_thead = $("<thead>");
							table_thead
									.append("<th style='width: 160px'>步骤名称</th>");
							table_thead
									.append("<th style='width: 160px'>到达时间</th>");
							table_thead
									.append("<th style='width: 160px'>完成时间</th>");
							table_thead
									.append("<th style='width: 160px'>处理者</th>");
							table_thead
									.append("<th style='width: 80px'>处理结果</th>");
							table_thead
									.append("<th style='width: 80px'>处理意见</th>");
							table_thead.append("</thead>");
							table.append(table_thead);
							//未完成信息
							$.each(data.openTasks,
									function(j, row) {
										table_tr = $("<tr "
												+ (j % 2 == 0 ? "class='gray'"
														: "") + ">");
										table_tr.append("<td>" + row.nodeName
												+ "</td>");
										table_tr.append("<td>"
												+ (!row.createTime ? ''
														: row.createTime)
												+ "</td>");
										table_tr.append("<td>"
												+ (!row.endTime ? ''
														: row.endTime)
												+ "</td>");
										table_tr.append("<td>"
												+ (!row.userName ? ''
														: row.userName)
												+ "</td>");
										table_tr.append("<td class='left'>"
												+ (!row.commandMessage ? ''
														: row.commandMessage)
												+ "</td>");
										table_tr.append("<td class='left'>"
												+ (!row.taskComment ? ''
														: row.taskComment)
												+ "</td>");
										table_tr.append("</tr>");
										table.append(table_tr);
									});
							//添加未完成信息
							taskNotEndDoneTb.append(table);
							taksDetailDiv.append(taskNotEndDoneTb);
						}
						// 初始化全局变量
						_GlobalFlowVar.init({
							taskListEnd : data.endTasks,
							taskListIng : data.openTasks,
							nodeInforArr : data.positionInfor
						});
						// 加载流程图上展现任务完成情况
						_self.getFlowGraphic().showFlowImgStatus();
						_self.getRunTrack().loadRunTrackData();
					}
				});
		}
		_self.createFlowchartOper();
		// 加载流程图
		_self.getFlowGraphic().loadFlowGraphicData();
		// 加载运行轨迹数据
	},
	flowImgStatusClick:function()
	{
		TaskDeatailInfor._flowGraphic.hideFlowImgStatus(($(this).attr("checked") == 'checked'));
	},
	runningTrackClick:function()
	{
		TaskDeatailInfor._runTrack.runningTrack(($(this).attr("checked") == 'checked'));
	},
	getRunTrack : function() {
		return this._runTrack;
	},
	getFlowGraphic : function() {
		return this._flowGraphic;
	},
	/**
	 * 加载流程图操作
	 */
	createFlowchartOper:function() {
		var flowGraphicDiv = $("#"+TaskDeatailInfor._config.flowGraphicDiv);
		var h3 = $("<h3>");
		h3.append("<span id='lct'>流程图</span>");
        var ul = $("<ul>");
        ul.append("<li class='img01'>已完成</li>");
        ul.append("<li class='img01'>进行中</li>");
		ul.append("<li><input id='yczt' type='checkbox' name='cczt' />&nbsp;&nbsp;隐藏状态</li>");
		ul.append("<li><input id='runningTrackTable' type='checkbox' name='runningTrackTable' />&nbsp;&nbsp;轨迹信息</li>");
		ul.append("<li><input id='runningTrack' type='checkbox' name='runningTrack' />&nbsp;&nbsp;轨迹动态运行</li>");
		
		h3.append(ul);
		flowGraphicDiv.append(h3);
		flowGraphicDiv.append($("<div id='flowImg' class='pos_abs' style='position: relative; overflow-x: auto; width: 100%;'></div> "));
	    //事件注册
		$("#yczt").bind("click", this.flowImgStatusClick);
		$("#runningTrack").bind("click", this.runningTrackClick);
		$("#runningTrackTable").bind("click", function(){
			if ($(this).attr("checked") == 'checked') {
				$("#"+TaskDeatailInfor._config.runningTrackDIV).show();
			}else{
				$("#"+TaskDeatailInfor._config.runningTrackDIV).hide();
			}
		});
	}
};

/**
 * 流程图处理类 taskListEnd 任务结束节点 taskListIng 任务进入节点 nodeInfoArr 节点信息 isIE 是否IE
 * parentId 流程图添加到该元素中 <div id='xxx'><img /></div> action 图形加载后台action
 * processDefinitionId 流程定义id
 */
function FlowGraphic(param) {
	this.taskListEnd = param.taskListEnd || [];
	this.taskListIng = param.taskListIng || [];
	this.nodeInforArr = param.nodeInforArr || [];
	this.parentId = param.parentId;
	this.isIE = param.isIE;
	this.action = param.action;
	this.processInstanceId = param.processInstanceId;
	this.processDefinitionKey = param.processDefinitionKey;
	var _self = this;

	/** img图形处理方式* */
	/**
	 * 添加图信息
	 */
	this.addGraphicInfo = function() {
		var divcontent = "";
		for ( var nodeInfor in this.nodeInforArr) {
			var nodeInfoObj = this.nodeInforArr[nodeInfor];
			divcontent = divcontent
					+ getDiv(nodeInfor, nodeInfoObj.x - 4, nodeInfoObj.y - 4,
							nodeInfoObj.width + 4, nodeInfoObj.height + 4);
		}
		$('#' + this.parentId).append(divcontent);
	};
	/**
	 * 生成div
	 */
	function getDiv(nodeId, x, y, w, h) {
		return "<DIV id='"
				+ nodeId
				+ "' class='nodeclass' style='display:none;position: absolute; left: "
				+ x + "px; top: " + y + "px;WIDTH:" + w + "px;HEIGHT:" + h
				+ "px'  ></DIV>";
	}
	this.hideMark = function() {
		$(".nodeclass").css('display', 'none');
	};
	/**
	 * 标记图形
	 */
	this.markImags = function() {
		$.each(this.taskListEnd, function(i, task) {
			markImg(task.nodeId, "green", 2, 0);
		});
		$.each(this.taskListIng, function(i, task) {
			if (task.taskType == "callactivitytask") {
				// 如果是正在运行的，则将z-idnex设为最大，因为子流程如果折叠起来，会有重叠的DIV
				markImg(task.nodeId, "#ff6000", 4, 999);
			} else {
				markImg(task.nodeId, "#ff6000", 2, 999);
			}
		});
	};
	/**
	 * 标记单个图形
	 */
	function markImg(svgNodeId, color, width, zIndex) {
		var svgElement = $("#" + svgNodeId);
		if (svgElement) {
			svgElement.css('display', 'block');
			svgElement.css('border', '2px solid ' + color);
			svgElement.css('z-index', zIndex);
		}
	}
	/** svg图形处理方式* */

	/**
	 * 标记流程状态
	 */
	this.signProcessState = function() {
		signEndTaskState();
		signIngTaskState();
	};
	/**
	 * 取消流程状态标记
	 */
	this.clearTaskState = function() {
		clearEndTaskState();
		clearIngTaskState();
	};
	/**
	 * 清除任务结束状态
	 */
	function clearEndTaskState() {
		for (var i = 0; i < _self.taskListEnd.length; i++) {
			var endTask =  _self.taskListEnd[i];
			var rectAttributes = $("#" + endTask.nodeId)[0].attributes;
			for (var j = 0; j < rectAttributes.length; j++) {
				var rectAttribute = rectAttributes[j];
				if (rectAttribute.name == "stroke") {
					rectAttribute.nodeValue = backUpColorDictionary[endTask.nodeId];
				}
				if (rectAttribute.name == "stroke-width") {
					rectAttribute.nodeValue = backUpWidthDictionary[endTask.nodeId];
				}
			}
		}
	}
	/**
	 * 清除任务进入状态
	 */
	function clearIngTaskState() {
		for (var i = 0; i <  _self.taskListIng.length; i++) {
			var ingTask =  _self.taskListIng[i];
			var nodeId = $("#" + ingTask.nodeId)[0];
			if(nodeId){
				var rectAttributes =nodeId.attributes;
				for (var j = 0; j < rectAttributes.length; j++) {
					var rectAttribute = rectAttributes[j];
					if (rectAttribute.name == "stroke") {
						rectAttribute.nodeValue = backUpColorDictionary[ingTask.nodeId];
					}
					if (rectAttribute.name == "stroke-width") {
						rectAttribute.nodeValue = backUpWidthDictionary[ingTask.nodeId];
					}
				}
			}
		}
	}
	function signEndTaskState() {
		if (_self.taskListEnd == null || _self.taskListEnd.length == 0) {
			return;
		} else {
			for (var i = 0; i < _self.taskListEnd.length; i++) {
				var endTask = _self.taskListEnd[i];
				var nodeId = $("#" + endTask.nodeId)[0];
				if(nodeId){
					var rectAttributes = nodeId.attributes;
					for (var j = 0; j < rectAttributes.length; j++) {
						var rectAttribute = rectAttributes[j];
						if (rectAttribute.name == "stroke") {
							backUpColorDictionary[endTask.nodeId] = rectAttribute.nodeValue;
							rectAttribute.nodeValue = TASK_END_COLOR;
						}
						if (rectAttribute.name == "stroke-width") {
							backUpWidthDictionary[endTask.nodeId] = rectAttribute.nodeValue;
							rectAttribute.nodeValue = TASK_END_WIDTH;
						}
						
					}
				}
			}
		}
	}

	function signIngTaskState() {
		if (_self.taskListIng == null || _self.taskListIng.length == 0) {
			return;
		} else {
			for (var i = 0; i < _self.taskListIng.length; i++) {
				var ingTask = _self.taskListIng[i];
				var nodeId = $("#" + ingTask.nodeId)[0];
				if(nodeId){
					var rectAttributes = nodeId.attributes;
					for (var j = 0; j < rectAttributes.length; j++) {
						var rectAttribute = rectAttributes[j];
						if (rectAttribute.name == "stroke") {
							backUpColorDictionary[ingTask.nodeId] = rectAttribute.nodeValue;
							rectAttribute.nodeValue = TASK_ING_COLOR;
						}
						if (rectAttribute.name == "stroke-width") {
							backUpWidthDictionary[ingTask.nodeId] = rectAttribute.nodeValue;
							rectAttribute.nodeValue = TASK_ING_WIDTH;
						}

					}
				}
			}
		}
	}
	
	this.showFlowImgStatus = function() {
		this.taskListEnd = _GlobalFlowVar.taskListEnd;
		this.taskListIng = _GlobalFlowVar.taskListIng;
		this.nodeInforArr = _GlobalFlowVar.nodeInforArr;
		if (this.isIE) {
			// 标记流程图
			this.addGraphicInfo();
			this.markImags();
		} else {
			// 标记流程图
			this.signProcessState();
		}
	};
	/**
	 * 初始化
	 */
	this.loadFlowGraphicData = function() {
		if (this.isIE) {
			$('#' + this.parentId).append(
					"<img src= " + this.action + "?processInstanceId="
							+ this.processInstanceId + "&processDefinitionKey="
							+ this.processDefinitionKey + " />");
		} else {
			// 加载svg图片
			$.ajax({
				type : "GET",
				url : this.action,
				dataType : "html",// 返回json格式的数据
				data : "flag=svg&processInstanceId=" + this.processInstanceId
						+ "&processDefinitionKey=" + this.processDefinitionKey,
				success : function(src) {
					$('#' + _self.parentId).html(src);
					//防止详细信息先加载
					_self.signProcessState();
				}
			});
		}
	};
	/**
	 * 隐藏状态操作
	 */
	this.hideFlowImgStatus = function(flag) {
		if (flag) {
			if (this.isIE) {
				this.hideMark();
			} else {
				this.clearTaskState();
			}
		} else {
			if (this.isIE) {
				this.markImags();
			} else {
				this.signProcessState();
			}
		}
	};
}

/**
 * 定义运行轨迹类
 */
function RunTrack(config) {
	this._config = config;
	this.action = config.action;
	this.processInstanceId = config.processInstanceId;
	// 运行轨迹
	var runningTrackInfor = [];
	var runningTrackIndex = 0;
	var tempNodeID = '';
	var runningTrackLength = 0;
	var currentRunningTrack = [];
	var runningTrackThreadId = 0;
	// 保存流程节点本身式样
	var backUpRunningTrackColorDictionary = {};
	var backUpRunningTrackWidthDictionary = {};
	/** ******************************************函数********************************************************* */
	this.clearRunningTracks = function() {
		runningTrackIndex = 0;
		if (runningTrackLength != 0 && runningTrackIndex < runningTrackLength) {
			currentRunningTrack = runningTrackInfor[runningTrackIndex];
			var rectAttributes = $("#" + currentRunningTrack.nodeId)[0].attributes;
			for (var j = 0; j < rectAttributes.length; j++) {
				var rectAttribute = rectAttributes[j];
				if (rectAttribute.name == "stroke") {
					rectAttribute.nodeValue = backUpRunningTrackColorDictionary[currentRunningTrack.nodeId];
				}
				if (rectAttribute.name == "stroke-width") {
					rectAttribute.nodeValue = backUpRunningTrackWidthDictionary[currentRunningTrack.nodeId];
				}
			}
			runningTrackIndex = runningTrackIndex + 1;
		}
	};
	// 去掉重复的节点ID
	this.distinctProcessNodeID = function() {
		var temprunningTrackInfor = new Array();
		var index = 0;
		for (var i = 0; i < runningTrackInfor.length; i++) {
			if (!this
					.confirmExists(temprunningTrackInfor, runningTrackInfor[i])) {
				temprunningTrackInfor[index] = runningTrackInfor[i];
				index = index + 1;
			}
		}
		runningTrackInfor = temprunningTrackInfor;
		runningTrackLength = temprunningTrackInfor.length;
	};
	// 判断是否存在
	this.confirmExists = function(tempArray, tempNode) {
		for (var i = 0; i < tempArray.length; i++) {
			if (tempArray[i].nodeId == tempNode.nodeId
					&& tempArray[i].eventName != tempNode.eventName) {
				return true;
			}
		}
	};
	/**
	 * 加载轨迹洗数据
	 */
	this.loadRunTrackData = function() {
		   $.ajax({
					type : "get",// 使用get方法访问后台
					dataType : "json",// 返回json格式的数据
					url : this.action,// 要访问的后台地址
					data : {
						processInstanceId : this.processInstanceId
					},
					success : function(msg) {// msg为返回的数据，在这里做数据绑定
						if (!msg && !msg.data) {
							alert("返回数据为null");
							return;
						}
						var data = msg.data;
						runningTrackInfor = data.runningTrackInfor;
						// 生成页面元素
						var runningTrackDIV = $("#"+TaskDeatailInfor._config.runningTrackDIV);
						runningTrackDIV
								.append("<h3><span id='clz'>流程运行信息</span></h3>");
						var taskNotDoneTb = $("<div id='taskNotDoneTb'>");
						var table = $("<table width='100' class='table-list'>");
						var table_thead;
						var table_tr;

						if (runningTrackInfor) {
							table_thead = $("<thead>");
							table_thead
									.append("<th style='width: 100px'>轨迹编号</th>");
							table_thead
									.append("<th style='width: 270px'>轨迹令牌编号</th>");
							table_thead
									.append("<th style='width: 270px'>轨迹父令牌编号</th>");
							table_thead
									.append("<th style='width: 130px'>执行时间</th>");
							table_thead
									.append("<th style='width: 130px'>事件名称</th>");
							table_thead
									.append("<th style='width: 60px'>处理者</th>");
							table_thead
									.append("<th style='width: 270px'>流程实例编号</th>");
							table_thead.append("</thead>");
							table.append(table_thead);

							$.each(runningTrackInfor,
									function(i, row) {
										table_tr = $("<tr "
												+ (i % 2 == 0 ? "class='gray'"
														: "") + ">");
										table_tr.append("<td>" + row.nodeId
												+ "</td>");
										table_tr.append("<td>" + row.tokenId
												+ "</td>");
										table_tr.append("<td>"
												+ row.parentTokenId + "</td>");
										table_tr.append("<td>"
												+ (!row.executionTime ? ''
														: row.executionTime)
												+ "</td>");
										table_tr.append("<td class='left'>"
												+ (!row.eventName ? ''
														: row.eventName)
												+ "</td>");
										table_tr.append("<td class='left'>"
												+ (!row.operator ? ''
														: row.operator)
												+ "</td>");
										table_tr.append("<td class='left'>"
												+ row.processInstanceId
												+ "</td>");
										table_tr.append("</tr>");
										table.append(table_tr);

									});
						}
						if (runningTrackInfor) {
							runningTrackLength = runningTrackInfor.length;
						}
						taskNotDoneTb.append(table);
						runningTrackDIV.append(taskNotDoneTb);

					}
				});
	};
	this.runningTrack = function(flag) {
		if(_GlobalFlowVar.isIE){
			alert("IE浏览器不支持该功能!");
			$("#runningTrack").attr("checked",null);
			return;
		}
		if (flag) {
			if (runningTrackInfor && runningTrackInfor.length != 0) {
				runningTrackIndex = 0;
				// 去掉重复的节点ID
				this.distinctProcessNodeID();
				runningTrackThreadId = window.setInterval('moveRunningTrack()',RUNNING_MILLESIMAL_SPEED);
				$("#runningTrack").attr("disabled", "disabled");
			} else {
				alert("无流程运行轨迹 数据");
				$("#runningTrack").attr("disabled", "disabled");
			}
		} 
	};
	/** ****************************************私有函数******************************************************* */
	// 移除前一个轨迹
	removePreviousRunningTrack = function(index) {
		if (index != 0) {
			var nodeId = $("#" + runningTrackInfor[index - 1].nodeId)[0];
			if(nodeId){
				var rectAttributes = nodeId.attributes;
				for (var j = 0; j < rectAttributes.length; j++) {
					var rectAttribute = rectAttributes[j];
					if (rectAttribute.name == "stroke") {
						rectAttribute.nodeValue = backUpRunningTrackColorDictionary[runningTrackInfor[runningTrackIndex - 1].nodeId];
					}
					if (rectAttribute.name == "stroke-width") {
						rectAttribute.nodeValue = backUpRunningTrackWidthDictionary[runningTrackInfor[runningTrackIndex - 1].nodeId];
					}

				}
			}
		}
	};
	// 移动节点光标
	moveRunningTrack = function() {
		if (runningTrackLength != 0 && runningTrackIndex < runningTrackLength) {
			currentRunningTrack = runningTrackInfor[runningTrackIndex];
			if(currentRunningTrack){
				var nodeId = $("#" + currentRunningTrack.nodeId)[0];
				if(nodeId){
					var rectAttributes = nodeId.attributes;
					for (var j = 0; j < rectAttributes.length; j++) {
						var rectAttribute = rectAttributes[j];
						if (rectAttribute.name == "stroke") {
							if (tempNodeID != currentRunningTrack.nodeId) {
								backUpRunningTrackColorDictionary[currentRunningTrack.nodeId] = rectAttribute.nodeValue;
								rectAttribute.nodeValue = RUNNING_TRACK_COLOR;
							}
						}
						if (rectAttribute.name == "stroke-width") {
							if (tempNodeID != currentRunningTrack.nodeId) {
								backUpRunningTrackWidthDictionary[currentRunningTrack.nodeId] = rectAttribute.nodeValue;
								rectAttribute.nodeValue = RUNNING_TRACK_WIDTH;
							}
						}
					}
					this.tempNodeID = currentRunningTrack.nodeId;
				}
			}
		} else {
			$("#runningTrack").removeAttr("disabled");
			$("#runningTrack").attr("checked",null);
			if (runningTrackIndex == runningTrackLength) {
				clearInterval(runningTrackThreadId);
			}
		}
		//移除轨迹
		removePreviousRunningTrack(runningTrackIndex);
		runningTrackIndex = runningTrackIndex + 1;
	};
};
