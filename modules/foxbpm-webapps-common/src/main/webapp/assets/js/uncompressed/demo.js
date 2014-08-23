/*****
* LAYOUT SETTINGS
* Following functions are responsible for options and themes switch. You can remove this file.
*/

$(document).ready(function(){
	
	$('#open-close').addClass('closed');
	
	$('#open-close').click(function(){
		
		if ($(this).hasClass('closed')) {
			$(this).parent().animate({ right: "+=200" }, 500);
			$(this).removeClass('closed');
		} else {
			$(this).parent().animate({ right: "-=200" }, 500);
			$(this).addClass('closed');
		}
		
	});
	
	$('#restore').click(function(){
		$('body').removeClass();
		
		$('#themes #default').attr('checked',true);
		$('#sidebar-minify i').removeClass('fa-list').addClass('fa-ellipsis-v');
		
		$('#theme-settings input[type="checkbox"]').each(function(){
			$(this).attr('checked',false);
			$.removeCookie($(this).attr('id'));
		});
	});
	
	if ($.cookie().sm) {
		$('body').addClass('sidebar-minified');
		$('#sidebar-minify i').removeClass('fa-ellipsis-v').addClass('fa-list');
		$('#sm').attr('checked',true);
	} 
	
	if ($.cookie().sh) {
		$('body').addClass('sidebar-hidden');
		$('#sh').attr('checked',true);
	}
	
	if ($.cookie().rtl) {
		$('body').addClass('rtl');
		loadCSS('assets/css/bootstrap-rtl.min.css', loadCSS('assets/css/style.rtl.min.css',1,0))
		$('#rtl').attr('checked',true);
	}
	
	if ($.cookie().bl) {
		$('body').addClass('container');
		$('#bl').attr('checked',true);
	}
	
	if ($.cookie().ss) {
		$('body').addClass('static-sidebar');
		$('#ss').attr('checked',true);
	}
	
	if ($.cookie().she) {
		$('body').addClass('static-header');
		$('#she').attr('checked',true);
	}
	
	if ($.cookie().hu) {
		$('body').addClass('hidden-usage');
		$('#hu').attr('checked',true);
	}
	
	if ($.cookie().theme) {
		$('body').addClass($.cookie().theme);
		
		$('#themes input[type="checkbox"]').each(function(){
			if ($(this).attr('id') == $.cookie().theme) {
				$(this).attr('checked', true);
			}
		})
		
	}
	
	$('#options input[type="checkbox"]').change(function(){
		
		if ($(this).attr('checked')) {
			$.cookie($(this).attr('id'), 1);
			
			if ($(this).attr('id') == 'sm') {
				$('body').addClass('sidebar-minified');
				$('#sidebar-minify i').removeClass('fa-ellipsis-v').addClass('fa-list');
			} else if ($(this).attr('id') == 'sh') {
				$('body').addClass('sidebar-hidden')
			} else if ($(this).attr('id') == 'rtl') {
				$('body').addClass('rtl');
				loadCSS('assets/css/bootstrap-rtl.min.css', loadCSS('assets/css/style.rtl.min.css',1,0));
				$('link[href="assets/css/bootstrap-rtl.min.css"]').prop("disabled", false);
				$('link[href="assets/css/style.rtl.min.css"]').prop("disabled", false);
			} else if ($(this).attr('id') == 'bl') {
				$('body').addClass('container')
			} else if ($(this).attr('id') == 'ss') {
				$('body').addClass('static-sidebar')
			} else if ($(this).attr('id') == 'she') {
				$('body').addClass('static-header')
			} else if ($(this).attr('id') == 'hu') {
				$('body').addClass('hidden-usage')
			}
				
		} else {
			$.removeCookie($(this).attr('id'));
			
			if ($(this).attr('id') == 'sm') {
				$('body').removeClass('sidebar-minified');
				$('#sidebar-minify i').removeClass('fa-list').addClass('fa-ellipsis-v');
			} else if ($(this).attr('id') == 'sh') {
				$('body').removeClass('sidebar-hidden')
			} else if ($(this).attr('id') == 'rtl') {
				$('body').removeClass('rtl');
				$('link[href="assets/css/bootstrap-rtl.min.css"]').prop("disabled", true);
				$('link[href="assets/css/style.rtl.min.css"]').prop("disabled", true);
				window.location.reload();
			} else if ($(this).attr('id') == 'bl') {
				$('body').removeClass('container')
			} else if ($(this).attr('id') == 'ss') {
				$('body').removeClass('static-sidebar')
			} else if ($(this).attr('id') == 'she') {
				$('body').removeClass('static-header')
			} else if ($(this).attr('id') == 'hu') {
				$('body').removeClass('hidden-usage')
			}
		}
				
	});
	
	$('#themes input[type="checkbox"]').change(function(){
		
		$('#themes input[type="checkbox"]').not(this).attr('checked', false);
		
		$('#themes input[type="checkbox"]').each(function(){
			$('body').removeClass($(this).attr('id'));
		});
		
		if ($(this).attr('checked')) {
			$.cookie('theme', $(this).attr('id'));
			$('body').addClass($(this).attr('id'));		
		} 		
	})
	
});