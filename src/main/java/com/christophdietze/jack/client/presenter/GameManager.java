package com.christophdietze.jack.client.presenter;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.event.MatchEndedEvent;
import com.christophdietze.jack.client.event.PromotionMoveInitiatedEvent;
import com.christophdietze.jack.client.event.MatchEndedEvent.Reason;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.christophdietze.jack.client.util.MyAsyncCallback;
import com.christophdietze.jack.shared.ChessServiceAsync;
import com.christophdietze.jack.shared.MakeMoveResponse;
import com.christophdietze.jack.shared.board.ChessUtils;
import com.christophdietze.jack.shared.board.Game;
import com.christophdietze.jack.shared.board.IllegalMoveException;
import com.christophdietze.jack.shared.board.Move;
import com.christophdietze.jack.shared.board.MoveChecker;
import com.christophdietze.jack.shared.board.PieceType;
import com.christophdietze.jack.shared.board.Position;
import com.christophdietze.jack.shared.board.PositionUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class GameManager {

	private GlobalEventBus eventBus;
	private Game game;
	private ChessServiceAsync chessService;
	private ApplicationContext applicationContext;

	private MatchInfo matchInfo;
	private GameMode currentMode = GameMode.ANALYSIS_MODE;

	@Inject
	public GameManager(GlobalEventBus eventBus, Game game, ChessServiceAsync chessService,
			ApplicationContext applicationContext) {
		this.eventBus = eventBus;
		this.game = game;
		this.chessService = chessService;
		this.applicationContext = applicationContext;
	}

	public GameMode getCurrentMode() {
		return currentMode;
	}

	/**
	 * @return The current match info or null when not in match mode.
	 */
	public MatchInfo getMatchInfo() {
		return matchInfo;
	}

	public void switchToAnalysisMode() {
		matchInfo = null;
		this.currentMode = GameMode.ANALYSIS_MODE;
	}

	public void switchToMatchMode(MatchInfo matchInfo) {
		this.matchInfo = matchInfo;
		this.currentMode = GameMode.MATCH_MODE;
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
				case SUCCESS:
					break;
				case NO_ACTIVE_MATCH:
					Log.warn("Found no active match, probably the opponent has just aborted the match");
					switchToAnalysisMode();
					eventBus.fireEvent(new MatchEndedEvent(Reason.UNEXPECTED_ERROR));
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
