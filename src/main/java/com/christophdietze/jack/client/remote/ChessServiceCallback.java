package com.christophdietze.jack.client.remote;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.presenter.AnalysisMode;
import com.christophdietze.jack.client.presenter.ApplicationContext;
import com.christophdietze.jack.client.presenter.GameModeManager;
import com.christophdietze.jack.client.presenter.MatchMode;
import com.christophdietze.jack.common.MatchAbortedEvent;
import com.christophdietze.jack.common.MatchStartedEvent;
import com.christophdietze.jack.common.MoveMadeEvent;
import com.christophdietze.jack.common.RemoteEvent;
import com.christophdietze.jack.common.board.ChessUtils;
import com.christophdietze.jack.common.board.Game;
import com.christophdietze.jack.common.board.IllegalMoveException;
import com.christophdietze.jack.common.board.Move;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class ChessServiceCallback {

	@Inject
	private ApplicationContext applicationContext;

	@Inject
	private Game game;

	@Inject
	private GameModeManager gameModeManager;

	@Inject
	private Provider<MatchMode.Builder> matchModeProvider;

	public void dispatchEvent(RemoteEvent event) {
		if (event instanceof MatchStartedEvent) {
			onMatchStarted((MatchStartedEvent) event);
		} else if (event instanceof MoveMadeEvent) {
			onMove((MoveMadeEvent) event);
		} else if (event instanceof MatchAbortedEvent) {
			onMatchAborted((MatchAbortedEvent) event);
		} else {
			throw new AssertionError("unknown remote event: " + event);
		}
	}

	private void onMatchStarted(MatchStartedEvent event) {
		Log.info("Match received: " + event);
		game.setupStartingPosition();
		long myUserId = applicationContext.getMyUserId();
		if (!Lists.newArrayList(event.getWhitePlayerId(), event.getBlackPlayerId()).contains(myUserId)) {
			throw new RuntimeException("Received " + event + ", but I (User[" + myUserId
					+ "]) am not playing in this match.");
		}
		boolean playerIsWhite = (myUserId == event.getWhitePlayerId());
		// gameModeManager.setCurrentMode(new MatchMode(playerIsWhite));
		gameModeManager.setCurrentMode(matchModeProvider.get().playerIsWhite(playerIsWhite).build());
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
		gameModeManager.setCurrentMode(AnalysisMode.INSTANCE);
	}
}
