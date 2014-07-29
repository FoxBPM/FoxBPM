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
package org.foxbpm.engine.impl.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Bpmn2Factory;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.Documentation;
import org.eclipse.bpmn2.Expression;
import org.eclipse.bpmn2.ExtensionAttributeValue;
import org.eclipse.bpmn2.FormalExpression;
import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.ResourceRole;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.UserTask;
import org.eclipse.bpmn2.di.BPMNEdge;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.dd.di.DiagramElement;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.BasicEObjectImpl;
import org.eclipse.emf.ecore.impl.EStructuralFeatureImpl.SimpleFeatureMapEntry;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.foxbpm.engine.impl.bpmn.behavior.SkipStrategy;
import org.foxbpm.engine.impl.connector.Connector;
import org.foxbpm.engine.impl.task.TaskAssigneeDefinition;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.task.TaskCommand;
import org.foxbpm.model.bpmn.foxbpm.AssignPolicyType;
import org.foxbpm.model.bpmn.foxbpm.CompleteTaskDescription;
import org.foxbpm.model.bpmn.foxbpm.FormParamContainer;
import org.foxbpm.model.bpmn.foxbpm.FormUri;
import org.foxbpm.model.bpmn.foxbpm.FormUriView;
import org.foxbpm.model.bpmn.foxbpm.FoxBPMPackage;
import org.foxbpm.model.bpmn.foxbpm.PotentialStarter;
import org.foxbpm.model.bpmn.foxbpm.TaskDescription;
import org.foxbpm.model.bpmn.foxbpm.TaskPriority;
import org.foxbpm.model.bpmn.foxbpm.TaskSubject;


public class BpmnModelUtil {
	
	/** 获取流程的唯一编号*/
	public static String getProcessId(Process process){
		return StringUtil.getString(getExtensionAttribute(process,FoxBPMPackage.Literals.DOCUMENT_ROOT__DBID));
	}
	
	/** 获取任务领取方式*/
	public static String claimType(UserTask userTask){
		return StringUtil.getString(getExtensionAttribute(userTask,FoxBPMPackage.Literals.DOCUMENT_ROOT__CLAIM_TYPE));
	}
	
	/** 获取并行网关合并策略*/
	public static String convergType(ParallelGateway parallelGateway){
		return StringUtil.getString(getExtensionAttribute(parallelGateway,FoxBPMPackage.Literals.DOCUMENT_ROOT__CONVERG_TYPE));
	}
	
	/** 获取流程的分类*/
	public static String getProcessCategory(Process process){
		return StringUtil.getString(getExtensionAttribute(process,FoxBPMPackage.Literals.DOCUMENT_ROOT__CATEGORY));
	}
	
	/** 获取流程的发起人*/
	public static List<PotentialStarter> getPotentialStarters(Process process){
		
		List<PotentialStarter> extensionElementList = getExtensionElementList(PotentialStarter.class, process, FoxBPMPackage.Literals.DOCUMENT_ROOT__POTENTIAL_STARTER);
		
		return extensionElementList;
	}
	
	public List<Connector> getcConnectors(BaseElement baseElement){
		
		List<Connector> connectors=new ArrayList<Connector>();
		
		
		return connectors;
	}
	
	/**
	 * 获取开始节点是否持久化属性
	 * @param baseElement
	 * @return
	 */
	public static boolean getStartEventPersistence(BaseElement baseElement){
		return StringUtil.getBoolean(BpmnModelUtil.getExtensionAttribute(baseElement, FoxBPMPackage.Literals.DOCUMENT_ROOT__IS_PERSISTENCE));
	}
	
	/**
	 * 获取线条表达式值
	 * @param baseElement
	 * @return
	 */
	public static String getSequenceFlowCondition(BaseElement baseElement){
		SequenceFlow sequenceFlow = (SequenceFlow)baseElement;
		Expression expression = sequenceFlow.getConditionExpression();
		if(expression != null){
			FormalExpression formalExpression = (FormalExpression)expression;
			return formalExpression.getBody();
		}
		return null;
	}
	
