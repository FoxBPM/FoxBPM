package org.foxbpm.engine.spring;

import org.foxbpm.engine.config.TransactionState;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.transaction.TransactionContext;
import org.foxbpm.engine.transaction.TransactionListener;
import org.springframework.transaction.PlatformTransactionManager;

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
