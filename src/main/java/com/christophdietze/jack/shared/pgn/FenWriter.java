package com.christophdietze.jack.shared.pgn;

import com.christophdietze.jack.shared.board.Piece;
import com.christophdietze.jack.shared.board.Position;

/**
 * @see http://en.wikipedia.org/wiki/Forsyth-Edwards_Notation
 */
public class FenWriter {

	public static String toFen(Position position) {
		return new FenWriter().write(position);
	}

	public FenWriter() {
	}

	public String write(Position position) {
		StringBuilder sb = new StringBuilder();
		writePosition(position, sb);
		sb.append(' ');
		sb.append(position.isWhiteToMove() ? 'w' : 'b');
		sb.append(' ');
		writeCastleAvailabitity(position, sb);
		sb.append(' ');
		writeEnPassantAvailability(position, sb);
		sb.append(' ');
		sb.append(position.getHalfmoveClock());
		sb.append(' ');
		sb.append(position.getFullmoveNumber());
		return sb.toString();
	}

	private void writePosition(Position position, StringBuilder sb) {
		int skipCount = 0;
		for (int y = 0; y < 8; ++y) {
			if (y != 0) {
				if (skipCount > 0) {
					sb.append(skipCount);
					skipCount = 0;
				}
				sb.append("/");
			}
			for (int x = 0; x < 8; ++x) {
				int index = (7 - y) * 8 + x;
				Piece square = position.getPiece(index);
				if (square.isEmpty()) {
					skipCount++;
				} else {
					if (skipCount > 0) {
						sb.append(skipCount);
						skipCount = 0;
					}
					char symbol = square.getPieceType().getSymbol();
					if (!square.isWhite()) {
						symbol = Character.toLowerCase(symbol);
					}
					sb.append(symbol);
				}
			}
		}
		if (skipCount > 0) {
			sb.append(skipCount);
			skipCount = 0;
		}
	}

	private void writeCastleAvailabitity(Position position, StringBuilder sb) {
		boolean empty = true;
		if (position.canWhiteCastleKingside()) {
			sb.append('K');
			empty = false;
		}
		if (position.canWhiteCastleQueenside()) {
			sb.append('Q');
			empty = false;
		}
		if (position.canBlackCastleKingside()) {
			sb.append('k');
			empty = false;
		}
		if (position.canBlackCastleQueenside()) {
			sb.append('q');
			empty = false;
		}
		if (empty) {
			sb.append('-');
		}
	}

	private void writeEnPassantAvailability(Position position, StringBuilder sb) {
		int startIndex = position.isWhiteToMove() ? 5 * 8 : 2 * 8;
		Piece square = position.isWhiteToMove() ? Piece.BLACK_EN_PASSANT_PAWN : Piece.WHITE_EN_PASSANT_PAWN;
		for (int offset = 0; offset < 8; ++offset) {
			int index = startIndex + offset;
			if (position.getPiece(index) == square) {
				sb.append((char) ('a' + offset));
				sb.append(position.isWhiteToMove() ? '6' : '3');
				return;
			}
		}
		sb.append('-');
	}
}
