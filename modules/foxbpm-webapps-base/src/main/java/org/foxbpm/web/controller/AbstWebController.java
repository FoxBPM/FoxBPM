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
package org.foxbpm.web.controller;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.foxbpm.web.common.constant.FoxbpmViewNameDefinition;
import org.foxbpm.web.common.constant.FoxbpmWebContextAttributeNameDefinition;
import org.foxbpm.web.common.exception.FoxbpmWebException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 抽象控制器
 * 
 * @author yangguangftlp
 * @date 2014年6月11日
 */
public abstract class AbstWebController {

	@ExceptionHandler
	public String exp(HttpServletRequest request, Exception ex) {
		request.setAttribute("ex", ex);
		return getPrefix() + FoxbpmViewNameDefinition.ERROR_VIEWNAME;
	}

	/**
	 * 根据视图名称创建 modelAndView 视图对象
	 * 
	 * @param viewName
	 *            视图名称
	 * @return 返回视图对象
	 */
	public ModelAndView createModelAndView(String viewName) {
		return new ModelAndView(getPrefix() + viewName);
	}

	/**
	 * 获取控制对象视图前缀 例如:taskCenter/xxxx 子类需要根据业务情况重写该类
	 * 
	 * @return 返回视图前缀
	 */
	protected abstract String getPrefix();

	/**
	 * http request 请求参数获取
	 * 
	 * @param request
	 *            http 请求
	 * @return 返回获取的http请求参数
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Object> getRequestParams(HttpServletRequest request) {
		// 请求参数
		Map<String, Object> requestParams = new HashMap<String, Object>();
		requestParams.putAll(request.getParameterMap());
		try {
			Enumeration<String> enumeration = null;
			if (ServletFileUpload.isMultipartContent(request)) {
				ServletFileUpload Uploader = new ServletFileUpload(new DiskFileItemFactory());
				Uploader.setHeaderEncoding("utf-8");
				List<FileItem> fileItems = Uploader.parseRequest(request);
				for (FileItem item : fileItems) {
					requestParams.put(item.getFieldName(), item);
					if (FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_DEPLOYMENTID.equals(item.getFieldName())) {
						requestParams.put(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_DEPLOYMENTID, item.getString());
					}
				}
			} else {
				// 获取parmeter中参数
				enumeration = request.getParameterNames();
				if (null != enumeration) {
					String key = null;
					String value = null;
					while (enumeration.hasMoreElements()) {
						key = enumeration.nextElement();
						value = request.getParameter(key);
						requestParams.put(key, new String(value.getBytes("ISO8859-1"), "utf-8"));
					}
				}
			}
			// 获取attribute中参数
			enumeration = request.getAttributeNames();
			if (null != enumeration) {
				String key = null;
				while (enumeration.hasMoreElements()) {
					key = enumeration.nextElement();
					requestParams.put(key, request.getAttribute(key));
				}
			}
		} catch (Exception e) {
			throw new FoxbpmWebException(e);
		}
		return requestParams;
	}

	/**
	 * 响应客户端
	 * 
	 * @param response
	 *            响应
	 * @param in
	 *            将流内容响应给客户端
	 */
	public void doResponse(HttpServletResponse response, InputStream in) {
		try {
			ServletOutputStream out = response.getOutputStream();
			response.setContentType("application/octet-stream;charset=UTF-8");
			byte[] buff = new byte[2048];
			int size = 0;
			while (in != null && (size = in.read(buff)) != -1) {
				out.write(buff, 0, size);
			}
			out.flush();
		} catch (Exception e) {
			throw new FoxbpmWebException(e);
		}
	}

	/**
	 * 响应客户端
	 * 
	 * @param response
	 *            响应
	 * @param in
	 *            将流内容响应给客户端
	 */
	public void doResponse(HttpServletResponse response, String content) {
		try {
			PrintWriter out = response.getWriter(); 
			response.setContentType("text/html;charset=UTF-8");
			out.print(content);
			out.flush();
		} catch (Exception e) {
			throw new FoxbpmWebException(e);
		}
	}
}
