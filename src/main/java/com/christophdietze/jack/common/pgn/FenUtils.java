package com.christophdietze.jack.common.pgn;

/**
 * Plain: rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1 URL encoded:
 * rnbqkbnr_pppppppp_8_8_8_8_PPPPPPPP_RNBQKBNR.w.KQkq.-.0.1
 */
public class FenUtils {

	public static String toUrlParameterString(String fenString) {
		return fenString.replace(' ', '.').replace('/', '_');
	}

	public static String fromUrlParameterString(String urlFenString) {
		return urlFenString.replace('_', '/').replace('.', ' ');
	}
}
