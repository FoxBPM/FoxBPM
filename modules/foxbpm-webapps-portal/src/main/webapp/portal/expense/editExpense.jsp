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
	<script type="text/javascript" src="portal/taskCommand/js/foxbpmframework.js"></script>
	<script type="text/javascript" src="portal/taskCommand/js/flowCommandCompenent.js"></script>
	<script type="text/javascript" src="portal/taskCommand/js/flowCommandHandler.js"></script>
	
	
	<script type="text/javascript">
	 parent.window.$('#contentFrame').css("width",650);
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
		initChatMsg();
	});
	
	function initChatMsg(){
		var msgUrl = _serviceUrl+"social";
		alert(msgUrl);
		$.ajax({
	        type: "get",//使用get方法访问后台
	        dataType: "json",//返回json格式的数据
	        url: msgUrl,//要访问的后台地址
	        data:{taskId:"",msgType:"findAll",userId:"",loginTime:new Date},
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
	
<body style="width:600px" class="keBody">

<div class="jarviswidget" id="wid-id-8" data-widget-editbutton="false" data-widget-custombutton="false"  style="margin:0 0 0 0">
	<header>
		<span class="widget-icon"> <i class="fa fa-edit"></i>
		</span>
		<h2>报销单据</h2>
	</header>
	<!-- widget div-->
	<div id="leftDiv" style="float:left;width:100%;height:100%; border:1px solid red;">
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

				<footer id="toolbar">
				<button type='button' id="closeModal" class='btn btn-primary'>关闭</button>
				</footer>
				<input type="hidden" name="flowCommandInfo" id="flowCommandInfo">
			</form>
		<a id="aaa" flag="0"
					style="position: absolute; right: -5; top: 50%; z-index: 1; cursor: pointer; border-top: 10px solid rgba(241, 248, 241, 0); border-right: 10px solid #993366; border-bottom: 10px solid rgba(0, 0, 255, 0);"></a>
		
		</div>
	</div>
        <div id="rightDiv" style="float:left;display:none;height:100%; border:1px solid red;border-left-width:0px;padding-left: 0px;padding-top: 0px;">
 
			<!--效果html开始-->
			    <div class="content" style="background-color:white;height: 480px;width: 500px;">
			        <div class="chatBox"  style="margin-left: 0px;width: 500px">
			        <div class="chatLeft" style="width: 339px;">
			                <div class="chat01">
			                    <div class="chat01_title">
			                        <ul class="talkTo">
			                            <li><a href="javascript:;">王旭</a></li></ul>
			                    </div>
			                    <div class="chat01_content" style="height:294px">
			                        <div class="message_box mes1">
			                        </div>
			                        <div class="message_box mes2">
			                        </div>
			                        <div class="message_box mes3" style="display: block;">
			                        </div>
			                        <div class="message_box mes4">
			                        </div>
			                        <div class="message_box mes5">
			                        </div>
			                        <div class="message_box mes6">
			                        </div>
			                        <div class="message_box mes7">
			                        </div>
			                        <div class="message_box mes8">
			                        </div>
			                        <div class="message_box mes9">
			                        </div>
			                        <div class="message_box mes10">
			                        </div>
			                    </div>
			                </div>
			                <div class="chat02">
			                    <div class="chat02_title">
			                        <a class="chat02_title_btn ctb01" href="javascript:;"></a><a class="chat02_title_btn ctb02"
			                            href="javascript:;" title="选择文件">
			                            <embed width="15" height="16" flashvars="swfid=2556975203&amp;maxSumSize=50&amp;maxFileSize=50&amp;maxFileNum=1&amp;multiSelect=0&amp;uploadAPI=http%3A%2F%2Fupload.api.weibo.com%2F2%2Fmss%2Fupload.json%3Fsource%3D209678993%26tuid%3D1887188824&amp;initFun=STK.webim.ui.chatWindow.msgToolBar.upload.initFun&amp;sucFun=STK.webim.ui.chatWindow.msgToolBar.upload.sucFun&amp;errFun=STK.webim.ui.chatWindow.msgToolBar.upload.errFun&amp;beginFun=STK.webim.ui.chatWindow.msgToolBar.upload.beginFun&amp;showTipFun=STK.webim.ui.chatWindow.msgToolBar.upload.showTipFun&amp;hiddenTipFun=STK.webim.ui.chatWindow.msgToolBar.upload.hiddenTipFun&amp;areaInfo=0-16|12-16&amp;fExt=*.jpg;*.gif;*.jpeg;*.png|*&amp;fExtDec=选择图片|选择文件"
			                                data="upload.swf" wmode="transparent" bgcolor="" allowscriptaccess="always" allowfullscreen="true"
			                                scale="noScale" menu="false" type="application/x-shockwave-flash" src="http://service.weibo.com/staticjs/tools/upload.swf?v=36c9997f1313d1c4"
			                                id="swf_3140">
			                        </a><a class="chat02_title_btn ctb03" href="javascript:;" title="选择附件">
			                            <embed width="15" height="16" flashvars="swfid=2556975203&amp;maxSumSize=50&amp;maxFileSize=50&amp;maxFileNum=1&amp;multiSelect=0&amp;uploadAPI=http%3A%2F%2Fupload.api.weibo.com%2F2%2Fmss%2Fupload.json%3Fsource%3D209678993%26tuid%3D1887188824&amp;initFun=STK.webim.ui.chatWindow.msgToolBar.upload.initFun&amp;sucFun=STK.webim.ui.chatWindow.msgToolBar.upload.sucFun&amp;errFun=STK.webim.ui.chatWindow.msgToolBar.upload.errFun&amp;beginFun=STK.webim.ui.chatWindow.msgToolBar.upload.beginFun&amp;showTipFun=STK.webim.ui.chatWindow.msgToolBar.upload.showTipFun&amp;hiddenTipFun=STK.webim.ui.chatWindow.msgToolBar.upload.hiddenTipFun&amp;areaInfo=0-16|12-16&amp;fExt=*.jpg;*.gif;*.jpeg;*.png|*&amp;fExtDec=选择图片|选择文件"
			                                data="upload.swf" wmode="transparent" bgcolor="" allowscriptaccess="always" allowfullscreen="true"
			                                scale="noScale" menu="false" type="application/x-shockwave-flash" src="http://service.weibo.com/staticjs/tools/upload.swf?v=36c9997f1313d1c4"
			                                id="swf_3140">
			                        </a>
			                        <label class="chat02_title_t">
			                            <a href="chat.htm" target="_blank">聊天记录</a></label>
			                        <div class="wl_faces_box">
			                            <div class="wl_faces_content">
			                                <div class="title">
			                                    <ul>
			                                        <li class="title_name">常用表情</li><li class="wl_faces_close"><span>&nbsp;</span></li></ul>
			                                </div>
			                                <div class="wl_faces_main">
			                                    <ul>
			                                        <li><a href="javascript:;">
			                                            <img src="img/emo_01.gif" /></a></li><li><a href="javascript:;">
			                                                <img src="img/emo_02.gif" /></a></li><li><a href="javascript:;">
			                                                    <img src="img/emo_03.gif" /></a></li>
			                                        <li><a href="javascript:;">
			                                            <img src="img/emo_04.gif" /></a></li><li><a href="javascript:;">
			                                                <img src="img/emo_05.gif" /></a></li><li><a href="javascript:;">
			                                                    <img src="img/emo_06.gif" /></a></li>
			                                        <li><a href="javascript:;">
			                                            <img src="img/emo_07.gif" /></a></li><li><a href="javascript:;">
			                                                <img src="img/emo_08.gif" /></a></li><li><a href="javascript:;">
			                                                    <img src="img/emo_09.gif" /></a></li>
			                                        <li><a href="javascript:;">
			                                            <img src="img/emo_10.gif" /></a></li><li><a href="javascript:;">
			                                                <img src="img/emo_11.gif" /></a></li><li><a href="javascript:;">
			                                                    <img src="img/emo_12.gif" /></a></li>
			                                        <li><a href="javascript:;">
			                                            <img src="img/emo_13.gif" /></a></li><li><a href="javascript:;">
			                                                <img src="img/emo_14.gif" /></a></li><li><a href="javascript:;">
			                                                    <img src="img/emo_15.gif" /></a></li>
			                                        <li><a href="javascript:;">
			                                            <img src="img/emo_16.gif" /></a></li><li><a href="javascript:;">
			                                                <img src="img/emo_17.gif" /></a></li><li><a href="javascript:;">
			                                                    <img src="img/emo_18.gif" /></a></li>
			                                        <li><a href="javascript:;">
			                                            <img src="img/emo_19.gif" /></a></li><li><a href="javascript:;">
			                                                <img src="img/emo_20.gif" /></a></li><li><a href="javascript:;">
			                                                    <img src="img/emo_21.gif" /></a></li>
			                                        <li><a href="javascript:;">
			                                            <img src="img/emo_22.gif" /></a></li><li><a href="javascript:;">
			                                                <img src="img/emo_23.gif" /></a></li><li><a href="javascript:;">
			                                                    <img src="img/emo_24.gif" /></a></li>
			                                        <li><a href="javascript:;">
			                                            <img src="img/emo_25.gif" /></a></li><li><a href="javascript:;">
			                                                <img src="img/emo_26.gif" /></a></li><li><a href="javascript:;">
			                                                    <img src="img/emo_27.gif" /></a></li>
			                                        <li><a href="javascript:;">
			                                            <img src="img/emo_28.gif" /></a></li><li><a href="javascript:;">
			                                                <img src="img/emo_29.gif" /></a></li><li><a href="javascript:;">
			                                                    <img src="img/emo_30.gif" /></a></li>
			                                        <li><a href="javascript:;">
			                                            <img src="img/emo_31.gif" /></a></li><li><a href="javascript:;">
			                                                <img src="img/emo_32.gif" /></a></li><li><a href="javascript:;">
			                                                    <img src="img/emo_33.gif" /></a></li>
			                                        <li><a href="javascript:;">
			                                            <img src="img/emo_34.gif" /></a></li><li><a href="javascript:;">
			                                                <img src="img/emo_35.gif" /></a></li><li><a href="javascript:;">
			                                                    <img src="img/emo_36.gif" /></a></li>
			                                        <li><a href="javascript:;">
			                                            <img src="img/emo_37.gif" /></a></li><li><a href="javascript:;">
			                                                <img src="img/emo_38.gif" /></a></li><li><a href="javascript:;">
			                                                    <img src="img/emo_39.gif" /></a></li>
			                                        <li><a href="javascript:;">
			                                            <img src="img/emo_40.gif" /></a></li><li><a href="javascript:;">
			                                                <img src="img/emo_41.gif" /></a></li><li><a href="javascript:;">
			                                                    <img src="img/emo_42.gif" /></a></li>
			                                        <li><a href="javascript:;">
			                                            <img src="img/emo_43.gif" /></a></li><li><a href="javascript:;">
			                                                <img src="img/emo_44.gif" /></a></li><li><a href="javascript:;">
			                                                    <img src="img/emo_45.gif" /></a></li>
			                                        <li><a href="javascript:;">
			                                            <img src="img/emo_46.gif" /></a></li><li><a href="javascript:;">
			                                                <img src="img/emo_47.gif" /></a></li><li><a href="javascript:;">
			                                                    <img src="img/emo_48.gif" /></a></li>
			                                        <li><a href="javascript:;">
			                                            <img src="img/emo_49.gif" /></a></li><li><a href="javascript:;">
			                                                <img src="img/emo_50.gif" /></a></li><li><a href="javascript:;">
			                                                    <img src="img/emo_51.gif" /></a></li>
			                                        <li><a href="javascript:;">
			                                            <img src="img/emo_52.gif" /></a></li><li><a href="javascript:;">
			                                                <img src="img/emo_53.gif" /></a></li><li><a href="javascript:;">
			                                                    <img src="img/emo_54.gif" /></a></li>
			                                        <li><a href="javascript:;">
			                                            <img src="img/emo_55.gif" /></a></li><li><a href="javascript:;">
			                                                <img src="img/emo_56.gif" /></a></li><li><a href="javascript:;">
			                                                    <img src="img/emo_57.gif" /></a></li>
			                                        <li><a href="javascript:;">
			                                            <img src="img/emo_58.gif" /></a></li><li><a href="javascript:;">
			                                                <img src="img/emo_59.gif" /></a></li><li><a href="javascript:;">
			                                                    <img src="img/emo_60.gif" /></a></li>
			                                    </ul>
			                                </div>
			                            </div>
			                            <div class="wlf_icon">
			                            </div>
			                        </div>
			                    </div>
			                    <div class="chat02_content">
			                        <textarea id="textarea" style="width: 338px;"></textarea>
			                    </div>
			                    <div class="chat02_bar">
			                        <ul>
			                            <li style="left: 2px; top: 10px; padding-left: 0px;">任务沟通中心</li>
			                            <li style="right: 5px; top: 5px;"><a href="javascript:;">
			                                <img src="img/send_btn.jpg"></a></li>
			                        </ul>
			                    </div>
			                </div>
			            </div>
			            <div class="chatRight" style="width: 158px;">
			                <div class="chat03">
			                    <div class="chat03_title">
			                        <label class="chat03_title_t">
			                            成员列表</label>
			                    </div>
			                    <div class="chat03_content">
			                        <ul>
			                            <li>
			                                <label class="online">
			                                </label>
			                                <a href="javascript:;">
			                                    <img src="img/head/2013.jpg"></a><a href="javascript:;" class="chat03_name">刘秀</a>
			                            </li>
			                            <li>
			                                <label class="offline">
			                                </label>
			                                <a href="javascript:;">
			                                    <img src="img/head/2014.jpg"></a><a href="javascript:;" class="chat03_name">陈诚</a>
			                            </li>
			                            
			                        </ul>
			                    </div>
			                </div>
			            </div>
			            <div style="clear: both;">
			            </div>
			        </div>
			    </div>
			<!--效果html结束-->
        </div>
</div>
</body>
<link rel="stylesheet" type="text/css" href="css/chat.css" />
<script type="text/javascript" src="js/chat.js"></script>
<script type="text/javascript">
   
   
   $("#aaa").on("click", function() {
		var $this = $(this);
		var flag = $this.attr("flag");
		if ("0" == flag) {
			$this.css("border-left", "10px solid #993366");
			$this.css("border-top", "10px solid rgba(241, 248, 241, 0)");
			$this.css("border-bottom", "10px solid rgba(0, 0, 255, 0)");
			$this.css("border-right", "0 none");
			$this.css("right", "-9px");
			 $('#leftDiv').css("width", 600);
			   parent.window.$('#contentFrame').css("width",1100);
			   $('body').css("width", 1100);
			   $('#rightDiv').css("width", 340);
			   $('#rightDiv').css("display", "block");
			   $(this).attr("flag","1");
			$(this).attr("flag", "1");
		} else {
			$this.css("border-right", "10px solid #993366");
			$this.css("border-top", "10px solid rgba(241, 248, 241, 0)");
			$this.css("border-bottom", "10px solid rgba(0, 0, 255, 0)");
			$this.css("border-left", "0 none");
			$this.css("right", "-4px");
			parent.window.$('#contentFrame').css("width",650);
			   $('body').css("width", 600);
			   $('#leftDiv').css("width", "100%");
			   $('#rightDiv').css("display", "none");
			   $(this).attr("flag","0");
		}
	});

</script>
</html>