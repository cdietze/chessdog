package com.christophdietze.jack.client.presenter;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.util.MyAsyncCallback;
import com.christophdietze.jack.common.ChessServiceAsync;
import com.christophdietze.jack.common.MakeMoveResponse;
import com.christophdietze.jack.common.board.ChessUtils;
import com.christophdietze.jack.common.board.Game;
import com.christophdietze.jack.common.board.IllegalMoveException;
import com.christophdietze.jack.common.board.Move;
import com.google.inject.Inject;

public class MatchMode extends GameMode {

	@Inject
	ChessServiceAsync chessService;

	private boolean playerIsWhite;

	public static class Builder {
		@Inject
		private MatchMode o;

		public Builder playerIsWhite(boolean playerIsWhite) {
			o.playerIsWhite = playerIsWhite;
			return this;
		}

		public GameMode build() {
			return o;
		}
	}

	@Inject
	private MatchMode() {
	}

	@Override
	protected void onBeforeMove(Move move, Game game) throws IllegalMoveException {
		boolean isWhiteMove = game.getPosition().getPiece(move.getFrom()).isWhite();
		if (isWhiteMove != playerIsWhite) {
			throw new IllegalMoveException();
		}
	}

	@Override
	protected void onAfterMove(Move move, Game game) {
		String algebraicMove = ChessUtils.toAlgebraicMove(move);
		chessService.makeMove(algebraicMove, new MyAsyncCallback<MakeMoveResponse>() {
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
}
