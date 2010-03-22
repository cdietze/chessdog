package com.christophdietze.jack.client.remote;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.common.MatchAbortedEvent;
import com.christophdietze.jack.common.MatchCreatedEvent;
import com.christophdietze.jack.common.MoveMadeEvent;
import com.christophdietze.jack.common.RemoteEvent;
import com.christophdietze.jack.common.board.ChessUtils;
import com.christophdietze.jack.common.board.Game;
import com.christophdietze.jack.common.board.IllegalMoveException;
import com.christophdietze.jack.common.board.Move;
import com.google.inject.Inject;

public class ChessServiceCallback {

	@Inject
	private Game game;

	public void dispatchEvent(RemoteEvent event) {
		if (event instanceof MatchCreatedEvent) {
			onMatchFound((MatchCreatedEvent) event);
		} else if (event instanceof MoveMadeEvent) {
			onMove((MoveMadeEvent) event);
		} else if (event instanceof MatchAbortedEvent) {
			onMatchAborted((MatchAbortedEvent) event);
		} else {
			throw new AssertionError("unknown remote event: " + event);
		}
	}

	private void onMatchFound(MatchCreatedEvent event) {
		Log.info("Match received: " + event);
		game.setupStartingPosition();
	}

	private void onMove(MoveMadeEvent event) {
		Log.info("received move: " + event);
		Move move = ChessUtils.toMoveFromAlgebraic(event.getAlgebraicMove());
		try {
			game.makeMoveVerified(move);
		} catch (IllegalMoveException ex) {
			// TODO act more fault tolerant ...
			throw new RuntimeException(ex);
		}
	}

	private void onMatchAborted(MatchAbortedEvent event) {
		Log.info("The other player aborted the match");
	}
}
