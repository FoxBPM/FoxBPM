<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-us">	
<head>
	<meta charset="utf-8">
	<title>FoxBPM流程门户</title>
	<meta name="description" content="">
	<meta name="author" content="">
	
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<link rel="stylesheet" type="text/css" media="screen" href="../css/your_style.css">
	<link rel="stylesheet" type="text/css" media="screen" href="../css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" media="screen" href="../css/smartadmin-production.css">
	<script src="../js/libs/jquery-2.0.2.min.js"></script>
	<script src="../js/notification/SmartNotification.min.js"></script>
	<script src="../js/libs/jquery-ui-1.10.3.min.js"></script>
	<script src="../../js/plugin/jquery-validate/jquery.validate.min.js"></script>
	<script src="../js/foxbpm/common.js"></script>
	<script type="text/javascript" src="/foxbpm-webapps-common/portal/taskCommand/js/foxbpmframework.js"></script>
	<script type="text/javascript" src="/foxbpm-webapps-common/portal/taskCommand/js/flowCommandCompenent.js"></script>
	<script type="text/javascript" src="/foxbpm-webapps-common/portal/taskCommand/js/flowCommandHandler.js"></script>
	
	
	<script type="text/javascript">
	$(document).ready(function() {
		
		$("#closeModal").click(function(){
			if(self.frameElement != null && self.frameElement.tagName=='IFRAME'){
				window.parent.closeModal();
			}else{
				window.close();
			}
		});
		
		//加载业务数据
		initFormData();
		//加载校验信息
		initValidation();
		
		var _getBizKey = function() {
	        return $("#expenseId").val();
	    };
	    var _getTaskComment = function() {
	       	return $("#_taskComment").val();
	    };
	    
	    var _flowCommit = function(flowInfo){
    		$("#flowCommandInfo").val(JSON.stringify(flowInfo));
    		$("#form1").submit();
	    };
		var flowconfig ={ getBizKey: _getBizKey, getTaskComment: _getTaskComment,flowCommit:_flowCommit };
		
		var flowCommandCompenent = new Foxbpm.FlowCommandCompenent(flowconfig);
		flowCommandCompenent.init();
	});
	
	
	function initFormData(){
		var expenseId = requestUrlParam("dataId");
		if(expenseId == null || expenseId == undefined || expenseId == ""){
			showMessage("错误","单据号为空！","error");
			return;
		}
		$.ajax({
	        type: "get",//使用get方法访问后台
	        dataType: "json",//返回json格式的数据
	        url: "../../findExpense.action",//要访问的后台地址
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
	        	showMessage("错误","系统错误，请重新打开或联系管理员！","error");
	        }
		});
	}
	
	function initValidation(){
		$("#form1").validate({
			rules : {
				account : {
					required : true,
					number:true
				}
			},
			messages : {
				account : {
					required : '请输入金额',
					number:'请输入合法数字'
				}
			},
			errorPlacement : function(error, element) {
				showMessage("错误",error.html(),"error");
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

			<form action="../../updateExpense.action" method="post" id="form1" class="smart-form">
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
								name="account" id="account">
							</label>
						</section>
						<section class="col col-6">
							<label class="label">发票类型</label>
							<label class="select">
								<select name="invoiceType" id="invoiceType">
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
								name="reason" id="reason"></textarea>
						</label>
					</section>
					
					<section>
						 <label class="textarea">
							<i class="icon-append fa fa-comment"></i> <textarea rows="1"
								name="_taskComment" id="_taskComment" placeholder="审批意见"></textarea>
						</label>
					</section>

				</fieldset>

				<footer id="toolbar">
				<button type='button' id="closeModal" class='btn btn-primary'>关闭</button>
				</footer>
				<input type="hidden" name="flowCommandInfo" id="flowCommandInfo">
			</form>

		</div>
	</div>
</div>
</body>
</html>