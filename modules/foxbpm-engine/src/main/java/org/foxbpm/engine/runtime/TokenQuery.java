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
 * @author ych
 */
package org.foxbpm.engine.runtime;

import org.foxbpm.engine.query.Query;

/**
 * 流程令牌查询
 * @author kenshin
 */
public interface TokenQuery extends Query<TokenQuery, Token>{
	
	/**
	 * 根据流程实例ID查询
	 * @param processInstanceId
	 * @return
	 */
	TokenQuery processInstanceId(String processInstanceId);
	
	/**
	 * 根据令牌ID查询
	 * @param tokenId
	 * @return
	 */
	TokenQuery tokenId(String tokenId);
	
	/**
	 * 查询已结束的令牌
	 * @return
	 */
	TokenQuery tokenIsEnd();
	
	/**
	 * 查询未结束的令牌
	 * @return
	 */
	TokenQuery tokenNotEnd();
	
	/**
	 * 根据流程实例ID排序
	 * @return
	 */
	TokenQuery orderByProcessInstanceId();

	/**
	 * 根据令牌ID排序
	 * @return
	 */
	TokenQuery orderByTokenId();
}
