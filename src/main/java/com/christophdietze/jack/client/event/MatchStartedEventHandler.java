package com.christophdietze.jack.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface MatchStartedEventHandler extends EventHandler {
	public void onMatchStarted(MatchStartedEvent event);
}
