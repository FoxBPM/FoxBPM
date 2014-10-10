function Calendarrule(config) {
	this._config = config;

	// 私有函数定义
	// 公共函数定义
	this.loadData=function(){
	};
};

/**
 * 定义类成员
 */
Calendarrule.prototype = {
	init : function() {
	}
};

var ruledataTable = null;
var selectedId = null;
function calendarrulepagefunction() {
	ruledataTable = $('#datatable_col_reorder')
			.DataTable(
					{
						initComplete:function(){
						$('#datatable_col_reorder tbody').on( 'click', 'tr', function () {
							var flag = $(this).attr("flag");
							$("#editrule").removeAttr("disabled");
							$("#deleterule").removeAttr("disabled");
							ruledataTable.$('tr').children().attr("style","");
							if(1 == flag){
								$(this).children().attr("style","background-color:#b0bed9");
								selectedId = $(this).children()[0].innerText;
							    $(this).attr("flag",0);
							}
							else {
								$(this).children().attr("style","background-color:#b0bed9");
								selectedId = $(this).children()[0].innerText;
							    $(this).attr("flag",1);
							}
							
						} );
						
						 $("#editrule").attr('disabled',"true");
						 $("#deleterule").attr('disabled',"true");
						 $("[type='search']").attr("placeholder","名称");
						 $("[type='search']").val("");
						},
						
						columns : [ {
							data : 'id'
						}, {
							data : 'year'
						}, {
							data : 'week'
						}, {
							data : 'name'
						}, {
							data : 'workdate'
						}, {
							data : 'status'
						}, {
							data : 'typeid'
						}
						],

						columnDefs : [ {
							"targets" : [ 2 ],
							"orderable" : false,
							"createdCell" : function(td, cellData,
									rowData, row, col) {

								if (rowData.week == "0") {
									$(td).html("<span class='badge'>无</span>");
								}
								if (rowData.week == "1") {
									$(td).html("<span class='badge bg-color-greenLight'>周一</span>");
								}
								if (rowData.week == "2") {
									$(td).html("<span class='badge bg-color-greenLight'>周二</span>");
								}
								if (rowData.week == "3") {
									$(td).html("<span class='badge bg-color-greenLight'>周三</span>");
								}
								if (rowData.week == "4") {
									$(td).html("<span class='badge bg-color-greenLight'>周四</span>");
								}
								if (rowData.week == "5") {
									$(td).html("<span class='badge bg-color-greenLight'>周五</span>");
								}
								if (rowData.week == "6") {
									$(td).html("<span class='badge bg-color-red'>周六</span>");
								}
								if (rowData.week == "7") {
									$(td).html("<span class='badge bg-color-red'>周日</span>");
								}
							}
						},
							
						{
							"targets" : [ 5 ],
							"orderable" : false,
							"createdCell" : function(td, cellData,
									rowData, row, col) {

								if (rowData.status == "0") {
									$(td).html("<span class='badge'>工作时间</span>");
								}
								if (rowData.status == "1") {
									$(td).html("<span class='badge bg-color-greenLight'>假期时间</span>");
								}
							}
//							"render" : function(data, type, row) {
//								 return "<a href='javascript:void(0);' onclick='ruleaddfunc(\""+ row.id + "\")' class='btn btn-danger'>删除</a>" +
//								 		"<a onclick='ruleeditFun(\""+ row.id + "\")' class='btn btn-success'>编辑</a>";
//							},
//							"targets" : 7
						}

						],

						"processing" : true,
						"orderable" : true,
						"serverSide" : true,
						"ajax" : "/foxbpm-webapps-common/service/workcal/calendarrule",
						"sDom" : "<'dt-toolbar'<'col-sm-6 col-xs-12 hidden-xs'f><'col-sm-6 col-xs-12 hidden-xs'>C>"+
								 "t"+
								 "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-sm-6 col-xs-12'p l>>",
						"autoWidth" : true,

						"colVis" : {
							"buttonText" : "选择展示的列",
							"restore" : "恢复默认展示",
							"showAll" : "展示所有列",
							"showNone" : "不展示列",
							"groups" : [ {
								title : "查看",
								columns : [ 0, 1 ]
							} ]
						},
						"oLanguage" : {
							"sProcessing" : "正在加载中......",
							"sZeroRecords" : "对不起，查询不到相关数据！",
							"sEmptyTable" : "表中无数据存在！",
							"sInfo" : "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
							"sInfoFiltered" : "数据表中共为 _MAX_ 条记录",
							"sSearch" : "_INPUT_&nbsp;&nbsp;<a class='btn btn-primary' style='height: 30px; disabled: true;' href='javascript:void(0);' onclick='searchcalendarrule()'>搜索</a>&nbsp;&nbsp;<a class='btn btn-primary' style='height: 30px; disabled: true;' <a href='javascript:void(0);' onclick='addrulefunc()'  class='btn btn-success btn-lg pull-right header-btn hidden-mobile'>新增</a>&nbsp;&nbsp;<a href='javascript:void(0);' onclick='editrulefunc()' id='editrule' class='btn btn-primary' style='height: 30px; disabled: true;'>编辑</a>&nbsp;&nbsp;<a href='javascript:void(0);' onclick='deleterulefunc()' id='deleterule' class='btn btn-primary' style='height: 30px; disabled: true;'>删除</a>&nbsp;&nbsp;<a class='btn btn-primary' style='height: 30px; disabled: true;' onclick='clearrule();' href='javascript:void(0);'>重置</a>",
							"oPaginate" : {
								"sFirst" : "首页",
								"sPrevious" : "上一页",
								"sNext" : "下一页",
								"sLast" : "末页"
							}
						},
						"bStateSave" : true,
						"sPaginationType":'full_numbers',
						"drawCallback":function(){
							//屏蔽搜索框所有事件
							$("#datatable_col_reorder_filter input[type=search]").unbind();
						}
					});
	
	
};

