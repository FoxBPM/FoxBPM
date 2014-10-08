/**
 * 定义个人信息类
 * 
 * @author yangguangftlp
 */
function PersonInfo(config) {
	this.userId = config.userId;
}
/**
 * 定义方法
 */
PersonInfo.prototype = {
	init : function() {
		if (this.userId) {
			$("#userPicture").attr("src",_serviceUrl+"identity/users/"+this.userId+"/picture");
			$("#editPersonalInfo").show();
			$("#updatePersonalInfo").show();
			this.loadPersonalInfo();
			this.addListener();
		}
	},
	addListener : function() {
		var _self = this;
		$("#updatePicture").on("click", function() {
			_self.updatePicture();
		});
		$("#updatePersonalInfo").on("click", function() {
			_self.updatePersonalInfo();
		});
		$("#editPersonalInfo").on("click", function() {
			_self.editPersonalInfo();
		});
	},
	ajaxSubmit : function(formId) {
		// 构建ajaxSubmit参数
		try {
			// 开始ajax提交表单
			$("#" + formId).ajaxSubmit({
				cache : false,
				type : "post",
				dataType : "json",
				error : function(response) {
				},
				success : function(response) {
				}
			});
		} catch (e) {
			alert(e.message);
		}
	},
	loadPersonInfo : function() {
		$.ajax({
			url : "/foxbpm-webapps-common/service/identity/users/"
					+ this.userId,
			type : 'get',
			dataType : "json",
			cache : false,
			error : function(response) {
			},
			success : function(response) {
				if (!!response) {
					$("#name_label").html(response.userName);
					$("#name_label").attr("label", response.userName);
					$("#tel_label").html(response.tel);
					$("#tel_label").attr("label", response.tel);
					$("#email_label").html(response.email);
					$("#email_label").attr("label", response.email);
					$("#email_label").attr("href", "mailto:" + response.email);
				}
			}
		});
	},
	editPersonalInfo : function() {
		var editPersonalInfo = $("#editPersonalInfo");
		var updatePicture = $("#updatePicture");
		var name = $("#name");
		var tel = $("#tel");
		var email = $("#email");
		var name_label = $("#name_label");
		var tel_label = $("#tel_label");
		var email_label = $("#email_label");
		var flag = editPersonalInfo.attr("flag");
		if (0 == flag) {
			updatePicture.show();
			name.attr("placeholder", name_label.attr("label"));
			name.attr("value", name_label.attr("label"));
			name.show();
			tel.attr("placeholder", tel_label.attr("label"));
			tel.attr("value", tel_label.attr("label"));
			tel.show();
			email.attr("placeholder", email_label.attr("label"));
			email.attr("value", email_label.attr("label"));
			email.show();
			editPersonalInfo.attr("flag", 1);
			editPersonalInfo.html("Cancel");
			name_label.hide();
			tel_label.hide();
			email_label.hide();
		} else {
			updatePicture.hide();
			name.hide();
			tel.hide();
			email.hide();
			editPersonalInfo.attr("flag", 0);
			editPersonalInfo.html("Edit");
			name_label.show();
			tel_label.show();
			email_label.show();
		}
	},
	updatePicture : function() {
		var _self = this;
		var $form = document.getElementById("uploadForm");
		if (!$form) {
			// 创建表单
			$form = document.createElement("form");
			$form.action = "/foxbpm-webapps-common/service/identity/users/"
					+ this.userId + "/picture";
			$form.id = "uploadForm";
			$form.method = "post";
			$form.enctype = "multipart/form-data";
			$form.style.display = "none";
			// 创建文件输入框
			var hiddenFileInput = document.createElement("input");
			hiddenFileInput.setAttribute("type", "file");
			hiddenFileInput.setAttribute("id", "uploadFile");
			hiddenFileInput.setAttribute("name", "uploadFile");
			hiddenFileInput.setAttribute("multiple", "multiple");
			hiddenFileInput.style.visibility = "hidden";
			hiddenFileInput.onchange = function() {
				if(this.files.length > 1){
					showMessage("错误","请选择一个文件！","error");
					return;
				}
				if (this.files[0].size > 1 * 1024 * 1024) {
					showMessage("错误","文件过大，请选择小于1MB的文件！","error");
					return;
				}
				_self.previewImage(this);
				_self.ajaxSubmit("uploadForm");
			};
			$form.appendChild(hiddenFileInput);
			// 将表单加当document上，
			document.body.appendChild($form);
			hiddenFileInput.click();
		} else {
			document.getElementById("uploadFile").click();
		}
	},
	updatePersonalInfo : function() {
		//var _self = this;
		if("1" == $("#editPersonalInfo").attr("flag")){
			$.ajax({
				url : "/foxbpm-webapps-common/service/identity/users/"
						+ this.userId,
				type : 'put',
				dataType : "json",
				cache : false,
				data : {
					name : $("#name").val(),
					email : $("#email").val(),
					tel : $("#tel").val()
				},
				error : function() {
				},
				success : function(data, status) {
					window.location.reload(true);
				}
			});
		}else {
			showMessage("提示","请先编辑!","warning");
		}
	},
	loadPersonalInfo : function() {
		$.ajax({
			url : "/foxbpm-webapps-common/service/identity/users/"
					+ this.userId,
			type : 'get',
			dataType : "json",
			cache : false,
			error : function() {
			},
			success : function(response) {
				if (!!response) {
					var name_label = $("#name_label");
					var tel_label = $("#tel_label");
					var email_label = $("#email_label");
					name_label.html(response.userName);
					name_label.attr("label", response.userName);
					tel_label.html(response.tel);
					tel_label.attr("label", response.tel);
					email_label.html(response.email);
					email_label.attr("label", response.email);
					email_label.attr("href", "mailto:" + response.email);
				}
			}
		});
	},
	previewImage : function(file) {
		var MAXWIDTH = 260;
		var MAXHEIGHT = 180;
		var div = document.getElementById('preview');
		if (file.files && file.files[0]) {
			var img = document.getElementById('userPicture');
			var reader = new FileReader();
			reader.onload = function(evt) {
				img.src = evt.target.result;
			};
			reader.readAsDataURL(file.files[0]);
		} else // 兼容IE
		{
			var sFilter = 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
			file.select();
			var src = document.selection.createRange().text;
			div.innerHTML = '<img id=userPicture>';
			var img = document.getElementById('userPicture');
			img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
			var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth,
					img.offsetHeight);
			status = ('rect:' + rect.top + ',' + rect.left + ',' + rect.width
					+ ',' + rect.height);
			div.innerHTML = "<div id=divhead style='width:" + rect.width
					+ "px;height:" + rect.height + "px;margin-top:" + rect.top
					+ "px;" + sFilter + src + "\"'></div>";
		}
	},
	clacImgZoomParam : function(maxWidth, maxHeight, width, height) {
		var param = {
			top : 0,
			left : 0,
			width : width,
			height : height
		};
		if (width > maxWidth || height > maxHeight) {
			rateWidth = width / maxWidth;
			rateHeight = height / maxHeight;

			if (rateWidth > rateHeight) {
				param.width = maxWidth;
				param.height = Math.round(height / rateWidth);
			} else {
				param.width = Math.round(width / rateHeight);
				param.height = maxHeight;
			}
		}
		param.left = Math.round((maxWidth - param.width) / 2);
		param.top = Math.round((maxHeight - param.height) / 2);
		return param;
	}
};
