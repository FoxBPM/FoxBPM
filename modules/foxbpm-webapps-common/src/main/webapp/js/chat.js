function message() {
	var a = $.blinkTitle.show();
	setTimeout(function() {
		$.blinkTitle.clear(a)
	},
	8e3)
}
function sendMsg(userId,content,time){
	var msgUrl = _serviceUrl+"social";
	$.ajax({
        type: "post",
        url: msgUrl,//要访问的后台地址
        data:{taskId:"taskId",msgType:"findAll",userId:userId,time:time,type:3,content:content,commentedCount:"",commentCount:"",transferredCount:"",transferCount:"",processInstanceId:"processInstanceId",openFlag:0},
        success: function(msg){//msg为返回的数据，在这里做数据绑定 
        },
        error:function(msg){
        	showMessage("错误","系统错误，请重新打开或联系管理员！","error");
        }
	});
}

$(document).ready(function() {
	var msgIndex=0;
	function e() { 
		var e = new Date,
		f = "";
		f += e.getFullYear() + "/",
		f += ((e.getMonth()+ 1) < 10 ?  "0"+(e.getMonth()+ 1):e.getMonth()+ 1) + "/",
		f += (e.getDate() < 10 ?  "0"+e.getDate():e.getDate()) + " ";
		f += (e.getHours() < 10 ?  "0"+e.getHours():e.getHours())  + ":",
		f += (e.getMinutes() < 10 ?  "0"+e.getMinutes():e.getMinutes()) ;
//		f += e.getSeconds();
		var g = $("#textarea-expand").val();
		  
		var i = "<div class='message clearfix' style='border-bottom:0px'><div class='user-logo' style='float:right'><img src='" + b + "'/>" + "</div>" +"<div class='msgDiv' style='margin-top:0px;margin-right:65;position:relative;width:110px;'>&nbsp;"+g+" <div style='position:absolute;top:5px;right:-16px;border:solid 8px;border-color:rgba(15, 15, 15, 0) rgba(200, 37, 207, 0) rgba(248, 195, 1, 0) #66FFFF;'></div></div>" + "<div class='wrap-ri'>" + "<div clsss='clearfix' style='bottom: 5px;right:150px;width:200px' style='float:right'><span>" + f + "</span></div>" + "</div>" + "<div style='clear:both;'></div>" + "</div>";
		  //i = i+"<div class='message clearfix' style='border-bottom:0px'><div class='user-logo' style='float:left'><img src='" + b + "'/>" + "</div>" +"<div class='msgDiv' style='margin-top:0px;margin-left:65;width:110px;background:#33CC99'>&nbsp;"+g+" <div style='position:absolute;top:5px;left:-20px;border:solid 10px;border-color: rgba(15, 15, 15, 0) #33CC99 rgba(200, 37, 207, 0) rgba(248, 195, 1, 0);'></div>"+ "<div class='wrap-ri'>" + "<div clsss='clearfix' style='bottom: 0px;width: 150px;left: 50px;top: 40px;' style='float:right'><span>" + f + "</span></div>" + "</div>" + "<div style='clear:both;'></div>" + "</div>";
		$("#msg_list").append("<li style='height: 69px' id='msg"+msgIndex+"' class='message'><img src='img/avatars/sunny.png' class='online' alt=''><div class='message-text'><time>"+f+"</time> <a href='javascript:void(0);' class='username'>John Doe</a><section id='msg_section_"+msgIndex+"'> "+g+"</section><i class='fa fa-smile-o txt-color-orange'></i></div></li>");
//		 $('#msg'+msgIndex).css("height", $("#msg_section").height()+100); 
		if(null != g && "" != g){
			$("#chat-body").scrollTop($("#msg_list").height());
			$("#textarea-expand").val(""), message();
			sendMsg("admin",g,f); 
			msgIndex ++;
		}else{
			alert("请输入消息");
		}
		  
	}
	var a = 3,
	b = "img/head/2024.jpg",
	c = "img/head/2015.jpg",
	d = "reqwer";
	$(".close_btn").click(function() {
		$(".chatBox").hide()
	}),
	$(".chat03_content li").mouseover(function() {
		$(this).addClass("hover").siblings().removeClass("hover")
	}).mouseout(function() {
		$(this).removeClass("hover").siblings().removeClass("hover")
	}),
	$(".chat03_content li").dblclick(function() {
		var b = $(this).index() + 1;
		a = b,
		c = "img/head/20" + (12 + a) + ".jpg",
		d = $(this).find(".chat03_name").text(),
		$(".chat01_content").scrollTop(0),
		$(this).addClass("choosed").siblings().removeClass("choosed"),
		$(".talkTo a").text($(this).children(".chat03_name").text()),
		$(".mes" + b).show().siblings().hide()
	}),
	$(".ctb01").mouseover(function() {
		$(".wl_faces_box").show()
	}).mouseout(function() {
		$(".wl_faces_box").hide()
	}),
	$(".wl_faces_box").mouseover(function() {
		$(".wl_faces_box").show()
	}).mouseout(function() {
		$(".wl_faces_box").hide()
	}),
	$(".wl_faces_close").click(function() {
		$(".wl_faces_box").hide()
	}),
	$(".wl_faces_main img").click(function() {
		var a = $(this).attr("src");
		$("#textarea").val($("#textarea").val() + "*#" + a.substr(a.indexOf("img/") + 4, 6) + "#*"),
		$("#textarea").focusEnd(),
		$(".wl_faces_box").hide()
	}),
	$("#ReplyChat").click(function() {
		e();
	}),
	document.onkeydown = function(a) {
		var b = document.all ? window.event: a;
		return 13 == b.keyCode ? (e(), !1) : void 0
	},
	$.fn.setCursorPosition = function(a) {
		return 0 == this.lengh ? this: $(this).setSelection(a, a)
	},
	$.fn.setSelection = function(a, b) {
		if (0 == this.lengh) return this;
		if (input = this[0], input.createTextRange) {
			var c = input.createTextRange();
			c.collapse(!0),
			c.moveEnd("character", b),
			c.moveStart("character", a),
			c.select()
		} else input.setSelectionRange && (input.focus(), input.setSelectionRange(a, b));
		return this
	},
	$.fn.focusEnd = function() {
		this.setCursorPosition(this.val().length)
	}
}),
function(a) {
	a.extend({
		blinkTitle: {
			show: function() {
				var a = 0,
				b = document.title;
				if ( - 1 == document.title.indexOf("\u3010")) var c = setInterval(function() {
					a++,
					3 == a && (a = 1),
					1 == a && (document.title = "\u3010\u3000\u3000\u3000\u3011" + b),
					2 == a && (document.title = "\u3010\u65b0\u6d88\u606f\u3011" + b)
				},
				500);
				return [c, b]
			},
			clear: function(a) {
				a && (clearInterval(a[0]), document.title = a[1])
			}
		}
	})
} (jQuery);