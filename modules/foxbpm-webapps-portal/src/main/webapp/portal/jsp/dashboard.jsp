<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en-us">
<!-- 加载头部信箱 -->
<head>
<jsp:include page="../header.jsp" />
</head>
<body class="">
	<jsp:include page="../top.jsp" />
	
	<div class="row">
	<div class="col-xs-12 col-sm-7 col-md-7 col-lg-4">
		<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa fa-home"></i> Dashboard <span>> My Dashboard</span></h1>
	</div>
	<div class="col-xs-12 col-sm-5 col-md-5 col-lg-8">
		<ul id="sparks" class="">
			<li class="sparks-info">
				<h5> My Income <span class="txt-color-blue">$47,171</span></h5>
				<div class="sparkline txt-color-blue hidden-mobile hidden-md hidden-sm">
					1300, 1877, 2500, 2577, 2000, 2100, 3000, 2700, 3631, 2471, 2700, 3631, 2471
				</div>
			</li>
			<li class="sparks-info">
				<h5> Site Traffic <span class="txt-color-purple"><i class="fa fa-arrow-circle-up"></i>&nbsp;45%</span></h5>
				<div class="sparkline txt-color-purple hidden-mobile hidden-md hidden-sm">
					110,150,300,130,400,240,220,310,220,300, 270, 210
				</div>
			</li>
			<li class="sparks-info">
				<h5> Site Orders <span class="txt-color-greenDark"><i class="fa fa-shopping-cart"></i>&nbsp;2447</span></h5>
				<div class="sparkline txt-color-greenDark hidden-mobile hidden-md hidden-sm">
					110,150,300,130,400,240,220,310,220,300, 270, 210
				</div>
			</li>
		</ul>
	</div>
