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
 * @author yangguangftlp
 */
package org.foxbpm.engine.impl.util;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.foxbpm.engine.config.FoxBPMConfig;
import org.foxbpm.engine.exception.FoxBPMBizException;
import org.foxbpm.engine.impl.datavariable.DataObjectDefinitionImpl;
import org.foxbpm.engine.impl.event.EventListenerImpl;
import org.foxbpm.engine.impl.task.CommandParam;
import org.foxbpm.engine.impl.task.TaskCommandDefinitionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * foxbpmcfg配置文件解析处理类
 * 
 * @author yangguangftlp
 * @date 2014年10月28日
 */
@SuppressWarnings("unchecked")
public class FoxBPMCfgParseUtil {
	protected static final Logger LOGGER = LoggerFactory.getLogger(XMLToObject.class);
	private static FoxBPMCfgParseUtil instance;
	public static final String GENERAL_M_PREFIX = "set";
	public static final String BOOL_PREFIX = "is";
	
	public static final String ELEMENT_FOXBPMCONFIG = "FoxBPMConfig";
	public static final String ELEMENT_TASKCOMMANDS = "taskCommands";
	public static final String ELEMENT_TASKCOMMANDDEFINITION = "taskCommandDefinition";
	public static final String ELEMENT_COMMANDPARAM = "commandParam";
	public static final String ELEMENT_EVENTLISTENERS = "eventListeners";
	public static final String ELEMENT_EVENTLISTENER = "eventListener";
	public static final String ELEMENT_BIZDATAOBJECTS = "bizDataObjects";
	public static final String ELEMENT_DATAOBJECTBEHAVIOR = "dataObjectBehavior";
	
