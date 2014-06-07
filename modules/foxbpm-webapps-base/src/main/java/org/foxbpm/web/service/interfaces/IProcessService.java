package org.foxbpm.web.service.interfaces;

import org.foxbpm.web.common.exception.FoxbpmWebException;
import org.foxbpm.web.model.ProcessDefinition;

/**
 * 流程接口
 * 
 * @author MEL
 * @date 2014-06-04
 */
public interface IProcessService {
	public ProcessDefinition createProcessDefinition(String parameter)
			throws FoxbpmWebException;
}
