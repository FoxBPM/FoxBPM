var pagefunction = function() {
	var doneTable = $('#datatable_col_reorder')
			.DataTable(
					{
						columns : [{
							data : 'processInitiator'
						}, {
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
							"width":"15%",
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
						"sDom" : "<'dt-toolbar'<'col-sm-6 col-xs-12 hidden-xs'f><'col-sm-6 col-xs-12 hidden-xs'>C>"+
								 "t"+
								 "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-sm-6 col-xs-12'p l>>",
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
		                    "sSearch": "搜索",
		                    "oPaginate": {
		                        "sFirst": "首页",
		                        "sPrevious": "上一页",
		                        "sNext": "下一页",
		                        "sLast": "末页"
		                    },
		                    "sSearch": "<form>主题：_INPUT_ 发起人：<input id='INITIATOR_SEARCH' type='text' class='form-control' value=''  style='width:8%' />" +
                    		"创建：<input id='createtime_start_dateselect_filter' type='text'  class='form-control datepicker' data-dateformat='yy-mm-dd' style='width:8%'>" +
                    		"<label for='createtime_start_dateselect_filter' class='glyphicon glyphicon-calendar no-margin padding-top-15' rel='tooltip' title='' style='left:-63%;top:-5px'></label>" +
                    		" - " +
                    		"<input id='createtime_end_dateselect_filter' type='text' value='' class='form-control datepicker' data-dateformat='yy-mm-dd' style='width:8%'>"+
                    		"<label for='createtime_end_dateselect_filter' class='glyphicon glyphicon-calendar no-margin padding-top-15' rel='tooltip' title=''  style='left:-53%;top:-5px'></label> " +
                    		"完成：<input id='complete_start_dateselect_filter' type='text'  class='form-control datepicker' data-dateformat='yy-mm-dd' style='width:8%'>"+
                    		"<label for='complete_start_dateselect_filter' class='glyphicon glyphicon-calendar no-margin padding-top-15' rel='tooltip' title=''  style='left:-40.3%;top:-5px'></label>" +
                    		" - "+
                    		"<input id='complete_end_dateselect_filter' type='text'  class='form-control datepicker' data-dateformat='yy-mm-dd' style='width:8%'>"+
                    		"<label for='complete_end_dateselect_filter' class='glyphicon glyphicon-calendar no-margin padding-top-15' rel='tooltip' title='' style='left:-30.2%;top:-5px'></label>"+
                    		"&nbsp;&nbsp;<a class='btn btn-primary' style='height: 30px; disabled: true;' onclick='searchTodoTask();' href='javascript:void(0);'>搜索</a>&nbsp;&nbsp;<a class='btn btn-primary' style='height: 30px; disabled: true;' href='javascript:void(0);' onclick='clearCondition();'>重置</a></div></form>"

				        },
				        "bStateSave" : false,
				        "bAutoWidth": true,
				        "sPaginationType":'full_numbers',
				        "drawCallback":function(){
							//调整页面布局
							$("#datatable_col_reorder_length").css("padding-right","10px");
							$(".dt-toolbar").css("padding-bottom","6px");

							$("#datatable_col_reorder_filter").css("width","1200px");
							$("[type='search']").css("width","10%");
							pageSetUp();	
							
						}

					});
	 
		doneTable.column(1).order( 'asc' );
		clearCondition = function (){
			$("[type='search']").val("");
			$("#INITIATOR_SEARCH").val("");
			$("#createtime_end_dateselect_filter").val("");
			$("#createtime_start_dateselect_filter").val("");
			$("#complete_start_dateselect_filter").val("");
			$("#complete_end_dateselect_filter").val("");
		};
	     searchTodoTask = function() {
	    	var baseUrl = "/foxbpm-webapps-common/service/tasks?";
	    	var subjectLike =  $("[type='search']").val();
	    	baseUrl = baseUrl + "ended=true";
	    	if(subjectLike != ""){
	    		baseUrl = baseUrl + "&subjectLike="+ subjectLike;
	    	}
	     	var initiator = $("#INITIATOR_SEARCH").val();
	     	if(initiator != ""){
	     		baseUrl = baseUrl + "&initiator="+ initiator;
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
	     	
		    //完成
		    var  completeTimeB =  $("#complete_start_dateselect_filter").val();
		    if(completeTimeB != ""){
		    	baseUrl = baseUrl + "&endTimeB="+ completeTimeB;
		    }
		    var completeTimeE = $("#complete_end_dateselect_filter").val();
		    if(completeTimeE != "")
		    {
		    	baseUrl = baseUrl + "&endTimeE="+ completeTimeE;
		    }
		    
		    doneTable.ajax.url(baseUrl).load();
	     };
};
