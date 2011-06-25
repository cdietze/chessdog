package com.christophdietze.jack.client.presenter;

import com.christophdietze.jack.client.event.GameUpdatedEvent;
import com.christophdietze.jack.client.event.GameUpdatedEventHandler;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.christophdietze.jack.shared.board.Game;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DragAndDropPresenter {

	public static interface View {
		public void update();
		public void setEnabled(boolean enabled);
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

	public void bindView(View view) {
		assert this.view == null;
		this.view = view;
	}

	public Game getGame() {
		return game;
	}

	public void makeMove(int fromIndex, int toIndex) {
		gameManager.makeMove(fromIndex, toIndex);
	}

	public void setEnabled(boolean enabled) {
		view.setEnabled(enabled);
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
