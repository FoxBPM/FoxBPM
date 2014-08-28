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
 * @author ych
 */
package org.foxbpm.rest.common;

/**
 * foxbpm rest项目中参数常量
 * @author ych
 *
 */
public interface RestConstants {
	
	final static String USERID = "userId";

	final static String PROCESS_KEY = "key";
	final static String PROCESS_ID = "processDefinitionId";
	final static String TASK_ID = "taskId";
	final static String PROCESSINSTANCE_ID = "processInstanceId";
	final static String DEPLOY_ID = "deploymentId";
	final static String RESOUCE_NAME ="resource_name";
	final static String VERSION = "version";
	
	final static String CATEGORY = "category";
	final static String NAME = "name";
	final static String NAME_LIKE = "nameLike";
	
	final static String PAGE_INDEX ="pageIndex";
	final static String PAGE_SIZE = "pageSize";
	
	final static String IS_END = "ended";
	
	final static int DEFAULT_INDEX = 1;
	final static int DEFAULT_SIZE = 15;
	
	final static String PAGE_START ="start";
	final static String PAGE_LENGTH = "length";
	
	//是否我参与的
	final static String PARTICIPATE = "participate";
	final static String INITIATOR = "initiator";
}
