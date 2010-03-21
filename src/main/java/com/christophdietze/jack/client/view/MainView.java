package com.christophdietze.jack.client.view;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.remote.RemotePoller;
import com.christophdietze.jack.client.util.MyAsyncCallback;
import com.christophdietze.jack.common.ChessServiceAsync;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class MainView {

	@Inject
	private ChessServiceAsync chessService;

	@Inject
	private BoardView boardView;

	@Inject
	private Provider<DragAndDropView> dndViewProvider;

	@Inject
	private Provider<PromotionView> promotionViewProvider;

	@Inject
	private RemotePoller remotePoller;

	private Button togglePollingButton;

	public void buildView(Panel container) {
		dndViewProvider.get();
		promotionViewProvider.get();

		container.add(boardView.getPanel());

		addLoginStuff(container);
		container.add(new HTML("<br/>"));
		addSeekListStuff(container);
		container.add(new HTML("<br/>"));
		addEventPollingStuff(container);
	}

	private void addLoginStuff(Panel container) {
		Button loginButton = new Button("Login");
		container.add(loginButton);

		final Label userNameLabel = new Label("You are not logged in.");
		container.add(userNameLabel);

		loginButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				chessService.login(new MyAsyncCallback<Long>() {
					@Override
					public void onSuccess(Long result) {
						userNameLabel.setText("You are logged in as User with id " + result);
						Log.info("Logged in as user with id " + result);
					}
				});
			}
		});
	}

	private void addSeekListStuff(Panel container) {
		Button seekButton = new Button("Seek Game");
		container.add(seekButton);

		seekButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				chessService.postSeek(new MyAsyncCallback<Void>() {
					@Override
					public void onSuccess(Void result) {
						Log.debug("Seek posted");
					}
				});
			}
		});
	}

	private void addEventPollingStuff(Panel container) {
		togglePollingButton = new Button();
		container.add(togglePollingButton);
		updatePollingButton();
		togglePollingButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				remotePoller.toggleActive();
				updatePollingButton();
			}
		});
	}

	private void updatePollingButton() {
		togglePollingButton.setText(remotePoller.isActive() ? "Turn polling off" : "Turn polling on");
	}
}
