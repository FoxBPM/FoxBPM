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

import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * spring事务拦截器
 * 在每个cmd执行前进行事务处理,如果在spring事务上下文中，这里不影响
 * @author ych
 *
 */
public class SpringTransactionInterceptor extends CommandInterceptor {

	Logger log = LoggerFactory.getLogger(SpringTransactionInterceptor.class);
	private PlatformTransactionManager transactionManager;
	public SpringTransactionInterceptor(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	@Override
	public <T> T execute(final Command<T> command) {
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
	    T result = transactionTemplate.execute(new TransactionCallback<T>() {
	      public T doInTransaction(TransactionStatus status) {
	        return next.execute(command);
	      }
	    });
	    return result;
	}

}
