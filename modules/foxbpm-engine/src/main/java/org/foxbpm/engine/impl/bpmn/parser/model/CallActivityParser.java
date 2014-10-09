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
package org.foxbpm.engine.impl.bpmn.parser.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.CallActivity;
import org.foxbpm.engine.impl.bpmn.behavior.BaseElementBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.CallActivityBehavior;
import org.foxbpm.engine.impl.util.BpmnModelUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.model.bpmn.foxbpm.DataSourceToSubProcessMapping;
import org.foxbpm.model.bpmn.foxbpm.FoxBPMPackage;
import org.foxbpm.model.bpmn.foxbpm.SubProcessToDataSourceMapping;

public class CallActivityParser extends TaskParser {
	 
	public BaseElementBehavior parser(BaseElement baseElement) {
		
		CallActivity callActivity=(CallActivity)baseElement;
		
		boolean isAsync = StringUtil.getBoolean(callActivity.eGet(FoxBPMPackage.Literals.DOCUMENT_ROOT__IS_ASYNC));
		
		String callableElementId = StringUtil.getString(callActivity.eGet(FoxBPMPackage.Literals.DOCUMENT_ROOT__CALLABLE_ELEMENT_ID));
		
		String callableElementVersion = StringUtil.getString(callActivity.eGet(FoxBPMPackage.Literals.DOCUMENT_ROOT__CALLABLE_ELEMENT_VERSION));
		
		String callableElementBizKey = StringUtil.getString(callActivity.eGet(FoxBPMPackage.Literals.DOCUMENT_ROOT__CALLABLE_ELEMENT_BIZ_KEY));
		
		DataSourceToSubProcessMapping dataSourceToSubProcessMapping=BpmnModelUtil.getExtensionElementOne(DataSourceToSubProcessMapping.class,callActivity,FoxBPMPackage.Literals.DOCUMENT_ROOT__DATA_SOURCE_TO_SUB_PROCESS_MAPPING);
		
		SubProcessToDataSourceMapping subProcessToDataSourceMapping=BpmnModelUtil.getExtensionElementOne(SubProcessToDataSourceMapping.class,callActivity,FoxBPMPackage.Literals.DOCUMENT_ROOT__SUB_PROCESS_TO_DATA_SOURCE_MAPPING);
		
		
		
		CallActivityBehavior callActivityBehavior=(CallActivityBehavior)baseElementBehavior;
		
		callActivityBehavior.setAsync(isAsync);
		
		callActivityBehavior.setCallableElementBizKey(callableElementBizKey);
		
		callActivityBehavior.setCallableElementId(callableElementId);
		
		callActivityBehavior.setCallableElementVersion(callableElementVersion);
		
		org.foxbpm.engine.impl.bpmn.behavior.DataSourceToSubProcessMapping dataSourceToSubProcessMappingNew=new org.foxbpm.engine.impl.bpmn.behavior.DataSourceToSubProcessMapping();
		
		org.foxbpm.engine.impl.bpmn.behavior.SubProcessToDataSourceMapping subProcessToDataSourceMappingNew=new org.foxbpm.engine.impl.bpmn.behavior.SubProcessToDataSourceMapping();
		
		
		List<org.foxbpm.engine.impl.bpmn.behavior.DataVariableMapping> dataVariableMappingsOne=new ArrayList<org.foxbpm.engine.impl.bpmn.behavior.DataVariableMapping>();
		
		List<org.foxbpm.engine.impl.bpmn.behavior.DataVariableMapping> dataVariableMappingsTwo=new ArrayList<org.foxbpm.engine.impl.bpmn.behavior.DataVariableMapping>();
		
		if(dataSourceToSubProcessMapping!=null){
			List<org.foxbpm.model.bpmn.foxbpm.DataVariableMapping> dataVariableMappings = dataSourceToSubProcessMapping.getDataVariableMapping();
			if(dataVariableMappings!=null){
				for (org.foxbpm.model.bpmn.foxbpm.DataVariableMapping dataVariableMapping : dataVariableMappings) {
					org.foxbpm.engine.impl.bpmn.behavior.DataVariableMapping dataVariableMappingNew=new org.foxbpm.engine.impl.bpmn.behavior.DataVariableMapping();
					dataVariableMappingNew.setDataSourceId(dataVariableMapping.getDataSourceId());
					dataVariableMappingNew.setSubProcesId(dataVariableMapping.getSubProcesId());
					dataVariableMappingsOne.add(dataVariableMappingNew);
				}
			}
		}
		
		if(subProcessToDataSourceMapping!=null){
			List<org.foxbpm.model.bpmn.foxbpm.DataVariableMapping> dataVariableMappings = subProcessToDataSourceMapping.getDataVariableMapping();
			if(dataVariableMappings!=null){
				for (org.foxbpm.model.bpmn.foxbpm.DataVariableMapping dataVariableMapping : dataVariableMappings) {
					org.foxbpm.engine.impl.bpmn.behavior.DataVariableMapping dataVariableMappingNew=new org.foxbpm.engine.impl.bpmn.behavior.DataVariableMapping();
					dataVariableMappingNew.setDataSourceId(dataVariableMapping.getDataSourceId());
					dataVariableMappingNew.setSubProcesId(dataVariableMapping.getSubProcesId());
					dataVariableMappingsTwo.add(dataVariableMappingNew);
				}
			}
		}
		dataSourceToSubProcessMappingNew.setDataVariableMappings(dataVariableMappingsOne);
		subProcessToDataSourceMappingNew.setDataVariableMappings(dataVariableMappingsTwo);
		
		callActivityBehavior.setDataSourceToSubProcessMapping(dataSourceToSubProcessMappingNew);
		callActivityBehavior.setSubProcessToDataSourceMapping(subProcessToDataSourceMappingNew);
		
		return super.parser(baseElement);
	}
	
	 
	public void init() {
		baseElementBehavior=new CallActivityBehavior();
	}
	
}
