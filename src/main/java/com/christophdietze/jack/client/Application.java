package com.christophdietze.jack.client;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.remote.RemotePoller;
import com.christophdietze.jack.client.util.ClientConstants;
import com.christophdietze.jack.client.view.MainView;
import com.christophdietze.jack.common.board.Game;
import com.christophdietze.jack.common.board.IllegalPositionException;
import com.christophdietze.jack.common.pgn.FenParsingException;
import com.christophdietze.jack.common.pgn.ImportUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;

public class Application {

	@Inject
	public Application(MainView mainView, Game game, RemotePoller remotePoller) {
		String elementId = ClientConstants.DEFAULT_ROOT_ID;
		Log.debug("Creating application at element " + elementId);
		RootPanel rootPanel = RootPanel.get(elementId);
		if (rootPanel == null) {
			throw new IllegalArgumentException("No element with id " + elementId + " found.");
		}
		rootPanel.getElement().setInnerHTML("");

		mainView.buildView(rootPanel);

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
