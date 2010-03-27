package com.christophdietze.jack.client.presenter;

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
	private GameModeManager gameModeManager;

	public void makeMoveVerified(Move move) throws IllegalMoveException {
		gameModeManager.get().onBeforeMove(move, game);

		game.makeMoveVerified(move);

		gameModeManager.get().onAfterMove(move, game);
	}
}
