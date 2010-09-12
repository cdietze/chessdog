package com.christophdietze.jack.client.view;

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

	@UiField
	Label challengeDescriptionLabel;
	@UiField
	Button acceptButton;
	@UiField
	Button declineButton;

	@Inject
	public ChallengeReceivedPopup() {
		super(false);
		setWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("acceptButton")
	void onAcceptClick(ClickEvent e) {
		Window.alert("Hello!");
	}
	@UiHandler("declineButton")
	void onDeclineClick(ClickEvent e) {
		Window.alert("Hello!");
	}
}
