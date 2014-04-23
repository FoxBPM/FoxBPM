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
package org.foxbpm.bpmn.converter.model;

import org.eclipse.bpmn2.BaseElement;
import org.foxbpm.engine.impl.behavior.ActivityBehavior;
import org.foxbpm.engine.impl.behavior.BaseElementBehavior;

public class ActivityParser extends FlowNodeParser {

	@Override
	public BaseElementBehavior parser(BaseElement baseElement) {
		// TODO Auto-generated method stub
		return super.parser(baseElement);
	}

	@Override
	public void init() {
		baseElementBehavior=new ActivityBehavior();
	}

}
