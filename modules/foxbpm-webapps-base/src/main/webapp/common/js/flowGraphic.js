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
		init : function(config) {
			taskListEnd = config.taskListEnd || [];
			taskListIng = config.taskListIng || [];
			nodeInforArr = config.nodeInforArr || [];
		}
};
var backUpColorDictionary = {};
var backUpWidthDictionary = {};


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
		for ( var nodeInfor in nodeInforArr) {
			var nodeInfoObj = nodeInforArr[nodeInfor];
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
		$.each(taskListEnd, function(i, task) {
			markImg(task.nodeId, "green", 2, 0);
		});
		$.each(taskListIng, function(i, task) {
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
		for (var i = 0; i < taskListEnd.length; i++) {
			var endTask = taskListEnd[i];
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
		for (var i = 0; i < taskListIng.length; i++) {
			var ingTask = taskListIng[i];
			var rectAttributes = $("#" + ingTask.nodeId)[0].attributes;
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
	function signEndTaskState() {
		if (taskListEnd == null || taskListEnd.length == 0) {
			return;
		} else {
			for (var i = 0; i < taskListEnd.length; i++) {
				var endTask = taskListEnd[i];
				var rectAttributes = $("#" + endTask.nodeId)[0].attributes;
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

	function signIngTaskState() {
		if (taskListIng == null || taskListIng.length == 0) {
			return;
		} else {
			for (var i = 0; i < taskListIng.length; i++) {
				var ingTask = taskListIng[i];
				var rectAttributes = $("#" + ingTask.nodeId)[0].attributes;
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

	this.showFlowImgStatus = function() {
		this.taskListEnd = _GlobalFlowVar.taskListEnd || [];
		this.taskListIng = _GlobalFlowVar.taskListIng || [];
		this.nodeInforArr = _GlobalFlowVar.nodeInforArr || [];
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
	this.loadFlowImg = function() {
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
				data : "flag=svg&processDefinitionId=" + this.processInstanceId
						+ "&processDefinitionKey=" + this.processDefinitionKey,
				success : function(src) {
					$('#' + _self.parentId).html(src);
				}
			});
		}
	};
	/**
	 * 隐藏状态操作
	 */
	this.hideFlowImgStatus = function(flag) {
		if (flag) {
			if (isIE) {
				this.hideMark();
			} else {
				this.clearTaskState();
			}
		} else {
			if (isIE) {
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
	// 运行轨迹
	var runningTrackInfor=[];
	var runningTrackIndex = 0;
	var tempNodeID = '';
	var runningTrackLength = 0;
	var currentRunningTrack = [];
	var runningTrackThreadId = 0;
	//保存流程节点本身式样
	var backUpRunningTrackColorDictionary = {};
	var backUpRunningTrackWidthDictionary = {};
	/********************************************函数**********************************************************/
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
			if (!this.confirmExists(temprunningTrackInfor, runningTrackInfor[i])) {
				temprunningTrackInfor[index] = runningTrackInfor[i];
				index = index + 1;
			}
		}
		runningTrackInfor = temprunningTrackInfor;
		runningTrackLength = temprunningTrackInfor.length;
	};
	//判断是否存在
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
			url : "service/task/runTrack",// 要访问的后台地址
			data : {
				processInstanceId : _processInstanceId
			},
			success : function(msg) {// msg为返回的数据，在这里做数据绑定
				if (!msg && !msg.data) {
					alert("返回数据为null");
					return;
				}
				var data = msg.data;
				runningTrackInfor = data.runningTrackInfor;
				//生成页面元素
				var runningTrackDIV = $("#runningTrackDIV");
				runningTrackDIV.append("<h3><span id='clz'>流程运行信息</span></h3>");
				var taskNotDoneTb = $("<div id='taskNotDoneTb'>");
				var table = $("<table width='100' class='table-list'>");
				var table_thead;
				var table_tr;

				if (runningTrackInfor) {
					table_thead = $("<thead>");
					table_thead.append("<th style='width: 100px'>轨迹编号</th>");
					table_thead.append("<th style='width: 270px'>轨迹令牌编号</th>");
					table_thead.append("<th style='width: 270px'>轨迹父令牌编号</th>");
					table_thead.append("<th style='width: 130px'>执行时间</th>");
					table_thead.append("<th style='width: 130px'>事件名称</th>");
					table_thead.append("<th style='width: 60px'>处理者</th>");
					table_thead.append("<th style='width: 270px'>流程实例编号</th>");
					table_thead.append("</thead>");
					table.append(table_thead);
					
					$.each(runningTrackInfor, function(i, row) {
						table_tr = $("<tr " + (i % 2 == 0 ? "class='gray'" : "") + ">");
						table_tr.append("<td>" + row.nodeId + "</td>");
						table_tr.append("<td>" + row.tokenId + "</td>");
						table_tr.append("<td>" + row.parentTokenId + "</td>");
						table_tr.append("<td>"+ (!row.executionTime ? '' : row.executionTime) + "</td>");
						table_tr.append("<td class='left'>"+ (!row.eventName ? '' : row.eventName) + "</td>");
						table_tr.append("<td class='left'>"+ (!row.operator ? '' : row.operator) + "</td>");
						table_tr.append("<td class='left'>"+ row.processInstanceId + "</td>");
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
	this.runningTrack = function(flag)
	{
		if(flag){
			if (runningTrackInfor && runningTrackInfor.length != 0) {
				runningTrackIndex = 0;
				//去掉重复的节点ID
				this.distinctProcessNodeID();
				runningTrackThreadId = window.setInterval('moveRunningTrack()',RUNNING_MILLESIMAL_SPEED);
				$("#runningTrack").attr("disabled", "disabled");
				$("#runningTrackDIV").show();
			} else {
				alert("无流程运行轨迹 数据");
				$("#runningTrack").attr("disabled", "disabled");
			}
		}else {
			$("#runningTrackDIV").hide();
		}
	};
	/******************************************私有函数********************************************************/
	//移除前一个轨迹
	removePreviousRunningTrack = function(index) {
		if (index != 0) {
			var rectAttributes = $("#" + runningTrackInfor[index - 1].nodeId)[0].attributes;
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
	};
	// 移动节点光标
	moveRunningTrack = function() {
		if (runningTrackLength != 0 && runningTrackIndex < runningTrackLength) {
			currentRunningTrack = runningTrackInfor[runningTrackIndex];
			var rectAttributes = $("#" + currentRunningTrack.nodeId)[0].attributes;
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
		} else {
			$("#runningTrack").removeAttr("disabled");
			if (runningTrackIndex == runningTrackLength) {
				clearInterval(runningTrackThreadId);
			}
		}
		//移除轨迹
		removePreviousRunningTrack(runningTrackIndex);
		runningTrackIndex = runningTrackIndex + 1;
	};
};
