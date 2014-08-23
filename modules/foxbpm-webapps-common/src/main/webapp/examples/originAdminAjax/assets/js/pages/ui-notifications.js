$('#add-regular').click(function(){

	$.gritter.add({
		// (string | mandatory) the heading of the notification
		title: 'This is a regular notice!',
		// (string | mandatory) the text inside the notification
		text: 'This will fade out after a certain amount of time. Vivamus eget tincidunt velit. Cum sociis natoque penatibus et <a href="#" style="color:#ccc">magnis dis parturient</a> montes, nascetur ridiculus mus.',
		// (string | optional) the image to display on the left
		image: 'assets/img/avatar.jpg',
		// (bool | optional) if you want it to fade out on its own or just sit there
		sticky: false,
		// (int | optional) the time you want it to be alive for before fading out
		time: ''
	});

	return false;

});

$('#add-sticky').click(function(){

    $.gritter.add({
        // (string | mandatory) the heading of the notification
        title: 'This is a sticky notice!',
        // (string | mandatory) the text inside the notification
        text: 'This will fade out after a certain amount of time. Vivamus eget tincidunt velit. Cum sociis natoque penatibus et <a href="#" style="color:#ccc">magnis dis parturient</a> montes, nascetur ridiculus mus.',
        // (string | optional) the image to display on the left
        image: 'assets/img/avatar.jpg',
        // (bool | optional) if you want it to fade out on its own or just sit there
        sticky: true,
        // (function) before the gritter notice is opened
    });

    return false;

});

$('#add-without-image').click(function(){

	$.gritter.add({
		// (string | mandatory) the heading of the notification
		title: 'This is a notice without an image!',
		// (string | mandatory) the text inside the notification
		text: 'This will fade out after a certain amount of time. Vivamus eget tincidunt velit. Cum sociis natoque penatibus et <a href="#" style="color:#ccc">magnis dis parturient</a> montes, nascetur ridiculus mus.'
	});

	return false;
});

$('#add-gritter-light').click(function(){

    $.gritter.add({
        // (string | mandatory) the heading of the notification
        title: 'This is a light notification',
        // (string | mandatory) the text inside the notification
        text: 'Just add a "gritter-light" class_name to your $.gritter.add or globally to $.gritter.options.class_name',
        class_name: 'gritter-light'
    });

    return false;
});

$("#remove-all").click(function(){

	$.gritter.removeAll();
	return false;

});

$("#remove-all-with-callbacks").click(function(){

	$.gritter.removeAll({
		before_close: function(e){
			alert("I am called before all notifications are closed.  I am passed the jQuery object containing all  of Gritter notifications.\n" + e);
		},
		after_close: function(){
			alert('I am called after everything has been closed.');
		}
	});
	return false;

});

$('#add-gritter-default').click(function(){

    $.gritter.add({
        // (string | mandatory) the heading of the notification
        title: 'This is a primary notification',
        // (string | mandatory) the text inside the notification
        text: 'Just add a "gritter-default" class_name to your $.gritter.add or globally to $.gritter.options.class_name',
        class_name: 'gritter-default'
    });

    return false;
});

$('#add-gritter-primary').click(function(){

    $.gritter.add({
        // (string | mandatory) the heading of the notification
        title: 'This is a primary notification',
        // (string | mandatory) the text inside the notification
        text: 'Just add a "gritter-primary" class_name to your $.gritter.add or globally to $.gritter.options.class_name',
        class_name: 'gritter-primary'
    });

    return false;
});

$('#add-gritter-success').click(function(){

    $.gritter.add({
        // (string | mandatory) the heading of the notification
        title: 'This is a success notification',
        // (string | mandatory) the text inside the notification
        text: 'Just add a "gritter-success" class_name to your $.gritter.add or globally to $.gritter.options.class_name',
        class_name: 'gritter-success'
    });

    return false;
});

$('#add-gritter-info').click(function(){

    $.gritter.add({
        // (string | mandatory) the heading of the notification
        title: 'This is a info notification',
        // (string | mandatory) the text inside the notification
        text: 'Just add a "gritter-info" class_name to your $.gritter.add or globally to $.gritter.options.class_name',
        class_name: 'gritter-info'
    });

    return false;
});	

$('#add-gritter-danger').click(function(){

    $.gritter.add({
        // (string | mandatory) the heading of the notification
        title: 'This is a danger notification',
        // (string | mandatory) the text inside the notification
        text: 'Just add a "gritter-danger" class_name to your $.gritter.add or globally to $.gritter.options.class_name',
        class_name: 'gritter-danger'
    });

    return false;
});

$('#add-gritter-warning').click(function(){

    $.gritter.add({
        // (string | mandatory) the heading of the notification
        title: 'This is a warning notification',
        // (string | mandatory) the text inside the notification
        text: 'Just add a "gritter-warning" class_name to your $.gritter.add or globally to $.gritter.options.class_name',
        class_name: 'gritter-warning'
    });

    return false;
});

