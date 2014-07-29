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
package org.foxbpm.engine.impl.datavariable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 业务数据对象模型
 * 
 * @author yangguangftlp
 * @date 2014年7月28日
 */
public class BizDataObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/** 表名称 */
	protected String id;
	
	protected String name;
	/** 描述 */
	protected String documentation;
	/** 数据源id */
	protected String dataSource;
	
	protected List<DataVariableDefinition> dataVariableDefinitions = new ArrayList<DataVariableDefinition>();
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getDocumentation() {
		return documentation;
	}
	
	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}
	
	public String getDataSource() {
		return dataSource;
	}
	
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<DataVariableDefinition> getDataVariableDefinitions() {
		return dataVariableDefinitions;
	}
	
	public void setDataVariableDefinitions(List<DataVariableDefinition> dataVariableDefinitions) {
		this.dataVariableDefinitions = dataVariableDefinitions;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> getPersistentState() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("id", id);
		result.put("documentation", documentation);
		result.put("dataSource", dataSource);
		result.put("name", name);
		List<Map> columnDataList = new ArrayList<Map>();
		for (DataVariableDefinition dataEntity : dataVariableDefinitions) {
			columnDataList.add(dataEntity.getPersistentState());
		}
		result.put("dataVariableDefinitions", columnDataList);
		return result;
	}
}
