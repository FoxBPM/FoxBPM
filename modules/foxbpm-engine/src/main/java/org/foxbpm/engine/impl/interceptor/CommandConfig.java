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
package org.foxbpm.engine.impl.interceptor;

import org.foxbpm.engine.config.TransactionPropagation;

/**
 * 命令执行配置
 * @author ych
 *
 */
public class CommandConfig {

	//默认复用commandContext
	private boolean isContextReuse = true;
	
	private boolean isCommit = true;
	//默认required事务传播类型
	private TransactionPropagation propagation = TransactionPropagation.REQUIRED;

	public boolean isContextReuse() {
		return isContextReuse;
	}

	public void setContextReuse(boolean isContextReuse) {
		this.isContextReuse = isContextReuse;
	}

	public TransactionPropagation getPropagation() {
		return propagation;
	}

	public void setPropagation(TransactionPropagation propagation) {
		this.propagation = propagation;
	}
	
	
	public void setCommit(boolean commit){
		this.isCommit = commit;
	}
	
	public boolean getCommit(){
		return isCommit;
	}
	

}
