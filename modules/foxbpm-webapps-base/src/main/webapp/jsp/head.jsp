<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	pageContext.setAttribute("basePath",basePath); 
%>
<base href="<%=basePath%>">
<title>${applicationScope.appInfo["product.title"]}</title>
<script type="text/javascript" src="foxbpm/js/jquery.js"></script>
<script type="text/javascript"src="foxbpm/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="foxbpm/js/common.js"></script>
<script type="text/javascript" src="foxbpm/js/select.js"></script>
<link rel="shortcut icon" href="foxbpm/images/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="foxbpm/css/reset.css" />
<link rel="stylesheet" type="text/css" href="foxbpm/css/global.css" />
<link rel="stylesheet" type="text/css" href="foxbpm/css/index.css" />
<link id="color" rel="stylesheet" type="text/css" href="/foxbpm/css/color_red.css" />
<!-- <link rel="stylesheet" type="text/css" href="foxbpm/css/popup.css"> -->
<script type="text/javascript">
	var message = "${errorMsg}";
	if (message != '') {
		alert(message);
	}
</script>
