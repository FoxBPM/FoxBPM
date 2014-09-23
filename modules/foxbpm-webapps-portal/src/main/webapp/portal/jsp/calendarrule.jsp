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
	<script src="portal/js/calendarrule.js"></script>
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
					},
					year : {
						required : true,
					},
					typeid : {
						required : true,
					}
				},

				// Messages for form validation
				messages : {
					id : {
						required : '编号不能为空',
					},
					name : {
						required : '名称不能为空'
					},
					year : {
						required : "年度不能为空",
					},
					typeid : {
						required : "类型不能为空",
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
			                url:"/foxbpm-webapps-common/service/workcal/calendarrule/" + $("#ruleId").val(), //请求url  
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
			                     ruledataTable.ajax.url("/foxbpm-webapps-common/service/workcal/calendarrule").load();
								 $("#editrule").attr('disabled',"true");
								 $("#deleterule").attr('disabled',"true");
			                },
			                error:function(){
			                	$.smallBox({ 
			            			title : '错误!',
			            			content : '提交数据失败',
			            			color : "#C46A69",
			            			icon : "fa fa-warning shake animated",
			            			timeout : 2000
			            		});
			                	ruledataTable.ajax.url("/foxbpm-webapps-common/service/workcal/calendarrule").load();
								 $("#editrule").attr('disabled',"true");
								 $("#deleterule").attr('disabled',"true");
			                }
			            });  
					}else {
						$(this).ajaxSubmit({  
			                type:"post",  //提交方式  
			                dataType:"text", //数据类型  
			                contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			                url:"/foxbpm-webapps-common/service/workcal/calendarrule", //请求url  
			                success:function(data){ //提交成功的回调函数  
			                     $('#remoteModal').modal('hide');
			                
			                     $.smallBox({ 
				            			title : '提示!',
				            			content : '提交成功！',
				  	     				color : '#296191',
				  	     				icon : 'fa fa-bell swing animated',
				            			timeout : 2000
				            		});
			                
			                     ruledataTable.ajax.url("/foxbpm-webapps-common/service/workcal/calendarrule").load();
								 $("#editrule").attr('disabled',"true");
								 $("#deleterule").attr('disabled',"true");
			                },
			                error:function(){
								 $.smallBox({ 
									title : '错误!',
									content : '提交数据失败',
									color : "#C46A69",
									icon : "fa fa-warning shake animated",
									timeout : 2000
								});
								 ruledataTable.ajax.url("/foxbpm-webapps-common/service/workcal/calendarrule").load();
								 $("#editrule").attr('disabled',"true");
								 $("#deleterule").attr('disabled',"true");
			                }
			            }); 
					}
			          return false; //不刷新页面
				}
	    });
			
		$('#ruleWorkdate').datepicker({
			dateFormat : 'yy-mm-dd',
			prevText : '<i class="fa fa-chevron-left"></i>',
			nextText : '<i class="fa fa-chevron-right"></i>',
			/* onSelect : function(selectedDate) {
				$('#finishdate').datepicker('option', 'minDate', selectedDate);
			} */
		});
		
		$.ajax({
			  url: 'service/workcal/calendartype',
			  type : 'get',
			  dataType: 'json',
			  success: function(data){
				  $('#ruleTypeid').innerHTML = "";
				  for (var i = 0; i < data.data.length; i++) {
					  $('#ruleTypeid').append($("<option value=\'"+ data.data[i].id +"\'>").html(data.data[i].name + "&nbsp;&nbsp;(" +data.data[i].id + ")"));
					};
			  },
		});
		
			calendarrulepagefunction();
		});
	</script>	
	<body>
<jsp:include page="../top.jsp"/>

