package org.foxbpm.web.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 封装异常信息
 * 
 * @author MEL
 * @date 2014-06-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FoxbpmWebException extends RuntimeException {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String errorCode;
	private String errorMsg;
	private Throwable cause;

	public FoxbpmWebException(String errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public FoxbpmWebException(Throwable cause) {
		super(cause);
		this.cause = cause;
	}

	public FoxbpmWebException(String errorMsg, String errorCode, Throwable cause) {
		super(errorMsg, cause);
		this.cause = cause;
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
}
