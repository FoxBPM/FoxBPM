<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%    
String path = request.getContextPath();    
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";    
pageContext.setAttribute("basePath",basePath); 
%>
<base href="<%=basePath%>">
<script type="text/javascript" src="foxbpm/js/jquery.js"></script>
<script type="text/javascript" src="foxbpm/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="foxbpm/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="foxbpm/css/reset.css">
<link rel="stylesheet" type="text/css" href="foxbpm/css/global.css">
<link rel="stylesheet" type="text/css" href="foxbpm/css/popup.css">
<script type="text/javascript">
var message = '${errorMsg}';
if(message!=''){
	alert(message);
}
</script>
