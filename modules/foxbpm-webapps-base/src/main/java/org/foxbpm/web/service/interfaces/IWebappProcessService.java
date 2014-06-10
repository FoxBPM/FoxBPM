package org.foxbpm.web.service.interfaces;

import java.util.List;
import java.util.Map;

import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.web.common.exception.FoxbpmWebException;
import org.foxbpm.web.common.util.Pagination;

/**
 * 流程接口
 * 
 * @author MEL
 * @date 2014-06-04
 */
public interface IWebappProcessService {
	public List<ProcessDefinition> queryProcessDefinition();

	public ProcessDefinition createProcessDefinition(String parameter) throws FoxbpmWebException;

	/**
	 * 查询所有流程定义信息
	 * 
	 * @param pageInfor
	 *            分页对象
	 * @param params
	 *            查询条件参数
	 * @return 返回查询结果
	 * @throws FoxbpmWebException
	 */
	Map<String, List<Map<String, Object>>> queryProcessDef(Pagination<String> pageInfor, Map<String, Object> params) throws FoxbpmWebException;

	/**
	 * 查询所有流程实例信息
	 * 
	 * @param pageInfor
	 *            分页对象
	 * @param params
	 *            查询条件参数
	 * @return 返回查询结果
	 * @throws FoxbpmWebException
	 */
	Map<String, Object> queryProcessInst(Pagination<String> pageInfor, Map<String, Object> params) throws FoxbpmWebException;
}
