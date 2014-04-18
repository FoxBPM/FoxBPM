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
package org.foxbpm.engine.impl.db;

import java.sql.Connection;

import org.foxbpm.engine.db.ConnectionManagement;
import org.foxbpm.engine.db.FoxConnectionAdapter;
import org.foxbpm.engine.impl.Context;

public class GeneralConnectionManagement extends ConnectionManagement {

	public FoxConnectionAdapter getFoxConnectionAdapter() {
		String defaultId=ConnectionManagement.DAFAULT_DATABASE_ID;
		return getFoxConnectionAdapter(defaultId);
	}

	public FoxConnectionAdapter getFoxConnectionAdapter(String dbId) {
		FoxConnectionAdapter fixConnectionResult=new GeneralConnectionAdapter(dbId);
		return fixConnectionResult;
	}

	public void setFoxConnection(String dbId, Connection connection) {
		FoxConnectionAdapter fixConnectionResult=new GeneralConnectionAdapter(dbId,connection);
	}
}
