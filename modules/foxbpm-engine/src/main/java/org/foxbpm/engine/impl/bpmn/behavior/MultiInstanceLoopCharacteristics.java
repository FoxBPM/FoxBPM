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

/**
 * 
 * @author kenshin
 *
 */
public class MultiInstanceLoopCharacteristics extends LoopCharacteristics {

	private static final long serialVersionUID = 1L;
	
	
	/** 输入数据集 */
	private Expression loopDataInputCollection;
	/** 输出数据集 */
	private Expression loopDataOutputCollection;
	/** 输入项 */
	private Expression inputDataItem;
	/** 输出项 */
	private Expression outputDataItem;
	/** 完成条件 */
	private Expression completionCondition;
	/** 完成触发机制 */
	private MultiInstanceBehavior multiInstanceBehavior=MultiInstanceBehavior.ALL;
	/** 是否是串行 */
	private boolean isSequential=false;
	
	
	public Expression getLoopDataInputCollection() {
		return loopDataInputCollection;
	}
	public void setLoopDataInputCollection(String loopDataInputCollection) {
		this.loopDataInputCollection = new ExpressionImpl(loopDataInputCollection);
	}
	public Expression getLoopDataOutputCollection() {
		return loopDataOutputCollection;
	}
	public void setLoopDataOutputCollection(String loopDataOutputCollection) {
		this.loopDataOutputCollection = new ExpressionImpl(loopDataOutputCollection);
	}
	public Expression getInputDataItem() {
		return inputDataItem;
	}
	public void setInputDataItem(String inputDataItem) {
		this.inputDataItem = new ExpressionImpl(inputDataItem);
	}
	public Expression getOutputDataItem() {
		return outputDataItem;
	}
	public void setOutputDataItem(String outputDataItem) {
		this.outputDataItem = new ExpressionImpl(outputDataItem);
	}
	public Expression getCompletionCondition() {
		return completionCondition;
	}
	public void setCompletionCondition(String completionCondition) {
		this.completionCondition = new ExpressionImpl(completionCondition);
	}
	public MultiInstanceBehavior getMultiInstanceBehavior() {
		return multiInstanceBehavior;
	}
	public void setMultiInstanceBehavior(MultiInstanceBehavior multiInstanceBehavior) {
		this.multiInstanceBehavior = multiInstanceBehavior;
	}
	public boolean isSequential() {
		return isSequential;
	}
	public void setSequential(boolean isSequential) {
		this.isSequential = isSequential;
	}
	
	

}
