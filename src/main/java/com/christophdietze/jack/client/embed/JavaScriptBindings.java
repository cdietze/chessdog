package com.christophdietze.jack.client.embed;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.shared.board.Game;
import com.christophdietze.jack.shared.board.IllegalPositionException;
import com.christophdietze.jack.shared.board.Position;
import com.christophdietze.jack.shared.pgn.FenParsingException;
import com.christophdietze.jack.shared.pgn.FenUtils;
import com.google.gwt.user.client.Window;
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
		$wnd.resetPosition = $entry(@com.christophdietze.jack.client.embed.JavaScriptBindings::resetPosition());
		$wnd.getFen = $entry(@com.christophdietze.jack.client.embed.JavaScriptBindings::getFen());
		$wnd.setFen = $entry(@com.christophdietze.jack.client.embed.JavaScriptBindings::setFen(Ljava/lang/String;));
	}-*/;

	private static String getFen() {
		return FenUtils.positionToFen(INSTANCE.game.getPosition());
	}

	private static void setFen(String fen) {
		Position pos;
		try {
			pos = FenUtils.fenToPosition(fen.trim());
		} catch (FenParsingException ex) {
			Window.alert("Failed to parse FEN. " + ex.getLocalizedMessage());
			return;
		} catch (IllegalPositionException ex) {
			Window.alert(ex.getLocalizedMessage());
			return;
		}
		INSTANCE.game.setInitialPosition(pos);
	}

	private static void resetPosition() {
		INSTANCE.game.setupStartingPosition();
	}
}
