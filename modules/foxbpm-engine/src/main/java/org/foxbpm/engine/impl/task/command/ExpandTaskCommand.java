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
 * @author kenshin
 * @author ych
 */
package org.foxbpm.engine.impl.task.command;

import java.util.HashMap;
import java.util.Map;



/**
 * 扩展类型
 * @author kenshin
 *
 */
public class ExpandTaskCommand extends BaseTaskCommand {
	
	public ExpandTaskCommand(){};
	
	public ExpandTaskCommand(BaseTaskCommand baseTaskCommand){
		super(baseTaskCommand);
	}
	
	protected String businessKey;
	
	protected String initiator;
	
	protected String processDefinitionKey;
	
	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getInitiator() {
		return initiator;
	}

	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}
	
	/**
	 * 动态参数Map
	 */
	protected Map<String, Object> paramMap=new HashMap<String, Object>();

	/**
	 * 获取动态参数Map
	 * @return
	 */
	public Map<String, Object> getParamMap() {
		return paramMap;
	}
	
	/**
	 * 获取动态参数
	 * @return
	 */
	public Object getParam(String paramKey) {
		return paramMap.get(paramKey);
	}

	/**
	 * 设置动态参数Map
	 * @param paramMap 动态参数Map
	 */
	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

}
