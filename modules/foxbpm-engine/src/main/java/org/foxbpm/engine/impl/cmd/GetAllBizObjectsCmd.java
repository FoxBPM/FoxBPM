package org.foxbpm.engine.impl.cmd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.foxbpm.engine.datavariable.BizDataObjectBehavior;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.ProcessEngineConfigurationImpl;
import org.foxbpm.engine.impl.datavariable.BizDataObject;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.util.ReflectUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.model.config.foxbpmconfig.BizDataObjectConfig;
import org.foxbpm.model.config.foxbpmconfig.DataObjectBehavior;

public class GetAllBizObjectsCmd implements Command<List<Map<String,Object>>> {

	@Override
	public List<Map<String, Object>> execute(CommandContext commandContext) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		ProcessEngineConfigurationImpl processEngine = commandContext.getProcessEngineConfigurationImpl();
		BizDataObjectConfig bizDataObjectConfig = processEngine.getBizDataObjectConfig();
		if (null == bizDataObjectConfig) {
			throw new FoxBPMException("获取业务数据对象配置为空!");
		}
		EList<DataObjectBehavior> dataObjBehaviorList = bizDataObjectConfig.getDataObjectBehavior();
	
		if(dataObjBehaviorList == null){
			return Collections.emptyList();
		}
		
		for(DataObjectBehavior dataObjectBehavior : dataObjBehaviorList){
			Map<String,Object> tmpResult = new HashMap<String, Object>();
			String id = dataObjectBehavior.getId();
			String name = dataObjectBehavior.getName();
			tmpResult.put("id", id);
			tmpResult.put("name", name);
			
			// 实例化业务数据对象行为处理类
			BizDataObjectBehavior bizDataObjectBehavior = (BizDataObjectBehavior) ReflectUtil.instantiate(StringUtil.trim(dataObjectBehavior.getBehavior()));
			//这里暂时不做多数据源问题，所以dataSource参数为null
			List<BizDataObject> bizObjects = bizDataObjectBehavior.getDataObjects(null);
			tmpResult.put("data", bizObjects);
			result.add(tmpResult);
		}
		
		return result;
	}
}
