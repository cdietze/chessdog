package com.christophdietze.jack.client.presenter;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.event.PromotionMoveCancelledEvent;
import com.christophdietze.jack.client.event.PromotionMoveInitiatedEvent;
import com.christophdietze.jack.client.event.PromotionMoveInitiatedEventHandler;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.christophdietze.jack.common.board.IllegalMoveException;
import com.christophdietze.jack.common.board.Move;
import com.christophdietze.jack.common.board.PieceType;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * <p>
 * <img src='doc-files/PromotionMoveEvent.jpg'>
 * </p>
 */
@Singleton
public class PromotionPresenter {

	public static interface View {
		public void showPopup(int toIndex);
	}

	@Inject
	private GameManager gameManager;

	private View view;
	private GlobalEventBus eventBus;
	private int moveFrom;
	private int moveTo;

	@Inject
	public PromotionPresenter(GlobalEventBus eventBus) {
		invalidateMove();
		this.eventBus = eventBus;
		initListeners();
	}

	public void setView(View view) {
		assert this.view == null;
		this.view = view;
	}

	public void onPieceSelected(PieceType piece) {
		assert piece != null;
		Log.debug("Promotion Piece selected: " + piece);
		Move actualMove = new Move(moveFrom, moveTo, piece);
		try {
			gameManager.makeMoveVerified(actualMove);
		} catch (IllegalMoveException ex) {
			throw (AssertionError) (new AssertionError("Illegal promotion move should have been discarded earlier")
					.initCause(ex));
		}
		invalidateMove();
	}
	public void onNoPieceSelected() {
		invalidateMove();
		eventBus.fireEvent(new PromotionMoveCancelledEvent());
	}
	private void initListeners() {
		eventBus.addHandler(PromotionMoveInitiatedEvent.TYPE, new PromotionMoveInitiatedEventHandler() {
			@Override
			public void onEvent(PromotionMoveInitiatedEvent event) {
				moveFrom = event.getFromIndex();
				moveTo = event.getToIndex();
				view.showPopup(event.getToIndex());
			}
		});
	}

	private void invalidateMove() {
		moveFrom = moveTo = -1;
	}
}
