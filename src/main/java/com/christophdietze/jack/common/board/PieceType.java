package com.christophdietze.jack.common.board;

public enum PieceType {
	KING('K'), QUEEN('Q'), BISHOP('B'), KNIGHT('N'), ROOK('R'), PAWN('P');

	private char symbol;

	private PieceType(char symbol) {
		this.symbol = symbol;
	}

	public char getSymbol() {
		return symbol;
	}

	public static PieceType getBySymbol(char symbol) {
		for (PieceType p : values()) {
			if (p.symbol == symbol) {
				return p;
			}
		}
		return null;
	}
}