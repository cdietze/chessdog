package com.christophdietze.jack.client.remote;

import com.christophdietze.jack.client.util.MyAsyncCallback;
import com.christophdietze.jack.common.ChessServiceAsync;
import com.christophdietze.jack.common.RemoteEvent;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class RemotePoller {

	private static final int POLL_INTERVAL = 2000;

	private ChessServiceAsync chessService;
	private ChessServiceCallback callback;

	private boolean isActive = false;

	private Timer timer;

	@Inject
	public RemotePoller(ChessServiceCallback callback, ChessServiceAsync chessService) {
		this.callback = callback;
		this.chessService = chessService;
		init();
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
				chessService.poll(new MyAsyncCallback<RemoteEvent>() {
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

	public void toggleActive() {
		if (isActive) {
			isActive = false;
		} else {
			isActive = true;
			timer.run();
		}
	}

	public boolean isActive() {
		return isActive;
	}
}
