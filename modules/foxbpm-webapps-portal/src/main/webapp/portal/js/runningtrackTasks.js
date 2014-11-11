var runningTrackTaskUrl = _serviceUrl+"runtime/process-instances?participate="+_userId;
var pagefunction = function() {
	var dateInputWidth = "15%";
	var dateWidth = "-32%";
	if(document.body.clientWidth > 1366){
		dateInputWidth = "17%";
		dateWidth = "-29.5%";
	}
	var runningTrackTable = $('#datatable_col_reorder')
			.DataTable(
					{
						columns : [
								{
									data : 'initiator'
								}, 
								{
									data : 'subject'
								},
								{
									data : 'startTime'
								},
								{
									data : 'updateTime'
								},
								{
									"orderable" : false,
									"data" : 'instanceStatus',
									"defaultContent" : "-"
								},
								{
									data : 'initatorName'
								},
								{
									"orderable" : false,
									"data" : 'processLocationString',
									"defaultContent" : "-"
								},
								{
									"orderable" : false,
									"data" : null,
									"width":"15%",
									"defaultContent" : ""//"<a href='javascript:void(0);' class='btn btn-danger'>查看</a> "
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
										if(cellData.length>24){
											$(td).html("<span data-original-title='"+cellData+"' rel='tooltip'>"+cellData.substring(0,20)+"<b> . . .</b>"+"</span>");
										}else{
											$(td).html(cellData);
										}
										
										
									}
								},
								{
									"targets" : [ 4 ],
									"orderable" : true,
									"createdCell" : function(td, cellData,
											rowData, row, col) {
										if (rowData.endTime == null) {
											$(td)
													.html(
															"<span class='label label-default'>激活</span>");
											if (rowData.isSuspended == 'true') {
												$(td)
														.html(
																"<span class='label label-default'>暂停</span>");
											}
											if (rowData.assignee == null) {
												$(td)
														.html(
																"<span class='label label-default'>未领取</span>");
											}

										} else {
											$(td)
													.html(
															"<span class='label label-default'>完成</span>");
										}

									}
								} ,
								{
									"targets" : [ 7 ],
									"orderable" : true,
									"createdCell" : function(td, cellData,
											rowData, row, col) { 
										var tdHtml = "<a class='btn btn-default btn-xs' href='javascript:void(0);' onclick=openTaskForm('"+rowData.formUriView+"','"+rowData.bizKey+"','"+rowData.id+"');><i class='fa fa-pencil-square-o'></i> 查看</a>"+
											" <a class='btn btn-default btn-xs' href='javascript:void(0);' onclick=showDiagram('"+rowData.processDefinitionKey+"','"+rowData.id+"');><i class='fa fa-sitemap'></i> 流程图</a>";
										$(td).html(tdHtml);
									}
								}],

						"processing" : true,
						"orderable" : true,
						"serverSide" : true,
						"ajax" : {
							"url":runningTrackTaskUrl,
							"async":false
							
						},
						"sDom" : "<'dt-toolbar'<'col-sm-6 col-xs-12 hidden-xs'f><'col-sm-6 col-xs-12 hidden-xs'>C>"
								+ "t"
								+ "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-sm-6 col-xs-12'p l>>",
						"autoWidth" : true,
						"colVis" : {
							"buttonText" : "展 示 列",
							"restore" : "恢复默认展示",
							"showAll" : "展示所有列",
							"showNone" : "不展示列"
						},
						"oLanguage" : {
							"sProcessing" : "正在加载中......",
							"sZeroRecords" : "对不起，查询不到相关数据！",
							"sEmptyTable" : "表中无数据存在！",
							"sInfo" : "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
							"sInfoFiltered" : "数据表中共为 _MAX_ 条记录",
							"sSearch" : "搜索",
							"oPaginate" : {
								"sFirst" : "首页",
								"sPrevious" : "上一页",
								"sNext" : "下一页",
								"sLast" : "末页"
							},
							"sSearch": "_INPUT_ "+
                    		"<select id='TASKSTATE_SEARCH' class='form-control'><option value='0'>未领取</option><option value='1'>领取</option><option value='2' selected>所有</option></select> " +
                    		"&nbsp;&nbsp;<input placeholder='创建起始' id='createtime_start_dateselect_filter' type='text'  class='form-control datepicker' data-dateformat='yy-mm-dd' style='width:"+dateInputWidth+"'>" +
                    		"<label for='createtime_start_dateselect_filter' class='glyphicon glyphicon-calendar no-margin padding-top-15' rel='tooltip' title='' style='left:-50%;top:-5px'></label>" +
                    		" - " +
                    		"<input placeholder='创建终止' id='createtime_end_dateselect_filter' type='text' value='' class='form-control datepicker' data-dateformat='yy-mm-dd' style='width:"+dateInputWidth+"'>"+
                    		"<label for='createtime_end_dateselect_filter' class='glyphicon glyphicon-calendar no-margin padding-top-15' rel='tooltip' title=''  style='left:"+dateWidth+";top:-5px'></label> " +
                    		"&nbsp;<a class='btn btn-primary' style='height: 30px; disabled: true;' onclick='searchTodoTask();' href='javascript:void(0);'>搜索</a>&nbsp;&nbsp;<a class='btn btn-primary' style='height: 30px; disabled: true;' href='javascript:void(0);' onclick='clearCondition();'>重置</a></form>"
						},
						"bStateSave" : false,
						"bAutoWidth" : true,
						"sPaginationType" : 'full_numbers',
						"drawCallback":function(){
							//调整页面布局
							$("#datatable_col_reorder_length").css("padding-right","10px");
							$(".dt-toolbar").css("padding-bottom","6px");
							$(".dt-toolbar").css("padding-right","64px");
							$("#datatable_col_reorder_filter").css("width","900px");
							$("[type='search']").attr("placeholder","主题/发起人");
							//屏蔽搜索框所有事件
							$("#datatable_col_reorder_filter input[type=search]").unbind();
							pageSetUp();		
						}

					});
	runningTrackTable.column(3).order( 'desc' );
	clearCondition = function (){
		$("[type='search']").val("");
		$("#INITIATOR_SEARCH").val("");
		$("#TASKSTATE_SEARCH").val("2");
		$("#createtime_end_dateselect_filter").val("");
		$("#createtime_start_dateselect_filter").val("");
		var baseUrl = runningTrackTaskUrl+"&";
		runningTrackTable.ajax.url(baseUrl).load();
	};
     searchTodoTask = function() {
    	var baseUrl = runningTrackTaskUrl+"&";
    	var assigneed = $("#TASKSTATE_SEARCH").val();
    	var subjectLike =  $("[type='search']").val();
    	baseUrl = baseUrl + "assigneed="+assigneed;
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
     	
	    runningTrackTable.ajax.url(baseUrl).load();
     };
};

function viewForm(formUrl,processInstanceId,bizKey){
	var url = "ajaxpage/viewExpense.jsp?dataId="+bizKey;
	$("#contentFrame").attr("src",url);
	$('#remoteModal').modal({backdrop:"static"});
	
}

function openTaskForm(url,dataId,taskId,processInstanceId){
	//测试时暂时用报销的表单代替
	url = "portal/expense/viewExpense.jsp";
	var formUrl = url+"?dataId="+dataId+"&taskId="+taskId+"&processInstanceId="+processInstanceId+"&refresh="+new Date();
	openModalForm(formUrl);
}

function showDiagram(processDefinitionKey,processInstanceId){ 
	window.open(_WEBPATH+"portal/taskCommand/showTaskDetailInfor.jsp?processDefinitionKey="+processDefinitionKey+"&processInstanceId="+processInstanceId);
}

