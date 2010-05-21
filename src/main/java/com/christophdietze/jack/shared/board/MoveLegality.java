package com.christophdietze.jack.shared.board;

public class MoveLegality {

	public static MoveLegality LEGAL_MOVE = new MoveLegality(true, null);
	public static MoveLegality BASIC_ILLEGAL_MOVE = newIllegalMove(null);
	public static MoveLegality IT_IS_WHITES_TURN = newIllegalMove("It is the white player's turn.");
	public static MoveLegality IT_IS_BLACKS_TURN = newIllegalMove("It is the black player's turn.");
	public static MoveLegality CANNOT_CAPTURE_OWN_PIECES = newIllegalMove("Cannot capture own pieces.");

	public static MoveLegality valueOf(boolean isLegal) {
		return isLegal ? LEGAL_MOVE : BASIC_ILLEGAL_MOVE;
	}

	private boolean isLegal;
	private String message;

	private MoveLegality(boolean isLegal, String message) {
		this.isLegal = isLegal;
		this.message = message;
	}

	private static MoveLegality newIllegalMove(String message) {
		return new MoveLegality(false, message);
	}

	public boolean isLegal() {
		return isLegal;
	}

	public void setLegal(boolean isLegal) {
		this.isLegal = isLegal;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean hasMessage() {
		return message != null;
	}
}
