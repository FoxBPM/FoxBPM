var pagefunction = function() {
	var doneTable = $('#datatable_col_reorder')
			.DataTable(
					{
						columns : [ {
							data : 'subject'
						}, {
							data : 'priority'
						}, {
							data : 'createTime'
						},  { data: 'dueDate' },
				        { data: 'endTime' },
						{
							data : 'processInitiator'
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
									"targets" : [ 1 ],
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
									"targets" : [ 3 ],
									"orderable" : true,
									"createdCell" : function(td, cellData,
											rowData, row, col) {
										if(cellData != ""){
											$(td).html("<img width='20' height='20' class='online' src='/foxbpm-webapps-common/service/identity/users/"+cellData+"/picture'/> ("+cellData+")");
											
										}
									}
								} 
								],

						"processing" : true,
						"orderable" : true,
						"serverSide" : true,
						"ajax": {
				            "url": "/foxbpm-webapps-common/service/tasks",
				            "data": function ( d ) {
				                d.ended = true; 
				            }
				        },
						"sDom" : "<'dt-toolbar'<'col-sm-6 col-xs-12 hidden-xs'><'col-sm-6 col-xs-12 hidden-xs'C>>"+
								 "t"+
								 "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-sm-6 col-xs-12'p l>>",
						"autoWidth" : true,
						"colVis": {
				            "buttonText": "选择展示的列",
				            "restore": "恢复默认展示",
				            "showAll": "展示所有列",
				            "showNone": "不展示列",
				            "groups": [
				                     {
				                         title: "领到查看",
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
				        "drawCallback":function(){
							//调整页面布局
							$("#datatable_col_reorder_length").css("padding-right","10px");
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
	    	var subjectLike =  $("#SUBJECT_SEARCH").val();
	    	
	    	baseUrl = baseUrl + "ended=true";
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
		    var  createTimeB =  $("#create_start_dateselect_filter").val();
		    if(createTimeB != ""){
		    	baseUrl = baseUrl + "&createTimeB="+ createTimeB;
		    }
		    var createTimeE = $("#create_end_dateselect_filter").val();
		    if(createTimeE != "")
		    {
		    	baseUrl = baseUrl + "&createTimeE="+ createTimeE;
		    }
	     	
		    doneTable.ajax.url(baseUrl).load();
	     };
};
