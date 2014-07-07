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
package org.foxbpm.kernel.process.impl;

import java.util.Comparator;

import org.foxbpm.kernel.process.KernelSequenceFlow;

public class ComparatorSequence implements Comparator<KernelSequenceFlow> {

	public int compare(KernelSequenceFlow sequenceFlow1, KernelSequenceFlow sequenceFlow2) {

		KernelSequenceFlow sequenceFlowBehavior1 = sequenceFlow1;
		KernelSequenceFlow sequenceFlowBehavior2 = sequenceFlow2;

		int orderId1 = sequenceFlowBehavior1.getOrderId();
		int orderId2 = sequenceFlowBehavior2.getOrderId();

		if (orderId1 > orderId2) {
			return 1;
		} else {
			if (orderId1 == orderId2) {
				return 0;
			} else {
				return -1;
			}

		}

	}

}
