package com.christophdietze.jack.client.presenter;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.event.PromotionMoveInitiatedEvent;
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
public class MatchMode extends GameMode {

	@Inject
	private ApplicationContext applicationContext;

	@Inject
	private Game game;

	@Inject
	private GlobalEventBus eventBus;

	@Inject
	private JackServiceAsync chessService;

	private MatchInfo matchInfo;

	private void onBeforeMove(Move move, Game game) throws IllegalMoveException {
		boolean isWhiteMove = game.getPosition().getPiece(move.getFrom()).isWhite();
		if (isWhiteMove != matchInfo.isPlayerWhite()) {
			throw new IllegalMoveException();
		}
	}

	private void sendMoveToServer(final Move move, final Game game) {
		String algebraicMove = ChessUtils.toAlgebraicMove(move);
		chessService.makeMove(applicationContext.getLocationId(), algebraicMove, new MyAsyncCallback<MakeMoveResponse>() {
			@Override
			public void onSuccess(MakeMoveResponse result) {
				switch (result) {
				case OK:
					try {
						game.makeMoveVerified(move);
					} catch (IllegalMoveException ex) {
						throw new RuntimeException("move was checked before, server says ok, why is this move not ok now?!",
								ex);
					}
					break;
				case NO_ACTIVE_MATCH:
					Log.error("Found no active match");
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

	public void activate(MatchInfo matchInfo) {
		this.matchInfo = matchInfo;
	}

	@Override
	public void deactivate() {
		this.matchInfo = null;
	}
	//
	// public boolean isPlayerWhite() {
	// return applicationContext.getLocationId() == matchInfo.getWhitePlayerId();
	// }
	//
	// public long getOpponentId() {
	// return isPlayerWhite() ? matchInfo.getBlackPlayerId() : matchInfo.getWhitePlayerId();
	// }
	//
	// public long getWhitePlayerId() {
	// return matchInfo.getWhitePlayerId();
	// }
	//
	// public long getBlackPlayerId() {
	// return matchInfo.getBlackPlayerId();
	// }

	public MatchInfo getMatchInfo() {
		return matchInfo;
	}

	@Override
	public void makeMove(int fromIndex, int toIndex) {
		Position position = game.getPosition();
		Move move = new Move(fromIndex, toIndex);
		if (PositionUtils.isPseudoPromotionMove(position, move)) {
			Move pretendedPromoMove = new Move(fromIndex, toIndex, PieceType.QUEEN);
			if (MoveChecker.isLegalMove(position, pretendedPromoMove)) {
				if (position.getPiece(fromIndex).isWhite() != matchInfo.isPlayerWhite()) {
					return;
				}
				eventBus.fireEvent(new PromotionMoveInitiatedEvent(fromIndex, toIndex));
			}
		} else {
			try {
				onBeforeMove(move, game);
				sendMoveToServer(move, game);
			} catch (IllegalMoveException ex) {
			}
		}
	}

	@Override
	public void makePromotionMove(int fromIndex, int toIndex, PieceType promotionPiece) {
		Move move = new Move(fromIndex, toIndex, promotionPiece);
		try {
			game.makeMoveVerified(move);
			sendMoveToServer(move, game);
		} catch (IllegalMoveException ex) {
			throw (AssertionError) (new AssertionError("Illegal promotion move should have been discarded earlier")
					.initCause(ex));
		}
	}
}
