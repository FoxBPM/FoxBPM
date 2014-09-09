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
 * @author demornain
 */
package org.foxbpm.social.impl.mybatis;

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.impl.mybatis.FoxbpmMapperConfig;

public class SocialMapperConfigImpl implements FoxbpmMapperConfig {

	@Override
	public List<String> getMapperConfig() {
		List<String> xmlList = new ArrayList<String>();
		xmlList.add("mybatis/mapping/CalendarType.xml");
		xmlList.add("mybatis/mapping/CalendarRule.xml");
		xmlList.add("mybatis/mapping/CalendarPart.xml");
		return xmlList;
	}

}
