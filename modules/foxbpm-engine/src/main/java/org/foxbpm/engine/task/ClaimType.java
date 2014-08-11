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
 */
package org.foxbpm.engine.task;

/**
 * 任务领取方式
 * @author kenshin
 *
 */
public class ClaimType {
	
	/** 默认领取方式读取系统配置 */
	public static String DEFAULT_CLAIM="defaultclaim";
	/** 自动领取 */
	public static String AUTO_CLAIM="autoclaim";
	/** 手动领取 */
	public static String MANUAL_CLAIM="manualclaim";
}
