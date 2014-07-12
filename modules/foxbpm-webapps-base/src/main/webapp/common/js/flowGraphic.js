/**
 * 标记常量属性
 */
var TASK_END_COLOR = "#458f05";
var TASK_END_WIDTH = "2";
var TASK_ING_COLOR = "#ff7200";
var TASK_ING_WIDTH = "2";
var RUNNING_TRACK_COLOR = "#ff7200";
var RUNNING_TRACK_WIDTH = 5;
var RUNNING_MILLESIMAL_SPEED = 300;

/**
 * 流程图处理类 taskListEnd 任务结束节点 taskListIng 任务进入节点 nodeInfoArr 节点信息 isIE 是否IE
 * parentId 流程图添加到该元素中 <div id='xxx'><img /></div> action 图形加载后台action
 * processDefinitionId 流程定义id
 */
function FlowGraphic(param) {
	this.taskListEnd = param.taskListEnd || [];
	this.taskListIng = param.taskListIng || [];
	this.nodeInfoArr = param.nodeInfoArr || [];
	this.parentId = param.parentId;
	this.isIE = param.isIE;
	this.action = param.action;
	this.processDefinitionId = param.processDefinitionId;

	this.removePreviousRunningTrack = function(currentTrack) {
		if (runningTrackIndex != 0
				&& currentTrack.nodeId != runningTrackInfo[runningTrackIndex - 1].nodeId) {
			var rectAttributes = $("#"
					+ runningTrackInfo[runningTrackIndex - 1].nodeId)[0].attributes;
			for (var j = 0; j < rectAttributes.length; j++) {
				var rectAttribute = rectAttributes[j];
				if (rectAttribute.name == "stroke") {
					rectAttribute.nodeValue = backUpRunningTrackColorDictionary[runningTrackInfo[runningTrackIndex - 1].nodeId];

				}
				if (rectAttribute.name == "stroke-width") {
					rectAttribute.nodeValue = backUpRunningTrackWidthDictionary[runningTrackInfo[runningTrackIndex - 1].nodeId];
				}

			}
		}
	};
	this.clearRunningTracks = function() {
		runningTrackIndex = 0;
		if (runningTrackLength != 0 && runningTrackIndex < runningTrackLength) {
			currentRunningTrack = runningTrackInfo[runningTrackIndex];
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

	this.moveRunningTrack = function() {
		if (runningTrackLength != 0 && runningTrackIndex < runningTrackLength) {
			currentRunningTrack = runningTrackInfo[runningTrackIndex];
			this.removePreviousRunningTrack(currentRunningTrack);
			var rectAttributes = $("#" + currentRunningTrack.nodeId)[0].attributes;
			for (var j = 0; j < rectAttributes.length; j++) {
				var rectAttribute = rectAttributes[j];
				if (rectAttribute.name == "stroke") {
					if (tempNodeID != currentRunningTrack.nodeId) {
						backUpRunningTrackColorDictionary[currentRunningTrack.nodeId] = rectAttribute.nodeValue;
						rectAttribute.nodeValue = RUNNING_TRACK_COLOR;
					}
					if (tempNodeID == currentRunningTrack.nodeId) {
						rectAttribute.nodeValue = backUpRunningTrackColorDictionary[currentRunningTrack.nodeId];
					}

				}
				if (rectAttribute.name == "stroke-width") {
					if (tempNodeID != currentRunningTrack.nodeId) {
						backUpRunningTrackWidthDictionary[currentRunningTrack.nodeId] = rectAttribute.nodeValue;
						rectAttribute.nodeValue = RUNNING_TRACK_WIDTH;
					}
					if (tempNodeID == currentRunningTrack.nodeId) {
						rectAttribute.nodeValue = backUpRunningTrackWidthDictionary[currentRunningTrack.nodeId];
					}

				}

			}
			tempNodeID = currentRunningTrack.nodeId;
		} else {
			clearInterval(runningTrackThreadId);
			$("#runningTrack").removeAttr("disabled");
		}
		runningTrackIndex = runningTrackIndex + 1;
	};
	/** img图形处理方式* */
	/**
	 * 添加图信息
	 */
	this.addGraphicInfo = function() {
		var divcontent = "";
		for ( var nodeInfo in nodeInfoArr) {
			var nodeInfoObj = nodeInfoArr[nodeInfo];
			divcontent = divcontent
					+ getDiv(nodeInfo, nodeInfoObj.x - 4, nodeInfoObj.y - 4,
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
	;
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

	function markRunningTrack() {
		var trackInfo = runningTrackInfo;
		if (trackInfo == null || trackInfo.length == 0) {
			return;
		} else {
			var element;
			var tempNodeId = "";
			for (var i = 0; i < trackInfo.length; i++) {
				element = trackInfo[i];
				if (tempNodeId != element.nodeId) {
					wait(1000);
				}
				var rectAttributes = $("#" + element.nodeId)[0].attributes;
				for (var j = 0; j < rectAttributes.length; j++) {
					var rectAttribute = rectAttributes[j];
					if (rectAttribute.name == "stroke") {
						backUpColorDictionary[element.nodeId] = rectAttribute.nodeValue;
						rectAttribute.nodeValue = RUNNING_TRACK_COLOR;
					}
					if (rectAttribute.name == "stroke-width") {
						backUpWidthDictionary[element.nodeId] = rectAttribute.nodeValue;
						rectAttribute.nodeValue = 2;
					}

				}
				tempNodeId = element.nodeId;
			}
		}
	}

	/**
	 * 初始化
	 */
	this.loadFlowImg = function() {
		if (this.isIE) {
			$('#' + this.parentId).append(
					"<img src= " + this.action + "?processDefinitionId="
							+ this.processDefinitionId + " />");
			// 标记流程图
			this.addGraphicInfo();
			this.markImags();
		} else {
			// 加载svg图片
			$.ajax({
				type : "POST",
				url : action,
				data : "flag=svg&processDefinitionId=" + processDefinitionId,
				success : function(src) {
					$('#' + parentId).html(src);
					// 标记流程图
					signProcessState();
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
				flowGraphic.hideMark();
			} else {
				flowGraphic.clearTaskState();
			}
		} else {
			if (isIE) {
				flowGraphic.markImags();
			} else {
				flowGraphic.signProcessState();
			}
		}
	};
	return this;
}
