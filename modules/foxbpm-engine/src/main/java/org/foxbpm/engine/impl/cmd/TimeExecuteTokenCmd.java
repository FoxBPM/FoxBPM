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
 * 
 */
package org.foxbpm.engine.impl.cmd;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;

/**
 * 
 * 
 * TimeExecuteConnectorCmd
 * 
 * MAENLIANG 2014年6月27日 下午3:14:43
 * 
 * @version 1.0.0
 * 
 */
public class TimeExecuteTokenCmd implements Command<Void>, Serializable {
	/**
	 * serialVersionUID 序列化ID
	 */
	private static final long serialVersionUID = 718413641270120033L;
	protected String processInstanceID;
	protected String nodeID;
	protected String tokenID;
	@Override
	public Void execute(CommandContext commandContext) {
		ProcessInstanceEntity processInstance = commandContext.getProcessInstanceManager()
				.findProcessInstanceById(processInstanceID);
		TokenEntity tokenEntity = (TokenEntity) this
				.getExecutionContextFromInstance(processInstance);
		// TODO 结束令牌
		// 驱动令牌,继续往前执行
		tokenEntity.signal();
		return null;
	}
	/**
	 * 
	 * getExecutionContextFromInstance(根据记录的tokenID获取ListenerExecutionContext)
	 * 
	 * @param processInstance
	 * @return ListenerExecutionContext
	 * 
	 * @since 1.0.0
	 */
	private KernelTokenImpl getExecutionContextFromInstance(ProcessInstanceEntity processInstance) {
		List<KernelTokenImpl> tokens = processInstance.getTokens();
		Iterator<KernelTokenImpl> iterator = tokens.iterator();
		while (iterator.hasNext()) {
			KernelTokenImpl next = iterator.next();
			if (StringUtils.equalsIgnoreCase(next.getId(), this.tokenID)) {
				return next;
			}
		}
		return null;
	}
	public void setProcessInstanceID(String processInstanceID) {
		this.processInstanceID = processInstanceID;
	}
	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}
	public void setTokenID(String tokenID) {
		this.tokenID = tokenID;
	}

}
