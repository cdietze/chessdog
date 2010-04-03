package com.christophdietze.jack.client.view;

import com.christophdietze.jack.client.presenter.ApplicationContext;
import com.christophdietze.jack.client.presenter.CommandPresenter;
import com.christophdietze.jack.client.presenter.GameModeManager;
import com.christophdietze.jack.client.presenter.MatchMode;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class CommandView extends Composite implements CommandPresenter.View {

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, CommandView> {
	}

	private CommandPresenter presenter;

	private ApplicationContext applicationContext;
	private GameModeManager gameModeManager;

	@UiField
	Anchor signInLink;
	@UiField
	Label userNameLabel;
	@UiField
	Anchor signOutLink;
	@UiField
	HTMLPanel spacer1;
	@UiField
	Anchor seekLink;
	@UiField
	Anchor abortMatchLink;

	@Inject
	public CommandView(CommandPresenter presenter, ApplicationContext applicationContext, GameModeManager gameModeManager) {
		this.presenter = presenter;
		this.applicationContext = applicationContext;
		this.gameModeManager = gameModeManager;
		initWidget(uiBinder.createAndBindUi(this));
		presenter.bindView(this);
		initListeners();
	}
	private void initListeners() {
		signInLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				presenter.onSignInClick();
			}
		});
		signOutLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				presenter.onSignOutClick();
			}
		});
		seekLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				presenter.onSeekClick();
			}
		});
		abortMatchLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				presenter.onAbortMatchClick();
			}
		});
	}

	@Override
	public void update() {
		boolean signedIn = applicationContext.isSignedIn();
		userNameLabel.setVisible(signedIn);
		if (signedIn) {
			userNameLabel.setText("You are signed in as User[" + applicationContext.getLocationId() + "]");
		}
		signInLink.setVisible(!signedIn);
		signOutLink.setVisible(signedIn);
		seekLink.setVisible(signedIn);
		abortMatchLink.setVisible(gameModeManager.getCurrentMode() instanceof MatchMode);
	}
}
