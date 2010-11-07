package com.christophdietze.jack.client.view;

import com.christophdietze.jack.client.event.MatchEndedEvent;
import com.christophdietze.jack.client.event.MatchEndedEventHandler;
import com.christophdietze.jack.client.event.MatchStartedEvent;
import com.christophdietze.jack.client.event.MatchStartedEventHandler;
import com.christophdietze.jack.client.event.SignInFailedEvent;
import com.christophdietze.jack.client.event.SignInFailedEventHandler;
import com.christophdietze.jack.client.event.SignedInEvent;
import com.christophdietze.jack.client.event.SignedInEventHandler;
import com.christophdietze.jack.client.event.SignedOutEvent;
import com.christophdietze.jack.client.event.SignedOutEventHandler;
import com.christophdietze.jack.client.presenter.CommandPresenter;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class CommandPanel extends Composite implements CommandPresenter.View {

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, CommandPanel> {
	}

	private CommandPresenter presenter;

	private GlobalEventBus eventBus;

	private PostChallengePopup postChallengePopup;

	@UiField
	HTMLPanel signInPanel;
	@UiField
	TextBox nicknameTextBox;
	TextBoxWithPlaceholder nicknameTextBoxWithPlaceHolder;
	@UiField
	Button signInButton;
	@UiField
	HTML nicknameValidationLabel;
	@UiField
	HTMLPanel signInRunningPanel;

	@UiField
	HTMLPanel signOutPanel;
	@UiField
	Label signInStatusLabel;
	@UiField
	Button signOutButton;

	@UiField
	HTMLPanel newGamePanel;
	@UiField
	Button newGameButton;

	@UiField
	Button startMatchButton;
	@UiField
	Button abortMatchButton;
	@UiField
	HTMLPanel activeMatchPanel;

	@Inject
	public CommandPanel(CommandPresenter presenter, GlobalEventBus eventBus, PostChallengePopup postChallengePopup) {
		this.presenter = presenter;
		this.eventBus = eventBus;
		this.postChallengePopup = postChallengePopup;
		initWidget(uiBinder.createAndBindUi(this));
		this.nicknameTextBoxWithPlaceHolder = TextBoxWithPlaceholder.attachTo(nicknameTextBox, "Your Nickname");
		presenter.bindView(this);
		initListeners();
	}

	public void setSignInStatusToSignedOut() {
		signInPanel.setVisible(true);
		signInRunningPanel.setVisible(false);
		signOutPanel.setVisible(false);
		startMatchButton.setVisible(false);
	}

	public void setSignInStatusToSigningIn() {
		signInPanel.setVisible(false);
		signInRunningPanel.setVisible(true);
	}

	public void setSignInStatusToSignedIn(String nickname) {
		signInPanel.setVisible(false);
		signInRunningPanel.setVisible(false);
		signInStatusLabel.setText("You are signed in as " + nickname);
		signOutPanel.setVisible(true);
		startMatchButton.setVisible(true);
	}

	@Override
	public void showNicknameValidationError(String msg) {
		nicknameValidationLabel.setHTML(msg);
	}

	@Override
	public void hideNicknameValidationError() {
		nicknameValidationLabel.setHTML("");
	}

	private void initListeners() {
		eventBus.addHandler(SignedInEvent.TYPE, new SignedInEventHandler() {
			@Override
			public void onSignIn(SignedInEvent event) {
				setSignInStatusToSignedIn(event.getMyPlayer().getNickname());
			}
		});
		eventBus.addHandler(SignedOutEvent.TYPE, new SignedOutEventHandler() {
			@Override
			public void onSignOut(SignedOutEvent event) {
				setSignInStatusToSignedOut();
			}
		});
		eventBus.addHandler(SignInFailedEvent.TYPE, new SignInFailedEventHandler() {
			@Override
			public void onSignInFailed(SignInFailedEvent event) {
				setSignInStatusToSignedOut();
			}
		});
		eventBus.addHandler(MatchStartedEvent.TYPE, new MatchStartedEventHandler() {
			@Override
			public void onMatchStarted(MatchStartedEvent event) {
				newGamePanel.setVisible(false);
				startMatchButton.setVisible(false);
				activeMatchPanel.setVisible(true);
			}
		});
		eventBus.addHandler(MatchEndedEvent.TYPE, new MatchEndedEventHandler() {
			@Override
			public void onMatchEnded(MatchEndedEvent event) {
				newGamePanel.setVisible(true);
				startMatchButton.setVisible(true);
				activeMatchPanel.setVisible(false);
			}
		});
	}

	@UiHandler("nicknameTextBox")
	void handleEnter(KeyPressEvent event) {
		if (event.getCharCode() == '\r') {
			doSignIn();
		}
	}

	@UiHandler("signInButton")
	void handleSignInClick(ClickEvent event) {
		doSignIn();
	}

	@UiHandler("signOutButton")
	void handleSignOutClick(ClickEvent event) {
		presenter.onSignOutClick();
	}

	@UiHandler("newGameButton")
	void handleNewGameClick(ClickEvent event) {
		presenter.onNewGameClick();
	}

	@UiHandler("startMatchButton")
	void handleStartGameClick(ClickEvent event) {
		postChallengePopup.showRelativeTo(startMatchButton);
	}

	@UiHandler("abortMatchButton")
	void handleAbortMatchClick(ClickEvent event) {
		presenter.onAbortMatchClick();
	}

	private void doSignIn() {
		String nick = nicknameTextBoxWithPlaceHolder.getText();
		// TODO validate nick
		presenter.onSignInClick(nick);
	}
}
