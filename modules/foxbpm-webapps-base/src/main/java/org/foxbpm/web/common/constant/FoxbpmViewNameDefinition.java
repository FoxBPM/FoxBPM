package org.foxbpm.web.common.constant;

/**
 * 表示层的名称定义，用于映射/jsp/目录定义的jsp文件, 目前表示层支持.jsp格式文件
 * 
 * @author MEL
 * @date 2014-06-06
 */
public final class FoxbpmViewNameDefinition {
	public final static String START_PROCESS_VIEWNAME = "startProcess";
	/**
	 * 错误视图
	 */
	public final static String ERROR_VIEWNAME = "error";
	/**
	 * 代办视图
	 */
	public final static String QUERY_QUERYTODOTASK_VIEWNAME = "queryTodoTask";
	public final static String QUERY_QUERYALLPROCESSDEF_VIEWNAME = "queryProcessDef";
	public final static String QUERY_QUERYALLPROCESSINST_VIEWNAME = "queryProcessInst";
	public final static String QUERY_TASKDETAILINFOR_ACTION = "queryTaskDetailInfor";
	public final static String START_TASK_VIEWNAME = "startTask";
	public final static String DO_TASK_VIEWNAME = "doTask";
	public final static String COMPLETETASK_VIEWNAME = "result";
}
