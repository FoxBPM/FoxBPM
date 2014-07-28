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
package org.foxbpm.engine.impl.cmd;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.foxbpm.engine.datavariable.BizDataObjectBehavior;
import org.foxbpm.engine.exception.FoxBPMBizException;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.ProcessEngineConfigurationImpl;
import org.foxbpm.engine.impl.datavariable.BizDataObject;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.util.ReflectUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.model.config.foxbpmconfig.BizDataObjectConfig;
import org.foxbpm.model.config.foxbpmconfig.DataObjectBehavior;

/**
 * 获取业务数据对象命令类
 * 
 * @author yangguangftlp
 * @date 2014年7月26日
 */
public class GetBizDataObjectCmd implements Command<List<BizDataObject>> {
	
	protected String behaviorId;
	protected String dataSource;
	
	public GetBizDataObjectCmd(String behaviorId, String dataSource) {
		this.behaviorId = behaviorId;
		this.dataSource = dataSource;
	}
	
	@Override
	public List<BizDataObject> execute(CommandContext commandContext) {
		
		if (StringUtil.isEmpty(behaviorId)) {
			throw new FoxBPMBizException("type is null!");
		}
		if (StringUtil.isEmpty(dataSource)) {
			throw new FoxBPMBizException("dataSource is null!");
		}
		ProcessEngineConfigurationImpl processEngine = commandContext.getProcessEngineConfigurationImpl();
		BizDataObjectConfig bizDataObjectConfig = processEngine.getBizDataObjectConfig();
		if (null == bizDataObjectConfig) {
			throw new FoxBPMException("获取业务数据对象配置为空!");
		}
		EList<DataObjectBehavior> dataObjBehaviorList = bizDataObjectConfig.getDataObjectBehavior();
		if (null == dataObjBehaviorList) {
			throw new FoxBPMException("获取数据对象行为为空!");
		}
		
		DataObjectBehavior dataObjectBehavior = null;
		for (Iterator<DataObjectBehavior> iterator = dataObjBehaviorList.iterator(); iterator.hasNext();) {
			dataObjectBehavior = (DataObjectBehavior) iterator.next();
			if (behaviorId.equals(dataObjectBehavior.getId())) {
				break;
			}
		}
		
		// 判断是否存在type对应的数据处理行为
		if (null == dataObjectBehavior) {
			throw new FoxBPMException("获取 behaviorId=" + behaviorId + "对应数据对象行为为空!");
		}
		
		// 实例化业务数据对象行为处理类
		BizDataObjectBehavior bizDataObjectBehavior = (BizDataObjectBehavior) ReflectUtil.instantiate(StringUtil.trim(dataObjectBehavior.getBehavior()));
		return bizDataObjectBehavior.getDataObjects(dataSource);
	}
}
