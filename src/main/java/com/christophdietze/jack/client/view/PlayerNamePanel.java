package com.christophdietze.jack.client.view;

import com.christophdietze.jack.client.event.GameUpdatedEvent;
import com.christophdietze.jack.client.event.GameUpdatedEventHandler;
import com.christophdietze.jack.client.resources.MyClientBundle;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.christophdietze.jack.shared.board.Game;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class PlayerNamePanel extends Composite {

	private static final String WHITE_PLAYER_DEFAULT_NAME = "White player";
	private static final String BLACK_PLAYER_DEFAULT_NAME = "Black player";

	private static MyClientBundle myClientBundle = MyClientBundle.INSTANCE;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, PlayerNamePanel> {
	}

	interface MyStyle extends CssResource {
		String activeSection();
		String inactiveSection();
	}

	private GlobalEventBus eventBus;

	private Game game;

	@UiField
	MyStyle style;
	@UiField
	Label upperPlayerLabel;
	@UiField
	Label lowerPlayerLabel;
	@UiField
	Image upperPlayerIcon;
	@UiField
	Image lowerPlayerIcon;
	@UiField
	HTMLPanel upperSection;
	@UiField
	HTMLPanel lowerSection;

	@Inject
	public PlayerNamePanel(GlobalEventBus eventBus, Game game) {
		this.eventBus = eventBus;
		this.game = game;
		initWidget(uiBinder.createAndBindUi(this));
		upperPlayerLabel.setText(BLACK_PLAYER_DEFAULT_NAME);
		lowerPlayerLabel.setText(WHITE_PLAYER_DEFAULT_NAME);
		upperPlayerIcon.setResource(myClientBundle.blackPlayerIcon());
		lowerPlayerIcon.setResource(myClientBundle.whitePlayerIcon());
		initListeners();
		updatePlayerNameHighlighting();
	}

	private void initListeners() {
		eventBus.addHandler(GameUpdatedEvent.TYPE, new GameUpdatedEventHandler() {
			@Override
			public void onUpdate(GameUpdatedEvent event) {
				update();
			}
		});
	}

	private void update() {
		updatePlayerNames();
		updatePlayerIcons();
		updatePlayerNameHighlighting();
	}

	private void updatePlayerIcons() {
		upperPlayerIcon.setResource(game.isWhiteAtBottom() ? myClientBundle.blackPlayerIcon() : myClientBundle
				.whitePlayerIcon());
		lowerPlayerIcon.setResource(game.isWhiteAtBottom() ? myClientBundle.whitePlayerIcon() : myClientBundle
				.blackPlayerIcon());
	}

	private void updatePlayerNames() {
		Label whitePlayerLabel = game.isWhiteAtBottom() ? lowerPlayerLabel : upperPlayerLabel;
		Label blackPlayerLabel = game.isWhiteAtBottom() ? upperPlayerLabel : lowerPlayerLabel;
		String whiteName = game.getMetaInfo().getWhitePlayerName() != null ? game.getMetaInfo().getWhitePlayerName()
				: WHITE_PLAYER_DEFAULT_NAME;
		String blackName = game.getMetaInfo().getBlackPlayerName() != null ? game.getMetaInfo().getBlackPlayerName()
				: BLACK_PLAYER_DEFAULT_NAME;
		whitePlayerLabel.setText(whiteName);
		blackPlayerLabel.setText(blackName);
	}

	private void updatePlayerNameHighlighting() {
		UIObject activeSection;
		UIObject inactiveSection;
		if (game.getPosition().isWhiteToMove() == game.isWhiteAtBottom()) {
			activeSection = lowerSection;
			inactiveSection = upperSection;
		} else {
			activeSection = upperSection;
			inactiveSection = lowerSection;
		}
		activeSection.addStyleName(style.activeSection());
		activeSection.removeStyleName(style.inactiveSection());
		inactiveSection.addStyleName(style.inactiveSection());
		inactiveSection.removeStyleName(style.activeSection());
	}
}
