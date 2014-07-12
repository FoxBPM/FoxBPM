<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程状态</title>
<link rel="stylesheet" type="text/css" href="common/css/reset.css">
<link rel="stylesheet" type="text/css" href="common/css/form.css" />
<link rel="stylesheet" type="text/css" href="common/css/flowGraph.css" />

<script type="text/javascript" src="common/js/jquery.js"></script>
<script type="text/javascript" src="common/js/flowGraphic.js"></script>

<script type="text/javascript">
	
</script>
</head>
<body>
	<div style="padding: 10px; height: 95%; background: #fff;">
		<div class="process">
			<h1 id="processDefinitionName">${result.processName}</h1>
			<div class="proc_bg">
				<h3>
					<span id="clz">流程信息</span>
				</h3>
				<div id="taskNotDoneTb">
					<table width="100%" class="table-list">

						<c:if test="${result.dataList!=null}">
							<thead>
								<th style="width: 160px">步骤名称</th>
								<th style="width: 160px">到达时间</th>
								<th style="width: 160px">完成时间</th>
								<th style="width: 160px">处理者</th>
								<th style="width: 80px">处理结果</th>
								<th>处理意见</th>
							</thead>
							<c:forEach items="${result.dataList}" var="row"
								varStatus="status">
								<tr <c:if test="${status.index%2!=0}">class="gray"</c:if>>
									<td>${row.nodeName}</td>
									<td><fmt:formatDate value="${row.createTime}" type="both" /></td>
									<td><fmt:formatDate value="${row.endTime}" type="both" /></td>
									<td>${row.assgneeUserName}</td>
									<td class="left">${row.commandMessage}</td>
									<td class="left">${row.taskComment}</td>
								</tr>
							</c:forEach>
							<c:forEach items="${result.notEnddataList}" var="row"
								varStatus="status">
								<tr <c:if test="${status.index%2!=0}">class="gray"</c:if>>
									<td>${row.nodeName}</td>
									<td><fmt:formatDate value="${row.createTime}" type="both" /></td>
									<td><fmt:formatDate value="${row.endTime}" type="both" /></td>
									<td>${row.assgneeUserName}</td>
									<td class="left">${row.commandMessage}</td>
									<td class="left">${row.taskComment}</td>
								</tr>
							</c:forEach>
						</c:if>
					</table>
				</div>
				<!---表格 START--->
			</div>
			<div class="proc_bg">
				<h3>
					<span id="lct">流程图</span>
					<ul>
						<li class="img01">已完成</li>
						<li class="img02">进行中</li>
						<li><input id="yczt" type="checkbox" name="cczt" />&nbsp;&nbsp;隐藏状态</li>
						<li><input id="runningTrack" type="checkbox"
							name="runningTrack" />&nbsp;&nbsp;运行轨迹</li>
					</ul>
				</h3>
				<!---流程图 START--->
				<div id="flowImg" class="pos_abs"
					style='position: relative; overflow-x: auto; width: 100%;'></div>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">
	var runningTrackInfo = $.parseJSON('${result.runningTrackInfo}');//运行轨迹
	var runningTrackIndex = 0;

	var runningTrackLength;
	if (runningTrackInfo) {
		runningTrackLength = runningTrackInfo.length;
	}
	var currentRunningTrack;
	var runningTrackThreadId;
	//判断是否为IE浏览器标示
	var isIE = window.ActiveXObject && $.browser.msie;
	//页面初始化后需要展现流程图
	var taskListEnd = $.parseJSON('${result.taskEndedJson}');//存放已经结束的节点
	var taskListIng = $.parseJSON('${result.taskNotEndJson}');//存放正在处理的节点
	var nodeInfoArr = $.parseJSON('${result.positionInfo}');//存放流程节点信息

	/**
	 * 保存流程节点本身式样
	 */
	var backUpColorDictionary = {};
	var backUpWidthDictionary = {};

	var backUpRunningTrackColorDictionary = {};
	var backUpRunningTrackWidthDictionary = {};

	//创建任务详细对象
	var flowGraphic = FlowGraphic({
		'taskListEnd' : taskListEnd,
		'taskListIng' : taskListIng,
		'nodeInfoArr' : nodeInfoArr,
		'isIE' : isIE,
		'parentId' : 'flowImg',
		'action' : 'getFlowGraph.action',
		'processDefinitionId' : '${result.processDefinitionId}'
	});
	var tempNodeID;
	function moveRunningTrack() {
		if (runningTrackLength != 0 && runningTrackIndex < runningTrackLength) {
			currentRunningTrack = runningTrackInfo[runningTrackIndex];
			removePreviousRunningTrack(currentRunningTrack);
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
	}
	//如果前一个节点只存在单个执行事件，用这个方法清空前一个节点的轨迹
	function removePreviousRunningTrack(currentTrack) {
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
	}
	function clearRunningTracks() {
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
	}

	$(function() {
		//判断浏览器类型为IE
		flowGraphic.loadFlowImg();
		$("#yczt")
				.bind(
						"click",
						function() {
							flowGraphic.hideFlowImgStatus(($(this).attr(
									"checked") == 'checked'));
						});
		$("#runningTrack").bind(
				"click",
				function() {
					//flowGraphic.runningTrack(($(this).attr("checked") == 'checked'));
					if ($(this).attr("checked") == 'checked') {
						if (runningTrackInfo && runningTrackInfo.length != 0) {
							runningTrackIndex = 0;
							runningTrackThreadId = setInterval(
									"moveRunningTrack()",
									RUNNING_MILLESIMAL_SPEED);
							$(this).attr("disabled", "disabled");
						} else {
							alert("无流程运行轨迹 数据");
							$(this).attr("disabled", "disabled");
						}

					}
					//暂停
					if ($(this).attr("checked") != 'checked') {

					}
					//如果轨迹是正在运行的则不让它运行
					if ($(this).attr("checked") != 'checked'
							&& runningTrackIndex != 0
							&& runningTrackIndex != runningTrackLength - 1) {
						//runningTrackIndex = 0;
					}

				});
	});
</script>
</html>