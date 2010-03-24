package com.christophdietze.jack.common.board;

public class MoveUtil {

	public static boolean makeCastleMove(Move move, Position position) {
		// FIXME check that the piece to move is a king!
		if (move.getFrom() == 4 && move.getTo() == 6) {
			// white O-O
			position.setPiece(4, Piece.EMPTY);
			position.setPiece(5, Piece.WHITE_ROOK);
			position.setPiece(6, Piece.WHITE_KING);
			position.setPiece(7, Piece.EMPTY);
			position.addPhantomKing(4);
			position.addPhantomKing(5);
			return true;
		} else if (move.getFrom() == 4 && move.getTo() == 2) {
			// white O-O-O
			position.setPiece(0, Piece.EMPTY);
			position.setPiece(1, Piece.EMPTY);
			position.setPiece(2, Piece.WHITE_KING);
			position.setPiece(3, Piece.WHITE_ROOK);
			position.setPiece(4, Piece.EMPTY);
			position.addPhantomKing(3);
			position.addPhantomKing(4);
			return true;
		} else if (move.getFrom() == 60 && move.getTo() == 62) {
			// black O-O
			position.setPiece(60, Piece.EMPTY);
			position.setPiece(61, Piece.BLACK_ROOK);
			position.setPiece(62, Piece.BLACK_KING);
			position.setPiece(63, Piece.EMPTY);
			position.addPhantomKing(60);
			position.addPhantomKing(61);
			return true;
		} else if (move.getFrom() == 60 && move.getTo() == 58) {
			// black O-O-O
			position.setPiece(56, Piece.EMPTY);
			position.setPiece(57, Piece.EMPTY);
			position.setPiece(58, Piece.BLACK_KING);
			position.setPiece(59, Piece.BLACK_ROOK);
			position.setPiece(60, Piece.EMPTY);
			position.addPhantomKing(59);
			position.addPhantomKing(60);
			return true;
		}
		return false;
	}

	/**
	 * Ignores if the promotion piece is set in the move.
	 */
	public static boolean isPseudoPromotionMove(Move move, Position position) {
		if (position.getPiece(move.getFrom()) == Piece.WHITE_PAWN && move.getFrom() / 8 == 6) {
			return true;
		}
		if (position.getPiece(move.getFrom()) == Piece.BLACK_PAWN && move.getFrom() / 8 == 1) {
			return true;
		}
		return false;
	}
}
