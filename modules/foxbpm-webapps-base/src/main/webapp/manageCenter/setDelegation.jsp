<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="head.jsp" flush="true" />
<script type="text/javascript" src="common/js/select.js"></script>
<link rel="stylesheet" type="text/css" href="common/css/form.css"></link>
<title>设置代理人</title>
<script type="text/javascript" src="common/js/SETDelegation.js"></script>
<script type="text/javascript">
	var setDelegation = new SETDelegation({
		formId : 'subForm',
		tableId : 'detailTable',
		nowIndex : 'nowIndex',
		saveAction : 'saveUserDelegationInfo.action'
	});
	//全选
	$(function() {
		$("#checkall").on('click', function() {
			if (($(this).attr("checked") == 'checked')) {
				$("input[name='check']").attr("checked", true);
			} else {
				$("input[name='check']").attr("checked", false);

			}
		});
		$(".ClickLink").live('click', function() {
			//1选择用户 ,0流程定义
			var type = $(this).attr('type');
			var rowindex = $(this).attr('rowindex');
			if (0 == type) {
				var data = FoxbpmSelect({
					type : "processDef"
				});
				if (data && data.length > 0) {
					$("#processKey" + rowindex).val(data[0].key);
				}
			} else if (1 == type) {
				var data = FoxbpmSelect({
					type : "user"
				});
				if (data && data.length > 0) {
					$("#userId" + rowindex).val(data[0].userId);
				}
			}
		});
	});
</script>
</head>
<body>
	<form method="post" id="subForm" action="delegateAuthority.action">
		<div class="tpl-form-border">
			<div class="formbox">
				<table class="table-form">
					<tr>
						<td class="title-r">操作人:</td>
						<td colspan="3"><input name="agentUser" id="agentUser"
							value="${result.agentInfo.agentFrom}" readonly="readonly" /></td>
					</tr>
					<tr>
						<td class="title-r">开始时间:</td>
						<td><input type="text" id="startTime" name="startTime"
							readonly="readonly"
							value='<fmt:formatDate value="${result.agentInfo.startTime}"  type="date" pattern="yyyy-MM-dd"/>'
							onClick="WdatePicker()" /></td>
						<td class="title-r">结束时间:</td>
						<td><input type="text" id="endTime" name="endTime"
							readonly="readonly"
							value='<fmt:formatDate value="${result.agentInfo.endTime}" type="date" pattern="yyyy-MM-dd"/>'
							onClick="WdatePicker()" /></td>
					</tr>
					<tr>
						<td class="title-r">状态：</td>
						<td><select id="status" name="status">
								<option value="1"
									<c:if test="${result.agentInfo.status==1}">selected</c:if>>启用</option>
								<option value="0"
									<c:if test="${result.agentInfo.status==0}">selected</c:if>>停用</option>
						</select></td>
						<td></td>
						<td><input type="hidden" name="viewType" /></td>
					</tr>
				</table>
			</div>
			<div class="listbox">
				<table class="table-list" id="detailTable">
					<thead>
						<tr>
							<th style="width: 10%"><input type="checkbox" id="checkall"
								name="checkall"></th>
							<th style="width: 45%">流程</th>
							<th style="width: 45%">代理人</th>
						</tr>
					</thead>
					<tbody>
						<c:set var="nowIndex" value="0" />
						<c:forEach items="${result.agentInfo.detailInfoList}"
							var="dataList" varStatus="index">
							<tr id="row${index.index+1}">
								<input type="hidden" id="agentDetailsId${index.index+1}"
									value="${dataList.id}" />
								<td style="text-align: center"><input type="checkbox"
									name="check" rowindex="${index.index+1}" isNew='false'></td>
								<td><input id="processKey${index.index+1}"
									value="${dataList.processKey}" name="key${index.index+1}"
									readonly="readonly">
									<div class="btn-normal">
										<a href="javascript:void(0)" type='0' class="ClickLink"
											id="p${index.index+1}" rowindex="${index.index+1}">选择<em
											class="arrow-small"></em></a>
									</div></td>
								<td><input id="userId${index.index+1}"
									value="${dataList.agentTo}" readonly="readonly">
									<div class="btn-normal">
										<a href="javascript:void(0)" type='1' class="ClickLink"
											id="u${index.index+1}" rowindex="${index.index+1}">选择<em
											class="arrow-small"></em></a>
									</div></td>
							</tr>
							<c:set var="nowIndex" value="${index.index}" />
						</c:forEach>
					</tbody>
				</table>
				<table>
					<tr>
						<td>
							<div class="btn-normal">
								<a href="javascript:void(0)" onclick="setDelegation.addRow();">新增</a>
							</div>
						</td>
						<td>
							<div class="btn-normal">
								<a href="javascript:void(0)"
									onclick="setDelegation.deleteRow();">删除</a>
							</div>
						</td>
					</tr>
				</table>
				<div class="toolbar">
					<div class="btn-normal">
						<a href="javascript:void(0)" onclick="setDelegation.saveData();">保存</a>
					</div>
				</div>
			</div>
		</div>
	</form>
	<!-- 隐藏变量定义 -->
	<input type="hidden" id="agentId" name="agentId"
		value="${result.agentInfo.id}" />
	<input type="hidden" id="nowIndex" value="${nowIndex+1}" />
	<input type="hidden" id="insertAndUpdate" name="insertAndUpdate" />
	<input type="hidden" id="add" name="add" />
	<input type="hidden" id="update" name="update" />
	<input type="hidden" id="delete" name="delete" />
</body>
</html>