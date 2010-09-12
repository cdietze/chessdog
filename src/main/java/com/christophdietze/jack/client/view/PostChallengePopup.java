package com.christophdietze.jack.client.view;

import com.christophdietze.jack.client.presenter.PostChallengePresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.FocusEvent;
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

	// flag to prevent clearing the text box again when the user has already entered some text
	private boolean opponentNicknameTextBoxAlreadyCleared;

	@UiField
	TextBox opponentNicknameTextBox;
	@UiField
	Button postPlayerChallengeButton;
	// @UiField
	// Button postPublicChallengeButton;

	@Inject
	public PostChallengePopup(PostChallengePresenter presenter) {
		super(true);
		this.presenter = presenter;
		setWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("postPlayerChallengeButton")
	void handlePostPublicChallengeClick(ClickEvent event) {
		String opponentNick = opponentNicknameTextBox.getText();
		presenter.onPostPersonalChallenge(opponentNick);
		hide();
	}

	@UiHandler("opponentNicknameTextBox")
	void handleFocus(FocusEvent event) {
		if (!opponentNicknameTextBoxAlreadyCleared) {
			opponentNicknameTextBox.setText("");
			opponentNicknameTextBoxAlreadyCleared = true;
		}
	}
}
