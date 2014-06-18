/**
 * 标记常量属性
 */
var TASK_END_COLOR = "#458f05";
var TASK_END_WIDTH = "2";
var TASK_ING_COLOR = "#ff7200";
var TASK_ING_WIDTH = "2";
/**
 * 保存流程节点本身式样
 */
var backUpColorDictionary = new Object();
var backUpWidthDictionary = new Object();
/**
 * 标记流程状态
 */
function signProcessState() {
	signEndTaskState();
	signIngTaskState();
}
/**
 * 取消流程状态标记
 */
function clearTaskState() {
	clearEndTaskState();
	clearIngTaskState();
}
function clearEndTaskState() {
	for (var i = 0; i < taskListEnd.length; i++) {
		var endTask = taskListEnd[i];
		var rectAttributes = $("#" + endTask.nodeId)[0].attributes;
		for (var j = 0; j < rectAttributes.length; j++) {
			var rectAttribute = rectAttributes[j];
			if (rectAttribute.name == "stroke") {
				rectAttribute.nodeValue = backUpColorDictionary[endTask.nodeId];
			}
			if (rectAttribute.name == "stroke-width") {
				rectAttribute.nodeValue = backUpWidthDictionary[endTask.nodeId];
			}
		}
	}

}
function clearIngTaskState() {
	for (var i = 0; i < taskListIng.length; i++) {
		var ingTask = taskListIng[i];
		var rectAttributes = $("#" + ingTask.nodeId)[0].attributes;
		for (var j = 0; j < rectAttributes.length; j++) {
			var rectAttribute = rectAttributes[j];
			if (rectAttribute.name == "stroke") {
				rectAttribute.nodeValue = backUpColorDictionary[ingTask.nodeId];
			}
			if (rectAttribute.name == "stroke-width") {
				rectAttribute.nodeValue = backUpWidthDictionary[ingTask.nodeId];
			}
		}
	}
}

function signEndTaskState() {
	if (taskListEnd == null || taskListEnd.length == 0) {
		return;
	} else {
		for (var i = 0; i < taskListEnd.length; i++) {
			var endTask = taskListEnd[i];
			var rectAttributes = $("#" + endTask.nodeId)[0].attributes;
			for (var j = 0; j < rectAttributes.length; j++) {
				var rectAttribute = rectAttributes[j];
				if (rectAttribute.name == "stroke") {
					backUpColorDictionary[endTask.nodeId] = rectAttribute.nodeValue;
					rectAttribute.nodeValue = TASK_END_COLOR;
				}
				if (rectAttribute.name == "stroke-width") {
					backUpWidthDictionary[endTask.nodeId] = rectAttribute.nodeValue;
					rectAttribute.nodeValue = TASK_END_WIDTH;
				}

			}
		}
	}
}
function signIngTaskState() {
	if (taskListIng == null || taskListIng.length == 0) {
		return;
	} else {
		for (var i = 0; i < taskListIng.length; i++) {
			var ingTask = taskListIng[i];
			var rectAttributes = $("#" + ingTask.nodeId)[0].attributes;
			for (var j = 0; j < rectAttributes.length; j++) {
				var rectAttribute = rectAttributes[j];
				if (rectAttribute.name == "stroke") {
					backUpColorDictionary[ingTask.nodeId] = rectAttribute.nodeValue;
					rectAttribute.nodeValue = TASK_ING_COLOR;
				}
				if (rectAttribute.name == "stroke-width") {
					backUpWidthDictionary[ingTask.nodeId] = rectAttribute.nodeValue;
					rectAttribute.nodeValue = TASK_ING_WIDTH;
				}

			}
		}
	}
}