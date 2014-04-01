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
package org.foxbpm.engine.impl.util;

import org.foxbpm.engine.ProcessEngineManagement;

public class  BpmnElement {

	//	Event
	public static String StartEvent="StartEvent";
	public static String EndEvent="EndEvent";	
	public static String TimerEventDefinition="TimerEventDefinition";
	public static String TerminateEventDefinition="TerminateEventDefinition";
	public static String IntermediateCatchEvent="IntermediateCatchEvent";
	public static String ErrorEventDefinition="ErrorEventDefinition";
	public static String BoundaryEvent="BoundaryEvent";
	public static String MessageEventDefinition="MessageEventDefinition";
	public static String IntermediateThrowEvent="IntermediateThrowEvent";
	

	
	
	
	//	Gateway
	public static String ParallelGateway="ParallelGateway";	
	public static String InclusiveGateway="InclusiveGateway";	
	public static String ExclusiveGateway="ExclusiveGateway";	
	public static String EventBasedGateway="EventBasedGateway";
	public static String ComplexGateway="ComplexGateway";
	
	
	//	Activity
	public static String UserTask="UserTask";
	public static String SubProcess="SubProcess";
	public static String SendTask="SendTask";
	public static String ScriptTask="ScriptTask";
	public static String ReceiveTask="ReceiveTask";
	public static String CallActivity="CallActivity";
	
	//	Other
	public static String SequenceFlow="SequenceFlow";
	public static String ProcessDefinition="ProcessDefinition";
	public static String Definitions="Definitions";
	

	@SuppressWarnings("unchecked")
	public static <T> T getInstance(String elementId){
		Class<?> classObj=ProcessEngineManagement.getDefaultProcessEngine().getProcessEngineConfiguration().getExpandClassMap().get(elementId);
		
		try {
			return (T)classObj.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
