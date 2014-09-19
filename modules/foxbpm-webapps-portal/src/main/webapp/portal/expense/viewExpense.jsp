<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-us">	
<head>
<%
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
	<base href="<%=basePath%>">
	<meta charset="utf-8">
	<title>FoxBPM流程门户</title>
	<meta name="description" content="">
	<meta name="author" content="">
	
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<link rel="stylesheet" type="text/css" media="screen" href="css/your_style.css">
	<link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" media="screen" href="css/smartadmin-production.css">
	<script src="js/libs/jquery-2.0.2.min.js"></script>
	<script src="js/notification/SmartNotification.min.js"></script>
	<script src="js/common.js"></script>
	
	
	<script type="text/javascript">
	$(document).ready(function() {
		$("#closeModal").click(function(){
			if(self.frameElement != null && self.frameElement.tagName=='IFRAME'){
				window.parent.$('#remoteModal').modal('hide');
			}else{
				window.close();
			}
		});
		//加载业务数据
		initFormData();
	});
	
	
	function initFormData(){
		var expenseId = requestUrlParam("dataId");
		if(expenseId == null || expenseId == undefined || expenseId == ""){
			$.smallBox({ 
				title : '错误!',
				content : '单据号为空！',
				color : "#C46A69",
				icon : "fa fa-warning shake animated",
				timeout : 2000
			});
			return;
		}
		$.ajax({
	        type: "get",//使用get方法访问后台
	        dataType: "json",//返回json格式的数据
	        url: "findExpense.action",//要访问的后台地址
	        data:{expenseId:expenseId},
	        success: function(msg){//msg为返回的数据，在这里做数据绑定
	        	$("#expenseId").val(msg.expenseId);
	        	$("#ownerName").val(msg.ownerName);
	        	$("#account").val(msg.account);
	        	$("#deptName").val(msg.deptName);
	        	$("#reason").val(msg.reason);
	        	$("#invoiceType").val(msg.invoiceType);
	        	$("#createTime").val(msg.createTime);
	        	$("#owner").val(msg.owner);
	        	$("#dept").val(msg.dept);
	        },
	        error:function(msg){
	        	$.smallBox({
					title : '提示!',
					content : '系统错误，请联系管理员！',
					color : "#C46A69",
					icon : "fa fa-warning shake animated",
					timeout : 2000
				});
	        }
		});
	}
	
	</script>
</head>
	
<body class="">

<div class="jarviswidget" id="wid-id-8" data-widget-editbutton="false" data-widget-custombutton="false">
	<header>
		<span class="widget-icon"> <i class="fa fa-edit"></i>
		</span>
		<h2>报销单据</h2>
	</header>
	<!-- widget div-->
	<div>
		<div class="jarviswidget-editbox">
		</div>
		<div class="widget-body no-padding">

			<form method="post" id="form1" class="smart-form">
				<fieldset>
					<div class="row">
						<section class="col col-6">
							<label class="label">单据号</label> <label class="input"> <i
								class="icon-append fa fa-user"></i> <input type="text"
								name="expenseId" id="expenseId" readOnly>
							</label>
						</section>
						<section class="col col-6">
							<label class="label">申请日期</label> <label class="input">
								<i class="icon-append fa fa-calendar"></i>
								<input type="text" name="createTime" id="createTime" class="form-control" readOnly>
							</label>
						</section>
					</div>
					
					<div class="row">
						<section class="col col-6">
							<label class="label">报销人</label> <label class="input"> <i
								class="icon-append fa fa-user"></i> <input type="text" class="form-control"
								name="ownerName" id="ownerName" readOnly>
								<input type="hidden" name="owner" id="owner">
							</label>
						</section>
						<section class="col col-6">
							<label class="label">报销人部门</label> <label class="input">
								<i class="icon-append fa fa-envelope-o"></i> <input name="deptName" id="deptName" readOnly>
								<input type="hidden" id="dept" name="dept">
							</label>
						</section>
					</div>
					
					<div class="row">
						<section class="col col-6">
							<label class="label">报销金额</label> <label class="input"> <i
								class="icon-append fa fa-user"></i> <input type="text"
								name="account" id="account" readOnly>
							</label>
						</section>
						<section class="col col-6">
							<label class="label">发票类型</label>
							<label class="select">
								<select name="invoiceType" id="invoiceType" disabled>
									<option value="0" selected="">餐饮费</option>
									<option value="1">住宿费</option>
									<option value="2">车船票</option>
									<option value="3">市内公交</option>
									<option value="4">办公用品</option>
									<option value="5">其他</option>
								</select> <i></i>
							</label>
						</section>
					</div>

					<section>
						<label class="label">事由</label><label class="textarea">
							<i class="icon-append fa fa-comment"></i> <textarea rows="4"
								name="reason" id="reason" readOnly></textarea>
						</label>
					</section>
				</fieldset>

				<footer id="toolbar">
					<button type='button' id="closeModal" class='btn btn-primary'>关闭</button>
				</footer>
			</form>

		</div>
	</div>
</div>
</body>
</html>