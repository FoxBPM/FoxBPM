package org.foxbpm.web.common.constant;

/**
 * 异常码定义 对应的异常消息定义到properties文件
 * 
 * @author MEL
 * @date 2014-06-06
 */
public interface FoxbpmExceptionCode {
	/**
	 * 启动流程异常
	 */
	String START_PROCESS_EXCEPTION = "BIZPROCESSSTART";

	/**
	 * 恢复流程出现异常
	 */
	String RESUME_PROCESS_EXCEPTION = "BIZPROCESSRESUME";

	/**
	 * 成功
	 */
	String FOXBPMEX_SUCCESS = "000000";
	/**
	 * businessKey
	 */
	String FOXBPMEX_BUSINESSKEY = "000001";
	/**
	 * 用户名
	 */
	String FOXBPMEX_USERID = "000002";
	/**
	 * 流程定义key processDefinitionKey
	 */
	String FOXBPMEX_PROCESSDEFKEY = "000003";
	/**
	 * 任务Id
	 */
	String FOXBPMEX_TASKID = "000004";
	/**
	 * 命令类型
	 */
	String FOXBPMEX_COMMANDTYPE = "000005";
	/**
	 * 命令Id
	 */
	String FOXBPMEX_COMMANDID = "000006";
	/**
	 * 流程定义Id
	 */
	String FOXBPMEX_PROCESSDEFID = "000007";
	/**
	 * 流程实例Id
	 */
	String FOXBPMEX_PROCESSINSTID = "000008";
	/**
	 * 任务意见
	 */
	String FOXBPMEX_TASKCOMMENT = "000009";
	/**
	 * 流程定义发布Id
	 */
	String FOXBPMEX_DEPLOYMENTID = "000010";
	/**
	 * 服务器内部错误
	 */
	String FOXBPMEX_ERROR = "999999";
}
