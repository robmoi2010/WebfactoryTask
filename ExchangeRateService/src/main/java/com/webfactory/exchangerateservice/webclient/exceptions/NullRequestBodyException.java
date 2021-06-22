package com.webfactory.exchangerateservice.webclient.exceptions;

public class NullRequestBodyException extends Exception {
	private static final long serialVersionUID = 1L;

	public NullRequestBodyException() {
		super();
	}

	public NullRequestBodyException(String message) {
		super(message);
	}

	public NullRequestBodyException(String message, Throwable cause) {
		super(message, cause);
	}

	public NullRequestBodyException(Throwable cause) {
		super(cause);
	}
}
