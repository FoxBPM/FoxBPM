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
							data : 'createTime'
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
						"sDom" : "<'dt-toolbar'<'col-sm-6 col-xs-12 hidden-xs'><'col-sm-6 col-xs-12 hidden-xs'C>>"+
								 "t"+
								 "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-sm-6 col-xs-12'p  l >>",
						"autoWidth" : true,
						"colVis": {
				            "buttonText": "选择展示的列",
				            "restore": "恢复默认展示",
				            "showAll": "展示所有列",
				            "showNone": "不展示列",
				            "groups": [
				                     {
				                         title: "领导查看",
				                         columns: [ 0, 3, 4 ]
				                     }
				                 ]
				        },
				        "oLanguage": {
		                    "sProcessing": "正在加载中......",
		                    "sZeroRecords": "对不起，查询不到相关数据！",
		                    "sEmptyTable": "表中无数据存在！",
		                    "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
		                    "sInfoFiltered": "数据表中共为 _MAX_ 条记录",
		                    "sSearch": "搜索",
		                    "oPaginate": {
		                        "sFirst": "首页",
		                        "sPrevious": "上一页",
		                        "sNext": "下一页",
		                        "sLast": "末页"
		                    }
				        },
				        "bStateSave" : true,
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

							
						}

					});
	 
    $('input').on( 'change', function () {
    	searchTodoTask();
    });
    $('#TASKSTATE_SEARCH').on( 'change', function () {
    	searchTodoTask();
    });
    
     searchTodoTask = function() {
    	var baseUrl = "/foxbpm-webapps-common/service/tasks?";
    	var assigneed = $("#TASKSTATE_SEARCH").val();
    	var subjectLike =  $("#SUBJECT_SEARCH").val();
    	
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
