<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	pageContext.setAttribute("basePath", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>审批</title>
<link rel="stylesheet" type="text/css" href="common/css/reset.css">
<link rel="stylesheet" type="text/css" href="common/css/form.css">
<script type="text/javascript" src="common/js/jquery.js"></script>
<script type="text/javascript" src="common/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="common/js/flowCommandBase.js"></script>
<script type="text/javascript" src="common/js/compenents/flowCommandHandler.js"></script>

<script type="text/javascript">
	var message = '${errorMsg}';
	if (message != '') {
		alert(message);
	}
	
	function flowCommit(){
		alert($("#flowInfo").val());
		var form = $("#form1");
		form.submit();
	}
	
	function getBizKey(){
		var bizKey = $("#businessKey").val();
		return bizKey;
	}
	
	function getTaskComment(){
		return $("#_taskComment").val();
	}
</script>

</head>

<body>
	<div class="tpl-form-border">
		<form id="form1" action="completeTask.action" method="post">
		<input id="businessKey" name="businessKey" type="hidden" value="${result.demoObject.bKey}" />
			<table class="table-form">
				<tr>
					<td class="title-r">流程关联键：</td>
					<td>${result.demoObject.bKey}</td>
				</tr>
				<tr>
					<td class="title-r">信息：</td>
					<td>${result.demoObject.infor}</td>
				</tr>
				<tr>
					<td class="title-r">审批意见：</td>
					<td><textarea rows="3" cols="20" name="_taskComment"></textarea></td>
				</tr>
			</table>
			<div class="toolbar" id="toolbar"></div>
		</form>
	</div>
</body>
</html>