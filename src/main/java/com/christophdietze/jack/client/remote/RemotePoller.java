package com.christophdietze.jack.client.remote;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.event.SignedInEvent;
import com.christophdietze.jack.client.event.SignedInEventHandler;
import com.christophdietze.jack.client.presenter.ApplicationContext;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.christophdietze.jack.client.util.MyAsyncCallback;
import com.christophdietze.jack.common.JackServiceAsync;
import com.christophdietze.jack.common.RemoteEvent;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class RemotePoller {

	private static final int POLL_INTERVAL = 1000;

	private ApplicationContext applicationContext;
	private GlobalEventBus eventBus;
	private JackServiceAsync jackService;
	private ChessServiceCallback callback;

	private boolean isActive = false;

	private Timer timer;

	@Inject
	public RemotePoller(ChessServiceCallback callback, JackServiceAsync jackService,
			ApplicationContext applicationContext, GlobalEventBus eventBus) {
		this.callback = callback;
		this.jackService = jackService;
		this.applicationContext = applicationContext;
		this.eventBus = eventBus;
		init();
		initListeners();
	}

	private void init() {
		timer = new Timer() {
			@Override
			public void run() {
				if (!isActive) {
					// we were turned inactive while the timer was running
					return;
				}
				final Timer t = this;
				jackService.poll(applicationContext.getLocationId(), new MyAsyncCallback<RemoteEvent>() {
					@Override
					public void onSuccess(RemoteEvent result) {
						if (result != null) {
							callback.dispatchEvent(result);
						}
						if (isActive) {
							t.schedule(POLL_INTERVAL);
						}
					}
				});
			}
		};
	}

	private void initListeners() {
		eventBus.addHandler(SignedInEvent.TYPE, new SignedInEventHandler() {
			@Override
			public void onSignIn(SignedInEvent event) {
				activate();
			}
		});
	}

	private void activate() {
		if (!isActive) {
			Log.debug("Starting remote event polling");
			isActive = true;
			timer.run();
		}
	}
}
