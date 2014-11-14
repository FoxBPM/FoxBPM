
var doneTaskUrl = _serviceUrl + "runtime/tasks?assignee="+_userId+"&candidateUser="+_userId+"&ended=true";
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
						},  { data: 'createTime' },
				        { data: 'endTime' },
						{
							data : 'initatorName'
						}, {
							"orderable" : false,
							"data" : 'bizKey',
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
									"targets" : [ 1 ],
									"createdCell" : function(td, cellData,
											rowData, row, col) {
										//任务主题避免这行
										if(cellData.length>20){
											$(td).html("<span data-original-title='"+cellData+"' rel='tooltip'>"+cellData.substring(0,20)+"<b> . . .</b>"+"</span>");
										}else{
											$(td).html(cellData);
										}
										
										
									}
								},
								{
									"targets" : [ 2 ],
									"orderable" : true,
									"createdCell" : function(td, cellData,
											rowData, row, col) {

										if (rowData.priority == "20") {
											$(td).html("<span class='badge bg-color-blueLight'>非常低</span>");
										}
										if (rowData.priority == "40") {
											$(td).html("<span class='badge bg-color-blue'>低</span>");
										}
										if (rowData.priority == "50") {
											$(td).html("<span class='badge bg-color-greenLight'>一般</span>");
										}
										if (rowData.priority == "80") {
											$(td).html("<span class='badge bg-color-redLight'>高</span>");
										}
										if (rowData.priority == "100") {
											$(td).html("<span class='badge bg-color-red'>非常高</span>");
										}
									}
								},
								{
									"targets" : [ 7 ],
									"orderable" : true,
									"createdCell" : function(td, cellData,
											rowData, row, col) { 
										var tdHtml ="<a class='btn btn-default btn-xs' href='javascript:void(0);' onclick=openTaskForm('"+rowData.formUri+"','"+cellData+"','"+rowData.id+"','"+rowData.processInstanceId+"');><i class='fa fa-pencil-square-o'></i> 查看</a>"+
											"   <a class='btn btn-default btn-xs' href='javascript:void(0);' onclick=showDiagram('"+rowData.processDefinitionKey+"','"+rowData.processInstanceId+"');><i class='fa fa-sitemap'></i> 流程图</a>";
										$(td).html(tdHtml);
									
										
										

									}
								}
								],

						"processing" : true,
						"orderable" : true,
						"serverSide" : true,
						"ajax": {
				            "url": doneTaskUrl,
				            "async":false,
				            "data": function ( d ) {
				                d.ended = true; 
				            }
				        },
						"sDom" : "<'dt-toolbar'<'col-sm-6 col-xs-12 hidden-xs'f><'col-sm-6 col-xs-12 hidden-xs'>C>"+
								 "t"+
								 "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-sm-6 col-xs-12'p l>>",
						"autoWidth" : true,
						"colVis": {
				            "buttonText": "展 示 列",
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
		                    "sSearch": "_INPUT_ "+
                    		"&nbsp;&nbsp;<input placeholder='创建起始'id='createtime_start_dateselect_filter' type='text'  class='form-control datepicker' data-dateformat='yy-mm-dd' style='width:11%'>" +
                    		"<label for='createtime_start_dateselect_filter' class='glyphicon glyphicon-calendar no-margin padding-top-15' rel='tooltip' title='' style='left:-72.2%;top:-5px'></label>" +
                    		" - " +
                    		"<input placeholder='创建终止' id='createtime_end_dateselect_filter' type='text' value='' class='form-control datepicker' data-dateformat='yy-mm-dd' style='width:11%'>"+
                    		"<label for='createtime_end_dateselect_filter' class='glyphicon glyphicon-calendar no-margin padding-top-15' rel='tooltip' title=''  style='left:-59%;top:-5px'></label> " +
                    		"&nbsp;&nbsp;<input placeholder='完成起始' id='complete_start_dateselect_filter' type='text'  class='form-control datepicker' data-dateformat='yy-mm-dd' style='width:11%'>"+
                    		"<label for='complete_start_dateselect_filter' class='glyphicon glyphicon-calendar no-margin padding-top-15' rel='tooltip' title=''  style='left:-46%;top:-5px'></label>" +
                    		" - "+
                    		"<input placeholder='完成终止' id='complete_end_dateselect_filter' type='text'  class='form-control datepicker' data-dateformat='yy-mm-dd' style='width:11%'>"+
                    		"<label for='complete_end_dateselect_filter' class='glyphicon glyphicon-calendar no-margin padding-top-15' rel='tooltip' title='' style='left:-33%;top:-5px'></label>"+
                    		"&nbsp;&nbsp;<a class='btn btn-primary' style='height: 30px; disabled: true;' onclick='searchTodoTask();' href='javascript:void(0);'>搜索</a>&nbsp;&nbsp;<a class='btn btn-primary' style='height: 30px; disabled: true;' href='javascript:void(0);' onclick='clearCondition();'>重置</a></div></form>"

				        },
				        "bStateSave" : false,
				        "bAutoWidth": true,
				        "sPaginationType":'full_numbers',
				        "drawCallback":function(){
							//调整页面布局
							$("#datatable_col_reorder_length").css("padding-right","10px");
							$(".dt-toolbar").css("padding-bottom","6px");
							$(".dt-toolbar").css("padding-right","64px");
							$("#datatable_col_reorder_filter").css("width","1200px");
							$("[type='search']").attr("placeholder","主题/发起人");
							//屏蔽搜索框所有事件
							$("#datatable_col_reorder_filter input[type=search]").unbind();
							pageSetUp();	
						}

					});
	 
		doneTable.column(3).order( 'desc' );
		clearCondition = function (){
			$("[type='search']").val("");
			$("#INITIATOR_SEARCH").val("");
			$("#createtime_end_dateselect_filter").val("");
			$("#createtime_start_dateselect_filter").val("");
			$("#complete_start_dateselect_filter").val("");
			$("#complete_end_dateselect_filter").val(""); 
			 doneTable.ajax.url(doneTaskUrl).load();
		};
	     searchTodoTask = function() {
	    	var baseUrl = doneTaskUrl;
	    	var subjectLike =  $("[type='search']").val();
	    	baseUrl = baseUrl + "?ended=true";
	    	if(subjectLike != ""){
	    		baseUrl = baseUrl + "&subjectLike="+ subjectLike;
	    	}
	     	var initiator = "";//$("#INITIATOR_SEARCH").val();
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

function openTaskForm(url,dataId,taskId,processInstanceId){
	//测试时暂时用报销的表单代替
	url = "portal/expense/editExpense.jsp";
	var formUrl = url+"?dataId="+dataId+"&taskId="+taskId+"&processInstanceId="+processInstanceId+"&refresh="+new Date();
	openModalForm(formUrl);
}

function showDiagram(processDefinitionKey,processInstanceId){ 
	window.open(_WEBPATH+"portal/taskCommand/showTaskDetailInfor.jsp?processDefinitionKey="+processDefinitionKey+"&processInstanceId="+processInstanceId);
}
