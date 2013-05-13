package com.mdmp.common.exception;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class MDMPException extends RuntimeException {
	private static ResourceBundle rb = ResourceBundle.getBundle("errorCode");

	private String errorCode;
	private String message;
	private Throwable cause = this;
	
	public MDMPException(){
		
	}
	public MDMPException(String errorCode) {
		this.errorCode = errorCode;
		init(null);
	}

	public MDMPException(String errorCode, Object... parameters) {
		this.errorCode = errorCode;
		init(parameters);
	}

	public MDMPException(String errorCode, Throwable cause) {
		this.errorCode = errorCode;
		this.cause = cause;
		init(null);
	}

	public MDMPException(String errorCode, Throwable cause,
			Object... parameters) {
		this.errorCode = errorCode;
		this.cause = cause;
		init(parameters);
	}

	private void init(Object[] parameters) {
		if (errorCode == null) {
			message = "unknown exception";
			return;
		}
		message = rb.getString(errorCode);
		if (message == null) {
			message = errorCode;
		}
		message = MessageFormat.format(message, parameters);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getMessage() {
		return message;
	}

	public Throwable getCause() {
		return (cause == this ? null : cause);
	}

}
