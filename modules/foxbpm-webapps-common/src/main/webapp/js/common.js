/**
 * 
 */
var _serviceUrl = document.getElementsByTagName("base")[0].href+"service/";
var formCommit = false;
/**
 * iframe弹出框
 * 依赖：bootstrap.js
 * @param url url路径
 * @param height 高度，由于宽度需要自适应，所以这里只给了高度参数
 * 例：openModalForm("http://www.baidu.com",500);
 */
function openModalForm(url,height){
	if(height === undefined){
		height=550;
	}
	var contentFrame = $("<div class='modal fade ' id='remoteModal' tabindex='-1'  scrolling='no' role='dialog' aria-labelledby='remoteModalLabel' aria-hidden='true'> <div class='modal-dialog' style='width:900px'> <iframe id='contentFrame' class='col-sm-10 col-md-12 col-lg-12' style='border:0px; height:"+height+"px;'></iframe></div> </div>  ");
	$("body").append(contentFrame);
	$("#contentFrame").attr("src",url);
	$('#remoteModal').modal({backdrop:"static"});
}

/**
 * 关闭modal,如果页面中存在打开的modal,则关闭之
 * <p>此关闭方法会清空浏览器缓存，防止iframe内存泄露问题</p>
 */
function closeModal(){
	var contentFrame = $("#contentFrame");
	if(contentFrame){
		contentFrame.attr("src","about:blank");
		var contentModal = $('#remoteModal');
		if(contentModal){
			contentModal.modal("hide");
		}
	}
}

/**
 * web中的通知方式
 * 依赖：bootstrap.js、SmartNotification.js
 * @param mesTitle 标题
 * @param mes 信息内容
 * @param type 类型：error,info,warning
 * 例：showMesssage("提示","保存成功","info")
 */
function showMessage(mesTitle,mes,type){
	if(mesTitle || mesTitle === undefined){
		mesTitle ="提示";
	}
	$.smallBox({ 
		title : mesTitle,
		content : mes,
		color : "#C46A69",
		icon : "fa fa-warning shake animated",
		timeout : 2000
	});
}

/**
 * 截取url中的parameter
 * 依赖：无
 * @param paras
 * @return
 * 例：url http://localhost:8080/index.jsp?username=admin
 * requestUrlParam("username");
 * 则return admin
 */
function requestUrlParam(paras) {
	var url = location.href;
	var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
	var paraObj = {};
	for (var i = 0; j = paraString[i]; i++) {
		paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j
				.indexOf("=") + 1, j.length);
	}
	var returnValue = paraObj[paras.toLowerCase()];
	if (typeof (returnValue) == "undefined") {
		return "";
	} else {
		return returnValue;
	}
}