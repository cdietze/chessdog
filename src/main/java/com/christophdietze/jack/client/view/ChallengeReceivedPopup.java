package com.christophdietze.jack.client.view;

import com.christophdietze.jack.client.event.ChallengeReceivedEvent;
import com.christophdietze.jack.client.event.ChallengeReceivedEventHandler;
import com.christophdietze.jack.client.presenter.GameManager;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class ChallengeReceivedPopup extends PopupPanel {

	private static ChallengeReceivedPopupUiBinder uiBinder = GWT.create(ChallengeReceivedPopupUiBinder.class);

	interface ChallengeReceivedPopupUiBinder extends UiBinder<Widget, ChallengeReceivedPopup> {
	}

	private GameManager gameManager;
	private GlobalEventBus eventBus;

	@UiField
	Label challengeDescriptionLabel;
	@UiField
	Button acceptButton;
	@UiField
	Button declineButton;

	@Inject
	public ChallengeReceivedPopup(GameManager gameManager, GlobalEventBus eventBus) {
		super(false);
		this.gameManager = gameManager;
		this.eventBus = eventBus;
		setWidget(uiBinder.createAndBindUi(this));
		initListeners();
	}

	@UiHandler("acceptButton")
	void onAcceptClick(ClickEvent e) {
		Window.alert("Hello accept!");
	}
	@UiHandler("declineButton")
	void onDeclineClick(ClickEvent e) {
		Window.alert("Hello decline!");
	}

	private void initListeners() {
		eventBus.addHandler(ChallengeReceivedEvent.TYPE, new ChallengeReceivedEventHandler() {
			@Override
			public void onChallengeReceived(ChallengeReceivedEvent event) {
				challengeDescriptionLabel.setText("ChallengeId(" + event.getChallengeId() + "): Player "
						+ event.getChallenger() + " challenged you for a match.");
				show();
			}
		});
	}
}
