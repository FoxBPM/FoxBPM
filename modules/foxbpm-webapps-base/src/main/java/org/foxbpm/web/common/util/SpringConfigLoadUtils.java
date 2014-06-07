/**
 *  Copyright 1996-2013 Founder International Co.,Ltd.
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
 * @author shao
 */
package org.foxbpm.web.common.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring 容器工具类
 * 
 * @author MEL
 * @date 2014-06-06
 */
public class SpringConfigLoadUtils {
	private static Object object = new Object();
	private static ApplicationContext _wac = null;
	private static ApplicationContext context;
	static {

	}

	public static ApplicationContext getLocalContext() {
		if (context == null) {
			String[] sources = { "applicationContext*.xml" };
			context = new ClassPathXmlApplicationContext(sources);
		}
		return context;
	}

	public static boolean isCreated() {
		return (_wac != null || context != null);
	}

	/**
	 * 加载spring配置信息
	 * 
	 * @param wac
	 */
	public static void buildApplicationContext(ApplicationContext wac) {
		if (_wac == null) {
			synchronized (object) {
				if (_wac == null) {
					_wac = wac;
				}
			}
		}
	}

	/**
	 * 根据Class从容器中查询bean名称
	 * 
	 * @param clazz
	 * @return bean名称
	 */
	public static String[] getBeanId(Class clazz) {
		return getContext().getBeanNamesForType(clazz);
	}

	/**
	 * 获得spring容器
	 * 
	 * @return spring容器
	 */
	public ApplicationContext getApplicationContext() {
		return _wac;
	}

	/**
	 * 根据名称从容器中获得实例
	 * 
	 * @param name
	 * @return 实例
	 */
	public static Object getBean(String name) {
		if (_wac == null)
			return getLocalContext().getBean(name);
		return _wac.getBean(name);
	}

	public static ApplicationContext getContext() {
		if (_wac == null)
			return getLocalContext();
		return _wac;
	}
}