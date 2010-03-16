package com.christophdietze.jack.common.board;

public enum GameResult {

	/**
	 * UNDECIDED means game still in progress, game abandoned, or result otherwise unknown
	 */
	WHITE_WIN("1-0"), BLACK_WIN("0-1"), DRAW("1/2-1/2"), UNDECIDED("*");

	private String symbol;

	private GameResult(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	public static GameResult parseSymbol(String symbol) {
		for (GameResult result : GameResult.values()) {
			if (symbol.equals(result.symbol)) {
				return result;
			}
		}
		return null;
	}
}
