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
 * @author yangguangftlp
 */
package org.foxbpm.engine.config;

import org.foxbpm.engine.datavariable.DataObjectBehavior;

/**
 * 数据变量配置实现类
 * 
 * @author yangguangftlp
 * @date 2014年10月27日
 */
public class DataObjectBehaviorImpl implements DataObjectBehavior {
	
	protected String id;
	protected String name;
	protected String behavior;
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setBehavior(String behavior) {
		this.behavior = behavior;
	}
	
	public String getId() {
		return null;
	}
	
	public String getName() {
		return null;
	}
	
	public String getBehavior() {
		return null;
	}
	
}
