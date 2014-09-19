/**
 * 
 */
var _serviceUrl = "service/";
function openModalForm(url,height){
	if(height || height === undefined){
		height=550;
	}
	var contentFrame = $("<div class='modal fade ' id='remoteModal' tabindex='-1' role='dialog' aria-labelledby='remoteModalLabel' aria-hidden='true'> <div class='modal-dialog' style='width:800px'> <iframe id='contentFrame' class='col-sm-10 col-md-12 col-lg-12' style='border:0px; height:"+height+"px;'></iframe></div> </div>  ");
	$("body").append(contentFrame);
	$("#contentFrame").attr("src",url);
	$('#remoteModal').modal({backdrop:"static"});
}

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