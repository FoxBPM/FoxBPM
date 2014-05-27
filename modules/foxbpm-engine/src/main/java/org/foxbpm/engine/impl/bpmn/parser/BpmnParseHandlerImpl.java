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
 */
package org.foxbpm.engine.impl.bpmn.parser;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.RootElement;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.bpmn2.di.BpmnDiPackage;
import org.eclipse.bpmn2.util.Bpmn2ResourceFactoryImpl;
import org.eclipse.dd.dc.DcPackage;
import org.eclipse.dd.di.DiPackage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.exception.ExceptionCode;
import org.foxbpm.engine.exception.FoxBPMClassLoadingException;
import org.foxbpm.engine.impl.ProcessDefinitionEntityBuilder;
import org.foxbpm.engine.impl.bpmn.behavior.BaseElementBehavior;
import org.foxbpm.engine.impl.connector.Connector;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.util.BpmnModelUtil;
import org.foxbpm.engine.impl.util.ReflectUtil;
import org.foxbpm.engine.modelparse.ProcessModelParseHandler;
import org.foxbpm.kernel.ProcessDefinitionBuilder;
import org.foxbpm.kernel.behavior.KernelFlowNodeBehavior;
import org.foxbpm.kernel.process.KernelProcessDefinition;
import org.foxbpm.model.bpmn.foxbpm.FoxBPMPackage;

public class BpmnParseHandlerImpl implements ProcessModelParseHandler {

	public KernelProcessDefinition createProcessDefinition(String processId, Object processFile) {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("process_test222.bpmn");
		Process process = null;
		if (processFile != null) {
			process = createProcess(processId, (InputStream)processFile);

		}
		KernelProcessDefinition processDefinition=loadBehavior(process);
		// 加载事件定义.
		loadConnector(processDefinition);
//		// 加载数据变量
//		loadVariable(processDefinition);
//		// 设置FlowNode元素的子流程
//		loadSubProcess(processDefinition);
//		processDefinition.persistentInit(processDataMap);
//		deploymentCache.addProcessDefinition(processDefinition);
		return processDefinition;
	}
	
	
	private void loadConnector(KernelProcessDefinition processDefinition) {
		// TODO Auto-generated method stub
		
	}


	private KernelProcessDefinition loadBehavior(Process process){
		String processObjId = BpmnModelUtil.getProcessId(process);
		String category=BpmnModelUtil.getProcessCategory(process);
		List<FlowElement> flowElements=process.getFlowElements();
		ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionEntityBuilder(processObjId);
		for(FlowElement flowElement :flowElements){
			KernelFlowNodeBehavior flowNodeBehavior = BpmnBehaviorEMFConverter.getFlowNodeBehavior(flowElement);
			if(flowElement instanceof FlowNode){
				processDefinitionBuilder.createFlowNode(flowElement.getId()).behavior(flowNodeBehavior);
				
				if(flowNodeBehavior instanceof BaseElementBehavior){
					for (Connector connector :((BaseElementBehavior) flowNodeBehavior).getConnectors()) {
						processDefinitionBuilder.executionListener(connector.getEventType(), connector);
					}
				}

				if(flowElement instanceof StartEvent){
					processDefinitionBuilder.initial();
				}
				List<SequenceFlow>  sequenceFlows=((FlowNode) flowElement).getOutgoing();
				for (SequenceFlow sequenceFlow : sequenceFlows) {
					processDefinitionBuilder.sequenceFlow(sequenceFlow.getTargetRef().getId());
					
				}
				processDefinitionBuilder.endFlowNode();
			}
		}
		
		ProcessDefinitionEntity processDefinition=(ProcessDefinitionEntity)processDefinitionBuilder.buildProcessDefinition();
		processDefinition.setKey(process.getId());
		processDefinition.setName(process.getName());
		processDefinition.setCategory(category);
		return processDefinition;
	}
	

	private Process createProcess(String processId, InputStream is) {

		ResourceSet resourceSet = getResourceSet();
		String fixflowFilePath = ProcessEngineManagement.getDefaultProcessEngine().getProcessEngineConfiguration().getNoneTemplateFilePath();
		URL url = ReflectUtil.getResource(fixflowFilePath);
		if (url == null) {
			throw new FoxBPMClassLoadingException(ExceptionCode.CLASSLOAD_EXCEPTION_FILENOTFOUND, fixflowFilePath);
		}
		String filePath = url.toString();
		Resource ddddResource = null;
		try {
			if (!filePath.startsWith("jar")) {
				filePath = java.net.URLDecoder.decode(url.getFile(), "utf-8");
				ddddResource = resourceSet.createResource(URI.createFileURI(filePath));
			} else {
				ddddResource = resourceSet.createResource(URI.createURI(filePath));
			}
			ddddResource.load(is, null);
		} catch (Exception e) {
			throw new FoxBPMClassLoadingException(ExceptionCode.CLASSLOAD_EXCEPTION, e);
		}
		Definitions definitions = (Definitions) ddddResource.getContents().get(0).eContents().get(0);
		for (RootElement rootElement : definitions.getRootElements()) {
			if (rootElement instanceof Process) {
				Process processObj = (Process) rootElement;

				

//				if (processObjId.equals(processId)) {
					processObj = (Process) rootElement;
					return processObj;
//				}
			}
		}
		return null;

	}

	private ResourceSet getResourceSet() {
		ResourceSet resourceSet = new ResourceSetImpl();
		(EPackage.Registry.INSTANCE).put("http://www.omg.org/spec/BPMN/20100524/MODEL", Bpmn2Package.eINSTANCE);
		(EPackage.Registry.INSTANCE).put("http://www.foxbpm.org/foxbpm", FoxBPMPackage.eINSTANCE);
		(EPackage.Registry.INSTANCE).put("http://www.omg.org/spec/DD/20100524/DI", DiPackage.eINSTANCE);
		(EPackage.Registry.INSTANCE).put("http://www.omg.org/spec/DD/20100524/DC", DcPackage.eINSTANCE);
		(EPackage.Registry.INSTANCE).put("http://www.omg.org/spec/BPMN/20100524/DI", BpmnDiPackage.eINSTANCE);
		FoxBPMPackage.eINSTANCE.eClass();
		FoxBPMPackage xxxPackage = FoxBPMPackage.eINSTANCE;
		EPackage.Registry.INSTANCE.put(xxxPackage.getNsURI(), xxxPackage);
		Bpmn2ResourceFactoryImpl ddd = new Bpmn2ResourceFactoryImpl();
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("foxbpm", ddd);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("bpmn", ddd);
		resourceSet.getPackageRegistry().put(xxxPackage.getNsURI(), xxxPackage);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("bpmn", ddd);
		return resourceSet;
	}

}
