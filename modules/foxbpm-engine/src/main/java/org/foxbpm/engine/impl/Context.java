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
package org.foxbpm.engine.impl;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.foxbpm.engine.db.ConnectionManagement;
import org.foxbpm.engine.db.FoxConnectionAdapter;
import org.foxbpm.engine.impl.interceptor.CommandContext;

/**
 * 流程引擎线程副本管理器
 * @author kenshin
 */
public class Context {

	protected static ThreadLocal<Stack<Map<String, FoxConnectionAdapter>>> dbConnectionThreadLocal = new ThreadLocal<Stack<Map<String, FoxConnectionAdapter>>>();
	protected static ThreadLocal<Stack<ProcessEngineConfigurationImpl>> processEngineConfigurationStackThreadLocal = new ThreadLocal<Stack<ProcessEngineConfigurationImpl>>();
	protected static ThreadLocal<Stack<CommandContext>> commandContextThreadLocal = new ThreadLocal<Stack<CommandContext>>();

	public static ProcessEngineConfigurationImpl getProcessEngineConfiguration() {
		Stack<ProcessEngineConfigurationImpl> stack = getStack(processEngineConfigurationStackThreadLocal);
		if (stack.isEmpty()) {
			return null;
		}
		return stack.peek();
	}
	
	public static Connection getDbConnection() {
		String dbID = ConnectionManagement.DAFAULT_DATABASE_ID;
		return getDbConnection(dbID);
	}

	public static Connection getDbConnection(String dbId) {
		Stack<Map<String, FoxConnectionAdapter>> stack = getStack(dbConnectionThreadLocal);
		if (stack.isEmpty() || stack.peek() == null) {
			Map<String, FoxConnectionAdapter> connectioMap = new HashMap<String, FoxConnectionAdapter>();
			FoxConnectionAdapter foxConnectionAdapter = ConnectionManagement.INSTANCE().getFoxConnectionAdapter(dbId);
			connectioMap.put(dbId, foxConnectionAdapter);
			getStack(dbConnectionThreadLocal).push(connectioMap);
			return foxConnectionAdapter.getConnection();

		}else {
			Map<String, FoxConnectionAdapter> connMap = stack.peek();
			FoxConnectionAdapter fixConnectionResult = connMap.get(dbId);
			if (fixConnectionResult == null) {
				FoxConnectionAdapter foxConnectionAdapter = ConnectionManagement.INSTANCE().getFoxConnectionAdapter(dbId);
				connMap.put(dbId, foxConnectionAdapter);
				return foxConnectionAdapter.getConnection();
			} else {
				return fixConnectionResult.getConnection();
			}
		}
	}
	
	public static void setFoxConnectionAdapter(String dbID, FoxConnectionAdapter foxConnectionAdapter) {
		Stack<Map<String, FoxConnectionAdapter>> stack = getStack(dbConnectionThreadLocal);
		if (stack.isEmpty()) {
			Map<String, FoxConnectionAdapter> conMap = new HashMap<String, FoxConnectionAdapter>();
			conMap.put(dbID, foxConnectionAdapter);
			getStack(dbConnectionThreadLocal).push(conMap);
		} else {
			Map<String, FoxConnectionAdapter> connMap = stack.peek();
			if (connMap == null) {
				Map<String, FoxConnectionAdapter> conMap = new HashMap<String, FoxConnectionAdapter>();
				conMap.put(dbID, foxConnectionAdapter);
				getStack(dbConnectionThreadLocal).push(conMap);
			} else {
				connMap.put(dbID, foxConnectionAdapter);
			}
		}
	}
	
	public static void removeDbConnection() {
		getStack(dbConnectionThreadLocal).clear();

	}

	public static void setProcessEngineConfiguration(ProcessEngineConfigurationImpl processEngineConfiguration) {
		getStack(processEngineConfigurationStackThreadLocal).push(processEngineConfiguration);
	}

	public static void removeProcessEngineConfiguration() {
		getStack(processEngineConfigurationStackThreadLocal).pop();
	}
	
	public static void setCommandContext(CommandContext commandContext) {
		getStack(commandContextThreadLocal).push(commandContext);
	}
	
	public static CommandContext getCommandContext() {
		Stack<CommandContext> stack = getStack(commandContextThreadLocal);
		if (stack.isEmpty()) {
			return null;
		}
		return stack.peek();
	}
	
	public static void removeCommandContext() {
		getStack(commandContextThreadLocal).pop();
	}
	
	protected static <T> Stack<T> getStack(ThreadLocal<Stack<T>> threadLocal) {
		Stack<T> stack = threadLocal.get();
		if (stack == null) {
			stack = new Stack<T>();
			threadLocal.set(stack);
		}
		return stack;
	}

}
