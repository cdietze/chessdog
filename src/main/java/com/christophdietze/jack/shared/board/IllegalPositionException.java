package com.christophdietze.jack.shared.board;

@SuppressWarnings("serial")
public class IllegalPositionException extends Exception {

	public IllegalPositionException() {
		super();
	}

	public IllegalPositionException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalPositionException(String message) {
		super(message);
	}

	public IllegalPositionException(Throwable cause) {
		super(cause);
	}

}