	/**
	 * 获取人工任务主题信息
	 * @param baseElement
	 * @return
	 */
	public static String getUserTaskSubject(BaseElement baseElement){
		TaskSubject taskSubject = (TaskSubject)BpmnModelUtil.getExtensionElement(baseElement,FoxBPMPackage.Literals.DOCUMENT_ROOT__TASK_SUBJECT);
		if(taskSubject != null&&taskSubject.getExpression()!=null){
			
			
			
			return taskSubject.getExpression().getValue();
			
		}
		return null;
	}
	
	
	/**
	 * 获取人工任务表单参数
	 * @param baseElement
	 * @return
	 */
	public static FormParamContainer getFormParamContainer(BaseElement baseElement){
		FormParamContainer formParamContainer = (FormParamContainer)BpmnModelUtil.getExtensionElement(baseElement,FoxBPMPackage.Literals.DOCUMENT_ROOT__FORM_PARAM_CONTAINER);
		return formParamContainer;
	}
	
	
	
	
	/**
	 * 获取人工任务描述
	 * @param baseElement
	 * @return
	 */
	public static String getUserTaskDescription(BaseElement baseElement){
		TaskDescription taskDescription = (TaskDescription)BpmnModelUtil.getExtensionElement(baseElement,FoxBPMPackage.Literals.DOCUMENT_ROOT__TASK_DESCRIPTION);
		if(taskDescription != null&&taskDescription.getExpression()!=null){
			return taskDescription.getExpression().getValue();
		}
		return null;
	}
	
	/**
	 * 获取人工任务完成后的描述
	 * @param baseElement
	 * @return
	 */
	public static String getUserTaskCompleteTaskDescription(BaseElement baseElement){
		CompleteTaskDescription completeTaskDescription = (CompleteTaskDescription)BpmnModelUtil.getExtensionElement(baseElement,FoxBPMPackage.Literals.DOCUMENT_ROOT__COMPLETE_TASK_DESCRIPTION);
		if(completeTaskDescription != null&&completeTaskDescription.getExpression()!=null){
			return completeTaskDescription.getExpression().getValue();
		}
		return null;
	}
	
	/**
	 * 获取操作表单
	 * @param baseElement
	 * @return
	 */
	public static String getFormUri(BaseElement baseElement){
		FormUri formUri = (FormUri)BpmnModelUtil.getExtensionElement(baseElement, FoxBPMPackage.Literals.DOCUMENT_ROOT__FORM_URI);
		if(formUri != null&&formUri.getExpression()!=null){
			return formUri.getExpression().getValue();
		}
		return null;
	}
	
	/**
	 * 获取浏览表单
	 * @param baseElement
	 * @return
	 */
	public static String getFormUriView(BaseElement baseElement){
		FormUriView formUri = (FormUriView)BpmnModelUtil.getExtensionElement(baseElement, FoxBPMPackage.Literals.DOCUMENT_ROOT__FORM_URI_VIEW);
		if(formUri != null&&formUri.getExpression()!=null){
			return formUri.getExpression().getValue();
		}
		return null;
	}
	
	/**
	 * 获取任务分配策略
	 * @param baseElement
	 * @return
	 */
	public static String getUserTaskAssigneePolicyType(BaseElement baseElement){
		AssignPolicyType policyType = (AssignPolicyType)BpmnModelUtil.getExtensionElement(baseElement, FoxBPMPackage.Literals.DOCUMENT_ROOT__ASSIGN_POLICY_TYPE);
		if(policyType != null){
			return policyType.getExpression().getValue();
		}
		return null;
	}
	
	/**
	 * 获取任务分配表达式
	 * @param baseElement
	 * @return
	 */
	public static String getUserTaskAssigneeExpression(BaseElement baseElement){
		org.foxbpm.model.bpmn.foxbpm.Expression expression = (org.foxbpm.model.bpmn.foxbpm.Expression)BpmnModelUtil.getExtensionElement(baseElement, FoxBPMPackage.Literals.DOCUMENT_ROOT__ASSIGN_POLICY_TYPE);
		return getValueFormExpression(expression);
	}
	
