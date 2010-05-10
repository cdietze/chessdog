package com.christophdietze.jack.common.pgn;

import com.christophdietze.jack.common.board.Piece;
import com.christophdietze.jack.common.board.PieceType;
import com.christophdietze.jack.common.board.Position;

/**
 * Parser for a reduced version of FEN. The differences are:
 * <ul>
 * <li>Only the position is stored. Meta info such as e.p., castles, etc. is omitted.</li>
 * <li>The seperator '/' is omitted.</li>
 * <li>It starts from a1 instead of a8.</li>
 * <li>Any consecutive numbers are replaced by their sum.</li>
 * <li>A trailing number is omitted.</li>
 * </ul>
 * E.g. 'RNBQKBNRPPPPPPPP32pppppppprnbqkbnr' is the initial position.
 */
public class ReducedFenParser {

	private Position.Builder builder;
	private String inputString;

	public ReducedFenParser() {
	}

	public void parse(String inputString) throws ReducedFenParsingException {
		this.inputString = inputString;
		builder = new Position.Builder();
		parsePosition();
	}

	public Position getPosition() {
		return builder.build();
	}

	private void parsePosition() throws ReducedFenParsingException {
		int skipCount = 0;
		int index = 0;
		for (char ch : inputString.toCharArray()) {
			if (ch >= '0' && ch <= '9') {
				skipCount *= 10;
				skipCount += ch - '0';
				continue;
			}
			if (skipCount > 0) {
				index += skipCount;
				skipCount = 0;
			}
			boolean isWhite;
			if (Character.isUpperCase(ch)) {
				isWhite = true;
			} else {
				isWhite = false;
				ch = Character.toUpperCase(ch);
			}
			PieceType piece = PieceType.getBySymbol(ch);
			if (piece == null) {
				throw new ReducedFenParsingException("Unrecognized symbol '" + ch + "'");
			}
			Piece square = Piece.getFromColorAndPieceType(isWhite, piece);
			if (index > 63) {
				throw new ReducedFenParsingException("Input too long");
			}
			builder.piece(index, square);
			index++;
		}
	}
}
