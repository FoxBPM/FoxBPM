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
 * @author ych
 */
package org.foxbpm.bpmn.parser.model;

import java.util.List;

import org.foxbpm.engine.impl.bpmn.behavior.BaseElementBehavior;
import org.foxbpm.engine.impl.connector.ConnectorListener;
import org.foxbpm.kernel.process.impl.KernelFlowElementsContainerImpl;
import org.foxbpm.model.BaseElement;

public class BaseElementParser {

	protected KernelFlowElementsContainerImpl flowElementsContainer;
	protected BaseElementBehavior baseElementBehavior;

	/**
	 * @param baseElement
	 * @return
	 */
	public BaseElementBehavior parser(BaseElement baseElement) {
		baseElementBehavior.setId(baseElement.getId());
		baseElementBehavior.setBaseElement(baseElement);
		return baseElementBehavior;
	}

	protected List<ConnectorListener> parserConnector(BaseElement baseElement, String connrctorType) {
		return null;
	}

	public void init() {
		baseElementBehavior = new BaseElementBehavior();
	}

	public KernelFlowElementsContainerImpl getFlowElementsContainer() {
		return flowElementsContainer;
	}

	public void setFlowElementsContainer(KernelFlowElementsContainerImpl flowElementsContainer) {
		this.flowElementsContainer = flowElementsContainer;
	}

}
