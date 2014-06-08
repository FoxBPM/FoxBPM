package org.foxbpm.web.service.interfaces;

import java.util.List;

import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.web.common.exception.FoxbpmWebException;

/**
 * 流程接口
 * 
 * @author MEL
 * @date 2014-06-04
 */
public interface IWebappProcessService {
	public List<ProcessDefinition> queryProcessDefinition();

	public ProcessDefinition createProcessDefinition(String parameter)
			throws FoxbpmWebException;
}
