package com.christophdietze.jack.test.common.board;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.christophdietze.jack.common.board.ChessUtils;
import com.christophdietze.jack.common.board.Piece;
import com.christophdietze.jack.common.board.PieceType;
import com.christophdietze.jack.common.board.Position;
import com.christophdietze.jack.common.pgn.FenWriter;

/**
 * TODO make this a non-static class ?!
 */
public class PositionSetupUtils {

	private static Logger log = LoggerFactory.getLogger(PositionSetupUtils.class);

	/**
	 * Creates a new Position containing only the pieces specified in the squareNotationList.
	 * <p>
	 * A square notation can be e.g., 'Ke4', 'pf3', etc.
	 */
	public static Position newPosition(String... squareNotationList) {
		Position position = new Position();
		for (String squareNotation : squareNotationList) {
			PositionedPiece square = parseSquareNotation(squareNotation);
			position.setPiece(square.getIndex(), square.getPiece());
		}
		return position;
	}

	public static void assertSquaresInPosition(Position position, String... squareNotationList) {
		for (String squareNotation : squareNotationList) {
			PositionedPiece square = parseSquareNotation(squareNotation);
			if (square.getPiece() != position.getPiece(square.getIndex())) {
				log.info(position.toDiagramString());
				throw new RuntimeException("Assertion '" + squareNotation + "' failed");
			}
		}
	}

	public static boolean arePositionsEqual(Position pos1, Position pos2) {
		String fen1 = new FenWriter().write(pos1);
		String fen2 = new FenWriter().write(pos2);
		return fen1.equals(fen2);
	}

	private static PositionedPiece parseSquareNotation(String squareNotation) {
		if (squareNotation.length() != 3) {
			throw new RuntimeException("string length must be 3 chars long, '" + squareNotation + "'");
		}
		char pieceSymbol = squareNotation.charAt(0);
		PieceType pieceType = PieceType.getBySymbol(Character.toUpperCase(pieceSymbol));
		boolean isWhite = Character.isUpperCase(pieceSymbol);
		Piece piece = Piece.getFromColorAndPiece(isWhite, pieceType);
		assert piece != null;
		int index = ChessUtils.toIndexFromAlgebraic(squareNotation.substring(1, 3));
		return new PositionedPiece(piece, index);
	}

	//
	// private static Pair<Square, Integer> toSquare(String squareNotation) {
	// if (squareNotation.length() != 2 && squareNotation.length() != 3) {
	// throw new RuntimeException("string length must be 2 or 3 chars long, '" + squareNotation + "'");
	// }
	// boolean isWhite = Character.isUpperCase(squareNotation.charAt(0));
	// Piece piece;
	// if (squareNotation.length() == 2) {
	// piece = null;
	// } else {
	// piece = Piece.getBySymbol(Character.toUpperCase(squareNotation.charAt(0)));
	// if (piece == null) {
	// throw new RuntimeException("unknown piece type for '" + squareNotation + "'");
	// }
	// }
	// String algebraic = (squareNotation.length() == 2 ? squareNotation.toLowerCase() : squareNotation.substring(1, 3)
	// .toLowerCase());
	// int index = ChessUtils.toIndexFromAlgebraic(algebraic);
	// Square square = Square.getFromColorAndPiece(isWhite, piece);
	// return new Pair<Square, Integer>(square, index);
	// }

	// public static Move createMove(String algebraicFrom, String algebraicTo) {
	// return new Move(ChessUtils.toIndexFromAlgebraic(algebraicFrom), ChessUtils.toIndexFromAlgebraic(algebraicTo));
	// }

	private static class PositionedPiece {
		private final Piece piece;
		private final int index;

		public PositionedPiece(Piece piece, int index) {
			this.piece = piece;
			this.index = index;
		}

		public Piece getPiece() {
			return piece;
		}

		public int getIndex() {
			return index;
		}
	}
}
