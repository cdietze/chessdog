package com.christophdietze.jack.shared.pgn;

import java.util.Map;

import com.christophdietze.jack.shared.board.Game;
import com.christophdietze.jack.shared.board.Move;

public class PgnUtils {

	public static String gameToPgn(Game game) throws PgnWritingException {
		PgnWriter writer = new PgnWriter();
		return writer.write(game);
	}

	public static void importPgnString(Game game, String pgnString) throws PgnParsingException {
		PgnParser parser = new PgnParser();
		parser.parse(pgnString);

		game.clear();

		game.setInitialPosition(parser.getInitialPosition());
		for (Move move : parser.getMoves()) {
			game.addMove(move);
		}
		game.gotoFirstPosition();

		game.getMetaInfo().setWhitePlayerName(parser.getWhitePlayerName());
		game.getMetaInfo().setBlackPlayerName(parser.getBlackPlayerName());
		if (parser.getGameResult() != null) {
			game.setGameResult(parser.getGameResult());
		}
		for (Map.Entry<String, String> entry : parser.getAdditionalTags().entrySet()) {
			game.getMetaInfo().addTag(entry.getKey(), entry.getValue());
		}
	}
}
