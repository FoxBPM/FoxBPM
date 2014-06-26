<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	pageContext.setAttribute("basePath", basePath);
%>
<base href="<%=basePath%>">
<link rel="shortcut icon" href="common/images/favicon.ico"
	type="image/x-icon">
<title>${applicationScope.appInfo["product.title"]}</title>
<script type="text/javascript" src="common/js/jquery.js"></script>
<script type="text/javascript" src="common/js/common.js"></script>
<script type="text/javascript" src="common/js/select.js"></script>
<script type="text/javascript" src="common/js/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="common/css/reset.css">
<link rel="stylesheet" type="text/css" href="common/css/global.css">
<link rel="stylesheet" type="text/css" href="common/css/index.css">
<link id="color" rel="stylesheet" type="text/css" href="common/css/color_blue.css">
<script type="text/javascript">
	var message = "${errorMsg}";
	if (message != '') {
		alert(message);
	}
</script>