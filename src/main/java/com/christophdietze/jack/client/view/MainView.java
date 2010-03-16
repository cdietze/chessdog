package com.christophdietze.jack.client.view;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.remote.RemotePoller;
import com.christophdietze.jack.client.util.MyAsyncCallback;
import com.christophdietze.jack.common.ChessServiceAsync;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
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
