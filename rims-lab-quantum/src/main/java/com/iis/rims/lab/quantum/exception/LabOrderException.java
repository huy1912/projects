package com.iis.rims.lab.quantum.exception;

public class LabOrderException extends RuntimeException {

	public LabOrderException() {
	}

	public LabOrderException(String message) {
		super(message);
	}

	public LabOrderException(Throwable cause) {
		super(cause);
	}

	public LabOrderException(String message, Throwable cause) {
		super(message, cause);
	}

	public LabOrderException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
