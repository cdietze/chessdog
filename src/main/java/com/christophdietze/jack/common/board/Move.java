package com.christophdietze.jack.common.board;

import com.christophdietze.jack.common.util.SimpleToStringBuilder;

public class Move {

	private int from;
	private int to;
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

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
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
		return SimpleToStringBuilder.create(this).append("from", from).append("to", to).toString();
	}

	public String toAlgebraic() {
		return ChessUtils.toAlgebraicFromIndex(from) + ChessUtils.toAlgebraicFromIndex(to);
	}
}
