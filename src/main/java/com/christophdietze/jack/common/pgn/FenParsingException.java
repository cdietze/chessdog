package com.christophdietze.jack.common.pgn;

@SuppressWarnings("serial")
public class FenParsingException extends Exception {

	// public FenParsingException() {
	// super();
	// }

	// public FenParsingException(String message, Throwable cause) {
	// super(message, cause);
	// }

	public FenParsingException(String fenString, int index, String message) {
		super(message + ", at index " + index + ", input: '" + fenString + "'");
	}

	// public FenParsingException(Throwable cause) {
	// super(cause);
	// }
}