	private FoxBPMCfgParseUtil() {
		
	}
	
	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static FoxBPMCfgParseUtil getInstance() {
		if (null == instance) {
			synchronized (XMLToObject.class) {
				if (null == instance) {
					instance = new FoxBPMCfgParseUtil();
				}
			}
		}
		return instance;
	}
	/**
	 * 解析foxbpm.cfg配置文件
	 * 
	 * @param in
	 *            文件流
	 * @return 返回解析后生成的FoxBPMConfig实例对象
	 */
	public FoxBPMConfig parsecfg(InputStream in) {
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(in);
			Element rootElem = document.getRootElement();
			if (ELEMENT_FOXBPMCONFIG.equals(rootElem.getName())) {
				return parseElement(rootElem);
			}
		} catch (Exception e) {
			throw new FoxBPMBizException("解析foxbpm.cfg配置出现错误!", e);
		}
		return null;
	}
	
	private FoxBPMConfig parseElement(Element element) throws IllegalArgumentException, IllegalAccessException,
	    InvocationTargetException {
		Element childElem = null;
		String nodeName = null;
		FoxBPMConfig foxBPMConfig = new FoxBPMConfig();
		for (Iterator<Element> iterator = element.elementIterator(); iterator.hasNext();) {
			childElem = iterator.next();
			nodeName = childElem.getName();
			if (ELEMENT_TASKCOMMANDS.equals(nodeName)) {
				foxBPMConfig.setTaskCommandDefinitions(parseTaskCommands(childElem));
			} else if (ELEMENT_EVENTLISTENERS.equals(nodeName)) {
				foxBPMConfig.setEventListeners(parseEventListeners(childElem));
			} else if (ELEMENT_BIZDATAOBJECTS.equals(nodeName)) {
				foxBPMConfig.setDataObjectDefinitions(parseBizDataObject(childElem));
			}
		}
		return foxBPMConfig;
	}
	
	/**
	 * 解析任务命令
	 * 
	 * @param element
	 *            节点
	 * @return 返回任务命令定义
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private List<TaskCommandDefinitionImpl> parseTaskCommands(Element element) throws IllegalArgumentException,
	    IllegalAccessException, InvocationTargetException {
		Element childElem = null;
		String nodeName = null;
		List<TaskCommandDefinitionImpl> taskCommandDefs = null;
		TaskCommandDefinitionImpl taskCommandDef = null;
		List<CommandParam> commandParams = null;
		for (Iterator<Element> iterator = element.elementIterator(); iterator.hasNext();) {
			childElem = iterator.next();
			nodeName = childElem.getName();
			if (ELEMENT_TASKCOMMANDDEFINITION.equals(nodeName)) {
				taskCommandDef = new TaskCommandDefinitionImpl();
				// 处理基本属性
				doAttributes(childElem, taskCommandDef);
				// 处理命令
				commandParams = parseCommandParams(childElem);
				if (null != commandParams) {
					taskCommandDef.setCommandParam(commandParams);
				}
				if (null == taskCommandDefs) {
					taskCommandDefs = new ArrayList<TaskCommandDefinitionImpl>();
				}
				taskCommandDefs.add(taskCommandDef);
			}
		}
		return taskCommandDefs;
	}
	private List<CommandParam> parseCommandParams(Element element) throws IllegalArgumentException,
	    IllegalAccessException, InvocationTargetException {
		
		Element childElem = null;
		String nodeName = null;
		List<CommandParam> commandParams = null;
		CommandParam commandParam = null;
		for (Iterator<Element> iterator = element.elementIterator(); iterator.hasNext();) {
			// 处理基本属性
			childElem = iterator.next();
			nodeName = childElem.getName();
			if (ELEMENT_COMMANDPARAM.equals(nodeName)) {
				commandParam = new CommandParam();
				doAttributes(childElem, commandParam);
				if (null == commandParams) {
					commandParams = new ArrayList<CommandParam>();
				}
				commandParams.add(commandParam);
			}
		}
		return commandParams;
	}
	private List<EventListenerImpl> parseEventListeners(Element element) throws IllegalArgumentException,
	    IllegalAccessException, InvocationTargetException {
		
		Element childElem = null;
		String nodeName = null;
		List<EventListenerImpl> eventListeners = null;
		EventListenerImpl eventListener = null;
		for (Iterator<Element> iterator = element.elementIterator(); iterator.hasNext();) {
			childElem = iterator.next();
			nodeName = childElem.getName();
			if (ELEMENT_EVENTLISTENER.equals(nodeName)) {
				eventListener = new EventListenerImpl();
				// 处理基本属性
				doAttributes(childElem, eventListener);
				if (null == eventListeners) {
					eventListeners = new ArrayList<EventListenerImpl>();
				}
				eventListeners.add(eventListener);
			}
		}
		return eventListeners;
	}
	private List<DataObjectDefinitionImpl> parseBizDataObject(Element element) throws IllegalArgumentException,
	    IllegalAccessException, InvocationTargetException {
		Element childElem = null;
		String nodeName = null;
		List<DataObjectDefinitionImpl> dataObjectDefinitions = null;
		DataObjectDefinitionImpl dataObjectDefinition = null;
		for (Iterator<Element> iterator = element.elementIterator(); iterator.hasNext();) {
			childElem = iterator.next();
			nodeName = childElem.getName();
			if (ELEMENT_DATAOBJECTBEHAVIOR.equals(nodeName)) {
				dataObjectDefinition = new DataObjectDefinitionImpl();
				// 处理基本属性
				doAttributes(childElem, dataObjectDefinition);
				if (null == dataObjectDefinitions) {
					dataObjectDefinitions = new ArrayList<DataObjectDefinitionImpl>();
				}
				dataObjectDefinitions.add(dataObjectDefinition);
			}
		}
		return dataObjectDefinitions;
	}
	
	private void doAttributes(Element element, Object paramObj) throws IllegalArgumentException,
	    IllegalAccessException, InvocationTargetException {
		// 处理element属性
		Method method = null;
		Attribute attribute = null;
		Map<String, Method> methodMap = getSetMethods(GENERAL_M_PREFIX, paramObj);
		for (int i = 0, length = element.attributeCount(); i < length; i++) {
			attribute = element.attribute(i);
			method = methodMap.get(generateMethodName(GENERAL_M_PREFIX, attribute.getName()));
			if (null != method) {
				doAttributeValue(paramObj, method, attribute.getValue(), method.getParameterTypes()[0]);
			}
		}
	}
	private Map<String, Method> getSetMethods(String prefix, Object obj) {
		Map<String, Method> methodMap = new HashMap<String, Method>();
		Method[] methods = obj.getClass().getMethods();
		
		if (null != methods) {
			int length = methods.length;
			Method method = null;
			for (int i = 0; i < length; i++) {
				method = methods[i];
				if (method.getParameterTypes().length == 1 && method.getName().startsWith(prefix)) {
					methodMap.put(method.getName(), method);
				}
			}
		}
		return methodMap;
	}
	/**
	 * 通过节点名称构造方法名
	 * 
	 * @param prefix
	 *            get
	 * @param name
	 *            节点名称
	 * @return 返回方法名
	 */
	private String generateMethodName(String prefix, String name) {
		StringBuffer sbuffer = new StringBuffer();
		sbuffer.append(name.substring(name.indexOf(':') + 1));
		// 处理boolean变量
		if (sbuffer.toString().startsWith(BOOL_PREFIX)) {
			sbuffer.delete(0, 2);
		}
		// 处理有些节点属性a:b 去掉a:
		return new StringBuffer(prefix).append(Character.toUpperCase(sbuffer.charAt(0))).append(sbuffer.substring(1)).toString();
	}
	private void doAttributeValue(Object pObj, Method method, String value, Class<?> pType)
	    throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		if (String.class == pType) {
			method.invoke(pObj, value);
		} else if (boolean.class == pType) {
			method.invoke(pObj, Boolean.valueOf(value));
		} else {
			// 暂不支持的类型
			LOGGER.warn("不支持的类型是:" + pType);
		}
	}
}
