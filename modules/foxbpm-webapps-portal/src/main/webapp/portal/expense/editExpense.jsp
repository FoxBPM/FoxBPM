<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<base href="<%=basePath%>">
<!DOCTYPE html>
<html lang="en-us">	
<head>
	<meta charset="utf-8">
	<title>FoxBPM流程门户</title>
	<meta name="description" content="">
	<meta name="author" content="">
	
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<link rel="stylesheet" type="text/css" media="screen" href="css/your_style.css">
	<link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" media="screen" href="css/smartadmin-production.css">
	<script src="js/libs/jquery-2.0.2.min.js"></script>
	<script src="js/libs/jquery-ui-1.10.3.min.js"></script>
	<script src="js/plugin/jquery-validate/jquery.validate.min.js"></script>
	<script src="js/common.js"></script>
	<script src="js/notification/SmartNotification.min.js"></script>
	<script type="text/javascript" src="portal/taskCommand/js/foxbpmframework.js"></script>
	<script type="text/javascript" src="portal/taskCommand/js/flowCommandCompenent.js"></script>
	<script type="text/javascript" src="portal/taskCommand/js/flowCommandHandler.js"></script>
	<!-- 表单命令弹出框 -->
	<link href="portal/taskCommand/js/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css">
	<script src="portal/taskCommand/js/ligerUI/js/core/base.js" type="text/javascript"></script>
	<script src="portal/taskCommand/js/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
	<script src="portal/taskCommand/js/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
	<script src="portal/taskCommand/js/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script src="portal/taskCommand/js/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
	
	<script type="text/javascript">
	 //parent.window.$('#contentFrame').css("width",650);
	 //设置上次读取的时间，表单初始化和每次轮询之后设置值
	 var lastReadTime;
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
    		formCommit = true;
	    };
		var flowconfig ={ getBizKey: _getBizKey, getTaskComment: _getTaskComment,flowCommit:_flowCommit };
		
		var flowCommandCompenent = new Foxbpm.FlowCommandCompenent(flowconfig);
		flowCommandCompenent.init();
		//initChatMsg();
