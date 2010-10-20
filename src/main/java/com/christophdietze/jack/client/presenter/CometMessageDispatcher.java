package com.christophdietze.jack.client.presenter;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.event.ChallengeRevokedEvent;
import com.christophdietze.jack.client.event.ChallengeReceivedEvent;
import com.christophdietze.jack.client.event.GameUpdatedEvent;
import com.christophdietze.jack.client.event.LogMessageEvent;
import com.christophdietze.jack.client.event.MatchEndedEvent;
import com.christophdietze.jack.client.event.MatchEndedEvent.Reason;
import com.christophdietze.jack.client.event.MatchStartedEvent;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.christophdietze.jack.client.util.MyAsyncCallback;
import com.christophdietze.jack.shared.ChallengeDeclinedCometMessage;
import com.christophdietze.jack.shared.ChallengeReceivedCometMessage;
import com.christophdietze.jack.shared.ChallengeRevokedCometMessage;
import com.christophdietze.jack.shared.ChessService.ChallengeCancellationReason;
import com.christophdietze.jack.shared.ChessServiceAsync;
import com.christophdietze.jack.shared.CometMessage;
import com.christophdietze.jack.shared.MatchAbortedChannelMessage;
import com.christophdietze.jack.shared.MatchStartedCometMessage;
import com.christophdietze.jack.shared.MoveMadeCometMessage;
import com.christophdietze.jack.shared.board.ChessUtils;
import com.christophdietze.jack.shared.board.Game;
import com.christophdietze.jack.shared.board.IllegalMoveException;
import com.christophdietze.jack.shared.board.Move;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class CometMessageDispatcher {

	@Inject
	private ApplicationContext applicationContext;
	@Inject
	private Game game;
	@Inject
	private GameManager gameManager;
	@Inject
	private GlobalEventBus eventBus;
	@Inject
	private ChessServiceAsync chessService;

	public void dispatch(CometMessage message) {
		if (message instanceof MoveMadeCometMessage) {
			onMoveMade((MoveMadeCometMessage) message);
		} else if (message instanceof MatchStartedCometMessage) {
			onMatchStarted((MatchStartedCometMessage) message);
		} else if (message instanceof MatchAbortedChannelMessage) {
			onMatchAborted((MatchAbortedChannelMessage) message);
		} else if (message instanceof ChallengeReceivedCometMessage) {
			onChallengeReceived((ChallengeReceivedCometMessage) message);
		} else if (message instanceof ChallengeDeclinedCometMessage) {
			onChallengeDeclined((ChallengeDeclinedCometMessage) message);
		} else if (message instanceof ChallengeRevokedCometMessage) {
			onChallengeRevoked((ChallengeRevokedCometMessage) message);
		} else {
			throw new AssertionError("unknown comet message: " + message);
		}
	}

	private void onMoveMade(MoveMadeCometMessage message) {
		Log.info("received move: " + message);
		Move move = ChessUtils.toMoveFromAlgebraic(message.getAlgebraicMove());
		try {
			game.makeMoveVerified(move);
		} catch (IllegalMoveException ex) {
			// TODO act more fault tolerant ...
			throw new RuntimeException(ex);
		}
	}

	private void onMatchStarted(MatchStartedCometMessage message) {
		// private void onMatchStarted(MatchStartedRemoteEvent event) {
		Log.info("Match received: " + message);
		game.setupStartingPosition();
		long myUserId = applicationContext.getLocationId();
		if (!Lists.newArrayList(message.getWhitePlayer().getLocationId(), message.getBlackPlayer().getLocationId())
				.contains(myUserId)) {
			throw new RuntimeException("Received " + message + ", but I (User[" + myUserId
					+ "]) am not playing in this match.");
		}
		applicationContext.setAvailableForChallenges(false);
		boolean isPlayerWhite = applicationContext.getLocationId() == message.getWhitePlayer().getLocationId();
		game.setWhiteAtBottom(isPlayerWhite);
		MatchInfo matchInfo = new MatchInfo(message.getWhitePlayer(), message.getBlackPlayer(), isPlayerWhite);
		gameManager.switchToMatchMode(matchInfo);
		eventBus.fireEvent(new MatchStartedEvent(matchInfo));
		// A GameUpdatedEvent is already sent when the starting position is set up, but the direction of the board might
		// have flipped now. This is of interest for e.g., Drag n Drop
		eventBus.fireEvent(new GameUpdatedEvent());
	}

	private void onMatchAborted(MatchAbortedChannelMessage message) {
		Log.info("The other player aborted the match");
		applicationContext.setAvailableForChallenges(true);
		gameManager.switchToAnalysisMode();
		eventBus.fireEvent(new MatchEndedEvent(Reason.OPPONENT_ABORTED));
	}

	private void onChallengeReceived(ChallengeReceivedCometMessage message) {
		if (applicationContext.isAvailableForChallenges()) {
			eventBus.fireEvent(new ChallengeReceivedEvent(message.getChallengeId(), message.getChallenger()));
		} else {
			chessService.declineChallenge(applicationContext.getLocationId(), message.getChallengeId(),
					ChallengeCancellationReason.BUSY, MyAsyncCallback.<Void> doNothing());
		}
	}

	private void onChallengeDeclined(ChallengeDeclinedCometMessage message) {
		// TODO store the posted challenges so that i can find out the name of the opponent
		eventBus.fireEvent(new LogMessageEvent("Challenge declined by opponent ???, reason is " + message.getReason()));
	}

	private void onChallengeRevoked(ChallengeRevokedCometMessage message) {
		eventBus.fireEvent(new ChallengeRevokedEvent(message.getChallengeId()));
	}
}
