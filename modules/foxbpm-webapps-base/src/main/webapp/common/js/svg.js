var TASK_END_COLOR = "green";
var TASK_END_WIDTH = "2";
var TASK_ING_COLOR = "red";
var TASK_ING_WIDTH = "2";
var TASK_BACKUP_COLOR;
var TASK_BACKUP_WIDTH;
/**
 * 
 */
function signProcessState() {
	signEndTaskState();
	signIngTaskState();
}
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
				rectAttribute.nodeValue = TASK_BACKUP_COLOR;
			}
			if (rectAttribute.name == "stroke-width") {
				rectAttribute.nodeValue = TASK_BACKUP_WIDTH;
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
				rectAttribute.nodeValue = TASK_BACKUP_COLOR;
			}
			if (rectAttribute.name == "stroke-width") {
				rectAttribute.nodeValue = TASK_BACKUP_WIDTH;
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
					TASK_BACKUP_COLOR = rectAttribute.nodeValue;
					rectAttribute.nodeValue = TASK_END_COLOR;
				}
				if (rectAttribute.name == "stroke-width") {
					TASK_BACKUP_WIDTH = rectAttribute.nodeValue;
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
					TASK_BACKUP_COLOR = rectAttribute.nodeValue;
					rectAttribute.nodeValue = TASK_ING_COLOR;
				}
				if (rectAttribute.name == "stroke-width") {
					TASK_BACKUP_WIDTH = rectAttribute.nodeValue;
					rectAttribute.nodeValue = TASK_ING_WIDTH;
				}

			}
		}
	}
}