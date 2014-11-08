/**
 * 流程信息加载
 */
function FlowInfor(config) {
	this.eleId = config.eleId;
	this.action = config.action;
	this.processInstanceId = config.processInstanceId;
	var $this = this;
	this.loadData = function() {
		var $this = this;
		$.ajax({
			type : "get",// 使用get方法访问后台
			dataType : "json",// 返回json格式的数据
			url : $this.action + "/runtime/process-instances/"
					+ $this.processInstanceId,// 要访问的后台地址
			success : function(msg) {// msg为返回的数据，在这里做数据绑定
				if (!msg) {
					alert("返回数据为null");
					return;
				}
				loadStatus(msg.instanceStatus);
				loadProcessSetp(msg.nowStep);
			}
		});
	};
	function loadStatus(instanceStatus) {
		var statusEle = $("<a id='processStatus_a' class='label css-truncate-target' style='color: rgb(28, 39, 51) !important;background-color: rgb(132, 182, 235) !important;'>");
		statusEle.attr("href", "javascript:void(0);");

		if ("running" == instanceStatus) {
			instanceStatus = "运行中";
		} else if ("suspend" == instanceStatus) {
			instanceStatus = "暂停";
		} else if ("abort" == instanceStatus) {
			instanceStatus = "终止";
		} else if ("complete" == instanceStatus) {
			instanceStatus = "正常结束";
			statusEle
					.attr(
							"style",
							"color: rgb(28, 39, 51) !important;background-color: rgb(153, 255 ,102) !important;");
		} else {
			instanceStatus = "未知状态";
			statusEle
					.attr(
							"style",
							"color: rgb(28, 39, 51) !important;background-color: rgb(204 ,0 ,51) !important;");
		}

		statusEle.attr("title", "状态【" + instanceStatus + "】");
		statusEle.html("状态【" + instanceStatus + "】");
		$("#processStatus").append(statusEle);
	}
	;
	function loadProcessSetp(nowStep) {
		if (nowStep) {
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
					users = taskInfo[i].user;
					if (users) {
						for (var m = 0, n = users.length; m < n; m++) {
							spanEle = $("<span class='css-truncate js-assignee-infobar-item-wrapper'>");
							imgEle = $("<img width='20' height='20' class='avatar'>");
							type = users[i].type;
							id = users[i].id;
							if ("user" == type) {
								if ("foxbpm_all_user" == id) {
									imgEle.attr("src", "images/default.png");
								} else {
									imgEle.attr("src", $this.action
											+ "/identity/users/" + users[m].name
											+ "/picture");
								}
							} else if ("role" == type) {
								imgEle.attr("src", "images/group.png");
							} else if ("dept" == type) {
								imgEle.attr("src", "images/group.png");
							} else {
								// 默认
								imgEle.attr("src", "images/group.png");
							}
							imgEle.attr("alt", users[m].name);
							aEle = $("<a class='assignee css-truncate-target'>");
							aEle.attr("href", "javascript:void(0);");
							aEle.html(users[m].name);
							spanEle.append(imgEle);
							spanEle.append(aEle);
							sidebarAssignee.append(spanEle);
						}
					}
				}
			}
		}
	}
	;
}

FlowInfor.prototype.init = function(nowStep) {
	var html = "<div class='discussion-sidebar-item sidebar-labels'>";
	html += "<div class='select-menu label-select-menu js-issue-show-label-select-menu js-menu-container js-select-menu'>";
	html += "<button class='discussion-sidebar-heading discussion-sidebar-toggle js-menu-target'>流程状态</button>";
	html += "</div>";
	html += "<div class='labels css-truncate js-timeline-label-list' id='processStatus'></div>";
	html += "</div>";
	// 流程当前处理者
	html += "<div class='discussion-sidebar-item sidebar-assignee' id='sidebarAssignee'>";
	html += "<div class='select-menu js-menu-container js-select-menu js-assignee-picker is-showing-clear-item'>";
	html += "<button class='discussion-sidebar-heading discussion-sidebar-toggle js-menu-target'>";
	html += "<span class='octicon octicon-gear'></span>当前处理者</button>";
	html += "</div>";
	html += "</div>";
	// 暂时保留
	html += "<div class='discussion-sidebar-item sidebar-notifications'>";
	html += "<h3 class='discussion-sidebar-heading'>Notifications</h3>";
	html += "<div class='thread-subscription-status js-thread-subscription-status js-socket-channel js-updatable-content'>";
	// html += "<span class='mega-octicon octicon-radio-tower'></span>";
	// html += "<p class='reason'>You're receiving notifications because you're
	// subscribed to this repository.</p>";
	html += "</div>";
	html += "</div>";
	// 流程参入者
	html += "<div class='discussion-sidebar-item js-socket-channel js-updatable-content'>";
	html += "<div class='participation' id='partial-users-participants'>";
	html += "</div>";
	html += "</div>";
	// 用户锁定
	// html += "<div class='discussion-sidebar-item lock-toggle'>";
	// html += "<a class='lock-toggle-link' href='javascript:void(0);'><span
	// class='octicon octicon-lock'></span>Lock issue</a>";
	// html += "</div>";

	$("#" + this.eleId).append(html);
	// 加载数据
	this.loadData();
};
FlowInfor.prototype.loadProcessParticipant = function(participants) {
	var len = participants.length;
	if (len > 0) {
		var participationDiv = $("#partial-users-participants");
		participationDiv.append("<h3 class='discussion-sidebar-heading'>" + len
				+ " 流程参与者</h3>");
		var participationAvatarsDiv = $("<div class='participation-avatars'>");
		var a = null;
		var a_Img = null;
		for (var i = 0; i < len; i++) {
			a = $("<a class='participant-avatar tooltipped tooltipped-s'>");
			a.attr("title", participants[i].name);
			a.attr("href", "javascript:void(0);");
			a_Img = $("<img width='20' height='20' class='avatar'>");
			a_Img.attr("alt", participants[i].name);
			a_Img.attr("src", this.action + "/identity/users/"
					+ participants[i].id + "/picture");
			a.append(a_Img);
			participationAvatarsDiv.append(a);
		}
		participationDiv.append(participationAvatarsDiv);
	}
};