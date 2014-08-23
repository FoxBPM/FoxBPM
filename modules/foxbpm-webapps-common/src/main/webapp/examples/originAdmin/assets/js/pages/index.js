$(document).ready(function(){
	
	todoList();
	discussionWidget();
	
	/* ---------- Datable ---------- */
	$('.datatable').dataTable({
		"sDom": "<'row'<'col-lg-6'l><'col-lg-6'f>r>t<'row'<'col-lg-12'i><'col-lg-12 center'p>>",
		"bPaginate": false,
		"bFilter": false,
		"bLengthChange": false,
		"bInfo": false,		
	});
	
	$('.countries').dataTable({
		"sDom": "<'row'<'col-lg-6'l><'col-lg-6'f>r>t<'row'<'col-lg-12'i><'col-lg-12 center'p>>",
		"bPaginate": false,
		"bFilter": false,
		"bLengthChange": false,
		"bInfo": false,
		// Disable sorting on the first column
		"aoColumnDefs" : [ {
			'bSortable' : false,
			'aTargets' : [ 0 ]
		} ]
	});
	
	/* ---------- Map ---------- */
	$(function(){
	  $('#map').vectorMap({
	    map: 'world_mill_en',
	    series: {
	      regions: [{
	        values: gdpData,
	        scale: ['#f5f5f5', '#d4d4d4'],
	        normalizeFunction: 'polynomial'
	      }]
	    },
		backgroundColor: '#fff',
	    onLabelShow: function(e, el, code){
	      el.html(el.html()+' (GDP - '+gdpData[code]+')');
	    }
	  });
	});
	
	/* ---------- Placeholder Fix for IE ---------- */
	$('input, textarea').placeholder();

	/* ---------- Auto Height texarea ---------- */
	$('textarea').autosize();
	
	$('#recent a:first').tab('show');
	$('#recent a').click(function (e) {
	  e.preventDefault();
	  $(this).tab('show');
	}); 
	
	/*------- Main Calendar -------*/
	$('#external-events div.external-event').each(function() {

		// it doesn't need to have a start or end
		var eventObject = {
			title: $.trim($(this).text()) // use the element's text as the event title
		};
		
		// store the Event Object in the DOM element so we can get to it later
		$(this).data('eventObject', eventObject);
		
		// make the event draggable using jQuery UI
		$(this).draggable({
			zIndex: 999,
			revert: true,      // will cause the event to go back to its
			revertDuration: 0  //  original position after the drag
		});
		
	});
	
	var date = new Date();
	var d = date.getDate();
	var m = date.getMonth();
	var y = date.getFullYear();
	
	$('.calendar-small').fullCalendar({
		header: {
			right: 'next',
			center: 'title',
			left: 'prev'
		},
		defaultView: 'month',
		editable: true,
		events: [
			{
				title: 'All Day Event',
				start: '2014-06-01'
			},
			{
				title: 'Long Event',
				start: '2014-06-07',
				end: '2014-06-10'
			},
			{
				id: 999,
				title: 'Repeating Event',
				start: '2014-06-09 16:00:00'
			},
			{
				id: 999,
				title: 'Repeating Event',
				start: '2014-06-16 16:00:00'
			},
			{
				title: 'Meeting',
				start: '2014-06-12 10:30:00',
				end: '2014-06-12 12:30:00'
			},
			{
				title: 'Lunch',
				start: '2014-06-12 12:00:00'
			},
			{
				title: 'Birthday Party',
				start: '2014-06-13 07:00:00'
			},
			{
				title: 'Click for Google',
				url: 'http://google.com/',
				start: '2014-06-28'
			}
		]
	});
	
	/*------- Moris Chart -------*/
	Morris.Donut({
		element: 'hero-donut',
		data: [
			{label: 'Chrome', value: 35 },
	      	{label: 'Firefox', value: 20 },
	      	{label: 'Safari', value: 15 },
	      	{label: 'IE', value: 10 },
			{label: 'Other', value: 20 }
	    ],
		colors: ["#36A9E1", "#bdea74", "#67c2ef", "#fabb3d", "#ff5454"],
		formatter: function (y) { return y + "%" }
	});
	
	
	/*------- Main Chart -------*/
	var d1 = [[0,0],[1,0],[2,1],[3,2],[4,21],[5,9],[6,12],[7,10],[8,31],[9,13],[10,65],[11,10],[12,12],[13,6],[14,4],[15,3],[16,0]];
	var d2 = [[0,0],[1,0],[2,1],[3,2],[4,7],[5,5],[6,6],[7,8],[8,24],[9,7],[10,12],[11,5],[12,6],[13,3],[14,2],[15,2],[16,0]];
	$("#flot-main").length && $.plot($("#flot-main"), [ d1, d2 ],
		{
			series: {
				lines: {
	                show: false
	            },
	            splines: {
	              	show: true,
	              	tension: 0.4,
	              	lineWidth: 1,
	              	fill: 0.4
	            },
	            points: {
	                radius: 2,
	                show: true,
					lineWidth: 1,
	            },
	            shadowSize: 0
	        },
	        grid: {
	            hoverable: true,
	            clickable: true,
	            tickColor: "#f5f5f5",
	            borderWidth: 1,
	            color: '#f5f5f5'
	        },
	        colors: ["#67c2ef", "#bdea74"],
	        xaxis:{
				tickColor: '#fff'
			},
	        yaxis: {
				ticks: 4
	        },
	        tooltip: true,
	        tooltipOpts: {
				content: "chart: %x.1 is %y.4",
				defaultTheme: false,
				shifts: {
	            	x: 0,
	            	y: 20
				}
			}
		}
	  );
	
});

$(document).ready(function(){
	
	
	/*------- Gauge -------*/
	var opts = {
	  	lines: 12, // The number of lines to draw
	  	angle: 0.05, // The length of each line
	  	lineWidth: 0.44, // The line thickness
	  	pointer: {
	    	length: 0.75, // The radius of the inner circle
	    	strokeWidth: 0.035, // The rotation offset
	    	color: '#374767' // Fill color
	  	},
	  	limitMax: 'false',   // If true, the pointer will not go past the end of the gauge
	  	colorStart: '#67c2ef',   // Colors
	  	colorStop: '#67c2ef',    // just experiment with them
	  	strokeColor: '#f5f5f5',   // to see which ones work best for you
	  	generateGradient: true
	};
	var target = document.getElementById('gauge1'); // your canvas element
	var gauge = new Gauge(target).setOptions(opts); // create sexy gauge!
	gauge.maxValue = 3000; // set max gauge value
	gauge.animationSpeed = 32; // set animation speed (32 is default value)
	gauge.set(1650); // set actual value
	
	var opts2 = {
	  	lines: 12, // The number of lines to draw
	  	angle: 0.05, // The length of each line
	  	lineWidth: 0.44, // The line thickness
	  	pointer: {
	    	length: 0.75, // The radius of the inner circle
	    	strokeWidth: 0.035, // The rotation offset
	    	color: '#374767' // Fill color
	  	},
	  	limitMax: 'false',   // If true, the pointer will not go past the end of the gauge
	  	colorStart: '#fabb3d',   // Colors
	  	colorStop: '#fabb3d',    // just experiment with them
	  	strokeColor: '#f5f5f5',   // to see which ones work best for you
	  	generateGradient: true
	};
	var target = document.getElementById('gauge2'); // your canvas element
	var gauge = new Gauge(target).setOptions(opts2); // create sexy gauge!
	gauge.maxValue = 3000; // set max gauge value
	gauge.animationSpeed = 32; // set animation speed (32 is default value)
	gauge.set(650); // set actual value
	
});