<section id="widget-grid" class="">
	<div class="modal fade" id="remoteModal" tabindex="-1" role="dialog" aria-labelledby="remoteModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
						<form action="" id="login-form" class="smart-form">
		
						<fieldset>
										<section>
								<div class="row">
									<label class="label col col-2">编号</label>
									<div class="col col-10">
										<label class="input">
											<input type="text" name="id" id="ruleId" placeholder="请输入编号">
										</label>
									</div>
								</div>
							</section>
				
							<section>
								<div class="row">
									<label class="label col col-2">年度</label>
									<div class="col col-10">
										<label class="input">
											<input type="text" name="year" id="ruleYear"  placeholder="请输入年度">
										</label>
									</div>
								</div>
							</section>
							
							<section>
								<div class="row">
									<label class="label col col-2">周</label>
									<div class="col col-10">
										<label class="select">
											<!-- <input type="text" name="week" id="ruleWeek" placeholder="请输入周"> -->
											<select id="ruleWeek" name="week">
												<option value="0" selected=""></option>
											    <option value="1">周一</option>
												<option value="2">周二</option>
												<option value="3">周三</option>
												<option value="4">周四</option>
												<option value="5">周五</option>
												<option value="6">周六</option>
												<option value="7">周日</option>
											</select> <i></i> </label>
											
										<p class="note">
											如果为特定工作时间，则此项可以不选。
										</p>
									</div>
								</div>
							</section>
							
							<section>
								<div class="row">
									<label class="label col col-2">名称</label>
									<div class="col col-10">
										<label class="input">
											<input type="text" name="name" id="ruleName" placeholder="请输入名称">
										</label>
									</div>
								</div>
							</section>
							
							<section>
								<div class="row">
									<label class="label col col-2">工作时间</label>
									<div class="col col-10">
										<label class="input"><i class="icon-append fa fa-calendar"></i>
											<input type="text" name="workdate" id="ruleWorkdate" placeholder="请选择时间">
										</label>
									</div>
								</div>
							</section>
							
							<section>
								<div class="row">
									<label class="label col col-2">状态</label>
									<div class="col col-10">
										<label class="select">
											<!-- <input type="text" name="week" id="ruleWeek" placeholder="请输入周"> -->
											<select id="ruleStatus" name="status">
											    <option value="0" selected="">工作时间</option>
												<option value="1">假期时间</option>
										</select> <i></i> </label>
									</div>
								</div>
							</section>
							
							<section>
								<div class="row">
									<label class="label col col-2">类型ID</label>
									<div class="col col-10">
										<!-- <label class="input">
											<input type="text" name="typeid" id="ruleTypeid" placeholder="请选择类型">
										</label> -->
										<label class="select">
											<!-- <input type="text" name="week" id="ruleWeek" placeholder="请输入周"> -->
											<select id="ruleTypeid" name="typeid">
											</select> <i></i> 
										</label>
									</div>
								</div>
							</section>
				
						</fieldset>
						<footer>
							<button type="submit" class="btn btn-primary">
								提交
							</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">
								取消
							</button>
						</footer>
					</form>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="remoteModalAdd" tabindex="-1" role="dialog" aria-labelledby="remoteModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
				</div>
			</div>>
		</div>

	<!-- row -->
	<div class="row">
		<!-- NEW WIDGET START -->
		<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-2" data-widget-editbutton="false">
				<header>
					<span class="widget-icon"> <i class="fa fa-table"></i> </span>
					<h2>日历规则</h2>

				</header>
				<!-- widget div-->
				<div>
					<!-- widget content -->
					<div class="widget-body no-padding" id="datatable_div">
						<table id="datatable_col_reorder" class="table table-striped table-bordered table-hover" width="100%">
							<thead>
								<tr> 
									<th><i class="fa fa-fw fa-id text-muted hidden-md hidden-sm hidden-xs"></i>  编号</th>
									<th><i class="fa fa-fw fa-year text-muted hidden-md hidden-sm hidden-xs"></i> 年度</th>
									<th><i class="fa fa-fw fa-week text-muted hidden-md hidden-sm hidden-xs"></i> 周</th>
									<th><i class="fa fa-fw fa-name text-muted hidden-md hidden-sm hidden-xs"></i> 名称</th>
									<th><i class="fa fa-fw fa-workdate text-muted hidden-md hidden-sm hidden-xs"></i> 工作日期</th>
									<th><i class="fa fa-fw fa-status text-muted hidden-md hidden-sm hidden-xs"></i> 状态</th>
									<th><i class="fa fa-fw fa-type text-muted hidden-md hidden-sm hidden-xs"></i> 类型</th>
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