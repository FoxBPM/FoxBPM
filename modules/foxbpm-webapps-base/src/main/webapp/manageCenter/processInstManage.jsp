<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../common/head.jsp" flush="true" />
<script type="text/javascript" src="common/js/processInstManage.js"></script>
<script type="text/javascript">
	$('#checkall').click(function() {
		var tii = $(this).attr("checked");
		var checkboxs = $("input[name=checked]");
		if (tii == "checked") {
			for (var i = 0; i < checkboxs.length; i++) {
				$(checkboxs[i]).attr("checked", 'true');
			}
		} else {
			for (var i = 0; i < checkboxs.length; i++) {
				$(checkboxs[i]).removeAttr("checked");
			}
		}
	});

	$(function() {
		Fix.Util.ClickTr(null, false, true, 0, function($table) {
			var flag = true;
			$("tbody tr.selected", $table).each(function() {
				var state = $("td:eq(10)", $(this)).html();
				if (state.trim() == "运行中" || state.trim() == "暂停") {
					flag = false;
				}
			});
			if (!flag) {
				$("div#setHis").removeClass("btn-normal").addClass(
						"btn-disable");
			}
		});

		$("a[name=flowGraph]")
				.click(
						function() {
							var pdk = $(this).attr("pdk");
							var pii = $(this).attr("pii");
							var url = "queryTaskDetailInfor.action?processDefinitionKey="
									+ pdk + "&processInstanceId=" + pii;
							window.open(url);
						});

		$("#selectUser").click(function() {
			var obj = {
				type : "user"
			};
			var d = FixSelect(obj);
			var userId = d[0].USERID;
			var userName = d[0].USERNAME;
			$("#initor").val(userId);
			$("#initorName").val(userName);
		});

		var status = '${result.status}';
		if (status != '')
			$("#status").val(status);
	});

	//表单清空
	function clearInfo() {
		$("#processDefinitionKey").val("");
		$("#processInstanceId").val("");
		$("#processDefinitionName").val("");
		$("#subject").val("");
		$("#bizKey").val("");
		$("#initor").val("");
		$("#initorName").val("");
		$("#status").val("");
	}

	var pinstManage = new ProcessInstManage({formId:'subForm'});
	
</script>
</head>

