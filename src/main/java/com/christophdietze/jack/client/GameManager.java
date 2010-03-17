package com.christophdietze.jack.client;

import com.christophdietze.jack.client.util.MyAsyncCallback;
import com.christophdietze.jack.common.ChessServiceAsync;
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
		// Character promoSymbol = move.getPromotionPiece() == null ? null : move.getPromotionPiece().getSymbol();
		// chessService.makeMove(move.getFrom(), move.getTo(), promoSymbol, MyAsyncCallback.<Void> doNothing());
		chessService.makeMove(algebraicMove, MyAsyncCallback.<Void> doNothing());
	}
}
