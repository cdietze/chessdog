package com.christophdietze.jack.client.presenter.embed;

import com.christophdietze.jack.client.event.GameUpdatedEvent;
import com.christophdietze.jack.client.event.GameUpdatedEventHandler;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.christophdietze.jack.shared.board.Game;
import com.google.inject.Inject;

public class MoveNavigationPresenter {

	public static interface View {
		public void update();
	}

	private GlobalEventBus eventBus;
	private Game game;
	private View view;

	@Inject
	public MoveNavigationPresenter(GlobalEventBus eventBus, Game game) {
		this.eventBus = eventBus;
		this.game = game;
		initListeners();
	}

	public void bindView(View view) {
		assert this.view == null;
		this.view = view;
	}

	public Game getGame() {
		return game;
	}

	public void onNavStartClick() {
		game.gotoFirstPosition();
	}

	public void onNavPrevMoveClick() {
		game.gotoPrevMove();
	}

	public void onNavNextMoveClick() {
		game.gotoNextMove();
	}

	public void onNavEndClick() {
		game.gotoLastPosition();
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
