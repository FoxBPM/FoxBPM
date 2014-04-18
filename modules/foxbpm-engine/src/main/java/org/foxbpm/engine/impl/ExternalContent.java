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

/**
 * 外部内容构造器
 * @author kenshin
 *
 */
public class ExternalContent {
	
	protected String authenticatedUserId;
	protected String languageType;
	protected boolean isQuartzTransactionAuto=true;
	
	/**
	 * 获取当前登陆用户
	 * @return
	 */
	public String getAuthenticatedUserId() {
		return authenticatedUserId;
	}

	/**
	 * 设置当前登陆用户
	 * @param authenticatedUserId 用户编号
	 */
	public void setAuthenticatedUserId(String authenticatedUserId) {
		this.authenticatedUserId = authenticatedUserId;
	}

	/**
	 * 获取语言类型
	 * @return
	 */
	public String getLanguageType() {
		return languageType;
	}

	/**
	 * 设置语言类型
	 * @param languageType 语言类型
	 */
	public void setLanguageType(String languageType) {
		this.languageType = languageType;
	}

	/**
	 * 获取定时任务框架的事务控制类型
	 * @return
	 */
	public boolean isQuartzTransactionAuto() {
		return isQuartzTransactionAuto;
	}

}
