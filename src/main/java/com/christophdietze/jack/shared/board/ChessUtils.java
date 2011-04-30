package com.christophdietze.jack.shared.board;

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

	public static String toAlgebraicSquare(int index) {
		return "" + toFileCharFromIndex(index) + toRankCharFromIndex(index);
	}

	public static String toAlgebraicMove(Move move) {
		return ChessUtils.toAlgebraicSquare(move.getFrom()) + ChessUtils.toAlgebraicSquare(move.getTo())
				+ (move.isPromotionMove() ? move.getPromotionPiece().getSymbol() : "");
	}

	public static Move toMoveFromAlgebraic(String algebraicMove) {
		try {
			if (algebraicMove.length() != 4 && algebraicMove.length() != 5) {
				throw new RuntimeException("Algebraic move notation must have length 4 or 5");
			}
			int from = toIndexFromAlgebraic(algebraicMove.substring(0, 2));
			int to = toIndexFromAlgebraic(algebraicMove.substring(2, 4));
			if (algebraicMove.length() == 4) {
				return new Move(from, to);
			} else {
				PieceType promoPiece = PieceType.getBySymbol(algebraicMove.charAt(4));
				if (promoPiece == null) {
					throw new RuntimeException("Unknown symbol for promotion piece: '" + algebraicMove.charAt(4) + "'");
				}
				return new Move(from, to, promoPiece);
			}
		} catch (RuntimeException ex) {
			throw new RuntimeException("Error while parsing algebraic move notation '" + algebraicMove + "'", ex);
		}
	}

	public static int toIndexFromAlgebraic(String algebraicSquare) {
		try {
			if (algebraicSquare.length() != 2) {
				throw new RuntimeException("Algebraic square notation must consist of two characters");
			}
			int file = algebraicSquare.charAt(0) - 'a';
			int rank = algebraicSquare.charAt(1) - '1';
			if (file < 0 || file >= 8 || rank < 0 || rank >= 8) {
				throw new RuntimeException("Algebraic square notation contains unexpected character(s)");
			}
			return file + 8 * rank;
		} catch (RuntimeException ex) {
			throw new RuntimeException("Error while parsing algebraic square notation '" + algebraicSquare + "'", ex);
		}
	}

	public static int toPlyFromFullmoveNumber(int fullmoveNumber, boolean isWhiteToMove) {
		return (fullmoveNumber - 1) * 2 + (isWhiteToMove ? 0 : 1);
	}

	public static int toFullmoveNumberFromPly(int ply) {
		return ply / 2 + 1;
	}

	public static boolean toIsWhiteToMoveFromPly(int ply) {
		assert ply >= 0;
		return ply % 2 == 0;
	}
}
