var calendarpartpagefunction = function() {
	$('#datatable_col_reorder')
			.dataTable(
					{
						columns : [ {
							data : 'id'
						}, {
							data : 'amorpm'
						}, {
							data : 'starttime'
						}, {
							data : 'endtime'
						}, {
							data : 'ruleid'
						}, {
							
						} ],

						columnDefs : [ {
							"render" : function(data, type, row) {
								 return "<a href='javascript:void(0);' onclick='partaddfunc(\""+ row.id + "\")' class='btn btn-danger'>删除</a>" +
								 		"<a onclick='parteditFun(\""+ row.id + "\")' class='btn btn-success'>编辑</a>";
							},
							"targets" : 5
						}

						],

						"processing" : true,
						"orderable" : true,
						"serverSide" : true,
						"ajax" : "/foxbpm-webapps-common/service/workcal/calendarpart",
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
							"sSearch" : "_INPUT_&nbsp;&nbsp;<a class='btn btn-primary' style='height: 30px; disabled: true;' href='javascript:void(0);'>搜索</a>&nbsp;&nbsp;<a class='btn btn-primary' style='height: 30px; disabled: true;' <a href='ajaxpage/form-calendarpart.html' data-toggle='modal' data-target='#remoteModalAdd' class='btn btn-success btn-lg pull-right header-btn hidden-mobile'>新增</a>",
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

function partaddfunc(id) {
	if (id != null) {
		$.ajax({
			type : "DELETE",
			url : "/foxbpm-webapps-common/service/workcal/calendarpart/" + id,
			dataType : "json"
		});
	}
}
function parteditFun(id){
	$.ajax({
		type : "GET",
		url : "/foxbpm-webapps-common/service/workcal/calendarpart/" + id,
		dataType : "json",
		success:function(data){
			 $("#partId").val(data.id);
             $("#partAmorpm").val(data.amorpm);
             $("#partStarttime").val(data.starttime);
             $("#partEndtime").val(data.endtime);
             $("#partRuleid").val(data.ruleid);
             $('#remoteModal').modal('show');
		}
	});
}


