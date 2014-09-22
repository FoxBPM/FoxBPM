<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-us">	
	<head>
		 <jsp:include page="../header.jsp"/>
	</head>
	<body>
<jsp:include page="../top.jsp"/>
<!-- widget grid -->
<section id="widget-grid" class="">
	<!-- row -->
	<div class="row">
		<!-- NEW WIDGET START -->
		<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<!-- Widget ID (each widget will need unique ID)-->
			<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-1" data-widget-editbutton="false">
				<header>
					<span class="widget-icon"> <i class="fa fa-table"></i> </span>
					<h2>待办任务</h2>
				</header>
				<!-- widget div-->
				<div>

					<!-- widget edit box -->
					<div class="jarviswidget-editbox">
						<!-- This area used as dropdown edit box -->
					</div>
					
					<!-- widget content -->
					<div class="widget-body no-padding">
						<table id="datatable_fixed_column" class="table table-striped table-bordered" width="100%">
					        <thead>
					            <tr>
				                    <th><i></i></th>
									<th><i class="fa fa-fw fa-tasks text-muted hidden-md hidden-sm hidden-xs"></i>  任务</th>
									<th><i class="fa fa-fw fa-rocket text-muted hidden-md hidden-sm hidden-xs"></i> 优先级</th>
									<th><i class="fa fa-fw fa-user text-muted hidden-md hidden-sm hidden-xs"></i> 发起人</th>
									<th><i class="fa fa-fw fa-flag text-muted hidden-md hidden-sm hidden-xs"></i> 状态</th>
									<th><i class="fa fa-fw fa-calendar text-muted hidden-md hidden-sm hidden-xs"></i> 创建</th>
									<th><i class="fa fa-fw fa-calendar text-muted hidden-md hidden-sm hidden-xs"></i> 期限</th>
									<th><i class="fa fa-fw fa-cogs text-muted hidden-md hidden-sm hidden-xs" ></i> 操作</th>
					            </tr>
					        </thead>

					        <tbody>
					        </tbody>
					
						</table>

					</div>
					<!-- end widget content -->

				</div>
				<!-- end widget div -->

			</div>
			<!-- end widget -->
		</article>
		<!-- WIDGET END -->
	</div>
	<!-- end row -->
</section>
<jsp:include page="../bottom.jsp"/>
    <!-- js -->
	<script src="portal/js/todotasks.js"></script>
	<script src="js/plugin/datatables/jquery.dataTables.min.js"></script>
	<script src="js/plugin/datatables/dataTables.colVis.min.js"></script>
	<script src="js/plugin/datatables/dataTables.tableTools.min.js"></script>
	<script src="js/plugin/datatables/dataTables.bootstrap.min.js"></script>
	<script src="js/plugin/datatable-responsive/datatables.responsive.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			pageSetUp();
			pagefunction();
		});
	</script>	
</body>
</html>