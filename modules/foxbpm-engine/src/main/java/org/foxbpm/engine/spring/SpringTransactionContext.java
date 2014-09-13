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
package org.foxbpm.engine.spring;

import org.foxbpm.engine.config.TransactionState;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.transaction.TransactionContext;
import org.foxbpm.engine.transaction.TransactionListener;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * spring事务上下文
 * 在spring中，由于将事务托管给了spring管理，所以此类意义不大，后期可能会扩展事务监听
 * @author ych
 *
 */
public class SpringTransactionContext implements TransactionContext {

	protected PlatformTransactionManager transactionManager;
	protected CommandContext commandContext;
	
	public SpringTransactionContext(PlatformTransactionManager transactionManager, CommandContext commandContext) {
		this.transactionManager = transactionManager;
		this.commandContext = commandContext;
	}
	
	@Override
	public void commit() {

	}

	@Override
	public void rollback() {
		transactionManager.getTransaction(null).setRollbackOnly();
	}

	@Override
	public void addTransactionListener(TransactionState transactionState, TransactionListener transactionListener) {
		// TODO Auto-generated method stub

	}

}
