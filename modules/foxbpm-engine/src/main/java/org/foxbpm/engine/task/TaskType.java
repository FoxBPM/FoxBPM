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
 * @author ych
 */
package org.foxbpm.engine.task;

public class TaskType {
	
	
	/**
	 * foxbpm流程引擎产生的任务
	 */
	public static final String FOXBPMTASK="foxbpmtask";
	
	/**
	 * foxbpm产生的通知型任务
	 */
	public static final String NOTICETASK="noticetask";


	/**
	 * 调用节点状态记录
	 */
	public static final String CALLACTIVITYTASK="callactivitytask";
	
	/**
	 * 启动任务
	 */
	public static final String STARTEVENTTASK="starteventtask";
	
	/**
	 * 提醒
	 */
	public static final String REMINDTASK="remindtask";
	
	/**
	 * 结束任务
	 */
	public static final String ENDEVENTTASK="endeventtask";
	
	/**
	 * 捕获型任务记录
	 */
	public static final String INTERMEDIATECATCHEVENT="intermediatecatchevent";
	
	/**
	 * 等待任务
	 */
	public static final String RECEIVETASK="receivetask";
	
	
	/**
	 * 其他流程引擎产生的任务
	 */
	public static final String OTHERFLOWTASK="otherflowtask";
	
	
	/**
	 * 其他系统产生的通知型任务
	 */
	public static final String OTHERNOTICETASK="othernoticetask";
	


}
