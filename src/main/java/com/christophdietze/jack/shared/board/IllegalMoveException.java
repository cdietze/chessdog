package com.christophdietze.jack.shared.board;

@SuppressWarnings("serial")
public class IllegalMoveException extends Exception {

	public IllegalMoveException() {
		super();
	}

	public IllegalMoveException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalMoveException(String message) {
		super(message);
	}

	public IllegalMoveException(Throwable cause) {
		super(cause);
	}

}
