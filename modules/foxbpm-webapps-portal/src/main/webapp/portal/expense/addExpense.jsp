<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.foxbpm.engine.impl.entity.UserEntity" %>
<%@ page import="org.foxbpm.engine.impl.entity.GroupEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<base href="<%=basePath%>">
<title>FoxBPM流程门户</title>
<meta name="description" content="">
<meta name="author" content="">
<%
	UserEntity user = (UserEntity)request.getSession().getAttribute("user");
	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmsssss");
	String expenseId = "BXD-" + df.format(new Date());
%>

<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" type="text/css" media="screen" href="css/your_style.css">
<link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" media="screen" href="css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" media="screen" href="css/smartadmin-production.css">
<script src="js/libs/jquery-2.0.2.min.js"></script>
<script src="js/libs/jquery-ui-1.10.3.min.js"></script>
<!-- JQUERY VALIDATE -->
<script src="js/plugin/jquery-validate/jquery.validate.min.js"></script>
<script src="js/common.js"></script>
<script src="js/notification/SmartNotification.min.js"></script>
<script type="text/javascript" src="portal/taskCommand/js/foxbpmframework.js"></script>
<script type="text/javascript" src="portal/taskCommand/js/flowCommandCompenent.js"></script>
<script type="text/javascript" src="portal/taskCommand/js/flowCommandHandler.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$("#closeModal").click(function(){
			if(self.frameElement != null && self.frameElement.tagName=='IFRAME'){
				window.parent.closeModal();
			}else{
				window.close();
			}
		});
		
		var $this = $("#createTime");
		var dataDateFormat = $this.attr('data-dateformat') || 'yy-mm-dd';
		$this.datepicker({
					dateFormat : dataDateFormat,
					prevText : '<i class="fa fa-chevron-left"></i>',
					nextText : '<i class="fa fa-chevron-right"></i>',
				});
		$this = null;
		
		
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
				}});
		var _getBizKey = function() {
			return $("#expenseId").val();
		};
		var _getTaskComment = function() {
			return "";
		};

		var _flowCommit = function(flowInfo) {
			$("#flowCommandInfo").val(JSON.stringify(flowInfo));
			$("#form1").submit();
		};
		var flowconfig = {
			getBizKey : _getBizKey,
			getTaskComment : _getTaskComment,
			flowCommit : _flowCommit
		};

		var flowCommandCompenent = new Foxbpm.FlowCommandCompenent(
				flowconfig);
		flowCommandCompenent.init();
	});
</script>
	
</head>

<body class="">

	<div class="jarviswidget" id="wid-id-8" data-widget-editbutton="false"
		data-widget-custombutton="false">
		<header>
			<span class="widget-icon"> <i class="fa fa-edit"></i>
			</span>
			<h2>报销单据</h2>
		</header>
		<!-- widget div-->
		<div>
			<div class="jarviswidget-editbox"></div>
			<div class="widget-body no-padding">

				<form action="expenses.action" method="post"
					id="form1" class="smart-form">
					<fieldset>
						<div class="row">
							<section class="col col-6">
								<label class="label">单据号</label> <label class="input"> <i
									class="icon-append fa fa-user"></i> <input type="text"
									name="expenseId" id="expenseId" value="<%=expenseId%>" readOnly>
								</label>
							</section>
							<section class="col col-6">
								<label class="label">申请日期</label> <label class="input">
									<i class="icon-append fa fa-calendar"></i> <input type="text"
									name="createTime" id="createTime" class="form-control">
								</label>
							</section>
						</div>

						<div class="row">
							<section class="col col-6">
								<label class="label">报销人</label> <label class="input"> <i
									class="icon-append fa fa-user"></i> <input type="text"
									class="form-control" name="ownerName" id="ownerName" value="<%=user.getUserName()%>" readOnly>
									<input type="hidden"
									class="form-control" name="owner" id="owner" value="<%=user.getUserId()%>">
								</label>
							</section>
							<section class="col col-6">
								<label class="label">报销人部门</label>  <label class="select">
									<select name="dept" id="dept">
									<%
										List<GroupEntity> groupList = user.getGroups();
										if(groupList != null){
											for(GroupEntity group : groupList){
												
												if("dept".equals(group.getGroupType())){
													%>
														<option value="<%=group.getGroupId() %>"><%=group.getGroupName() %></option>
													<% 
												}
											}
										}
									%>
									
										
								</select> <i></i>
								</label>
							</section>
						</div>

						<div class="row">
							<section class="col col-6">
								<label class="label">报销金额</label> <label class="input">
									<i class="icon-append fa fa-user"></i> <input type="text"
									name="account" id="account" check-type="required" required-message="密码不能为空！">
								</label>
							</section>
							<section class="col col-6">
								<label class="label">发票类型</label> <label class="select">
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
							<label class="label">事由</label> <label class="textarea">
								<i class="icon-append fa fa-comment"></i> <textarea rows="4"
									name="reason" id="reason"></textarea>
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