package org.foxbpm.kernel.process;

public class KernelException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public KernelException(String message, Throwable cause) {
		super(message, cause);
	}

	public KernelException(String message) {
		super(message);
	}

}
