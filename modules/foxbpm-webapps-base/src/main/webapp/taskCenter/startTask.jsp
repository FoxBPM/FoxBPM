<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>启动任务</title>
<link rel="stylesheet" type="text/css" href="common/css/form.css">
<link rel="stylesheet" type="text/css" href="common/css/reset.css">
<script type="text/javascript" src="common/js/jquery.js"></script>
<script type="text/javascript" src="common/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="common/js/flowcommand.js"></script>
<script type="text/javascript" src="common/js/flowautoassemble.js"></script>
<script type="text/javascript">
	var message = '${errorMsg}';
	if (message != '') {
		alert(message);
	}
</script>

<style>
</style>
</head>

<body>
	<div class="tpl-form-border">
		<form id="form1" action="executeTask.action" method="post">
			<table class="table-form">
				<tr>
					<td class="title-r">流程关联键：</td>
					<td><input type="text" name="businessKey"
						value="${result.demoObject.pak}" /></td>
				</tr>
				<tr>
					<td class="title-r">信息：</td>
					<td><input type="text" name="infor"
						value="${result.demoObject.infor}" /></td>
				</tr>
				<tr>
					<td class="title-r">审批意见：</td>
					<td><textarea rows="3" cols="20" name="_taskComment"></textarea></td>
				</tr>
			</table>
			<div class="toolbar">
				<c:forEach items="${result.commandList}" var="row"
					varStatus="status">
					<div class="btn-normal" id="btn_${status.index+1}"
						commandId="${row.id}" commandName="${row.name}"
						commandType="${row.type}" isAdmin="${row.isAdmin}"
						isVerification="${row.isVerification}"
						isSaveData="${row.isSaveData}"
						isSimulationRun="${row.isSimulationRun}" nodeId="${row.nodeId}"
						nodeName="${row.nodeName}">
						<a>${row.name}</a>
					</div>
				</c:forEach>
			</div>
			<!-- 首次启动 -->
			<input type="hidden" name="taskStauts" id="taskStauts" value="0" />
		</form>
	</div>
</body>
</html>
