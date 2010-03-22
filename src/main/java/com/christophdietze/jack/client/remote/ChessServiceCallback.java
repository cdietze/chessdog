package com.christophdietze.jack.client.remote;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.common.MoveMadeEvent;
import com.christophdietze.jack.common.board.ChessUtils;
import com.christophdietze.jack.common.board.Game;
import com.christophdietze.jack.common.board.IllegalMoveException;
import com.christophdietze.jack.common.board.Move;
import com.google.inject.Inject;

public class ChessServiceCallback {

	@Inject
	private Game game;

	public void onMatchFound() {
		Log.info("Match received");
		game.setupStartingPosition();
	}

	public void onMove(MoveMadeEvent event) {
		Log.info("received move: " + event);
		Move move = ChessUtils.toMoveFromAlgebraic(event.getAlgebraicMove());
		try {
			game.makeMoveVerified(move);
		} catch (IllegalMoveException ex) {
			// TODO act more fault tolerant ...
			throw new RuntimeException(ex);
		}
	}
}
