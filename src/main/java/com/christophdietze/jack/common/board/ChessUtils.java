package com.christophdietze.jack.common.board;

public class ChessUtils {

	public static boolean isWhite(int index) {
		return (index % 8 + index / 8) % 2 != 0;
	}

	public static int toFile(int index) {
		return index % 8;
	}

	public static int toRank(int index) {
		return index / 8;
	}

	public static int toIndex(int file, int rank) {
		return file + rank * 8;
	}

	public static char toFileChar(int file) {
		return (char) ('a' + file);
	}

	public static char toRankChar(int rank) {
		return (char) ('1' + rank);
	}

	public static char toFileCharFromIndex(int index) {
		return toFileChar(toFile(index));
	}

	public static char toRankCharFromIndex(int index) {
		return toRankChar(toRank(index));
	}

	public static String toAlgebraicFromIndex(int index) {
		return "" + toFileCharFromIndex(index) + toRankCharFromIndex(index);
	}

	public static int toIndexFromAlgebraic(String algebraic) {
		if (algebraic.length() != 2) {
			throw new RuntimeException("Algebraic notation must have be two characters, was '" + algebraic + "'");
		}
		int file = algebraic.charAt(0) - 'a';
		int rank = algebraic.charAt(1) - '1';
		return file + 8 * rank;
	}

	public static int toPlyFromFullmoveNumber(int fullmoveNumber, boolean isWhiteToMove) {
		return (fullmoveNumber - 1) * 2 + (isWhiteToMove ? 0 : 1);
	}

	public static int toFullmoveNumberFromPly(int ply) {
		return ply / 2 + 1;
	}

	public static boolean toIsWhiteToMoveFromPly(int ply) {
		return ply % 2 == 0;
	}

}
