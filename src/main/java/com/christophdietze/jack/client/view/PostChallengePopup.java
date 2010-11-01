package com.christophdietze.jack.client.view;

import com.christophdietze.jack.client.presenter.PostChallengePresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
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

	@UiField
	TextBox opponentNicknameTextBox;
	TextBoxWithPlaceholder opponentNicknameTextBoxWithPlaceholder;
	@UiField
	Button postPlayerChallengeButton;

	@Inject
	public PostChallengePopup(PostChallengePresenter presenter) {
		super(true);
		this.presenter = presenter;
		setWidget(uiBinder.createAndBindUi(this));
		this.opponentNicknameTextBoxWithPlaceholder = TextBoxWithPlaceholder.attachTo(opponentNicknameTextBox,
				"Opponent's Nickname");
		// The onBlur Handler does not get called on the TextBox when the popup hides, so give it an extra chance to show
		// the placeholder
		this.addCloseHandler(new CloseHandler<PopupPanel>() {
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				opponentNicknameTextBoxWithPlaceholder.update();
			}
		});
	}

	@UiHandler("postPlayerChallengeButton")
	void handlePostPublicChallengeClick(ClickEvent event) {
		String opponentNick = opponentNicknameTextBoxWithPlaceholder.getText();
		presenter.onPostPersonalChallenge(opponentNick);
		hide();
	}
}
