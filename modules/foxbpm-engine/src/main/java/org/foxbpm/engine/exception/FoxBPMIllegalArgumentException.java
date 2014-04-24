package org.foxbpm.engine.exception;

public class FoxBPMIllegalArgumentException extends FoxBPMException {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FoxBPMIllegalArgumentException(String exceptionCode, String objectName) {
		super(exceptionCode, new Object[] { objectName });
	}

}
