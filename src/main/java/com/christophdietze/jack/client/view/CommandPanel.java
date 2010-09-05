package com.christophdietze.jack.client.view;

import com.christophdietze.jack.client.event.MatchEndedEvent;
import com.christophdietze.jack.client.event.MatchEndedEventHandler;
import com.christophdietze.jack.client.event.MatchStartedEvent;
import com.christophdietze.jack.client.event.MatchStartedEventHandler;
import com.christophdietze.jack.client.event.SignedInEvent;
import com.christophdietze.jack.client.event.SignedInEventHandler;
import com.christophdietze.jack.client.presenter.CommandPresenter;
import com.christophdietze.jack.client.presenter.GameManager;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
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

	private GameManager gameManager;
	private GlobalEventBus eventBus;

	private PostChallengePopup postChallengePopup;

	@UiField
	HTMLPanel signInPanel;
	@UiField
	TextBox nickTextBox;
	@UiField
	Button signInButton;
	@UiField
	HTMLPanel signInRunningPanel;

	@UiField
	HTMLPanel signOutPanel;
	@UiField
	Label signInStatusLabel;
	@UiField
	Button signOutButton;
	@UiField
	Button startMatchButton;

	@UiField
	Button abortMatchLink;
	@UiField
	HTMLPanel activeMatchPanel;

	@Inject
	public CommandPanel(CommandPresenter presenter, GameManager gameManager, GlobalEventBus eventBus,
			PostChallengePopup postChallengePopup) {
		this.presenter = presenter;
		this.gameManager = gameManager;
		this.eventBus = eventBus;
		this.postChallengePopup = postChallengePopup;
		initWidget(uiBinder.createAndBindUi(this));
		// signInRunningPanel.setVisible(false);
		presenter.bindView(this);
		initListeners();
	}

	private void initListeners() {
		signInButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doSignIn();
			}
		});
		eventBus.addHandler(SignedInEvent.TYPE, new SignedInEventHandler() {
			@Override
			public void onSignIn(SignedInEvent event) {
				signInPanel.setVisible(false);
				signInRunningPanel.setVisible(false);
				signInStatusLabel.setText("You are signed in at location " + event.getLocationId());
				signOutPanel.setVisible(true);
				startMatchButton.setVisible(true);
			}
		});
		eventBus.addHandler(MatchStartedEvent.TYPE, new MatchStartedEventHandler() {
			@Override
			public void onMatchStarted(MatchStartedEvent event) {
				startMatchButton.setVisible(false);
				activeMatchPanel.setVisible(true);
				update();
			}
		});
		eventBus.addHandler(MatchEndedEvent.TYPE, new MatchEndedEventHandler() {
			@Override
			public void onMatchEnded(MatchEndedEvent event) {
				activeMatchPanel.setVisible(false);
				startMatchButton.setVisible(true);
				update();
			}
		});
	}

	@UiHandler("nickTextBox")
	void handleFocus(FocusEvent event) {
		nickTextBox.setText("");
	}

	@UiHandler("nickTextBox")
	void handleEnter(KeyPressEvent event) {
		if (event.getCharCode() == '\r') {
			doSignIn();
		}
	}

	@UiHandler("startMatchButton")
	void handleStartGameClick(ClickEvent event) {
		postChallengePopup.showRelativeTo(startMatchButton);
	}

	private void doSignIn() {
		String nick = nickTextBox.getText();
		// TODO validate nick
		presenter.onSignInClick(nick);
		signInPanel.setVisible(false);
		signInRunningPanel.setVisible(true);
	}

	@Override
	public void update() {
		// abortMatchLink.setVisible(gameManager.getCurrentMode() == GameMode.MATCH_MODE);
	}
}
