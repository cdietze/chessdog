package com.christophdietze.jack.common.pgn;

import com.christophdietze.jack.common.board.Piece;
import com.christophdietze.jack.common.board.Position;

/**
 * @see ReducedFenParser
 */
public class ReducedFenWriter {

	public ReducedFenWriter() {
	}

	public String write(Position position) {
		StringBuilder sb = new StringBuilder();
		writePosition(position, sb);
		return sb.toString();
	}

	private void writePosition(Position position, StringBuilder sb) {
		int skipCount = 0;
		for (int index = 0; index < 64; ++index) {
			Piece square = position.getPiece(index);
			if (square.isEmpty()) {
				skipCount++;
				continue;
			}
			if (skipCount > 0) {
				sb.append(skipCount);
				skipCount = 0;
			}
			char symbol = square.getPiece().getSymbol();
			if (!square.isWhite()) {
				symbol = Character.toLowerCase(symbol);
			}
			sb.append(symbol);
		}
	}
}
