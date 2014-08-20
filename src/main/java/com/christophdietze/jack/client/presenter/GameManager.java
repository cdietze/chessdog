package com.christophdietze.jack.client.presenter;

import com.christophdietze.jack.client.event.PromotionMoveInitiatedEvent;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.christophdietze.jack.shared.board.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class GameManager {

	private GlobalEventBus eventBus;
	private Game game;

	@Inject
	public GameManager(GlobalEventBus eventBus, Game game) {
		this.eventBus = eventBus;
		this.game = game;
	}

	public void makeMove(int fromIndex, int toIndex) {
		Position position = game.getPosition();
		Move move = new Move(fromIndex, toIndex);
		if (PositionUtils.isPseudoPromotionMove(position, move)) {
			Move pretendedPromoMove = new Move(fromIndex, toIndex, PieceType.QUEEN);
			if (MoveChecker.isLegalMove(position, pretendedPromoMove)) {
				eventBus.fireEvent(new PromotionMoveInitiatedEvent(fromIndex, toIndex));
			}
		} else {
			try {
				game.makeMoveVerified(move);
			} catch (IllegalMoveException ex) {
			}
		}
	}

	public void makePromotionMove(int fromIndex, int toIndex, PieceType promotionPiece) {
		Move move = new Move(fromIndex, toIndex, promotionPiece);
		try {
			game.makeMoveVerified(move);
		} catch (IllegalMoveException ex) {
			throw (AssertionError) (new AssertionError("Illegal promotion move should have been discarded earlier")
					.initCause(ex));
		}
	}
}
