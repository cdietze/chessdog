package com.christophdietze.jack.client.presenter;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.event.PromotionMoveCancelledEvent;
import com.christophdietze.jack.client.event.PromotionMoveInitiatedEvent;
import com.christophdietze.jack.client.event.PromotionMoveInitiatedEventHandler;
import com.christophdietze.jack.client.util.GlobalEventBus;
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

	public interface View {
		public void showPopup(int toIndex);
	}

	@Inject
	private GameModeManager gameModeManager;

	private View view;
	private GlobalEventBus eventBus;
	private int fromIndex;
	private int toIndex;

	@Inject
	public PromotionPresenter(GlobalEventBus eventBus) {
		invalidateTentativeMove();
		this.eventBus = eventBus;
		initListeners();
	}

	public void bindView(View view) {
		assert this.view == null;
		this.view = view;
	}

	public void onPieceSelected(PieceType promotionPiece) {
		assert promotionPiece != null;
		Log.debug("Promotion Piece selected: " + promotionPiece);
		gameModeManager.getCurrentMode().makePromotionMove(fromIndex, toIndex, promotionPiece);
		invalidateTentativeMove();
	}

	public void onNoPieceSelected() {
		invalidateTentativeMove();
		eventBus.fireEvent(new PromotionMoveCancelledEvent());
	}

	private void initListeners() {
		eventBus.addHandler(PromotionMoveInitiatedEvent.TYPE, new PromotionMoveInitiatedEventHandler() {
			@Override
			public void onEvent(PromotionMoveInitiatedEvent event) {
				fromIndex = event.getFromIndex();
				toIndex = event.getToIndex();
				view.showPopup(event.getToIndex());
			}
		});
	}

	private void invalidateTentativeMove() {
		fromIndex = toIndex = -1;
	}
}