</div>
<!-- widget grid -->
<section id="widget-grid" class="">

	<!-- row -->
	<div class="row">
		<article class="col-sm-12">
			<!-- new widget -->
			<div class="jarviswidget" id="wid-id-0" data-widget-togglebutton="false" data-widget-editbutton="false" data-widget-fullscreenbutton="false" data-widget-colorbutton="false" data-widget-deletebutton="false">
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
					<span class="widget-icon"> <i class="glyphicon glyphicon-stats txt-color-darken"></i> </span>
					<h2>Live Feeds </h2>

					<ul class="nav nav-tabs pull-right in" id="myTab">
						<li class="active">
							<a data-toggle="tab" href="#s1"><i class="fa fa-clock-o"></i> <span class="hidden-mobile hidden-tablet">Live Stats</span></a>
						</li>

						<li>
							<a data-toggle="tab" href="#s2"><i class="fa fa-facebook"></i> <span class="hidden-mobile hidden-tablet">Social Network</span></a>
						</li>

						<li>
							<a data-toggle="tab" href="#s3"><i class="fa fa-dollar"></i> <span class="hidden-mobile hidden-tablet">Revenue</span></a>
						</li>
					</ul>

				</header>

				<!-- widget div-->
				<div class="no-padding">
					<!-- widget edit box -->
					<div class="jarviswidget-editbox">

						test
					</div>
					<!-- end widget edit box -->

					<div class="widget-body">
						<!-- content -->
						<div id="myTabContent" class="tab-content">
							<div class="tab-pane fade active in padding-10 no-padding-bottom" id="s1">
								<div class="row no-space">
									<div class="col-xs-12 col-sm-12 col-md-8 col-lg-8">
										<span class="demo-liveupdate-1"> <span class="onoffswitch-title">Live switch</span> <span class="onoffswitch">
												<input type="checkbox" name="start_interval" class="onoffswitch-checkbox" id="start_interval">
												<label class="onoffswitch-label" for="start_interval"> 
													<span class="onoffswitch-inner" data-swchon-text="ON" data-swchoff-text="OFF"></span> 
													<span class="onoffswitch-switch"></span> </label> </span> </span>
										<div id="updating-chart" class="chart-large txt-color-blue"></div>

									</div>
									<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4 show-stats">

										<div class="row">
											<div class="col-xs-6 col-sm-6 col-md-12 col-lg-12"> <span class="text"> My Tasks <span class="pull-right">130/200</span> </span>
												<div class="progress">
													<div class="progress-bar bg-color-blueDark" style="width: 65%;"></div>
												</div> </div>
											<div class="col-xs-6 col-sm-6 col-md-12 col-lg-12"> <span class="text"> Transfered <span class="pull-right">440 GB</span> </span>
												<div class="progress">
													<div class="progress-bar bg-color-blue" style="width: 34%;"></div>
												</div> </div>
											<div class="col-xs-6 col-sm-6 col-md-12 col-lg-12"> <span class="text"> Bugs Squashed<span class="pull-right">77%</span> </span>
												<div class="progress">
													<div class="progress-bar bg-color-blue" style="width: 77%;"></div>
												</div> </div>
											<div class="col-xs-6 col-sm-6 col-md-12 col-lg-12"> <span class="text"> User Testing <span class="pull-right">7 Days</span> </span>
												<div class="progress">
													<div class="progress-bar bg-color-greenLight" style="width: 84%;"></div>
												</div> </div>

											<span class="show-stat-buttons"> <span class="col-xs-12 col-sm-6 col-md-6 col-lg-6"> <a href="javascript:void(0);" class="btn btn-default btn-block hidden-xs">Generate PDF</a> </span> <span class="col-xs-12 col-sm-6 col-md-6 col-lg-6"> <a href="javascript:void(0);" class="btn btn-default btn-block hidden-xs">Report a bug</a> </span> </span>

										</div>

									</div>
								</div>

								<div class="show-stat-microcharts">
									<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3">

										<div class="easy-pie-chart txt-color-orangeDark" data-percent="33" data-pie-size="50">
											<span class="percent percent-sign">35</span>
										</div>
										<span class="easy-pie-title"> Server Load <i class="fa fa-caret-up icon-color-bad"></i> </span>
										<ul class="smaller-stat hidden-sm pull-right">
											<li>
												<span class="label bg-color-greenLight"><i class="fa fa-caret-up"></i> 97%</span>
											</li>
											<li>
												<span class="label bg-color-blueLight"><i class="fa fa-caret-down"></i> 44%</span>
											</li>
										</ul>
										<div class="sparkline txt-color-greenLight hidden-sm hidden-md pull-right" data-sparkline-type="line" data-sparkline-height="33px" data-sparkline-width="70px" data-fill-color="transparent">
											130, 187, 250, 257, 200, 210, 300, 270, 363, 247, 270, 363, 247
										</div>
									</div>
									<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3">
										<div class="easy-pie-chart txt-color-greenLight" data-percent="78.9" data-pie-size="50">
											<span class="percent percent-sign">78.9 </span>
										</div>
										<span class="easy-pie-title"> Disk Space <i class="fa fa-caret-down icon-color-good"></i></span>
										<ul class="smaller-stat hidden-sm pull-right">
											<li>
												<span class="label bg-color-blueDark"><i class="fa fa-caret-up"></i> 76%</span>
											</li>
											<li>
												<span class="label bg-color-blue"><i class="fa fa-caret-down"></i> 3%</span>
											</li>
										</ul>
										<div class="sparkline txt-color-blue hidden-sm hidden-md pull-right" data-sparkline-type="line" data-sparkline-height="33px" data-sparkline-width="70px" data-fill-color="transparent">
											257, 200, 210, 300, 270, 363, 130, 187, 250, 247, 270, 363, 247
										</div>
									</div>
									<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3">
										<div class="easy-pie-chart txt-color-blue" data-percent="23" data-pie-size="50">
											<span class="percent percent-sign">23 </span>
										</div>
										<span class="easy-pie-title"> Transfered <i class="fa fa-caret-up icon-color-good"></i></span>
										<ul class="smaller-stat hidden-sm pull-right">
											<li>
												<span class="label bg-color-darken">10GB</span>
											</li>
											<li>
												<span class="label bg-color-blueDark"><i class="fa fa-caret-up"></i> 10%</span>
											</li>
										</ul>
										<div class="sparkline txt-color-darken hidden-sm hidden-md pull-right" data-sparkline-type="line" data-sparkline-height="33px" data-sparkline-width="70px" data-fill-color="transparent">
											200, 210, 363, 247, 300, 270, 130, 187, 250, 257, 363, 247, 270
										</div>
									</div>
									<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3">
										<div class="easy-pie-chart txt-color-darken" data-percent="36" data-pie-size="50">
											<span class="percent degree-sign">36 <i class="fa fa-caret-up"></i></span>
										</div>
										<span class="easy-pie-title"> Temperature <i class="fa fa-caret-down icon-color-good"></i></span>
										<ul class="smaller-stat hidden-sm pull-right">
											<li>
												<span class="label bg-color-red"><i class="fa fa-caret-up"></i> 124</span>
											</li>
											<li>
												<span class="label bg-color-blue"><i class="fa fa-caret-down"></i> 40 F</span>
											</li>
										</ul>
										<div class="sparkline txt-color-red hidden-sm hidden-md pull-right" data-sparkline-type="line" data-sparkline-height="33px" data-sparkline-width="70px" data-fill-color="transparent">
											2700, 3631, 2471, 2700, 3631, 2471, 1300, 1877, 2500, 2577, 2000, 2100, 3000
										</div>
									</div>
								</div>

							</div>
							<!-- end s1 tab pane -->

							<div class="tab-pane fade" id="s2">
								<div class="widget-body-toolbar bg-color-white">

									<form class="form-inline" role="form">

										<div class="form-group">
											<label class="sr-only" for="s123">Show From</label>
											<input type="email" class="form-control input-sm" id="s123" placeholder="Show From">
										</div>
										<div class="form-group">
											<input type="email" class="form-control input-sm" id="s124" placeholder="To">
										</div>

										<div class="btn-group hidden-phone pull-right hidden-xs">
											<a class="btn dropdown-toggle btn-xs btn-default" data-toggle="dropdown"><i class="fa fa-cog"></i> More <span class="caret"> </span> </a>
											<ul class="dropdown-menu pull-right">
												<li>
													<a href="javascript:void(0);"><i class="fa fa-file-text-alt"></i> Export to PDF</a>
												</li>
												<li>
													<a href="javascript:void(0);"><i class="fa fa-question-sign"></i> Help</a>
												</li>
											</ul>
										</div>

									</form>

								</div>
								<div class="padding-10">
									<div id="statsChart" class="chart-large has-legend-unique"></div>
								</div>

							</div>
							<!-- end s2 tab pane -->

							<div class="tab-pane fade" id="s3">

								<div class="widget-body-toolbar bg-color-white smart-form" id="rev-toggles">

									<div class="inline-group">

										<label for="gra-0" class="checkbox">
											<input type="checkbox" name="gra-0" id="gra-0" checked="checked">
											<i></i> Target </label>
										<label for="gra-1" class="checkbox">
											<input type="checkbox" name="gra-1" id="gra-1" checked="checked">
											<i></i> Actual </label>
										<label for="gra-2" class="checkbox">
											<input type="checkbox" name="gra-2" id="gra-2" checked="checked">
											<i></i> Signups </label>
									</div>

									<div class="btn-group hidden-phone pull-right hidden-xs">
										<a class="btn dropdown-toggle btn-xs btn-default" data-toggle="dropdown"><i class="fa fa-cog"></i> More <span class="caret"> </span> </a>
										<ul class="dropdown-menu pull-right">
											<li>
												<a href="javascript:void(0);"><i class="fa fa-file-text-alt"></i> Export to PDF</a>
											</li>
											<li>
												<a href="javascript:void(0);"><i class="fa fa-question-sign"></i> Help</a>
											</li>
										</ul>
									</div>

								</div>

								<div class="padding-10">
									<div id="flotcontainer" class="chart-large has-legend-unique"></div>
								</div>
							</div>
							<!-- end s3 tab pane -->
						</div>

						<!-- end content -->
					</div>

				</div>
				<!-- end widget div -->
			</div>
			<!-- end widget -->

		</article>
	</div>

	<!-- end row -->

	<!-- row -->

	<div class="row">

		<article class="col-sm-12 col-md-12 col-lg-6">
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
											<a href="javascript:void(0);" id="priority_high">高</a>
										</li>
										<li>
											<a href="javascript:void(0);" id="priority_mid">中</a>
										</li> 
										<li>
											<a href="javascript:void(0);" id="priority_low">低</a>
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
			<!-- end widget -->

		</article>

		<article class="col-sm-12 col-md-12 col-lg-6">

			<!-- new widget -->
			<div class="jarviswidget jarviswidget-color-blue" id="wid-id-4" data-widget-editbutton="false" data-widget-colorbutton="false">

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
					<span class="widget-icon"> <i class="fa fa-check txt-color-white"></i> </span>
					<h2> ToDo's </h2>
					<!-- <div class="widget-toolbar">
					add: non-hidden - to disable auto hide

					</div>-->
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

					<div class="widget-body no-padding smart-form">
						<!-- content goes here -->
						<h5 class="todo-group-title"><i class="fa fa-warning"></i> Critical Tasks (<small class="num-of-tasks">1</small>)</h5>
						<ul id="sortable1" class="todo">
							<li>
								<span class="handle"> <label class="checkbox">
										<input type="checkbox" name="checkbox-inline">
										<i></i> </label> </span>
								<p>
									<strong>Ticket #17643</strong> - Hotfix for WebApp interface issue [<a href="javascript:void(0);" class="font-xs">More Details</a>] <span class="text-muted">Sea deep blessed bearing under darkness from God air living isn't. </span>
									<span class="date">Jan 1, 2014</span>
								</p>
							</li>
						</ul>
						<h5 class="todo-group-title"><i class="fa fa-exclamation"></i> Important Tasks (<small class="num-of-tasks">3</small>)</h5>
						<ul id="sortable2" class="todo">
							<li>
								<span class="handle"> <label class="checkbox">
										<input type="checkbox" name="checkbox-inline">
										<i></i> </label> </span>
								<p>
									<strong>Ticket #1347</strong> - Inbox email is being sent twice <small>(bug fix)</small> [<a href="javascript:void(0);" class="font-xs">More Details</a>] <span class="date">Nov 22, 2013</span>
								</p>
							</li>
							<li>
								<span class="handle"> <label class="checkbox">
										<input type="checkbox" name="checkbox-inline">
										<i></i> </label> </span>
								<p>
									<strong>Ticket #1314</strong> - Call customer support re: Issue <a href="javascript:void(0);" class="font-xs">#6134</a><small> (code review)</small>
									<span class="date">Nov 22, 2013</span>
								</p>
							</li>
							<li>
								<span class="handle"> <label class="checkbox">
										<input type="checkbox" name="checkbox-inline">
										<i></i> </label> </span>
								<p>
									<strong>Ticket #17643</strong> - Hotfix for WebApp interface issue [<a href="javascript:void(0);" class="font-xs">More Details</a>] <span class="text-muted">Sea deep blessed bearing under darkness from God air living isn't. </span>
									<span class="date">Jan 1, 2014</span>
								</p>
							</li>
						</ul>

						<h5 class="todo-group-title"><i class="fa fa-check"></i> Completed Tasks (<small class="num-of-tasks">1</small>)</h5>
						<ul id="sortable3" class="todo">
							<li class="complete">
								<span class="handle"> <label class="checkbox state-disabled" style="display:none">
										<input type="checkbox" name="checkbox-inline" checked="checked" disabled="disabled">
										<i></i> </label> </span>
								<p>
									<strong>Ticket #17643</strong> - Hotfix for WebApp interface issue [<a href="javascript:void(0);" class="font-xs">More Details</a>] <span class="text-muted">Sea deep blessed bearing under darkness from God air living isn't. </span>
									<span class="date">Jan 1, 2014</span>
								</p>
							</li>
						</ul>

						<!-- end content -->
					</div>

				</div>
				<!-- end widget div -->
			</div>
			<!-- end widget -->

		</article>

	</div>

	<!-- end row -->

