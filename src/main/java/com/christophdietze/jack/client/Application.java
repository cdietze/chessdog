package com.christophdietze.jack.client;

import com.christophdietze.jack.client.util.ClientConstants;
import com.christophdietze.jack.common.board.Game;
import com.christophdietze.jack.common.board.IllegalPositionException;
import com.christophdietze.jack.common.pgn.FenParsingException;
import com.christophdietze.jack.common.pgn.ImportUtils;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;

/**
 * TODO remove this class
 */
public class Application {

	@Inject
	public Application(Game game) {
		String fenString = Window.Location.getParameter(ClientConstants.URL_PARAMETER_KEY_FEN);
		if (fenString != null) {
			try {
				ImportUtils.importFenString(game, fenString);
			} catch (FenParsingException ex) {
				throw new RuntimeException(ex);
			} catch (IllegalPositionException ex) {
				throw new RuntimeException(ex);
			}
		}
	}
}
