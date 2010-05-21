package com.christophdietze.jack.client;

import com.christophdietze.jack.client.util.ClientConstants;
import com.christophdietze.jack.shared.board.Game;
import com.christophdietze.jack.shared.board.IllegalPositionException;
import com.christophdietze.jack.shared.pgn.FenParsingException;
import com.christophdietze.jack.shared.pgn.ImportUtils;
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
