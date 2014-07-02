/**
 * Copyright 1996-2014 FoxBPM ORG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author MAENLIANG
 */
package org.foxbpm.engine.impl.diagramview.svg;

/**
 * 元素子类型名称，常量类
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 */
public class SVGTypeNameConstant {
	public static final String SVT_TYPE_GATEWAY = "gateway";
	public static final String SVG_TYPE_EVENT = "event";
	public static final String SVG_TYPE_LANE = "Lane";
	public static final String SVG_TYPE_TASK = "Task";
	public static final String SVG_TYPE_CONNECTOR = "connector";

	public static final String ACTIVITY_MANUALTASK = "manualTask";
	public static final String ACTIVITY_MANUALTASK_LOOP = "manualTask/loop";
	public static final String ACTIVITY_MANUALTASK_PARALLEL = "manualTask/parallel";
	public static final String ACTIVITY_MANUALTASK_SEQUENTIAL = "manualTask/sequential";
	public static final String ACTIVITY_MANUALTASK_COMPENSATION = "manualTask/compensation";

	public static final String ACTIVITY_BUSINESSRULETASK = "businessRuleTask";
	public static final String ACTIVITY_BUSINESSRULETASK_LOOP = "businessRuleTask/loop";
	public static final String ACTIVITY_BUSINESSRULETASK_PARALLEL = "businessRuleTask/parallel";
	public static final String ACTIVITY_BUSINESSRULETASK_SEQUENTIAL = "businessRuleTask/sequential";
	public static final String ACTIVITY_BUSINESSRULETASK_COMPENSATION = "businessRuleTask/compensation";

	public static final String ACTIVITY_SCRIPTTASK = "scriptTask";
	public static final String ACTIVITY_SCRIPTTASK_LOOP = "scriptTask/loop";
	public static final String ACTIVITY_SCRIPTTASK_PARALLEL = "scriptTask/parallel";
	public static final String ACTIVITY_SCRIPTTASK_SEQUENTIAL = "scriptTask/sequential";
	public static final String ACTIVITY_SCRIPTTASK_COMPENSATION = "scriptTask/compensation";

	public static final String ACTIVITY_USERTASK = "userTask";
	public static final String ACTIVITY_USERTASK_LOOP = "userTask/loop";
	public static final String ACTIVITY_USERTASK_PARALLEL = "userTask/parallel";
	public static final String ACTIVITY_USERTASK_SEQUENTIAL = "userTask/sequential";
	public static final String ACTIVITY_USERTASK_COMPENSATION = "userTask/compensation";

	public static final String ACTIVITY_SERVICETASK = "serviceTask";
	public static final String ACTIVITY_SERVICETASK_LOOP = "serviceTask/loop";
	public static final String ACTIVITY_SERVICETASK_PARALLEL = "serviceTask/parallel";
	public static final String ACTIVITY_SERVICETASK_SEQUENTIAL = "serviceTask/sequential";
	public static final String ACTIVITY_SERVICETASK_COMPENSATION = "serviceTask/compensation";

	public static final String ACTIVITY_SENDTASK = "sendTask";
	public static final String ACTIVITY_SENDTASK_LOOP = "sendTask/loop";
	public static final String ACTIVITY_SENDTASK_PARALLEL = "sendTask/parallel";
	public static final String ACTIVITY_SENDTASK_SEQUENTIAL = "sendTask/sequential";
	public static final String ACTIVITY_SENDTASK_COMPENSATION = "sendTask/compensation";

	public static final String ACTIVITY_RECEIVETASK = "receiveTask";
	public static final String ACTIVITY_RECEIVETASK_LOOP = "receiveTask/loop";
	public static final String ACTIVITY_RECEIVETASK_PARALLEL = "receiveTask/parallel";
	public static final String ACTIVITY_RECEIVETASK_SEQUENTIAL = "receiveTask/sequential";
	public static final String ACTIVITY_RECEIVETASK_COMPENSATION = "receiveTask/compensation";

}
