/**
 * 代理操作类
 */
function SETDelegation(config) {
	this.formId = config.formId;
	this.tableId = config.tableId;
	this.nowIndex = config.nowIndex;
	this.saveAction = config.saveAction;
	/**
	 * 添加行
	 */
	this.addRow = function() {
		var rowIndex = $("#" + this.nowIndex).val();
		(++rowIndex);
		var trow = "<tr id=row" + rowIndex + ">";
		trow += "<td style='text-align: center'>";
		trow += "<input type='checkbox' name='check' rowindex=" + rowIndex
				+ " isNew='true'></td>";
		trow += "<td><label id='processName'" + rowIndex + "'/>";
		trow += "<input id='processKey" + rowIndex + "' readonly='readonly'/>";
		trow += "<div class='btn-normal'><a href='javascript:void(0)'"
				+ " id='p" + rowIndex
				+ "' class='ClickLink' type='0' rowindex=" + rowIndex
				+ ">选择<em class='arrow-small'></em></a></div></td>";
		trow += "<td><label id='userName'" + rowIndex + "'/>";
		trow += "<input id='userId" + rowIndex + "' readonly='readonly'/>";
		trow += "<div class='btn-normal'>";
		trow += "<a href='javascript:void(0)'" + " id='u" + rowIndex
				+ "' class='ClickLink' type='1' rowindex=" + rowIndex
				+ ">选择<em class='arrow-small'></em></a></div></td>";
		trow += "</tr>";

		$("#" + this.tableId).append(trow);
		$("#" + this.nowIndex).attr("value", rowIndex);
	};

	/**
	 * 删除
	 */
	this.deleteRow = function() {
		var deleteed = $("#delete").val();
		var rowIndex = '';
		$("input[name=check]").each(function(index, ele) {
			if (ele.checked == true) {
				rowIndex = $(ele).attr("rowIndex");
				if ($(ele).attr("isNew") == 'false') {
					if (deleteed != "") {
						deleteed += ",";
					}
					deleteed += $("#agentDetailsId" + rowIndex).val();
				}
				$("#row" + rowIndex).remove();
			}
		});
		$("#checkall").attr("checked", false);
		$("#delete").val(deleteed);
	};
	this.saveData = function() {
		var updateParams = {};
		var addParams = {};
		var rowIndex = '';
		var agentDetailsId = '';
		var key = '';
		var user = '';
		var isError = false;
		var checkboxs = $("input[name=check]");
		var ele = null;

		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if ('' == startTime) {
			alert("开始时间不能为空!");
			return;
		}
		if ('' == endTime) {
			alert("结束时间不能为空!");
			return;
		}

		for (var i = 0, length = checkboxs.length; i < length; i++) {
			ele = checkboxs[i];
			rowIndex = $(ele).attr("rowIndex");
			agentDetailsId = $("#agentDetailsId" + rowIndex).val();
			key = $("#processKey" + rowIndex).val();
			user = $("#userId" + rowIndex).val();
			if (key == '' || key == undefined) {
				alert("流程不能为空！");
				isError = true;
				return;
			} else if (user == '' || user == undefined) {
				alert("代理人不能为空！");
				isError = true;
				return;
			}
			if ($(ele).attr("isNew") == 'false') {
				updateParams[agentDetailsId] = {
					'key' : key,
					'user' : user,
				};
			} else if ($(ele).attr("isNew") == 'true') {
				addParams[key] = user;

			}
		}

		if (isError != true) {
			var addInfo = JSON.stringify(addParams);
			var updateInfo = JSON.stringify(updateParams);
			var agentId = $("#agentId").val();
			var agentUser = $("#agentUser").val();
			var status = $("#status").val();

			var formId = this.formId;
			$.ajax({
				type : "POST",
				url : this.saveAction,
				data : "agentId=" + agentId + "&agentUser=" + agentUser
						+ "&delete=" + $("#delete").val() + "&startTime="
						+ startTime + "&endTime=" + endTime + "&status="
						+ status + "&add=" + addInfo + "&update=" + updateInfo,
				success : function(msg) {
					try {
						if ("" != msg) {
							var jsonObj = eval("(" + msg + ")");
							if (typeof (jsonObj) != undefined) {
								// 成功需要刷新页面数据
								if ('0' == jsonObj.statusCode) {
									alert("操作成功!");
									$("#" + formId).submit();
								} else {
									alert(jsonObj.errorMsg);
								}
							} else {
								alert("操作失败!");
							}
						}
					} catch (e) {

					}
				}
			});
		}
	};
}