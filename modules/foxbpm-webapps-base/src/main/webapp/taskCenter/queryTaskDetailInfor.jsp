<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程状态</title>
<jsp:include page="formHead.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="common/css/form.css"></link>
<link rel="stylesheet" type="text/css" href="common/css/flowGraph.css"></link>

<script type="text/javascript">
	var taskListEnd = [];//存放已经结束的节点
	var taskListIng = [];//存放正在处理的节点
	var noPostion = 0;
	function bodyOnload() {
		var flag = "${param.noGraphic}";
		if (flag != "1") {
			//初始化流程信息
			initFlowInfo();
			//添加图信息
			addGraphicInfo();
			//标记流程图
			markImags();
		}
	}

	//初始化已完成和未完成的节点信息。
	function initFlowInfo() {
		var taskEnded = '${result.taskEndedJson}';
		var taskIng = '${result.taskNotEndJson}';
		taskListEnd = $.parseJSON(taskEnded);
		taskListIng = $.parseJSON(taskIng);
	}
	//给流程图加坐标
	function addGraphicInfo(key, instid) {
		var divcontent = "";
		var nodeInfoArr = $.parseJSON('${result.positionInfo}');
		for ( var nodeInfo in nodeInfoArr) {
			var nodeInfoObj = nodeInfoArr[nodeInfo];
			divcontent = divcontent
					+ getDiv(nodeInfo, nodeInfoObj.x - 4, nodeInfoObj.y - 4,
							nodeInfoObj.width + 4, nodeInfoObj.height + 4);
		}
		$("#flowImg").append(divcontent);
	}

	//获取图标的div
	function getDiv(nodeId, x, y, w, h) {
		return "<DIV id='"+nodeId+"' class='nodeclass' style='display:none;position:  absolute; left: "+x+"px; top: "+y+"px;WIDTH:"+w+"px;HEIGHT:"+h+"px'  ></DIV>";
	}

	//标记节点
	function markImags() {
		$.each(taskListEnd, function(i, task) {
			markImg(task.nodeId, "green", 2, 0);
		});

		$.each(taskListIng, function(i, task) {
			if (task.taskType == "callactivitytask") {
				//如果是正在运行的，则将z-idnex设为最大，因为子流程如果折叠起来，会有重叠的DIV
				markImg(task.nodeId, "#ff6000", 4, 999);
			} else {
				markImg(task.nodeId, "#ff6000", 2, 999);
			}
		});
	}
	//标记单个节点
	function markImg(svgNodeId, color, width, zIndex) {
		var svgElement = $("#" + svgNodeId);
		if (svgElement) {
			svgElement.css('display', 'block');
			svgElement.css('border', '2px solid ' + color);
			svgElement.css('z-index', zIndex);
		}
	}
	function viewPostion() {
		if (noPostion == 0) {
			$(".nodeclass").css('display', 'none');
			noPostion = 1;
		} else {
			markImags();
			noPostion = 0;
		}
	}
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
									<%-- 							<td>${row.expectedExecutionTime}</td> --%>
									<td class="left">${row.commandMessage}</td>
									<td class="left">${row.taskComment}</td>
									<!-- 							<td class="left">已完成</td> -->
								</tr>
							</c:forEach>
							<c:forEach items="${result.notEnddataList}" var="row"
								varStatus="status">
								<tr <c:if test="${status.index%2!=0}">class="gray"</c:if>>
									<td>${row.nodeName}</td>
									<td><fmt:formatDate value="${row.createTime}" type="both" /></td>
									<td><fmt:formatDate value="${row.endTime}" type="both" /></td>
									<td>${row.assgneeUserName}</td>
									<%-- 							<td>${row.expectedExecutionTime}</td> --%>
									<td class="left">${row.commandMessage}</td>
									<td class="left">${row.taskComment}</td>
									<!-- 							<td class="left">进行中</td> -->
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
						<li><input id="yczt" type="checkbox" name="cczt"
							onclick="viewPostion()" />&nbsp;&nbsp;隐藏状态</li>
					</ul>
				</h3>
				<!---流程图 START--->
				<div id="flowImg" class="pos_abs" style='position: relative;'>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	//页面初始化后需要展现流程图
	$(function() {
		var noGraphic = (1 != '${param.noGraphic}');
		if (true == noGraphic) {
			//判断浏览器类型为IE
			if (window.ActiveXObject && $.browser.msie) {
				$("#flowImg")
						.append(
								"<img src='getFlowGraph.action?processDefinitionId=${result.processDefinitionId}' />");
				bodyOnload();
			} 
			else 
			{
				//加载svg图片
				$.ajax({
					type : "POST",
					url : "getFlowGraph.action",
					data : "flag=svg&processDefinitionId="
							+ '${result.processDefinitionId}',
					success : function(src) {
						$("#flowImg").append(src);
					}
				});
			}
		}
	});
</script>
</html>