/**
 * 
 */
package org.foxbpm.web.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * web控制器抽象类
 * 
 * @author yangguangftlp
 * 
 */
public abstract class AbstractWebappController {
	/**
	 * http request 请求参数获取
	 * 
	 * @param request
	 *            http 请求
	 * @return 返回获取的http请求参数
	 */
	protected Map<String, Object> getRequestParams(HttpServletRequest request) {

		// 请求参数
		Map<String, Object> requestParams = new HashMap<String, Object>();

		requestParams.putAll(request.getParameterMap());
		Enumeration<String> enumeration = request.getParameterNames();
		if (null != enumeration) {
			String key = null;
			while (enumeration.hasMoreElements()) {
				key = enumeration.nextElement();
				requestParams.put(key, request.getParameter(key));
			}
		}
		enumeration = request.getAttributeNames();
		if (null != enumeration) {
			String key = null;
			while (enumeration.hasMoreElements()) {
				key = enumeration.nextElement();
				requestParams.put(key, request.getAttribute(key));
			}
		}
		return requestParams;
	}
}
