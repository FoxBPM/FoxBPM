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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.bpmn2.Activity;
import org.foxbpm.engine.impl.bpmn.behavior.ActivityBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.BoundaryEventBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.EventBehavior;

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
	/** 临时存储MAP */
	private Map<String, ActivityBehavior> attachActivityMap = new HashMap<String, ActivityBehavior>();
	private Map<String, EventBehavior> beAttachedActivityMap = new HashMap<String, EventBehavior>();

	/**
	 * 
	 * attachActivityAndBoundaryEventBehaviorRelation(创建Activity
	 * 和BoundaryEventBehavior之间的关联关系) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public void attachActivityAndBoundaryEventBehaviorRelation() {
		Set<String> keySet = beAttachedActivityMap.keySet();
		for (String activityID : keySet) {
			if (attachActivityMap.containsKey(activityID)) {
				attachActivityMap.get(activityID).getBoundaryEvents()
						.add((BoundaryEventBehavior) beAttachedActivityMap.get(activityID));
			}
		}
		this.attachActivityMap.clear();
		this.beAttachedActivityMap.clear();
	}

	/**
	 * 
	 * addActivity(保存解释得到的活动节点)
	 * 
	 * @param activity
	 * @param activityBehavior
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public void addActivity(Activity activity, ActivityBehavior activityBehavior) {
		this.attachActivityMap.put(activity.getId(), activityBehavior);
	}
	/**
	 * 
	 * addActivity(保存解释得到的事件行为)
	 * 
	 * @param activity
	 * @param eventBehavior
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public void addBeAttachedActivity(Activity activity, EventBehavior eventBehavior) {
		this.beAttachedActivityMap.put(activity.getId(), eventBehavior);
	}

}
