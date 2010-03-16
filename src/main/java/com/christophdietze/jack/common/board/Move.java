package com.christophdietze.jack.common.board;

/**
 * TODO make this immutable
 */
public class Move {

	private int from;
	private int to;
	/** is null for non-promotion moves */
	private PieceType promotionPiece;

	public Move() {
	}

	public Move(int from, int to) {
		this.from = from;
		this.to = to;
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

	public Move setPromotionPiece(PieceType promotionPiece) {
		this.promotionPiece = promotionPiece;
		return this;
	}

	@Override
	public String toString() {
		return "Move[" + ChessUtils.toAlgebraic(this) + "]";
	}
}
