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

import org.foxbpm.engine.database.ConnectionManagement;
import org.foxbpm.engine.impl.interceptor.CommandContext;

/**
 * 流程引擎线程副本管理器
 * @author kenshin
 */
public class Context {

	protected static ThreadLocal<Stack<Map<String,Connection>>> dbConnectionThreadLocal = new ThreadLocal<Stack<Map<String,Connection>>>();
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
		Stack<Map<String,Connection>> stack = getStack(dbConnectionThreadLocal);
		if (stack.isEmpty() || stack.peek() == null) {
			return null;
			
		}else {
			Map<String,Connection> connMap = stack.peek();
			return connMap.get("dbId");
		}
	}
	
	public static void setDBConnection(String dbID, Connection connection) {
		Stack<Map<String,Connection>> stack = getStack(dbConnectionThreadLocal);
		if(stack.isEmpty() || stack.peek() == null){
			Map<String,Connection> connMap =new HashMap<String,Connection>();
			connMap.put(dbID, connection);
			stack.push(connMap);
		}else{
			Map<String,Connection> connMap = stack.peek();
			connMap.put(dbID, connection);
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
