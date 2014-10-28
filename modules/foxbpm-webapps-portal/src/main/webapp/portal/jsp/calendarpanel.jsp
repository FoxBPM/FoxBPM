<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-us">	
	<head>
		 <jsp:include page="../header.jsp"/>
	</head>
	
	<link href='css/fullcalendar.min.css' rel='stylesheet' /> 
	<link href='css/fullcalendar.print.css' rel='stylesheet' media='print' />
	
	<script src="portal/js/calendarpanel.js"></script>
	<script src="js/plugin/fullcalendar/moment.min.js"></script>
	<script src="js/plugin/fullcalendar/fullcalendar.min.js"></script>
	<script src="js/plugin/fullcalendar/lang-all.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			pageSetUp();
			pagefunction();
		});
	</script>	
<body>
	<jsp:include page="../top.jsp"/>
	
	<!-- widget grid -->
	<section id="widget-grid" class="">
	
	<div class="row">
		<div class="col-sm-12 col-md-12 col-lg-9" style="width:100%">
	
			<!-- new widget -->
			<div class="jarviswidget jarviswidget-color-blueDark">
	
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
					<span class="widget-icon"> <i class="fa fa-calendar"></i> </span>
					<h2>日历面板 </h2>
					<div class="widget-toolbar">
						<!-- add: non-hidden - to disable auto hide -->
						<div class="btn-group">
							<button id="select_priority" class="btn dropdown-toggle btn-xs btn-default" data-toggle="dropdown">
								优先级<i class="fa fa-caret-down"></i>
							</button>
							<ul class="dropdown-menu js-status-update pull-right">
								<li>
									<a href="javascript:void(0);" id="priority_all">所有</a>
								</li>
								<li>
									<a href="javascript:void(0);" id="priority_veryhigh">非常高</a>
								</li>
								<li>
									<a href="javascript:void(0);" id="priority_high">高</a>
								</li> 
								<li>
									<a href="javascript:void(0);" id="priority_normal">一般</a>
								</li> 
								<li>
									<a href="javascript:void(0);" id="priority_low">低</a>
								</li> 
								<li>
									<a href="javascript:void(0);" id="priority_verylow">非常低</a>
								</li> 
							</ul>
						</div> &nbsp;&nbsp;&nbsp;
						<div class="btn-group">
							<button id="select_time" class="btn dropdown-toggle btn-xs btn-default" data-toggle="dropdown">
								任务创建<i class="fa fa-caret-down"></i>
							</button>
							<ul class="dropdown-menu js-status-update pull-right">
								<li>
									<a href="javascript:void(0);" id="task_createtime">任务创建</a>
								</li>
								<li>
									<a href="javascript:void(0);" id="task_duration">任务期限</a>
								</li> 
							</ul>
						</div> &nbsp;&nbsp;&nbsp;
						<div class="btn-group">
							<button id="select_view" class="btn dropdown-toggle btn-xs btn-default" data-toggle="dropdown">
								周-日程表<i class="fa fa-caret-down"></i>
							</button>
							<ul class="dropdown-menu js-status-update pull-right">
								<li>
									<a href="javascript:void(0);" id="mt">月 视图</a>
								</li>
								<li>
									<a href="javascript:void(0);" id="basicWeek">周 视图</a>
								</li>
								<li>
									<a href="javascript:void(0);" id="basicDay">日 视图</a>
								</li>
								<li>
									<a href="javascript:void(0);" id="ag">周-日程表</a>
								</li>
								<li>
									<a href="javascript:void(0);" id="td">日-日程表</a>
								</li>
							</ul>
						</div>
					</div>
				</header>
	
				<!-- widget div-->
				<div>
	
					<div class="widget-body no-padding"> 
								<div class="btn-group" style="padding-top:5px;padding-left:5px">
									<a href="javascript:void(0)" class="btn btn-default btn-xs" id="btn-prev"><i class="fa fa-chevron-left"></i></a>
									<a href="javascript:void(0)" class="btn btn-default btn-xs" id="btn-next"><i class="fa fa-chevron-right"></i></a>
								</div> 
						<div id="calendar" style="padding-top:6px"></div>
	
						<!-- end content -->
					</div>
	
				</div>
				<!-- end widget div -->
			</div>
			<!-- end widget -->
	
		</div>
	
	</div>
	<div id="eventInfoTip" >
		<div id="eventInfoTip2" class="SmallBox animated fadeInRight fast" style="top:500px;left:500px;background-color:#4C4F53;display:none">
			<div>
				<span id="eventInfoTipCreateTime"></span>
				<span id="eventInfoTipTitle"></span>
				<span id="eventInfoTipPriority"></span>
			</div>
		</div>
	</div>
	</section>
	
	<jsp:include page="../bottom.jsp"/>
</body>
</html>