<div class="modal fade " id="remoteModal" tabindex="-1" role="dialog" aria-labelledby="remoteModalLabel" aria-hidden="true">  
    <div class="modal-dialog" style="width:800px">  
      	<iframe id="contentFrame" class="col-sm-10 col-md-12 col-lg-12" style="border:0px; height:500px;"></iframe>
    </div>  
</div>   
<div id="eventInfoTip">
	<div id="eventInfoTip2" class="SmallBox animated fadeInRight fast" style="top:500px;left:500px;background-color:#4C4F53">
		<div>
			<span id="eventInfoTipCreateTime"></span>
			<span id="eventInfoTipTitle"></span>
			<span id="eventInfoTipPriority"></span>
		</div>
	</div>
</div>
</section>
<!-- end widget grid -->

<link href='css/fullcalendar.min.css' rel='stylesheet' /> 
<link href='css/fullcalendar.print.css' rel='stylesheet' media='print' />

<script type="text/javascript">
	/* DO NOT REMOVE : GLOBAL FUNCTIONS!
	 *
	 * pageSetUp(); WILL CALL THE FOLLOWING FUNCTIONS
	 *
	 * // activate tooltips
	 * $("[rel=tooltip]").tooltip();
	 *
	 * // activate popovers
	 * $("[rel=popover]").popover();
	 *
	 * // activate popovers with hover states
	 * $("[rel=popover-hover]").popover({ trigger: "hover" });
	 *
	 * // activate inline charts
	 * runAllCharts();
	 *
	 * // setup widgets
	 * setup_widgets_desktop();
	 *
	 * // run form elements
	 * runAllForms();
	 *
	 ********************************
	 *
	 * pageSetUp() is needed whenever you load a page.
	 * It initializes and checks for all basic elements of the page
	 * and makes rendering easier.
	 *
	 */

	pageSetUp();
	
	/*
	 * PAGE RELATED SCRIPTS
	 */

	// pagefunction
	
	var pagefunction = function() {
			
		$(".js-status-update a").click(function () {
		    var selText = $(this).text();
		    var $this = $(this);
		    $this.parents('.btn-group').find('.dropdown-toggle').html(selText + ' <span class="caret"></span>');
		    $this.parents('.dropdown-menu').find('li').removeClass('active');
		    $this.parent().addClass('active');
		});
		
		/*
		 * TODO: add a way to add more todo's to list
		 */
		
		// initialize sortable
		
	    $("#sortable1, #sortable2").sortable({
	        handle: '.handle',
	        connectWith: ".todo",
	        update: countTasks
	    }).disableSelection();
		
		
		// check and uncheck
		$('.todo .checkbox > input[type="checkbox"]').click(function () {
		    var $this = $(this).parent().parent().parent();
		
		    if ($(this).prop('checked')) {
		        $this.addClass("complete");
		
		        // remove this if you want to undo a check list once checked
		        //$(this).attr("disabled", true);
		        $(this).parent().hide();
		
		        // once clicked - add class, copy to memory then remove and add to sortable3
		        $this.slideUp(500, function () {
		            $this.clone().prependTo("#sortable3").effect("highlight", {}, 800);
		            $this.remove();
		            countTasks();
		        });
		    } else {
		        // insert undo code here...
		    }
		
		})
		// count tasks
		function countTasks() {
		
		    $('.todo-group-title').each(function () {
		        var $this = $(this);
		        $this.find(".num-of-tasks").text($this.next().find("li").size());
		    });
		
		}
		
		/*
		 * RUN PAGE GRAPHS
		 */
		
		// Load FLOAT dependencies (related to page)
		loadScript("js/plugin/flot/jquery.flot.cust.min.js", loadFlotResize);
		
		function loadFlotResize() {
		    loadScript("js/plugin/flot/jquery.flot.resize.min.js", loadFlotToolTip);
		}
		
		function loadFlotToolTip() {
		    loadScript("js/plugin/flot/jquery.flot.tooltip.min.js", generatePageGraphs);
		}
		
		function generatePageGraphs() {
		
		    /* TAB 1: UPDATING CHART */
		    // For the demo we use generated data, but normally it would be coming from the server
		
		    var data = [],
		        totalPoints = 200,
		        $UpdatingChartColors = $("#updating-chart").css('color');
		
		    function getRandomData() {
		        if (data.length > 0)
		            data = data.slice(1);
		
		        // do a random walk
		        while (data.length < totalPoints) {
		            var prev = data.length > 0 ? data[data.length - 1] : 50;
		            var y = prev + Math.random() * 10 - 5;
		            if (y < 0)
		                y = 0;
		            if (y > 100)
		                y = 100;
		            data.push(y);
		        }
		
		        // zip the generated y values with the x values
		        var res = [];
		        for (var i = 0; i < data.length; ++i)
		            res.push([i, data[i]])
		        return res;
		    }
		
		    // setup control widget
		    var updateInterval = 1500;
		    $("#updating-chart").val(updateInterval).change(function () {
		
		        var v = $(this).val();
		        if (v && !isNaN(+v)) {
		            updateInterval = +v;
		            $(this).val("" + updateInterval);
		        }
		
		    });
		
		    // setup plot
		    var options = {
		        yaxis: {
		            min: 0,
		            max: 100
		        },
		        xaxis: {
		            min: 0,
		            max: 100
		        },
		        colors: [$UpdatingChartColors],
		        series: {
		            lines: {
		                lineWidth: 1,
		                fill: true,
		                fillColor: {
		                    colors: [{
		                        opacity: 0.4
		                    }, {
		                        opacity: 0
		                    }]
		                },
		                steps: false
		
		            }
		        }
		    };
		
		    var plot = $.plot($("#updating-chart"), [getRandomData()], options);
		
		    /* live switch */
		    $('input[type="checkbox"]#start_interval').click(function () {
		        if ($(this).prop('checked')) {
		            $on = true;
		            updateInterval = 1500;
		            update();
		        } else {
		            clearInterval(updateInterval);
		            $on = false;
		        }
		    });
		
		    function update() {
		        if ($on == true) {
		            plot.setData([getRandomData()]);
		            plot.draw();
		            setTimeout(update, updateInterval);
		
		        } else {
		            clearInterval(updateInterval)
		        }
		
		    }
		
		    var $on = false;
		
		    /*end updating chart*/
		
		    /* TAB 2: Social Network  */
		
		    $(function () {
		        // jQuery Flot Chart
		        var twitter = [
		            [1, 27],
		            [2, 34],
		            [3, 51],
		            [4, 48],
		            [5, 55],
		            [6, 65],
		            [7, 61],
		            [8, 70],
		            [9, 65],
		            [10, 75],
		            [11, 57],
		            [12, 59],
		            [13, 62]
		        ],
		            facebook = [
		                [1, 25],
		                [2, 31],
		                [3, 45],
		                [4, 37],
		                [5, 38],
		                [6, 40],
		                [7, 47],
		                [8, 55],
		                [9, 43],
		                [10, 50],
		                [11, 47],
		                [12, 39],
		                [13, 47]
		            ],
		            data = [{
		                label: "Twitter",
		                data: twitter,
		                lines: {
		                    show: true,
		                    lineWidth: 1,
		                    fill: true,
		                    fillColor: {
		                        colors: [{
		                            opacity: 0.1
		                        }, {
		                            opacity: 0.13
		                        }]
		                    }
		                },
		                points: {
		                    show: true
		                }
		            }, {
		                label: "Facebook",
		                data: facebook,
		                lines: {
		                    show: true,
		                    lineWidth: 1,
		                    fill: true,
		                    fillColor: {
		                        colors: [{
		                            opacity: 0.1
		                        }, {
		                            opacity: 0.13
		                        }]
		                    }
		                },
		                points: {
		                    show: true
		                }
		            }];
		
		        var options = {
		            grid: {
		                hoverable: true
		            },
		            colors: ["#568A89", "#3276B1"],
		            tooltip: true,
		            tooltipOpts: {
		                //content : "Value <b>$x</b> Value <span>$y</span>",
		                defaultTheme: false
		            },
		            xaxis: {
		                ticks: [
		                    [1, "JAN"],
		                    [2, "FEB"],
		                    [3, "MAR"],
		                    [4, "APR"],
		                    [5, "MAY"],
		                    [6, "JUN"],
		                    [7, "JUL"],
		                    [8, "AUG"],
		                    [9, "SEP"],
		                    [10, "OCT"],
		                    [11, "NOV"],
		                    [12, "DEC"],
		                    [13, "JAN+1"]
		                ]
		            },
		            yaxes: {
		
		            }
		        };
		
		        var plot3 = $.plot($("#statsChart"), data, options);
		    });
		
		    // END TAB 2
		
		    // TAB THREE GRAPH //
		    /* TAB 3: Revenew  */
		
		    $(function () {
		
		        var trgt = [
		            [1354586000000, 153],
		            [1364587000000, 658],
		            [1374588000000, 198],
		            [1384589000000, 663],
		            [1394590000000, 801],
		            [1404591000000, 1080],
		            [1414592000000, 353],
		            [1424593000000, 749],
		            [1434594000000, 523],
		            [1444595000000, 258],
		            [1454596000000, 688],
		            [1464597000000, 364]
		        ],
		            prft = [
		                [1354586000000, 53],
		                [1364587000000, 65],
		                [1374588000000, 98],
		                [1384589000000, 83],
		                [1394590000000, 980],
		                [1404591000000, 808],
		                [1414592000000, 720],
		                [1424593000000, 674],
		                [1434594000000, 23],
		                [1444595000000, 79],
		                [1454596000000, 88],
		                [1464597000000, 36]
		            ],
		            sgnups = [
		                [1354586000000, 647],
		                [1364587000000, 435],
		                [1374588000000, 784],
		                [1384589000000, 346],
		                [1394590000000, 487],
		                [1404591000000, 463],
		                [1414592000000, 479],
		                [1424593000000, 236],
		                [1434594000000, 843],
		                [1444595000000, 657],
		                [1454596000000, 241],
		                [1464597000000, 341]
		            ],
		            toggles = $("#rev-toggles"),
		            target = $("#flotcontainer");
		
		        var data = [{
		            label: "Target Profit",
		            data: trgt,
		            bars: {
		                show: true,
		                align: "center",
		                barWidth: 30 * 30 * 60 * 1000 * 80
		            }
		        }, {
		            label: "Actual Profit",
		            data: prft,
		            color: '#3276B1',
		            lines: {
		                show: true,
		                lineWidth: 3
		            },
		            points: {
		                show: true
		            }
		        }, {
		            label: "Actual Signups",
		            data: sgnups,
		            color: '#71843F',
		            lines: {
		                show: true,
		                lineWidth: 1
		            },
		            points: {
		                show: true
		            }
		        }]
		
		        var options = {
		            grid: {
		                hoverable: true
		            },
		            tooltip: true,
		            tooltipOpts: {
		                //content: '%x - %y',
		                //dateFormat: '%b %y',
		                defaultTheme: false
		            },
		            xaxis: {
		                mode: "time"
		            },
		            yaxes: {
		                tickFormatter: function (val, axis) {
		                    return "$" + val;
		                },
		                max: 1200
		            }
		
		        };
		
		        plot2 = null;
		
		        function plotNow() {
		            var d = [];
		            toggles.find(':checkbox').each(function () {
		                if ($(this).is(':checked')) {
		                    d.push(data[$(this).attr("name").substr(4, 1)]);
		                }
		            });
		            if (d.length > 0) {
		                if (plot2) {
		                    plot2.setData(d);
		                    plot2.draw();
		                } else {
		                    plot2 = $.plot(target, d, options);
		                }
		            }
		
		        };
		
		        toggles.find(':checkbox').on('change', function () {
		            plotNow();
		        });
		        plotNow()
		
		    });
		
		}
		
		/*
		 * VECTOR MAP
		 */
		
		data_array = {
		    "US": 4977,
		    "AU": 4873,
		    "IN": 3671,
		    "BR": 2476,
		    "TR": 1476,
		    "CN": 146,
		    "CA": 134,
		    "BD": 100
		};
		
		// Load Map dependency 1 then call for dependency 2
		loadScript("js/plugin/vectormap/jquery-jvectormap-1.2.2.min.js", loadMapFile);
		
		// Load Map dependency 2 then rendeder Map
		function loadMapFile() {
		    loadScript("js/plugin/vectormap/jquery-jvectormap-world-mill-en.js", renderVectorMap);
		}
		
		function renderVectorMap() {
		    $('#vector-map').vectorMap({
		        map: 'world_mill_en',
		        backgroundColor: '#fff',
		        regionStyle: {
		            initial: {
		                fill: '#c4c4c4'
		            },
		            hover: {
		                "fill-opacity": 1
		            }
		        },
		        series: {
		            regions: [{
		                values: data_array,
		                scale: ['#85a8b6', '#4d7686'],
		                normalizeFunction: 'polynomial'
		            }]
		        },
		        onRegionLabelShow: function (e, el, code) {
		            if (typeof data_array[code] == 'undefined') {
		                e.preventDefault();
		            } else {
		                var countrylbl = data_array[code];
		                el.html(el.html() + ': ' + countrylbl + ' visits');
		            }
		        }
		    });
		}
		
		/*
		 * FULL CALENDAR JS
		 */
		
		var taskPriority="";
		$("#eventInfoTip").hide(); 
		function showFormByUrl(formUrl){
			$("#contentFrame").attr("src",formUrl);
			$('#remoteModal').modal({backdrop:"static"});
			//$('#calendar').fullCalendar('refetchEvents');
			//$('#calendar').fullCalendar( 'removeEvents', [formUrl ] );
		}
		$('#remoteModal').on('hide.bs.modal', function () {
				$('#calendar').fullCalendar('refetchEvents');
		});
		var showWeekend = true;
		var pagefunction = function() {
			
			// full calendar
			 
		    var hdr = {
		        left:  'prev,next today',
		        center: 'title',
		        right:'month,agendaWeek,agendaDay'
		    }; 
		    /* initialize the calendar
			 -----------------------------------------------------------------*/
		    $('#calendar').fullCalendar({
		    	lazyFetching:false,
		    	aspectRatio:2,
		    	allDaySlot:false,
		    	weekends:showWeekend,
		    	slotEventOverlap:false,
		    	lang:'zh-cn',
		    	defaultView: 'agendaWeek',
		        header: hdr,
		        editable: false,
		        droppable: false, // this allows things to be dropped onto the calendar !!!
		        viewRender:function(a,b){
		        	$(".fc-toolbar").css("margin-bottom","0px");
		        	 
				},
				eventMouseover:function(event, jsEvent, view ) {
					$("#eventInfoTipCreateTime").html("创        &nbsp;&nbsp; 建:  "+event.start.format("YYYY-MM-DD HH:mm:ss"));
					$("#eventInfoTipTitle").html("主      &nbsp;&nbsp; 题:  "+event.title);
					
					var priority = "";
					if(event.className == "event,bg-color-red"){
						priority = "高";
					}else if(event.className == "event,bg-color-greenLight"){
						priority = "中";
					}else if(event.className == "event,bg-color-darken"){
						priority = "低";
					}
					
					$("#eventInfoTipPriority").html("优先级:  "+priority);
					
					$("#eventInfoTip").css("display", "block"); 
					$("#eventInfoTip").css("z-index", "1000000"); 
					$("#eventInfoTip").css("position", ""); 
					if(document.getElementById("jarviswidget-fullscreen-mode")){
						$("#eventInfoTip").css("position", "fixed"); 
					}
					
					var tipLeft = jsEvent.clientX-200;
					if(jsEvent.clientX+500>document.body.clientWidth){
						tipLeft = jsEvent.clientX-(700-(document.body.clientWidth-jsEvent.clientX))
					}
			      	$("#eventInfoTip2").css("top",jsEvent.clientY-70+document.body.scrollTop);  
			       	$("#eventInfoTip2").css("left",tipLeft);  
			       	$("#eventInfoTip").show();
					
				},
				eventMouseout:function( calEvent, jsEvent, view ) {
					$("#eventInfoTip").hide(); 
				},
				eventClick:function( event, jsEvent, view ) {
					if(document.getElementById("jarviswidget-fullscreen-mode")){
						$("#remoteModal").css("z-index", "1000000"); 
						$("#remoteModal").css("position", "fixed"); 
					}
					showFormByUrl(event.id);
				},
		
		        events: function(start, end, timezone, callback) {
			    	$.ajax({
			            url: _serviceUrl+"runtime/tasks",
			            dataType: 'json',
			            data: {
			            	createTimeB: start.format(),
			            	createTimeE: end.format()
			            },
			            success: function(doc) {
			                var events = []; 
			                var taskColor;
			                for(var i=0;i<doc.data.length;i++){ 
			                	
			                	//判断是否选择了优先级
			                	if(taskPriority == ""){
			                		if(doc.data[i].priority == "50"){
				                		taskColor = ["event", "bg-color-greenLight"];
				                	}else if(doc.data[i].priority == "0"){
				                		taskColor = ["event", "bg-color-darken"];
				                	}else if(doc.data[i].priority == "100"){
				                		taskColor = ["event", "bg-color-red"];
				                	}
				                	var create_time = new Date(doc.data[i].createTime);
				                	var due_time;
				                	if(doc.data[i].dueDate == null){
				                		due_time = null;
				                	}else{
				                		due_time = new Date(doc.data[i].dueDate);
				                	}
				                	events.push({
				                        title: doc.data[i].subject,
				                        start: create_time, 
										end:due_time,
				                        className: taskColor,
				                        id:_formUrl+"?dataId="+doc.data[i].bizKey+"&taskId="+doc.data[i].id+"&processInstanceId="+doc.data[i].processInstanceId,
				                    });
			                	}else if(taskPriority == doc.data[i].priority){
			                		if(doc.data[i].priority == "50"){
				                		taskColor = ["event", "bg-color-greenLight"];
				                	}else if(doc.data[i].priority == "0"){
				                		taskColor = ["event", "bg-color-darken"];
				                	}else if(doc.data[i].priority == "100"){
				                		taskColor = ["event", "bg-color-red"];
				                	}
				                	var create_time = new Date(doc.data[i].createTime);
				                	var due_time;
				                	if(doc.data[i].dueDate == null){
				                		due_time = null;
				                	}else{
				                		due_time = new Date(doc.data[i].dueDate);
				                	}
				                	events.push({
				                        title: doc.data[i].subject,
				                        start: create_time, 
										end:due_time,
				                        className: taskColor,
				                        id:_formUrl+"?dataId="+doc.data[i].bizKey+"&taskId="+doc.data[i].id+"&processInstanceId="+doc.data[i].processInstanceId,
				                    });
			                	}
			                	
			                }
			                callback(events);
			            }
			        });
			         
			    },
		
		        eventRender: function (event, element, icon) {
		        },
		
		        windowResize: function (event, ui) {
		            $('#calendar').fullCalendar('render');
		        }
		    });
		 
		    $('.fc-right, .fc-left').hide();
		
			$('#btn-prev').click(function () {
			    //$('.fc-prev-button').click();
			    $('#calendar').fullCalendar('prev');
			    var moment = $('#calendar').fullCalendar('getDate');
			    //alert("The current date of the calendar is " + moment.format());
			    
			    var view = $('#calendar').fullCalendar('getView');
			    //alert("The view's title is " + view.title);
			    
			    //alert("The view's name is " + view.name);
			    
			    //alert("The view's start is " + view.start.format());
			    // alert("The view's end is " + view.end.format());
			    //alert("The view's intervalStart is " + view.intervalStart.format());
			    // alert("The view's intervalEnd is " + view.intervalEnd.format());
			    
			    return false;
			});
			
			$('#btn-next').click(function () {
			    //$('.fc-next-button').click();
			    $('#calendar').fullCalendar('next');
			    var moment = $('#calendar').fullCalendar('getDate'); 
			    return false;
			});
			
			$('#calendar-buttons #btn-today').click(function () {
			    $('.fc-button-today').click();
			    return false;
			});
			
			$('#mt').click(function () {
				$('#select_view').html($('#mt').html()+"<i class='fa fa-caret-down'></i>");
			    $('#calendar').fullCalendar('changeView', 'month');
			});
			
			$('#ag').click(function () {
				$('#select_view').html($('#ag').html()+"<i class='fa fa-caret-down'></i>");
			    $('#calendar').fullCalendar('changeView', 'agendaWeek');
			});
			
			$('#td').click(function () {
				$('#select_view').html($('#td').html()+"<i class='fa fa-caret-down'></i>");
			    $('#calendar').fullCalendar('changeView', 'agendaDay');
			});
			
			$('#basicWeek').click(function () {
				$('#select_view').html($('#basicWeek').html()+"<i class='fa fa-caret-down'></i>");
			    $('#calendar').fullCalendar('changeView', 'basicWeek');
			});
			
			$('#basicDay').click(function () {
				$('#select_view').html($('#basicDay').html()+"<i class='fa fa-caret-down'></i>");
			    $('#calendar').fullCalendar('changeView', 'basicDay');
			});
			
			$('#task_createtime').click(function () {
				$('#select_time').html($('#task_createtime').html()+"<i class='fa fa-caret-down'></i>"); 
			});
			$('#task_duration').click(function () {
				$('#select_time').html($('#task_duration').html()+"<i class='fa fa-caret-down'></i>"); 
			});
			
			$('#priority_high').click(function () {
				taskPriority = "100";
				$('#select_priority').html($('#priority_high').html()+"<i class='fa fa-caret-down'></i>"); 
				$('#calendar').fullCalendar('refetchEvents');
			});
			
			$('#priority_mid').click(function () {
				taskPriority = "50";
				$('#select_priority').html($('#priority_mid').html()+"<i class='fa fa-caret-down'></i>"); 
				$('#calendar').fullCalendar('refetchEvents');
			});
			
			$('#priority_low').click(function () {
				taskPriority = "0";
				$('#select_priority').html($('#priority_low').html()+"<i class='fa fa-caret-down'></i>"); 
				$('#calendar').fullCalendar('refetchEvents');
			});
			$('#priority_all').click(function () {
				taskPriority = "";
				$('#select_priority').html($('#priority_all').html()+"<i class='fa fa-caret-down'></i>"); 
				$('#calendar').fullCalendar('refetchEvents');
			});
			 
		};
		
		// end pagefunction
		
		 loadScript("js/plugin/fullcalendar/moment.min.js", function(){
				loadScript("js/plugin/fullcalendar/fullcalendar.min.js",function(){
					loadScript("js/plugin/fullcalendar/lang-all.js",pagefunction)
				});
			});
		// loadscript and run pagefunction 
		
		/*
		 * CHAT
		 */
		
		var filter_input = $('#filter-chat-list'),
			chat_users_container = $('#chat-container > .chat-list-body'),
			chat_users = $('#chat-users'),
			chat_list_btn = $('#chat-container > .chat-list-open-close'),
			chat_body = $('#chat-body');
		
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
	
	};
	// end pagefunction
	// run pagefunction on load
	pagefunction();
</script>
	<jsp:include page="../bottom.jsp" />
</body>
</html>