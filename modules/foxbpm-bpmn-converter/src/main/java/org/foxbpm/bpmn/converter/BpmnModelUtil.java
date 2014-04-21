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
package org.foxbpm.bpmn.converter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Bpmn2Factory;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.Documentation;
import org.eclipse.bpmn2.Expression;
import org.eclipse.bpmn2.ExtensionAttributeValue;
import org.eclipse.bpmn2.FormalExpression;
import org.eclipse.bpmn2.Process;
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
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.model.bpmn.foxbpm.FoxBPMPackage;


public class BpmnModelUtil {
	
	/** 获取流程的唯一编号*/
	public static String getProcessId(Process process){
		return StringUtil.getString(getExtensionAttribute(process,FoxBPMPackage.Literals.DOCUMENT_ROOT__DBID));
	}
	
	
	public static Object getExtensionAttribute(BaseElement baseElement,EAttribute eAttribute){
		return baseElement.eGet(eAttribute);
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
				String bpmnId=((BPMNEdge)diagramElement).getBpmnElement().getId();
				if(elementId.equals(bpmnId)){
					return (BPMNEdge)diagramElement;
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
