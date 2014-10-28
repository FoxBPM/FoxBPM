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
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.foxbpm.engine.exception.FoxBPMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;

/**
 * 配置文件转换对象
 * 
 * @author yangguangftlp
 * @date 2014年10月27日
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class XMLToObject {
	protected static final Logger LOGGER = LoggerFactory.getLogger(XMLToObject.class);
	public static final String GENERAL_M_PREFIX = "set";
	public static final String BOOL_PREFIX = "is";
	private static XMLToObject instance;
	
	private XMLToObject() {
		
	}
	
	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static XMLToObject getInstance() {
		if (null == instance) {
			synchronized (XMLToObject.class) {
				if (null == instance) {
					instance = new XMLToObject();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 将xml转换Object
	 * 
	 * @param in
	 *            文档对象
	 * @param objClass
	 *            class
	 * @param flag
	 *            方法可见性 true public false all
	 * @return 返回 生成objClass 实例对象
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws DOMException
	 * @throws ClassNotFoundException
	 */
	public Object transform(InputStream in, Class objClass, boolean flag) {
		if (null == in) {
			throw new IllegalArgumentException("in is null!");
		}
		try {
			SAXReader reader = new SAXReader();
			return transform(reader.read(in), objClass, flag);
		} catch (DocumentException e) {
			LOGGER.error("从IO中读取文档出现错误!", e);
			throw new FoxBPMException("从IO中读取文档出现错误!", e);
		}
	}
	
	/**
	 * 将xml转换Object
	 * 
	 * @param doc
	 *            文档对象
	 * @param objClass
	 *            class
	 * @param flag
	 *            是否获取 objClass 继承的所有方法
	 * @return 返回 生成objClass 实例对象
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws DOMException
	 * @throws ClassNotFoundException
	 */
	public Object transform(Document doc, Class objClass, boolean flag) {
		if (null == doc) {
			throw new IllegalArgumentException("doc is null!");
		}
		if (null == objClass) {
			throw new IllegalArgumentException("objClass is null!");
		}
		try {
			return doRootElement(doc.getRootElement(), objClass.newInstance(), flag);
		} catch (Exception e) {
			LOGGER.error("解析文档出现错误!", e);
			throw new FoxBPMException("解析文档出现错误!", e);
		}
	}
	
	private Object doRootElement(Element root, Object obj, boolean flag) throws ClassNotFoundException,
	    IllegalAccessException, InvocationTargetException, InstantiationException {
		if (null != root) {
			Element element = null;
			Method method = null;
			Class paramType = null;
			Object paramObj = null;
			for (Iterator<Element> iterator = root.elements().iterator(); iterator.hasNext();) {
				element = iterator.next();
				method = getMethods(GENERAL_M_PREFIX, obj.getClass(), flag).get(generateMethodName(GENERAL_M_PREFIX, element.getName()));
				if (null != method && method.getParameterTypes().length == 1) {
					paramType = method.getParameterTypes()[0];
					if (!paramType.isArray() && !paramType.isPrimitive()) {
						paramObj = paramType.newInstance();
						// 处理节点属性
						doAttributes(element, paramObj, getMethods(GENERAL_M_PREFIX, paramType, flag));
						// 将实例化的对象设置到对应obj属性中
						method.invoke(obj, paramObj);
						// 获取子节点
						doChildElement(element.elements(), paramObj, flag);
					}
				}
			}
		}
		return obj;
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
	
	/**
	 * 处理属性
	 * 
	 * @param element
	 * @param paramObj
	 * @param temp
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private void doAttributes(Element element, Object paramObj, Map<String, Method> methods)
	    throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		// 处理element属性
		Method method = null;
		Attribute attribute = null;
		for (int i = 0, length = element.attributeCount(); i < length; i++) {
			attribute = element.attribute(i);
			method = methods.get(generateMethodName(GENERAL_M_PREFIX, attribute.getName()));
			if (null != method && method.getParameterTypes().length == 1) {
				doAttributeValue(paramObj, method, attribute.getValue(), method.getParameterTypes()[0]);
			}
		}
	}
	
	/**
	 * 处理参数值
	 * 
	 * @param pObj
	 * @param method
	 * @param value
	 * @param pType
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void doAttributeValue(Object pObj, Method method, String value, Class pType)
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
	
	/**
	 * 处理子节点
	 * 
	 * @param nodeList
	 * @param obj
	 * @param flag
	 * @return
	 * @throws IllegalArgumentException
	 * @throws DOMException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	private Object doChildElement(List<Element> elements, Object obj, boolean flag) throws InstantiationException,
	    IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
		
		if (!elements.isEmpty()) {
			Map<String, Method> methodMap = getMethods(GENERAL_M_PREFIX, obj.getClass(), flag);
			Map<String, List<Object>> objMap = new HashMap<String, List<Object>>();
			Element element = null;
			Method method = null;
			String methodName = null;
			Object paramObj = null;
			for (Iterator<Element> iterator = elements.iterator(); iterator.hasNext();) {
				element = iterator.next();
				methodName = generateMethodName(GENERAL_M_PREFIX, element.getName());
				method = methodMap.get(methodName);
				if (null != method && method.getParameterTypes().length == 1) {
					paramObj = createParameterInstance(method);
					if (!objMap.containsKey(methodName)) {
						objMap.put(methodName, new ArrayList<Object>());
					}
					doAttributes(element, paramObj, getMethods(GENERAL_M_PREFIX, paramObj.getClass(), flag));
					objMap.get(methodName).add(doChildElement(element.elements(), paramObj, flag));
				}
			}
			// 这里处理子节点
			for (Entry<String, List<Object>> entry : objMap.entrySet()) {
				method = methodMap.get(entry.getKey());
				// 处理数组
				if (method.getParameterTypes()[0].isArray()) {
					method.invoke(obj, (Object) copyOf(entry.getValue().toArray(), entry.getValue().size(), (Class) method.getParameterTypes()[0]));
				} else /** 处理list集合 */
				if (method.getParameterTypes()[0].isAssignableFrom(List.class)) {
					method.invoke(obj, entry.getValue());
				} else {
					method.invoke(obj, entry.getValue().get(0));
				}
			}
		}
		return obj;
	}
	
	/**
	 * 根据参数类型创建实例
	 * 
	 * @param method
	 * @return
	 */
	private Object createParameterInstance(Method method) {
		Object paramObj = null;
		Class<?> class1 = method.getParameterTypes()[0];
		try {
			if (class1.isArray()) {
				paramObj = Class.forName(new StringBuffer().append(class1.getName()).delete(0, 2).reverse().deleteCharAt(0).reverse().toString()).newInstance();
			} else if (class1.isAssignableFrom(List.class)) {
				Type paramType = method.getGenericParameterTypes()[0];// 方法的参数列表
				if (paramType instanceof ParameterizedType)/**//* 如果是泛型类型 */{
					Type type = ((ParameterizedType) paramType).getActualTypeArguments()[0];// 泛型类型列表
					if (type instanceof Class) {
						paramObj = Class.forName(((Class) type).getName()).newInstance();
					}
				}
			} else {
				paramObj = class1.newInstance();
			}
		} catch (Exception e) {
			LOGGER.error("根据参数类型实例对象出现错误!,参数类型:" + class1);
			throw new FoxBPMException("根据参数类型实例对象出现错误!,参数类型:" + class1, e);
		}
		return paramObj;
	}
	
	/**
	 * 拷贝数组
	 * 
	 * @param original
	 *            原数组
	 * @param newLength
	 *            新的长度
	 * @param newType
	 *            新的类型
	 * @return
	 */
	private <T, U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType) {
		T[] copy = ((Object) newType == (Object) Object[].class) ? (T[]) new Object[newLength] : (T[]) Array.newInstance(newType.getComponentType(), newLength);
		System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
		return copy;
	}
	
	/**
	 * 根据前缀获取class上的方法
	 * 
	 * @param prefix
	 *            方法名前缀
	 * @param cls
	 *            class
	 * @param flag
	 *            true 获取public方法,false includes public, protected, default
	 *            (package) access, and private methods
	 * @return 返回prefix前缀的所有方法
	 */
	private Map<String, Method> getMethods(String prefix, Class cls, boolean flag) {
		Map<String, Method> methodMap = new HashMap<String, Method>();
		Method[] methods = flag ? cls.getMethods() : cls.getDeclaredMethods();
		if (null != methods) {
			for (Method m : methods) {
				if (m.getParameterTypes().length == 1 && m.getName().startsWith(prefix)
				        && Modifier.PUBLIC == m.getModifiers()) {
					methodMap.put(m.getName(), m);
				}
			}
		}
		return methodMap;
	}
}
