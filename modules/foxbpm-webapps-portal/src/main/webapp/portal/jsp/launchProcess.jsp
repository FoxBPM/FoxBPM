<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en-us">	
    <!-- 加载头部信箱 -->
    <head>
	 <jsp:include page="../header.jsp"/>
    </head>
	<body class="">
		<jsp:include page="../top.jsp"/>
		

<div class="row hidden-mobile">
	<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
		<h1 class="page-title txt-color-blueDark">
			<i class="fa-fw fa fa-picture-o"></i>发起流程<span>
		</h1>
	</div>
</div>
<div class="row">
	<div class="col-sm-12" style="margin-left: 5px">
		<div class="input-group">
			<input class="form-control" style="width: 150px; height: 30px"
				type="text" id="input_search" placeholder="Search..." title="流程名称"> <a
				class="btn btn-primary disabled"
				style="height: 30px; disabled: true" href="javascript:void(0);"
				id="btn_search">Search</a>
		</div>
	</div>
</div>
<br>
<!-- row -->
<div class="row">
	<!-- SuperBox -->
	<div class="superbox col-sm-12" id="superbox"></div>
	<!-- /SuperBox -->
	<div class="superbox-show" style="height: 300px; display: none"></div>
</div>

<div class="modal fade " id="remoteModal" tabindex="-1" role="dialog"
	aria-labelledby="remoteModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width: 800px">
		<iframe id="contentFrame" class="col-sm-10 col-md-12 col-lg-12"
			style="border: 0px; height: 500px;"></iframe>
	</div>
</div>
<!-- end row -->

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
	 * ALL PAGE RELATED SCRIPTS CAN GO BELOW HERE
	 * eg alert("my home function");
	 * 
	 * var pagefunction = function() {
	 *   ...
	 * }
	 * loadScript("js/plugin/_PLUGIN_NAME_.js", pagefunction);
	 * 
	 * TO LOAD A SCRIPT:
	 * var pagefunction = function (){ 
	 *  loadScript(".../plugin.js", run_after_loaded);	
	 * }
	 * 
	 * OR
	 * 
	 * loadScript(".../plugin.js", run_after_loaded);
	 */

	// PAGE RELATED SCRIPTS
	// pagefunction
	var pagefunction = function() {
		  new Gallery({
			imgServiceUrl : _serviceUrl,
			searchId:"searchbtton",
			doAfter : function() {
				//资源异步加载成功后执行
				$('.superbox').SuperBox();
			}
		});   
	};

	// end pagefunction
	// run pagefunction on load
	// load bootstrap-progress bar script
	loadScript("portal/js/launchProcess.js", function() {
		loadScript("portal/js/plugin/superbox/superbox.min.js", pagefunction);
	});
</script>
		<jsp:include page="../bottom.jsp"/>
	</body>
</html>