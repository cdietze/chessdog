package com.christophdietze.jack.client.remote;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.event.GameUpdatedEvent;
import com.christophdietze.jack.client.event.MatchEndedEvent;
import com.christophdietze.jack.client.event.MatchStartedEvent;
import com.christophdietze.jack.client.event.MatchEndedEvent.Reason;
import com.christophdietze.jack.client.presenter.ApplicationContext;
import com.christophdietze.jack.client.presenter.GameManager;
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
	private GameManager gameManager;
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
		boolean isPlayerWhite = applicationContext.getLocationId() == event.getWhitePlayerId();
		game.setWhiteAtBottom(isPlayerWhite);
		MatchInfo matchInfo = new MatchInfo(event.getWhitePlayerId(), event.getBlackPlayerId(), isPlayerWhite);
		gameManager.switchToMatchMode(matchInfo);
		eventBus.fireEvent(new MatchStartedEvent(matchInfo));
		// A GameUpdatedEvent is already sent when the starting position is set up, but the direction of the board might
		// have flipped now. This is of interest for e.g., Drag n Drop
		eventBus.fireEvent(new GameUpdatedEvent());
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
		gameManager.switchToAnalysisMode();
		eventBus.fireEvent(new MatchEndedEvent(Reason.OPPONENT_ABORTED));
	}
}
