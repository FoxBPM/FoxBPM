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

<style>
</style>
</head>

<body>
	<div class="tpl-form-border">
		<form id="form1" action="completeTask.action" method="post">
			<input type="hidden" name="taskStauts" id="taskStauts" value="0" />
			<table class="table-form">
				<tr>
					<td class="title-r">流程关联键：</td>
					<td><input type="text" name="businessKey" id="businessKey"
						value="${result.demoObject.bKey}" /></td>
				</tr>
				<tr>
					<td class="title-r">信息：</td>
					<td><input type="text" name="infor"
						value="${result.demoObject.infor}" /></td>
				</tr>
				<tr>
					<td class="title-r">审批意见：</td>
					<td><textarea rows="3" cols="20" name="_taskComment"
							id="_taskComment"></textarea></td>
				</tr>
			</table>
			<input type="hidden" name="taskStauts" id="taskStauts" value="0" />
			<input type='hidden' name='flowInfo' id='flowInfo' />
			<div class="toolbar" id="toolbar"></div>
		</form>
	</div>
</body>
</html>
