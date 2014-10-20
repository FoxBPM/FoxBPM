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
 * @author MAENLIANG
 */
package org.foxbpm.bpmn.parser.model;

import org.foxbpm.bpmn.parser.BpmnParseHandlerImpl;
import org.foxbpm.engine.impl.bpmn.behavior.BaseElementBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.BoundaryEventBehavior;
import org.foxbpm.model.BaseElement;
import org.foxbpm.model.BoundaryEvent;

/**
 * 
 * 
 * BoundaryEventParser 边界事件转换器
 * 
 * MAENLIANG 2014年7月22日 下午2:14:08
 * 
 * @version 1.0.0
 * 
 */
public class BoundaryEventParser extends CatchEventParser {
	 
	public BaseElementBehavior parser(BaseElement baseElement) {
		BoundaryEventBehavior boundaryEventBehavior = (BoundaryEventBehavior) baseElementBehavior;
		BoundaryEvent boundaryEventImpl = (BoundaryEvent) baseElement;
		boundaryEventBehavior.setCancelActivity(boundaryEventImpl.isCancelActivity());
		BpmnParseHandlerImpl.behaviorRelationMemo.addBeAttachedActivity(boundaryEventImpl.getAttachedToRef(), boundaryEventBehavior);
		return super.parser(baseElement);
	}
	 
	public void init() {
		baseElementBehavior = new BoundaryEventBehavior();
	}
}
