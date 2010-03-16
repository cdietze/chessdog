package com.christophdietze.jack.common.board;

@SuppressWarnings("serial")
public class SanParsingException extends Exception {

	public SanParsingException() {
		super();
	}

	public SanParsingException(String message, Throwable cause) {
		super(message, cause);
	}

	public SanParsingException(String message) {
		super(message);
	}

	public SanParsingException(Throwable cause) {
		super(cause);
	}
}
