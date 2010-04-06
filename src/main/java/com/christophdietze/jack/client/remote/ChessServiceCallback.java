package com.christophdietze.jack.client.remote;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.event.MatchEndedEvent;
import com.christophdietze.jack.client.event.MatchStartedEvent;
import com.christophdietze.jack.client.presenter.ApplicationContext;
import com.christophdietze.jack.client.presenter.GameModeManager;
import com.christophdietze.jack.client.presenter.MatchInfo;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.christophdietze.jack.common.MatchAbortedRemoteEvent;
import com.christophdietze.jack.common.MatchStartedRemoteEvent;
import com.christophdietze.jack.common.MoveMadeRemoteEvent;
import com.christophdietze.jack.common.RemoteEvent;
import com.christophdietze.jack.common.board.ChessUtils;
import com.christophdietze.jack.common.board.Game;
import com.christophdietze.jack.common.board.IllegalMoveException;
import com.christophdietze.jack.common.board.Move;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class ChessServiceCallback {

	@Inject
	private ApplicationContext applicationContext;

	@Inject
	private Game game;

	@Inject
	private GameModeManager gameModeManager;

	@Inject
	private GlobalEventBus eventBus;

	public void dispatchEvent(RemoteEvent event) {
		if (event instanceof MatchStartedRemoteEvent) {
			onMatchStarted((MatchStartedRemoteEvent) event);
		} else if (event instanceof MoveMadeRemoteEvent) {
			onMove((MoveMadeRemoteEvent) event);
		} else if (event instanceof MatchAbortedRemoteEvent) {
			onMatchAborted((MatchAbortedRemoteEvent) event);
		} else {
			throw new AssertionError("unknown remote event: " + event);
		}
	}

	private void onMatchStarted(MatchStartedRemoteEvent event) {
		Log.info("Match received: " + event);
		game.setupStartingPosition();
		long myUserId = applicationContext.getLocationId();
		if (!Lists.newArrayList(event.getWhitePlayerId(), event.getBlackPlayerId()).contains(myUserId)) {
			throw new RuntimeException("Received " + event + ", but I (User[" + myUserId
					+ "]) am not playing in this match.");
		}
		MatchInfo matchInfo = new MatchInfo(event.getWhitePlayerId(), event.getBlackPlayerId());
		gameModeManager.activateMatchMode(matchInfo);
		eventBus.fireEvent(new MatchStartedEvent());
	}

	private void onMove(MoveMadeRemoteEvent event) {
		Log.info("received move: " + event);
		Move move = ChessUtils.toMoveFromAlgebraic(event.getAlgebraicMove());
		try {
			game.makeMoveVerified(move);
		} catch (IllegalMoveException ex) {
			// TODO act more fault tolerant ...
			throw new RuntimeException(ex);
		}
	}

	private void onMatchAborted(MatchAbortedRemoteEvent event) {
		Log.info("The other player aborted the match");
		gameModeManager.activateAnalysisMode();
		eventBus.fireEvent(new MatchEndedEvent());
	}
}
