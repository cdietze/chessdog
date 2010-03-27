package com.christophdietze.jack.client.presenter;

import com.christophdietze.jack.common.board.Game;
import com.christophdietze.jack.common.board.IllegalMoveException;
import com.christophdietze.jack.common.board.Move;

public abstract class GameMode {

	protected void onBeforeMove(Move move, Game game) throws IllegalMoveException {
	}

	protected void onAfterMove(Move move, Game game) {
	}

}
