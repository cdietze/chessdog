package com.christophdietze.jack.client.presenter;

import com.christophdietze.jack.client.event.GameUpdatedEvent;
import com.christophdietze.jack.client.event.GameUpdatedEventHandler;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.christophdietze.jack.common.board.Game;
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
		gameManager.makeMove(fromIndex, toIndex);
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
