var RUNNING_TRACK_COLOR = "#ff7200", RUNNING_TRACK_WIDTH = 5, RUNNING_MILLESIMAL_SPEED = 1000;
function FlowRunTrackInfor(config) {
	this.eleId = config.eleId;
	this.isIELowVer = config.isIELowVer || false;
	this._config = config || {};
	this.action = config.action || '';
	this.processInstanceId = config.processInstanceId || '';
	this.operateId = config.operateId || '';
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
	/** ******************************************public函数********************************************************* */
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
		var $this = this;
		$("#taskNotDoneTb").remove();
		$.ajax({
					type : "get",// 使用get方法访问后台
					dataType : "json",// 返回json格式的数据
					url : this.action,// 要访问的后台地址
					data : {
						processInstanceId : this.processInstanceId
					},
					error:function(){
						$("#loading").hide();
						$("#loadTips").show();
					},
					beforeSend:function(){
						$("#loading").show();
						$("#loadTips").hide();
					},
					success : function(msg) {// msg为返回的数据，在这里做数据绑定
						$("#loading").hide();
						if (!msg && !msg.data) {
							alert("返回数据为null");
							return;
						}
						var data = msg.data;
						runningTrackInfor = data.runningTrackInfor;
						// 生成页面元素
						var runningTrackDIV = $("#" + $this.eleId);
						var taskNotDoneTb = $("<div id='taskNotDoneTb'>");
						var table = $("<table width='100' class='table-list'>");
						var table_thead;
						var table_tr;

						if (runningTrackInfor) {
							$("#loadTips").hide();
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
												+ (!row.parentTokenId ? ''
														: row.parentTokenId)
												+ "</td>");
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
	
	this.runningTrack = function(flag, eleId) {
		if (this.isIELowVer) {
			alert("IE浏览器不支持该功能!");
			$("#"+eleId).attr("checked", null);
			return;
		}
		if (flag) {
			if (runningTrackInfor && runningTrackInfor.length != 0) {
				runningTrackIndex = 0;
				// 去掉重复的节点ID
				this.distinctProcessNodeID();
				runningTrackThreadId = window.setInterval(function(){moveRunningTrack(eleId);},RUNNING_MILLESIMAL_SPEED);
				$("#"+eleId).attr("disabled", "disabled");
			} else {
				alert("无流程运行轨迹 数据");
				$("#"+eleId).attr("checked", null);
			}
		}
	};
	/** ****************************************private函数******************************************************* */
	// 移除前一个轨迹
	/*removePreviousRunningTrack = function(index) {
		if (index != 0) {
			var nodeId = $("#" + runningTrackInfor[index - 1].nodeId)[0];
			if (nodeId) {
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
	};*/
	// 移除前一个轨迹
	removePreviousRunningTrack = function(index) {
		if (index != 0) {
			var nodeId = $("#" + runningTrackInfor[index - 1].nodeId)[0];
			if (nodeId) {
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
			
			var markerPathId = runningTrackInfor[index - 1].nodeId+"MarkerPath";
			var markerPath =$("#" + markerPathId)[0];
			if (markerPath) {
				var rectAttributes = markerPath.attributes;
				for (var j = 0; j < rectAttributes.length; j++) {
					var rectAttribute = rectAttributes[j];
					if (rectAttribute.name == "stroke") {
						rectAttribute.nodeValue = backUpRunningTrackColorDictionary[markerPathId];
					}
					if (rectAttribute.name == "stroke-width") {
						rectAttribute.nodeValue = backUpRunningTrackWidthDictionary[markerPathId];
					}

				}
			}
		}
	};
	// 移动节点光标
/*	moveRunningTrack = function(eleId) {
		if (runningTrackLength != 0 && runningTrackIndex < runningTrackLength) {
			currentRunningTrack = runningTrackInfor[runningTrackIndex];
			if (currentRunningTrack) {
				var nodeId = $("#" + currentRunningTrack.nodeId)[0];
				if (nodeId) {
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
			if(eleId && ($("#"+eleId).attr('type') === 'checkbox')){
				$("#"+eleId).removeAttr("disabled");
				$("#"+eleId).attr("checked", null);
			}
			if (runningTrackIndex == runningTrackLength) {
				clearInterval(runningTrackThreadId);
			}
		}
		// 移除轨迹
		removePreviousRunningTrack(runningTrackIndex);
		runningTrackIndex = runningTrackIndex + 1;
	};*/
	
	moveRunningTrack = function(eleId) {
		if (runningTrackLength != 0 && runningTrackIndex < runningTrackLength) {
			currentRunningTrack = runningTrackInfor[runningTrackIndex];
			if (currentRunningTrack) {
				var nodeId = $("#" + currentRunningTrack.nodeId)[0];
				var markerPathId = currentRunningTrack.nodeId+"MarkerPath";
				var markerPath =$("#" + markerPathId)[0];
				if (nodeId) {
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
				
				if (markerPath) {
					var rectAttributes = markerPath.attributes;
					for (var j = 0; j < rectAttributes.length; j++) {
						var rectAttribute = rectAttributes[j];
						if (rectAttribute.name == "stroke") {
							if (tempNodeID != markerPathId) {
								backUpRunningTrackColorDictionary[markerPathId] = rectAttribute.nodeValue; 
								rectAttribute.nodeValue = RUNNING_TRACK_COLOR;
							}
						}
						if (rectAttribute.name == "stroke-width") {
							if (tempNodeID != markerPathId) {
								backUpRunningTrackWidthDictionary[markerPathId] = rectAttribute.nodeValue; 
								rectAttribute.nodeValue = RUNNING_TRACK_WIDTH;
							}
						}
					}
					this.tempNodeID = markerPathId;
				}
			}
		} else {
			if(eleId && ($("#"+eleId).attr('type') === 'checkbox')){
				$("#"+eleId).removeAttr("disabled");
				$("#"+eleId).attr("checked", null);
			}
			if (runningTrackIndex == runningTrackLength) {
				clearInterval(runningTrackThreadId);
			}
		}
		//移除轨迹
		removePreviousRunningTrack(runningTrackIndex);
		runningTrackIndex = runningTrackIndex + 1;
	};
	
	
	
	
	//定义回调函数
	this.init = function() {
		$("#"+this.eleId).append("<h3><span id='clz'>流程运行信息</span></h3>");
		$("#"+this.eleId).append("<div id='warning' style='text-align:center;'><span id=loadTips style='display:none'>数据为空!</span><img style='display:none' src='images/loading.gif' id=loading /></div>");
		
		
		//加载最新数据
		this.loadRunTrackData();
	};
	//展现数据
	this.show = function(){
		$("#"+this.eleId).show();
	};
	this.hide = function(){
		$("#"+this.eleId).hide();
	};
}
