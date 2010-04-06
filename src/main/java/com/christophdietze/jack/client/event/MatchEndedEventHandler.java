package com.christophdietze.jack.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface MatchEndedEventHandler extends EventHandler {
	public void onMatchEnded(MatchEndedEvent event);
}
