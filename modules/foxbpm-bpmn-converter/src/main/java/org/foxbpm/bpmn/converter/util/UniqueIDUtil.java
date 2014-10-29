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
package org.foxbpm.bpmn.converter.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * 唯一ID生成工具类
 * 
 * @author yangguangftlp
 * @date 2014年10月18日
 */
public class UniqueIDUtil {
	/**
	 * 实例
	 */
	private static UniqueIDUtil instance;
	/**
	 * 唯一数字
	 */
	private static final AtomicInteger uniqueId = new AtomicInteger(10);
	public UniqueIDUtil() {
	}
	
	public static UniqueIDUtil getInstance() {
		if (null == instance) {
			synchronized (UniqueIDUtil.class) {
				if (null == instance) {
					instance = new UniqueIDUtil();
				}
			}
		}
		return instance;
	}
	/**
	 * 生成xml节点ID
	 * 
	 * @param prefix
	 *            前缀
	 * @return 返回生成的ID
	 */
	public String generateElementID(String prefix) {
		synchronized (this) {
			long timeMillis = System.currentTimeMillis() + uniqueId.incrementAndGet();
			if (null != prefix) {
				return new StringBuffer().append(prefix).append('_').append(timeMillis).toString();
			}
			return String.valueOf(timeMillis);
		}
	}
}
