<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en-us">
<!-- 加载头部信箱 -->
<head>
<jsp:include page="../header.jsp" />
</head>
<body class="">
	<jsp:include page="../top.jsp" />
	
	<div class="row">
	<div class="col-xs-12 col-sm-7 col-md-7 col-lg-4">
		<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa fa-home"></i>个人中心</h1>
	</div>
	<div class="col-xs-12 col-sm-5 col-md-5 col-lg-8">
	</div>
</div>
<!-- widget grid -->
<section id="widget-grid" class="">

	<!-- row -->
	<div class="row">

		<article class="col-sm-12 col-md-12 col-lg-6">
			<div class="row">
				<div class="col-sm-12 col-md-12 col-lg-9" style="width:100%">
			
					<!-- new widget -->
					<div class="jarviswidget jarviswidget-color-blueDark">
							<header>
							<span class="widget-icon"> <i class="fa fa-calendar"></i> </span>
							<h2>日历面板 </h2>
							<div class="widget-toolbar">
								<!-- add: non-hidden - to disable auto hide -->
								<div class="btn-group">
									<button id="select_priority" class="btn dropdown-toggle btn-xs btn-default" data-toggle="dropdown">
										优先级<i class="fa fa-caret-down"></i>
									</button>
									<ul class="dropdown-menu js-status-update pull-right">
										<li>
											<a href="javascript:void(0);" id="priority_all">所有</a>
										</li> 
										
										<li>
											<a href="javascript:void(0);" id="priority_veryhigh">非常高</a>
										</li>
										<li>
											<a href="javascript:void(0);" id="priority_high">高</a>
										</li> 
										<li>
											<a href="javascript:void(0);" id="priority_normal">一般</a>
										</li> 
										<li>
											<a href="javascript:void(0);" id="priority_low">低</a>
										</li> 
										<li>
											<a href="javascript:void(0);" id="priority_verylow">非常低</a>
										</li> 
										
										
									</ul>
								</div> &nbsp;&nbsp;&nbsp;
								<div class="btn-group">
									<button id="select_time" class="btn dropdown-toggle btn-xs btn-default" data-toggle="dropdown">
										任务创建<i class="fa fa-caret-down"></i>
									</button>
									<ul class="dropdown-menu js-status-update pull-right">
										<li>
											<a href="javascript:void(0);" id="task_createtime">任务创建</a>
										</li>
										<li>
											<a href="javascript:void(0);" id="task_duration">任务期限</a>
										</li> 
									</ul>
								</div> &nbsp;&nbsp;&nbsp;
								<div class="btn-group">
									<button id="select_view" class="btn dropdown-toggle btn-xs btn-default" data-toggle="dropdown">
										周-日程表<i class="fa fa-caret-down"></i>
									</button>
									<ul class="dropdown-menu js-status-update pull-right">
										<li>
											<a href="javascript:void(0);" id="mt">月 视图</a>
										</li>
										<li>
											<a href="javascript:void(0);" id="basicWeek">周 视图</a>
										</li>
										<li>
											<a href="javascript:void(0);" id="basicDay">日 视图</a>
										</li>
										<li>
											<a href="javascript:void(0);" id="ag">周-日程表</a>
										</li>
										<li>
											<a href="javascript:void(0);" id="td">日-日程表</a>
										</li>
									</ul>
								</div>
							</div>
						</header>
			
						<!-- widget div-->
						<div>
			
							<div class="widget-body no-padding"> 
										<div class="btn-group" style="padding-top:5px;padding-left:5px">
											<a href="javascript:void(0)" class="btn btn-default btn-xs" id="btn-prev"><i class="fa fa-chevron-left"></i></a>
											<a href="javascript:void(0)" class="btn btn-default btn-xs" id="btn-next"><i class="fa fa-chevron-right"></i></a>
										</div> 
								<div id="calendar" style="padding-top:6px"></div>
			
								<!-- end content -->
							</div>
			
						</div>
						<!-- end widget div -->
					</div>
					<!-- end widget -->
			
				</div>
			
			</div>
			<!-- end widget -->

		</article>

		<article class="col-sm-12 col-md-12 col-lg-6">

			<!-- new widget -->
			<div class="jarviswidget jarviswidget-color-blue" id="wid-id-4" data-widget-editbutton="false" >

				<!-- widget options:
				usage: <div class="jarviswidget" id="wid-id-0" data-widget-editbutton="false">

				data-widget-colorbutton="false"
				data-widget-editbutton="false"
				data-widget-togglebutton="false"
				data-widget-deletebutton="false"
				data-widget-fullscreenbutton="false"
				data-widget-custombutton="false"
				data-widget-collapsed="true"
				data-widget-sortable="false"

				-->

				<header>
					<span class="widget-icon"> <i class="fa fa-check txt-color-white"></i> </span>
					<h2>待办任务</h2>
				</header>

				<!-- widget div-->
				<div>
					<!-- widget edit box -->
					<div class="jarviswidget-editbox">
						<div>
							<label>Title:</label>
							<input type="text" />
						</div>
					</div>
					<!-- end widget edit box -->

					<div class="widget-body no-padding smart-form">
						<!-- content goes here -->
						<!-- <h5 class="todo-group-title"><i class="fa fa-warning"></i> Critical Tasks (<small class="num-of-tasks">1</small>)</h5>
						<ul id="sortable1" class="todo">
							<li>
								<span class="handle"> <label class="checkbox">
										<input type="checkbox" name="checkbox-inline">
										<i></i> </label> </span>
								<p>
									<strong>Ticket #17643</strong> - Hotfix for WebApp interface issue [<a href="javascript:void(0);" class="font-xs">More Details</a>] <span class="text-muted">Sea deep blessed bearing under darkness from God air living isn't. </span>
									<span class="date">Jan 1, 2014</span>
								</p>
							</li>
						</ul> -->
						<!-- <h5 class="todo-group-title"><i class="fa fa-exclamation"></i> Important Tasks (<small class="num-of-tasks">3</small>)</h5> -->
						<ul id="todotasks" class="todo" style='width:100%;height:275px;overflow:auto;overflow-x:hidden;'>
						</ul>
                        <span style="float: right;padding-right: 5px;"><a id="moreDetail" href="javascript:void(0);">更多</a></span>
						<!-- <h5 class="todo-group-title"><i class="fa fa-check"></i> Completed Tasks (<small class="num-of-tasks">1</small>)</h5>
						<ul id="sortable3" class="todo">
							<li class="complete">
								<span class="handle"> <label class="checkbox state-disabled" style="display:none">
										<input type="checkbox" name="checkbox-inline" checked="checked" disabled="disabled">
										<i></i> </label> </span>
								<p>
									<strong>Ticket #17643</strong> - Hotfix for WebApp interface issue [<a href="javascript:void(0);" class="font-xs">More Details</a>] <span class="text-muted">Sea deep blessed bearing under darkness from God air living isn't. </span>
									<span class="date">Jan 1, 2014</span>
								</p>
							</li>
						</ul>
 -->
						<!-- end content -->
					</div>

				</div>
				<!-- end widget div -->
			</div>
			<!-- end widget -->

		</article>

	</div>

	<!-- end row -->

