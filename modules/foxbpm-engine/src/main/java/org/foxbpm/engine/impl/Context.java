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

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.foxbpm.engine.ConnectionManagement;
import org.foxbpm.engine.database.FoxConnectionAdapter;

/**
 * @author kenshin
 */
public class Context {

	protected static ThreadLocal<Stack<Map<String, FoxConnectionAdapter>>> dbConnectionThreadLocal = new ThreadLocal<Stack<Map<String, FoxConnectionAdapter>>>();
	protected static ThreadLocal<Stack<ProcessEngineConfigurationImpl>> processEngineConfigurationStackThreadLocal = new ThreadLocal<Stack<ProcessEngineConfigurationImpl>>();
	// languageType
	protected static ThreadLocal<Stack<String>> languageTypeThreadLocal = new ThreadLocal<Stack<String>>();
	protected static ThreadLocal<Stack<String>> connectionManagementDefault = new ThreadLocal<Stack<String>>();

	public static void setConnectionManagementDefault(String cmId) {
		getStack(connectionManagementDefault).push(cmId);
	}

	public static String getConnectionManagementDefault() {

		Stack<String> stack = getStack(connectionManagementDefault);
		if (stack.isEmpty()) {
			return null;
		}

		String languageTypeTemp = stack.peek();
		if (languageTypeTemp == null || languageTypeTemp.equals("")) {
			return null;
		} else {
			return stack.peek();

		}

	}
	
	public static void removeConnectionManagement() {

		getStack(connectionManagementDefault).clear();

	}

	// protected static ThreadLocal<Stack<Interpreter>>
	// bshInterpreterThreadLocal = new ThreadLocal<Stack<Interpreter>>();
	public static void setLanguageType(String languageType) {
		getStack(languageTypeThreadLocal).push(languageType);
	}

	public static String getLanguageType() {

		Stack<String> stack = getStack(languageTypeThreadLocal);
		if (stack.isEmpty()) {
			return "zh_CN";
		}

		String languageTypeTemp = stack.peek();
		if (languageTypeTemp == null || languageTypeTemp.equals("")) {
			return "zh_CN";
		} else {
			return stack.peek();
		}
	}

	public static void removeLanguageType() {

		getStack(languageTypeThreadLocal).clear();
	}

	public static Map<String, FoxConnectionAdapter> getDbConnectionMap() {

		Stack<Map<String, FoxConnectionAdapter>> stack = getStack(dbConnectionThreadLocal);
		if (stack.isEmpty()) {
			return null;
		}

		Map<String, FoxConnectionAdapter> connMap = stack.peek();
		if (connMap == null) {
			return null;
		} else {
			return connMap;
		}

	}

	public static void setFixConnectionResult(String dbID, FoxConnectionAdapter fixConnectionResult) {

		Stack<Map<String, FoxConnectionAdapter>> stack = getStack(dbConnectionThreadLocal);
		if (stack.isEmpty()) {
			Map<String, FoxConnectionAdapter> conMap = new HashMap<String, FoxConnectionAdapter>();
			conMap.put(dbID, fixConnectionResult);

			getStack(dbConnectionThreadLocal).push(conMap);
		} else {
			Map<String, FoxConnectionAdapter> connMap = stack.peek();
			if (connMap == null) {
				Map<String, FoxConnectionAdapter> conMap = new HashMap<String, FoxConnectionAdapter>();
				conMap.put(dbID, fixConnectionResult);

				getStack(dbConnectionThreadLocal).push(conMap);
			} else {
				connMap.put(dbID, fixConnectionResult);
			}
		}

	}

	public static void setFixConnectionResult(FoxConnectionAdapter fixConnectionResult) {

		String dbID = ConnectionManagement.defaultDataBaseId;

		setFixConnectionResult(dbID, fixConnectionResult);

	}

	public static void removeDbConnection() {

		getStack(dbConnectionThreadLocal).clear();

	}

	public static ProcessEngineConfigurationImpl getProcessEngineConfiguration() {
		Stack<ProcessEngineConfigurationImpl> stack = getStack(processEngineConfigurationStackThreadLocal);
		if (stack.isEmpty()) {
			return null;
		}
		return stack.peek();
	}

	public static void setProcessEngineConfiguration(ProcessEngineConfigurationImpl processEngineConfiguration) {
		getStack(processEngineConfigurationStackThreadLocal).push(processEngineConfiguration);
	}

	public static void removeProcessEngineConfiguration() {
		getStack(processEngineConfigurationStackThreadLocal).clear();
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
