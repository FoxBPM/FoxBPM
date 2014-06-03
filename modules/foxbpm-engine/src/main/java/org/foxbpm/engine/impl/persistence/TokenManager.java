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
package org.foxbpm.engine.impl.persistence;

import java.util.List;

import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.engine.impl.runtime.TokenQueryImpl;
import org.foxbpm.engine.runtime.Token;

/**
 * 令牌管理器
 * @author kenshin
 *
 */
public class TokenManager extends AbstractManager {

	@SuppressWarnings("unchecked")
	public List<TokenEntity> findChildTokensByProcessInstanceId(String id) {
		return (List<TokenEntity>)getSqlSession().selectListWithRawParameter("selectTokensByProcessInstanceId", id);
	}

	public TokenEntity findTokenById(String rootTokenId) {
		return selectById(TokenEntity.class, rootTokenId);
	}
	
	public long findTokenCountByQueryCriteria(TokenQueryImpl tokenQuery){
		return (Long)getSqlSession().selectOne("selectTokenCountByQueryCriteria", tokenQuery);
	}
	
	@SuppressWarnings("unchecked")
	public List<Token> findTokenByQueryCriteria(TokenQueryImpl tokenQuery){
		return getSqlSession().selectList("selectTokensByQueryCriteria", tokenQuery);
	}
	
	public void deleteTokenByProcessInstanceId(String processInstanceId){
		
	}
}
