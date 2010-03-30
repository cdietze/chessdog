package com.christophdietze.jack.client.presenter;

import com.christophdietze.jack.client.event.PromotionMoveInitiatedEvent;
import com.christophdietze.jack.client.util.GlobalEventBus;
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
public class AnalysisMode extends GameMode {

	@Inject
	private Game game;

	@Inject
	private GlobalEventBus eventBus;

	@Override
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

	@Override
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
