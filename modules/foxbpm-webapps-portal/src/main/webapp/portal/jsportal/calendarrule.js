var pagefunction = function() {
	$('#datatable_col_reorder')
			.dataTable(
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
							"orderable" : false,
							"data" : null,
							"defaultContent" : ""
						} ],
						
						columnDefs : [
										
										],

						"processing" : true,
						"orderable" : true,
						"serverSide" : true,
						"ajax" : "/foxbpm-webapps-common/service/workcal/calendarrule",
						"sDom" : "<'dt-toolbar'<'col-sm-6 col-xs-12 hidden-xs'f><'col-sm-6 col-xs-12 hidden-xs'lC>>"+
								 "t"+
								 "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-sm-6 col-xs-12'p>>",
						"autoWidth" : true,
						
						"colVis": {
				            "buttonText": "选择展示的列",
				            "restore": "恢复默认展示",
				            "showAll": "展示所有列",
				            "showNone": "不展示列",
				            "groups": [
				                     {
				                         title: "查看",
				                         columns: [ 0, 1 ]
				                     }
				                 ]
				        },
				        "oLanguage": {
		                    "sProcessing": "正在加载中......",
		                    "sLengthMenu": "每页显示 _MENU_ 条记录",
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
				        "bStateSave" : true

					});
};
