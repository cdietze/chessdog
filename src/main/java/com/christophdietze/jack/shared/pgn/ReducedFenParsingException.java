package com.christophdietze.jack.shared.pgn;

@SuppressWarnings("serial")
public class ReducedFenParsingException extends Exception {

	public ReducedFenParsingException() {
		super();
	}

	public ReducedFenParsingException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReducedFenParsingException(String message) {
		super(message);
	}

	public ReducedFenParsingException(Throwable cause) {
		super(cause);
	}

}
