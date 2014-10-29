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
package org.foxbpm.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 流程元素
 * 
 * @author ych
 * 
 */
public abstract class FlowElement extends BaseElement {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 名称
	 */
	protected String name;
	
	/**
	 * 连接器集合
	 */
	protected List<Connector> connector = new ArrayList<Connector>();
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@SuppressWarnings("unchecked")
	public List<Connector> getConnector() {
		if (connector == null) {
			return Collections.EMPTY_LIST;
		}
		return connector;
	}
	
	public void setConnector(List<Connector> connector) {
		this.connector = connector;
	}
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [id=" + id + ", name=" + name + ']';
	}
}
