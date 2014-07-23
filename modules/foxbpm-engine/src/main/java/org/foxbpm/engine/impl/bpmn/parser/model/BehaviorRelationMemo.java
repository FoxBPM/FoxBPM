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
package org.foxbpm.engine.impl.bpmn.parser.model;

import org.eclipse.bpmn2.Activity;
import org.foxbpm.engine.impl.bpmn.behavior.ActivityBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.BoundaryEventBehavior;
import org.foxbpm.engine.impl.util.StringUtil;

/**
 * 
 * 
 * BehaviorRelationMemo 用于记录解释过程中产生的关联关系
 * 
 * MAENLIANG 2014年7月22日 下午3:29:03
 * 
 * @version 1.0.0
 * 
 */
public class BehaviorRelationMemo {
	private Activity attachActivity;
	private ActivityBehavior attachActivityBehavior;

	private Activity beAttachedActivity;
	private BoundaryEventBehavior beAttachedActivityBoundaryEventBehavior;

	/**
	 * 
	 * attachActivityAndBoundaryEventBehaviorRelation(创建Activity
	 * 和BoundaryEventBehavior之间的关联关系) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public void attachActivityAndBoundaryEventBehaviorRelation() {
		if (this.attachActivity != null && attachActivityBehavior != null
				&& beAttachedActivity != null && beAttachedActivityBoundaryEventBehavior != null
				&& StringUtil.equals(attachActivity.getId(), beAttachedActivity.getId())) {
			attachActivityBehavior.getBoundaryEvents().add(beAttachedActivityBoundaryEventBehavior);
			this.setBeAttachedActivity(null);
			this.setBeAttachedActivityBoundaryEventBehavior(null);
		}
	}
	public Activity getAttachActivity() {
		return attachActivity;
	}
	public void setAttachActivity(Activity attachActivity) {
		this.attachActivity = attachActivity;
	}

	public Activity getBeAttachedActivity() {
		return beAttachedActivity;
	}
	public void setBeAttachedActivity(Activity beAttachedActivity) {
		this.beAttachedActivity = beAttachedActivity;
	}
	public BoundaryEventBehavior getBeAttachedActivityBoundaryEventBehavior() {
		return beAttachedActivityBoundaryEventBehavior;
	}
	public void setBeAttachedActivityBoundaryEventBehavior(
			BoundaryEventBehavior beAttachedActivityBoundaryEventBehavior) {
		this.beAttachedActivityBoundaryEventBehavior = beAttachedActivityBoundaryEventBehavior;
	}
	public ActivityBehavior getAttachActivityBehavior() {
		return attachActivityBehavior;
	}
	public void setAttachActivityBehavior(ActivityBehavior attachActivityBehavior) {
		this.attachActivityBehavior = attachActivityBehavior;
	}
}
