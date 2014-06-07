package org.foxbpm.engine.spring;

import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.transaction.TransactionContext;
import org.foxbpm.engine.transaction.TransactionContextFactory;
import org.springframework.transaction.PlatformTransactionManager;

public class SpringTransactionContextFactory implements TransactionContextFactory {

	protected PlatformTransactionManager transactionManager;
	public SpringTransactionContextFactory(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	@Override
	public TransactionContext openTransactionContext(CommandContext commandContext) {
		return new SpringTransactionContext(transactionManager, commandContext);
	}
}
