package com.christophdietze.jack.client.presenter;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.event.MatchEndedEvent;
import com.christophdietze.jack.client.event.PromotionMoveInitiatedEvent;
import com.christophdietze.jack.client.event.SwitchGameModeEvent;
import com.christophdietze.jack.client.event.SwitchGameModeEventHandler;
import com.christophdietze.jack.client.event.MatchEndedEvent.Reason;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.christophdietze.jack.client.util.MyAsyncCallback;
import com.christophdietze.jack.common.JackServiceAsync;
import com.christophdietze.jack.common.MakeMoveResponse;
import com.christophdietze.jack.common.board.ChessUtils;
import com.christophdietze.jack.common.board.Game;
import com.christophdietze.jack.common.board.IllegalMoveException;
import com.christophdietze.jack.common.board.Move;
import com.christophdietze.jack.common.board.MoveChecker;
import com.christophdietze.jack.common.board.PieceType;
import com.christophdietze.jack.common.board.Position;
import com.christophdietze.jack.common.board.PositionUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class GameManager {

	private GlobalEventBus eventBus;
	private Game game;
	private JackServiceAsync chessService;
	private ApplicationContext applicationContext;

	private MatchInfo matchInfo;
	private GameMode currentMode = GameMode.ANALYSIS_MODE;

	@Inject
	public GameManager(GlobalEventBus eventBus, Game game, JackServiceAsync jackService,
			ApplicationContext applicationContext) {
		this.eventBus = eventBus;
		this.game = game;
		this.chessService = jackService;
		this.applicationContext = applicationContext;
		initListeners();
	}

	public GameMode getCurrentMode() {
		return currentMode;
	}

	private void initListeners() {
		eventBus.addHandler(SwitchGameModeEvent.TYPE, new SwitchGameModeEventHandler() {
			@Override
			public void onSwitchGameMode(SwitchGameModeEvent event) {
				switch (event.getNewMode()) {
				case ANALYSIS_MODE:
					matchInfo = null;
					currentMode = GameMode.ANALYSIS_MODE;
					break;
				case MATCH_MODE:
					matchInfo = event.getMatchInfo();
					currentMode = GameMode.MATCH_MODE;
					break;
				default:
					throw new AssertionError();
				}
			}
		});
	}

	/**
	 * @return The current match info or null when not in match mode.
	 */
	public MatchInfo getMatchInfo() {
		return matchInfo;
	}

	public void makeMove(int fromIndex, int toIndex) {
		Position position = game.getPosition();
		Move move = new Move(fromIndex, toIndex);
		if (PositionUtils.isPseudoPromotionMove(position, move)) {
			Move pretendedPromoMove = new Move(fromIndex, toIndex, PieceType.QUEEN);
			if (MoveChecker.isLegalMove(position, pretendedPromoMove)) {
				if (currentMode == GameMode.MATCH_MODE
						&& position.getPiece(fromIndex).isWhite() != matchInfo.isPlayerWhite()) {
					return;
				}
				eventBus.fireEvent(new PromotionMoveInitiatedEvent(fromIndex, toIndex));
			}
		} else {
			try {
				if (currentMode == GameMode.MATCH_MODE) {
					boolean isWhiteMove = game.getPosition().getPiece(move.getFrom()).isWhite();
					if (isWhiteMove != matchInfo.isPlayerWhite()) {
						throw new IllegalMoveException();
					}
				}
				game.makeMoveVerified(move);
				if (currentMode == GameMode.MATCH_MODE) {
					sendMoveToServer(move, game);
				}
			} catch (IllegalMoveException ex) {
			}
		}
	}

	public void makePromotionMove(int fromIndex, int toIndex, PieceType promotionPiece) {
		Move move = new Move(fromIndex, toIndex, promotionPiece);
		try {
			game.makeMoveVerified(move);
			if (currentMode == GameMode.MATCH_MODE) {
				sendMoveToServer(move, game);
			}
		} catch (IllegalMoveException ex) {
			throw (AssertionError) (new AssertionError("Illegal promotion move should have been discarded earlier")
					.initCause(ex));
		}
	}

	private void sendMoveToServer(final Move move, final Game game) {
		String algebraicMove = ChessUtils.toAlgebraicMove(move);
		chessService.makeMove(applicationContext.getLocationId(), algebraicMove, new MyAsyncCallback<MakeMoveResponse>() {
			@Override
			public void onSuccess(MakeMoveResponse result) {
				switch (result) {
				case OK:
					break;
				case NO_ACTIVE_MATCH:
					Log.warn("Found no active match, probably the opponent has just aborted the match");
					eventBus.fireEvent(SwitchGameModeEvent.newSwitchToAnalysisModeEvent());
					eventBus.fireEvent(new MatchEndedEvent(Reason.OPPONENT_ABORTED));
					break;
				case MOVE_FOR_OPPOSITE_PLAYER:
					Log.error("Server says you tried to make a move for the opponent");
					break;
				case ILLEGAL_MOVE:
					Log.error("Server says that move is illegal");
					break;
				default:
					throw new AssertionError();
				}
			}
		});
	}
}
