/**
 * Copyright 1996-2014 FoxBPM Co.,Ltd.
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
package org.foxbpm.engine.runtime;

import java.util.Date;
import java.util.Map;

public interface ProcessInstance {

	/**
	 * 获取流程实例 编号
	 * @return
	 */
	String getId();
	
	/**
	 * 判断实例是否结束
	 * @return
	 */
	boolean isEnd();

	/**
	 * 获取流程定义编号
	 * @return
	 */
	String getProcessDefinitionId();
	
	/**
	 * 获取流程定义名称
	 * @return
	 */
	String getProcessDefinitionName();
	
	/**
	 * 获取流程主题
	 * @return
	 */
	String getSubject();
	
	/**
	 * 获取流程定义key
	 * @return
	 */
	String getProcessDefinitionKey();
	
	/**
	 * 获取流程启动人
	 * 正常人工启动时此属性和initator一致
	 * 定时启动任务，此属性可能不一致
	 * @return
	 */
	String getStartAuthor();
	
	/**
	 * 获取流程的提交人
	 * @return
	 */
	String getInitiator();
	
	/**
	 * 获取流程开始时间
	 * @return
	 */
	Date getStartTime();
	
	/**
	 * 获取流程结束时间
	 * @return
	 */
	Date getEndTime();
	
	/**
	 * 获取流程上次更新时间
	 * @return
	 */
	Date getUpdateTime();
	
	/**
	 * 获取流程状态
	 * 详见ProcessInstanceStatus
	 * running，suspend，termination，complete
	 * @return
	 */
	String getInstanceStatus();
	
	/**
	 * 获取流程实例当前所在位置，如果多个节点，则以逗号分隔
	 * @return
	 */
	String getProcessLocation();
	
	/**
	 * 判断流程是否暂停
	 * @return
	 */
	boolean isSuspended();
	
	/**
	 * 获取流程关联键
	 * @return
	 */
	String getBizKey();
	
	/**
	 * 获取实体属性map
	 * @return
	 */
	Map<String, Object> getPersistentState();
}
