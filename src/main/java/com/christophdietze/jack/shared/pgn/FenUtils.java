package com.christophdietze.jack.shared.pgn;

import com.christophdietze.jack.shared.board.IllegalPositionException;
import com.christophdietze.jack.shared.board.Position;

/**
 * Plain: rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1 URL encoded:
 * rnbqkbnr_pppppppp_8_8_8_8_PPPPPPPP_RNBQKBNR.w.KQkq.-.0.1
 */
public class FenUtils {

	public static Position fenToPosition(String fen) throws FenParsingException, IllegalPositionException {
		FenParser parser = new FenParser();
		parser.parse(fen);
		return parser.getPosition();
	}

	public static String positionToFen(Position position) {
		return new FenWriter().write(position);
	}

	public static String toUrlParameterString(String fenString) {
		return fenString.replace(' ', '.').replace('/', '_');
	}

	public static String fromUrlParameterString(String urlFenString) {
		return urlFenString.replace('_', '/').replace('.', ' ');
	}
}
