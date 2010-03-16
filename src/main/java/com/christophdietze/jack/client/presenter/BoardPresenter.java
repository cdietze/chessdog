package com.christophdietze.jack.client.presenter;

import com.christophdietze.jack.client.event.GameUpdatedEvent;
import com.christophdietze.jack.client.event.GameUpdatedEventHandler;
import com.christophdietze.jack.client.event.PromotionMoveCancelledEvent;
import com.christophdietze.jack.client.event.PromotionMoveCancelledEventHandler;
import com.christophdietze.jack.client.event.PromotionMoveInitiatedEvent;
import com.christophdietze.jack.client.event.PromotionMoveInitiatedEventHandler;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.christophdietze.jack.common.board.Game;
import com.christophdietze.jack.common.board.Move;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class BoardPresenter {

	public static interface View {
		public void update();
		public void showPromotionPawn(int from, int to);
	}

	private View view;
	private Game game;
	// private PromotionModel promotionModel;
	private GlobalEventBus eventBus;
	private Move promotionMove;

	@Inject
	public BoardPresenter(GlobalEventBus eventBus, Game game) {
		this.eventBus = eventBus;
		// this.promotionModel = promotionModel;
		this.game = game;
		initListeners();
	}

	public void setView(View view) {
		assert this.view == null;
		this.view = view;
	}

	public Game getGame() {
		return game;
	}

	public Move getPromotionMove() {
		return promotionMove;
	}

	private void initListeners() {
		eventBus.addHandler(GameUpdatedEvent.TYPE, new GameUpdatedEventHandler() {
			@Override
			public void onUpdate(GameUpdatedEvent event) {
				view.update();
			}
		});
		eventBus.addHandler(PromotionMoveInitiatedEvent.TYPE, new PromotionMoveInitiatedEventHandler() {
			@Override
			public void onEvent(PromotionMoveInitiatedEvent event) {
				view.showPromotionPawn(event.getFromIndex(), event.getToIndex());
			}
		});
		eventBus.addHandler(PromotionMoveCancelledEvent.TYPE, new PromotionMoveCancelledEventHandler() {
			@Override
			public void onEvent(PromotionMoveCancelledEvent event) {
				view.update();
			}
		});
	}
}
