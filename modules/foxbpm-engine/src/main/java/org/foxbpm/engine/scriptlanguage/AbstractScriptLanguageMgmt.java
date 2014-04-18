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
package org.foxbpm.engine.scriptlanguage;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.foxbpm.engine.runtime.ExecutionContext;

public abstract class AbstractScriptLanguageMgmt {
	
	/**
	 * 执行表达式
	 * @param scriptText 表达式文本
	 * @return
	 */
	public abstract Object execute(String scriptText);
	
	/**
	 * 向表达式环境中放入变量
	 * @param variableName 变量名称
	 * @param variableObj 变量值
	 */
	public abstract void setVariable(String variableName, Object variableObj);
	
	/**
	 * 向表达式环境中放入变量,这个变量将是流程变量${var}
	 * @param variableName 变量名称
	 * @param variableObj 变量值
	 * @param executionContext 流程上下文
	 */
	public abstract void setVariable(String variableName, Object variableObj,ExecutionContext executionContext);
	
	/**
	 * 获取变量值
	 * @param variableName 变量名称
	 * @return
	 */
	public abstract Object getVariable(String variableName);
	
	/**
	 * 执行表达式
	 * @param scriptText 表达式字符串
	 * @param executionContext 流程上下文
	 * @return
	 */
	public abstract Object execute(String scriptText, ExecutionContext executionContext);
	
	/**
	 * 脚本管理器初始化方法
	 */
	public abstract AbstractScriptLanguageMgmt init();
	
	public abstract void close();
	
	public static List<String> getDataVariableList(String scriptText) {
		String inexp = scriptText;
		String regex = "\\$\\{[^}{]+\\}";
		Pattern regexExpType = Pattern.compile(regex);
		Matcher mType = regexExpType.matcher(inexp);
		String expType = inexp;
		List<String> list = new ArrayList<String>();
		while (mType.find()) {
			expType = mType.group();
			expType = expType.substring(2, expType.length() - 1);
			list.add(expType);
		}
		return list;
	}

	private static String __REGEX_SIGNS = "\\$\\{[^}{]+\\}";

	public static String getExpressionAll(String inexp) {
		String str = null;
		String regex = __REGEX_SIGNS;
		Pattern regexExpType = Pattern.compile(regex);
		Matcher mType = regexExpType.matcher(inexp);
		String expType = inexp;
		StringBuffer sb = new StringBuffer();
		while (mType.find()) {
			expType = mType.group();
			String dist = expType.substring(2, expType.length() - 1); // StringUtil.getString(getExpressionValue(dataView,expType));
			mType.appendReplacement(sb, dist);
		}
		mType.appendTail(sb);
		str = sb.toString();
		return str;
	}
	public static void dataVariableCalculate(String scriptText, ExecutionContext executionContext){

	}
}
