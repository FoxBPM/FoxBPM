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
package org.foxbpm.test.engine.manage;

import java.util.Date;

import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.engine.impl.util.GuidUtil;

public class TokenManageTest extends AbstractFoxBpmManageTestCase {

	public void testSave(){
		
		Date date = new Date();
		TokenEntity tokenEntity = new TokenEntity();
		String id = GuidUtil.CreateGuid();
		tokenEntity.setId(id);
		tokenEntity.setName("name");
		tokenEntity.setProcessInstanceId("processInstanceId");
		tokenEntity.setNodeId("nodeId");
		tokenEntity.setParentId("parentId");
		tokenEntity.setStartTime(date);
		tokenEntity.setEndTime(date);
		tokenEntity.setNodeEnterTime(date);
		tokenEntity.setArchiveTime(date);
		tokenEntity.setLocked(true);
		tokenEntity.setSuspended(true);
		tokenEntity.setActive(true);
		tokenEntity.setSubProcessRootToken(true);
		commandContext.getTokenManager().insert(tokenEntity);
		
		commandContext.flushSession();
		
		TokenEntity token = commandContext.getTokenManager().selectById(TokenEntity.class, id);
		System.out.println(token.isLocked());
		
	}
}
