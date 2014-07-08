<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="common/css/reset.css">
<link rel="stylesheet" type="text/css" href="common/css/global.css">
<link rel="stylesheet" type="text/css" href="common/css/login.css">
<script type="text/javascript" src="common/js/jquery.js"></script>
<script type="text/javascript" src="common/js/common.js"></script>
<title>${applicationScope.appInfo["product.title"]}</title>
</head>

<body>
	<div class="main-panel">
		<div class="center-panel">
			<div class="login">
				<div class="logo">&nbsp;</div>
				<form id="loginF" method=post action="login.action">
					<table id="loginForm">
						<tbody>
							<tr>
								<td><span class="label-orange">用户名：</span></td>
								<td><input type="text" id="userName" name="userName"
									class="inputset" /></td>
							</tr>
							<tr>
								<td><span class="label-orange">密 码：</span></td>
								<td><input type="password" id="password" name="password"
									class="inputset" /></td>
							</tr>
							<tr>
								<td class="change"><input type="checkbox" id="loginType"
									name="loginType" /><label for="loginType">管控中心登陆 </label></td>
								<td class="change_right"><a href="#"
									id="returnToLockScreen">返回锁屏画面</a></td>
							</tr>
							<tr>
								<td colspan="2"><div class="btn-box">
										<div class="btn-orange">
											<a href="javascript:void(0)" id="login">登&emsp;录</a>
										</div>
										<div class="btn-gray">
											<a href="javascript:void(0)" id="clear">清&emsp;空</a>
										</div>
									</div></td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
		<div class="bottom-panel"></div>
	</div>
</body>
</html>

<script>
	$(function() {
		$("#returnLoginForm").click(function() {
			$("#lockScreen").hide("fast");
			$("#loginForm").show("fast");
			$("#userName").focus();
		});
		$("#login").click(function() {
			var storage = window.sessionStorage;
			storage.setItem("username", $("#userName").val());
			if ($("#loginType").attr("checked")) {
				storage.setItem("loginType", "on");
			}
			$("#loginF").submit();
		});

		$("#clear").click(function() {
			$("#loginF")[0].reset();
		});

		$(document).keydown(function(event) {
			loginClick(event);
		});
	});

	function loginClick(e) {
		if (e.which == 13) {
			if ($("#loginForm").is(":hidden")) {
				$(".btn-login").click();
			} else {
				$("#login").click();
			}
		}
	}
</script>
