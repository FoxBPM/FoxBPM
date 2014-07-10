<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="common/css/form.css">
<link rel="stylesheet" type="text/css" href="common/css/reset.css">
<link rel="shortcut icon" href="common/images/favicon.ico" type="image/x-icon"/>
<script type="text/javascript" src="common/js/jquery.js"></script>
<script type="text/javascript" src="common/js/My97DatePicker/WdatePicker.js"></script>
<title>${applicationScope.appInfo["product.title"]}</title>
<%
	String deploymentId = request.getParameter("deploymentId");
	if (deploymentId == null) {
		deploymentId = "";
	}
%>

<script type="text/javascript">
	function sub() {
		var fileName = $("#processFile").val();
		var d = /\.[^\.]+$/.exec(fileName);
		if (d == ".zip" || d == ".ZIP") {
			$("#subForm").submit();
		} else {
			alert("请选择正确的文件类型");
			return false;
		}
	}

	function bodyOnLoad() {
		var deployId = $("#deploymentId").val();
		if (deployId == "") {
			$("#trDeploy").css("display", "none");
		}
	}
</script>
</head>
<body onload="bodyOnLoad();">
	<form action="deploy.action" method="post" id="subForm"
		ENCTYPE="multipart/form-data">
		<input type="hidden" name="action" value="deploy">
		<div class="tpl-form-border">
			<table class="table-form">
				<tr>
					<td class="title-r" style="width: 300px"><p>请选择文件：</p>(<span
						style="color: red">注：只能选择设计器或者web下载的zip格式文件</span>)：</td>
					<td><input type="file" name="processFile" id="processFile" /></td>
				</tr>
				<tr id="trDeploy">
					<td class="title-r">发布号：</td>
					<td><input type="text" name="deploymentId" id="deploymentId"
						readOnly value="<%=deploymentId%>"></td>
				</tr>
			</table>
			<div class="toolbar">
				<div class="btn-normal" onclick="sub();">
					<a href="javascript:void(0)">发布</a>
				</div>
			</div>
		</div>
	</form>
</body>
</html>