package com.christophdietze.jack.client.view;

import com.christophdietze.jack.client.event.MatchEndedEvent;
import com.christophdietze.jack.client.event.MatchEndedEventHandler;
import com.christophdietze.jack.client.event.MatchStartedEvent;
import com.christophdietze.jack.client.event.MatchStartedEventHandler;
import com.christophdietze.jack.client.event.SignedInEvent;
import com.christophdietze.jack.client.event.SignedInEventHandler;
import com.christophdietze.jack.client.presenter.ApplicationContext;
import com.christophdietze.jack.client.presenter.CommandPresenter;
import com.christophdietze.jack.client.presenter.GameModeManager;
import com.christophdietze.jack.client.presenter.MatchMode;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class CommandView extends Composite implements CommandPresenter.View {

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, CommandView> {
	}

	private CommandPresenter presenter;

	private ApplicationContext applicationContext;
	private GameModeManager gameModeManager;
	private GlobalEventBus eventBus;

	// @UiField
	// HTML statusLabel;
	// @UiField
	// Button signInLink;
	@UiField
	Button seekLink;
	@UiField
	HTMLPanel seekRunningPanel;
	@UiField
	Button abortMatchLink;

	@Inject
	public CommandView(CommandPresenter presenter, ApplicationContext applicationContext,
			GameModeManager gameModeManager, GlobalEventBus eventBus) {
		this.presenter = presenter;
		this.applicationContext = applicationContext;
		this.gameModeManager = gameModeManager;
		this.eventBus = eventBus;
		initWidget(uiBinder.createAndBindUi(this));
		// seekLink.setVisible(false);
		seekRunningPanel.setVisible(false);
		presenter.bindView(this);
		initListeners();
	}

	private void initListeners() {
		// signInLink.addClickHandler(new ClickHandler() {
		// @Override
		// public void onClick(ClickEvent event) {
		// presenter.onSignInClick();
		// }
		// });
		seekLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				presenter.onSeekClick();
				seekLink.setVisible(false);
				seekRunningPanel.setVisible(true);
			}
		});
		abortMatchLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				presenter.onAbortMatchClick();
			}
		});

		// eventBus.addHandler(SignedInEvent.TYPE, new SignedInEventHandler() {
		// @Override
		// public void onSignIn(SignedInEvent event) {
		// seekLink.setVisible(true);
		// }
		// });
		eventBus.addHandler(MatchStartedEvent.TYPE, new MatchStartedEventHandler() {
			@Override
			public void onMatchStarted(MatchStartedEvent event) {
				seekLink.setVisible(false);
				seekRunningPanel.setVisible(false);
				update();
			}
		});
		eventBus.addHandler(MatchEndedEvent.TYPE, new MatchEndedEventHandler() {
			@Override
			public void onMatchEnded(MatchEndedEvent event) {
				seekLink.setVisible(true);
				seekRunningPanel.setVisible(false);
				update();
			}
		});
	}

	@Override
	public void update() {
		// String statusText = getStatusText();
		// statusLabel.setHTML(statusText);

		boolean signedIn = applicationContext.isSignedIn();
		// signInLink.setVisible(!signedIn);
		abortMatchLink.setVisible(gameModeManager.getCurrentMode() instanceof MatchMode);
	}

	private String getStatusText() {
		boolean signedIn = applicationContext.isSignedIn();
		StringBuilder sb = new StringBuilder();
		// if (!signedIn) {
		// sb.append("You are not signed in.");
		// } else {
		// sb.append("You are signed in as User[" + applicationContext.getLocationId() + "].");
		// }
		// sb.append("<br/>");
		// if (gameModeManager.getCurrentMode() instanceof AnalysisMode) {
		// } else if (gameModeManager.getCurrentMode() instanceof MatchMode) {
		// sb.append("<br/>");
		// MatchMode matchMode = (MatchMode) gameModeManager.getCurrentMode();
		// sb.append("You play as ");
		// sb.append(matchMode.isPlayerWhite() ? "white" : "black");
		// sb.append(" against User[" + matchMode.getOpponentId() + "].");
		// }
		return sb.toString();
	}
}
