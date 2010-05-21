package com.christophdietze.jack.shared.board;

@SuppressWarnings("serial")
public class SanWritingException extends Exception {

	public SanWritingException() {
		super();
	}

	public SanWritingException(String message, Throwable cause) {
		super(message, cause);
	}

	public SanWritingException(String message) {
		super(message);
	}

	public SanWritingException(Throwable cause) {
		super(cause);
	}
}
