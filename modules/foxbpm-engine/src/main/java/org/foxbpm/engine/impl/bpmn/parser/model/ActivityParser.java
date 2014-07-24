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
package org.foxbpm.engine.impl.bpmn.parser.model;

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.LoopCharacteristics;
import org.foxbpm.engine.impl.bpmn.behavior.ActivityBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.BaseElementBehavior;
import org.foxbpm.engine.impl.bpmn.parser.BpmnBehaviorEMFConverter;
import org.foxbpm.engine.impl.bpmn.parser.BpmnParseHandlerImpl;
import org.foxbpm.engine.impl.util.BpmnModelUtil;

public class ActivityParser extends FlowNodeParser {

	@Override
	public BaseElementBehavior parser(BaseElement baseElement) {

		Activity activity = (Activity) baseElement;
		ActivityBehavior activityBehavior = (ActivityBehavior) baseElementBehavior;
		LoopCharacteristics loopCharacteristics = activity.getLoopCharacteristics();

		if (loopCharacteristics != null) {
			org.foxbpm.engine.impl.bpmn.behavior.LoopCharacteristics loopCharacteristicsbehavior = (org.foxbpm.engine.impl.bpmn.behavior.LoopCharacteristics) BpmnBehaviorEMFConverter
					.getBaseElementBehavior(loopCharacteristics, null);

			activityBehavior.setLoopCharacteristics(loopCharacteristicsbehavior);
		}
		// 记录Activity 以便维护关联关系
		BpmnParseHandlerImpl.behaviorRelationMemo.addActivity(activity, activityBehavior);
		activityBehavior.setSkipStrategy(BpmnModelUtil.getSkipStrategy(activity));

		return super.parser(baseElement);
	}

	@Override
	public void init() {
		baseElementBehavior = new ActivityBehavior();
	}

}
