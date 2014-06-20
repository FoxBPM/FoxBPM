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
	//判断是否为IE浏览器标示
	var isIE = window.ActiveXObject && $.browser.msie;
	//页面初始化后需要展现流程图
	var taskListEnd = $.parseJSON('${result.taskEndedJson}');//存放已经结束的节点
	var taskListIng = $.parseJSON('${result.taskNotEndJson}');//存放正在处理的节点
	var nodeInfoArr = $.parseJSON('${result.positionInfo}');//存放流程节点信息
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
	});
</script>
</html>