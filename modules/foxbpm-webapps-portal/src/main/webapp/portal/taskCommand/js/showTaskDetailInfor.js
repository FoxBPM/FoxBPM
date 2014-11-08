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
	isIE : window.ActiveXObject && $.browser.msie && $.browser.version < 8.0,
	_self : this,
	_bpmServiceUrl : _bpmServiceUrl,
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
		taskDetailDiv : 'taksDetailInfoDiv',
		runningTrackDiv : 'runningTrackDiv',
		flowGraphicDiv : 'flowGraphicDiv'
	};
	this._flowGraphic = new FlowGraphic({
		'isIE' : _GlobalFlowVar.isIE,
		'parentId' : 'flowImg',
		'action' : _GlobalFlowVar._bpmServiceUrl + '/flowGraphic/flowImg',
		'processInstanceId' : _processInstanceId,
		'processDefinitionKey' : _processDefinitionKey
	});
	this._runTrack = new RunTrack({
		action : _GlobalFlowVar._bpmServiceUrl + '/task/runTrack',
		processInstanceId : _processInstanceId
	});
	this._processInfor = new ProcessInfor({
		
	});
	TaskDeatailInfor = this;
};
Foxbpm.TaskDeatailInfor.prototype = {
	init : function() {
		var _self = this;
		if (_processInstanceId && "" != _processInstanceId) {
			// 设置定义名称
			$.ajax({
						type : "get",// 使用get方法访问后台
						dataType : "json",// 返回json格式的数据
						url : _GlobalFlowVar._bpmServiceUrl + "/task/taskInfor",// 要访问的后台地址
						data : {
							processInstanceId : _processInstanceId
						},
						success : function(msg) {// msg为返回的数据，在这里做数据绑定
							if (!msg && !msg.data) {
								alert("返回数据为null");
								return;
							}
							var data = msg.data;
							//加载流程状态
							_self._processInfor.loadProcessSetp(data.nowStep);
							var taskDetailDiv = $("#"+ TaskDeatailInfor._config.taskDetailDiv);
							//添加流程主题信息
							$("#processInfo").before($("<div class='container process'><h1 id='processDefinitionName'>"
													+ data.processName
													+ "</h1></div><br>"));
							if (data.endTasks) {
								taskDetailDiv.append("<div style='border-bottom-style:solid;border-bottom-width:1px;border-bottom-color:#ddd'><span style='padding-right:4px;padding-bottom:3px;padding-left:4px;font-size:11px;font-weight:bold;display:inline-block;line-height:1;padding-top:3px;color:rgb(255,255,255);padding-top:0.2em;padding-left:0.6em;padding-right:0.6em;padding-bottom:0.3em;font-size:75%;display:inline;background-color: rgb(153, 153, 153);'>已完成</span></div>");
								var completedDetailInfoDiv = $("<div class='js-discussion js-socket-channel'>");
								var participants = "";
								$.each(data.endTasks,
									function(j, data) {
										var completedRecord = $("<div class='timeline-comment-wrapper js-comment-container'>");
										var a = $("<a href='javascript:void(0);'>");
										var img = $("<img width='48' height='48' class='timeline-comment-avatar'>");
										img.attr("alt", data.userName);
										img.attr("src",_GlobalFlowVar._bpmServiceUrl+ "identity/users/"+data.userName+"/picture");
										a.append(img);
										var comment = $("<div class='comment timeline-comment js-comment js-task-list-container  is-task-list-enabled'>");
										var comment_header = $("<div class='timeline-comment-header'>");
										var comment_action = $("<div class='timeline-comment-actions'>");
										var comment_header_text = $("<div class='timeline-comment-header-text'>");
										comment_header_text
												.append("<strong> <a class='author' href='javascript:void(0);'>"
														+ data.userName
														+ "</a>");
										comment_header_text.append("<strong>&nbsp"
														+ data.nodeName
														+ "&nbsp"
														+ (!data.commandMessage? '': data.commandMessage)
														+ "&nbsp"
														+ data.endTime);
										var comment_content = $("<div class='comment-content'>");
										var edit_comment_hide = $("<div class='edit-comment-hide'>");
										edit_comment_hide.append("<div class='comment-body markdown-body markdown-format js-comment-body'><p>"
														+ (!data.taskComment ? '': data.taskComment)
														+ "</p></div>");
										comment_header.append(comment_action);
										comment_header.append(comment_header_text);
										comment_content.append(edit_comment_hide);
										comment.append(comment_header);
										comment.append(comment_content);
										completedRecord.append(a);
										completedRecord.append(comment);
										var loadMsg = $("<a style='text-decoration:none;cursor:pointer;float: right;'>");
										loadMsg.attr("href",'javascript:void(0);');
										loadMsg.attr("flag",'false'); 
										loadMsg.html("+");
										loadMsg.on("click",{obj:loadMsg,flag:j},function(event){
											_self._processInfor.loadMsg(event.data.obj,event.data.flag);
										});
										completedRecord.append(loadMsg);
										completedDetailInfoDiv.append(completedRecord);
										if (participants.indexOf(data.userName) < 0) {
											participants += data.userName+ "#";
										}
										/**********************处理信息*****************************/
										var html = _self._processInfor.createMsgTemplet(j, data.id);
										completedDetailInfoDiv.append(html);
										/**********************处理信息*****************************/
									});
								taskDetailDiv.append(completedDetailInfoDiv);
								//初始化流程参入者信息
								participants = participants.substring(0,
										participants.length - 1).split("#");
								var len = participants.length;
								if (len > 0) {
									var participationDiv = $("#partial-users-participants");
									participationDiv.append("<h3 class='discussion-sidebar-heading'>"+ len+ " 流程参入者</h3>");
									var participationAvatarsDiv = $("<div class='participation-avatars'>");
									var a = null;
									var a_Img = null;
									for (var i = 0; i < len; i++) {
										a = $("<a class='participant-avatar tooltipped tooltipped-s'>");
										a.attr("title", participants[i]);
										a.attr("href", "javascript:void(0);");
										a_Img = $("<img width='20' height='20' class='avatar'>");
										a_Img.attr("alt", participants[i]);
										a_Img.attr("src",_GlobalFlowVar._bpmServiceUrl+ "identity/users/"+ participants[i]+ "/picture");
										a.append(a_Img);
										participationAvatarsDiv.append(a);
									}
									participationDiv.append(participationAvatarsDiv);
								}
							}
							// 未结束任务
							if (data.openTasks) {
								taskDetailDiv
										.append("<div style='border-bottom-style:solid;border-bottom-width:1px;border-bottom-color:#ddd'><span style='padding-right:4px;padding-bottom:3px;padding-left:4px;font-size:11px;font-weight:bold;display:inline-block;line-height:1;padding-top:3px;color:rgb(255,255,255);padding-top:0.2em;padding-left:0.6em;padding-right:0.6em;padding-bottom:0.3em;font-size:75%;display:inline;background-color: rgb(153, 153, 153);'>未完成</span></div>");
								var unfinishedDetailInfo = $("<div class='js-discussion js-socket-channel'>");
								$
										.each(
												data.openTasks,
												function(j, data) {
													var completedRecord = $("<div class='timeline-comment-wrapper js-comment-container'>");
													var a = $("<a href='javascript:void(0);'>");
													var img = $("<img width='48' height='48' class='timeline-comment-avatar'>");
													img.attr("alt", "admin");
													img
															.attr("src",
																	_bpmServiceUrl+"/identity/users/admin/picture");
													a.append(img);
													var comment = $("<div class='comment timeline-comment js-comment js-task-list-container  is-task-list-enabled'>");
													var comment_header = $("<div class='timeline-comment-header'>");
													var comment_action = $("<div class='timeline-comment-actions'>");
													var comment_header_text = $("<div class='timeline-comment-header-text'>");
													comment_header_text
															.append("<strong> <a class='author' href='javascript:void(0);'>"
																	+ data.userName
																	+ "</a>");
													comment_header_text
															.append("<strong>&nbsp"
																	+ data.nodeName
																	+ "&nbsp"
																	+ (!data.commandMessage? '': data.commandMessage)
																	+ "&nbsp"
																	+ (!data.endTime? '': data.endTime));
													var comment_content = $("<div class='comment-content'>");
													var edit_comment_hide = $("<div class='edit-comment-hide'>");
													edit_comment_hide
															.append("<div class='comment-body markdown-body markdown-format js-comment-body'><p>"
																	+ (!data.taskComment? ''
																			: data.taskComment)
																	+ "</p></div>");
													comment_header
															.append(comment_action);
													comment_header
															.append(comment_header_text);
													comment_content
															.append(edit_comment_hide);
													comment
															.append(comment_header);
													comment
															.append(comment_content);
													completedRecord.append(a);
													completedRecord
															.append(comment);
													unfinishedDetailInfo
															.append(completedRecord);
												});
								taskDetailDiv.append(unfinishedDetailInfo);
							}
							// 初始化全局变量
							_GlobalFlowVar.init({
								taskListEnd : data.endTasks,
								taskListIng : data.openTasks,
								nodeInforArr : data.positionInfor
							});
							// 加载流程图上展现任务完成情况
							_self.getFlowGraphic().showFlowImgStatus();
							//加载运行轨迹信息
							_self.getRunTrack().loadRunTrackData();
						}
					});
		} else {
			$("#processInfo").hide();
		}
		_self.createFlowchartOper();
		// 加载流程图
		_self.getFlowGraphic().loadFlowGraphicData();
		// 加载运行轨迹数据
	},
	flowImgStatusClick : function() {
		TaskDeatailInfor._flowGraphic.hideFlowImgStatus(($(this)
				.attr("checked") == 'checked'));
	},
	runningTrackClick : function() {
		TaskDeatailInfor._runTrack
				.runningTrack(($(this).attr("checked") == 'checked'));
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
	createFlowchartOper : function() {
		var flowGraphicDiv = $("#" + TaskDeatailInfor._config.flowGraphicDiv);
		var h3 = $("<h3>");
		h3.append("<span id='lct'>流程图</span>");
		var ul = $("<ul>");
		ul.append("<li class='img01'>已完成</li>");
		ul.append("<li class='img02'>进行中</li>");
		ul.append("<li><input id='yczt' type='checkbox' name='cczt' />&nbsp;&nbsp;隐藏状态</li>");
		ul.append("<li><input id='runningTrackTable' type='checkbox' name='runningTrackTable' />&nbsp;&nbsp;轨迹信息</li>");
		ul.append("<li><input id='runningTrack' type='checkbox' name='runningTrack' />&nbsp;&nbsp;轨迹动态运行</li>");

		h3.append(ul);
		flowGraphicDiv.append(h3);
		flowGraphicDiv
				.append($("<div id='flowImg' class='pos_abs' style='position: relative; overflow-x: auto; width: 100%;'></div> "));
		//事件注册
		$("#yczt").bind("click", this.flowImgStatusClick);
		$("#runningTrack").bind("click", this.runningTrackClick);
		$("#runningTrackTable").bind("click", function() {
			if ($(this).attr("checked") == 'checked') {
				$("#" + TaskDeatailInfor._config.runningTrackDiv).show();
			} else {
				$("#" + TaskDeatailInfor._config.runningTrackDiv).hide();
			}
		});
	}
};

/**
 * 流程信息加载
 */
function ProcessInfor(config){
	this._config = config;
	
}

/**
 * 设置属性
 */
ProcessInfor.prototype = {
		init : function(){},
		loadStatus: function(){},
		//创建流程信息模板
		createMsgTemplet:function(flag,taskId){
			var html = "<div id=msg_"+flag+" style='display:none' taskId="+taskId+" >";
			html+="<div id='warning' style='text-align:center;'><span id=tips_"+flag+" style='display:none'>数据为空!</span><img style='display:none' src='"+_bpmFilePath+"/images/loading.gif' id=loading_"+flag+" /></div>";
			html+="<div id=data_"+flag+"></div>";
			return html;
		},
		loadProcessSetp : function(nowStep) {
			if (nowStep) {
				/** *****************************展现流程状态*************************** */
				var status = nowStep.status;
				if (status) {
					var processStatus = $("#processStatus");
					var statusEle = $("<a class='label css-truncate-target linked-labelstyle-84b6eb'>");
					statusEle.attr("href", "javascript:void(0);");
					statusEle.attr("title", "状态【" + status + "】");
					statusEle.html("状态【" + status + "】");
					processStatus.append(statusEle);
				}
				/** *********************************end***************************** */
				// 展现当前处理者
				var taskInfo = nowStep.taskInfo;
				if (taskInfo) {
					var sidebarAssignee = $("#sidebarAssignee");
					var spanEle = null;
					var imgEle = null;
					var aEle = null;
					var users = null;
					var type = null;
					var id = null;
					for (var i = 0, j = taskInfo.length; i < j; i++) {
						users =  taskInfo[i].user;
						if(users){
							for(var m = 0,n = users.length;m < n;m++){
								spanEle = $("<span class='css-truncate js-assignee-infobar-item-wrapper'>");
								imgEle = $("<img width='20' height='20' class='avatar'>");
								type = users[i].type;
								id = users[i].id;
								if("user" == type){
									if("foxbpm_all_user" == id){
										imgEle.attr("src","images/default.png");
									}else {
										imgEle.attr("src",_GlobalFlowVar._bpmServiceUrl+ "/identity/users/"+users[m].name+"/picture");
									}
								}else if("role" == type){
									imgEle.attr("src","images/group.png");
								}else if("dept" == type){
									imgEle.attr("src","images/group.png");
								}else {
									//默认
									imgEle.attr("src","images/group.png");
								}
								imgEle.attr("alt",users[m].name);
								aEle = $("<a class='assignee css-truncate-target'>");
								aEle.attr("href","javascript:void(0);");
								aEle.html(users[m].name);
								spanEle.append(imgEle);
								spanEle.append(aEle);
								sidebarAssignee.append(spanEle);
							}
						}
					}
				}
			}
		},
		loadMsg: function(t,i){
			var m = $(t);
			if('false' == m.attr("flag")){
				$("#msg_"+i).show();
				$("#loading_"+i).show();
				var taskId = $("#msg_"+i).attr("taskid");
				$.ajax({
					type : "get",// 使用get方法访问后台
					cache:false,
					dataType : "json",// 返回json格式的数据
					url : _GlobalFlowVar._bpmServiceUrl + "/runtime/tasks/"+taskId+"/operations",// 要访问的后台地址
					success : function(msg) {
						$("#loading_"+i).hide();
						if (!msg && !msg.data) {
							alert("返回数据为null");
							return;
						}
						var data = msg.data;
						if(data.length <= 0){
							$("#tips_"+i).show();
							return;
						}
						$("#tips_"+i).hide();
						var html = "";
						$.each(data,function(j, d) {
							html+="<div class='discussion-item discussion-item-milestoned'>";
							html+="<div class='discussion-item-header' id='event-130077772'>";
							html+="<span class='octicon octicon-milestone discussion-item-icon'></span>";
							html+="<img alt='kenshin' class='avatar' height='16' src='https://avatars3.githubusercontent.com/u/900179?v=2&amp;s=32' width='16'>";
							html+="<a href='javascript:void(0);' class='author'>"+d.operatorName+"</a>&nbsp"+d.commandMessage+"&nbsp<a href='javascript:void(0);' class='timestamp'><time class='timestamp' datetime='2014-06-10T19:38:56-07:00' is='relative-time' title='2014年6月11日 上午10:38 格林尼治标准时间+0800'>&nbsp"+d.operatingTime+"</time></a>";
							html+="</div>";
							html+="</div>";
						});
						//加载数据
						$("#data_"+i).html("");
						html+="<div class='discussion-timeline-actions'></div>";
						$("#data_"+i).append(html);
					},
					error:function(){
						$("#loading_"+i).hide();
						$("#tips_"+i).show();
					},
					beforeSend:function(){
						$("#loading_"+i).show();
						$("#tips_"+i).hide();
					}
				});
				m.attr("flag","true");
				m.html("-");
			}
			else {
				$("#msg_"+i).hide();
				m.attr("flag","false");
				m.html("+");
			}
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
			var img = $("<img id='flowGraphicImg'>");
			img.attr("src", this.action+"?processInstanceId=" + this.processInstanceId+ "&processDefinitionKey=" + this.processDefinitionKey);
			$('#' + this.parentId).append(img);
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
						var runningTrackDiv = $("#"
								+ TaskDeatailInfor._config.runningTrackDiv);
						runningTrackDiv
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
									.append("<th style='width: 200px'>轨迹父令牌编号</th>");
							table_thead
									.append("<th style='width: 200px'>执行时间</th>");
							table_thead
									.append("<th style='width: 130px'>事件名称</th>");
							table_thead
									.append("<th style='width: 60px'>处理者</th>");
							table_thead
									.append("<th style='width: 200px'>流程实例编号</th>");
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
										table_tr.append("<td >"
												+ (!row.eventName ? ''
														: row.eventName)
												+ "</td>");
										table_tr.append("<td>"
												+ (!row.operator ? ''
														: row.operator)
												+ "</td>");
										table_tr.append("<td>"
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
						runningTrackDiv.append(taskNotDoneTb);

					}
				});
	};
	this.runningTrack = function(flag) {
		if (_GlobalFlowVar.isIE) {
			alert("IE浏览器不支持该功能!");
			$("#runningTrack").attr("checked", null);
			return;
		}
		if (flag) {
			if (runningTrackInfor && runningTrackInfor.length != 0) {
				runningTrackIndex = 0;
				// 去掉重复的节点ID
				this.distinctProcessNodeID();
				runningTrackThreadId = window.setInterval('moveRunningTrack()',
						RUNNING_MILLESIMAL_SPEED);
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
	moveRunningTrack = function() {
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
			$("#runningTrack").removeAttr("disabled");
			$("#runningTrack").attr("checked", null);
			if (runningTrackIndex == runningTrackLength) {
				clearInterval(runningTrackThreadId);
			}
		}
		//移除轨迹
		removePreviousRunningTrack(runningTrackIndex);
		runningTrackIndex = runningTrackIndex + 1;
	};
};

