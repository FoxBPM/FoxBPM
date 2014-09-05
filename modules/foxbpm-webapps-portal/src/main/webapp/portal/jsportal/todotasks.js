var pagefunction = function() {
	var responsiveHelper_datatable_fixed_column = undefined;
	var breakpointDefinition = {
			tablet : 1024,
			phone : 480
		};

	var todoTable = $('#datatable_fixed_column')
			.DataTable(
					{
						columns : [ {
							data : 'processInitiator'
						},{
							data : 'subject'
						}, {
							data : 'priority'
						}, {
							data : 'processInitiator'
						}, {
							data : 'endTime'
						}, {
							data : 'createTime'
						}, {
							data : 'dueDate'
						}, {
							"orderable" : false,
							"data" : null,
							"defaultContent" : ""// <a
													// href='javascript:void(0);'
													// class='btn
													// btn-danger'>删除</a> <a
													// href='javascript:void(0);'
													// class='btn
													// btn-success'>处理</a>
						} ],
						columnDefs : [
								{
									"targets" : [ 0 ],
									"orderable" : false,
									"width":20,
									"createdCell" : function(td, cellData,
											rowData, row, col) {
								
										$(td).html("<img width='20' height='20' class='online' src='/foxbpm-webapps-common/service/identity/users/"+cellData+"/picture'/>");
										
									}
								},
								{
									"targets" : [ 2 ],
									"orderable" : true,
									"createdCell" : function(td, cellData,
											rowData, row, col) {

										if (rowData.priority == "0") {
											$(td)
													.html(
															"<span class='badge'>低</span>");
										}
										if (rowData.priority == "50") {
											$(td)
													.html(
															"<span class='badge bg-color-greenLight'>中</span>");
										}
										if (rowData.priority == "100") {
											$(td)
													.html(
															"<span class='badge bg-color-red'>高</span>");
										}
									}
								},
								{
									"targets" : [ 4 ],
									"orderable" : true,
									"createdCell" : function(td, cellData,
											rowData, row, col) {
										if (rowData.endTime == null) {
											$(td).html("<span class='label label-default'>激活</span>");
											if (rowData.isSuspended == 'true') {
												$(td).html("<span class='label label-default'>暂停</span>");
											}
											if (rowData.assignee == null) {
												$(td).html("<span class='label label-default'>未领取</span>");
											}

										} else {
											$(td).html("<span class='label label-default'>完成</span>");
										}

									}
								},
								{
									"targets" : [ 5 ],
									"orderable" : true,
									"createdCell" : function(td, cellData,
											rowData, row, col) {
										$(td).html(cellData);
									}
								},
								{
									"targets" : [ 7 ],
									"orderable" : true,
									"createdCell" : function(td, cellData,
											rowData, row, col) {
										
										$(td).html("<a class='btn btn-default btn-xs' href='javascript:void(0);'><i class='fa fa-pencil-square-o'></i> 表单</a>"+
												"    <a class='btn btn-default btn-xs' href='javascript:void(0);'><i class='fa fa-sitemap'></i> 流程图</a>");
									
										
										

									}
								} 
								],

						"processing" : true,
						"orderable" : true,
						"serverSide" : true,
						"ajax" : "/foxbpm-webapps-common/service/tasks",
						"sDom" : "<'dt-toolbar'<'col-sm-6 col-xs-12 hidden-xs'f><'col-sm-6 col-xs-12 hidden-xs'>C>"+
								 "t"+
								 "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-sm-6 col-xs-12'p  l >>",
						"autoWidth" : true,
						"colVis": {
				            "buttonText": "选择列",
				            "restore": "恢复默认展示",
				            "showAll": "展示所有列",
				            "showNone": "不展示列"
				           
				        },
				        "oLanguage": {
		                    "sProcessing": "正在加载中......",
		                    "sZeroRecords": "对不起，查询不到相关数据！",
		                    "sEmptyTable": "表中无数据存在！",
		                    "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
		                    "sInfoFiltered": "数据表中共为 _MAX_ 条记录",

		                    "oPaginate": {
		                        "sFirst": "首页",
		                        "sPrevious": "上一页",
		                        "sNext": "下一页",
		                        "sLast": "末页"
		                    },
		                    "sSearch": "<form>主题：_INPUT_ 发起人：<input id='INITIATOR_SEARCH' type='text' class='form-control' value='' style='width:6%' />" +
		                    		"状态：<select id='TASKSTATE_SEARCH' class='form-control' style='width:8%'><option value='0'>未领取</option><option value='1'>领取</option><option value='2' selected>所有</option></select>" +
		                    		"&nbsp;&nbsp;创建：<input id='createtime_start_dateselect_filter' type='text'  class='form-control datepicker' data-dateformat='yy-mm-dd' style='width:8%'>" +
		                    		"<label for='createtime_start_dateselect_filter' class='glyphicon glyphicon-calendar no-margin padding-top-15' rel='tooltip' title='' style='left:-55%;top:-5px'></label>" +
		                    		" - " +
		                    		"<input id='createtime_end_dateselect_filter' type='text' value='' class='form-control datepicker' data-dateformat='yy-mm-dd' style='width:8%'>"+
		                    		"<label for='createtime_end_dateselect_filter' class='glyphicon glyphicon-calendar no-margin padding-top-15' rel='tooltip' title=''  style='left:-45%;top:-5px'></label> " +
		                    		"期限：<input id='duration_start_dateselect_filter' type='text'  class='form-control datepicker' data-dateformat='yy-mm-dd' style='width:8%'>"+
		                    		"<label for='duration_start_dateselect_filter' class='glyphicon glyphicon-calendar no-margin padding-top-15' rel='tooltip' title=''  style='left:-32.3%;top:-5px'></label>" +
		                    		" - "+
		                    		"<input id='duration_end_dateselect_filter' type='text'  class='form-control datepicker' data-dateformat='yy-mm-dd' style='width:8%'>"+
		                    		"<label for='duration_end_dateselect_filter' class='glyphicon glyphicon-calendar no-margin padding-top-15' rel='tooltip' title='' style='left:-22.2%;top:-5px'></label>"+
		                    		"&nbsp;&nbsp;<a class='btn btn-primary' style='height: 30px; disabled: true;' onclick='searchTodoTask();' href='javascript:void(0);'>搜索</a>&nbsp;&nbsp;<a class='btn btn-primary' style='height: 30px; disabled: true;' onclick='clearCondition();' href='javascript:void(0);'>重置</a></form>"
								
				        },
				        "bStateSave" : false,
				        "bAutoWidth": true,
				        "sPaginationType":'full_numbers',
				        "preDrawCallback" : function() {
							if (!responsiveHelper_datatable_fixed_column) {
								responsiveHelper_datatable_fixed_column = new ResponsiveDatatablesHelper($('#datatable_fixed_column'), breakpointDefinition);
							}
						},
						"rowCallback" : function(nRow) {
							responsiveHelper_datatable_fixed_column.createExpandIcon(nRow);
						},
						"drawCallback" : function(oSettings) {
							responsiveHelper_datatable_fixed_column.respond();
						},
						"drawCallback":function(){
							//调整页面布局
							$("#datatable_fixed_column_length").css("padding-right","10px");
							$(".dt-toolbar").css("padding-bottom","6px");
							$("#datatable_fixed_column_filter").css("width","1200px");
							$("[type='search']").css("width","8%");
							
//							var itemnumberInfo = $("#datatable_fixed_column_info").html();
//							var totalNumber = itemnumberInfo.substring(itemnumberInfo.indexOf("共")+1,itemnumberInfo.indexOf("条记录"));
//							$("#todo_item_number").html(totalNumber);
							pageSetUp();						
						},
						
					});
     
	 todoTable.column(1).order( 'asc' );
	 clearCondition = function (){
		$("[type='search']").val("");
		$("#INITIATOR_SEARCH").val("");
		$("#TASKSTATE_SEARCH").val("2");
		$("#duration_start_dateselect_filter").val("");
		$("#createtime_end_dateselect_filter").val("");
		$("#duration_end_dateselect_filter").val(""); 
		$("#createtime_start_dateselect_filter").val("");
	};
     searchTodoTask = function() {
    	var baseUrl = "/foxbpm-webapps-common/service/tasks?";
    	var assigneed = $("#TASKSTATE_SEARCH").val();
    	var subjectLike =  $("[type='search']").val();
    	
    	baseUrl = baseUrl + "assigneed="+assigneed;
    	if(subjectLike != ""){
    		baseUrl = baseUrl + "&subjectLike="+ subjectLike;
    	}
     	var initiator = $("#INITIATOR_SEARCH").val();
     	if(initiator != ""){
     		baseUrl = baseUrl + "&initiator="+ initiator;
     	}
      	
    	//期限
	    var dueDateB = $("#duration_start_dateselect_filter").val(); 
	    if(dueDateB !=""){
	    	baseUrl = baseUrl + "&dueDateB="+ dueDateB;
	    }
	    var dueDateE =$("#duration_end_dateselect_filter").val(); 
	    if(dueDateE !=""){
	    	baseUrl = baseUrl + "&dueDateE="+ dueDateE;
	    }
	    //创建
	    var  createTimeB =  $("#createtime_start_dateselect_filter").val();
	    if(createTimeB != ""){
	    	baseUrl = baseUrl + "&createTimeB="+ createTimeB;
	    }
	    var createTimeE = $("#createtime_end_dateselect_filter").val();
	    if(createTimeE != "")
	    {
	    	baseUrl = baseUrl + "&createTimeE="+ createTimeE;
	    }
	   
     	
//     	var searchURL = +"&initiator="+initiator+"&assigneed="+assigneed
//     					+"&createTimeB="+createTimeB+"&createTimeE="+createTimeE+"&dueDateB="+dueDateB+"&dueDateE="+dueDateE;
     	todoTable.ajax.url(baseUrl).load();
    };
};

