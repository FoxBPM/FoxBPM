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
<script type="text/javascript" src="foxbpm/js/jquery.js"></script>
<script type="text/javascript" src="common/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="foxbpm/js/foxbpmframework.js"></script>
<script type="text/javascript" src="foxbpm/js/flowCommandCompenent.js"></script>
<script type="text/javascript" src="foxbpm/js/flowCommandHandler.js"></script>
<script type="text/javascript" >
$(document).ready(function() {
	var _getBizKey = function() {
        return $("#businessKey").val();
    };
    var _getTaskComment = function() {
       	return $("#_taskComment").val();
    };
    
    var _flowCommit = function(flowInfo){
    	if(confirm("确定要提交吗?")){
    		$("#flowInfo").val(JSON.stringify(flowInfo));
    		$("#form1").submit();
    	}
    	return false;
    };
	var flowconfig ={ getBizKey: _getBizKey, getTaskComment: _getTaskComment,flowCommit:_flowCommit };
	
	var flowCommandCompenent = new Foxbpm.FlowCommandCompenent(flowconfig);
	flowCommandCompenent.init();
});
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
			<input type="hidden" name="taskStauts" id="taskStauts" value="0" />
			<input type='hidden' name='flowInfo' id='flowInfo' />
		</form>
	</div>
</body>
</html>