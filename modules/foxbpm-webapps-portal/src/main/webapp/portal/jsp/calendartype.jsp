<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-us">	
	<head>
		 <jsp:include page="../header.jsp"/>
	</head>
	<script src="js/plugin/datatables/jquery.dataTables.js"></script>
	<script src="js/plugin/datatables/dataTables.colVis.min.js"></script>
	<script src="js/plugin/datatables/dataTables.tableTools.min.js"></script>
	<script src="js/plugin/datatables/dataTables.bootstrap.min.js"></script>
	<script src="js/plugin/datatable-responsive/datatables.responsive.min.js"></script>
	<script src="portal/js/calendartype.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			var $loginForm = $("#login-form").validate({
				// Rules for form validation
				rules : {
					id : {
						required : true,
					},
					name : {
						required : true,
						/* minlength : 3, */
						maxlength : 20
					}
				},

				// Messages for form validation
				messages : {
					id : {
						required : '编号不能为空',
					},
					name : {
						required : '名称不能为空'
					}
				},

				// Do not change code below
				errorPlacement : function(error, element) {
					error.insertAfter(element.parent());
				}
			});

			$("#login-form").submit(function(){
				if($("#login-form").valid()) {
					var op =  $("#login-form").attr("op");
					if(0 == op){
					 $(this).ajaxSubmit({  
			                type:"put",  //提交方式  
			                dataType:"text", //数据类型  
			                contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			                url:"/foxbpm-webapps-common/service/workcal/calendartype/" + $("#typeId").val(), //请求url  
			                success:function(data){ //提交成功的回调函数  
			                    /* alert(data.result); */
			                     $('#remoteModal').modal('hide');
			                    
			                     $.smallBox({ 
				            			title : '提示!',
				            			content : '提交成功！',
				  	     				color : '#296191',
				  	     				icon : 'fa fa-bell swing animated',
				            			timeout : 2000
				            		});
			                     typedataTable.ajax.url("/foxbpm-webapps-common/service/workcal/calendartype").load();
								 $("#edittype").attr('disabled',"true");
								 $("#deletetype").attr('disabled',"true");
			                },
			                error:function(){
			                	$.smallBox({ 
			            			title : '错误!',
			            			content : '提交数据失败',
			            			color : "#C46A69",
			            			icon : "fa fa-warning shake animated",
			            			timeout : 2000
			            		});
			                	typedataTable.ajax.url("/foxbpm-webapps-common/service/workcal/calendartype").load();
								 $("#edittype").attr('disabled',"true");
								 $("#deletetype").attr('disabled',"true");
			                }
			            });  
					}else {
						$(this).ajaxSubmit({
			                type:"post",  //提交方式  
			                dataType:"text", //数据类型  
			                contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			                url:"/foxbpm-webapps-common/service/workcal/calendartype", //请求url  
			                success:function(data){ //提交成功的回调函数  
			                     $('#remoteModal').modal('hide');
			                
			                     $.smallBox({ 
				            			title : '提示!',
				            			content : '提交成功！',
				  	     				color : '#296191',
				  	     				icon : 'fa fa-bell swing animated',
				            			timeout : 2000
				            		});
			                
			                     typedataTable.ajax.url("/foxbpm-webapps-common/service/workcal/calendartype").load();
								 $("#edittype").attr('disabled',"true");
								 $("#deletetype").attr('disabled',"true");
			                },
			                error:function(){
								 $.smallBox({ 
									title : '错误!',
									content : '提交数据失败',
									color : "#C46A69",
									icon : "fa fa-warning shake animated",
									timeout : 2000
								});
								 typedataTable.ajax.url("/foxbpm-webapps-common/service/workcal/calendartype").load();
								 $("#edittype").attr('disabled',"true");
								 $("#deletetype").attr('disabled',"true");
			                }
			            }); 
					}
			          return false; //不刷新页面
				}
	    });
			
			calendartypepagefunction();
		});
	</script>	
	<body>
<jsp:include page="../top.jsp"/>

<!-- 编辑 -->
<div class="modal fade" id="remoteModal" tabindex="-1" role="dialog"
	aria-labelledby="remoteModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<form action="" id="login-form" class="smart-form">
				<fieldset>
					<section>
						<div class="row">
							<label class="label col col-2">编号</label>
							<div class="col col-10">
								<label class="input"> <input type="text" name="id"
									id="typeId" placeholder="请输入编号">
								</label>
							</div>
						</div>
					</section>
					<section>
						<div class="row">
							<label class="label col col-2">名称</label>
							<div class="col col-10">
								<label class="input"> <input type="text" name="name"
									id="typeName" placeholder="请输入名称">
								</label>
							</div>
						</div>
					</section>
				</fieldset>
				<footer>
					<button type="submit" class="btn btn-primary" id="submit">提交</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				</footer>
			</form>
		</div>
	</div>
</div>

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
					<h2>日历类型</h2>

				</header>
				<!-- widget div-->

				<!-- MODAL PLACE HOLDER -->

				<div>
					<!-- widget content -->
					<div class="widget-body no-padding" id="datatable_div">
						<table id="datatable_col_reorder"
							class="table table-striped table-bordered table-hover"
							width="100%">
							<thead>
								<tr>
									<th><i
										class="fa fa-fw fa-id text-muted hidden-md hidden-sm hidden-xs"></i>
										编号</th>
									<th><i
										class="fa fa-fw fa-name text-muted hidden-md hidden-sm hidden-xs"></i>
										名称</th>
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
<jsp:include page="../bottom.jsp"/>
</body>

</html>