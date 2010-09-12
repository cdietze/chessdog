package com.christophdietze.jack.client.view;

import com.christophdietze.jack.client.presenter.ChallengeReceivedPresenter;
import com.christophdietze.jack.shared.Player;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class ChallengeReceivedPopup extends PopupPanel implements ChallengeReceivedPresenter.View {

	private static ChallengeReceivedPopupUiBinder uiBinder = GWT.create(ChallengeReceivedPopupUiBinder.class);

	interface ChallengeReceivedPopupUiBinder extends UiBinder<Widget, ChallengeReceivedPopup> {
	}

	private long challengeId;
	private ChallengeReceivedPresenter presenter;

	@UiField
	Label challengeDescriptionLabel;
	@UiField
	Button acceptButton;
	@UiField
	Button declineButton;

	@Inject
	public ChallengeReceivedPopup(ChallengeReceivedPresenter presenter) {
		super(false, true);
		setGlassEnabled(true);
		this.presenter = presenter;
		setWidget(uiBinder.createAndBindUi(this));
		presenter.bindView(this);
	}

	@UiHandler("acceptButton")
	void onAcceptClick(ClickEvent e) {
		presenter.onAcceptChallenge(challengeId);
		hide();
	}

	@UiHandler("declineButton")
	void onDeclineClick(ClickEvent e) {
		presenter.onDeclineChallenge(challengeId);
		hide();
	}

	@Override
	public void showPopup(long challengeId, Player challenger) {
		this.challengeId = challengeId;
		challengeDescriptionLabel.setText(challenger.getNickname() + " challenged you for a match.");
		show();
	}
}
