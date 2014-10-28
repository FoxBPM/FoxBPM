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
 * @author ych
 */
package org.foxbpm.engine.event;

/**
 * 引擎监听器配置
 * 见foxbpm.cfg.xml中的eventListenerConfig配置
 * @author ych
 *
 */
public interface EventListener {
	
	String getId();
	/**
	 * 监听的事件类型
	 * @return
	 */
	String getEventType();
	
	/**
	 * 监听器执行类
	 * @return
	 */
	String getListenerClass();
	
}
