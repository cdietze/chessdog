package com.christophdietze.jack.client.view;

import com.christophdietze.jack.client.presenter.GameManager;
import com.christophdietze.jack.client.presenter.PostChallengePresenter;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class PostChallengePopup extends PopupPanel {

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, PostChallengePopup> {
	}

	private PostChallengePresenter presenter;

	private GameManager gameManager;
	private GlobalEventBus eventBus;

	@UiField
	TextBox opponentNickTextBox;
	@UiField
	Button postPlayerChallengeButton;
	// @UiField
	// Button postPublicChallengeButton;

	@Inject
	public PostChallengePopup(PostChallengePresenter presenter, GameManager gameManager, GlobalEventBus eventBus) {
		super(true);
		this.presenter = presenter;
		this.gameManager = gameManager;
		this.eventBus = eventBus;
		setWidget(uiBinder.createAndBindUi(this));
		// signInRunningPanel.setVisible(false);
		// presenter.bindView(this);
	}

	@UiHandler("postPlayerChallengeButton")
	void handlePostPublicChallengeClick(ClickEvent event) {
		String opponentNick = opponentNickTextBox.getText();
		presenter.onPostPersonalChallenge(opponentNick);
		hide();
	}

}
