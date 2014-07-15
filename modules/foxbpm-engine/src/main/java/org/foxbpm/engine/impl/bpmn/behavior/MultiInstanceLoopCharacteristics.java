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

/**
 * 
 * @author kenshin
 *
 */
public class MultiInstanceLoopCharacteristics extends LoopCharacteristics {

	private static final long serialVersionUID = 1L;
	
	
	/** 输入数据集 */
	protected Expression loopDataInputCollection;
	/** 输出数据集 */
	protected Expression loopDataOutputCollection;
	/** 输入项 */
	protected Expression inputDataItem;
	/** 输出项 */
	protected Expression outputDataItem;
	/** 完成条件 */
	protected Expression completionCondition;
	/** 完成触发机制 */
	protected MultiInstanceBehavior multiInstanceBehavior=MultiInstanceBehavior.ALL;
	/** 是否是串行 */
	protected boolean isSequential=false;
	
	
	public Expression getLoopDataInputCollection() {
		return loopDataInputCollection;
	}
	public void setLoopDataInputCollection(Expression loopDataInputCollection) {
		this.loopDataInputCollection = loopDataInputCollection;
	}
	public Expression getLoopDataOutputCollection() {
		return loopDataOutputCollection;
	}
	public void setLoopDataOutputCollection(Expression loopDataOutputCollection) {
		this.loopDataOutputCollection = loopDataOutputCollection;
	}
	public Expression getInputDataItem() {
		return inputDataItem;
	}
	public void setInputDataItem(Expression inputDataItem) {
		this.inputDataItem = inputDataItem;
	}
	public Expression getOutputDataItem() {
		return outputDataItem;
	}
	public void setOutputDataItem(Expression outputDataItem) {
		this.outputDataItem = outputDataItem;
	}
	public Expression getCompletionCondition() {
		return completionCondition;
	}
	public void setCompletionCondition(Expression completionCondition) {
		this.completionCondition = completionCondition;
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
