package org.foxbpm.web.common.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.web.common.constant.FoxbpmWebContextAttributeNameDefinition;

/**
 * 定义Web Context控制对session的操作,所有需要设置到session对话的对象均需要通过此类
 * 
 * 
 * @author MEL
 * @date 2014-06-06
 */
public class FoxbpmWebContext {
	private Map<String, Object> contextMap = new HashMap<String, Object>();

	public void putProcessDefinition(List<Object> processDefinitionList) {
		this.contextMap
				.put(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_PROCESSDEFINITIONLIST,
						processDefinitionList);
	}

	@SuppressWarnings("unchecked")
	public List<Object> getProcessDefinitionLisd() {
		return (List<Object>) this.contextMap
				.get(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_PROCESSDEFINITIONLIST);
	}
}
