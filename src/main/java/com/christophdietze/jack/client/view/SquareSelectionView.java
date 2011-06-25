package com.christophdietze.jack.client.view;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.event.GameUpdatedEvent;
import com.christophdietze.jack.client.event.GameUpdatedEventHandler;
import com.christophdietze.jack.client.presenter.GameManager;
import com.christophdietze.jack.client.resources.MyClientBundle;
import com.christophdietze.jack.client.resources.MyCss;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.christophdietze.jack.shared.board.Game;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class SquareSelectionView {

	private static MyCss CSS = MyClientBundle.CSS;

	static {
		CSS.ensureInjected();
	}

	private Game game;
	private GameManager gameManager;
	private GlobalEventBus eventBus;
	private BoardPanel boardPanel;
	private FlowPanel rootPanel = new FlowPanel();
	private Image squareSelection = new Image(MyClientBundle.INSTANCE.squareSelection());
	private int selectedSquareIndex = -1; // -1 means that no square is selected
	private boolean enabled = true;

	@Inject
	public SquareSelectionView(Game game, GameManager gameManager, GlobalEventBus eventBus, BoardPanel boardPanel) {
		this.game = game;
		this.gameManager = gameManager;
		this.eventBus = eventBus;
		this.boardPanel = boardPanel;
		squareSelection.unsinkEvents(Event.MOUSEEVENTS);
		rootPanel.add(squareSelection);
		boardPanel.addWidget(rootPanel);
		squareSelection.addStyleName(CSS.squareSelection());
		squareSelection.setVisible(false);
		initSquareSelection();
		initListeners();
		Log.debug(this.getClass() + " initialized");
	}

	public void setEnabled(boolean enabled) {
		clearSelection();
		this.enabled = enabled;
	}

	private void initSquareSelection() {
		for (int i = 0; i < 64; ++i) {
			final int index = i;
			final BoardSquare square = boardPanel.getSquares()[index];
			square.getImage().addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (!enabled) {
						return;
					}
					if (selectedSquareIndex < 0) {
						selectedSquareIndex = index;
						squareSelection.getElement().getStyle()
								.setLeft(square.getAbsoluteLeft() - rootPanel.getAbsoluteLeft(), Unit.PX);
						squareSelection.getElement().getStyle()
								.setTop(square.getAbsoluteTop() - rootPanel.getAbsoluteTop(), Unit.PX);
						squareSelection.setVisible(true);
					} else {
						int fromIndex = selectedSquareIndex;
						int toIndex = index;
						if (!game.isWhiteAtBottom()) {
							fromIndex = 63 - fromIndex;
							toIndex = 63 - toIndex;
						}
						selectedSquareIndex = -1;
						squareSelection.setVisible(false);
						gameManager.makeMove(fromIndex, toIndex);
					}
				}
			});
		}
	}

	private void clearSelection() {
		selectedSquareIndex = -1;
		squareSelection.setVisible(false);
	}

	private void initListeners() {
		eventBus.addHandler(GameUpdatedEvent.TYPE, new GameUpdatedEventHandler() {
			@Override
			public void onUpdate(GameUpdatedEvent event) {
				clearSelection();
			}
		});
	}
}
