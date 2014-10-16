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
 * @author ych
 */
package org.foxbpm.model;

/**
 * 多实例
 * @author ych
 *
 */
public class MultiInstanceLoopCharacteristics extends LoopCharacteristics {

	private static final long serialVersionUID = 1L;

	/** 输入数据集 */
	private String loopDataInputCollection;
	/** 输出数据集 */
	private String loopDataOutputCollection;
	/** 输入项 */
	private String inputDataItem;
	/** 输出项 */
	private String outputDataItem;
	/** 完成条件 */
	private String completionCondition;
	/** 完成触发机制 */
	private String completeType;
	/** 是否是串行 */
	private boolean isSequential = false;

	public String getLoopDataInputCollection() {
		return loopDataInputCollection;
	}

	public void setLoopDataInputCollection(String loopDataInputCollection) {
		this.loopDataInputCollection = loopDataInputCollection;
	}

	public String getLoopDataOutputCollection() {
		return loopDataOutputCollection;
	}

	public void setLoopDataOutputCollection(String loopDataOutputCollection) {
		this.loopDataOutputCollection = loopDataOutputCollection;
	}

	public String getInputDataItem() {
		return inputDataItem;
	}

	public void setInputDataItem(String inputDataItem) {
		this.inputDataItem = inputDataItem;
	}

	public String getOutputDataItem() {
		return outputDataItem;
	}

	public void setOutputDataItem(String outputDataItem) {
		this.outputDataItem = outputDataItem;
	}

	public String getCompletionCondition() {
		return completionCondition;
	}

	public void setCompletionCondition(String completionCondition) {
		this.completionCondition = completionCondition;
	}

	public String getCompleteType() {
		return completeType;
	}

	public void setCompleteType(String completeType) {
		this.completeType = completeType;
	}

	public boolean isSequential() {
		return isSequential;
	}

	public void setSequential(boolean isSequential) {
		this.isSequential = isSequential;
	}

}