/* 		setInterval(function() { 
			var msgUrl = _serviceUrl+"social";
			$.ajax({
		        type: "get",//使用get方法访问后台
		        dataType: "json",//返回json格式的数据
		        url: msgUrl,//要访问的后台地址
		        data:{taskId:"taskId",msgType:"findReply",lastReadTime:lastReadTime},
		        success: function(msgInfos){//msg为返回的数据，在这里做数据绑定  
		        	//设置上次读取消息的时间
		        	setLastReadTime();
		        	for(var i=0;i<msgInfos.length;i++){
		        		//$(".mes" + 3).append("<div class='message clearfix' style='border-bottom:0px'><div class='user-logo' style='float:left'><img src='" + "img/head/2024.jpg" + "'/>" + "</div>" +"<div class='msgDiv' style='margin-top:0px;margin-left:65;width:110px;background:#33CC99'>&nbsp;"+msgInfos[i].content+" <div style='position:absolute;top:5px;left:-20px;border:solid 10px;border-color: rgba(15, 15, 15, 0) #33CC99 rgba(200, 37, 207, 0) rgba(248, 195, 1, 0);'></div>"+ "<div class='wrap-ri'>" + "<div clsss='clearfix' style='bottom: 0px;width: 150px;left: 50px;top: 40px;' style='float:right'><span>" + msgInfos[i].time + "</span></div>" + "</div>" + "<div style='clear:both;'></div>" + "</div>");
		        		$("#msg_list").append("<li style='height: 69px' class='message'><img src='img/avatars/sunny.png' class='online' alt=''><div class='message-text'><time>"+msgInfos[i].time+"</time> <a href='javascript:void(0);' class='username'>John Doe</a><span>"+msgInfos[i].content+" </span><i class='fa fa-smile-o txt-color-orange'></i></div></li>");
		        		$("#chat-body").scrollTop($("#msg_list").height());
		        	}
		        },
		        error:function(msg){
		        	showMessage("错误","系统错误，请重新打开或联系管理员！","error");
		        }
			});
		}, 5000); */
		
		
		var filter_input = $('#filter-chat-list'),
		chat_users_container = $('#chat-container > .chat-list-body'),
		chat_users = $('#chat-users'),
		chat_list_btn = $('#chat-container > .chat-list-open-close'),
		chat_body = $('#chat-body');
		
		// open chat list
		/*
		 * LIST FILTER (CHAT)
		 */
		
		// custom css expression for a case-insensitive contains()
		jQuery.expr[':'].Contains = function (a, i, m) {
		    return (a.textContent || a.innerText || "").toUpperCase().indexOf(m[3].toUpperCase()) >= 0;
		};
		
		function listFilter(list) { // header is any element, list is an unordered list
		    // create and add the filter form to the header
		
		    filter_input.change(function () {
		        var filter = $(this).val();
		        if (filter) {
		            // this finds all links in a list that contain the input,
		            // and hide the ones not containing the input while showing the ones that do
		            chat_users.find("a:not(:Contains(" + filter + "))").parent().slideUp();
		            chat_users.find("a:Contains(" + filter + ")").parent().slideDown();
		        } else {
		            chat_users.find("li").slideDown();
		        }
		        return false;
		    }).keyup(function () {
		        // fire the above change event after every letter
		        $(this).change();
		
		    });
		
		}
		
		// on dom ready
		listFilter(chat_users);
		
		// open chat list
		chat_list_btn.click(function () {
		    $(this).parent('#chat-container').toggleClass('open');
		});
		
		chat_body.animate({
		    scrollTop: chat_body[0].scrollHeight
		}, 500);
	});
	
	function setLastReadTime(){
		 
		var msgUrl = _serviceUrl+"social";
		$.ajax({
	        type: "get",//使用get方法访问后台
	        async:false,
	        dataType: "json",//返回json格式的数据
	        url: msgUrl,//要访问的后台地址
	        data:{taskId:"taskId",msgType:"getCurrentReadTime"},
	        success: function(msgInfo){//msg为返回的数据，在这里做数据绑定     
	        	lastReadTime = msgInfo.content;
	        } 
		}); 
		  
	}
	function initChatMsg(){
		//设置读取消息的时间
		setLastReadTime();
		var msgUrl = _serviceUrl+"social";
		$.ajax({
	        type: "get",//使用get方法访问后台
	        dataType: "json",//返回json格式的数据
	        url: msgUrl,//要访问的后台地址
	        data:{taskId:"taskId",msgType:"findAll"},
	        success: function(msgInfos){//msg为返回的数据，在这里做数据绑定  
	        	for(var i=0;i<msgInfos.length;i++){
	        		$("#msg_list").append("<li style='height: 69px' class='message'><img src='img/avatars/sunny.png' class='online' alt=''><div class='message-text'><time>"+msgInfos[i].time+"</time> <a href='javascript:void(0);' class='username'>John Doe</a>"+msgInfos[i].content+" <i class='fa fa-smile-o txt-color-orange'></i></div></li>");
	        		$("#chat-body").scrollTop($("#chat-body").height())
	        	}
	        },
	        error:function(msg){
	        	showMessage("错误","系统错误，请重新打开或联系管理员！","error");
	        }
		});
	}
	
	function initFormData(){
		var expenseId = requestUrlParam("dataId");
		if(expenseId == null || expenseId == undefined || expenseId == ""){
			showMessage("错误","单据号为空！","error");
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
	
	<style type="text/css">
		.msgDiv{
			position:relative;
			width:300px;
			height:35px;
			background:#66FFFF;
			border-radius:5px;  
			margin:30px auto 0;
			word-wrap: break-word;
			word-break: normal;
		} 
	</style>
</head>
	
<body class="keBody">

<div class="jarviswidget" id="wid-id-8" data-widget-editbutton="false" data-widget-custombutton="false"  style="margin:0 0 0 0">
	<header>
		<span class="widget-icon"> <i class="fa fa-edit"></i>
		</span>
		<h2>报销单据</h2>
	</header>
	<!-- widget div-->
	<div id="leftDiv">
		<div class="jarviswidget-editbox">
		</div>
		<div class="widget-body no-padding" style="border-right-width:2px;border-right-style:outset;">
			<form action="updateExpense.action" method="post" id="form1" class="smart-form">
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

				<footer id="toolbar" style="padding-top:3px;padding-bottom:3px">
				<button type='button' id="closeModal" class='btn btn-primary' >关闭</button>
				</footer>
				<input type="hidden" name="flowCommandInfo" id="flowCommandInfo">
			</form>
		<!-- <a id="aaa" flag="0"
					style="position: absolute; right: -5; top: 50%; z-index: 1; cursor: pointer; border-top: 10px solid rgba(241, 248, 241, 0); border-right: 10px solid #993366; border-bottom: 10px solid rgba(0, 0, 255, 0);"></a> -->
		
		</div>
	</div>
        <div id="rightDiv" style="float:left;display:none;height:502px; border:1px solid red;border-left-width:0px;padding-left: 0px;padding-top: 0px">
 
			<!--效果html开始--> 
			     <!-- new widget -->
			<div class="jarviswidget jarviswidget-color-blueDark" style="background-color:white;height: 455px;width: 500px;" id="wid-id-1" data-widget-editbutton="false" data-widget-fullscreenbutton="false">

				<!-- widget options:
				usage: <div class="jarviswidget" id="wid-id-0" data-widget-editbutton="false">

				data-widget-colorbutton="false"
				data-widget-editbutton="false"
				data-widget-togglebutton="false"
				data-widget-deletebutton="false"
				data-widget-fullscreenbutton="false"
				data-widget-custombutton="false"
				data-widget-collapsed="true"
				data-widget-sortable="false"

				-->

				<header>
					<span class="widget-icon"> <i class="fa fa-comments txt-color-white"></i> </span>
					<h2>社交面板</h2>
					<div class="widget-toolbar">
						<!-- add: non-hidden - to disable auto hide -->

						<div class="btn-group">
							<button class="btn dropdown-toggle btn-xs btn-success" data-toggle="dropdown">
								Status <i class="fa fa-caret-down"></i>
							</button>
							<ul class="dropdown-menu pull-right js-status-update">
								<li>
									<a href="javascript:void(0);"><i class="fa fa-circle txt-color-green"></i> Online</a>
								</li>
								<li>
									<a href="javascript:void(0);"><i class="fa fa-circle txt-color-red"></i> Busy</a>
								</li>
								<li>
									<a href="javascript:void(0);"><i class="fa fa-circle txt-color-orange"></i> Away</a>
								</li>
								<li class="divider"></li>
								<li>
									<a href="javascript:void(0);"><i class="fa fa-power-off"></i> Log Off</a>
								</li>
							</ul>
						</div>
					</div>
				</header>

				<!-- widget div-->
				<div>
					<!-- widget edit box -->
					<div class="jarviswidget-editbox">
						<div>
							<label>Title:</label>
							<input type="text" />
						</div>
					</div>
					<!-- end widget edit box -->

					<div class="widget-body widget-hide-overflow no-padding">
						<!-- content goes here -->

						<!-- CHAT CONTAINER -->
						<div id="chat-container">
							<span class="chat-list-open-close"><i class="fa fa-user"></i><b>!</b></span>

							<div class="chat-list-body custom-scroll">
								<ul id="chat-users">
									<li>
										<a href="javascript:void(0);"><img src="img/avatars/5.png" alt="">Robin Berry <span class="badge badge-inverse">23</span><span class="state"><i class="fa fa-circle txt-color-green pull-right"></i></span></a>
									</li>
									<li>
										<a href="javascript:void(0);"><img src="img/avatars/male.png" alt="">Mark Zeukartech <span class="state"><i class="last-online pull-right">2hrs</i></span></a>
									</li>
									 
								</ul>
							</div>
							<div class="chat-list-footer">
								<div class="control-group">
									<form class="smart-form">
										<section>
											<label class="input">
												<input type="text" id="filter-chat-list" placeholder="Filter">
											</label>
										</section>

									</form>

								</div>

							</div>

						</div>

						<!-- CHAT BODY -->
						<div id="chat-body" class="chat-body custom-scroll">
							<ul id="msg_list">
								 
							</ul>

						</div>

						<!-- CHAT FOOTER -->
						<div class="chat-footer" style="height: 166px;">

							<!-- CHAT TEXTAREA -->
							<div class="textarea-div">

								<div class="typearea" style="height: 111px;">
									<textarea placeholder="Write a reply..." id="textarea-expand" class="custom-scroll"></textarea>
								</div>

							</div>

							<!-- CHAT REPLY/SEND -->
							<span class="textarea-controls">
								<button class="btn btn-sm btn-primary pull-right" id="ReplyChat">
									回复
								</button> <span class="pull-right smart-form" style="margin-top: 3px; margin-right: 10px;"> <label class="checkbox pull-right">
										<input type="checkbox" name="subscription" id="subscription">
										<i></i>Press <strong> ENTER </strong> to send </label> </span> <a href="javascript:void(0);" class="pull-left"><i class="fa fa-camera fa-fw fa-lg"></i></a> </span>

						</div>

						<!-- end content -->
					</div>

				</div>
				<!-- end widget div -->
			</div>
        </div>
</div>
</body>

<script type="text/javascript">
   $("#rightDiv").hide();
   $("#aaa").on("click", function() {
		var $this = $(this);
		var flag = $this.attr("flag");
		if ("0" == flag) {
			$this.css("border-left", "10px solid #993366");
			$this.css("border-top", "10px solid rgba(241, 248, 241, 0)");
			$this.css("border-bottom", "10px solid rgba(0, 0, 255, 0)");
			$this.css("border-right", "0 none");
			$this.css("right", "-9px");
			$(this).attr("flag", "1");
			
			 $('#leftDiv').css("width", 600);
			 $('#leftDiv').css("height", "495");
			   parent.window.$('#contentFrame').css("width",1176);
			   parent.window.$('#contentFrame').css("height",505);
			   $('body').css("width", 1100);
			   $('#rightDiv').css("width", 340);
			   $('#rightDiv').css("height", 465); 
			   $('#leftDiv').css("height", 480);
			   $('body').css("height", 500);
			   $('#rightDiv').css("display", "block"); 
			
		} else {
			$this.css("border-right", "10px solid #993366");
			$this.css("border-top", "10px solid rgba(241, 248, 241, 0)");
			$this.css("border-bottom", "10px solid rgba(0, 0, 255, 0)");
			$this.css("border-left", "0 none");
			$this.css("right", "-4px");
			$(this).attr("flag","0");
			parent.window.$('#contentFrame').css("width",650);
			
			   $('body').css("width", 600);
			   $('#leftDiv').css("height", "495");
			   $('#leftDiv').css("width", "100%");
			   $('#rightDiv').css("display", "none");
			   
		}
	});
</script>
</html>