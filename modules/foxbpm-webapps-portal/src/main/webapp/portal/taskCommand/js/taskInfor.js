/**
 * 任务详细信息
 */
function TaskInfor(config) {
	this.eleId = config.eleId;
	this.action = config.action;
	this.processInstanceId = config.processInstanceId;
	this.flowInforObj = config.flowInforObj;
	this.flowGraphicInforObj = config.flowGraphicInforObj;
};
TaskInfor.prototype = {
	init : function() {
		var $this = this;
		if (this.processInstanceId && "" != this.processInstanceId) {
			// 设置定义名称
			$
					.ajax({
						type : "get",// 使用get方法访问后台
						dataType : "json",// 返回json格式的数据
						url : $this.action + "/runtime/tasks",// 要访问的后台地址
						data : {
							processInstanceId : $this.processInstanceId,
							orderby : 'endTime',
							sort : 'asc',
						},
						success : function(msg) {// msg为返回的数据，在这里做数据绑定
							if (!msg && !msg.data) {
								alert("返回数据为null");
								return;
							}
							var data = msg.data;
							var taskDetailInforDiv = $("#" + $this.eleId);
							var record = null;
							var a = null;
							var img = null;
							var loadMsg = null;
							var comment = null;
							var comment_header = null;
							var comment_action = null;
							var comment_header_text = null;
							var comment_content = null;
							var edit_comment_hide = null;
							var recordsDiv = null;
							var isEnd = false;
							var isOpen = false;
							var participants = [];
							var taskListEnd = [];
							var taskListIng = [];
							var processDefinitionId = null;
							var flag = false;
							$.each(
											data,
											function(i, d) {
												if (null != d.endTime) {
													taskListEnd.push(d);
													if(!isOpen){
														taskDetailInforDiv.append("<div style='border-bottom-style:solid;border-bottom-width:1px;border-bottom-color:#ddd'><span style='padding-right:4px;padding-bottom:3px;padding-left:4px;font-size:11px;font-weight:bold;display:inline-block;line-height:1;padding-top:3px;color:rgb(255,255,255);padding-top:0.2em;padding-left:0.6em;padding-right:0.6em;padding-bottom:0.3em;font-size:75%;display:inline;background-color: rgb(153, 153, 153);'>已完成</span></div>");
														recordsDiv = $("<div class='js-discussion js-socket-channel'>");
														isOpen = true;
													}
												} else{
													taskListIng.push(d);
													 if (!isEnd) {
														 taskDetailInforDiv.append("<div style='border-bottom-style:solid;border-bottom-width:1px;border-bottom-color:#ddd'><span style='padding-right:4px;padding-bottom:3px;padding-left:4px;font-size:11px;font-weight:bold;display:inline-block;line-height:1;padding-top:3px;color:rgb(255,255,255);padding-top:0.2em;padding-left:0.6em;padding-right:0.6em;padding-bottom:0.3em;font-size:75%;display:inline;background-color: rgb(153, 153, 153);'>未完成</span></div>");
														 isEnd = true;
														 recordsDiv = $("<div class='js-discussion js-socket-channel'>");
													 }
												}
												record = $("<div class='timeline-comment-wrapper js-comment-container'>");
												a = $("<a href='javascript:void(0);'>");
												img = $("<img width='48' height='48' class='timeline-comment-avatar'>");
												img.attr("alt", data.userName);
												img.attr("src", $this.action
														+ "/identity/users/"
														+ data.userName
														+ "/picture");
												a.append(img);
												comment = $("<div class='comment timeline-comment js-comment js-task-list-container  is-task-list-enabled'>");
												comment_header = $("<div class='timeline-comment-header'>");
												comment_action = $("<div class='timeline-comment-actions'>");
												comment_header_text = $("<div class='timeline-comment-header-text'>");
												comment_header_text
														.append("<strong> <a class='author' href='javascript:void(0);'>"
																+ d.assigneeName
																+ "</a>");
												comment_header_text
														.append("<strong>&nbsp"
																+ d.nodeName
																+ "&nbsp"
																+ (!d.commandMessage ? ''
																		: d.commandMessage)
																+ "&nbsp"
																+ (!d.endTime ? ''
																		: d.endTime));
												comment_content = $("<div class='comment-content'>");
												edit_comment_hide = $("<div class='edit-comment-hide'>");
												edit_comment_hide
														.append("<div class='comment-body markdown-body markdown-format js-comment-body'><p>"
																+ (!d.taskComment ? ''
																		: d.taskComment)
																+ "</p></div>");
												comment_header
														.append(comment_action);
												comment_header
														.append(comment_header_text);
												comment_content
														.append(edit_comment_hide);
												comment.append(comment_header);
												comment.append(comment_content);
												record.append(a);
												record.append(comment);
												if (null != d.endTime) {
													loadMsg = $("<a style='text-decoration:none;cursor:pointer;float: right;'>");
													loadMsg
															.attr("href",
																	'javascript:void(0);');
													loadMsg.attr("flag",
															'false');
													loadMsg.html("+");
													loadMsg
															.on(
																	"click",
																	{
																		obj : loadMsg,
																		flag : i
																	},
																	function(
																			event) {
																		$this
																				.loadMsg(
																						event.data.obj,
																						event.data.flag);
																	});
													//record.append(loadMsg);
												}
												recordsDiv.append(record);
												/** ********************处理信息**************************** */
												if (null != d.endTime) {
													flag = false;
													$.each(participants,function(j,data){
														if(data.id == d.assignee){
															flag = true;
															return;
														}
													});
													if(!flag){
														participants.push({id:d.assignee,name:d.assigneeName});
													}
													recordsDiv.append($this.createMsgTemplet(i, d.id));
												}
												if(null == processDefinitionId){
													processDefinitionId = d.processDefinitionId;
												}
												/** ********************处理信息**************************** */
												taskDetailInforDiv.append(recordsDiv);
											});
							//初始化流程参入者信息
							if($this.flowInforObj){
								$this.flowInforObj.loadProcessParticipant(participants);
							}
							if($this.flowGraphicInforObj){
								$.ajax({
									type : "GET",
									url : $this.action + '/flowGraphic/position',
									dataType : "json",// 返回json格式的数据
									data : "processDefinitionId=" + processDefinitionId,
									success : function(data) {
										if(data){
											var nodeInforArr = data.positionInfor;
											$this.flowGraphicInforObj.initTaskInfor({taskListEnd:taskListEnd,taskListIng:taskListIng,nodeInforArr:nodeInforArr});
										}
									}
								});
							}
						}
					});
		} else {
			$("#processInfo").hide();
		}
	},
	// 创建流程信息模板
	createMsgTemplet : function(flag, taskId) {
		var html = "<div id=msg_" + flag + " style='display:none' taskId="
				+ taskId + " >";
		html += "<div id='warning' style='text-align:center;'><span id=tips_"
				+ flag
				+ " style='display:none'>数据为空!</span><img style='display:none' src='images/loading.gif' id=loading_"
				+ flag + " /></div>";
		html += "<div id=data_" + flag + "></div>";
		return html;
	},
	loadMsg : function(t, i) {
		var m = $(t);
		if ('false' == m.attr("flag")) {
			$("#msg_" + i).show();
			$("#loading_" + i).show();
			var taskId = $("#msg_" + i).attr("taskid");
			$
					.ajax({
						type : "get",// 使用get方法访问后台
						cache : false,
						dataType : "json",// 返回json格式的数据
						url : this.action + "/runtime/tasks/" + taskId
								+ "/operations",// 要访问的后台地址
						success : function(msg) {
							$("#loading_" + i).hide();
							if (!msg && !msg.data) {
								alert("返回数据为null");
								return;
							}
							var data = msg.data;
							if (data.length <= 0) {
								$("#tips_" + i).show();
								return;
							}
							$("#tips_" + i).hide();
							var html = "";
							$
									.each(
											data,
											function(j, d) {
												html += "<div class='discussion-item discussion-item-milestoned'>";
												html += "<div class='discussion-item-header' id='event-130077772'>";
												html += "<span class='octicon octicon-milestone discussion-item-icon'></span>";
												html += "<img alt='kenshin' class='avatar' height='16' src='https://avatars3.githubusercontent.com/u/900179?v=2&amp;s=32' width='16'>";
												html += "<a href='javascript:void(0);' class='author'>"
														+ d.operatorName
														+ "</a>&nbsp"
														+ d.commandMessage
														+ "&nbsp<a href='javascript:void(0);' class='timestamp'><time class='timestamp' datetime='2014-06-10T19:38:56-07:00' is='relative-time' title='2014年6月11日 上午10:38 格林尼治标准时间+0800'>&nbsp"
														+ d.operatingTime
														+ "</time></a>";
												html += "</div>";
												html += "</div>";
											});
							//加载数据
							$("#data_" + i).html("");
							html += "<div class='discussion-timeline-actions'></div>";
							$("#data_" + i).append(html);
						},
						error : function() {
							$("#loading_" + i).hide();
							$("#tips_" + i).show();
						},
						beforeSend : function() {
							$("#loading_" + i).show();
							$("#tips_" + i).hide();
						}
					});
			m.attr("flag", "true");
			m.html("-");
		} else {
			$("#msg_" + i).hide();
			m.attr("flag", "false");
			m.html("+");
		}
	}
};
