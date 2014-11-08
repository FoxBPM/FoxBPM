/**
 * 流程图处理类 taskListEnd 任务结束节点 taskListIng 任务进入节点 nodeInfoArr 节点信息 isIELowVer
 * 是否IE低版本 eleId 流程图添加到该元素中 <div id='xxx'><img /></div> action 图形加载后台action
 * processDefinitionId 流程定义id
 */
function FlowGraphicInfor(config) {
	this.eleId = config.eleId;
	this.isIELowVer = config.isIELowVer || false;
	this.taskListEnd = config.taskListEnd || [];
	this.taskListIng = config.taskListIng || [];
	this.nodeInforArr = config.nodeInforArr || [];
	this.action = config.action;
	this.processInstanceId = config.processInstanceId;
	this.processDefinitionKey = config.processDefinitionKey;
	this.runTrackObj = config.runTrackObj;
	var _self = this;

	var TASK_END_COLOR = "#458f05";
	var TASK_END_WIDTH = "2";
	var TASK_ING_COLOR = "#ff7200";
	var TASK_ING_WIDTH = "2";
	var backUpColorDictionary = {};
	var backUpWidthDictionary = {};
	
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
		$('#' + this.eleId).append(divcontent);
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
			var endTask = _self.taskListEnd[i];
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
		for (var i = 0; i < _self.taskListIng.length; i++) {
			var ingTask = _self.taskListIng[i];
			var nodeId = $("#" + ingTask.nodeId)[0];
			if (nodeId) {
				var rectAttributes = nodeId.attributes;
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
				if (nodeId) {
					var rectAttributes = nodeId.attributes;
					for (var j = 0; j < rectAttributes.length; j++) {
						var rectAttribute = rectAttributes[j];
						if (rectAttribute.name == "stroke") {
							if (!backUpColorDictionary[endTask.nodeId]) {
								backUpColorDictionary[endTask.nodeId] = rectAttribute.nodeValue;
							}
							rectAttribute.nodeValue = TASK_END_COLOR;
						}
						if (rectAttribute.name == "stroke-width") {
							if (!backUpWidthDictionary[endTask.nodeId]) {
								backUpWidthDictionary[endTask.nodeId] = rectAttribute.nodeValue;
							}
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
				if (nodeId) {
					var rectAttributes = nodeId.attributes;
					for (var j = 0; j < rectAttributes.length; j++) {
						var rectAttribute = rectAttributes[j];
						if (rectAttribute.name == "stroke") {
							if (!backUpColorDictionary[ingTask.nodeId]) {
								backUpColorDictionary[ingTask.nodeId] = rectAttribute.nodeValue;
							}
							rectAttribute.nodeValue = TASK_ING_COLOR;
						}
						if (rectAttribute.name == "stroke-width") {
							if (!backUpWidthDictionary[ingTask.nodeId]) {
								backUpWidthDictionary[ingTask.nodeId] = rectAttribute.nodeValue;
							}
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

		if (this.isIELowVer) {
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
		
		if (this.isIELowVer) {
			$('#flowImg').append(
					"<img src= " + this.action + "/flowGraphic/flowImg?processInstanceId="
							+ this.processInstanceId + "&processDefinitionKey="
							+ this.processDefinitionKey + " />");
		} else {
			// 加载svg图片
			$.ajax({
				type : "GET",
				url : this.action + '/flowGraphic/flowSvg',
				dataType : "json",// 返回json格式的数据
				data : "processInstanceId=" + this.processInstanceId
						+ "&processDefinitionKey=" + this.processDefinitionKey,
				success : function(src) {
					$('#flowImg').html(src.data);
					// 防止详细信息先加载
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
			if (this.isIELowVer) {
				this.hideMark();
			} else {
				this.clearTaskState();
			}
		} else {
			if (this.isIELowVer) {
				this.markImags();
			} else {
				this.signProcessState();
			}
		}
	};

	this.init = function() {
		var flowGraphicDiv = $("#" + this.eleId);
		var h3 = $("<h3>");
		h3.append("<ul><span>流程图</span></ul>");
		var ul = $("<ul>");
		ul.append("<li class='img01'>已完成</li>");
		ul.append("<li class='img02'>进行中</li>");
		ul.append("<li><input id='yczt' type='checkbox' name='cczt' />隐藏状态</li>");
		var $this = this;
		if ($this.runTrackObj) {
			ul.append($("<li><input id='trackInforEle' type='checkbox' name='trackInforEle' />轨迹信息</li>")
							.on(
									"click",
									function() {
										if ($("#trackInforEle").attr("checked") == 'checked') {
											$this.runTrackObj.show();
										} else {
											$this.runTrackObj.hide();
										}
									}));
			ul.append($("<li><input id='trackRunEle' type='checkbox' name='trackRunEle' />轨迹动态运行</li>")
							.on(
									"click",
									function() {
										$this.runTrackObj
												.runningTrack(
														($("#trackRunEle")
																.attr("checked") == 'checked'),
														"trackRunEle");
									}));
		}
		h3.append(ul);
		flowGraphicDiv.append(h3);
		flowGraphicDiv
				.append($("<div id='flowImg' class='pos_abs' style='position: relative; overflow-x: auto; width: 100%;'></div> "));
		// 事件注册
		$("#yczt").bind("click", function(){
			$this.hideFlowImgStatus(($(this).attr("checked") == 'checked'));
		});
		this.loadFlowGraphicData();
	};
	//初始化
	this.initTaskInfor = function(param) {
		this.taskListEnd = param.taskListEnd || [];
		this.taskListIng = param.taskListIng || [];
		this.nodeInforArr = param.nodeInforArr || [];
		//这里初始化流程图状态
		this.signProcessState();
	};
}
