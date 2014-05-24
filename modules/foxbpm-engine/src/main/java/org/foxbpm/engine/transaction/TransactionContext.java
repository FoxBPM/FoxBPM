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
package org.foxbpm.engine.transaction;

import org.foxbpm.engine.config.TransactionState;

/**
 * 事务上下文
 * @author ych
 *
 */
public interface TransactionContext {
	/**
	 * 提交操作，应该触发提交前监听，提交后监听
	 */
	void commit();

	/**
	 * 回滚，应该触发回滚钱监听和回滚会监听
	 */
	void rollback();

	/**
	 * 增加监听， 类型transactionState
	 * @param transactionState
	 * @param transactionListener
	 */
	void addTransactionListener(TransactionState transactionState, TransactionListener transactionListener);
}
