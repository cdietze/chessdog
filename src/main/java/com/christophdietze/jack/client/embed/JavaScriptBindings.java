package com.christophdietze.jack.client.embed;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.shared.board.Game;
import com.christophdietze.jack.shared.pgn.FenWriter;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class JavaScriptBindings {

	private static JavaScriptBindings INSTANCE;

	private Game game;

	@Inject
	public JavaScriptBindings(Game game) {
		assert INSTANCE == null;
		INSTANCE = this;
		this.game = game;
		exportStuff();
		Log.debug("JavaScript Bindings initialized.");
	}

	private static native void exportStuff() /*-{
		$wnd.getFen = $entry(@com.christophdietze.jack.client.embed.JavaScriptBindings::getGameAsFen());
	}-*/;

	private static String getGameAsFen() {
		return FenWriter.toFen(INSTANCE.game.getPosition());
	}
}
