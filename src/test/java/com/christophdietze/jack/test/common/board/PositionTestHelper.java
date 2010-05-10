package com.christophdietze.jack.test.common.board;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.christophdietze.jack.common.board.ChessUtils;
import com.christophdietze.jack.common.board.Piece;
import com.christophdietze.jack.common.board.PieceType;
import com.christophdietze.jack.common.board.Position;
import com.christophdietze.jack.common.board.PositionUtils;
import com.christophdietze.jack.common.pgn.FenWriter;

public class PositionTestHelper {

	private static Logger log = LoggerFactory.getLogger(PositionTestHelper.class);

	/**
	 * Creates a new Position containing only the pieces specified in the squareNotationList.
	 * <p>
	 * A square notation can be e.g., 'Ke4', 'pf3', etc.
	 */
	public static Position.Builder newPosition(String... squareNotationList) {
		Position.Builder builder = new Position.Builder();
		for (String squareNotation : squareNotationList) {
			PositionedPiece square = parseSquareNotation(squareNotation);
			builder.piece(square.getIndex(), square.getPiece());
		}
		return builder;
	}

	public static void assertSquares(Position position, String... squareNotationList) {
		for (String squareNotation : squareNotationList) {
			PositionedPiece square = parseSquareNotation(squareNotation);
			if (square.getPiece() != position.getPiece(square.getIndex())) {
				log.info(PositionUtils.toDiagramString(position));
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
		if (squareNotation.length() != 2 && squareNotation.length() != 3) {
			throw new RuntimeException("string length must be 2 or 3 chars long, '" + squareNotation + "'");
		}
		Piece piece = Piece.EMPTY;
		int offset = 0;
		if (squareNotation.length() == 3) {
			offset = 1;
			char pieceSymbol = squareNotation.charAt(0);
			PieceType pieceType = PieceType.getBySymbol(Character.toUpperCase(pieceSymbol));
			boolean isWhite = Character.isUpperCase(pieceSymbol);
			piece = Piece.getFromColorAndPieceType(isWhite, pieceType);
		}
		assert piece != null;
		int index = ChessUtils.toIndexFromAlgebraic(squareNotation.substring(offset, offset + 2));
		return new PositionedPiece(piece, index);
	}

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
