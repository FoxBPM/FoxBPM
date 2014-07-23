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
package org.foxbpm.engine.impl.bpmn.behavior;

import org.foxbpm.engine.expression.Expression;
import org.foxbpm.engine.impl.expression.ExpressionImpl;

public class TimerEventBehavior extends EventDefinition {

	private static final long serialVersionUID = 1L;

	private Expression timeDate;

	private Expression timeDuration;

	private Expression timeCycle;

	public Expression getTimeDate() {
		
		return timeDate;
	}

	public void setTimeDate(String timeDate) {
		this.timeDate = new ExpressionImpl(timeDate);
	}

	public Expression getTimeDuration() {
		return timeDuration;
	}

	public void setTimeDuration(String timeDuration) {
		this.timeDuration = new ExpressionImpl(timeDuration);
	}

	public Expression getTimeCycle() {
		return timeCycle;
	}

	public void setTimeCycle(String timeCycle) {
		this.timeCycle = new ExpressionImpl(timeCycle);
	}

}