	/**
	 * 获取任务处理者
	 * @param baseElement
	 * @return
	 */
	public static List<TaskAssigneeDefinition> getUserTaskAssignees(BaseElement baseElement){
		UserTask userTask = (UserTask)baseElement;
		List<ResourceRole> resources = userTask.getResources();
		List<TaskAssigneeDefinition> assignees = new ArrayList<TaskAssigneeDefinition>();
		if(resources != null){
			for(ResourceRole resource :resources){
				if(resource != null){
					String resourceType = StringUtil.getString(resource.eGet(FoxBPMPackage.Literals.DOCUMENT_ROOT__RESOURCE_TYPE, true));
					boolean isContainsSub = StringUtil.getBoolean(resource.eGet(FoxBPMPackage.Literals.DOCUMENT_ROOT__IS_CONTAINS_SUB, true));
					String resourceExpression = BpmnModelUtil.getExpression(resource.getResourceAssignmentExpression().getExpression());
					//String resourceName = resource.getName();
					TaskAssigneeDefinition assignee = new TaskAssigneeDefinition();
					assignee.setContainsSub(isContainsSub);
					assignee.setUserIdExpression(resourceExpression);
					assignee.setGroupIdExpression(resourceExpression);
					assignee.setGroupTypeExpression(resourceType);
					assignees.add(assignee);
				}
			}
		}
		return assignees;
	}
	
	/**
	 * 获取任务命令集合
	 * @param baseElement
	 * @return
	 */
	public static List<TaskCommand> getUserTaskCommands(BaseElement baseElement){
		List<TaskCommand> taskCommands = new ArrayList<TaskCommand>();
//		List<TaskCommandModel> taskCommandsObj  =BpmnModelUtil.getAll(TaskCommandModel.class, baseElement);
//		if(taskCommandsObj!=null){
//			for (TaskCommandModel tmpTaskCommand : taskCommandsObj) {
//				TaskCommand taskCommandDefinition = new TaskCommand();
//				taskCommandDefinition.setCommandType(tmpTaskCommand.getCommandType());
//				taskCommandDefinition.setId(tmpTaskCommand.getId());
//				taskCommandDefinition.setName(tmpTaskCommand.getName());
//				org.foxbpm.model.bpmn.foxbpm.Expression expression =  tmpTaskCommand.getExpression();
//				if(expression != null){
//					taskCommandDefinition.setExpression(expression.getValue());
//				}
//				expression =  tmpTaskCommand.getParameterExpression();
//				if(expression != null){
//					taskCommandDefinition.setParaExpression(expression.getValue());
//				}
//				taskCommands.add(taskCommandDefinition);
//			}
//		}
		return taskCommands;
	}
	
	/**
	 * 获取任务优先级
	 * @param baseElement
	 * @return
	 */
	public static String getUserTaskPriority(BaseElement baseElement){
		TaskPriority taskPriorityObj  = (TaskPriority)BpmnModelUtil.getExtensionElement(baseElement, FoxBPMPackage.Literals.DOCUMENT_ROOT__TASK_PRIORITY);
		if(taskPriorityObj != null){
			return taskPriorityObj.getExpression().getValue();
		}
		return null;
	}
	
	public static String getValueFormExpression(org.foxbpm.model.bpmn.foxbpm.Expression expression){
		if(expression != null){
			return expression.getValue();
		}
		return null;
	}
	
	public static Object getExtensionAttribute(BaseElement baseElement,EAttribute eAttribute){
		return baseElement.eGet(eAttribute);
	}
	
