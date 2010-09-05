package com.christophdietze.jack.client.view;

import com.christophdietze.jack.client.presenter.CommandPresenter;
import com.christophdietze.jack.client.presenter.GameManager;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class PostChallengePopup extends PopupPanel {

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, PostChallengePopup> {
	}

	private CommandPresenter presenter;

	private GameManager gameManager;
	private GlobalEventBus eventBus;

	@Inject
	public PostChallengePopup(CommandPresenter presenter, GameManager gameManager, GlobalEventBus eventBus) {
		super(true);
		this.presenter = presenter;
		this.gameManager = gameManager;
		this.eventBus = eventBus;
		setWidget(uiBinder.createAndBindUi(this));
		// signInRunningPanel.setVisible(false);
		// presenter.bindView(this);
	}
}
