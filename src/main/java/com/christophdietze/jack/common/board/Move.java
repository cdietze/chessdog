package com.christophdietze.jack.common.board;

public class Move {

	private final int from;
	private final int to;
	/** is null for non-promotion moves */
	private final PieceType promotionPiece;

	public Move(int from, int to, PieceType promotionPiece) {
		assert from >= 0 && from < 64;
		assert to >= 0 && to < 64;
		this.from = from;
		this.to = to;
		this.promotionPiece = promotionPiece;
	}

	public Move(int from, int to) {
		this(from, to, null);
	}

	public int getFrom() {
		return from;
	}

	public int getTo() {
		return to;
	}

	public boolean isPromotionMove() {
		return promotionPiece != null;
	}

	public PieceType getPromotionPiece() {
		return promotionPiece;
	}

	@Override
	public String toString() {
		return "Move[" + ChessUtils.toAlgebraicMove(this) + "]";
	}
}
