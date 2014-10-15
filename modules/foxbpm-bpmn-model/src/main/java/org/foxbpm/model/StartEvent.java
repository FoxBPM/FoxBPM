package org.foxbpm.model;

public class StartEvent extends CatchEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected boolean isPersistence;

	public boolean isPersistence() {
		return isPersistence;
	}

	public void setPersistence(boolean isPersistence) {
		this.isPersistence = isPersistence;
	}

}
