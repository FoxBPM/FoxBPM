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
package org.foxbpm.engine.impl.transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.config.TransactionState;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.sqlsession.ISqlSession;
import org.foxbpm.engine.transaction.TransactionContext;
import org.foxbpm.engine.transaction.TransactionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认引擎事务处理
 * @author ych
 *
 */
public class DefaultTransactionContext implements TransactionContext {

	private static Logger log = LoggerFactory.getLogger(DefaultTransactionContext.class);

	protected CommandContext commandContext;
	protected Map<TransactionState, List<TransactionListener>> stateTransactionListeners = null;

	public DefaultTransactionContext(CommandContext commandContext) {
		this.commandContext = commandContext;
	}

	public void commit() {
		log.debug("firing event committing...");
		fireTransactionEvent(TransactionState.COMMITTING);
		log.debug("committing the ibatis sql session...");
		getSqlSession().commit();
		log.debug("firing event committed...");
		fireTransactionEvent(TransactionState.COMMITTED);
	}

	public void rollback() {
		try {
			try {
				log.debug("firing event rolling back...");
				fireTransactionEvent(TransactionState.ROLLINGBACK);
			}finally {
				log.debug("rolling back ibatis sql session...");
				getSqlSession().rollback();
			}

		}finally {
			log.debug("firing event rolled back...");
			fireTransactionEvent(TransactionState.ROLLED_BACK);
		}
	}

	private ISqlSession getSqlSession() {
		return commandContext.getSession(ISqlSession.class);
	}

	public void addTransactionListener(TransactionState transactionState, TransactionListener transactionListener) {
		if (stateTransactionListeners == null) {
			stateTransactionListeners = new HashMap<TransactionState, List<TransactionListener>>();
		}
		List<TransactionListener> transactionListeners = stateTransactionListeners.get(transactionState);
		if (transactionListeners == null) {
			transactionListeners = new ArrayList<TransactionListener>();
			stateTransactionListeners.put(transactionState, transactionListeners);
		}
		transactionListeners.add(transactionListener);
	}

	protected void fireTransactionEvent(TransactionState transactionState) {
		if (stateTransactionListeners == null) {
			return;
		}
		List<TransactionListener> transactionListeners = stateTransactionListeners.get(transactionState);
		if (transactionListeners == null) {
			return;
		}
		for (TransactionListener transactionListener : transactionListeners) {
			transactionListener.execute(commandContext);
		}
	}

}