<div class="modal fade " id="remoteModal" tabindex="-1" role="dialog" aria-labelledby="remoteModalLabel" aria-hidden="true">  
    <div class="modal-dialog" style="width:800px">  
      	<iframe id="contentFrame" class="col-sm-10 col-md-12 col-lg-12" style="border:0px; height:500px;"></iframe>
    </div>  
</div>   
<div id="eventInfoTip">
	<div id="eventInfoTip2" class="SmallBox animated fadeInRight fast" style="top:500px;left:500px;background-color:#4C4F53">
		<div>
			<span id="eventInfoTipCreateTime"></span>
			<span id="eventInfoTipTitle"></span>
			<span id="eventInfoTipPriority"></span>
		</div>
	</div>
</div>
</section>
<!-- end widget grid -->

<link href='css/fullcalendar.min.css' rel='stylesheet' /> 
<link href='css/fullcalendar.print.css' rel='stylesheet' media='print' />

<script type="text/javascript">
	/* DO NOT REMOVE : GLOBAL FUNCTIONS!
	 *
	 * pageSetUp(); WILL CALL THE FOLLOWING FUNCTIONS
	 *
	 * // activate tooltips
	 * $("[rel=tooltip]").tooltip();
	 *
	 * // activate popovers
	 * $("[rel=popover]").popover();
	 *
	 * // activate popovers with hover states
	 * $("[rel=popover-hover]").popover({ trigger: "hover" });
	 *
	 * // activate inline charts
	 * runAllCharts();
	 *
	 * // setup widgets
	 * setup_widgets_desktop();
	 *
	 * // run form elements
	 * runAllForms();
	 *
	 ********************************
	 *
	 * pageSetUp() is needed whenever you load a page.
	 * It initializes and checks for all basic elements of the page
	 * and makes rendering easier.
	 *
	 */

	pageSetUp();
	
	/*
	 * PAGE RELATED SCRIPTS
	 */

	// pagefunction
	
	var init = function() {
			
		$(".js-status-update a").click(function () {
		    var selText = $(this).text();
		    var $this = $(this);
		    $this.parents('.btn-group').find('.dropdown-toggle').html(selText + ' <span class="caret"></span>');
		    $this.parents('.dropdown-menu').find('li').removeClass('active');
		    $this.parent().addClass('active');
		});
		
		/*
		 * TODO: add a way to add more todo's to list
		 */
		
		// initialize sortable
		
	    $("#sortable1, #sortable2").sortable({
	        handle: '.handle',
	        connectWith: ".todo",
	        update: countTasks
	    }).disableSelection();
		
		
		// check and uncheck
		$('.todo .checkbox > input[type="checkbox"]').click(function () {
		    var $this = $(this).parent().parent().parent();
		
		    if ($(this).prop('checked')) {
		        $this.addClass("complete");
		
		        // remove this if you want to undo a check list once checked
		        //$(this).attr("disabled", true);
		        $(this).parent().hide();
		
		        // once clicked - add class, copy to memory then remove and add to sortable3
		        $this.slideUp(500, function () {
		            $this.clone().prependTo("#sortable3").effect("highlight", {}, 800);
		            $this.remove();
		            countTasks();
		        });
		    } else {
		        // insert undo code here...
		    }
		
		});
		// count tasks
		function countTasks() {
		
		    $('.todo-group-title').each(function () {
		        var $this = $(this);
		        $this.find(".num-of-tasks").text($this.next().find("li").size());
		    });
		
		}
		
		/*
		 * RUN PAGE GRAPHS
		 */
		
		// Load FLOAT dependencies (related to page)
		loadScript("js/plugin/flot/jquery.flot.cust.min.js", loadFlotResize);
		
		function loadFlotResize() {
		    loadScript("js/plugin/flot/jquery.flot.resize.min.js", loadFlotToolTip);
		}
		
		function loadFlotToolTip() {
		    loadScript("js/plugin/flot/jquery.flot.tooltip.min.js", generatePageGraphs);
		}
		
		function generatePageGraphs() {
		
		    /* TAB 1: UPDATING CHART */
		    // For the demo we use generated data, but normally it would be coming from the server
		
		    var data = [],
		        totalPoints = 200,
		        $UpdatingChartColors = $("#updating-chart").css('color');
		
		    function getRandomData() {
		        if (data.length > 0)
		            data = data.slice(1);
		
		        // do a random walk
		        while (data.length < totalPoints) {
		            var prev = data.length > 0 ? data[data.length - 1] : 50;
		            var y = prev + Math.random() * 10 - 5;
		            if (y < 0)
		                y = 0;
		            if (y > 100)
		                y = 100;
		            data.push(y);
		        }
		
		        // zip the generated y values with the x values
		        var res = [];
		        for (var i = 0; i < data.length; ++i)
		            res.push([i, data[i]]);
		        return res;
		    }
		
		    // setup control widget
		    var updateInterval = 1500;
		    $("#updating-chart").val(updateInterval).change(function () {
		
		        var v = $(this).val();
		        if (v && !isNaN(+v)) {
		            updateInterval = +v;
		            $(this).val("" + updateInterval);
		        }
		
		    });
		
		    // setup plot
		    var options = {
		        yaxis: {
		            min: 0,
		            max: 100
		        },
		        xaxis: {
		            min: 0,
		            max: 100
		        },
		        colors: [$UpdatingChartColors],
		        series: {
		            lines: {
		                lineWidth: 1,
		                fill: true,
		                fillColor: {
		                    colors: [{
		                        opacity: 0.4
		                    }, {
		                        opacity: 0
		                    }]
		                },
		                steps: false
		
		            }
		        }
		    };
		
		    var plot = $.plot($("#updating-chart"), [getRandomData()], options);
		
		    /* live switch */
		    $('input[type="checkbox"]#start_interval').click(function () {
		        if ($(this).prop('checked')) {
		            $on = true;
		            updateInterval = 1500;
		            update();
		        } else {
		            clearInterval(updateInterval);
		            $on = false;
		        }
		    });
		
		    var $on = false;
		    function update() {
		        if ($on == true) {
		            plot.setData([getRandomData()]);
		            plot.draw();
		            setTimeout(update, updateInterval);
		
		        } else {
		            clearInterval(updateInterval);
		        }
		
		    }
		
		
		    /*end updating chart*/
		
		    /* TAB 2: Social Network  */
		
		    $(function () {
		        // jQuery Flot Chart
		        var twitter = [
		            [1, 27],
		            [2, 34],
		            [3, 51],
		            [4, 48],
		            [5, 55],
		            [6, 65],
		            [7, 61],
		            [8, 70],
		            [9, 65],
		            [10, 75],
		            [11, 57],
		            [12, 59],
		            [13, 62]
		        ],
		            facebook = [
		                [1, 25],
		                [2, 31],
		                [3, 45],
		                [4, 37],
		                [5, 38],
		                [6, 40],
		                [7, 47],
		                [8, 55],
		                [9, 43],
		                [10, 50],
		                [11, 47],
		                [12, 39],
		                [13, 47]
		            ],
		            data = [{
		                label: "Twitter",
		                data: twitter,
		                lines: {
		                    show: true,
		                    lineWidth: 1,
		                    fill: true,
		                    fillColor: {
		                        colors: [{
		                            opacity: 0.1
		                        }, {
		                            opacity: 0.13
		                        }]
		                    }
		                },
		                points: {
		                    show: true
		                }
		            }, {
		                label: "Facebook",
		                data: facebook,
		                lines: {
		                    show: true,
		                    lineWidth: 1,
		                    fill: true,
		                    fillColor: {
		                        colors: [{
		                            opacity: 0.1
		                        }, {
		                            opacity: 0.13
		                        }]
		                    }
		                },
		                points: {
		                    show: true
		                }
		            }];
		
		        var options = {
		            grid: {
		                hoverable: true
		            },
		            colors: ["#568A89", "#3276B1"],
		            tooltip: true,
		            tooltipOpts: {
		                //content : "Value <b>$x</b> Value <span>$y</span>",
		                defaultTheme: false
		            },
		            xaxis: {
		                ticks: [
		                    [1, "JAN"],
		                    [2, "FEB"],
		                    [3, "MAR"],
		                    [4, "APR"],
		                    [5, "MAY"],
		                    [6, "JUN"],
		                    [7, "JUL"],
		                    [8, "AUG"],
		                    [9, "SEP"],
		                    [10, "OCT"],
		                    [11, "NOV"],
		                    [12, "DEC"],
		                    [13, "JAN+1"]
		                ]
		            },
		            yaxes: {
		
		            }
		        };
		
		        var plot3 = $.plot($("#statsChart"), data, options);
		    });
		
		    // END TAB 2
		
		    // TAB THREE GRAPH //
		    /* TAB 3: Revenew  */
		
		    $(function () {
		
		        var trgt = [
		            [1354586000000, 153],
		            [1364587000000, 658],
		            [1374588000000, 198],
		            [1384589000000, 663],
		            [1394590000000, 801],
		            [1404591000000, 1080],
		            [1414592000000, 353],
		            [1424593000000, 749],
		            [1434594000000, 523],
		            [1444595000000, 258],
		            [1454596000000, 688],
		            [1464597000000, 364]
		        ],
		            prft = [
		                [1354586000000, 53],
		                [1364587000000, 65],
		                [1374588000000, 98],
		                [1384589000000, 83],
		                [1394590000000, 980],
		                [1404591000000, 808],
		                [1414592000000, 720],
		                [1424593000000, 674],
		                [1434594000000, 23],
		                [1444595000000, 79],
		                [1454596000000, 88],
		                [1464597000000, 36]
		            ],
		            sgnups = [
		                [1354586000000, 647],
		                [1364587000000, 435],
		                [1374588000000, 784],
		                [1384589000000, 346],
		                [1394590000000, 487],
		                [1404591000000, 463],
		                [1414592000000, 479],
		                [1424593000000, 236],
		                [1434594000000, 843],
		                [1444595000000, 657],
		                [1454596000000, 241],
		                [1464597000000, 341]
		            ],
		            toggles = $("#rev-toggles"),
		            target = $("#flotcontainer");
		
		        var data = [{
		            label: "Target Profit",
		            data: trgt,
		            bars: {
		                show: true,
		                align: "center",
		                barWidth: 30 * 30 * 60 * 1000 * 80
		            }
		        }, {
		            label: "Actual Profit",
		            data: prft,
		            color: '#3276B1',
		            lines: {
		                show: true,
		                lineWidth: 3
		            },
		            points: {
		                show: true
		            }
		        }, {
		            label: "Actual Signups",
		            data: sgnups,
		            color: '#71843F',
		            lines: {
		                show: true,
		                lineWidth: 1
		            },
		            points: {
		                show: true
		            }
		        }];
		
		        var options = {
		            grid: {
		                hoverable: true
		            },
		            tooltip: true,
		            tooltipOpts: {
		                //content: '%x - %y',
		                //dateFormat: '%b %y',
		                defaultTheme: false
		            },
		            xaxis: {
		                mode: "time"
		            },
		            yaxes: {
		                tickFormatter: function (val, axis) {
		                    return "$" + val;
		                },
		                max: 1200
		            }
		
		        };
		
		        plot2 = null;
		
		        function plotNow() {
		            var d = [];
		            toggles.find(':checkbox').each(function () {
		                if ($(this).is(':checked')) {
		                    d.push(data[$(this).attr("name").substr(4, 1)]);
		                }
		            });
		            if (d.length > 0) {
		                if (plot2) {
		                    plot2.setData(d);
		                    plot2.draw();
		                } else {
		                    plot2 = $.plot(target, d, options);
		                }
		            }
		
		        };
		
		        toggles.find(':checkbox').on('change', function () {
		            plotNow();
		        });
		        plotNow();
		
		    });
		
		}
		
		/*
		 * VECTOR MAP
		 */
		
		data_array = {
		    "US": 4977,
		    "AU": 4873,
		    "IN": 3671,
		    "BR": 2476,
		    "TR": 1476,
		    "CN": 146,
		    "CA": 134,
		    "BD": 100
		};
		
		// Load Map dependency 1 then call for dependency 2
		loadScript("js/plugin/vectormap/jquery-jvectormap-1.2.2.min.js", loadMapFile);
		
		// Load Map dependency 2 then rendeder Map
		function loadMapFile() {
		    loadScript("js/plugin/vectormap/jquery-jvectormap-world-mill-en.js", renderVectorMap);
		}
		
		function renderVectorMap() {
		    $('#vector-map').vectorMap({
		        map: 'world_mill_en',
		        backgroundColor: '#fff',
		        regionStyle: {
		            initial: {
		                fill: '#c4c4c4'
		            },
		            hover: {
		                "fill-opacity": 1
		            }
		        },
		        series: {
		            regions: [{
		                values: data_array,
		                scale: ['#85a8b6', '#4d7686'],
		                normalizeFunction: 'polynomial'
		            }]
		        },
		        onRegionLabelShow: function (e, el, code) {
		            if (typeof data_array[code] == 'undefined') {
		                e.preventDefault();
		            } else {
		                var countrylbl = data_array[code];
		                el.html(el.html() + ': ' + countrylbl + ' visits');
		            }
		        }
		    });
		}
		
		/**
		  工作日历加载
		*/
		loadScript("portal/js/calendarpanel.js", function(){
			 loadScript("js/plugin/fullcalendar/moment.min.js", function(){
					loadScript("js/plugin/fullcalendar/fullcalendar.min.js",function(){
						loadScript("js/plugin/fullcalendar/lang-all.js",function(){
							pageSetUp();
							pagefunction();
						});
					});
				});
		});
		/**
		 代办任务加载
		*/
		(function(){
			var container = $("#todotasks");
			var url = _serviceUrl+"runtime/tasks";
			$.ajax({
				type : "GET",
				dataType : 'json',
				url : url,
				data:{
					assignee:_userId,
					candidateUser:_userId,
					ended:false,
					pageIndex:1,
					pageSize:5
					},
				cache : false,
				async : true ,
				beforeSend : function() {
					container.removeData().html("");
					container.html('<h1 class="ajax-loading-animation"><i class="fa fa-cog fa-spin"></i> Loading...</h1>');
				},
				success : function(msg) {
					if (!msg && !msg.data) {
						alert("返回数据为null");
						return;
					}
					var data = msg.data;
					var li = '';
					$.each(data,function(i,d){
						li += "<li>";
						li += "<span class='handle'></span>";
						li += "<p style='padding-top:14px;'></span><span><a href='javascript:void(0);' onclick=openTaskForm('"+d.formUri+"','"+d.bizKey+"','"+d.id+"','"+d.processInstanceId+"');>"+d.subject+"</a></span> <span class='date'>"+d.createTime+"</span></p>";
						li += "</li>";
						li += "<li>";
					});
					container.html(li);
					// clear data var
					msg = null;
					container = null;
				},
				error : function(xhr, ajaxOptions, thrownError) {
					container.html('<h4 class="ajax-loading-error"><i class="fa fa-warning txt-color-orangeDark"></i> Error 404! Page not found.</h4>');
				}
			});
			
			$("#moreDetail").on("click",function(){
				window.location.href = $("base").attr("href")+"portal/jsp/todoTasks.jsp";
			});
		})();
		
		
	};
	init();
	/**
	 * 打开表单
	 * @param url
	 * @param dataId
	 * @param taskId
	 * @param processInstanceId
	 */
	function openTaskForm (url,dataId,taskId,processInstanceId){
		//测试时暂时用报销的表单代替
		url = "portal/expense/editExpense.jsp";
		var formUrl = url+"?dataId="+dataId+"&taskId="+taskId+"&processInstanceId="+processInstanceId+"&refresh="+new Date();
		openModalForm(formUrl,505);
	}
</script>
	<jsp:include page="../bottom.jsp" />
</body>
</html>