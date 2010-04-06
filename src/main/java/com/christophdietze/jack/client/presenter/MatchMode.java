package com.christophdietze.jack.client.presenter;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.event.PromotionMoveInitiatedEvent;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.christophdietze.jack.client.util.MyAsyncCallback;
import com.christophdietze.jack.common.ChessServiceAsync;
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
	private ChessServiceAsync chessService;

	private MatchInfo matchInfo;

	public boolean isPlayerWhite() {
		return applicationContext.getLocationId() == matchInfo.getWhitePlayerId();
	}

	private void onBeforeMove(Move move, Game game) throws IllegalMoveException {
		boolean isWhiteMove = game.getPosition().getPiece(move.getFrom()).isWhite();
		if (isWhiteMove != isPlayerWhite()) {
			throw new IllegalMoveException();
		}
	}

	private void sendMoveToServer(Move move, Game game) {
		String algebraicMove = ChessUtils.toAlgebraicMove(move);
		chessService.makeMove(applicationContext.getLocationId(), algebraicMove, new MyAsyncCallback<MakeMoveResponse>() {
			@Override
			public void onSuccess(MakeMoveResponse result) {
				switch (result) {
				case OK:
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

	void activate(MatchInfo matchInfo) {
		this.matchInfo = matchInfo;
	}

	@Override
	void deactivate() {
		this.matchInfo = null;
	}

	public long getOpponentId() {
		return isPlayerWhite() ? matchInfo.getBlackPlayerId() : matchInfo.getWhitePlayerId();
	}

	@Override
	public void makeMove(int fromIndex, int toIndex) {
		Position position = game.getPosition();
		Move move = new Move(fromIndex, toIndex);
		if (PositionUtils.isPseudoPromotionMove(position, move)) {
			Move pretendedPromoMove = new Move(fromIndex, toIndex, PieceType.QUEEN);
			if (MoveChecker.isLegalMove(position, pretendedPromoMove)) {
				if (position.getPiece(fromIndex).isWhite() != isPlayerWhite()) {
					return;
				}
				eventBus.fireEvent(new PromotionMoveInitiatedEvent(fromIndex, toIndex));
			}
		} else {
			try {
				onBeforeMove(move, game);
				game.makeMoveVerified(move);
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