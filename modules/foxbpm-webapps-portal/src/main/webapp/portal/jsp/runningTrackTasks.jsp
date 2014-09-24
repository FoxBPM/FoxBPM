<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-us">	
	<head>
		 <jsp:include page="../header.jsp"/>
	</head>
	<script src="portal/js/runningtrackTasks.js"></script> 
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
	<body>
<jsp:include page="../top.jsp"/>
<!-- widget grid -->
<section id="widget-grid" class="">
	<!-- row -->
	<div class="row">
		<!-- NEW WIDGET START -->
		<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-2" data-widget-editbutton="false">
				<header>
					<span class="widget-icon"> <i class="fa fa-table"></i> </span>
					<h2>流 程 追 踪</h2>

				</header>
				<!-- widget div-->
				<div>
					<!-- widget content -->
					<div class="widget-body no-padding" id="datatable_div">
						<table id="datatable_col_reorder" class="table table-striped table-bordered table-hover" width="100%">
							<thead>
								<tr> 
									<th><i></i></th>
									<th><i class="fa fa-fw fa-tasks text-muted hidden-md hidden-sm hidden-xs"></i>  主题</th>
									<th ><i class="fa fa-fw fa-calendar text-muted hidden-md hidden-sm hidden-xs"></i> 创建</th>
									<th ><i class="fa fa-fw fa-calendar text-muted hidden-md hidden-sm hidden-xs"></i> 更新</th>
									<th><i class="fa fa-fw fa-flag text-muted hidden-md hidden-sm hidden-xs"></i> 状态</th>
									<th><i class="fa fa-fw fa-user text-muted hidden-md hidden-sm hidden-xs"></i> 发起人</th>
									<th><i class="fa fa-location-arrow"></i> 位置</th>
									<th><i class="fa fa-fw fa-cogs text-muted hidden-md hidden-sm hidden-xs" ></i> 操作</th>
					
								</tr>
							</thead>
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
</section>
<!-- end widget grid --> 
<jsp:include page="../bottom.jsp"/>
</body>

</html>