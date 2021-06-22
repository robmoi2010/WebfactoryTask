package com.webfactory.exchangerateservice.webclient.exceptions;

public class NullRequestTypeException extends Exception {
	private static final long serialVersionUID = 1L;

	public NullRequestTypeException() {
		super();
	}

	public NullRequestTypeException(String message) {
		super(message);
	}

	public NullRequestTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public NullRequestTypeException(Throwable cause) {
		super(cause);
	}
}
