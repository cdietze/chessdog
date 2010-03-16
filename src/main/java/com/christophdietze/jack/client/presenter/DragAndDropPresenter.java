package com.christophdietze.jack.client.presenter;

import com.christophdietze.jack.client.GameManager;
import com.christophdietze.jack.client.event.GameUpdatedEvent;
import com.christophdietze.jack.client.event.GameUpdatedEventHandler;
import com.christophdietze.jack.client.event.PromotionMoveInitiatedEvent;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.christophdietze.jack.common.board.Game;
import com.christophdietze.jack.common.board.IllegalMoveException;
import com.christophdietze.jack.common.board.Move;
import com.christophdietze.jack.common.board.MoveChecker;
import com.christophdietze.jack.common.board.MoveUtil;
import com.christophdietze.jack.common.board.PieceType;
import com.christophdietze.jack.common.board.Position;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DragAndDropPresenter {

	public static interface View {
		public void update();
	}

	@Inject
	private Game game;
	@Inject
	private GameManager gameManager;

	private GlobalEventBus eventBus;
	private View view;

	@Inject
	public DragAndDropPresenter(GlobalEventBus eventBus) {
		this.eventBus = eventBus;
		initListeners();
	}

	public void setView(View view) {
		assert this.view == null;
		this.view = view;
	}

	public Game getGame() {
		return game;
	}

	public void movePiece(int fromIndex, int toIndex) {
		Position position = game.getPosition();
		Move move = new Move(fromIndex, toIndex);
		if (MoveUtil.isPseudoPromotionMove(move, position)) {
			Move pretendedPromoMove = new Move(fromIndex, toIndex, PieceType.QUEEN);
			if (MoveChecker.isLegalMove(pretendedPromoMove, position)) {
				eventBus.fireEvent(new PromotionMoveInitiatedEvent(fromIndex, toIndex));
			}
		} else {
			try {
				gameManager.makeMoveVerified(move);
			} catch (IllegalMoveException ex) {
			}
		}
	}

	private void initListeners() {
		eventBus.addHandler(GameUpdatedEvent.TYPE, new GameUpdatedEventHandler() {
			@Override
			public void onUpdate(GameUpdatedEvent event) {
				view.update();
			}
		});
	}
}
