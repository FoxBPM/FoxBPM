<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<div class="header">
	<div class="top-right">
		<ul>
			<li><a id="updateCache" href="javascript:void(0)">${applicationScope.appInfo["common.clearCache"]}</a></li>
			<li><a id="setAgent" href="javascript:void(0)">${applicationScope.appInfo["common.agent"]}</a></li>
			<li><a href="#" onclick="updateMyself();">${sessionScope.LOGIN_USER_NAME}</a></li>
			<li><a href="login.action?doLogOut=true">${applicationScope.appInfo["common.logOut"]}</a></li>
			<li><a href="processDef.action" target='_blank'>管控中心</a></li>
			<li><a href="#" id="red" name="theme_color"
				style="background-color: #dc562e; display: block; width: 10px; height: 10px; margin-top: 5px; border: 1px solid #404040"></a></li>
			<li><a href="#" id="blue" name="theme_color"
				style="background-color: #10a7d9; display: block; width: 10px; height: 10px; margin-top: 4px; border: 1px solid #404040"></a></li>
		</ul>
	</div>
	<div class="menu">
		<div class="logo"></div>
		<ul>
			<li><a id="getMyProcess" href="queryStartProcess.action"><h1>${applicationScope.appInfo["module.startFlow"]}</h1>
					<h4>Start Flow</h4></a></li>
			<li><a id="getMyTask" href="queryToDoTask.action"><h1>${applicationScope.appInfo["module.schedule"]}</h1>
					<h4>To Do Task</h4></a></li>
			<li><a id="getAllProcess" href="queryProcessInst.action"><h1>${applicationScope.appInfo["module.flowQuery"]}</h1>
					<h4>Flow Query</h4></a></li>
			<li><a id="getPlaceOnFile"
				href="FlowCenter?action=getPlaceOnFile"><h1>${applicationScope.appInfo["module.placeOnFile"]}</h1>
					<h4>Archiving Flow</h4></a></li>
		</ul>
	</div>
</div>
<script>
	$(function() {
		var color = window.localStorage.getItem("color");
		if (color) {
			var url = $("#color").attr("href");
			url = url.substring(0, url.lastIndexOf("_") + 1);
			url += color + ".css";
			$("#color").attr("href", url);
		} else {
			var url = $("#color").attr("href");
			url = url.substring(0, url.lastIndexOf("_") + 1);
			url += "red.css";
			$("#color").attr("href", url);
		}
		$("#" + color).css("border", "1px solid #fff");
		$("#updateCache").click(function() {
			$.ajax({
				type : "POST",
				url : "updateCache.action",
				success : function(msg) {
					alert(msg);
				}
			});
		});

		$("#setAgent").click(function() {
			var url = "delegateAuthority.action";
			Fix.OpenMethod.openWindow(url);
		});

		$("#red").click(function() {
			var url = $("#color").attr("href");
			url = url.substring(0, url.lastIndexOf("_") + 1);
			url += "red.css";
			$("#color").attr("href", url);
			window.localStorage.setItem("color", "red");
			$("a[name=theme_color]").css("border", "1px solid #404040");
			$(this).css("border", "1px solid #fff");
			return false;
		});
		$("#blue").click(function() {
			var url = $("#color").attr("href");
			url = url.substring(0, url.lastIndexOf("_") + 1);
			url += "blue.css";
			$("#color").attr("href", url);
			window.localStorage.setItem("color", "blue");
			$("a[name=theme_color]").css("border", "1px solid #404040");
			$(this).css("border", "1px solid #fff");
			return false;
		});
	});

	chooseSelect();
	function updateMyself() {
		window.open("FlowCenter?action=getUserInfo&isUpdate=true");
	}

	function chooseSelect() {
		var now = '${nowAction}';
		$("#" + now).addClass("select");
	}
</script>
