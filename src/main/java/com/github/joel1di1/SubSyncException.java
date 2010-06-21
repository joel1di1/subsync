package com.github.joel1di1;

public class SubSyncException extends RuntimeException {

	private static final long serialVersionUID = -5792886225161578481L;

	public SubSyncException(String message) {
		super(message);
	}

	public SubSyncException(String message, Throwable cause) {
		super(message, cause);
	}

}
