package com.christophdietze.jack.common.pgn;

@SuppressWarnings("serial")
public class PgnWritingException extends Exception {

	public PgnWritingException() {
		super();
	}

	public PgnWritingException(String message, Throwable cause) {
		super(message, cause);
	}

	public PgnWritingException(String message) {
		super(message);
	}

	public PgnWritingException(Throwable cause) {
		super(cause);
	}
}
