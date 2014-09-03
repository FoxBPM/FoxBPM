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
		$.ajax({
			url : imgServiceUrl+"process-definitions",
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
					var superboxDiv = null;
					var img = null;
					
					for (var i = 0; i < response.total; i++) {
						superboxList = $("<div class='superbox-list'>");
						superboxDiv = $("<div class='type-box'>");//style='padding-top:15px;padding-bottom:15px;padding-right:15px;width:160px;height:160px'
						img = $("<a class='superbox-img' >");
						img.attr("src", "img/superbox/superbox-thumb-21.jpg");
						img.attr("data-img", imgServiceUrl + "flowGraphic/flowImg?processDefinitionKey=" + response.data[i].key);
						img.attr("title", response.data[i].name);
						img.attr("processDefinitionKey", response.data[i].key);
						img.attr("processDefinitionId", response.data[i].id);
						img.attr("formUrl", response.data[i].startFormUri);
						img.attr("alt", response.data[i].description);
						img.append("<div >"+response.data[i].name+"</div>");
						superboxDiv.append(img);
						//superboxDiv.append("<div style='position:absolute;margin-left:auto; margin-right:auto;top:30%; display: block; width: 85%;text-align:left;font-size:14;font-family:微软雅黑;line-height:1.5; overflow: hidden; white-space:nowrap; -o-text-overflow: ellipsis; text-overflow: clip;'>"+response.data[i].name+"</div>");
						superboxList.append(superboxDiv);
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
			if('' != $("#input_search").val()){
				$("#btn_search").addClass("disabled");
				loadResource({nameLike:$("#input_search").val()});
			};
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
