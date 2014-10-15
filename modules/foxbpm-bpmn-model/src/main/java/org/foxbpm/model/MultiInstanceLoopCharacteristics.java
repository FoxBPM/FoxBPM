package org.foxbpm.model;

public class MultiInstanceLoopCharacteristics extends LoopCharacteristics {

	/**
	 * 
	 */
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
