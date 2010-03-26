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
import com.google.inject.Singleton;

@Singleton
public class GameManager {

	@Inject
	private Game game;

	@Inject
	private ChessServiceAsync chessService;

	public void makeMoveVerified(Move move) throws IllegalMoveException {
		game.makeMoveVerified(move);
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
