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