<body>
	<div class="main-panel">
		<jsp:include page="top.jsp" flush="true" />
		<div class="center-panel">
			<form id="subForm" method="post" action="processInstManage.action">
				<!-- 左 -->
				<div class="right">
					<div class="search">
						<table>
							<tr>
								<td class="title-r">${applicationScope.appInfo["task.processDefinitionName"]}：</td>
								<td><input type="text" id="processDefinitionName"
									name="processDefinitionName" class="fix-input"
									style="width: 160px;" value="${result.processDefinitionName}" /></td>
								<td class="title-r">${applicationScope.appInfo["task.processInstanceId"]}：</td>
								<td><input type="text" id="processInstanceId"
									name="processInstanceId" class="fix-input"
									style="width: 160px;" value="${result.processInstanceId}" /></td>
								<td class="title-r">${applicationScope.appInfo["task.subject"]}：</td>
								<td style="width: 200px;"><input type="text" id="subject"
									name="subject" class="fix-input" style="width: 160px;"
									value="${result.subject}" /></td>
								<td>
									<table style="margin: 0">
										<tr>
											<td>
												<div class="btn-normal">
													<a href="javascript:void(0)"
														onclick="$('#subForm').submit();">${applicationScope.appInfo["common.search"]}</a>
												</div>
											</td>
											<td>
												<div class="btn-normal">
													<a href="javascript:void(0)" onclick="clearInfo();">${applicationScope.appInfo["common.clear"]}</a>
												</div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="title-r">${applicationScope.appInfo["task.bizKey"]}：</td>
								<td><input type="text" id="bizKey" name="bizKey"
									class="fix-input" style="width: 160px;"
									value="${result.bizKey}" /></td>
								<td class="title-r">${applicationScope.appInfo["task.initor"]}：</td>
								<td>
									<table style="margin: 0">
										<tr>
											<td><input type="hidden" id="initor" name="initor"
												class="fix-input" value="${result.initor}" /> <input
												type="text" id="initorName" readonly="true"
												name="initorName" class="fix-input" style="width: 94px;"
												value="${result.initorName}" /></td>
											<td>
												<div class="btn-normal">
													<a href="#" onclick="" id="selectUser">${applicationScope.appInfo["common.select"]}<em
														class="arrow-small"></em></a>
												</div>
											</td>
										</tr>
									</table>
								</td>
								<td class="title-r">${applicationScope.appInfo["task.status"]}：</td>
								<td><select id="status" name="status" class="fix-input"
									style="width: 172px;">
										<option value="">请选择</option>
										<option value="suspend">暂停</option>
										<option value="running">运行中</option>
										<option value="complete">完成</option>
										<option value="termination">终止</option>
								</select></td>
								<td></td>
							</tr>
						</table>
					</div>
					<div id="toolbar"
						style="padding-right: 2px; text-align: right; margin-bottom: 4px;">
						<div id="doSuspend" class="btn-normal" data-scope=single
							style="display: inline-block; margin-left: 5px;">
							<a href="javascript:void(0)" onclick="pinstManage.doSuspend();">暂停</a>
						</div>
						<div id="doContinue" class="btn-normal" data-scope=single
							style="display: inline-block; margin-left: 5px;">
							<a href="javascript:void(0)" onclick="pinstManage.doContinue();">恢复</a>
						</div>
						<div id="doTerminat" class="btn-normal" data-scope=single
							style="display: inline-block; margin-left: 5px;">
							<a href="javascript:void(0)" onclick="pinstManage.doTerminat();">作废</a>
						</div>
						<div id="doDelete" class="btn-normal" data-scope=single
							style="display: inline-block; margin-left: 5px;">
							<a href="javascript:void(0)" onclick="pinstManage.doDelete();">删除</a>
						</div>
						<div id="updateVariables" class="btn-normal" data-scope=single
							style="display: inline-block; margin-left: 5px;">
							<a href="javascript:void(0)"
								onclick="pinstManage.updateVariables();">变量管理</a>
						</div>
						<div id="updateToken" class="btn-normal" data-scope=single
							style="display: inline-block; margin-left: 5px;">
							<a href="javascript:void(0)" onclick="pinstManage.updateToken();">令牌管理</a>
						</div>
						<div id="setHis" class="btn-normal" data-scope=multi
							style="display: inline-block; margin-left: 5px;">
							<a href="javascript:void(0)" onclick="pinstManage.setHis();">归档</a>
						</div>
					</div>
					<div class="content">
						<table width="100%" class="fix-table">
							<thead>
								<th width="30">&nbsp;</th>
								<th width="30">${applicationScope.appInfo["common.no"]}</th>
								<th width="">${applicationScope.appInfo["task.processInstanceId"]}</th>
								<th>${applicationScope.appInfo["task.processDefinitionName"]}</th>
								<th width="">${applicationScope.appInfo["task.subject"]}</th>
								<th width="">${applicationScope.appInfo["task.bizKey"]}</th>
								<th width="130">${applicationScope.appInfo["task.startTime"]}</th>
								<th width="130">${applicationScope.appInfo["task.updateTime"]}</th>
								<th width="">${applicationScope.appInfo["task.initor"]}</th>
								<th width="">${applicationScope.appInfo["task.nodeName"]}</th>
								<th width="">${applicationScope.appInfo["task.status"]}</th>
								<th width="60">${applicationScope.appInfo["common.operation"]}</th>
							</thead>
							<c:forEach items="${result.dataList}" var="dataList"
								varStatus="index">
								<tr>
									<td class="num"><input type="radio" name="checked"
										value="${dataList.id}"></td>
									<td style="text-align: center;">${(index.index+1)+pageInfor.pageSize*(pageInfor.pageIndex-1)}</td>
									<td>${dataList.id}</td>
									<td>${dataList.processDefinitionName}</td>
									<td>${dataList.subject}</td>
									<td>${dataList.bizKey}</td>
									<td class="time"><fmt:formatDate
											value="${dataList.startTime}" type="both" /></td>
									<td class="time"><fmt:formatDate
											value="${dataList.updateTime}" type="both" /></td>
									<td>${dataList.startAuthorName}</td>
									<td>${dataList.nowNodeInfo}</td>
									<td><c:if test="${dataList.instanceStatus == 'suspend'}"
											var="runStatue">暂停</c:if> <c:if
											test="${dataList.instanceStatus == 'running'}"
											var="runStatue">运行中</c:if> <c:if
											test="${dataList.instanceStatus == 'complete'}"
											var="runStatue">完成</c:if> <c:if
											test="${dataList.instanceStatus == 'termination'}"
											var="runStatue">终止</c:if></td>
									<td><a name="flowGraph" href="javascript:void(0)"
										pii="${dataList.id}" pdk="${dataList.processDefinitionKey}">查看</a></td>
								</tr>
							</c:forEach>
						</table>
						<jsp:include page="../common/page.jsp" flush="true" />
					</div>
				</div>
				<!-- 分页 -->
			</form>
		</div>
	</div>
</body>
</html>
