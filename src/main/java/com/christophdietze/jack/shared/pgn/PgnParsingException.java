package com.christophdietze.jack.shared.pgn;

@SuppressWarnings("serial")
public class PgnParsingException extends Exception {

	public PgnParsingException() {
		super();
	}

	public PgnParsingException(String message, Throwable cause) {
		super(message, cause);
	}

	public PgnParsingException(String message) {
		super(message);
	}

	public PgnParsingException(Throwable cause) {
		super(cause);
	}
}