$('#add-gritter-top-right').click(function(){
	
	$.gritter.removeAll({
		after_close: function(){
          	$.gritter.add({
				// defaults to 'top-right' but can be 'bottom-left', 'bottom-right', 'top-left', 'top-right'
				position: 'top-right',
		        // (string | mandatory) the heading of the notification
		        title: 'This is a default notification',
		        // (string | mandatory) the text inside the notification
		        text: 'Just add a "gritter-default" class_name to your $.gritter.add or globally to $.gritter.options.class_name',
		        class_name: 'gritter-default'
		    });
        }
	});

	return false;
});

$('#add-gritter-bottom-right').click(function(){
	
    $.gritter.removeAll({
		after_close: function(){
          	$.gritter.add({
				// defaults to 'top-right' but can be 'bottom-left', 'bottom-right', 'top-left', 'top-right'
				position: 'bottom-right',
        		// (string | mandatory) the heading of the notification
        		title: 'This is a default notification',
        		// (string | mandatory) the text inside the notification
        		text: 'Just add a "bottom-right" position to your $.gritter.add',
        		class_name: 'gritter-default'
    		});
		}
	});		

    return false;
});

$('#add-gritter-bottom-left').click(function(){

    $.gritter.removeAll({
		after_close: function(){
          	$.gritter.add({
				// defaults to 'top-right' but can be 'bottom-left', 'bottom-right', 'top-left', 'top-right'
				position: 'bottom-left',
        		// (string | mandatory) the heading of the notification
        		title: 'This is a default notification',
        		// (string | mandatory) the text inside the notification
        		text: 'Just add a "bottom-left" position to your $.gritter.add',
        		class_name: 'gritter-default'
    		});
		}
	});	

    return false;
});

$('#add-gritter-top-left').click(function(){

    $.gritter.removeAll({
		after_close: function(){
          	$.gritter.add({
				// defaults to 'top-right' but can be 'bottom-left', 'bottom-right', 'top-left', 'top-right'
				position: 'top-left',
        		// (string | mandatory) the heading of the notification
        		title: 'This is a default notification',
        		// (string | mandatory) the text inside the notification
        		text: 'Just add a "top-left" position to your $.gritter.add',
        		class_name: 'gritter-default'
    		});
		}
	});

    return false;
});

$('#add-gritter-facebook').click(function(){

	$.gritter.add({
		// (string | mandatory) the heading of the notification
		title: '<i class="fa fa-facebook"></i> You have new connection!',
		// (string | mandatory) the text inside the notification
		text: 'This will fade out after a certain amount of time.',
		class_name: 'gritter-facebook gritter-icon',
	});

	return false;
});

$('#add-gritter-twitter').click(function(){

	$.gritter.add({
		// (string | mandatory) the heading of the notification
		title: '<i class="fa fa-twitter"></i> New follower!',
		// (string | mandatory) the text inside the notification
		text: 'This will fade out after a certain amount of time.',
		class_name: 'gritter-twitter gritter-icon',
	});

	return false;
});

$('#add-gritter-linkedin').click(function(){

	$.gritter.add({
		// (string | mandatory) the heading of the notification
		title: '<i class="fa fa-linkedin"></i> You have new message!',
		// (string | mandatory) the text inside the notification
		text: 'This will fade out after a certain amount of time.',
		class_name: 'gritter-linkedin gritter-icon',
	});

	return false;
});

$('#add-gritter-tumblr').click(function(){

	$.gritter.add({
		// (string | mandatory) the heading of the notification
		title: '<i class="fa fa-tumblr"></i> New post!',
		// (string | mandatory) the text inside the notification
		text: 'This will fade out after a certain amount of time.',
		class_name: 'gritter-tumblr gritter-icon',
	});

	return false;
});

$('#add-gritter-google-plus').click(function(){

	$.gritter.add({
		// (string | mandatory) the heading of the notification
		title: '<i class="fa fa-google-plus"></i> New connection!',
		// (string | mandatory) the text inside the notification
		text: 'This will fade out after a certain amount of time.',
		class_name: 'gritter-google-plus gritter-icon',
	});

	return false;
});

$('#add-gritter-instagram').click(function(){

	$.gritter.add({
		// (string | mandatory) the heading of the notification
		title: '<i class="fa fa-instagram"></i> New picture!',
		// (string | mandatory) the text inside the notification
		text: 'This will fade out after a certain amount of time.',
		class_name: 'gritter-instagram gritter-icon',
	});

	return false;
});

$('#add-gritter-pinterest').click(function(){

	$.gritter.add({
		// (string | mandatory) the heading of the notification
		title: '<i class="fa fa-pinterest"></i> New picture!',
		// (string | mandatory) the text inside the notification
		text: 'This will fade out after a certain amount of time.',
		class_name: 'gritter-pinterest gritter-icon',
	});

	return false;
});

