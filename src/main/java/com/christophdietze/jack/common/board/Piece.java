package com.christophdietze.jack.common.board;

public enum Piece {

	EMPTY(" ", true, null), WHITE_KING("K", true, PieceType.KING), BLACK_KING("k", false, PieceType.KING), WHITE_QUEEN(
			"Q", true, PieceType.QUEEN), BLACK_QUEEN("q", false, PieceType.QUEEN), WHITE_ROOK("R", true, PieceType.ROOK), BLACK_ROOK(
			"r", false, PieceType.ROOK), WHITE_BISHOP("B", true, PieceType.BISHOP), BLACK_BISHOP("b", false,
			PieceType.BISHOP), WHITE_KNIGHT("N", true, PieceType.KNIGHT), BLACK_KNIGHT("n", false, PieceType.KNIGHT), WHITE_PAWN(
			"P", true, PieceType.PAWN), BLACK_PAWN("p", false, PieceType.PAWN), WHITE_EN_PASSANT_PAWN("E", true, null), BLACK_EN_PASSANT_PAWN(
			"e", false, null);

	private final PieceType pieceType;
	private final boolean isWhite;
	private final String symbol;

	private Piece(String symbol, boolean isWhite, PieceType pieceType) {
		this.symbol = symbol;
		this.isWhite = isWhite;
		this.pieceType = pieceType;
	}

	public String getSymbol() {
		return symbol;
	}

	public boolean isWhite() {
		return isWhite;
	}

	public PieceType getPieceType() {
		return pieceType;
	}

	public boolean isPiece() {
		return pieceType != null;
	}

	public boolean isEmpty() {
		return pieceType == null;
	}

	public static Piece getFromColorAndPieceType(boolean isWhite, PieceType pieceType) {
		if (pieceType == null) {
			return EMPTY;
		}
		for (Piece s : values()) {
			if (s.pieceType == pieceType && s.isWhite() == isWhite) {
				return s;
			}
		}
		return null;
	}
}
