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

import org.foxbpm.engine.impl.interceptor.CommandContext;

/**
 * 事务上下文工厂
 * 流程引擎初始化时创建
 * 在每次调用cmd时，会使用此工厂创建事务上下文
 * @author ych
 *
 */
public interface TransactionContextFactory {
	TransactionContext openTransactionContext(CommandContext commandContext);
}
