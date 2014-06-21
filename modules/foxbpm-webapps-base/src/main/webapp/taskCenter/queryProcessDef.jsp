<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../common/head.jsp" flush="true" />
<style>
a {
	text-decoration: none;
}
</style>
</head>
<body>
	<div class="main-panel">
		<jsp:include page="top.jsp" flush="true" />
		<div class="center-panel">
			<div class="type-box">
				<div class="type">
					<h1>流程定义</h1>
					<c:forEach items="${result}" var="row" varStatus="status">
						<a href="javascript:void(0)" formUrl="${row.formUrl}"
							processDefinitionKey="${row.key}" processDefinitionId="${row.id}"><div>${row.name}</div></a>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	//这里启动表单操作
	$(function() {
		$("a[processDefinitionKey]").click(
				function() {
					var pdk = $(this).attr("processDefinitionKey");
					var pdi = $(this).attr("processDefinitionId");
					var userId =<%=request.getAttribute("userId")%>;

					var formUrl = $(this).attr("formUrl");
					var url = "startTask.action";
					if (formUrl.indexOf("?") != -1) {
						url += "&";
					} else {
						url += "?";
					}
					url += "userId=" + userId + "&processDefinitionKey=" + pdk
							+ "&processDefinitionId=" + pdi;
					window.open(url);
				});
	});
</script>
</html>