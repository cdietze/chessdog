package com.christophdietze.jack.client.presenter;

import com.christophdietze.jack.client.util.GlobalEventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class SeekManager {

	private GlobalEventBus eventBus;

	private boolean seekRunning;

	@Inject
	public SeekManager(GlobalEventBus eventBus) {
		this.eventBus = eventBus;
		initListeners();
	}

	public boolean isSeekRunning() {
		return seekRunning;
	}

	private void initListeners() {
	}
}
