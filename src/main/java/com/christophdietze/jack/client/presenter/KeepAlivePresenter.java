package com.christophdietze.jack.client.presenter;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.event.SignedInEvent;
import com.christophdietze.jack.client.event.SignedInEventHandler;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.christophdietze.jack.client.util.MyAsyncCallback;
import com.christophdietze.jack.shared.ChessServiceAsync;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;

public class KeepAlivePresenter {

	private static final int KEEP_ALIVE_INTERVAL = 1000 * 60 * 10; // 10 minutes

	private ChessServiceAsync chessService;
	private ApplicationContext applicationContext;
	private GlobalEventBus eventBus;
	private boolean isRunning = false;

	@Inject
	public KeepAlivePresenter(ChessServiceAsync chessService, ApplicationContext applicationContext,
			GlobalEventBus eventBus) {
		this.chessService = chessService;
		this.applicationContext = applicationContext;
		this.eventBus = eventBus;
		initListeners();
		Log.debug(this.getClass().getName() + " initialized");
	}

	public void startTimer() {
		final Timer t = new Timer() {
			@Override
			public void run() {
				if (applicationContext.isLoggedIn()) {
					chessService.keepAlive(applicationContext.getLocationId(), MyAsyncCallback.<Void> doNothing());
				}
			}
		};
		t.scheduleRepeating(KEEP_ALIVE_INTERVAL);
	}

	private void initListeners() {
		eventBus.addHandler(SignedInEvent.TYPE, new SignedInEventHandler() {
			@Override
			public void onSignIn(SignedInEvent event) {
				if (!isRunning) {
					startTimer();
					isRunning = true;
				}
			}
		});
	}
}