function deleterulefunc() {
	if (selectedId != null) {
		$.ajax({
			type : "DELETE",
			url : "/foxbpm-webapps-common/service/workcal/calendarrule/" + selectedId,
			dataType : "text",
			success:function(data){ //提交成功的回调函数  
                /* alert(data.result); */
                 $('#remoteModal').modal('hide');
                
                 $.smallBox({ 
            			title : '提示!',
            			content : '删除成功！',
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
        			content : '删除失败',
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
};

function editrulefunc(){
	$.ajax({
		type : "GET",
		url : "/foxbpm-webapps-common/service/workcal/calendarrule/" + selectedId,
		dataType : "json",
		success:function(data){
			 $("#ruleId").val(data.id);
             $("#ruleYear").val(data.year);
             $("#ruleWeek").val(data.week);
             $("#ruleName").val(data.name);
             $("#ruleWorkdate").val(data.workdate);
             $("#ruleStatus").val(data.status);
             $("#ruleTypeid").val(data.typeid);
             $("#login-form").attr("op", "0");
             $('#remoteModal').modal('show');
		}
	});
};

function addrulefunc() {
	$("#login-form").validate().resetForm();
	 $("#ruleId").val("");
     $("#ruleYear").val("");
     $("#ruleWeek").val(0);
     $("#ruleName").val("");
     $("#ruleWorkdate").val("");
     $("#ruleStatus").val("0");
     $("#ruleTypeid").val("");
	$("#login-form").attr("op", "1");
	$('#remoteModal').modal('show');
};

function searchcalendarrule() {
	var rulename =  $("[type='search']").val();
	ruleurl = "/foxbpm-webapps-common/service/workcal/calendarrule" + "?name=" + rulename;
	ruledataTable.ajax.url(ruleurl).load();
	 $("#editrule").attr('disabled',"true");
	 $("#deleterule").attr('disabled',"true");
};

function clearrule() {
	$("[type='search']").val("");
	ruledataTable.ajax.url("/foxbpm-webapps-common/service/workcal/calendarrule").load();
	 $("#editrule").attr('disabled',"true");
	 $("#deleterule").attr('disabled',"true");
};