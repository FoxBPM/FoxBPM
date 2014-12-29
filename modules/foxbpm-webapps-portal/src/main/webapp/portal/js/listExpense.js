
var doneTaskUrl = "listExpense.action?pageIndex=1&pageSize=10";
var pagefunction = function() {
	var doneTable = $('#datatable_col_reorder')
			.DataTable(
					{
						columns : [
							{data : 'expenseId'}, 
							{data : 'ownerName'}, 
							{data : 'deptName'},
							{data : 'account'},  
							{ data: 'invoiceType' },
							{ data: 'processInfo' },
					        { data: 'createTime' }
						],
						columnDefs : [
								{
									"targets" : [ 3 ],
									"orderable" : false,
									"createdCell" : function(td, cellData,
											rowData, row, col) {
										$(td).html("$."+rowData.account);
									}
								},
								{
									"targets" : [ 4 ],
									"orderable" : false,
									"createdCell" : function(td, cellData,
											rowData, row, col) {
										if (rowData.invoiceType == "0") {
											$(td).html("<span class='badge'>餐饮费</span>");
										}
										if (rowData.invoiceType == "1") {
											$(td).html("<span class='badge bg-color-greenLight'>住宿费</span>");
										}
										if (rowData.invoiceType == "2") {
											$(td).html("<span class='badge bg-color-red'>车船票</span>");
										}
										if (rowData.invoiceType == "3") {
											$(td).html("<span class='badge bg-color-red'>市内公交</span>");
										}
										if (rowData.invoiceType == "4") {
											$(td).html("<span class='badge bg-color-red'>办公用品</span>");
										}
										if (rowData.invoiceType == "5") {
											$(td).html("<span class='badge bg-color-red'>其他</span>");
										}
									}
								},{
									"targets" : [5],
									"orderable" : false,
									"createdCell" : function(td, cellData,
											rowData, row, col) {
										$(td).html(cellData.processStep);
									}
								}
						],

						"processing" : true,
						"orderable" : true,
						"serverSide" : true,
						"ajax": {
				            "url": doneTaskUrl,
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
		                    }
				        },
				        "bStateSave" : false,
				        "bAutoWidth": true,
				        "sPaginationType":'full_numbers'
					});
	 
		doneTable.column(3).order( 'desc' );
};

