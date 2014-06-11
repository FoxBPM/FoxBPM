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
package org.foxbpm.engine.impl.svg;

/**
 * 模版名称的定义类，和BPMN2.0下的SVG文件名进行映射
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 */
public final class SVGTemplateNameConstant {
	/**
	 * start_event
	 */
	public static final String TEMPLATE_STARTEVENT_NONE = "startevent/none.svg";
	public static final String TEMPLATE_STARTEVENT_TIMER = "startevent/time.svg";
	public static final String TEMPLATE_STARTEVENT_SIGNAL = "startevent/signal.svg";
	public static final String TEMPLATE_STARTEVENT_MULTIPLE = "startevent/multiple.svg";
	public static final String TEMPLATE_STARTEVENT_MULTIPLE_PARALLEL = "startevent/multiple.parallel.svg";
	public static final String TEMPLATE_STARTEVENT_MESSAGE = "startevent/message.svg";
	public static final String TEMPLATE_STARTEVENT_ESCALATION = "startevent/escalation.svg";
	public static final String TEMPLATE_STARTEVENT_ERROR = "startevent/error.svg";
	public static final String TEMPLATE_STARTEVENT_CONDITIONAL = "startevent/conditional.svg";
	public static final String TEMPLATE_STARTEVENT_COMPENSATION = "startevent/compensation.svg";

	/**
	 * end_event
	 */
	public static final String TEMPLATE_ENDEVENT_NONE = "endevent/none.svg";
	public static final String TEMPLATE_ENDEVENT_CANCEL = "endevent/cancel.svg";
	public static final String TEMPLATE_ENDEVENT_SIGNAL = "endevent/signal.svg";
	public static final String TEMPLATE_ENDEVENT_MULTIPLE = "endevent/multiple.svg";
	public static final String TEMPLATE_ENDEVENT_MESSAGE = "endevent/message.svg";
	public static final String TEMPLATE_ENDEVENT_ESCALATION = "endevent/escalation.svg";
	public static final String TEMPLATE_ENDEVENT_ERROR = "endevent/error.svg";
	public static final String TEMPLATE_ENDEVENT_TERMINATE = "endevent/terminate.svg";
	public static final String TEMPLATE_ENDEVENT_COMPENSATION = "endevent/compensation.svg";

	/**
	 * activity
	 */
	public static final String TEMPLATE_ACTIVITY_TASK = "activity/task.svg";

	/**
	 * connector
	 */
	public static final String TEMPLATE_CONNECTOR_SEQUENCEFLOW = "connector/sequenceflow.svg";

}
