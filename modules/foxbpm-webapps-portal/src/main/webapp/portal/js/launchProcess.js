/**
 * 定义个人信息类
 * 
 * @author yangguangftlp
 */
function Gallery(config) {
	// this.userId = config.userId;
	var _config = config || {};
	var doAfter = _config.doAfter;
	var imgServiceUrl = _config.imgServiceUrl;
	function loadResource(_data) {
		
		//判断字符是否有中文字符
		function isHasChn(s)  { 
			var patrn= /[\u4E00-\u9FA5]|[\uFE30-\uFFA0]/gi; 
			if (!patrn.exec(s)){ 
				return false; 
			}else{ 
				return true; 
			} 
		} 
		$.ajax({
			url : imgServiceUrl+"model/process-definitions",
			data:_data,
			type : 'get',
			dataType : "json",
			cache : true,
			error : function(response) {
				$("#btn_search").removeClass("disabled");
			},
			success : function(response) {
				$("#superbox").html("");
				$("#btn_search").removeClass("disabled");
				if (!!response) {
					var superbox = $("#superbox");
					var superboxList = null;
					var img = null;
					var canvas = null;
					for (var i = 0; i < response.total; i++) {
						superboxList = $("<div class='superbox-list'>");
						img = $("<img class='superbox-img'>");
						img.attr("data-img", imgServiceUrl + "flowGraphic/flowImg?processDefinitionKey=" + response.data[i].key);
						img.attr("title", response.data[i].name);
						img.attr("processDefinitionKey", response.data[i].key);
						img.attr("processDefinitionId", response.data[i].id);
						img.attr("formUrl", response.data[i].startFormUri);
						img.attr("alt", response.data[i].description);
						canvas = document.createElement("canvas");
						canvas.width = 160;
						canvas.height = 160;
						canvas.style="border:3px solid";
						ctx = canvas.getContext("2d");
						ctx.fillStyle = "#eee"; 
						ctx.fillRect(0,0,160,160); 
						//绘制带有边框的矩形
						ctx.strokeRect(0,0,160,160);
						ctx.moveTo(20,50);
						ctx.fillStyle = "#dc562e"; 
						ctx.textBaseline = 'middle';
						ctx.font = 'bold 50px Arial'; //字体设置bold 30px Arial
						var startCh = response.data[i].name.substr(0,1);
						ctx.fillText (startCh, 20, 73); //填充出来的文字
						ctx.font = 'normal 20px sans-serif'; //字体设置
						ctx.textBaseline = 'top';
						if(isHasChn(startCh)){
							ctx.fillText (response.data[i].name.substr(1,8), 70, 40); //填充出来的文字
						}else {
							ctx.fillText (response.data[i].name.substr(1,8), 50, 40); //填充出来的文字
						}
						//清空绘制的矩形区域，并使之透明；
						img.attr("src", canvas.toDataURL("image/png"));
						superboxList.append(img);
						superbox.append(superboxList);
					}
					superbox.append("<div class='superbox-float'></div>");
					if (!!doAfter) {
						doAfter();
					}
				}
			}
		});
	}
	function addLisenter(){
		$("#btn_search").on("click",function(){
				$("#btn_search").addClass("disabled");
				loadResource({nameLike:$("#input_search").val()});
		});
	}
	this.init = function() {
		loadResource({});
		addLisenter();
	};
	this.search = function(_data) {
		loadResource(_data);
	};
	this.init();
}
/**
 * 定义方法
 */
Gallery.prototype = {

};
