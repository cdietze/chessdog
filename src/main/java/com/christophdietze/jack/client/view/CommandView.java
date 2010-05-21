package com.christophdietze.jack.client.view;

import com.christophdietze.jack.client.event.MatchEndedEvent;
import com.christophdietze.jack.client.event.MatchEndedEventHandler;
import com.christophdietze.jack.client.event.MatchStartedEvent;
import com.christophdietze.jack.client.event.MatchStartedEventHandler;
import com.christophdietze.jack.client.presenter.CommandPresenter;
import com.christophdietze.jack.client.presenter.GameManager;
import com.christophdietze.jack.client.presenter.GameMode;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class CommandView extends Composite implements CommandPresenter.View {

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, CommandView> {
	}

	private CommandPresenter presenter;

	private GameManager gameManager;
	private GlobalEventBus eventBus;

	@UiField
	Button seekLink;
	@UiField
	HTMLPanel seekRunningPanel;
	@UiField
	Button abortMatchLink;

	@Inject
	public CommandView(CommandPresenter presenter, GameManager gameManager, GlobalEventBus eventBus) {
		this.presenter = presenter;
		this.gameManager = gameManager;
		this.eventBus = eventBus;
		initWidget(uiBinder.createAndBindUi(this));
		seekRunningPanel.setVisible(false);
		presenter.bindView(this);
		initListeners();
	}

	private void initListeners() {
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
		abortMatchLink.setVisible(gameManager.getCurrentMode() == GameMode.MATCH_MODE);
	}
}
