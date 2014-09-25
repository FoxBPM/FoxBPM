<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<jsp:include page="../header.jsp" />
</head>
<body>
	<jsp:include page="../top.jsp" />
	<!-- widget grid -->
	<section id="widget-grid" class="">
		<!-- row -->
		<div class="row">
			<!-- NEW WIDGET START -->
			<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-2"
					data-widget-editbutton="false">
					<header>
						<span class="widget-icon"> <i class="fa fa-table"></i>
						</span>
						<h2>用户列表</h2>
					</header>
					<!-- widget div-->
					<div>
						<!-- widget content -->
						<div class="widget-body no-padding" id="datatable_div">
							<table id="datatable_col_reorder"
								class="table table-striped table-bordered table-hover"
								width="100%">
								<thead>
									<tr>
										<th data-class="expand">用户ID</th>
										<th data-hide="phone">用户图像</th>
										<th data-hide="phone">用户名称</th>
										<th data-hide="phone,tablet">用户密码</th>
										<th data-hide="phone,tablet">电话号码</th>
										<th data-hide="phone,tablet">Emil地址</th>
										<th>操作</th>
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

		<!-- end row -->

		<!-- end row -->

	</section>
	<!-- end widget grid -->
	<jsp:include page="../bottom.jsp" />

	<!-- js -->
	<script src="js/plugin/datatables/jquery.dataTables.min.js"></script>
	<script src="js/plugin/datatables/dataTables.colVis.min.js"></script>
	<script src="js/plugin/datatables/dataTables.tableTools.min.js"></script>
	<script src="js/plugin/datatables/dataTables.bootstrap.min.js"></script>
	<script
		src="js/plugin/datatable-responsive/datatables.responsive.min.js"></script>
	<script type="text/javascript">
		var pagefunction = function() {
			var responsiveHelper_datatable_col_reorder = undefined;
			var breakpointDefinition = {
				tablet : 1024,
				phone : 480
			};
			/* COLUMN SHOW - HIDE */
			$('#datatable_col_reorder')
					.dataTable(
							{
								columns : [
										{
											data : 'userId'
										},
										{
											data : "userId"
										},
										{
											data : 'userName'
										},
										{
											data : 'password'
										},
										{
											data : 'tel'
										},
										{
											data : 'email'
										},
										{
											"orderable" : false,
											"data" : null,
											"defaultContent" : "<a href='javascript:void(0);' class='btn btn-danger'>删除</a> <a href='javascript:void(0);' class='btn btn-success'>处理</a>"
										} ],
								columnDefs : [ {
									"targets" : [ 1 ],
									"orderable" : true,
									"createdCell" : function(td, cellData,
											rowData, row, col) {
										$(td).css("text-align", "center");
										$(td).html("<img width='50' height='50' src='/foxbpm-webapps-common/service/identity/users/"+cellData+"/picture'/>");
									}
								} ],
								"processing" : true,
								"serverSide" : true,
								"ajax" : "/foxbpm-webapps-common/service/identity/users",
								"sDom" : "<'dt-toolbar'<'col-sm-6 col-xs-12 hidden-xs'l><'col-sm-6 col-xs-6 hidden-xs'C>r>"
										+ "t"
										+ "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-sm-6 col-xs-12'p>>",
								"autoWidth" : true,
								"preDrawCallback" : function() {
									if (!responsiveHelper_datatable_col_reorder) {
										responsiveHelper_datatable_col_reorder = new ResponsiveDatatablesHelper(
												$('#datatable_col_reorder'),
												breakpointDefinition);
									}
								},
								"rowCallback" : function(nRow) {
									responsiveHelper_datatable_col_reorder
											.createExpandIcon(nRow);
								},
								"drawCallback" : function(oSettings) {
									responsiveHelper_datatable_col_reorder
											.respond();
								}
							});
		};
		$(document).ready(function() {
			pageSetUp();
			pagefunction();
		});
	</script>
</body>
</html>