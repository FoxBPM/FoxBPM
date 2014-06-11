
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
package org.foxbpm.web.common.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * JSON工具类
 * @author yangguangftlp
   @date 2014年6月11日
 */
public class JSONUtil {

	/**
	 * JSON中的NULL
	 */
	public static final Object NULL = JSONObject.NULL;

	/** 空的 {@code JSON} 数据 - <code>"{}"</code>。 */
	public static final String EMPTY_JSON = "{}";
	/** 空的 {@code JSON} 数组(集合)数据 - {@code "[]"}。 */
	public static final String EMPTY_JSON_ARRAY = "[]";
	/** 默认的 {@code JSON} 日期/时间字段的格式化模式。 */
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss SSS";
	/** {@code Google Gson} 的 {@literal @Since} 注解常用的版本号常量 - {@code 1.0}。 */
	public static final Double SINCE_VERSION_10 = 1.0d;
	/** {@code Google Gson} 的 {@literal @Since} 注解常用的版本号常量 - {@code 1.1}。 */
	public static final Double SINCE_VERSION_11 = 1.1d;
	/** {@code Google Gson} 的 {@literal @Since} 注解常用的版本号常量 - {@code 1.2}。 */
	public static final Double SINCE_VERSION_12 = 1.2d;

	private JSONUtil() {

	}

	/**
	 * 将json数据转换为map
	 * 
	 * @param strJSON
	 * @return map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseJSON2MapFirstLevel(String strJSON) {
		if (strJSON != null && isJSONObject(strJSON))
			try {
				return (Map<String, Object>) __parseJSON(new JSONObject(strJSON), false);
			} catch (JSONException e) {
				return new HashMap<String, Object>();
			}
		else
			return new HashMap<String, Object>();
	}

	/**
	 * 将json数据转换为map
	 * 
	 * @param strJSON
	 * @return map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseJSON2Map(String strJSON) {
		if (strJSON != null && isJSONObject(strJSON))
			try {
				return (Map<String, Object>) __parseJSON(new JSONObject(strJSON), true);
			} catch (JSONException e) {
				return new HashMap<String, Object>();
			}
		else
			return new HashMap<String, Object>();
	}

	/**
	 * 将json数据转换为list
	 * 
	 * @param strJSON
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> parseJSON2List(String strJSON) {
		if (strJSON != null && isJSONArray(strJSON))
			try {
				return (List<Object>) __parseJSON(new JSONArray(strJSON), true);
			} catch (JSONException e) {
				return new ArrayList<Object>();
			}
		else
			return new ArrayList<Object>();
	}

	/**
	 * 通过JSON字符串转换成JAVA对象Map,List
	 * 
	 * @param strJSON
	 *            只能是对象{a:'',b:''}或数组['a','b']
	 * @return JSON
	 * @throws JSONException
	 */
	public static Object parseJSON(String strJSON) {
		Object obj = null;
		if (strJSON != null && isJSONArray(strJSON)) {
			obj = parseJSON2List(strJSON);
		} else if (strJSON != null && isJSONObject(strJSON)) {
			obj = parseJSON2Map(strJSON);
		}
		return obj;
	}

	private static Object __parseJSON(Object jsonObj, boolean doAll) throws JSONException {
		Object ret = null;
		if (jsonObj instanceof JSONArray) {
			JSONArray _array = (JSONArray) jsonObj;
			ArrayList<Object> _ret_list = new ArrayList<Object>();
			for (int _i = 0; _i < _array.length(); _i++) {
				Object _item = _array.get(_i);
				if (doAll) {
					if (_item instanceof JSONObject || _item instanceof JSONArray)
						_ret_list.add(__parseJSON(_item, true));
					else
						_ret_list.add(_item);
				} else {
					_ret_list.add(_item.toString());
				}
			}
			ret = _ret_list;
		} else if (jsonObj instanceof JSONObject) {
			JSONObject _obj = (JSONObject) jsonObj;
			Map<String, Object> _ret_map = new HashMap<String, Object>();
			for (Iterator<?> _it = _obj.keys(); _it.hasNext();) {
				String _key = _it.next().toString();
				Object _item = _obj.get(_key);
				if (doAll) {
					if (_item instanceof JSONObject || _item instanceof JSONArray)
						_ret_map.put(_key, __parseJSON(_item, true));
					else
						_ret_map.put(_key, _item);
				} else {
					_ret_map.put(_key, _item.toString());
				}
			}
			ret = _ret_map;
		}
		return ret;
	}

	private static String toJson(Object target, Type targetType, boolean isSerializeNulls, Double version, String datePattern, boolean excludesFieldsWithoutExpose) {
		if (target == null)
			return EMPTY_JSON;
		GsonBuilder builder = new GsonBuilder();
		if (isSerializeNulls)
			builder.serializeNulls();
		if (version != null)
			builder.setVersion(version.doubleValue());
		// if (StringUtil.isEmpty(datePattern))
		// datePattern = DEFAULT_DATE_PATTERN;
		builder.setDateFormat(datePattern);
		if (excludesFieldsWithoutExpose)
			builder.excludeFieldsWithoutExposeAnnotation();
		String result = null;
		Gson gson = builder.create();
		try {
			if (targetType != null) {
				result = gson.toJson(target, targetType);
			} else {
				result = gson.toJson(target);
			}
		} catch (Exception ex) {
			if (target instanceof Collection || target instanceof Iterator || target instanceof Enumeration || target.getClass().isArray()) {
				result = EMPTY_JSON_ARRAY;
			} else
				result = EMPTY_JSON;
		}
		return result;
	}

	/**
	 * 通过JAVA对象转换成JSON字符串
	 * 
	 * @param obj
	 *            JAVA对象:bean,map,list
	 * @return JSON字符串
	 */
	public static String parseObject2JSON(Object obj) {
		return toJson(obj, null, true, null, null, false);
	}

	private static boolean isJSONArray(String strJSON) {
		return strJSON.matches("^\\s*\\[.*");
	}

	private static boolean isJSONObject(String strJSON) {
		return strJSON.matches("^\\s*\\{.*");
	}

}
