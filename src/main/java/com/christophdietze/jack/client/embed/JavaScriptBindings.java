package com.christophdietze.jack.client.embed;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.presenter.DragAndDropPresenter;
import com.christophdietze.jack.client.view.SquareSelectionView;
import com.christophdietze.jack.shared.board.Game;
import com.christophdietze.jack.shared.board.IllegalPositionException;
import com.christophdietze.jack.shared.board.Position;
import com.christophdietze.jack.shared.pgn.FenParsingException;
import com.christophdietze.jack.shared.pgn.FenUtils;
import com.christophdietze.jack.shared.pgn.PgnParsingException;
import com.christophdietze.jack.shared.pgn.PgnUtils;
import com.christophdietze.jack.shared.pgn.PgnWritingException;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class JavaScriptBindings {

	private static JavaScriptBindings INSTANCE;

	private Game game;

	@Inject
	private SquareSelectionView squareSelectionView;

	@Inject
	private DragAndDropPresenter dragAndDropPresenter;

	@Inject
	public JavaScriptBindings(Game game) {
		assert INSTANCE == null;
		INSTANCE = this;
		this.game = game;
		exportStuff();
		Log.debug("JavaScript Bindings initialized.");
	}

	private static native void exportStuff() /*-{
		$wnd.reset = $entry(@com.christophdietze.jack.client.embed.JavaScriptBindings::reset());
		$wnd.getFen = $entry(@com.christophdietze.jack.client.embed.JavaScriptBindings::getFen());
		$wnd.setFen = $entry(@com.christophdietze.jack.client.embed.JavaScriptBindings::setFen(Ljava/lang/String;));
		$wnd.getPgn = $entry(@com.christophdietze.jack.client.embed.JavaScriptBindings::getPgn());
		$wnd.setPgn = $entry(@com.christophdietze.jack.client.embed.JavaScriptBindings::setPgn(Ljava/lang/String;));
		$wnd.allowUserMoves = $entry(@com.christophdietze.jack.client.embed.JavaScriptBindings::allowUserMoves(Z));
		$wnd.chessdogReady = 1;
	}-*/;

	private static void reset() {
		INSTANCE.game.setupStartingPosition();
	}

	private static String getFen() {
		return FenUtils.positionToFen(INSTANCE.game.getPosition());
	}

	private static void setFen(String fen) {
		Position pos;
		try {
			pos = FenUtils.fenToPosition(fen.trim());
		} catch (FenParsingException ex) {
			Window.alert("Failed to parse FEN. " + ex.getMessage());
			return;
		} catch (IllegalPositionException ex) {
			Window.alert(ex.getMessage());
			return;
		}
		INSTANCE.game.setInitialPosition(pos);
	}

	private static String getPgn() {
		try {
			return PgnUtils.gameToPgn(INSTANCE.game);
		} catch (PgnWritingException ex) {
			Window.alert(ex.getMessage());
			return "";
		}
	}

	private static void setPgn(String pgn) {
		try {
			PgnUtils.importPgnString(INSTANCE.game, pgn);
			allowUserMoves(false);
		} catch (PgnParsingException ex) {
			Window.alert(ex.getMessage());
		}
	}

	private static void allowUserMoves(boolean allowed) {
		INSTANCE.dragAndDropPresenter.setEnabled(allowed);
		INSTANCE.squareSelectionView.setEnabled(allowed);
	}
}