	public static Object getExtensionElement(BaseElement baseElement,EReference eReference){
		if(baseElement==null){
			return null;
		}
		if (baseElement.getExtensionValues().size() > 0) {
			for (ExtensionAttributeValue extensionAttributeValue : baseElement.getExtensionValues()) {
				FeatureMap extensionElements = extensionAttributeValue.getValue();
				Object objectElement = extensionElements.get(eReference, true);
				if (objectElement != null) {
					if(objectElement instanceof List){
						@SuppressWarnings("unchecked")
						List<EObject> tObjList = (List<EObject>)objectElement;
						if(tObjList.size()>0){
							return tObjList.get(0);
						}
					}else{
						return objectElement;
					}
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getExtensionElementOne(Class<T> t ,BaseElement baseElement,EReference eReference){
	
		
		if(baseElement==null){
			return null;
		}
		
		if (baseElement.getExtensionValues().size() > 0) {
			for (ExtensionAttributeValue extensionAttributeValue : baseElement.getExtensionValues()) {
				FeatureMap extensionElements = extensionAttributeValue.getValue();
				
				Object objectElement = extensionElements.get(eReference, true);
				if (objectElement != null) {

					
					if(objectElement instanceof List){
						List<T> tObjList = (List<T>) objectElement;
						if(tObjList.size()>0){
							return tObjList.get(0);
						}
						
					}else{
						return (T)objectElement;
					}

				}

				
			}
		}
		
		
		
		
		
		return (T)null;
	}
	
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> getExtensionElementList( Class<T> t ,BaseElement baseElement,EReference eReference){
		
		
		if (baseElement.getExtensionValues().size() > 0) {
			for (ExtensionAttributeValue extensionAttributeValue : baseElement.getExtensionValues()) {
				FeatureMap extensionElements = extensionAttributeValue.getValue();
				Object objectElement = extensionElements.get(eReference, true);
				if (objectElement != null) {

					List<T> tObjList = (List<T>) objectElement;
					return tObjList;
				

				}
			}
		}
		
		
		return (List<T>)null;
	}
	
	
	public static String getDocumentation(BaseElement baseElement){
		List<Documentation> documentations=baseElement.getDocumentation();
		if(documentations.size()==0){
			return null;
		}
		else{
			String documentationText=documentations.get(0).getText();
			return documentationText;
		}
	}
	
	/**
	 * 增加扩展元素
	 * @param baseElement
	 * @param eReference
	 * @param o
	 * @return
	 */
	public static boolean addExtensionElement(BaseElement baseElement,EReference eReference,Object o){
		final FeatureMap.Entry extensionElementEntry = new SimpleFeatureMapEntry((org.eclipse.emf.ecore.EStructuralFeature.Internal) eReference, o);
  	  	if(baseElement.getExtensionValues().size() > 0){
  	  		baseElement.getExtensionValues().get(0).getValue().add(extensionElementEntry);
  	  	}else{
  	  		ExtensionAttributeValue extensionElement = Bpmn2Factory.eINSTANCE.createExtensionAttributeValue();
  	  		extensionElement.getValue().add(extensionElementEntry);
  	  		baseElement.getExtensionValues().add(extensionElement);
  	  	} 
		return false;
	}
	
	/**
	 * 获取节点的跳过策略
	 * 
	 * @return
	 */
	public static SkipStrategy getSkipStrategy(BaseElement baseElement) {

		return getExtensionElementOne(SkipStrategy.class,baseElement,FoxBPMPackage.Literals.DOCUMENT_ROOT__SKIP_STRATEGY);
	
	}
	
	/**
	 * 增加擴展屬性
	 * @param baseElement
	 * @param eReference
	 * @param o
	 * @return
	 */
	public static boolean addExtensionAttribute(BaseElement baseElement,EAttribute eAttribute,Object o){
		final FeatureMap.Entry extensionElementEntry = new SimpleFeatureMapEntry((org.eclipse.emf.ecore.EStructuralFeature.Internal) eAttribute, o);
		baseElement.getAnyAttribute().add(extensionElementEntry);
		return false;
	}
	
	public static void setDocumentation(BaseElement baseElement,String documentationText){
		List<Documentation> documentations=baseElement.getDocumentation();
		if(documentations==null){
			documentations=new ArrayList<Documentation>();
			Documentation documentation=Bpmn2Factory.eINSTANCE.createDocumentation();
			documentation.setText(documentationText);
			documentations.add(documentation);
			return;
		}
		if(documentations.size()==0){
			Documentation documentation=Bpmn2Factory.eINSTANCE.createDocumentation();
			documentation.setText(documentationText);
			documentations.add(documentation);
			return;
		}
		else{
			documentations.get(0).setText(documentationText);
			return;
		}
	}
	
	public static String getExpression(Expression expression){
		if(expression==null){
			return null;
		}
		return ((FormalExpression)expression).getBody();
		
	}
	
	public static Expression getExpressionByString(String expression){
		FormalExpression formalExpression=Bpmn2Factory.eINSTANCE.createFormalExpression();
		formalExpression.setBody(expression);
		return formalExpression;
		
	}
	
	public static BPMNShape getBpmnShape(Definitions definitions,String elementId){
		List<DiagramElement> diagramElements=definitions.getDiagrams().get(0).getPlane().getPlaneElement();
		for (DiagramElement diagramElement : diagramElements) {
			if(diagramElement instanceof BPMNShape){
				BPMNShape bpmnShape = (BPMNShape) diagramElement;
				BaseElement bpmnElement=getBaseElement(definitions,bpmnShape.getBpmnElement());
				if(bpmnElement==null){
					continue;
				}
				if(elementId.equals(bpmnElement.getId())){
					return bpmnShape;
				}
			}
		}
		return null;
	}
	
	public static  BaseElement getBaseElement(Definitions definitions,BaseElement baseElement){
		if(baseElement==null){
			return null;
		}
		if(baseElement.getId()==null){
			BasicEObjectImpl basicEObjectImpl=(BasicEObjectImpl)baseElement;
			if(basicEObjectImpl!=null&&basicEObjectImpl.eProxyURI()!=null){
				String elementId=basicEObjectImpl.eProxyURI().fragment();
				BaseElement bpmnElement=getBaseElement(definitions,elementId);
				return bpmnElement;
			}
			else{
				return null;
			}
		}else{
			return baseElement;
		}
	}
	
	public static BPMNEdge getBpmnEdge(Definitions definitions,String elementId){
		List<DiagramElement> diagramElements=definitions.getDiagrams().get(0).getPlane().getPlaneElement();
		for (DiagramElement diagramElement : diagramElements) {
			if(diagramElement instanceof BPMNEdge){
				BPMNEdge bPMNEdge = (BPMNEdge)diagramElement;
				String bpmnId=bPMNEdge.getBpmnElement().getId();
				if(elementId.equals(bpmnId)){
					return bPMNEdge;
				}
			}
		}
		return null;
	}
	
	
	public static BaseElement findElement(String id,EObject eObject) {
		if (id == null || id.isEmpty())
			return null;

		List<BaseElement> baseElements = getAll(BaseElement.class,eObject);

		for (BaseElement be : baseElements) {
			if (id.equals(be.getId())) {
				return be;
			}
		}

		return null;
	}
	

	@SuppressWarnings("unchecked")
	public static <T> T findElement(String id,EObject eObject,Class<T> class1) {
		if (id == null || id.isEmpty())
			return null;

		List<BaseElement> baseElements = getAll(BaseElement.class,eObject);

		for (BaseElement be : baseElements) {
			if (id.equals(be.getId())&&class1.isInstance(be)) {
				return (T)be;
			}
		}

		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> getAll(final Class<T> class1,EObject eObject) {
		ArrayList<T> l = new ArrayList<T>();
		TreeIterator<EObject> contents =eObject.eResource().getAllContents();
		for (; contents.hasNext();) {
			Object t = contents.next();
			if (class1.isInstance(t)) {
				l.add((T) t);
			}
		}
		return l;
	}
	
	public static BaseElement  getBaseElement(Definitions definitions,String elementId){
		return findElement(elementId, definitions);
	}
	
	public static <T> T  getElement(Definitions definitions,String elementId,Class<T> class1){
		return findElement(elementId, definitions, class1);
	}
	
	public static <T> List<T>  getElementList(BaseElement baseElement,Class<T> class1){
		return getAll(class1, baseElement);
	}
	
	public static Process getProcess(BaseElement baseElement){
		return getAll(Process.class, baseElement).get(0);
	}
	
	public static List<Process> getProcessList(BaseElement baseElement){
		return getAll(Process.class, baseElement);
	}

}
