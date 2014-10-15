package org.foxbpm.model;

public class CatchEvent extends Event {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected boolean isParallelMultiple;

	public boolean isParallelMultiple() {
		return isParallelMultiple;
	}

	public void setParallelMultiple(boolean isParallelMultiple) {
		this.isParallelMultiple = isParallelMultiple;
	}
}
