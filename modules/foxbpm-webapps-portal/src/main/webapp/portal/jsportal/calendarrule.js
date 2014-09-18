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

function calendarrulepagefunction() {
	ruledataTable = $('#datatable_col_reorder')
			.DataTable(
					{
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
						}, {
							
						} ],

						columnDefs : [ {
							"render" : function(data, type, row) {
								 return "<a href='javascript:void(0);' onclick='ruleaddfunc(\""+ row.id + "\")' class='btn btn-danger'>删除</a>" +
								 		"<a onclick='ruleeditFun(\""+ row.id + "\")' class='btn btn-success'>编辑</a>";
							},
							"targets" : 7
						}

						],

						"processing" : true,
						"orderable" : true,
						"serverSide" : true,
						"ajax" : "/foxbpm-webapps-common/service/workcal/calendarrule",
						"sDom" : "<'dt-toolbar'<'col-sm-6 col-xs-12 hidden-xs'f><'col-sm-6 col-xs-12 hidden-xs'lC>>"
								+ "t"
								+ "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-sm-6 col-xs-12'p>>",
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
							"sLengthMenu" : "每页显示 _MENU_ 条记录",
							"sZeroRecords" : "对不起，查询不到相关数据！",
							"sEmptyTable" : "表中无数据存在！",
							"sInfo" : "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
							"sInfoFiltered" : "数据表中共为 _MAX_ 条记录",
							"sSearch" : "_INPUT_&nbsp;&nbsp;<a class='btn btn-primary' style='height: 30px; disabled: true;' href='javascript:void(0);'>搜索</a>&nbsp;&nbsp;<a class='btn btn-primary' style='height: 30px; disabled: true;' <a href='javascript:void(0);' onclick='addrule()' class='btn btn-success btn-lg pull-right header-btn hidden-mobile'>新增</a>",
							"oPaginate" : {
								"sFirst" : "首页",
								"sPrevious" : "上一页",
								"sNext" : "下一页",
								"sLast" : "末页"
							}
						},
						"bStateSave" : true

					});
	
	
};

function ruleaddfunc(id) {
	if (id != null) {
		$.ajax({
			type : "DELETE",
			url : "/foxbpm-webapps-common/service/workcal/calendarrule/" + id,
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
            },
            error:function(){
            	ruledataTable.ajax.url("/foxbpm-webapps-common/service/workcal/calendarrule").load();
            	$.smallBox({ 
        			title : '错误!',
        			content : '删除失败',
        			color : "#C46A69",
        			icon : "fa fa-warning shake animated",
        			timeout : 2000
        		});
            }
		});
	}
};

function ruleeditFun(id){
	$.ajax({
		type : "GET",
		url : "/foxbpm-webapps-common/service/workcal/calendarrule/" + id,
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

function addrule() {
	 $("#ruleId").val("");
     $("#ruleYear").val("");
     $("#ruleWeek").val(1);
     $("#ruleName").val("");
     $("#ruleWorkdate").val("");
     $("#ruleStatus").val("");
     $("#ruleTypeid").val("");
	$("#login-form").attr("op", "1");
	$('#remoteModal').modal('show');
};
