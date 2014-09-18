/**
 * 定义日历类型处理类
 */
function Calendartype(config) {
	this._config = config;

	// 私有函数定义
	// 公共函数定义
	this.loadData=function(){
	};
};

/**
 * 定义类成员
 */
Calendartype.prototype = {
	init : function() {
	}
};

var typedataTable = null;
var calendartypepagefunction = function() {
	typedataTable = $('#datatable_col_reorder')
			.DataTable(
					{
						initComplete:function(){
							$('#datatable_col_reorder tbody').on( 'click', 'tr', function () {
							    if ($(this).hasClass('selectedwy')) {
							        $(this).removeClass('selectedwy');
//							        alert($(this).children())
							    }
							    else {
							    	typedataTable.$('tr.selectedwy').removeClass('selectedwy');
							        $(this).addClass('selectedwy');
//							        alert($(this).children())
							    }
							} );
						},
						columns : [ {
							data : 'id'
						}, {
							data : 'name'
						}, {
						// "orderable" : false,
						// "data" : null,
						// "defaultContent" : "<a href='javascript:void(0);'
						// onclick='func(1)' class='btn btn-danger'>删除</a> <a
						// href='ajaxpage/form-calendartype.html?id=" + +"'
						// class='btn btn-success'>编辑</a>"
						} ],

						columnDefs : [ {
							// The `data` parameter refers to the data for the
							// cell (defined by the
							// `data` option, which defaults to the column being
							// worked with, in
							// this case `data: 0`.
							"render" : function(data, type, row) {
								// return "<a href='javascript:void(0);'
								// onclick='func("
								// + row.id
								// + ")' class='btn btn-danger'>删除</a> <a
								// href='javascript:void(0);' onclick='func("
								// + row.id
								// + ")' class='btn btn-success'>编辑</a>";
								return "<a href='javascript:void(0);' onclick='typefunc(\""
										+ row.id
										+ "\")' class='btn btn-danger'>删除</a>"
										+ "<a onclick='typpeeditfun(\""
										+ row.id
										+ "\")' class='btn btn-success'>编辑</a>";
							},
							"targets" : 2
						} ],
						"processing" : true,
						"orderable" : true,
						"serverSide" : true,
						"ajax" : "/foxbpm-webapps-common/service/workcal/calendartype",
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
							"sSearch" : "_INPUT_&nbsp;&nbsp;<a class='btn btn-primary' style='height: 30px; disabled: true;' href='javascript:void(0);'>搜索</a>&nbsp;&nbsp;<a class='btn btn-primary' style='height: 30px; disabled: true;' <a href='javascript:void(0);' onclick='addtype()'  class='btn btn-success btn-lg pull-right header-btn hidden-mobile'>新增</a>",
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


function typefunc(id) {
	if (id != null) {
		$.ajax({
			type : "DELETE",
			url : "/foxbpm-webapps-common/service/workcal/calendartype/" + id,
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
                 typedataTable.ajax.url("/foxbpm-webapps-common/service/workcal/calendartype").load();
            },
            error:function(){
            	typedataTable.ajax.url("/foxbpm-webapps-common/service/workcal/calendartype").load();
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

function typpeeditfun(id) {
	$.ajax({
		type : "GET",
		url : "/foxbpm-webapps-common/service/workcal/calendartype/" + id,
		dataType : "json",
		success : function(data) {
			$("#typeId").val(data.id);
			$("#typeName").val(data.name);
			$("#login-form").attr("op", "0");
			$('#remoteModal').modal('show');
		}
	});
};

function addtype() {
	$("#typeId").val("");
	$("#typeName").val("");
	$("#login-form").attr("op", "1");
	$('#remoteModal').modal('show');
};
