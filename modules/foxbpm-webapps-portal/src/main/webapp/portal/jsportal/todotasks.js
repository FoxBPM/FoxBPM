
var pagefunction = function() {
	var responsiveHelper_datatable_fixed_column = undefined;
	var breakpointDefinition = {
			tablet : 1024,
			phone : 480
		};
	
	var dateInputWidth = "11%";
	var dateLeft1 = "-66.3%";
	var dateLeft2 = "-53.3%";
	var dateLeft3 = "-40.3%";
	var dateLeft4 = "-27.3%";
	var subjectWidth = "14%";
	var taskStateWidth = "8%";
	if(document.body.clientWidth ==1024){
		dateInputWidth = "9%";
		dateLeft1 = "-73.2%";
		dateLeft2 = "-62%";
		dateLeft3 = "-51%";
		dateLeft4 = "-40.3%";
		subjectWidth = "10%";
		taskStateWidth = "7%";
	}
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
							"data" : 'bizKey',
							"defaultContent" : ""
						}],
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
											$(td).html(cellData.substring(0,24));
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
										var tdHtml = "<a class='btn btn-default btn-xs' href='javascript:void(0);' onclick=showForm('"+cellData+"','"+rowData.id+"','"+rowData.processInstanceId+"');><i class='fa fa-pencil-square-o'></i> 表单</a>"+
										"    <a class='btn btn-default btn-xs' href='javascript:void(0);' onclick=showDiagram('"+rowData.processDefinitionKey+"','"+rowData.processInstanceId+"');><i class='fa fa-sitemap'></i> 流程图</a>";
										$(td).html(tdHtml);
									 
									}
								}
								],

						"processing" : true,
						"orderable" : true,
						"serverSide" : true,
						"ajax" : _serviceTaskUrl,
						"sDom" : "<'dt-toolbar'<'col-sm-6 col-xs-12 hidden-xs'f><'col-sm-6 col-xs-12 hidden-xs'>C>"+
								 "t"+
								 "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-sm-6 col-xs-12'p  l >>",
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

		                    "oPaginate": {
		                        "sFirst": "首页",
		                        "sPrevious": "上一页",
		                        "sNext": "下一页",
		                        "sLast": "末页"
		                    },
		                    "sSearch": "_INPUT_" +
		                    		"&nbsp;&nbsp;<select id='TASKSTATE_SEARCH' class='form-control' style='width:"+taskStateWidth+"'><option value='0'>未领取</option><option value='1'>领取</option><option value='2' selected>所有</option></select>" +
		                    		"&nbsp;&nbsp;<input placeholder='创建起始' id='createtime_start_dateselect_filter' type='text'  class='form-control datepicker' data-dateformat='yy-mm-dd' style='width:"+dateInputWidth+"'>" +
		                    		"<label for='createtime_start_dateselect_filter' class='glyphicon glyphicon-calendar no-margin padding-top-15' rel='tooltip' title='' style='left:"+dateLeft1+";top:-5px'></label>" +
		                    		" - " +
		                    		"<input placeholder='创建终止' id='createtime_end_dateselect_filter' type='text' value='' class='form-control datepicker' data-dateformat='yy-mm-dd' style='width:"+dateInputWidth+"'>"+
		                    		"<label for='createtime_end_dateselect_filter' class='glyphicon glyphicon-calendar no-margin padding-top-15' rel='tooltip' title=''  style='left:"+dateLeft2+";top:-5px'></label> " +
		                    		"&nbsp;&nbsp;<input placeholder='期限起始' id='duration_start_dateselect_filter' type='text'  class='form-control datepicker' data-dateformat='yy-mm-dd' style='width:"+dateInputWidth+"'>"+
		                    		"<label for='duration_start_dateselect_filter' class='glyphicon glyphicon-calendar no-margin padding-top-15' rel='tooltip' title=''  style='left:"+dateLeft3+";top:-5px'></label>" +
		                    		" - "+
		                    		"<input placeholder='期限终止'  id='duration_end_dateselect_filter' type='text'  class='form-control datepicker' data-dateformat='yy-mm-dd' style='width:"+dateInputWidth+"'>"+
		                    		"<label for='duration_end_dateselect_filter' class='glyphicon glyphicon-calendar no-margin padding-top-15' rel='tooltip' title='' style='left:"+dateLeft4+";top:-5px'></label>"+
		                    		"&nbsp;&nbsp;<a class='btn btn-primary' style='height: 30px; disabled: true;' onclick='searchTodoTask();' href='javascript:void(0);'>搜索</a>&nbsp;&nbsp;<a class='btn btn-primary' style='height: 30px; disabled: true;' onclick='clearCondition();' href='javascript:void(0);'>重置</a>"
								
				        },
				        "bStateSave" : false,
				        "bAutoWidth": true,
				        "sPaginationType":'full_numbers',
				        "preDrawCallback" : function() {
							if (!responsiveHelper_datatable_fixed_column) {
								//responsiveHelper_datatable_fixed_column = new ResponsiveDatatablesHelper($('#datatable_fixed_column'), breakpointDefinition);
							}
						},
						"rowCallback" : function(nRow) {
							//responsiveHelper_datatable_fixed_column.createExpandIcon(nRow);
						},
						"drawCallback" : function(oSettings) {
							responsiveHelper_datatable_fixed_column.respond();
						},
						"drawCallback":function(){
							//调整页面布局
							$("#datatable_fixed_column_length").css("padding-right","10px");
							$(".dt-toolbar").css("padding-bottom","6px");
							$(".dt-toolbar").css("padding-right","64px");
							$("#datatable_fixed_column_filter").css("width","1200px");
							$("[type='search']").attr("placeholder","主题/发起人");
							$("[type='search']").css("width",subjectWidth);
							$(".ColVis").css("visible","true");
							
							
//							var itemnumberInfo = $("#datatable_fixed_column_info").html();
//							var totalNumber = itemnumberInfo.substring(itemnumberInfo.indexOf("共")+1,itemnumberInfo.indexOf("条记录"));
//							$("#todo_item_number").html(totalNumber);
							pageSetUp();						
						},
						
					});
     
	 todoTable.column(5).order( 'desc' );
	 clearCondition = function (){
		$("[type='search']").val("");
		$("#INITIATOR_SEARCH").val("");
		$("#TASKSTATE_SEARCH").val("2");
		$("#duration_start_dateselect_filter").val("");
		$("#createtime_end_dateselect_filter").val("");
		$("#duration_end_dateselect_filter").val(""); 
		$("#createtime_start_dateselect_filter").val(""); 
		todoTable.ajax.url(_serviceTaskUrl).load();
	};
     searchTodoTask = function() {
    	var baseUrl = _serviceTaskUrl+"?";
    	var assigneed = $("#TASKSTATE_SEARCH").val();
    	var subjectLike =  $("[type='search']").val();
    	
    	baseUrl = baseUrl + "assigneed="+assigneed;
    	if(subjectLike != ""){
    		baseUrl = baseUrl + "&subjectLike="+ subjectLike;
    	}
     	var initiator = "";//$("[type='search']").val();
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

