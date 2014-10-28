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
package org.foxbpm.engine.impl.cmd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.config.BizDataObjectConfig;
import org.foxbpm.engine.datavariable.BizDataObjectBehavior;
import org.foxbpm.engine.datavariable.DataObjectDefinition;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.ProcessEngineConfigurationImpl;
import org.foxbpm.engine.impl.datavariable.BizDataObject;
import org.foxbpm.engine.impl.datavariable.DataObjectDefinitionImpl;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.util.ReflectUtil;
import org.foxbpm.engine.impl.util.StringUtil;

/**
 * 获取所有的bizDataObject的map,供设计器缓存数据对象使用
 * @author ych
 *
 */
public class GetAllBizObjectsCmd implements Command<List<Map<String,Object>>> {

	 
	public List<Map<String, Object>> execute(CommandContext commandContext) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		ProcessEngineConfigurationImpl processEngine = commandContext.getProcessEngineConfigurationImpl();
		BizDataObjectConfig bizDataObjectConfig = processEngine.getBizDataObjectConfig();
		if (null == bizDataObjectConfig) {
			throw new FoxBPMException("获取业务数据对象配置为空!");
		}
		List<DataObjectDefinitionImpl> dataObjBehaviorList = bizDataObjectConfig.getDataObjectDefinitions();
	
		if(dataObjBehaviorList == null){
			return Collections.emptyList();
		}
		
		for(DataObjectDefinition dataObjectBehavior : dataObjBehaviorList){
			Map<String,Object> tmpResult = new HashMap<String, Object>();
			String id = dataObjectBehavior.getId();
			String name = dataObjectBehavior.getName();
			tmpResult.put("id", id);
			tmpResult.put("name", name);
			
			// 实例化业务数据对象行为处理类
			BizDataObjectBehavior bizDataObjectBehavior = (BizDataObjectBehavior) ReflectUtil.instantiate(StringUtil.trim(dataObjectBehavior.getBehavior()));
			//这里暂时不做多数据源问题，所以dataSource参数为null
			List<BizDataObject> bizObjects = bizDataObjectBehavior.getDataObjects(null);
			
			List<Map<String,Object>> tmpBizMap = new ArrayList<Map<String,Object>>();
			if(!bizObjects.isEmpty()){
				for(BizDataObject bizObject : bizObjects)
				tmpBizMap.add(bizObject.getPersistentState());
			}
			tmpResult.put("data", tmpBizMap);
			result.add(tmpResult);
		}
		
		return result;
	}
}
